package com.rachel.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;
import com.rachel.gulimall.product.dao.CategoryDao;
import com.rachel.gulimall.product.entity.CategoryEntity;
import com.rachel.gulimall.product.service.CategoryBrandRelationService;
import com.rachel.gulimall.product.service.CategoryService;
import com.rachel.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 每一个需要缓存的数据我们都来指定要放到那个名字的缓存。【缓存的分区(按照业务类型分)】
 * 代表当前方法的结果需要缓存，如果缓存中有，方法都不用调用，如果缓存中没有，会调用方法。最后将方法的结果放入缓存
 * 默认行为
 *      如果缓存中有，方法不再调用
 *      key是默认生成的:缓存的名字::SimpleKey::[](自动生成key值)
 *      缓存的value值，默认使用jdk序列化机制，将序列化的数据存到redis中
 *      默认时间是 -1：
 *
 *   自定义操作：key的生成
 *      指定生成缓存的key：key属性指定，接收一个Spel
 *      指定缓存的数据的存活时间:配置文档中修改存活时间
 *      将数据保存为json格式
 *
 *
 * 4、Spring-Cache的不足之处：
 *  1）、读模式
 *      缓存穿透：查询一个null数据。解决方案：缓存空数据
 *      缓存击穿：大量并发进来同时查询一个正好过期的数据。解决方案：加锁 ? 默认是无加锁的;使用sync = true来解决击穿问题
 *      缓存雪崩：大量的key同时过期。解决：加随机时间。加上过期时间
 *  2)、写模式：（缓存与数据库一致）
 *      1）、读写加锁。
 *      2）、引入Canal,感知到MySQL的更新去更新Redis
 *      3）、读多写多，直接去数据库查询就行
 *
 *  总结：
 *      常规数据（读多写少，即时性，一致性要求不高的数据，完全可以使用Spring-Cache）：写模式(只要缓存的数据有过期时间就足够了)
 *      特殊数据：特殊设计
 *
 *  原理：
 *      CacheManager(RedisCacheManager)->Cache(RedisCache)->Cache负责缓存的读写
 * @return
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(new Query<CategoryEntity>().getPage(params), new QueryWrapper<CategoryEntity>());

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree(Map<String, Object> params) {
        QueryWrapper<CategoryEntity> queryWrapper = new QueryWrapper<CategoryEntity>();
        List<CategoryEntity> categoryEntities = baseMapper.selectList(queryWrapper);
        if (categoryEntities == null) {
            return new ArrayList<>();
        }
        List<CategoryEntity> levelMenus = categoryEntities.stream().filter(categoryEntity -> categoryEntity.getParentCid() == 0).map((menu) -> {
            menu.setChildren(getCategoryChildren(menu, categoryEntities));
            return menu;
        }).sorted(Comparator.comparingInt(CategoryEntity::getSort)).collect(Collectors.toList());
        return levelMenus;
    }

    @Override
    public void deleteMenus(List<Long> categoryIds) {
        // 判断不能删除的菜单
        baseMapper.deleteBatchIds(categoryIds);
    }

    @Override
    public Long[] findCatelogPathByCatId(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        findParentCatelogId(catelogId, paths);
        Collections.reverse(paths);
        return paths.toArray(new Long[paths.size()]);
    }

    //    @Caching(evict = {
//            @CacheEvict(value = {"categorys"}, key = "'getLevel1Categorys'"),
//            @CacheEvict(value = {"categorys"}, key = "'getCatalogJson'")
//    })
    @CacheEvict(value = "categorys", allEntries = true) // 失效模式
    // @CachePut()    双写模式
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);

        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }


    @Cacheable(value = {"categorys"}, key = "#root.methodName")
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        System.out.println("getLevel1Categorys........");
        long l = System.currentTimeMillis();
        List<CategoryEntity> categoryEntities = this.baseMapper.selectList(
                new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        System.out.println("消耗时间：" + (System.currentTimeMillis() - l) + "ms");
        return categoryEntities;
    }

    @Cacheable(value = "categorys", key = "#root.methodName")
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        System.out.println("查询了数据库。。。。");
        //将数据库的多次查询变为一次
        List<CategoryEntity> selectList = this.baseMapper.selectList(null);

        //1、查出所有分类
        //1、1）查出所有一级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

        //封装数据
        Map<String, List<Catelog2Vo>> parentCid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //1、每一个的一级分类,查到这个一级分类的二级分类
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());

            //2、封装上面的结果
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName().toString());

                    //1、找当前二级分类的三级分类封装成vo
                    List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());

                    if (level3Catelog != null) {
                        List<Catelog2Vo.Category3Vo> category3Vos = level3Catelog.stream().map(l3 -> {
                            //2、封装成指定格式
                            Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                            return category3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(category3Vos);
                    }

                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        return parentCid;
    }

    /**
     * spring 2.0 后 默认使用letuce 操作redis 基于netty进行网络通信
     * letuce5.x 有 bug 导致netty堆外内存溢出
     * <p>
     * 解决方案1） 调大对外内存
     * 解决方案2） 升级客户端
     * 解决方案3） 切换jedis
     * <p>
     * 本系统使用的时候 用的6.x 没有这个bug了 所有无需变动
     *
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJson2() {
        // 从redis中获取数据
        String key = "catalogJSON";
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        String catalogJSON = stringValueOperations.get(key);
        if (StringUtils.isEmpty(catalogJSON)) {
            Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonFromDBWithRedisLock();
            return catalogJsonFromDb;
        }
        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
        });
        System.out.println("缓存命中。。。。直接返回");

        return result;
    }

    private Map<String, List<Catelog2Vo>> getCatalogJsonFromDBAndSetRedisCache() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String catalogJSON = operations.get("catalogJSON");
        if (!StringUtils.isEmpty(catalogJSON)) {
            return JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });
        }
        //将数据库的多次查询变为一次
        List<CategoryEntity> selectList = this.baseMapper.selectList(null);

        //1、查出所有分类
        //1、1）查出所有一级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

        //封装数据
        Map<String, List<Catelog2Vo>> parentCid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //1、每一个的一级分类,查到这个一级分类的二级分类
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());

            //2、封装上面的结果
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName().toString());

                    //1、找当前二级分类的三级分类封装成vo
                    List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());

                    if (level3Catelog != null) {
                        List<Catelog2Vo.Category3Vo> category3Vos = level3Catelog.stream().map(l3 -> {
                            //2、封装成指定格式
                            Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                            return category3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(category3Vos);
                    }

                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        System.out.println("查询了数据库。。。。");
        operations.set("catalogJSON", JSON.toJSONString(parentCid), 1, TimeUnit.DAYS);
        return parentCid;
    }


    /**
     * 缓存数据一致性
     * 1）双写模式 修改数据库后修改缓存 可能产生脏数据
     * 2）缓存失效 修改数据库后删除缓存
     *
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDBWithRedissonLock() {
        // 创建分布式锁
        RLock lock = redissonClient.getLock("catalogJson-lock");
        lock.lock();
        try {
            System.out.println("redisson获取到分布式锁。。。");
            // 获取到分布式锁 执行操作
            return getCatalogJsonFromDBAndSetRedisCache();
        } finally {
            lock.unlock();
        }
    }

    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDBWithRedisLock() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String uuid = UUID.randomUUID().toString();
        Boolean success = operations.setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
        if (success) {
            try {
                System.out.println("成功获取到分布式锁。。。");
                // 获取到分布式锁 执行操作
                return getCatalogJsonFromDBAndSetRedisCache();
            } finally {
                // 获取值和对比操作必须是一个原子操作
                String lua = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                stringRedisTemplate.execute(new DefaultRedisScript<>(lua, Long.class), Arrays.asList("lock"), uuid);
            }
        }

        System.out.println("获取分布式锁失败...等待重试...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 无限重试
        return getCatalogJsonFromDBWithRedisLock();
    }

    public synchronized Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithLocalLock() {
        return getCatalogJsonFromDBAndSetRedisCache();
    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parentCid) {
        List<CategoryEntity> categoryEntities = selectList.stream().filter(
                item -> item.getParentCid().equals(parentCid)).collect(Collectors.toList());
        return categoryEntities;
    }

    private List<Long> findParentCatelogId(Long catelogId, List<Long> paths) {
        paths.add(catelogId);
        CategoryEntity category = this.getById(catelogId);
        if (category != null && category.getParentCid() != 0) {
            findParentCatelogId(category.getParentCid(), paths);
        }
        return paths;
    }

    /**
     * 找到当前节点的子节点
     *
     * @param root
     * @param all
     * @return
     */
    private List<CategoryEntity> getCategoryChildren(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> ChildrenMenus = all.stream().filter(categoryEntity -> categoryEntity.getParentCid().equals(root.getCatId())).map((menu) -> {
            menu.setChildren(getCategoryChildren(menu, all));
            return menu;
        }).sorted((obj1, obj2) -> {
            return obj1.getSort() == null ? 0 : obj1.getSort() - (obj2.getSort() == null ? 0 : obj2.getSort());
        }).collect(Collectors.toList());
        return ChildrenMenus;
    }

}
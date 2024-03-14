package com.rachel.gulimall.product.service.impl;

import com.rachel.gulimall.product.service.CategoryBrandRelationService;
import com.rachel.gulimall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;

import com.rachel.gulimall.product.dao.CategoryDao;
import com.rachel.gulimall.product.entity.CategoryEntity;
import com.rachel.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

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


    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);

        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }


    }

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        System.out.println("getLevel1Categorys........");
        long l = System.currentTimeMillis();
        List<CategoryEntity> categoryEntities = this.baseMapper.selectList(
                new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        System.out.println("消耗时间：" + (System.currentTimeMillis() - l));
        return categoryEntities;
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        System.out.println("查询了数据库");

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
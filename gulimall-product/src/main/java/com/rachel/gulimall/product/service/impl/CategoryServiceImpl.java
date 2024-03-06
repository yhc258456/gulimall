package com.rachel.gulimall.product.service.impl;

import com.rachel.gulimall.product.service.CategoryBrandRelationService;
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

    private List<Long> findParentCatelogId(Long catelogId, List<Long> paths) {
        paths.add(catelogId);
        CategoryEntity category = this.getById(catelogId);
        if (category.getParentCid() != 0) {
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
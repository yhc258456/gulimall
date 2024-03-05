package com.rachel.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;

import com.rachel.gulimall.product.dao.CategoryDao;
import com.rachel.gulimall.product.entity.CategoryEntity;
import com.rachel.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree(Map<String, Object> params) {
        QueryWrapper<CategoryEntity> queryWrapper = new QueryWrapper<CategoryEntity>();
        List<CategoryEntity> categoryEntities = baseMapper.selectList(queryWrapper);
        if (categoryEntities == null) {
            return new ArrayList<>();
        }
       List<CategoryEntity> levelMenus = categoryEntities.stream()
               .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
               .map((menu) -> {
                   menu.setChildren(getCategoryChildren(menu, categoryEntities));
                   return menu;})
               .sorted(Comparator.comparingInt(CategoryEntity::getSort))
               .collect(Collectors.toList());
        return levelMenus;
    }

    @Override
    public void deleteMenus(List<Long> categoryIds) {
        // 判断不能删除的菜单
        baseMapper.deleteBatchIds(categoryIds);
    }

    /**
     * 找到当前节点的子节点
     * @param root
     * @param all
     * @return
     */
    private List<CategoryEntity> getCategoryChildren(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> ChildrenMenus = all.stream()
                .filter(categoryEntity ->  categoryEntity.getParentCid().equals(root.getCatId()))
                .map((menu) -> {
                    menu.setChildren(getCategoryChildren(menu, all));
                    return menu;})
                .sorted(Comparator.comparingInt(CategoryEntity::getSort))
                .collect(Collectors.toList());
        return ChildrenMenus;
    }

}
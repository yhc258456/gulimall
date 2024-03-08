package com.rachel.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;
import com.rachel.gulimall.product.dao.BrandDao;
import com.rachel.gulimall.product.dao.CategoryBrandRelationDao;
import com.rachel.gulimall.product.dao.CategoryDao;
import com.rachel.gulimall.product.entity.BrandEntity;
import com.rachel.gulimall.product.entity.CategoryBrandRelationEntity;
import com.rachel.gulimall.product.entity.CategoryEntity;
import com.rachel.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        BrandEntity brandEntity = brandDao.selectById(brandId);
        Long catelogId = categoryBrandRelation.getCatelogId();
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);

        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        UpdateWrapper<CategoryBrandRelationEntity> updateWrapper = new UpdateWrapper<>();
        CategoryBrandRelationEntity brandRelationEntity = new CategoryBrandRelationEntity();
        brandRelationEntity.setBrandId(brandId);
        brandRelationEntity.setBrandName(name);
        updateWrapper.eq("brand_id", brandId);
        this.update(brandRelationEntity, updateWrapper);
    }

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId, name);
    }

    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        List<CategoryBrandRelationEntity> brandRelationEntities = this.baseMapper.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        List<Long> brandIds = brandRelationEntities.stream().map(obj -> obj.getBrandId()).collect(Collectors.toList());
        if (brandIds.isEmpty()) {
            return new ArrayList<>();
        }

        return brandDao.selectList(new QueryWrapper<BrandEntity>().in("brand_id", brandIds));
    }

}
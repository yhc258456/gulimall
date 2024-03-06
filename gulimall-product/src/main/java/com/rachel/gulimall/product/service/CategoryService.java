package com.rachel.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rachel.common.utils.PageUtils;
import com.rachel.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author rachelk
 * @email 413843464@qq.com
 * @date 2024-03-01 14:04:28
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree(Map<String, Object> params);

    void deleteMenus(List<Long> categoryIds);

    Long[] findCatelogPathByCatId(Long catelogId);
}


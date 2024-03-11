package com.rachel.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rachel.common.utils.PageUtils;
import com.rachel.gulimall.product.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku
 *
 * @author rachelk
 * @email 413843464@qq.com
 * @date 2024-03-01 14:04:27
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCondition(Map<String, Object> params);
}


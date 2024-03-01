package com.rachel.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rachel.common.utils.PageUtils;
import com.rachel.gulimall.product.entity.BrandEntity;

import java.util.Map;

/**
 * Ʒ
 *
 * @author rachelk
 * @email 413843464@qq.com
 * @date 2024-03-01 14:04:28
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


package com.rachel.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rachel.common.utils.PageUtils;
import com.rachel.gulimall.product.entity.SkuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * skuͼƬ
 *
 * @author rachelk
 * @email 413843464@qq.com
 * @date 2024-03-01 14:04:27
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SkuImagesEntity> getImagesBySkuId(Long skuId);
}


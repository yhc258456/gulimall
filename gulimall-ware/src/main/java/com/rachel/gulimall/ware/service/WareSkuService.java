package com.rachel.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rachel.common.to.SkuHasStockVo;
import com.rachel.common.utils.PageUtils;
import com.rachel.gulimall.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author rachel
 * @email 413843464@qq.com
 * @date 2024-03-01 15:55:35
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);
}


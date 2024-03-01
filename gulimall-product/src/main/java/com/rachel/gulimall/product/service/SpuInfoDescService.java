package com.rachel.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rachel.common.utils.PageUtils;
import com.rachel.gulimall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spu
 *
 * @author rachelk
 * @email 413843464@qq.com
 * @date 2024-03-01 14:04:27
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


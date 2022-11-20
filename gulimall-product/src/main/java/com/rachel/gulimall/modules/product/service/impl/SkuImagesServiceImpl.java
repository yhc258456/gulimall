package com.rachel.gulimall.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rachel.gulimall.common.service.impl.CrudServiceImpl;
import com.rachel.gulimall.modules.product.dao.SkuImagesDao;
import com.rachel.gulimall.modules.product.dto.SkuImagesDTO;
import com.rachel.gulimall.modules.product.entity.SkuImagesEntity;
import com.rachel.gulimall.modules.product.service.SkuImagesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * skuÍ¼Æ¬
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Service
public class SkuImagesServiceImpl extends CrudServiceImpl<SkuImagesDao, SkuImagesEntity, SkuImagesDTO> implements SkuImagesService {

    @Override
    public QueryWrapper<SkuImagesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SkuImagesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}
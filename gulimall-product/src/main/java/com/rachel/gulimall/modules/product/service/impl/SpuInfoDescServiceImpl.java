package com.rachel.gulimall.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rachel.gulimall.common.service.impl.CrudServiceImpl;
import com.rachel.gulimall.modules.product.dao.SpuInfoDescDao;
import com.rachel.gulimall.modules.product.dto.SpuInfoDescDTO;
import com.rachel.gulimall.modules.product.entity.SpuInfoDescEntity;
import com.rachel.gulimall.modules.product.service.SpuInfoDescService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * spuÐÅÏ¢½éÉÜ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Service
public class SpuInfoDescServiceImpl extends CrudServiceImpl<SpuInfoDescDao, SpuInfoDescEntity, SpuInfoDescDTO> implements SpuInfoDescService {

    @Override
    public QueryWrapper<SpuInfoDescEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SpuInfoDescEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}
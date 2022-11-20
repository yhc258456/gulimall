package com.rachel.gulimall.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rachel.gulimall.common.service.impl.CrudServiceImpl;
import com.rachel.gulimall.modules.product.dao.AttrAttrgroupRelationDao;
import com.rachel.gulimall.modules.product.dto.AttrAttrgroupRelationDTO;
import com.rachel.gulimall.modules.product.entity.AttrAttrgroupRelationEntity;
import com.rachel.gulimall.modules.product.service.AttrAttrgroupRelationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ÊôÐÔ&ÊôÐÔ·Ö×é¹ØÁª
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Service
public class AttrAttrgroupRelationServiceImpl extends CrudServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity, AttrAttrgroupRelationDTO> implements AttrAttrgroupRelationService {

    @Override
    public QueryWrapper<AttrAttrgroupRelationEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<AttrAttrgroupRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}
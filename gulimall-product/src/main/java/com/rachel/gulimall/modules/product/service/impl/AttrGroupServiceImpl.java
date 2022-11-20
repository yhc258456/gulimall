package com.rachel.gulimall.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rachel.gulimall.common.service.impl.CrudServiceImpl;
import com.rachel.gulimall.modules.product.dao.AttrGroupDao;
import com.rachel.gulimall.modules.product.dto.AttrGroupDTO;
import com.rachel.gulimall.modules.product.entity.AttrGroupEntity;
import com.rachel.gulimall.modules.product.service.AttrGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ÊôÐÔ·Ö×é
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Service
public class AttrGroupServiceImpl extends CrudServiceImpl<AttrGroupDao, AttrGroupEntity, AttrGroupDTO> implements AttrGroupService {

    @Override
    public QueryWrapper<AttrGroupEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}
package com.rachel.gulimall.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rachel.gulimall.common.service.impl.CrudServiceImpl;
import com.rachel.gulimall.modules.product.dao.AttrDao;
import com.rachel.gulimall.modules.product.dto.AttrDTO;
import com.rachel.gulimall.modules.product.entity.AttrEntity;
import com.rachel.gulimall.modules.product.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ÉÌÆ·ÊôÐÔ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Service
public class AttrServiceImpl extends CrudServiceImpl<AttrDao, AttrEntity, AttrDTO> implements AttrService {

    @Override
    public QueryWrapper<AttrEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}
package com.rachel.gulimall.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rachel.gulimall.common.service.impl.CrudServiceImpl;
import com.rachel.gulimall.modules.product.dao.SpuCommentDao;
import com.rachel.gulimall.modules.product.dto.SpuCommentDTO;
import com.rachel.gulimall.modules.product.entity.SpuCommentEntity;
import com.rachel.gulimall.modules.product.service.SpuCommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ÉÌÆ·ÆÀ¼Û
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Service
public class SpuCommentServiceImpl extends CrudServiceImpl<SpuCommentDao, SpuCommentEntity, SpuCommentDTO> implements SpuCommentService {

    @Override
    public QueryWrapper<SpuCommentEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SpuCommentEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}
package com.rachel.gulimall.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rachel.gulimall.common.service.impl.CrudServiceImpl;
import com.rachel.gulimall.modules.product.dao.CommentReplayDao;
import com.rachel.gulimall.modules.product.dto.CommentReplayDTO;
import com.rachel.gulimall.modules.product.entity.CommentReplayEntity;
import com.rachel.gulimall.modules.product.service.CommentReplayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ÉÌÆ·ÆÀ¼Û»Ø¸´¹ØÏµ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Service
public class CommentReplayServiceImpl extends CrudServiceImpl<CommentReplayDao, CommentReplayEntity, CommentReplayDTO> implements CommentReplayService {

    @Override
    public QueryWrapper<CommentReplayEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<CommentReplayEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}
package com.rachel.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;
import com.rachel.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.rachel.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.rachel.gulimall.product.service.AttrAttrgroupRelationService;
import com.rachel.gulimall.product.vo.AttrGroupRelationVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void addAttrRelation(List<AttrGroupRelationVo> relationVos) {
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = relationVos.stream().map(obj -> {
            AttrAttrgroupRelationEntity attrgroupRelation = new AttrAttrgroupRelationEntity();
            attrgroupRelation.setAttrGroupId(obj.getAttrGroupId());
            attrgroupRelation.setAttrId(obj.getAttrId());
            return attrgroupRelation;
        }).collect(Collectors.toList());
        this.saveBatch(attrAttrgroupRelationEntities);

    }

}
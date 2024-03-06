package com.rachel.gulimall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;

import com.rachel.gulimall.product.dao.AttrGroupDao;
import com.rachel.gulimall.product.entity.AttrGroupEntity;
import com.rachel.gulimall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        if (catelogId == 0) {
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    new QueryWrapper<>());
                    return new PageUtils(page);
        } else{
            String key = (String) params.get("key");
            // select * from attr_group where catelog_id = catelogId and (attr_group_name = key or attr_group_id = key)
            QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("catelog_id", catelogId);
            if (!StringUtils.isEmpty(key)) {
                queryWrapper.and((obj) -> obj.eq("attr_group_id", key).or().like("attr_group_name", key));
            }
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    queryWrapper);
            return new PageUtils(page);
        }
    }

}
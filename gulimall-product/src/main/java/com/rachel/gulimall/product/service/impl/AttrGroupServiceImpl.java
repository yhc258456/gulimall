package com.rachel.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;
import com.rachel.gulimall.product.dao.AttrGroupDao;
import com.rachel.gulimall.product.entity.AttrEntity;
import com.rachel.gulimall.product.entity.AttrGroupEntity;
import com.rachel.gulimall.product.service.AttrGroupService;
import com.rachel.gulimall.product.service.AttrService;
import com.rachel.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Autowired
    private AttrService attrService;

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
        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");

        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((obj) -> obj.eq("attr_group_id", key).or().like("attr_group_name", key));
        }
        if (catelogId == 0) {
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    queryWrapper);
            return new PageUtils(page);
        } else {
            queryWrapper.eq("catelog_id", catelogId);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    queryWrapper);
            return new PageUtils(page);
        }
    }

    @Override
    public List<AttrGroupWithAttrsVo> queryGroupsWithAttrsByCatelogId(Long catelogId) {
        // 获取某个分类下的所有分组
        List<AttrGroupEntity> groupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));

        List<AttrGroupWithAttrsVo> attrGroupWithAttrsVos = groupEntities.stream().map(obj -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(obj, attrGroupWithAttrsVo);
            // 获取乜咯分组下的所有属性
            List<AttrEntity> attrEntities = attrService.queryAttrsByGroupId(obj.getAttrGroupId());
            attrGroupWithAttrsVo.setAttrs(attrEntities);
            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());

        return attrGroupWithAttrsVos;
    }

}
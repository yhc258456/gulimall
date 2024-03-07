package com.rachel.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rachel.common.constant.ProductConstant;
import com.rachel.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.rachel.gulimall.product.dao.AttrGroupDao;
import com.rachel.gulimall.product.dao.CategoryDao;
import com.rachel.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.rachel.gulimall.product.entity.AttrGroupEntity;
import com.rachel.gulimall.product.entity.CategoryEntity;
import com.rachel.gulimall.product.service.CategoryService;
import com.rachel.gulimall.product.vo.AttrRespVo;
import com.rachel.gulimall.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.Query;

import com.rachel.gulimall.product.dao.AttrDao;
import com.rachel.gulimall.product.entity.AttrEntity;
import com.rachel.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        // 保存基本数据
        this.save(attrEntity);
        // 保存分组关系
        if (ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() == attrEntity.getAttrType()) {
            AttrAttrgroupRelationEntity attrgroupRelation = new AttrAttrgroupRelationEntity();
            attrgroupRelation.setAttrGroupId(attr.getAttrGroupId());
            attrgroupRelation.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.insert(attrgroupRelation);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType) {
        String key = (String) params.get("key");
        QueryWrapper<AttrEntity> entityQuery = new QueryWrapper<>();
        entityQuery.eq("attr_type", "base".equalsIgnoreCase(attrType) ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        if (!StringUtils.isEmpty(key)) {
            entityQuery.eq("attr_id", key).or().like("attr_name", key);
        }
        IPage<AttrEntity> page;
        if (catelogId == 0) {
            page = this.page(
                    new Query<AttrEntity>().getPage(params),
                    entityQuery);
        } else {
            entityQuery.eq("catelog_id", catelogId);
            page = this.page(
                    new Query<AttrEntity>().getPage(params),
                    entityQuery
            );
        }
        PageUtils pageUtils = new PageUtils(page);
        List<AttrRespVo> attrRespVos = page.getRecords().stream().map(attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            // 分组信息
            if (ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() == attrEntity.getAttrType()) {
                Long attrId = attrEntity.getAttrId();
                AttrAttrgroupRelationEntity attrgroupRelation = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
                if (attrgroupRelation != null) {
                    Long attrGroupId = attrgroupRelation.getAttrGroupId();
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }

            // 分类信息
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }

            return attrRespVo;
        }).collect(Collectors.toList());
        // 替换成新的结果集
        pageUtils.setList(attrRespVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity, attrRespVo);

        // 分组信息
        if (ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() == attrEntity.getAttrType()) {

            AttrAttrgroupRelationEntity attrgroupRelation = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
            if (attrgroupRelation != null) {
                Long attrGroupId = attrgroupRelation.getAttrGroupId();
                attrRespVo.setAttrGroupId(attrGroupId);
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                if (attrGroupEntity != null) {
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }

        // 分类信息
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPathByCatId(catelogId);
        attrRespVo.setCatelogPath(catelogPath);

        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null) {
            attrRespVo.setCatelogName(categoryEntity.getName());
        }

        return attrRespVo;
    }


    @Transactional
    @Override
    public void updateAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);
        this.updateById(attrEntity);

        // 更新分组信息
        if (ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() == attrEntity.getAttrType()) {
            AttrAttrgroupRelationEntity attrgroupRelation = new AttrAttrgroupRelationEntity();
            attrgroupRelation.setAttrId(attrEntity.getAttrId());
            attrgroupRelation.setAttrGroupId(attrVo.getAttrGroupId());
            Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if (count > 0) {
                attrAttrgroupRelationDao.update(attrgroupRelation, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            } else {
                attrAttrgroupRelationDao.insert(attrgroupRelation);
            }
        }
    }

}
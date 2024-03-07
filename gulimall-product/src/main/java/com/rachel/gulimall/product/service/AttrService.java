package com.rachel.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rachel.common.utils.PageUtils;
import com.rachel.gulimall.product.entity.AttrEntity;
import com.rachel.gulimall.product.vo.AttrRespVo;
import com.rachel.gulimall.product.vo.AttrVo;

import java.util.Map;

/**
 * 
 *
 * @author rachelk
 * @email 413843464@qq.com
 * @date 2024-03-01 14:04:28
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attrVo);
}


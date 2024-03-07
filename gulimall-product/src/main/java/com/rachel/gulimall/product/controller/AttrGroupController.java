package com.rachel.gulimall.product.controller;

import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.R;
import com.rachel.gulimall.product.entity.AttrEntity;
import com.rachel.gulimall.product.entity.AttrGroupEntity;
import com.rachel.gulimall.product.service.AttrAttrgroupRelationService;
import com.rachel.gulimall.product.service.AttrGroupService;
import com.rachel.gulimall.product.service.AttrService;
import com.rachel.gulimall.product.service.CategoryService;
import com.rachel.gulimall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author rachelk
 * @email 413843464@qq.com
 * @date 2024-03-01 14:43:24
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;


    /**
     * 获取某个分组下的属性列表
     */
    @RequestMapping("/{groupId}/attr/relation")
    public R relationList(@PathVariable Long groupId) {
        List<AttrEntity> attrEntities = attrService.queryAttrGropuRealationByGroupId(groupId);

        return R.ok().put("data", attrEntities);
    }

    /**
     * 获取某个分组下的未关联的属性列表
     */
    @RequestMapping("/{groupId}/noattr/relation")
    public R noRelationList(@RequestParam Map<String, Object> params, @PathVariable Long groupId) {
        PageUtils page = attrService.queryNoAttrGropuRealationByGroupId(params, groupId);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable Long catelogId) {
        PageUtils page = attrGroupService.queryPage(params, catelogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        Long catelogId = attrGroup.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPathByCatId(catelogId);
        attrGroup.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/attr/relation/delete")
    public R delete(@RequestBody AttrGroupRelationVo[] relationVos) {
        attrService.removeAttrRelation(relationVos);

        return R.ok();
    }


    /**
     * 批量添加属性分组关联
     */
    @PostMapping("/attr/relation")
    public R delete(@RequestBody List<AttrGroupRelationVo> relationVos) {
        attrAttrgroupRelationService.addAttrRelation(relationVos);
        return R.ok();
    }


}

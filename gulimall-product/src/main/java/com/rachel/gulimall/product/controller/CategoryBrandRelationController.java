package com.rachel.gulimall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.R;
import com.rachel.gulimall.product.entity.BrandEntity;
import com.rachel.gulimall.product.entity.CategoryBrandRelationEntity;
import com.rachel.gulimall.product.service.CategoryBrandRelationService;
import com.rachel.gulimall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Ʒ
 *
 * @author rachelk
 * @email 413843464@qq.com
 * @date 2024-03-01 14:43:24
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    /**
     * 获取分类下的品牌列表
     */
    @GetMapping("/brands/list")
    public R brandsList(@RequestParam(value = "catId", required = true) Long catId) {
        List<BrandEntity> brandEntities = categoryBrandRelationService.getBrandsByCatId(catId);
        List<BrandVo> brandVos = brandEntities.stream().map(obj -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandName(obj.getName());
            brandVo.setBrandId(obj.getBrandId());
            return brandVo;
        }).collect(Collectors.toList());
        return R.ok().put("data", brandVos);
    }


    /**
     * 列表
     */
    @GetMapping("/catelog/list")
    public R list(@RequestParam("brandId") Long brandId) {
        QueryWrapper<CategoryBrandRelationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("brand_id", brandId);
        List<CategoryBrandRelationEntity> categoryBrandRelationEntityList = categoryBrandRelationService.list(queryWrapper);
        return R.ok().put("data", categoryBrandRelationEntityList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

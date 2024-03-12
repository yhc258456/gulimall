package com.rachel.gulimall.product.app;

import com.rachel.common.utils.PageUtils;
import com.rachel.common.utils.R;
import com.rachel.gulimall.product.entity.ProductAttrValueEntity;
import com.rachel.gulimall.product.service.AttrService;
import com.rachel.gulimall.product.service.ProductAttrValueService;
import com.rachel.gulimall.product.vo.AttrRespVo;
import com.rachel.gulimall.product.vo.AttrVo;
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
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;


    /**
     * 列表
     */
    @RequestMapping("/{type}/list/{catelogId}")
    public R baseList(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId, @PathVariable("type") String attrType) {
        PageUtils page = attrService.queryBaseAttrPage(params, catelogId, attrType);

        return R.ok().put("page", page);
    }


    /**
     * 获取某个spu下的属性
     */
    @RequestMapping("/base/listforspu/{spuId}")
    public R list(@PathVariable("spuId") String spuId) {
        List<ProductAttrValueEntity> attrEntities = productAttrValueService.queryAttrBySpuId(spuId);

        return R.ok().put("data", attrEntities);
    }
    // http://localhost:88/api/product/attr/update/6

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId) {

        AttrRespVo attr = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr) {
        attrService.saveAttr(attr);
        return R.ok();
    }


    /**
     * 修改商品属性
     */
    @RequestMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                           @RequestBody List<ProductAttrValueEntity> attrValueEntityList) {
        productAttrValueService.updateSpuAttr(spuId, attrValueEntityList);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attrVo) {
        attrService.updateAttr(attrVo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}

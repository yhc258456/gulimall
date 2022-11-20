package com.rachel.gulimall.modules.product.controller;

import com.rachel.gulimall.common.constant.Constant;
import com.rachel.gulimall.common.page.PageData;
import com.rachel.gulimall.common.utils.ExcelUtils;
import com.rachel.gulimall.common.utils.Result;
import com.rachel.gulimall.common.validator.AssertUtils;
import com.rachel.gulimall.common.validator.ValidatorUtils;
import com.rachel.gulimall.common.validator.group.AddGroup;
import com.rachel.gulimall.common.validator.group.DefaultGroup;
import com.rachel.gulimall.common.validator.group.UpdateGroup;
import com.rachel.gulimall.modules.product.dto.ProductAttrValueDTO;
import com.rachel.gulimall.modules.product.excel.ProductAttrValueExcel;
import com.rachel.gulimall.modules.product.service.ProductAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * spuÊôÐÔÖµ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@RestController
@RequestMapping("product/productattrvalue")
public class ProductAttrValueController {
    @Autowired
    private ProductAttrValueService productAttrValueService;

    @GetMapping("page")
    public Result<PageData<ProductAttrValueDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<ProductAttrValueDTO> page = productAttrValueService.page(params);

        return new Result<PageData<ProductAttrValueDTO>>().ok(page);
    }

    public Result<ProductAttrValueDTO> get(@PathVariable("id") Long id){
        ProductAttrValueDTO data = productAttrValueService.get(id);

        return new Result<ProductAttrValueDTO>().ok(data);
    }

    @PostMapping
    public Result save(@RequestBody ProductAttrValueDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        productAttrValueService.save(dto);

        return new Result();
    }

    @PutMapping
    public Result update(@RequestBody ProductAttrValueDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        productAttrValueService.update(dto);

        return new Result();
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        productAttrValueService.delete(ids);

        return new Result();
    }


}
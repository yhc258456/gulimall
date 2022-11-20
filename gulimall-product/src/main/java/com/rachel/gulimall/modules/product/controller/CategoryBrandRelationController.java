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
import com.rachel.gulimall.modules.product.dto.CategoryBrandRelationDTO;
import com.rachel.gulimall.modules.product.excel.CategoryBrandRelationExcel;
import com.rachel.gulimall.modules.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * Æ·ÅÆ·ÖÀà¹ØÁª
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @GetMapping("page")
    public Result<PageData<CategoryBrandRelationDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<CategoryBrandRelationDTO> page = categoryBrandRelationService.page(params);

        return new Result<PageData<CategoryBrandRelationDTO>>().ok(page);
    }

    public Result<CategoryBrandRelationDTO> get(@PathVariable("id") Long id){
        CategoryBrandRelationDTO data = categoryBrandRelationService.get(id);

        return new Result<CategoryBrandRelationDTO>().ok(data);
    }

    @PostMapping
    public Result save(@RequestBody CategoryBrandRelationDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        categoryBrandRelationService.save(dto);

        return new Result();
    }

    @PutMapping
    public Result update(@RequestBody CategoryBrandRelationDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        categoryBrandRelationService.update(dto);

        return new Result();
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        categoryBrandRelationService.delete(ids);

        return new Result();
    }


}
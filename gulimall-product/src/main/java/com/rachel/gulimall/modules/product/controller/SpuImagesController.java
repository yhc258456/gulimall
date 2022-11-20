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
import com.rachel.gulimall.modules.product.dto.SpuImagesDTO;
import com.rachel.gulimall.modules.product.excel.SpuImagesExcel;
import com.rachel.gulimall.modules.product.service.SpuImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * spuÍ¼Æ¬
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@RestController
@RequestMapping("product/spuimages")
public class SpuImagesController {
    @Autowired
    private SpuImagesService spuImagesService;

    @GetMapping("page")
    public Result<PageData<SpuImagesDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SpuImagesDTO> page = spuImagesService.page(params);

        return new Result<PageData<SpuImagesDTO>>().ok(page);
    }

    public Result<SpuImagesDTO> get(@PathVariable("id") Long id){
        SpuImagesDTO data = spuImagesService.get(id);

        return new Result<SpuImagesDTO>().ok(data);
    }

    @PostMapping
    public Result save(@RequestBody SpuImagesDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        spuImagesService.save(dto);

        return new Result();
    }

    @PutMapping
    public Result update(@RequestBody SpuImagesDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        spuImagesService.update(dto);

        return new Result();
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        spuImagesService.delete(ids);

        return new Result();
    }


}
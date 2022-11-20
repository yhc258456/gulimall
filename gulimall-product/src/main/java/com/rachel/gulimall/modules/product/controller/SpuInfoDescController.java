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
import com.rachel.gulimall.modules.product.dto.SpuInfoDescDTO;
import com.rachel.gulimall.modules.product.excel.SpuInfoDescExcel;
import com.rachel.gulimall.modules.product.service.SpuInfoDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * spuÐÅÏ¢½éÉÜ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@RestController
@RequestMapping("product/spuinfodesc")
public class SpuInfoDescController {
    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @GetMapping("page")
    public Result<PageData<SpuInfoDescDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SpuInfoDescDTO> page = spuInfoDescService.page(params);

        return new Result<PageData<SpuInfoDescDTO>>().ok(page);
    }

    public Result<SpuInfoDescDTO> get(@PathVariable("id") Long id){
        SpuInfoDescDTO data = spuInfoDescService.get(id);

        return new Result<SpuInfoDescDTO>().ok(data);
    }

    @PostMapping
    public Result save(@RequestBody SpuInfoDescDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        spuInfoDescService.save(dto);

        return new Result();
    }

    @PutMapping
    public Result update(@RequestBody SpuInfoDescDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        spuInfoDescService.update(dto);

        return new Result();
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        spuInfoDescService.delete(ids);

        return new Result();
    }


}
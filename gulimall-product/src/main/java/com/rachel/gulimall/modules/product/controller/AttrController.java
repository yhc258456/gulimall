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
import com.rachel.gulimall.modules.product.dto.AttrDTO;
import com.rachel.gulimall.modules.product.excel.AttrExcel;
import com.rachel.gulimall.modules.product.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * ÉÌÆ·ÊôÐÔ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @GetMapping("page")
    public Result<PageData<AttrDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<AttrDTO> page = attrService.page(params);

        return new Result<PageData<AttrDTO>>().ok(page);
    }

    public Result<AttrDTO> get(@PathVariable("id") Long id){
        AttrDTO data = attrService.get(id);

        return new Result<AttrDTO>().ok(data);
    }

    @PostMapping
    public Result save(@RequestBody AttrDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        attrService.save(dto);

        return new Result();
    }

    @PutMapping
    public Result update(@RequestBody AttrDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        attrService.update(dto);

        return new Result();
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        attrService.delete(ids);

        return new Result();
    }


}
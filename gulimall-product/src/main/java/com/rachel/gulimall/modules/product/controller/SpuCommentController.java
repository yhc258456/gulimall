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
import com.rachel.gulimall.modules.product.dto.SpuCommentDTO;
import com.rachel.gulimall.modules.product.excel.SpuCommentExcel;
import com.rachel.gulimall.modules.product.service.SpuCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * ÉÌÆ·ÆÀ¼Û
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@RestController
@RequestMapping("product/spucomment")
public class SpuCommentController {
    @Autowired
    private SpuCommentService spuCommentService;

    @GetMapping("page")
    public Result<PageData<SpuCommentDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SpuCommentDTO> page = spuCommentService.page(params);

        return new Result<PageData<SpuCommentDTO>>().ok(page);
    }

    public Result<SpuCommentDTO> get(@PathVariable("id") Long id){
        SpuCommentDTO data = spuCommentService.get(id);

        return new Result<SpuCommentDTO>().ok(data);
    }

    @PostMapping
    public Result save(@RequestBody SpuCommentDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        spuCommentService.save(dto);

        return new Result();
    }

    @PutMapping
    public Result update(@RequestBody SpuCommentDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        spuCommentService.update(dto);

        return new Result();
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        spuCommentService.delete(ids);

        return new Result();
    }


}
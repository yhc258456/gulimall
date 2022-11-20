package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * Æ·ÅÆ·ÖÀà¹ØÁª
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class CategoryBrandRelationExcel {
    @Excel(name = "")
    private Long id;
    @Excel(name = "Æ·ÅÆid")
    private Long brandId;
    @Excel(name = "·ÖÀàid")
    private Long catelogId;
    @Excel(name = "")
    private String brandName;
    @Excel(name = "")
    private String catelogName;

}
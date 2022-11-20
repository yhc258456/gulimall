package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * spuÐÅÏ¢½éÉÜ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class SpuInfoDescExcel {
    @Excel(name = "ÉÌÆ·id")
    private Long spuId;
    @Excel(name = "ÉÌÆ·½éÉÜ")
    private String decript;

}
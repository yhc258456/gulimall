package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * ÉÌÆ·Èý¼¶·ÖÀà
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class CategoryExcel {
    @Excel(name = "·ÖÀàid")
    private Long catId;
    @Excel(name = "·ÖÀàÃû³Æ")
    private String name;
    @Excel(name = "¸¸·ÖÀàid")
    private Long parentCid;
    @Excel(name = "²ã¼¶")
    private Integer catLevel;
    @Excel(name = "ÊÇ·ñÏÔÊ¾[0-²»ÏÔÊ¾£¬1ÏÔÊ¾]")
    private Integer showStatus;
    @Excel(name = "ÅÅÐò")
    private Integer sort;
    @Excel(name = "Í¼±êµØÖ·")
    private String icon;
    @Excel(name = "¼ÆÁ¿µ¥Î»")
    private String productUnit;
    @Excel(name = "ÉÌÆ·ÊýÁ¿")
    private Integer productCount;

}
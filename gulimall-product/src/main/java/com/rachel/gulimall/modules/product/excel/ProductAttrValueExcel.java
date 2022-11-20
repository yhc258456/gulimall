package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * spuÊôÐÔÖµ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class ProductAttrValueExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "ÉÌÆ·id")
    private Long spuId;
    @Excel(name = "ÊôÐÔid")
    private Long attrId;
    @Excel(name = "ÊôÐÔÃû")
    private String attrName;
    @Excel(name = "ÊôÐÔÖµ")
    private String attrValue;
    @Excel(name = "Ë³Ðò")
    private Integer attrSort;
    @Excel(name = "¿ìËÙÕ¹Ê¾¡¾ÊÇ·ñÕ¹Ê¾ÔÚ½éÉÜÉÏ£»0-·ñ 1-ÊÇ¡¿")
    private Integer quickShow;

}
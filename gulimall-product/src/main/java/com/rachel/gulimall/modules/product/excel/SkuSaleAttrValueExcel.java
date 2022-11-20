package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * skuÏúÊÛÊôÐÔ&Öµ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class SkuSaleAttrValueExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "sku_id")
    private Long skuId;
    @Excel(name = "attr_id")
    private Long attrId;
    @Excel(name = "ÏúÊÛÊôÐÔÃû")
    private String attrName;
    @Excel(name = "ÏúÊÛÊôÐÔÖµ")
    private String attrValue;
    @Excel(name = "Ë³Ðò")
    private Integer attrSort;

}
package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * skuÐÅÏ¢
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class SkuInfoExcel {
    @Excel(name = "skuId")
    private Long skuId;
    @Excel(name = "spuId")
    private Long spuId;
    @Excel(name = "skuÃû³Æ")
    private String skuName;
    @Excel(name = "sku½éÉÜÃèÊö")
    private String skuDesc;
    @Excel(name = "ËùÊô·ÖÀàid")
    private Long catalogId;
    @Excel(name = "Æ·ÅÆid")
    private Long brandId;
    @Excel(name = "Ä¬ÈÏÍ¼Æ¬")
    private String skuDefaultImg;
    @Excel(name = "±êÌâ")
    private String skuTitle;
    @Excel(name = "¸±±êÌâ")
    private String skuSubtitle;
    @Excel(name = "¼Û¸ñ")
    private BigDecimal price;
    @Excel(name = "ÏúÁ¿")
    private Long saleCount;

}
package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * skuÍ¼Æ¬
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class SkuImagesExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "sku_id")
    private Long skuId;
    @Excel(name = "Í¼Æ¬µØÖ·")
    private String imgUrl;
    @Excel(name = "ÅÅÐò")
    private Integer imgSort;
    @Excel(name = "Ä¬ÈÏÍ¼[0 - ²»ÊÇÄ¬ÈÏÍ¼£¬1 - ÊÇÄ¬ÈÏÍ¼]")
    private Integer defaultImg;

}
package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * skuÏúÊÛÊôÐÔ&Öµ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
@TableName("pms_sku_sale_attr_value")
public class SkuSaleAttrValueEntity {

    /**
     * id
     */
	private Long id;
    /**
     * sku_id
     */
	private Long skuId;
    /**
     * attr_id
     */
	private Long attrId;
    /**
     * ÏúÊÛÊôÐÔÃû
     */
	private String attrName;
    /**
     * ÏúÊÛÊôÐÔÖµ
     */
	private String attrValue;
    /**
     * Ë³Ðò
     */
	private Integer attrSort;
}
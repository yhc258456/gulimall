package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * spuÊôÐÔÖµ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
@TableName("pms_product_attr_value")
public class ProductAttrValueEntity {

    /**
     * id
     */
	private Long id;
    /**
     * ÉÌÆ·id
     */
	private Long spuId;
    /**
     * ÊôÐÔid
     */
	private Long attrId;
    /**
     * ÊôÐÔÃû
     */
	private String attrName;
    /**
     * ÊôÐÔÖµ
     */
	private String attrValue;
    /**
     * Ë³Ðò
     */
	private Integer attrSort;
    /**
     * ¿ìËÙÕ¹Ê¾¡¾ÊÇ·ñÕ¹Ê¾ÔÚ½éÉÜÉÏ£»0-·ñ 1-ÊÇ¡¿
     */
	private Integer quickShow;
}
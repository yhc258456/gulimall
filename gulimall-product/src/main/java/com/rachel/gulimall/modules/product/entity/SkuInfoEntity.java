package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("pms_sku_info")
public class SkuInfoEntity {

    /**
     * skuId
     */
	private Long skuId;
    /**
     * spuId
     */
	private Long spuId;
    /**
     * skuÃû³Æ
     */
	private String skuName;
    /**
     * sku½éÉÜÃèÊö
     */
	private String skuDesc;
    /**
     * ËùÊô·ÖÀàid
     */
	private Long catalogId;
    /**
     * Æ·ÅÆid
     */
	private Long brandId;
    /**
     * Ä¬ÈÏÍ¼Æ¬
     */
	private String skuDefaultImg;
    /**
     * ±êÌâ
     */
	private String skuTitle;
    /**
     * ¸±±êÌâ
     */
	private String skuSubtitle;
    /**
     * ¼Û¸ñ
     */
	private BigDecimal price;
    /**
     * ÏúÁ¿
     */
	private Long saleCount;
}
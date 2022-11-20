package com.rachel.gulimall.modules.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import java.math.BigDecimal;

/**
 * skuÐÅÏ¢
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class SkuInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long skuId;

	private Long spuId;

	private String skuName;

	private String skuDesc;

	private Long catalogId;

	private Long brandId;

	private String skuDefaultImg;

	private String skuTitle;

	private String skuSubtitle;

	private BigDecimal price;

	private Long saleCount;


}
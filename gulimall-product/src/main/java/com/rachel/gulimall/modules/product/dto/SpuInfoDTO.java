package com.rachel.gulimall.modules.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import java.math.BigDecimal;

/**
 * spuÐÅÏ¢
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class SpuInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long id;

	private String spuName;

	private String spuDescription;

	private Long catalogId;

	private Long brandId;

	private BigDecimal weight;

	private Integer publishStatus;

	private Date createTime;

	private Date updateTime;


}
package com.rachel.gulimall.modules.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * skuÍ¼Æ¬
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class SkuImagesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long id;

	private Long skuId;

	private String imgUrl;

	private Integer imgSort;

	private Integer defaultImg;


}
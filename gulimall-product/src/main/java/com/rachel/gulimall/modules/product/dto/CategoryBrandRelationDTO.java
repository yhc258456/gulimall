package com.rachel.gulimall.modules.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * Æ·ÅÆ·ÖÀà¹ØÁª
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class CategoryBrandRelationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long id;

	private Long brandId;

	private Long catelogId;

	private String brandName;

	private String catelogName;


}
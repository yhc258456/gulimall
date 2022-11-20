package com.rachel.gulimall.modules.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * ÉÌÆ·Èý¼¶·ÖÀà
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long catId;

	private String name;

	private Long parentCid;

	private Integer catLevel;

	private Integer showStatus;

	private Integer sort;

	private String icon;

	private String productUnit;

	private Integer productCount;


}
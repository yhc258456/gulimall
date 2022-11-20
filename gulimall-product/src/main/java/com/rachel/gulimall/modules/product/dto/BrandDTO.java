package com.rachel.gulimall.modules.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * Æ·ÅÆ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class BrandDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long brandId;

	private String name;

	private String logo;

	private String descript;

	private Integer showStatus;

	private String firstLetter;

	private Integer sort;


}
package com.rachel.gulimall.modules.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * ÉÌÆ·ÊôÐÔ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class AttrDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long attrId;

	private String attrName;

	private Integer searchType;

	private String icon;

	private String valueSelect;

	private Integer attrType;

	private Long enable;

	private Long catelogId;

	private Integer showDesc;


}
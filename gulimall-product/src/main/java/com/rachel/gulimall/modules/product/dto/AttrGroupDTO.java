package com.rachel.gulimall.modules.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * ÊôÐÔ·Ö×é
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class AttrGroupDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long attrGroupId;

	private String attrGroupName;

	private Integer sort;

	private String descript;

	private String icon;

	private Long catelogId;


}
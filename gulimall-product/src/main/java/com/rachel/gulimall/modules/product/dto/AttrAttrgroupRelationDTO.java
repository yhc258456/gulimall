package com.rachel.gulimall.modules.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * ÊôÐÔ&ÊôÐÔ·Ö×é¹ØÁª
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class AttrAttrgroupRelationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long id;

	private Long attrId;

	private Long attrGroupId;

	private Integer attrSort;


}
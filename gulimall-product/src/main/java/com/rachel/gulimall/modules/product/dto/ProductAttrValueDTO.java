package com.rachel.gulimall.modules.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * spuÊôÐÔÖµ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class ProductAttrValueDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long id;

	private Long spuId;

	private Long attrId;

	private String attrName;

	private String attrValue;

	private Integer attrSort;

	private Integer quickShow;


}
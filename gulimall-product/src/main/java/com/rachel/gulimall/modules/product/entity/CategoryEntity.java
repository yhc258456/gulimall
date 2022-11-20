package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * ÉÌÆ·Èý¼¶·ÖÀà
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
@TableName("pms_category")
public class CategoryEntity {

    /**
     * ·ÖÀàid
     */
	private Long catId;
    /**
     * ·ÖÀàÃû³Æ
     */
	private String name;
    /**
     * ¸¸·ÖÀàid
     */
	private Long parentCid;
    /**
     * ²ã¼¶
     */
	private Integer catLevel;
    /**
     * ÊÇ·ñÏÔÊ¾[0-²»ÏÔÊ¾£¬1ÏÔÊ¾]
     */
	private Integer showStatus;
    /**
     * ÅÅÐò
     */
	private Integer sort;
    /**
     * Í¼±êµØÖ·
     */
	private String icon;
    /**
     * ¼ÆÁ¿µ¥Î»
     */
	private String productUnit;
    /**
     * ÉÌÆ·ÊýÁ¿
     */
	private Integer productCount;
}
package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Æ·ÅÆ·ÖÀà¹ØÁª
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
@TableName("pms_category_brand_relation")
public class CategoryBrandRelationEntity {

    /**
     * 
     */
	private Long id;
    /**
     * Æ·ÅÆid
     */
	private Long brandId;
    /**
     * ·ÖÀàid
     */
	private Long catelogId;
    /**
     * 
     */
	private String brandName;
    /**
     * 
     */
	private String catelogName;
}
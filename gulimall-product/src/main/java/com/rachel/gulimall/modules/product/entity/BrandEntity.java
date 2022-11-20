package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Æ·ÅÆ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
@TableName("pms_brand")
public class BrandEntity {

    /**
     * Æ·ÅÆid
     */
	private Long brandId;
    /**
     * Æ·ÅÆÃû
     */
	private String name;
    /**
     * Æ·ÅÆlogoµØÖ·
     */
	private String logo;
    /**
     * ½éÉÜ
     */
	private String descript;
    /**
     * ÏÔÊ¾×´Ì¬[0-²»ÏÔÊ¾£»1-ÏÔÊ¾]
     */
	private Integer showStatus;
    /**
     * ¼ìË÷Ê××ÖÄ¸
     */
	private String firstLetter;
    /**
     * ÅÅÐò
     */
	private Integer sort;
}
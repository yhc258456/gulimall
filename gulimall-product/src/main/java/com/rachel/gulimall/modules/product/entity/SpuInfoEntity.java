package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * spuÐÅÏ¢
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
@TableName("pms_spu_info")
public class SpuInfoEntity {

    /**
     * ÉÌÆ·id
     */
	private Long id;
    /**
     * ÉÌÆ·Ãû³Æ
     */
	private String spuName;
    /**
     * ÉÌÆ·ÃèÊö
     */
	private String spuDescription;
    /**
     * ËùÊô·ÖÀàid
     */
	private Long catalogId;
    /**
     * Æ·ÅÆid
     */
	private Long brandId;
    /**
     * 
     */
	private BigDecimal weight;
    /**
     * ÉÏ¼Ü×´Ì¬[0 - ÏÂ¼Ü£¬1 - ÉÏ¼Ü]
     */
	private Integer publishStatus;
    /**
     * 
     */
	private Date createTime;
    /**
     * 
     */
	private Date updateTime;
}
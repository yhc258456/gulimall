package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * spuÐÅÏ¢½éÉÜ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
@TableName("pms_spu_info_desc")
public class SpuInfoDescEntity {

    /**
     * ÉÌÆ·id
     */
	private Long spuId;
    /**
     * ÉÌÆ·½éÉÜ
     */
	private String decript;
}
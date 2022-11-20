package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * spuÍ¼Æ¬
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
@TableName("pms_spu_images")
public class SpuImagesEntity {

    /**
     * id
     */
	private Long id;
    /**
     * spu_id
     */
	private Long spuId;
    /**
     * Í¼Æ¬Ãû
     */
	private String imgName;
    /**
     * Í¼Æ¬µØÖ·
     */
	private String imgUrl;
    /**
     * Ë³Ðò
     */
	private Integer imgSort;
    /**
     * ÊÇ·ñÄ¬ÈÏÍ¼
     */
	private Integer defaultImg;
}
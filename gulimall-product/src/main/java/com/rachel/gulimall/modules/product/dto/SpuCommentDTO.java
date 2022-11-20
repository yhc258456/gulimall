package com.rachel.gulimall.modules.product.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * ÉÌÆ·ÆÀ¼Û
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class SpuCommentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long id;

	private Long skuId;

	private Long spuId;

	private String spuName;

	private String memberNickName;

	private Integer star;

	private String memberIp;

	private Date createTime;

	private Integer showStatus;

	private String spuAttributes;

	private Integer likesCount;

	private Integer replyCount;

	private String resources;

	private String content;

	private String memberIcon;

	private Integer commentType;


}
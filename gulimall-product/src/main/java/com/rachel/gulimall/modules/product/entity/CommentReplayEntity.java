package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * ÉÌÆ·ÆÀ¼Û»Ø¸´¹ØÏµ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
@TableName("pms_comment_replay")
public class CommentReplayEntity {

    /**
     * id
     */
	private Long id;
    /**
     * ÆÀÂÛid
     */
	private Long commentId;
    /**
     * »Ø¸´id
     */
	private Long replyId;
}
package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * ÉÌÆ·ÆÀ¼Û»Ø¸´¹ØÏµ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class CommentReplayExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "ÆÀÂÛid")
    private Long commentId;
    @Excel(name = "»Ø¸´id")
    private Long replyId;

}
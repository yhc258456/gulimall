package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * ÉÌÆ·ÆÀ¼Û
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class SpuCommentExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "sku_id")
    private Long skuId;
    @Excel(name = "spu_id")
    private Long spuId;
    @Excel(name = "ÉÌÆ·Ãû×Ö")
    private String spuName;
    @Excel(name = "»áÔ±êÇ³Æ")
    private String memberNickName;
    @Excel(name = "ÐÇ¼¶")
    private Integer star;
    @Excel(name = "»áÔ±ip")
    private String memberIp;
    @Excel(name = "´´½¨Ê±¼ä")
    private Date createTime;
    @Excel(name = "ÏÔÊ¾×´Ì¬[0-²»ÏÔÊ¾£¬1-ÏÔÊ¾]")
    private Integer showStatus;
    @Excel(name = "¹ºÂòÊ±ÊôÐÔ×éºÏ")
    private String spuAttributes;
    @Excel(name = "µãÔÞÊý")
    private Integer likesCount;
    @Excel(name = "»Ø¸´Êý")
    private Integer replyCount;
    @Excel(name = "ÆÀÂÛÍ¼Æ¬/ÊÓÆµ[jsonÊý¾Ý£»[{type:ÎÄ¼þÀàÐÍ,url:×ÊÔ´Â·¾¶}]]")
    private String resources;
    @Excel(name = "ÄÚÈÝ")
    private String content;
    @Excel(name = "ÓÃ»§Í·Ïñ")
    private String memberIcon;
    @Excel(name = "ÆÀÂÛÀàÐÍ[0 - ¶ÔÉÌÆ·µÄÖ±½ÓÆÀÂÛ£¬1 - ¶ÔÆÀÂÛµÄ»Ø¸´]")
    private Integer commentType;

}
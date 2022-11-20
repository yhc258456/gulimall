package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
public class SpuInfoExcel {
    @Excel(name = "ÉÌÆ·id")
    private Long id;
    @Excel(name = "ÉÌÆ·Ãû³Æ")
    private String spuName;
    @Excel(name = "ÉÌÆ·ÃèÊö")
    private String spuDescription;
    @Excel(name = "ËùÊô·ÖÀàid")
    private Long catalogId;
    @Excel(name = "Æ·ÅÆid")
    private Long brandId;
    @Excel(name = "")
    private BigDecimal weight;
    @Excel(name = "ÉÏ¼Ü×´Ì¬[0 - ÏÂ¼Ü£¬1 - ÉÏ¼Ü]")
    private Integer publishStatus;
    @Excel(name = "")
    private Date createTime;
    @Excel(name = "")
    private Date updateTime;

}
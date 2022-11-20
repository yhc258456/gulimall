package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * Æ·ÅÆ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class BrandExcel {
    @Excel(name = "Æ·ÅÆid")
    private Long brandId;
    @Excel(name = "Æ·ÅÆÃû")
    private String name;
    @Excel(name = "Æ·ÅÆlogoµØÖ·")
    private String logo;
    @Excel(name = "½éÉÜ")
    private String descript;
    @Excel(name = "ÏÔÊ¾×´Ì¬[0-²»ÏÔÊ¾£»1-ÏÔÊ¾]")
    private Integer showStatus;
    @Excel(name = "¼ìË÷Ê××ÖÄ¸")
    private String firstLetter;
    @Excel(name = "ÅÅÐò")
    private Integer sort;

}
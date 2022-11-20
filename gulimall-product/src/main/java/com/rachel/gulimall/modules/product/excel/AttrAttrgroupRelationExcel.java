package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * ÊôÐÔ&ÊôÐÔ·Ö×é¹ØÁª
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class AttrAttrgroupRelationExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "ÊôÐÔid")
    private Long attrId;
    @Excel(name = "ÊôÐÔ·Ö×éid")
    private Long attrGroupId;
    @Excel(name = "ÊôÐÔ×éÄÚÅÅÐò")
    private Integer attrSort;

}
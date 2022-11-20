package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * ÊôÐÔ·Ö×é
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class AttrGroupExcel {
    @Excel(name = "·Ö×éid")
    private Long attrGroupId;
    @Excel(name = "×éÃû")
    private String attrGroupName;
    @Excel(name = "ÅÅÐò")
    private Integer sort;
    @Excel(name = "ÃèÊö")
    private String descript;
    @Excel(name = "×éÍ¼±ê")
    private String icon;
    @Excel(name = "ËùÊô·ÖÀàid")
    private Long catelogId;

}
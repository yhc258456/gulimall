package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * ÉÌÆ·ÊôÐÔ
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class AttrExcel {
    @Excel(name = "ÊôÐÔid")
    private Long attrId;
    @Excel(name = "ÊôÐÔÃû")
    private String attrName;
    @Excel(name = "ÊÇ·ñÐèÒª¼ìË÷[0-²»ÐèÒª£¬1-ÐèÒª]")
    private Integer searchType;
    @Excel(name = "ÊôÐÔÍ¼±ê")
    private String icon;
    @Excel(name = "¿ÉÑ¡ÖµÁÐ±í[ÓÃ¶ººÅ·Ö¸ô]")
    private String valueSelect;
    @Excel(name = "ÊôÐÔÀàÐÍ[0-ÏúÊÛÊôÐÔ£¬1-»ù±¾ÊôÐÔ£¬2-¼ÈÊÇÏúÊÛÊôÐÔÓÖÊÇ»ù±¾ÊôÐÔ]")
    private Integer attrType;
    @Excel(name = "ÆôÓÃ×´Ì¬[0 - ½ûÓÃ£¬1 - ÆôÓÃ]")
    private Long enable;
    @Excel(name = "ËùÊô·ÖÀà")
    private Long catelogId;
    @Excel(name = "¿ìËÙÕ¹Ê¾¡¾ÊÇ·ñÕ¹Ê¾ÔÚ½éÉÜÉÏ£»0-·ñ 1-ÊÇ¡¿£¬ÔÚskuÖÐÈÔÈ»¿ÉÒÔµ÷Õû")
    private Integer showDesc;

}
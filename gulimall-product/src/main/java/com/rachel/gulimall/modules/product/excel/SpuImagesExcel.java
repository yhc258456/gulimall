package com.rachel.gulimall.modules.product.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * spuÍ¼Æ¬
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
public class SpuImagesExcel {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "spu_id")
    private Long spuId;
    @Excel(name = "Í¼Æ¬Ãû")
    private String imgName;
    @Excel(name = "Í¼Æ¬µØÖ·")
    private String imgUrl;
    @Excel(name = "Ë³Ðò")
    private Integer imgSort;
    @Excel(name = "ÊÇ·ñÄ¬ÈÏÍ¼")
    private Integer defaultImg;

}
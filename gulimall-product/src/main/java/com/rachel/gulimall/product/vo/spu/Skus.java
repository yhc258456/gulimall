/**
  * Copyright 2024 bejson.com 
  */
package com.rachel.gulimall.product.vo.spu;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Auto-generated: 2024-03-08 16:32:42
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Skus {

    private List<Attr> attr;
    private Long countStatus;
    private List<String> descar;
    private BigDecimal discount;
    private BigDecimal fullCount;
    private BigDecimal fullPrice;
    private List<Image> images;
    private List<MemberPrice> memberPrice;
    private BigDecimal price;
    private BigDecimal priceStatus;
    private BigDecimal reducePrice;
    private String skuName;
    private String skuSubtitle;
    private String skuTitle;

}
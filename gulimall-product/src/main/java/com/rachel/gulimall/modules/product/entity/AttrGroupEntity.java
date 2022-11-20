package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * ÊôÐÔ·Ö×é
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
@TableName("pms_attr_group")
public class AttrGroupEntity {

    /**
     * ·Ö×éid
     */
	private Long attrGroupId;
    /**
     * ×éÃû
     */
	private String attrGroupName;
    /**
     * ÅÅÐò
     */
	private Integer sort;
    /**
     * ÃèÊö
     */
	private String descript;
    /**
     * ×éÍ¼±ê
     */
	private String icon;
    /**
     * ËùÊô·ÖÀàid
     */
	private Long catelogId;
}
package com.rachel.gulimall.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * ÊôÐÔ&ÊôÐÔ·Ö×é¹ØÁª
 *
 * @author Rachel huacheng.yin.me@gmail.com
 * @since 1.0.0 2022-11-20
 */
@Data
@TableName("pms_attr_attrgroup_relation")
public class AttrAttrgroupRelationEntity {

    /**
     * id
     */
    @TableField
	private Long id;
    /**
     * ÊôÐÔid
     */
	private Long attrId;
    /**
     * ÊôÐÔ·Ö×éid
     */
	private Long attrGroupId;
    /**
     * ÊôÐÔ×éÄÚÅÅÐò
     */
	private Integer attrSort;
}
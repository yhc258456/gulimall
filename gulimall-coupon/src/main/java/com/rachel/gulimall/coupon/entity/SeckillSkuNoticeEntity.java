package com.rachel.gulimall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author rachel
 * @email 413843464@qq.com
 * @date 2024-03-01 15:21:50
 */
@Data
@TableName("sms_seckill_sku_notice")
public class SeckillSkuNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * member_id
	 */
	private Long memberId;
	/**
	 * sku_id
	 */
	private Long skuId;
	/**
	 * 
	 */
	private Long sessionId;
	/**
	 * 
	 */
	private Date subcribeTime;
	/**
	 * 
	 */
	private Date sendTime;
	/**
	 * ֪ͨ
	 */
	private Integer noticeType;

}

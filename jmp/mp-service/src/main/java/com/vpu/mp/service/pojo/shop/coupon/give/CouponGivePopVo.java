package com.vpu.mp.service.pojo.shop.coupon.give;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 优惠券弹窗
 * @author liangchen
 * @date 2019年8月1日
 */
@Data
public class CouponGivePopVo {
	/** 优惠券id */
	private Integer id;
	/** 优惠券名称 */
	private String actName;
	/** 面额 */
	private BigDecimal denomination;
	/** 使用限制 0：无限制 1：有限制 */
	private Integer useConsumeRestrict;
	/** 满多少可用 */
	private BigDecimal leastConsume;
	/** 剩余数量 */
	private Integer surplus;
	/** 优惠券有效期类型标记 */
	private Integer validityType;
	/** 优惠券优惠券有效天数 */
	private Integer validity;
	/** 优惠券有效小时数 */
	private Integer validityHour;
	/** 优惠券有效分钟数 */
	private Integer validityMinute;
	/** 优惠券有效期开始时间 */
	private Timestamp startTime;
	/** 优惠券有效期开始时间 */
	private Timestamp endTime;
	
}

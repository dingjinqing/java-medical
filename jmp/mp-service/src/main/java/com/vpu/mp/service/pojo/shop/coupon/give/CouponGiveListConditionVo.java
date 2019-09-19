package com.vpu.mp.service.pojo.shop.coupon.give;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 定向发放优惠券
 * @author liangchen
 * @date 2019年7月29日
 */

@Data
public class CouponGiveListConditionVo {
	/** 优惠券名称 */
	private String 		couponName;
	/** 使用限制 */
	private BigDecimal	leastConsume;
	/** 面值 */
	private BigDecimal 	denomination;
	/** 有效期标识 0：固定时间 1：领取后指定时间段 */
	private Integer validityType;
	/** 开始时间 */
	private Timestamp startTime;
	/** 结束时间 */
	private Timestamp endTime;
	/** 有效期天数 */
	private Integer 	validity;
	/** 有效期小时数 */
	private Integer validityHour;
	/** 有效期分钟数 */
	private Integer validityMinute;
}

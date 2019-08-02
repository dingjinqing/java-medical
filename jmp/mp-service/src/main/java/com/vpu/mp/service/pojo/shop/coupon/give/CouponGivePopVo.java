package com.vpu.mp.service.pojo.shop.coupon.give;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 优惠券弹窗
 * @author liangchen
 * @date 2019年8月1日
 */
@Data
public class CouponGivePopVo {
	/* 优惠券名称 */
	private String 		actName;
	/* 面额 */
	private BigDecimal 	denomination;
	/* 使用限制 0：无限制 1：有限制 */
	private Integer 	useConsumeRestrict;
	/* 满多少可用 */
	private Integer 	leastConsume;
	/* 剩余数量 */
	private Integer 	remainAmount;
	
}

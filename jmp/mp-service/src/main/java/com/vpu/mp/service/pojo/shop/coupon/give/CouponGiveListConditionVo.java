package com.vpu.mp.service.pojo.shop.coupon.give;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 定向发放优惠券
 * @author liangchen
 * @date 2019年7月29日
 */

@Data
public class CouponGiveListConditionVo {
	
	private String 		couponName;
	private String 		leastConsume;
	private BigDecimal 	denomination;
	private Integer 	validity;
	
}

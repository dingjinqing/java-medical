package com.vpu.mp.service.pojo.shop.coupon.give;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

/**
 * 定向发放优惠券
 * @author liangchen
 * @date 2019年7月29日
 */

@Data
public class CouponGiveListVo {
	private String 	  	actName;
	private Timestamp 	createTime;
	private String 	  	sendCondition;
	private Integer 	sendAction;
	private Integer 	sendStatus;
	List<CouponGiveListConditionVo> couponGiveListConditionVo;
	
}

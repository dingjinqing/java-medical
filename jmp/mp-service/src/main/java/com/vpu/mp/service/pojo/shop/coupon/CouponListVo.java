package com.vpu.mp.service.pojo.shop.coupon;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 优惠券列表出参
 * @author 常乐
 * 2019年7月16日
 */
@Data
public class CouponListVo {
	 	private Integer    id;
	    private String     actName;
	    private String     actCode;
	    private Timestamp  startTime;
	    private Timestamp  endTime;
	    private BigDecimal denomination;
	    private Integer    totalAmount;
	    private Integer    surplus;
	    private Byte       useConsumeRestrict;
	    private BigDecimal    leastConsume;
	    private Byte       isRandom;
	    private Short      receivePerPerson;
	    private Short      giveoutAmount;
	    private Short      giveoutPerson;
	    private Short      receiveAmount;
	    private Short      receivePerson;
	    private Short      usedAmount;
	    private Byte       delFlag;
	    private Byte       useScore;
	    private Integer    scoreNumber;
	    private Integer    used;
	    private Integer enabled;
		/**
		 * 优惠券有效期类型
		 */
		private Byte    validityType;
		/**
		 * 优惠券有效天数
		 */
		private Integer    validity;
		/**
		 * 优惠券有效小时
		 */
		private Integer validityHour;
		/**
		 * 优惠券有效分钟数
		 */
		private Integer validityMinute;
}

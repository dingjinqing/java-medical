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
	    private Byte enabled;
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

		/**
		 * 分裂优惠卷随机金额最低
		 */
		private Integer randomMin;
		/**
		 * 分裂优惠卷随机金额最高
		 */
		private Integer randomMax;
		/**
		 * 分裂优惠券领券人数是否限制 0不限制 1限制
		 */
		private Integer receivePerNum;
		/**
		 * 分裂优惠券可领券人数
		 */
		private Integer receiveNum;
		/**
		 * 是否限制库存 0:限制；1:不限制
		 */
		private Integer limitSurplusFlag;

        /**
         * 当前活动状态：1进行中，2未开始，3已结束，4已停用
         */
        private Byte currentState;
}

package com.vpu.mp.service.pojo.wxapp.coupon;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 用户优惠券列表出参
 * @author 常乐
 * 2019年9月25日
 */
@Data
public class AvailCouponVo {
	/**
	 * 记录ID
	 */
	public Integer id;
	/**
	 * 优惠券名称
	 */
	public String actName;
	/**
	 * 有效期开始时间
	 */
	public Timestamp startTime;
	/**
	 * 有效期结束时间
	 */
	public Timestamp endTime;
	/**
	 * 优惠类型 0:减价;1打折
	 */
	public Integer type;
	/**
	 * 打折或减价量
	 */
	public BigDecimal amount;
	/**
	 * 使用条件
	 */
	public BigDecimal limit_order_amount;
	/**
	 * 是否已使用 0 未使用 1 已使用 2过期吧 3 废除
	 */
	public Integer isUsed;
}

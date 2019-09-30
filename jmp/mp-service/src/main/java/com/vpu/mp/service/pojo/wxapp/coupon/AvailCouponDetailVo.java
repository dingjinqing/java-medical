package com.vpu.mp.service.pojo.wxapp.coupon;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 优惠券详情出参类
 * @author 常乐
 * 2019年9月27日
 */
@Data
public class AvailCouponDetailVo {
	/**
	 * 记录ID
	 */
	public Integer id;
	/**
	 * 优惠券名称
	 */
	public String actName;
	/**
	 * 优惠券码
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


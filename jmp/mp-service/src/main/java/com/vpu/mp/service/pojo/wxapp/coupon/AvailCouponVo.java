package com.vpu.mp.service.pojo.wxapp.coupon;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
	 * 优惠券码
	 */
	public String couponSn;
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
	public BigDecimal limitOrderAmount;
	/**
	 * 是否已使用 0 未使用 1 已使用 2过期吧 3 废除
	 */
	public Integer isUsed;

    private long remainDays;
    private long remainHours;
    private long remainMinutes;
    private long remainSeconds;
    /**
     * 持有会员卡id
     */
    private String     cardId;
    /**
     * 指定商品可用
     */
    private String     recommendGoodsId;
    /**
     * 指定品牌可用
     */
    private String     recommendCatId;
    /**
     * 指定商家分类可用
     */
    private String     recommendSortId;
}

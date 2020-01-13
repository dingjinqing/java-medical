package com.vpu.mp.service.pojo.wxapp.coupon;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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
     * 优惠类型 discount:打折；voucher：优惠减价
     */
	public String actCode;
    /**
     * 面额/打折
     */
	public BigDecimal denomination;
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
    /**
     * 适用商品
     */
	public String recommendGoodsId;
    /**
     * 适用分类
     */
    public String recommendSortId;
    /**
     * 适用品牌
     */
    public String recommendCatId;
    /**
     * 是否积分兑换
     */
    public Byte useScore;
    /**
     * 需要积分数
     */
    public Integer scoreNumber;
    /**
     * 有效期类型
     */
    private Byte validityType;
    /**有效天数*/
    private Integer validity;
    /**有效小时*/
    private Integer validityHour;
    /**有效分钟*/
    private Integer validityMinute;

    /**是否存在使用门槛 0否 1是*/
    private Byte useConsumeRestrict;
    /**
     * 满多少可用
     */
    private BigDecimal leastConsume;
    /**
     * 用户可用积分
     */
    private Integer canUseScore;
    private long remainDays;
    private long remainHours;
    private long remainMinutes;
    private long remainSeconds;
    private String useExplain;
    private String validationCode;
    private String cardId;
    private Integer cardStatus;
    private String needGetCard;
}


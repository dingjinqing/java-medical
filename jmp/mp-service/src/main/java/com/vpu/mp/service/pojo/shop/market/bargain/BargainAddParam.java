package com.vpu.mp.service.pojo.shop.market.bargain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月24日
 */
@Data
public class BargainAddParam {

	/**
	 *  活动类型：0砍到指定金额结算，1砍到任意金额结算
	 */
	@NotNull
	private Byte bargainType;
	
	@NotNull
	private String bargainName;
	@NotNull
	private Timestamp startTime;
	@NotNull
	private Timestamp endTime;
	
	/**
	 *  活动商品的ID
	 */
	@NotNull
	private Integer goodsId;
	
	/**
	 *  运费设置：1免运费，0使用原商品运费模板
	 */
	@NotNull
	private Byte freeFreight;
	
	/**
	 * 砍价预期人数
	 */
	private Integer expectationNumber;

	/**
	 *  指定金额结算模式的砍价底价 或 砍到任意金额结算模式的结算金额上限
	 */
	private BigDecimal expectationPrice;

	/**
	 * 商品首次砍价可砍价比例区间 百分比
	 */
	private Double bargainMin;
	private Double bargainMax;
	
	/**
	 * 向帮忙砍价的用户赠送优惠券，ID串，逗号分隔
	 */
	private String mrkingVoucherId;
	
	/**
	 * 砍价失败后向买家赠送优惠券，ID串，逗号分隔
	 */
	private String rewardCouponId;

	/**
	 * 分享设置 
	 */
	private ShopShareConfig shareConfig;
	
	/**
	 * 任意金额结算模式的结算金额底价 
	 */
	private BigDecimal floorPrice;
	
	/**
	 * 活动商品库存 
	 */
	@NotNull
	@Min(1)
	private Integer stock;
	
	/**
	 *  任意金额结算模式的单次帮砍金额模式：0固定金额，1区间随机金额
	 */
	private Byte bargainMoneyType;
	
	/**
	 * 任意金额结算模式的，单次帮砍金额模式的固定金额模式的，固定金额数值
	 */
	private BigDecimal bargainFixedMoney;
	
	/**
	 * 任意金额结算模式的，单次帮砍金额模式的随机金额模式的，随机金额数值下限
	 */
	private BigDecimal bargainMinMoney;
	
	/**
	 * 任意金额结算模式的，单次帮砍金额模式的随机金额模式的，随机金额数值上限
	 */
	private BigDecimal bargainMaxMoney;
}

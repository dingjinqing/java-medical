package com.vpu.mp.service.pojo.wxapp.order.goods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockField;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * @author 王帅
 *
 */
@Builder
@Data
public class OrderGoodsBo {
    @JsonIgnore
	private Integer orderId;
    @JsonIgnore
	private String orderSn;
    @RedisLockField
	private Integer goodsId;
	private String goodsName;
	private String goodsSn;
	private Integer productId;
	private String productSn;
	private Integer goodsNumber;
	private BigDecimal marketPrice;
	private BigDecimal goodsPrice;
	private String goodsAttr;
	private String goodsAttrId;
	private String goodsImg;
	private Integer straId;
	private BigDecimal perDiscount;
	private Integer isGift;
	private String rGoods;
	private Integer goodsScore;
	private Integer goodsGrowth;
	private BigDecimal discountedGoodsPrice;
	/**
	 * 折后现价总价
	 */
	private BigDecimal discountedTotalPrice;
	/**折扣详情*/
	private String discountDetail;
	/**成本价*/
	private BigDecimal costPrice;
	/**对接CRM 商品推广*/
	private String promoteInfo;
	/***/
	private Integer sendNumber;
	private Integer returnNumber;
	private Byte isReal;
	private Byte refundStatus;
	private Byte commentFlag;
	private Byte fanliType;
	private BigDecimal canCalculateMoney;
	private BigDecimal fanliMoney;
	private BigDecimal totalFanliMoney;
	private String fanliStrategy;
	private BigDecimal fanliPercent;
	private Integer giftId;
	private Byte isCanReturn;
	private Integer reducePriceNum;
	private Byte activityType;
	private Integer activityId;
	private Integer activityRule;

	/**
	 * Table:goods字段
	 */
	private Byte goodsType;
	private Integer deliverTemplateId;
	private BigDecimal goodsWeight;
	/**平台分类*/
	private Integer catId;
	/**商家分类*/
	private Integer sortId;
	/**品牌*/
	private Integer brandId;
	private Byte isCardExclusive;
	/**
	 * 非Table
	 */
	/**TODO 优惠卷*/
	private Integer[] userCoupon;
	/**TODO 营销活动类型*/
	private Byte goodsPriceAction;
	/**加价购id*/
	private Integer purchasePriceId;
	/**加价购换购挡位id*/
	private Integer purchasePriceRuleId;
	/**限时降价活动id*/
	private Integer reducePriceId;
	/**首单特惠活动id*/
	private Integer firstSpecialId;
	/**是否可以配送*/
	private Byte isShipping;

	public String getKey(){
	    return goodsId.toString();
    }
}

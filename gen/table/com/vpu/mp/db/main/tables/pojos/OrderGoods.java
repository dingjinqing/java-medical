/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OrderGoods implements Serializable {

    private static final long serialVersionUID = -168534401;

    private Long       id;
    private Integer    recId;
    private Integer    shopId;
    private Integer    orderId;
    private String     orderSn;
    private Integer    goodsId;
    private String     goodsName;
    private String     goodsSn;
    private Integer    productId;
    private String     productSn;
    private Short      goodsNumber;
    private BigDecimal marketPrice;
    private BigDecimal goodsPrice;
    private String     goodsAttr;
    private Short      sendNumber;
    private Short      returnNumber;
    private Byte       isReal;
    private String     goodsAttrId;
    private String     goodsImg;
    private Byte       refundStatus;
    private Byte       commentFlag;
    private Integer    straId;
    private BigDecimal perDiscount;
    private Integer    isGift;
    private String     rGoods;
    private Integer    goodsScore;
    private Integer    goodsGrowth;
    private BigDecimal discountedGoodsPrice;
    private String     discountDetail;
    private Byte       fanliType;
    private BigDecimal canCalculateMoney;
    private BigDecimal fanliMoney;
    private BigDecimal discountedTotalPrice;
    private BigDecimal totalFanliMoney;
    private Integer    purchasePriceId;
    private Integer    purchasePriceRuleId;
    private Integer    reducePriceId;
    private String     fanliStrategy;
    private BigDecimal fanliPercent;
    private BigDecimal costPrice;
    private Byte       isCardExclusive;
    private String     promoteInfo;
    private Integer    giftId;
    private Byte       isCanReturn;

    public OrderGoods() {}

    public OrderGoods(OrderGoods value) {
        this.id = value.id;
        this.recId = value.recId;
        this.shopId = value.shopId;
        this.orderId = value.orderId;
        this.orderSn = value.orderSn;
        this.goodsId = value.goodsId;
        this.goodsName = value.goodsName;
        this.goodsSn = value.goodsSn;
        this.productId = value.productId;
        this.productSn = value.productSn;
        this.goodsNumber = value.goodsNumber;
        this.marketPrice = value.marketPrice;
        this.goodsPrice = value.goodsPrice;
        this.goodsAttr = value.goodsAttr;
        this.sendNumber = value.sendNumber;
        this.returnNumber = value.returnNumber;
        this.isReal = value.isReal;
        this.goodsAttrId = value.goodsAttrId;
        this.goodsImg = value.goodsImg;
        this.refundStatus = value.refundStatus;
        this.commentFlag = value.commentFlag;
        this.straId = value.straId;
        this.perDiscount = value.perDiscount;
        this.isGift = value.isGift;
        this.rGoods = value.rGoods;
        this.goodsScore = value.goodsScore;
        this.goodsGrowth = value.goodsGrowth;
        this.discountedGoodsPrice = value.discountedGoodsPrice;
        this.discountDetail = value.discountDetail;
        this.fanliType = value.fanliType;
        this.canCalculateMoney = value.canCalculateMoney;
        this.fanliMoney = value.fanliMoney;
        this.discountedTotalPrice = value.discountedTotalPrice;
        this.totalFanliMoney = value.totalFanliMoney;
        this.purchasePriceId = value.purchasePriceId;
        this.purchasePriceRuleId = value.purchasePriceRuleId;
        this.reducePriceId = value.reducePriceId;
        this.fanliStrategy = value.fanliStrategy;
        this.fanliPercent = value.fanliPercent;
        this.costPrice = value.costPrice;
        this.isCardExclusive = value.isCardExclusive;
        this.promoteInfo = value.promoteInfo;
        this.giftId = value.giftId;
        this.isCanReturn = value.isCanReturn;
    }

    public OrderGoods(
        Long       id,
        Integer    recId,
        Integer    shopId,
        Integer    orderId,
        String     orderSn,
        Integer    goodsId,
        String     goodsName,
        String     goodsSn,
        Integer    productId,
        String     productSn,
        Short      goodsNumber,
        BigDecimal marketPrice,
        BigDecimal goodsPrice,
        String     goodsAttr,
        Short      sendNumber,
        Short      returnNumber,
        Byte       isReal,
        String     goodsAttrId,
        String     goodsImg,
        Byte       refundStatus,
        Byte       commentFlag,
        Integer    straId,
        BigDecimal perDiscount,
        Integer    isGift,
        String     rGoods,
        Integer    goodsScore,
        Integer    goodsGrowth,
        BigDecimal discountedGoodsPrice,
        String     discountDetail,
        Byte       fanliType,
        BigDecimal canCalculateMoney,
        BigDecimal fanliMoney,
        BigDecimal discountedTotalPrice,
        BigDecimal totalFanliMoney,
        Integer    purchasePriceId,
        Integer    purchasePriceRuleId,
        Integer    reducePriceId,
        String     fanliStrategy,
        BigDecimal fanliPercent,
        BigDecimal costPrice,
        Byte       isCardExclusive,
        String     promoteInfo,
        Integer    giftId,
        Byte       isCanReturn
    ) {
        this.id = id;
        this.recId = recId;
        this.shopId = shopId;
        this.orderId = orderId;
        this.orderSn = orderSn;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsSn = goodsSn;
        this.productId = productId;
        this.productSn = productSn;
        this.goodsNumber = goodsNumber;
        this.marketPrice = marketPrice;
        this.goodsPrice = goodsPrice;
        this.goodsAttr = goodsAttr;
        this.sendNumber = sendNumber;
        this.returnNumber = returnNumber;
        this.isReal = isReal;
        this.goodsAttrId = goodsAttrId;
        this.goodsImg = goodsImg;
        this.refundStatus = refundStatus;
        this.commentFlag = commentFlag;
        this.straId = straId;
        this.perDiscount = perDiscount;
        this.isGift = isGift;
        this.rGoods = rGoods;
        this.goodsScore = goodsScore;
        this.goodsGrowth = goodsGrowth;
        this.discountedGoodsPrice = discountedGoodsPrice;
        this.discountDetail = discountDetail;
        this.fanliType = fanliType;
        this.canCalculateMoney = canCalculateMoney;
        this.fanliMoney = fanliMoney;
        this.discountedTotalPrice = discountedTotalPrice;
        this.totalFanliMoney = totalFanliMoney;
        this.purchasePriceId = purchasePriceId;
        this.purchasePriceRuleId = purchasePriceRuleId;
        this.reducePriceId = reducePriceId;
        this.fanliStrategy = fanliStrategy;
        this.fanliPercent = fanliPercent;
        this.costPrice = costPrice;
        this.isCardExclusive = isCardExclusive;
        this.promoteInfo = promoteInfo;
        this.giftId = giftId;
        this.isCanReturn = isCanReturn;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRecId() {
        return this.recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return this.orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSn() {
        return this.goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductSn() {
        return this.productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public Short getGoodsNumber() {
        return this.goodsNumber;
    }

    public void setGoodsNumber(Short goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public BigDecimal getMarketPrice() {
        return this.marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getGoodsPrice() {
        return this.goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsAttr() {
        return this.goodsAttr;
    }

    public void setGoodsAttr(String goodsAttr) {
        this.goodsAttr = goodsAttr;
    }

    public Short getSendNumber() {
        return this.sendNumber;
    }

    public void setSendNumber(Short sendNumber) {
        this.sendNumber = sendNumber;
    }

    public Short getReturnNumber() {
        return this.returnNumber;
    }

    public void setReturnNumber(Short returnNumber) {
        this.returnNumber = returnNumber;
    }

    public Byte getIsReal() {
        return this.isReal;
    }

    public void setIsReal(Byte isReal) {
        this.isReal = isReal;
    }

    public String getGoodsAttrId() {
        return this.goodsAttrId;
    }

    public void setGoodsAttrId(String goodsAttrId) {
        this.goodsAttrId = goodsAttrId;
    }

    public String getGoodsImg() {
        return this.goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Byte getRefundStatus() {
        return this.refundStatus;
    }

    public void setRefundStatus(Byte refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Byte getCommentFlag() {
        return this.commentFlag;
    }

    public void setCommentFlag(Byte commentFlag) {
        this.commentFlag = commentFlag;
    }

    public Integer getStraId() {
        return this.straId;
    }

    public void setStraId(Integer straId) {
        this.straId = straId;
    }

    public BigDecimal getPerDiscount() {
        return this.perDiscount;
    }

    public void setPerDiscount(BigDecimal perDiscount) {
        this.perDiscount = perDiscount;
    }

    public Integer getIsGift() {
        return this.isGift;
    }

    public void setIsGift(Integer isGift) {
        this.isGift = isGift;
    }

    public String getRGoods() {
        return this.rGoods;
    }

    public void setRGoods(String rGoods) {
        this.rGoods = rGoods;
    }

    public Integer getGoodsScore() {
        return this.goodsScore;
    }

    public void setGoodsScore(Integer goodsScore) {
        this.goodsScore = goodsScore;
    }

    public Integer getGoodsGrowth() {
        return this.goodsGrowth;
    }

    public void setGoodsGrowth(Integer goodsGrowth) {
        this.goodsGrowth = goodsGrowth;
    }

    public BigDecimal getDiscountedGoodsPrice() {
        return this.discountedGoodsPrice;
    }

    public void setDiscountedGoodsPrice(BigDecimal discountedGoodsPrice) {
        this.discountedGoodsPrice = discountedGoodsPrice;
    }

    public String getDiscountDetail() {
        return this.discountDetail;
    }

    public void setDiscountDetail(String discountDetail) {
        this.discountDetail = discountDetail;
    }

    public Byte getFanliType() {
        return this.fanliType;
    }

    public void setFanliType(Byte fanliType) {
        this.fanliType = fanliType;
    }

    public BigDecimal getCanCalculateMoney() {
        return this.canCalculateMoney;
    }

    public void setCanCalculateMoney(BigDecimal canCalculateMoney) {
        this.canCalculateMoney = canCalculateMoney;
    }

    public BigDecimal getFanliMoney() {
        return this.fanliMoney;
    }

    public void setFanliMoney(BigDecimal fanliMoney) {
        this.fanliMoney = fanliMoney;
    }

    public BigDecimal getDiscountedTotalPrice() {
        return this.discountedTotalPrice;
    }

    public void setDiscountedTotalPrice(BigDecimal discountedTotalPrice) {
        this.discountedTotalPrice = discountedTotalPrice;
    }

    public BigDecimal getTotalFanliMoney() {
        return this.totalFanliMoney;
    }

    public void setTotalFanliMoney(BigDecimal totalFanliMoney) {
        this.totalFanliMoney = totalFanliMoney;
    }

    public Integer getPurchasePriceId() {
        return this.purchasePriceId;
    }

    public void setPurchasePriceId(Integer purchasePriceId) {
        this.purchasePriceId = purchasePriceId;
    }

    public Integer getPurchasePriceRuleId() {
        return this.purchasePriceRuleId;
    }

    public void setPurchasePriceRuleId(Integer purchasePriceRuleId) {
        this.purchasePriceRuleId = purchasePriceRuleId;
    }

    public Integer getReducePriceId() {
        return this.reducePriceId;
    }

    public void setReducePriceId(Integer reducePriceId) {
        this.reducePriceId = reducePriceId;
    }

    public String getFanliStrategy() {
        return this.fanliStrategy;
    }

    public void setFanliStrategy(String fanliStrategy) {
        this.fanliStrategy = fanliStrategy;
    }

    public BigDecimal getFanliPercent() {
        return this.fanliPercent;
    }

    public void setFanliPercent(BigDecimal fanliPercent) {
        this.fanliPercent = fanliPercent;
    }

    public BigDecimal getCostPrice() {
        return this.costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Byte getIsCardExclusive() {
        return this.isCardExclusive;
    }

    public void setIsCardExclusive(Byte isCardExclusive) {
        this.isCardExclusive = isCardExclusive;
    }

    public String getPromoteInfo() {
        return this.promoteInfo;
    }

    public void setPromoteInfo(String promoteInfo) {
        this.promoteInfo = promoteInfo;
    }

    public Integer getGiftId() {
        return this.giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public Byte getIsCanReturn() {
        return this.isCanReturn;
    }

    public void setIsCanReturn(Byte isCanReturn) {
        this.isCanReturn = isCanReturn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("OrderGoods (");

        sb.append(id);
        sb.append(", ").append(recId);
        sb.append(", ").append(shopId);
        sb.append(", ").append(orderId);
        sb.append(", ").append(orderSn);
        sb.append(", ").append(goodsId);
        sb.append(", ").append(goodsName);
        sb.append(", ").append(goodsSn);
        sb.append(", ").append(productId);
        sb.append(", ").append(productSn);
        sb.append(", ").append(goodsNumber);
        sb.append(", ").append(marketPrice);
        sb.append(", ").append(goodsPrice);
        sb.append(", ").append(goodsAttr);
        sb.append(", ").append(sendNumber);
        sb.append(", ").append(returnNumber);
        sb.append(", ").append(isReal);
        sb.append(", ").append(goodsAttrId);
        sb.append(", ").append(goodsImg);
        sb.append(", ").append(refundStatus);
        sb.append(", ").append(commentFlag);
        sb.append(", ").append(straId);
        sb.append(", ").append(perDiscount);
        sb.append(", ").append(isGift);
        sb.append(", ").append(rGoods);
        sb.append(", ").append(goodsScore);
        sb.append(", ").append(goodsGrowth);
        sb.append(", ").append(discountedGoodsPrice);
        sb.append(", ").append(discountDetail);
        sb.append(", ").append(fanliType);
        sb.append(", ").append(canCalculateMoney);
        sb.append(", ").append(fanliMoney);
        sb.append(", ").append(discountedTotalPrice);
        sb.append(", ").append(totalFanliMoney);
        sb.append(", ").append(purchasePriceId);
        sb.append(", ").append(purchasePriceRuleId);
        sb.append(", ").append(reducePriceId);
        sb.append(", ").append(fanliStrategy);
        sb.append(", ").append(fanliPercent);
        sb.append(", ").append(costPrice);
        sb.append(", ").append(isCardExclusive);
        sb.append(", ").append(promoteInfo);
        sb.append(", ").append(giftId);
        sb.append(", ").append(isCanReturn);

        sb.append(")");
        return sb.toString();
    }
}

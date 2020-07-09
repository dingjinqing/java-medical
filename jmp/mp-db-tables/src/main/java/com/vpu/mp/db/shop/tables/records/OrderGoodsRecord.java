/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.OrderGoods;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 订单商品表  b2c_order_goods
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OrderGoodsRecord extends UpdatableRecordImpl<OrderGoodsRecord> {

    private static final long serialVersionUID = 1538478674;

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.rec_id</code>.
     */
    public void setRecId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.rec_id</code>.
     */
    public Integer getRecId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.main_rec_id</code>. 主订单rec_id
     */
    public void setMainRecId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.main_rec_id</code>. 主订单rec_id
     */
    public Integer getMainRecId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.order_id</code>.
     */
    public void setOrderId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.order_id</code>.
     */
    public Integer getOrderId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.order_sn</code>.
     */
    public void setOrderSn(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.order_sn</code>.
     */
    public String getOrderSn() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.goods_id</code>.
     */
    public void setGoodsId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.goods_id</code>.
     */
    public Integer getGoodsId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.goods_name</code>.
     */
    public void setGoodsName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.goods_name</code>.
     */
    public String getGoodsName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.goods_sn</code>.
     */
    public void setGoodsSn(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.goods_sn</code>.
     */
    public String getGoodsSn() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.product_id</code>.
     */
    public void setProductId(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.product_id</code>.
     */
    public Integer getProductId() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.product_sn</code>.
     */
    public void setProductSn(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.product_sn</code>.
     */
    public String getProductSn() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.goods_number</code>.
     */
    public void setGoodsNumber(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.goods_number</code>.
     */
    public Integer getGoodsNumber() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.market_price</code>.
     */
    public void setMarketPrice(BigDecimal value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.market_price</code>.
     */
    public BigDecimal getMarketPrice() {
        return (BigDecimal) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.goods_price</code>.
     */
    public void setGoodsPrice(BigDecimal value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.goods_price</code>.
     */
    public BigDecimal getGoodsPrice() {
        return (BigDecimal) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.goods_attr</code>.
     */
    public void setGoodsAttr(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.goods_attr</code>.
     */
    public String getGoodsAttr() {
        return (String) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.send_number</code>.
     */
    public void setSendNumber(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.send_number</code>.
     */
    public Integer getSendNumber() {
        return (Integer) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.return_number</code>. 退货/退款成功数量
     */
    public void setReturnNumber(Integer value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.return_number</code>. 退货/退款成功数量
     */
    public Integer getReturnNumber() {
        return (Integer) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.is_real</code>.
     */
    public void setIsReal(Byte value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.is_real</code>.
     */
    public Byte getIsReal() {
        return (Byte) get(16);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.goods_attr_id</code>.
     */
    public void setGoodsAttrId(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.goods_attr_id</code>.
     */
    public String getGoodsAttrId() {
        return (String) get(17);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.goods_img</code>.
     */
    public void setGoodsImg(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.goods_img</code>.
     */
    public String getGoodsImg() {
        return (String) get(18);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.refund_status</code>. 1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货
     */
    public void setRefundStatus(Byte value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.refund_status</code>. 1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货
     */
    public Byte getRefundStatus() {
        return (Byte) get(19);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.comment_flag</code>. 0:未评论，1:已评论，2：已晒单
     */
    public void setCommentFlag(Byte value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.comment_flag</code>. 0:未评论，1:已评论，2：已晒单
     */
    public Byte getCommentFlag() {
        return (Byte) get(20);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.stra_id</code>. 商品参与的促销活动id
     */
    public void setStraId(Integer value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.stra_id</code>. 商品参与的促销活动id
     */
    public Integer getStraId() {
        return (Integer) get(21);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.per_discount</code>. 促销折扣均摊到每件商品的折扣
     */
    public void setPerDiscount(BigDecimal value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.per_discount</code>. 促销折扣均摊到每件商品的折扣
     */
    public BigDecimal getPerDiscount() {
        return (BigDecimal) get(22);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.is_gift</code>. 是否是赠品
     */
    public void setIsGift(Integer value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.is_gift</code>. 是否是赠品
     */
    public Integer getIsGift() {
        return (Integer) get(23);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.r_goods</code>. 赠品的关联商品
     */
    public void setRGoods(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.r_goods</code>. 赠品的关联商品
     */
    public String getRGoods() {
        return (String) get(24);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.goods_score</code>. 商品积分
     */
    public void setGoodsScore(Integer value) {
        set(25, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.goods_score</code>. 商品积分
     */
    public Integer getGoodsScore() {
        return (Integer) get(25);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.goods_growth</code>. 商品成长值
     */
    public void setGoodsGrowth(Integer value) {
        set(26, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.goods_growth</code>. 商品成长值
     */
    public Integer getGoodsGrowth() {
        return (Integer) get(26);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.discounted_goods_price</code>. 折后单价
     */
    public void setDiscountedGoodsPrice(BigDecimal value) {
        set(27, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.discounted_goods_price</code>. 折后单价
     */
    public BigDecimal getDiscountedGoodsPrice() {
        return (BigDecimal) get(27);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.discount_detail</code>. 打折详情，json存储
     */
    public void setDiscountDetail(String value) {
        set(28, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.discount_detail</code>. 打折详情，json存储
     */
    public String getDiscountDetail() {
        return (String) get(28);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.fanli_type</code>. 0:不返利商品，1：返利商品
     */
    public void setFanliType(Byte value) {
        set(29, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.fanli_type</code>. 0:不返利商品，1：返利商品
     */
    public Byte getFanliType() {
        return (Byte) get(29);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.can_calculate_money</code>. 单品可计算返利金额，刨除优惠券和其他折扣后的金额
     */
    public void setCanCalculateMoney(BigDecimal value) {
        set(30, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.can_calculate_money</code>. 单品可计算返利金额，刨除优惠券和其他折扣后的金额
     */
    public BigDecimal getCanCalculateMoney() {
        return (BigDecimal) get(30);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.fanli_money</code>. 单品返利金额
     */
    public void setFanliMoney(BigDecimal value) {
        set(31, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.fanli_money</code>. 单品返利金额
     */
    public BigDecimal getFanliMoney() {
        return (BigDecimal) get(31);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.discounted_total_price</code>. 折后总价
     */
    public void setDiscountedTotalPrice(BigDecimal value) {
        set(32, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.discounted_total_price</code>. 折后总价
     */
    public BigDecimal getDiscountedTotalPrice() {
        return (BigDecimal) get(32);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.total_fanli_money</code>. 商品返利总金额
     */
    public void setTotalFanliMoney(BigDecimal value) {
        set(33, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.total_fanli_money</code>. 商品返利总金额
     */
    public BigDecimal getTotalFanliMoney() {
        return (BigDecimal) get(33);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.fanli_strategy</code>. 返利配置详情
     */
    public void setFanliStrategy(String value) {
        set(34, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.fanli_strategy</code>. 返利配置详情
     */
    public String getFanliStrategy() {
        return (String) get(34);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.fanli_percent</code>. 返利比例
     */
    public void setFanliPercent(BigDecimal value) {
        set(35, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.fanli_percent</code>. 返利比例
     */
    public BigDecimal getFanliPercent() {
        return (BigDecimal) get(35);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.cost_price</code>. 成本价
     */
    public void setCostPrice(BigDecimal value) {
        set(36, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.cost_price</code>. 成本价
     */
    public BigDecimal getCostPrice() {
        return (BigDecimal) get(36);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.is_card_exclusive</code>. 是否会员卡专属
     */
    public void setIsCardExclusive(Byte value) {
        set(37, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.is_card_exclusive</code>. 是否会员卡专属
     */
    public Byte getIsCardExclusive() {
        return (Byte) get(37);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.promote_info</code>. 推广信息
     */
    public void setPromoteInfo(String value) {
        set(38, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.promote_info</code>. 推广信息
     */
    public String getPromoteInfo() {
        return (String) get(38);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.gift_id</code>. 赠品ID
     */
    public void setGiftId(Integer value) {
        set(39, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.gift_id</code>. 赠品ID
     */
    public Integer getGiftId() {
        return (Integer) get(39);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.is_can_return</code>. 是否可退款
     */
    public void setIsCanReturn(Byte value) {
        set(40, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.is_can_return</code>. 是否可退款
     */
    public Byte getIsCanReturn() {
        return (Byte) get(40);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.reduce_price_num</code>.
     */
    public void setReducePriceNum(Short value) {
        set(41, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.reduce_price_num</code>.
     */
    public Short getReducePriceNum() {
        return (Short) get(41);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.activity_type</code>. 营销活动种类
     */
    public void setActivityType(Byte value) {
        set(42, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.activity_type</code>. 营销活动种类
     */
    public Byte getActivityType() {
        return (Byte) get(42);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.activity_id</code>. 营销活动id
     */
    public void setActivityId(Integer value) {
        set(43, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.activity_id</code>. 营销活动id
     */
    public Integer getActivityId() {
        return (Integer) get(43);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.activity_rule</code>. 营销活动id
     */
    public void setActivityRule(Integer value) {
        set(44, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.activity_rule</code>. 营销活动id
     */
    public Integer getActivityRule() {
        return (Integer) get(44);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(45, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(45);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(46, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.update_time</code>. 修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(46);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.purchase_id</code>. 加价购活动id
     */
    public void setPurchaseId(Integer value) {
        set(47, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.purchase_id</code>. 加价购活动id
     */
    public Integer getPurchaseId() {
        return (Integer) get(47);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_order_goods.prescription_no</code>. 处方项目明细号码（可根据此字段反查批次号）
     */
    public void setPrescriptionNo(String value) {
        set(48, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_order_goods.prescription_no</code>. 处方项目明细号码（可根据此字段反查批次号）
     */
    public String getPrescriptionNo() {
        return (String) get(48);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OrderGoodsRecord
     */
    public OrderGoodsRecord() {
        super(OrderGoods.ORDER_GOODS);
    }

    /**
     * Create a detached, initialised OrderGoodsRecord
     */
    public OrderGoodsRecord(Integer recId, Integer mainRecId, Integer shopId, Integer orderId, String orderSn, Integer goodsId, String goodsName, String goodsSn, Integer productId, String productSn, Integer goodsNumber, BigDecimal marketPrice, BigDecimal goodsPrice, String goodsAttr, Integer sendNumber, Integer returnNumber, Byte isReal, String goodsAttrId, String goodsImg, Byte refundStatus, Byte commentFlag, Integer straId, BigDecimal perDiscount, Integer isGift, String rGoods, Integer goodsScore, Integer goodsGrowth, BigDecimal discountedGoodsPrice, String discountDetail, Byte fanliType, BigDecimal canCalculateMoney, BigDecimal fanliMoney, BigDecimal discountedTotalPrice, BigDecimal totalFanliMoney, String fanliStrategy, BigDecimal fanliPercent, BigDecimal costPrice, Byte isCardExclusive, String promoteInfo, Integer giftId, Byte isCanReturn, Short reducePriceNum, Byte activityType, Integer activityId, Integer activityRule, Timestamp createTime, Timestamp updateTime, Integer purchaseId, String prescriptionNo) {
        super(OrderGoods.ORDER_GOODS);

        set(0, recId);
        set(1, mainRecId);
        set(2, shopId);
        set(3, orderId);
        set(4, orderSn);
        set(5, goodsId);
        set(6, goodsName);
        set(7, goodsSn);
        set(8, productId);
        set(9, productSn);
        set(10, goodsNumber);
        set(11, marketPrice);
        set(12, goodsPrice);
        set(13, goodsAttr);
        set(14, sendNumber);
        set(15, returnNumber);
        set(16, isReal);
        set(17, goodsAttrId);
        set(18, goodsImg);
        set(19, refundStatus);
        set(20, commentFlag);
        set(21, straId);
        set(22, perDiscount);
        set(23, isGift);
        set(24, rGoods);
        set(25, goodsScore);
        set(26, goodsGrowth);
        set(27, discountedGoodsPrice);
        set(28, discountDetail);
        set(29, fanliType);
        set(30, canCalculateMoney);
        set(31, fanliMoney);
        set(32, discountedTotalPrice);
        set(33, totalFanliMoney);
        set(34, fanliStrategy);
        set(35, fanliPercent);
        set(36, costPrice);
        set(37, isCardExclusive);
        set(38, promoteInfo);
        set(39, giftId);
        set(40, isCanReturn);
        set(41, reducePriceNum);
        set(42, activityType);
        set(43, activityId);
        set(44, activityRule);
        set(45, createTime);
        set(46, updateTime);
        set(47, purchaseId);
        set(48, prescriptionNo);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.OrderGoodsBak;
import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.sql.Timestamp;


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
public class OrderGoodsBakRecord extends UpdatableRecordImpl<OrderGoodsBakRecord> {

    private static final long serialVersionUID = 1197143186;

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.rec_id</code>.
     */
    public void setRecId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.rec_id</code>.
     */
    public Integer getRecId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.main_rec_id</code>. 主订单rec_id
     */
    public void setMainRecId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.main_rec_id</code>. 主订单rec_id
     */
    public Integer getMainRecId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.order_id</code>.
     */
    public void setOrderId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.order_id</code>.
     */
    public Integer getOrderId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.order_sn</code>.
     */
    public void setOrderSn(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.order_sn</code>.
     */
    public String getOrderSn() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.goods_id</code>.
     */
    public void setGoodsId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.goods_id</code>.
     */
    public Integer getGoodsId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.goods_name</code>.
     */
    public void setGoodsName(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.goods_name</code>.
     */
    public String getGoodsName() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.goods_sn</code>.
     */
    public void setGoodsSn(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.goods_sn</code>.
     */
    public String getGoodsSn() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.product_id</code>.
     */
    public void setProductId(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.product_id</code>.
     */
    public Integer getProductId() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.product_sn</code>.
     */
    public void setProductSn(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.product_sn</code>.
     */
    public String getProductSn() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.goods_number</code>.
     */
    public void setGoodsNumber(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.goods_number</code>.
     */
    public Integer getGoodsNumber() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.market_price</code>.
     */
    public void setMarketPrice(BigDecimal value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.market_price</code>.
     */
    public BigDecimal getMarketPrice() {
        return (BigDecimal) get(12);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.goods_price</code>.
     */
    public void setGoodsPrice(BigDecimal value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.goods_price</code>.
     */
    public BigDecimal getGoodsPrice() {
        return (BigDecimal) get(13);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.goods_attr</code>.
     */
    public void setGoodsAttr(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.goods_attr</code>.
     */
    public String getGoodsAttr() {
        return (String) get(14);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.send_number</code>.
     */
    public void setSendNumber(Integer value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.send_number</code>.
     */
    public Integer getSendNumber() {
        return (Integer) get(15);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.return_number</code>. 退货/退款成功数量
     */
    public void setReturnNumber(Integer value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.return_number</code>. 退货/退款成功数量
     */
    public Integer getReturnNumber() {
        return (Integer) get(16);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.is_real</code>.
     */
    public void setIsReal(Byte value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.is_real</code>.
     */
    public Byte getIsReal() {
        return (Byte) get(17);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.goods_attr_id</code>.
     */
    public void setGoodsAttrId(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.goods_attr_id</code>.
     */
    public String getGoodsAttrId() {
        return (String) get(18);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.goods_img</code>.
     */
    public void setGoodsImg(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.goods_img</code>.
     */
    public String getGoodsImg() {
        return (String) get(19);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.refund_status</code>. 1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货
     */
    public void setRefundStatus(Byte value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.refund_status</code>. 1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货
     */
    public Byte getRefundStatus() {
        return (Byte) get(20);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.comment_flag</code>. 0:未评论，1:已评论，2：已晒单
     */
    public void setCommentFlag(Byte value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.comment_flag</code>. 0:未评论，1:已评论，2：已晒单
     */
    public Byte getCommentFlag() {
        return (Byte) get(21);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.stra_id</code>. 商品参与的促销活动id
     */
    public void setStraId(Integer value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.stra_id</code>. 商品参与的促销活动id
     */
    public Integer getStraId() {
        return (Integer) get(22);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.per_discount</code>. 促销折扣均摊到每件商品的折扣
     */
    public void setPerDiscount(BigDecimal value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.per_discount</code>. 促销折扣均摊到每件商品的折扣
     */
    public BigDecimal getPerDiscount() {
        return (BigDecimal) get(23);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.is_gift</code>. 是否是赠品
     */
    public void setIsGift(Integer value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.is_gift</code>. 是否是赠品
     */
    public Integer getIsGift() {
        return (Integer) get(24);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.r_goods</code>. 赠品的关联商品
     */
    public void setRGoods(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.r_goods</code>. 赠品的关联商品
     */
    public String getRGoods() {
        return (String) get(25);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.goods_score</code>. 商品积分
     */
    public void setGoodsScore(Integer value) {
        set(26, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.goods_score</code>. 商品积分
     */
    public Integer getGoodsScore() {
        return (Integer) get(26);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.goods_growth</code>. 商品成长值
     */
    public void setGoodsGrowth(Integer value) {
        set(27, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.goods_growth</code>. 商品成长值
     */
    public Integer getGoodsGrowth() {
        return (Integer) get(27);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.discounted_goods_price</code>. 折后单价
     */
    public void setDiscountedGoodsPrice(BigDecimal value) {
        set(28, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.discounted_goods_price</code>. 折后单价
     */
    public BigDecimal getDiscountedGoodsPrice() {
        return (BigDecimal) get(28);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.discount_detail</code>. 打折详情，json存储
     */
    public void setDiscountDetail(String value) {
        set(29, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.discount_detail</code>. 打折详情，json存储
     */
    public String getDiscountDetail() {
        return (String) get(29);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.fanli_type</code>. 0:不返利商品，1：返利商品
     */
    public void setFanliType(Byte value) {
        set(30, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.fanli_type</code>. 0:不返利商品，1：返利商品
     */
    public Byte getFanliType() {
        return (Byte) get(30);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.can_calculate_money</code>. 单品可计算返利金额，刨除优惠券和其他折扣后的金额
     */
    public void setCanCalculateMoney(BigDecimal value) {
        set(31, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.can_calculate_money</code>. 单品可计算返利金额，刨除优惠券和其他折扣后的金额
     */
    public BigDecimal getCanCalculateMoney() {
        return (BigDecimal) get(31);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.fanli_money</code>. 单品返利金额
     */
    public void setFanliMoney(BigDecimal value) {
        set(32, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.fanli_money</code>. 单品返利金额
     */
    public BigDecimal getFanliMoney() {
        return (BigDecimal) get(32);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.discounted_total_price</code>. 折后总价
     */
    public void setDiscountedTotalPrice(BigDecimal value) {
        set(33, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.discounted_total_price</code>. 折后总价
     */
    public BigDecimal getDiscountedTotalPrice() {
        return (BigDecimal) get(33);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.total_fanli_money</code>. 商品返利总金额
     */
    public void setTotalFanliMoney(BigDecimal value) {
        set(34, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.total_fanli_money</code>. 商品返利总金额
     */
    public BigDecimal getTotalFanliMoney() {
        return (BigDecimal) get(34);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.fanli_strategy</code>. 返利配置详情
     */
    public void setFanliStrategy(String value) {
        set(35, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.fanli_strategy</code>. 返利配置详情
     */
    public String getFanliStrategy() {
        return (String) get(35);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.fanli_percent</code>. 返利比例
     */
    public void setFanliPercent(BigDecimal value) {
        set(36, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.fanli_percent</code>. 返利比例
     */
    public BigDecimal getFanliPercent() {
        return (BigDecimal) get(36);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.cost_price</code>. 成本价
     */
    public void setCostPrice(BigDecimal value) {
        set(37, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.cost_price</code>. 成本价
     */
    public BigDecimal getCostPrice() {
        return (BigDecimal) get(37);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.is_card_exclusive</code>. 是否会员卡专属
     */
    public void setIsCardExclusive(Byte value) {
        set(38, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.is_card_exclusive</code>. 是否会员卡专属
     */
    public Byte getIsCardExclusive() {
        return (Byte) get(38);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.promote_info</code>. 推广信息
     */
    public void setPromoteInfo(String value) {
        set(39, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.promote_info</code>. 推广信息
     */
    public String getPromoteInfo() {
        return (String) get(39);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.gift_id</code>. 赠品ID
     */
    public void setGiftId(Integer value) {
        set(40, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.gift_id</code>. 赠品ID
     */
    public Integer getGiftId() {
        return (Integer) get(40);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.is_can_return</code>. 是否可退款
     */
    public void setIsCanReturn(Byte value) {
        set(41, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.is_can_return</code>. 是否可退款
     */
    public Byte getIsCanReturn() {
        return (Byte) get(41);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.reduce_price_num</code>.
     */
    public void setReducePriceNum(Short value) {
        set(42, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.reduce_price_num</code>.
     */
    public Short getReducePriceNum() {
        return (Short) get(42);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.activity_type</code>. 营销活动种类
     */
    public void setActivityType(Byte value) {
        set(43, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.activity_type</code>. 营销活动种类
     */
    public Byte getActivityType() {
        return (Byte) get(43);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.activity_id</code>. 营销活动id
     */
    public void setActivityId(Integer value) {
        set(44, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.activity_id</code>. 营销活动id
     */
    public Integer getActivityId() {
        return (Integer) get(44);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.activity_rule</code>. 营销活动id
     */
    public void setActivityRule(Integer value) {
        set(45, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.activity_rule</code>. 营销活动id
     */
    public Integer getActivityRule() {
        return (Integer) get(45);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(46, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(46);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(47, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.update_time</code>. 修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(47);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.purchase_id</code>. 加价购活动id
     */
    public void setPurchaseId(Integer value) {
        set(48, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.purchase_id</code>. 加价购活动id
     */
    public Integer getPurchaseId() {
        return (Integer) get(48);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.prescription_old_code</code>. 老处方项目明细号码（可根据此字段反查批次号）
     */
    public void setPrescriptionOldCode(String value) {
        set(49, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.prescription_old_code</code>. 老处方项目明细号码（可根据此字段反查批次号）
     */
    public String getPrescriptionOldCode() {
        return (String) get(49);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.prescription_code</code>. 处方项目明细号码（可根据此字段反查批次号）
     */
    public void setPrescriptionCode(String value) {
        set(50, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.prescription_code</code>. 处方项目明细号码（可根据此字段反查批次号）
     */
    public String getPrescriptionCode() {
        return (String) get(50);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.medical_audit_type</code>. 药品审核类型, 0不审核,1审核,2开方,3根据处方下单
     */
    public void setMedicalAuditType(Byte value) {
        set(51, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.medical_audit_type</code>. 药品审核类型, 0不审核,1审核,2开方,3根据处方下单
     */
    public Byte getMedicalAuditType() {
        return (Byte) get(51);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.medical_audit_status</code>. 药品审核状态 0未审核 1审核通过 2审核不通过
     */
    public void setMedicalAuditStatus(Byte value) {
        set(52, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.medical_audit_status</code>. 药品审核状态 0未审核 1审核通过 2审核不通过
     */
    public Byte getMedicalAuditStatus() {
        return (Byte) get(52);
    }

    /**
     * Setter for <code>mini_main.b2c_order_goods_bak.audit_time</code>. 药品审核时间
     */
    public void setAuditTime(Timestamp value) {
        set(53, value);
    }

    /**
     * Getter for <code>mini_main.b2c_order_goods_bak.audit_time</code>. 药品审核时间
     */
    public Timestamp getAuditTime() {
        return (Timestamp) get(53);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OrderGoodsBakRecord
     */
    public OrderGoodsBakRecord() {
        super(OrderGoodsBak.ORDER_GOODS_BAK);
    }

    /**
     * Create a detached, initialised OrderGoodsBakRecord
     */
    public OrderGoodsBakRecord(Long id, Integer recId, Integer mainRecId, Integer shopId, Integer orderId, String orderSn, Integer goodsId, String goodsName, String goodsSn, Integer productId, String productSn, Integer goodsNumber, BigDecimal marketPrice, BigDecimal goodsPrice, String goodsAttr, Integer sendNumber, Integer returnNumber, Byte isReal, String goodsAttrId, String goodsImg, Byte refundStatus, Byte commentFlag, Integer straId, BigDecimal perDiscount, Integer isGift, String rGoods, Integer goodsScore, Integer goodsGrowth, BigDecimal discountedGoodsPrice, String discountDetail, Byte fanliType, BigDecimal canCalculateMoney, BigDecimal fanliMoney, BigDecimal discountedTotalPrice, BigDecimal totalFanliMoney, String fanliStrategy, BigDecimal fanliPercent, BigDecimal costPrice, Byte isCardExclusive, String promoteInfo, Integer giftId, Byte isCanReturn, Short reducePriceNum, Byte activityType, Integer activityId, Integer activityRule, Timestamp createTime, Timestamp updateTime, Integer purchaseId, String prescriptionOldCode, String prescriptionCode, Byte medicalAuditType, Byte medicalAuditStatus, Timestamp auditTime) {
        super(OrderGoodsBak.ORDER_GOODS_BAK);

        set(0, id);
        set(1, recId);
        set(2, mainRecId);
        set(3, shopId);
        set(4, orderId);
        set(5, orderSn);
        set(6, goodsId);
        set(7, goodsName);
        set(8, goodsSn);
        set(9, productId);
        set(10, productSn);
        set(11, goodsNumber);
        set(12, marketPrice);
        set(13, goodsPrice);
        set(14, goodsAttr);
        set(15, sendNumber);
        set(16, returnNumber);
        set(17, isReal);
        set(18, goodsAttrId);
        set(19, goodsImg);
        set(20, refundStatus);
        set(21, commentFlag);
        set(22, straId);
        set(23, perDiscount);
        set(24, isGift);
        set(25, rGoods);
        set(26, goodsScore);
        set(27, goodsGrowth);
        set(28, discountedGoodsPrice);
        set(29, discountDetail);
        set(30, fanliType);
        set(31, canCalculateMoney);
        set(32, fanliMoney);
        set(33, discountedTotalPrice);
        set(34, totalFanliMoney);
        set(35, fanliStrategy);
        set(36, fanliPercent);
        set(37, costPrice);
        set(38, isCardExclusive);
        set(39, promoteInfo);
        set(40, giftId);
        set(41, isCanReturn);
        set(42, reducePriceNum);
        set(43, activityType);
        set(44, activityId);
        set(45, activityRule);
        set(46, createTime);
        set(47, updateTime);
        set(48, purchaseId);
        set(49, prescriptionOldCode);
        set(50, prescriptionCode);
        set(51, medicalAuditType);
        set(52, medicalAuditStatus);
        set(53, auditTime);
    }
}

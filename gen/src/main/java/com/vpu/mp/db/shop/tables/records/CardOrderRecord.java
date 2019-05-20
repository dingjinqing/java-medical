/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.CardOrder;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CardOrderRecord extends UpdatableRecordImpl<CardOrderRecord> {

    private static final long serialVersionUID = 1210510469;

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.order_id</code>. 订单id
     */
    public void setOrderId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.order_id</code>. 订单id
     */
    public UInteger getOrderId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.card_id</code>. 会云卡id
     */
    public void setCardId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.card_id</code>. 会云卡id
     */
    public Integer getCardId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.card_no</code>. 会员卡NO
     */
    public void setCardNo(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.card_no</code>. 会员卡NO
     */
    public String getCardNo() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.order_sn</code>. 订单编号
     */
    public void setOrderSn(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.order_sn</code>. 订单编号
     */
    public String getOrderSn() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.user_id</code>. 用户id
     */
    public void setUserId(UInteger value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.user_id</code>. 用户id
     */
    public UInteger getUserId() {
        return (UInteger) get(4);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.order_status</code>. 订单状态
     */
    public void setOrderStatus(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.order_status</code>. 订单状态
     */
    public Byte getOrderStatus() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.order_status_name</code>. 订单状态名称
     */
    public void setOrderStatusName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.order_status_name</code>. 订单状态名称
     */
    public String getOrderStatusName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.invoice_id</code>. 发票id
     */
    public void setInvoiceId(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.invoice_id</code>. 发票id
     */
    public Integer getInvoiceId() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.invoice_detail</code>. 发票内容：json存储
     */
    public void setInvoiceDetail(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.invoice_detail</code>. 发票内容：json存储
     */
    public String getInvoiceDetail() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.add_message</code>. 客户留言
     */
    public void setAddMessage(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.add_message</code>. 客户留言
     */
    public String getAddMessage() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.pay_code</code>. 支付代号
     */
    public void setPayCode(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.pay_code</code>. 支付代号
     */
    public String getPayCode() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.pay_name</code>. 支付名称
     */
    public void setPayName(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.pay_name</code>. 支付名称
     */
    public String getPayName() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.prepay_id</code>. 微信支付Id，用于发送模板消息
     */
    public void setPrepayId(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.prepay_id</code>. 微信支付Id，用于发送模板消息
     */
    public String getPrepayId() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.pay_sn</code>. 支付流水号
     */
    public void setPaySn(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.pay_sn</code>. 支付流水号
     */
    public String getPaySn() {
        return (String) get(13);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.money_paid</code>. 订单应付金额
     */
    public void setMoneyPaid(BigDecimal value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.money_paid</code>. 订单应付金额
     */
    public BigDecimal getMoneyPaid() {
        return (BigDecimal) get(14);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.use_account</code>. 用户消费余额
     */
    public void setUseAccount(BigDecimal value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.use_account</code>. 用户消费余额
     */
    public BigDecimal getUseAccount() {
        return (BigDecimal) get(15);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.use_score</code>. 用户消费余额
     */
    public void setUseScore(BigDecimal value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.use_score</code>. 用户消费余额
     */
    public BigDecimal getUseScore() {
        return (BigDecimal) get(16);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.order_amount</code>. 订单总金额
     */
    public void setOrderAmount(BigDecimal value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.order_amount</code>. 订单总金额
     */
    public BigDecimal getOrderAmount() {
        return (BigDecimal) get(17);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.add_time</code>. 订单提交时间
     */
    public void setAddTime(Timestamp value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.add_time</code>. 订单提交时间
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(18);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.pay_time</code>. 支付时间
     */
    public void setPayTime(Timestamp value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.pay_time</code>. 支付时间
     */
    public Timestamp getPayTime() {
        return (Timestamp) get(19);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.seller_remark</code>. 卖家备注
     */
    public void setSellerRemark(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.seller_remark</code>. 卖家备注
     */
    public String getSellerRemark() {
        return (String) get(20);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.star_flag</code>. 标星订单：0 未标星 1 标星
     */
    public void setStarFlag(Byte value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.star_flag</code>. 标星订单：0 未标星 1 标星
     */
    public Byte getStarFlag() {
        return (Byte) get(21);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.del_flag</code>. 删除
     */
    public void setDelFlag(Byte value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.del_flag</code>. 删除
     */
    public Byte getDelFlag() {
        return (Byte) get(22);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.ali_trade_no</code>. 支付宝交易单号
     */
    public void setAliTradeNo(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.ali_trade_no</code>. 支付宝交易单号
     */
    public String getAliTradeNo() {
        return (String) get(23);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.return_flag</code>. 0:未申请退款，1：退款失败，2：退款成功
     */
    public void setReturnFlag(Byte value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.return_flag</code>. 0:未申请退款，1：退款失败，2：退款成功
     */
    public Byte getReturnFlag() {
        return (Byte) get(24);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.return_score</code>. 退款积分
     */
    public void setReturnScore(BigDecimal value) {
        set(25, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.return_score</code>. 退款积分
     */
    public BigDecimal getReturnScore() {
        return (BigDecimal) get(25);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.return_account</code>. 退款余额
     */
    public void setReturnAccount(BigDecimal value) {
        set(26, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.return_account</code>. 退款余额
     */
    public BigDecimal getReturnAccount() {
        return (BigDecimal) get(26);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.return_money</code>. 退款余额
     */
    public void setReturnMoney(BigDecimal value) {
        set(27, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.return_money</code>. 退款余额
     */
    public BigDecimal getReturnMoney() {
        return (BigDecimal) get(27);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_order.return_time</code>. 退款时间
     */
    public void setReturnTime(Timestamp value) {
        set(28, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_order.return_time</code>. 退款时间
     */
    public Timestamp getReturnTime() {
        return (Timestamp) get(28);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UInteger> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CardOrderRecord
     */
    public CardOrderRecord() {
        super(CardOrder.CARD_ORDER);
    }

    /**
     * Create a detached, initialised CardOrderRecord
     */
    public CardOrderRecord(UInteger orderId, Integer cardId, String cardNo, String orderSn, UInteger userId, Byte orderStatus, String orderStatusName, Integer invoiceId, String invoiceDetail, String addMessage, String payCode, String payName, String prepayId, String paySn, BigDecimal moneyPaid, BigDecimal useAccount, BigDecimal useScore, BigDecimal orderAmount, Timestamp addTime, Timestamp payTime, String sellerRemark, Byte starFlag, Byte delFlag, String aliTradeNo, Byte returnFlag, BigDecimal returnScore, BigDecimal returnAccount, BigDecimal returnMoney, Timestamp returnTime) {
        super(CardOrder.CARD_ORDER);

        set(0, orderId);
        set(1, cardId);
        set(2, cardNo);
        set(3, orderSn);
        set(4, userId);
        set(5, orderStatus);
        set(6, orderStatusName);
        set(7, invoiceId);
        set(8, invoiceDetail);
        set(9, addMessage);
        set(10, payCode);
        set(11, payName);
        set(12, prepayId);
        set(13, paySn);
        set(14, moneyPaid);
        set(15, useAccount);
        set(16, useScore);
        set(17, orderAmount);
        set(18, addTime);
        set(19, payTime);
        set(20, sellerRemark);
        set(21, starFlag);
        set(22, delFlag);
        set(23, aliTradeNo);
        set(24, returnFlag);
        set(25, returnScore);
        set(26, returnAccount);
        set(27, returnMoney);
        set(28, returnTime);
    }
}

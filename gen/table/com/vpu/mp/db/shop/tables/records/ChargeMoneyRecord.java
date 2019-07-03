/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.ChargeMoney;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record18;
import org.jooq.Row18;
import org.jooq.impl.UpdatableRecordImpl;


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
public class ChargeMoneyRecord extends UpdatableRecordImpl<ChargeMoneyRecord> implements Record18<Integer, Integer, Integer, Timestamp, BigDecimal, Short, String, Byte, String, String, String, String, Byte, BigDecimal, Byte, String, String, Short> {

    private static final long serialVersionUID = 2000555433;

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.user_id</code>. 用户id
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.user_id</code>. 用户id
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.card_id</code>. 会员卡id
     */
    public void setCardId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.card_id</code>. 会员卡id
     */
    public Integer getCardId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.add_time</code>. 充值时间
     */
    public void setAddTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.add_time</code>. 充值时间
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.charge</code>. 充值的钱
     */
    public void setCharge(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.charge</code>. 充值的钱
     */
    public BigDecimal getCharge() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.count</code>. 充值次数
     */
    public void setCount(Short value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.count</code>. 充值次数
     */
    public Short getCount() {
        return (Short) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.payment</code>. 支付方式
     */
    public void setPayment(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.payment</code>. 支付方式
     */
    public String getPayment() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.type</code>. 消费类型 0是普通卡 1限次卡
     */
    public void setType(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.type</code>. 消费类型 0是普通卡 1限次卡
     */
    public Byte getType() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.reason</code>. 充值原因
     */
    public void setReason(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.reason</code>. 充值原因
     */
    public String getReason() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.prepay_id</code>. 微信支付Id，用于发送模板消息
     */
    public void setPrepayId(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.prepay_id</code>. 微信支付Id，用于发送模板消息
     */
    public String getPrepayId() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.message</code>. 备注
     */
    public void setMessage(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.message</code>. 备注
     */
    public String getMessage() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.order_sn</code>.
     */
    public void setOrderSn(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.order_sn</code>.
     */
    public String getOrderSn() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.order_status</code>. 订单状态 0：待支付，1：已取消，2：已完成
     */
    public void setOrderStatus(Byte value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.order_status</code>. 订单状态 0：待支付，1：已取消，2：已完成
     */
    public Byte getOrderStatus() {
        return (Byte) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.money_paid</code>. 订单应付金额
     */
    public void setMoneyPaid(BigDecimal value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.money_paid</code>. 订单应付金额
     */
    public BigDecimal getMoneyPaid() {
        return (BigDecimal) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.charge_type</code>. 0按规则 1自定义
     */
    public void setChargeType(Byte value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.charge_type</code>. 0按规则 1自定义
     */
    public Byte getChargeType() {
        return (Byte) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.card_no</code>. 会员卡号
     */
    public void setCardNo(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.card_no</code>. 会员卡号
     */
    public String getCardNo() {
        return (String) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.ali_trade_no</code>. 支付宝交易单号
     */
    public void setAliTradeNo(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.ali_trade_no</code>. 支付宝交易单号
     */
    public String getAliTradeNo() {
        return (String) get(16);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_charge_money.exchang_count</code>. 兑换充值次数
     */
    public void setExchangCount(Short value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_charge_money.exchang_count</code>. 兑换充值次数
     */
    public Short getExchangCount() {
        return (Short) get(17);
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
    // Record18 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row18<Integer, Integer, Integer, Timestamp, BigDecimal, Short, String, Byte, String, String, String, String, Byte, BigDecimal, Byte, String, String, Short> fieldsRow() {
        return (Row18) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row18<Integer, Integer, Integer, Timestamp, BigDecimal, Short, String, Byte, String, String, String, String, Byte, BigDecimal, Byte, String, String, Short> valuesRow() {
        return (Row18) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ChargeMoney.CHARGE_MONEY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ChargeMoney.CHARGE_MONEY.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ChargeMoney.CHARGE_MONEY.CARD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return ChargeMoney.CHARGE_MONEY.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field5() {
        return ChargeMoney.CHARGE_MONEY.CHARGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field6() {
        return ChargeMoney.CHARGE_MONEY.COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return ChargeMoney.CHARGE_MONEY.PAYMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return ChargeMoney.CHARGE_MONEY.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return ChargeMoney.CHARGE_MONEY.REASON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return ChargeMoney.CHARGE_MONEY.PREPAY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return ChargeMoney.CHARGE_MONEY.MESSAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return ChargeMoney.CHARGE_MONEY.ORDER_SN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field13() {
        return ChargeMoney.CHARGE_MONEY.ORDER_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field14() {
        return ChargeMoney.CHARGE_MONEY.MONEY_PAID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field15() {
        return ChargeMoney.CHARGE_MONEY.CHARGE_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field16() {
        return ChargeMoney.CHARGE_MONEY.CARD_NO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field17() {
        return ChargeMoney.CHARGE_MONEY.ALI_TRADE_NO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field18() {
        return ChargeMoney.CHARGE_MONEY.EXCHANG_COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getCardId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component5() {
        return getCharge();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component6() {
        return getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getPayment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component8() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getReason();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getPrepayId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component13() {
        return getOrderStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component14() {
        return getMoneyPaid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component15() {
        return getChargeType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component16() {
        return getCardNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component17() {
        return getAliTradeNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component18() {
        return getExchangCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getCardId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value5() {
        return getCharge();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value6() {
        return getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getPayment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getReason();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getPrepayId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value13() {
        return getOrderStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value14() {
        return getMoneyPaid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value15() {
        return getChargeType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value16() {
        return getCardNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value17() {
        return getAliTradeNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value18() {
        return getExchangCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value3(Integer value) {
        setCardId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value4(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value5(BigDecimal value) {
        setCharge(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value6(Short value) {
        setCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value7(String value) {
        setPayment(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value8(Byte value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value9(String value) {
        setReason(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value10(String value) {
        setPrepayId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value11(String value) {
        setMessage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value12(String value) {
        setOrderSn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value13(Byte value) {
        setOrderStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value14(BigDecimal value) {
        setMoneyPaid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value15(Byte value) {
        setChargeType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value16(String value) {
        setCardNo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value17(String value) {
        setAliTradeNo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord value18(Short value) {
        setExchangCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoneyRecord values(Integer value1, Integer value2, Integer value3, Timestamp value4, BigDecimal value5, Short value6, String value7, Byte value8, String value9, String value10, String value11, String value12, Byte value13, BigDecimal value14, Byte value15, String value16, String value17, Short value18) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ChargeMoneyRecord
     */
    public ChargeMoneyRecord() {
        super(ChargeMoney.CHARGE_MONEY);
    }

    /**
     * Create a detached, initialised ChargeMoneyRecord
     */
    public ChargeMoneyRecord(Integer id, Integer userId, Integer cardId, Timestamp addTime, BigDecimal charge, Short count, String payment, Byte type, String reason, String prepayId, String message, String orderSn, Byte orderStatus, BigDecimal moneyPaid, Byte chargeType, String cardNo, String aliTradeNo, Short exchangCount) {
        super(ChargeMoney.CHARGE_MONEY);

        set(0, id);
        set(1, userId);
        set(2, cardId);
        set(3, addTime);
        set(4, charge);
        set(5, count);
        set(6, payment);
        set(7, type);
        set(8, reason);
        set(9, prepayId);
        set(10, message);
        set(11, orderSn);
        set(12, orderStatus);
        set(13, moneyPaid);
        set(14, chargeType);
        set(15, cardNo);
        set(16, aliTradeNo);
        set(17, exchangCount);
    }
}

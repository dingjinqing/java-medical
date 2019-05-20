/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.StatisticsShop;

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
public class StatisticsShopRecord extends UpdatableRecordImpl<StatisticsShopRecord> {

    private static final long serialVersionUID = -1233179509;

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.ref_date</code>. 统计数据时间
     */
    public void setRefDate(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.ref_date</code>. 统计数据时间
     */
    public Timestamp getRefDate() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.type</code>. 统计类型 1天 7周 30月 3季度
     */
    public void setType(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.type</code>. 统计类型 1天 7周 30月 3季度
     */
    public Byte getType() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.week</code>. 第几周
     */
    public void setWeek(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.week</code>. 第几周
     */
    public Byte getWeek() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.months</code>. 第几周
     */
    public void setMonths(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.months</code>. 第几周
     */
    public Byte getMonths() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.quarter</code>. 第几季度
     */
    public void setQuarter(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.quarter</code>. 第几季度
     */
    public Byte getQuarter() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.new_user</code>. 注册用户
     */
    public void setNewUser(UInteger value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.new_user</code>. 注册用户
     */
    public UInteger getNewUser() {
        return (UInteger) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.order_num</code>. 订单数
     */
    public void setOrderNum(UInteger value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.order_num</code>. 订单数
     */
    public UInteger getOrderNum() {
        return (UInteger) get(8);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.wx_order_num</code>. 微信支付订单数
     */
    public void setWxOrderNum(UInteger value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.wx_order_num</code>. 微信支付订单数
     */
    public UInteger getWxOrderNum() {
        return (UInteger) get(9);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.cod_order_num</code>. 货到付款订单数
     */
    public void setCodOrderNum(UInteger value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.cod_order_num</code>. 货到付款订单数
     */
    public UInteger getCodOrderNum() {
        return (UInteger) get(10);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.balance_order_num</code>. 余额订单
     */
    public void setBalanceOrderNum(UInteger value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.balance_order_num</code>. 余额订单
     */
    public UInteger getBalanceOrderNum() {
        return (UInteger) get(11);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.score_order_num</code>. 积分订单
     */
    public void setScoreOrderNum(UInteger value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.score_order_num</code>. 积分订单
     */
    public UInteger getScoreOrderNum() {
        return (UInteger) get(12);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.wx_money</code>. 微信支付总额
     */
    public void setWxMoney(BigDecimal value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.wx_money</code>. 微信支付总额
     */
    public BigDecimal getWxMoney() {
        return (BigDecimal) get(13);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.card_money</code>. 卡余额总额
     */
    public void setCardMoney(BigDecimal value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.card_money</code>. 卡余额总额
     */
    public BigDecimal getCardMoney() {
        return (BigDecimal) get(14);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.balance_money</code>. 余额总额
     */
    public void setBalanceMoney(BigDecimal value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.balance_money</code>. 余额总额
     */
    public BigDecimal getBalanceMoney() {
        return (BigDecimal) get(15);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.score_money</code>. 积分总额
     */
    public void setScoreMoney(BigDecimal value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.score_money</code>. 积分总额
     */
    public BigDecimal getScoreMoney() {
        return (BigDecimal) get(16);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.order_user_num</code>. 下单用户
     */
    public void setOrderUserNum(UInteger value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.order_user_num</code>. 下单用户
     */
    public UInteger getOrderUserNum() {
        return (UInteger) get(17);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.wx_user_num</code>. 微信下单用户
     */
    public void setWxUserNum(UInteger value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.wx_user_num</code>. 微信下单用户
     */
    public UInteger getWxUserNum() {
        return (UInteger) get(18);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.cod_user_num</code>. 货到付款用户
     */
    public void setCodUserNum(UInteger value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.cod_user_num</code>. 货到付款用户
     */
    public UInteger getCodUserNum() {
        return (UInteger) get(19);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.balance_user_num</code>. 余额购买用户
     */
    public void setBalanceUserNum(UInteger value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.balance_user_num</code>. 余额购买用户
     */
    public UInteger getBalanceUserNum() {
        return (UInteger) get(20);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.score_user_num</code>. 积分购买用户
     */
    public void setScoreUserNum(UInteger value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.score_user_num</code>. 积分购买用户
     */
    public UInteger getScoreUserNum() {
        return (UInteger) get(21);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.order_goods_num</code>. 订单商品数量
     */
    public void setOrderGoodsNum(UInteger value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.order_goods_num</code>. 订单商品数量
     */
    public UInteger getOrderGoodsNum() {
        return (UInteger) get(22);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.wx_goods_num</code>. 微信支付商品
     */
    public void setWxGoodsNum(UInteger value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.wx_goods_num</code>. 微信支付商品
     */
    public UInteger getWxGoodsNum() {
        return (UInteger) get(23);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.cod_goods_num</code>. 货到付款商品
     */
    public void setCodGoodsNum(UInteger value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.cod_goods_num</code>. 货到付款商品
     */
    public UInteger getCodGoodsNum() {
        return (UInteger) get(24);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.balance_goods_num</code>. 余额购买商品
     */
    public void setBalanceGoodsNum(UInteger value) {
        set(25, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.balance_goods_num</code>. 余额购买商品
     */
    public UInteger getBalanceGoodsNum() {
        return (UInteger) get(25);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.score_goods_num</code>. 积分购买商品
     */
    public void setScoreGoodsNum(UInteger value) {
        set(26, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.score_goods_num</code>. 积分购买商品
     */
    public UInteger getScoreGoodsNum() {
        return (UInteger) get(26);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.wx_order_pay</code>. 微信金额
     */
    public void setWxOrderPay(BigDecimal value) {
        set(27, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.wx_order_pay</code>. 微信金额
     */
    public BigDecimal getWxOrderPay() {
        return (BigDecimal) get(27);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.cod_order_pay</code>. 货到付款金额
     */
    public void setCodOrderPay(BigDecimal value) {
        set(28, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.cod_order_pay</code>. 货到付款金额
     */
    public BigDecimal getCodOrderPay() {
        return (BigDecimal) get(28);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.balance_order_pay</code>. 余额金额
     */
    public void setBalanceOrderPay(BigDecimal value) {
        set(29, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.balance_order_pay</code>. 余额金额
     */
    public BigDecimal getBalanceOrderPay() {
        return (BigDecimal) get(29);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.score_order_pay</code>. 积分金额
     */
    public void setScoreOrderPay(BigDecimal value) {
        set(30, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.score_order_pay</code>. 积分金额
     */
    public BigDecimal getScoreOrderPay() {
        return (BigDecimal) get(30);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.wx_order_balance</code>. 微信支付余额
     */
    public void setWxOrderBalance(BigDecimal value) {
        set(31, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.wx_order_balance</code>. 微信支付余额
     */
    public BigDecimal getWxOrderBalance() {
        return (BigDecimal) get(31);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.cod_order_balance</code>. 货到付款支付余额
     */
    public void setCodOrderBalance(BigDecimal value) {
        set(32, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.cod_order_balance</code>. 货到付款支付余额
     */
    public BigDecimal getCodOrderBalance() {
        return (BigDecimal) get(32);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.balance_order_balance</code>. 余额支付余额
     */
    public void setBalanceOrderBalance(BigDecimal value) {
        set(33, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.balance_order_balance</code>. 余额支付余额
     */
    public BigDecimal getBalanceOrderBalance() {
        return (BigDecimal) get(33);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.score_order_balance</code>. 积分支付余额
     */
    public void setScoreOrderBalance(BigDecimal value) {
        set(34, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.score_order_balance</code>. 积分支付余额
     */
    public BigDecimal getScoreOrderBalance() {
        return (BigDecimal) get(34);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.wx_order_card</code>. 微信支付卡余额
     */
    public void setWxOrderCard(BigDecimal value) {
        set(35, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.wx_order_card</code>. 微信支付卡余额
     */
    public BigDecimal getWxOrderCard() {
        return (BigDecimal) get(35);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.cod_order_card</code>. 货到付款支付卡余额
     */
    public void setCodOrderCard(BigDecimal value) {
        set(36, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.cod_order_card</code>. 货到付款支付卡余额
     */
    public BigDecimal getCodOrderCard() {
        return (BigDecimal) get(36);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.balance_order_card</code>. 余额支付卡余额
     */
    public void setBalanceOrderCard(BigDecimal value) {
        set(37, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.balance_order_card</code>. 余额支付卡余额
     */
    public BigDecimal getBalanceOrderCard() {
        return (BigDecimal) get(37);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.score_order_card</code>. 积分支付卡余额
     */
    public void setScoreOrderCard(BigDecimal value) {
        set(38, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.score_order_card</code>. 积分支付卡余额
     */
    public BigDecimal getScoreOrderCard() {
        return (BigDecimal) get(38);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.wx_order_score</code>. 微信支付积分
     */
    public void setWxOrderScore(UInteger value) {
        set(39, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.wx_order_score</code>. 微信支付积分
     */
    public UInteger getWxOrderScore() {
        return (UInteger) get(39);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.cod_order_score</code>. 货到付款支付积分
     */
    public void setCodOrderScore(UInteger value) {
        set(40, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.cod_order_score</code>. 货到付款支付积分
     */
    public UInteger getCodOrderScore() {
        return (UInteger) get(40);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.balance_order_score</code>. 余额支付积分
     */
    public void setBalanceOrderScore(UInteger value) {
        set(41, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.balance_order_score</code>. 余额支付积分
     */
    public UInteger getBalanceOrderScore() {
        return (UInteger) get(41);
    }

    /**
     * Setter for <code>mini_main.b2c_statistics_shop.score_order_score</code>. 积分支付卡积分
     */
    public void setScoreOrderScore(UInteger value) {
        set(42, value);
    }

    /**
     * Getter for <code>mini_main.b2c_statistics_shop.score_order_score</code>. 积分支付卡积分
     */
    public UInteger getScoreOrderScore() {
        return (UInteger) get(42);
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
     * Create a detached StatisticsShopRecord
     */
    public StatisticsShopRecord() {
        super(StatisticsShop.STATISTICS_SHOP);
    }

    /**
     * Create a detached, initialised StatisticsShopRecord
     */
    public StatisticsShopRecord(UInteger id, Integer shopId, Timestamp refDate, Byte type, Byte week, Byte months, Byte quarter, UInteger newUser, UInteger orderNum, UInteger wxOrderNum, UInteger codOrderNum, UInteger balanceOrderNum, UInteger scoreOrderNum, BigDecimal wxMoney, BigDecimal cardMoney, BigDecimal balanceMoney, BigDecimal scoreMoney, UInteger orderUserNum, UInteger wxUserNum, UInteger codUserNum, UInteger balanceUserNum, UInteger scoreUserNum, UInteger orderGoodsNum, UInteger wxGoodsNum, UInteger codGoodsNum, UInteger balanceGoodsNum, UInteger scoreGoodsNum, BigDecimal wxOrderPay, BigDecimal codOrderPay, BigDecimal balanceOrderPay, BigDecimal scoreOrderPay, BigDecimal wxOrderBalance, BigDecimal codOrderBalance, BigDecimal balanceOrderBalance, BigDecimal scoreOrderBalance, BigDecimal wxOrderCard, BigDecimal codOrderCard, BigDecimal balanceOrderCard, BigDecimal scoreOrderCard, UInteger wxOrderScore, UInteger codOrderScore, UInteger balanceOrderScore, UInteger scoreOrderScore) {
        super(StatisticsShop.STATISTICS_SHOP);

        set(0, id);
        set(1, shopId);
        set(2, refDate);
        set(3, type);
        set(4, week);
        set(5, months);
        set(6, quarter);
        set(7, newUser);
        set(8, orderNum);
        set(9, wxOrderNum);
        set(10, codOrderNum);
        set(11, balanceOrderNum);
        set(12, scoreOrderNum);
        set(13, wxMoney);
        set(14, cardMoney);
        set(15, balanceMoney);
        set(16, scoreMoney);
        set(17, orderUserNum);
        set(18, wxUserNum);
        set(19, codUserNum);
        set(20, balanceUserNum);
        set(21, scoreUserNum);
        set(22, orderGoodsNum);
        set(23, wxGoodsNum);
        set(24, codGoodsNum);
        set(25, balanceGoodsNum);
        set(26, scoreGoodsNum);
        set(27, wxOrderPay);
        set(28, codOrderPay);
        set(29, balanceOrderPay);
        set(30, scoreOrderPay);
        set(31, wxOrderBalance);
        set(32, codOrderBalance);
        set(33, balanceOrderBalance);
        set(34, scoreOrderBalance);
        set(35, wxOrderCard);
        set(36, codOrderCard);
        set(37, balanceOrderCard);
        set(38, scoreOrderCard);
        set(39, wxOrderScore);
        set(40, codOrderScore);
        set(41, balanceOrderScore);
        set(42, scoreOrderScore);
    }
}

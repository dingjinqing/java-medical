/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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
public class StatisticsShop implements Serializable {

    private static final long serialVersionUID = 345024392;

    private Integer    id;
    private Integer    shopId;
    private Timestamp  refDate;
    private Byte       type;
    private Byte       week;
    private Byte       months;
    private Byte       quarter;
    private Integer    newUser;
    private Integer    orderNum;
    private Integer    wxOrderNum;
    private Integer    codOrderNum;
    private Integer    balanceOrderNum;
    private Integer    scoreOrderNum;
    private BigDecimal wxMoney;
    private BigDecimal cardMoney;
    private BigDecimal balanceMoney;
    private BigDecimal scoreMoney;
    private Integer    orderUserNum;
    private Integer    wxUserNum;
    private Integer    codUserNum;
    private Integer    balanceUserNum;
    private Integer    scoreUserNum;
    private Integer    orderGoodsNum;
    private Integer    wxGoodsNum;
    private Integer    codGoodsNum;
    private Integer    balanceGoodsNum;
    private Integer    scoreGoodsNum;
    private BigDecimal wxOrderPay;
    private BigDecimal codOrderPay;
    private BigDecimal balanceOrderPay;
    private BigDecimal scoreOrderPay;
    private BigDecimal wxOrderBalance;
    private BigDecimal codOrderBalance;
    private BigDecimal balanceOrderBalance;
    private BigDecimal scoreOrderBalance;
    private BigDecimal wxOrderCard;
    private BigDecimal codOrderCard;
    private BigDecimal balanceOrderCard;
    private BigDecimal scoreOrderCard;
    private Integer    wxOrderScore;
    private Integer    codOrderScore;
    private Integer    balanceOrderScore;
    private Integer    scoreOrderScore;

    public StatisticsShop() {}

    public StatisticsShop(StatisticsShop value) {
        this.id = value.id;
        this.shopId = value.shopId;
        this.refDate = value.refDate;
        this.type = value.type;
        this.week = value.week;
        this.months = value.months;
        this.quarter = value.quarter;
        this.newUser = value.newUser;
        this.orderNum = value.orderNum;
        this.wxOrderNum = value.wxOrderNum;
        this.codOrderNum = value.codOrderNum;
        this.balanceOrderNum = value.balanceOrderNum;
        this.scoreOrderNum = value.scoreOrderNum;
        this.wxMoney = value.wxMoney;
        this.cardMoney = value.cardMoney;
        this.balanceMoney = value.balanceMoney;
        this.scoreMoney = value.scoreMoney;
        this.orderUserNum = value.orderUserNum;
        this.wxUserNum = value.wxUserNum;
        this.codUserNum = value.codUserNum;
        this.balanceUserNum = value.balanceUserNum;
        this.scoreUserNum = value.scoreUserNum;
        this.orderGoodsNum = value.orderGoodsNum;
        this.wxGoodsNum = value.wxGoodsNum;
        this.codGoodsNum = value.codGoodsNum;
        this.balanceGoodsNum = value.balanceGoodsNum;
        this.scoreGoodsNum = value.scoreGoodsNum;
        this.wxOrderPay = value.wxOrderPay;
        this.codOrderPay = value.codOrderPay;
        this.balanceOrderPay = value.balanceOrderPay;
        this.scoreOrderPay = value.scoreOrderPay;
        this.wxOrderBalance = value.wxOrderBalance;
        this.codOrderBalance = value.codOrderBalance;
        this.balanceOrderBalance = value.balanceOrderBalance;
        this.scoreOrderBalance = value.scoreOrderBalance;
        this.wxOrderCard = value.wxOrderCard;
        this.codOrderCard = value.codOrderCard;
        this.balanceOrderCard = value.balanceOrderCard;
        this.scoreOrderCard = value.scoreOrderCard;
        this.wxOrderScore = value.wxOrderScore;
        this.codOrderScore = value.codOrderScore;
        this.balanceOrderScore = value.balanceOrderScore;
        this.scoreOrderScore = value.scoreOrderScore;
    }

    public StatisticsShop(
        Integer    id,
        Integer    shopId,
        Timestamp  refDate,
        Byte       type,
        Byte       week,
        Byte       months,
        Byte       quarter,
        Integer    newUser,
        Integer    orderNum,
        Integer    wxOrderNum,
        Integer    codOrderNum,
        Integer    balanceOrderNum,
        Integer    scoreOrderNum,
        BigDecimal wxMoney,
        BigDecimal cardMoney,
        BigDecimal balanceMoney,
        BigDecimal scoreMoney,
        Integer    orderUserNum,
        Integer    wxUserNum,
        Integer    codUserNum,
        Integer    balanceUserNum,
        Integer    scoreUserNum,
        Integer    orderGoodsNum,
        Integer    wxGoodsNum,
        Integer    codGoodsNum,
        Integer    balanceGoodsNum,
        Integer    scoreGoodsNum,
        BigDecimal wxOrderPay,
        BigDecimal codOrderPay,
        BigDecimal balanceOrderPay,
        BigDecimal scoreOrderPay,
        BigDecimal wxOrderBalance,
        BigDecimal codOrderBalance,
        BigDecimal balanceOrderBalance,
        BigDecimal scoreOrderBalance,
        BigDecimal wxOrderCard,
        BigDecimal codOrderCard,
        BigDecimal balanceOrderCard,
        BigDecimal scoreOrderCard,
        Integer    wxOrderScore,
        Integer    codOrderScore,
        Integer    balanceOrderScore,
        Integer    scoreOrderScore
    ) {
        this.id = id;
        this.shopId = shopId;
        this.refDate = refDate;
        this.type = type;
        this.week = week;
        this.months = months;
        this.quarter = quarter;
        this.newUser = newUser;
        this.orderNum = orderNum;
        this.wxOrderNum = wxOrderNum;
        this.codOrderNum = codOrderNum;
        this.balanceOrderNum = balanceOrderNum;
        this.scoreOrderNum = scoreOrderNum;
        this.wxMoney = wxMoney;
        this.cardMoney = cardMoney;
        this.balanceMoney = balanceMoney;
        this.scoreMoney = scoreMoney;
        this.orderUserNum = orderUserNum;
        this.wxUserNum = wxUserNum;
        this.codUserNum = codUserNum;
        this.balanceUserNum = balanceUserNum;
        this.scoreUserNum = scoreUserNum;
        this.orderGoodsNum = orderGoodsNum;
        this.wxGoodsNum = wxGoodsNum;
        this.codGoodsNum = codGoodsNum;
        this.balanceGoodsNum = balanceGoodsNum;
        this.scoreGoodsNum = scoreGoodsNum;
        this.wxOrderPay = wxOrderPay;
        this.codOrderPay = codOrderPay;
        this.balanceOrderPay = balanceOrderPay;
        this.scoreOrderPay = scoreOrderPay;
        this.wxOrderBalance = wxOrderBalance;
        this.codOrderBalance = codOrderBalance;
        this.balanceOrderBalance = balanceOrderBalance;
        this.scoreOrderBalance = scoreOrderBalance;
        this.wxOrderCard = wxOrderCard;
        this.codOrderCard = codOrderCard;
        this.balanceOrderCard = balanceOrderCard;
        this.scoreOrderCard = scoreOrderCard;
        this.wxOrderScore = wxOrderScore;
        this.codOrderScore = codOrderScore;
        this.balanceOrderScore = balanceOrderScore;
        this.scoreOrderScore = scoreOrderScore;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return this.shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Timestamp getRefDate() {
        return this.refDate;
    }

    public void setRefDate(Timestamp refDate) {
        this.refDate = refDate;
    }

    public Byte getType() {
        return this.type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getWeek() {
        return this.week;
    }

    public void setWeek(Byte week) {
        this.week = week;
    }

    public Byte getMonths() {
        return this.months;
    }

    public void setMonths(Byte months) {
        this.months = months;
    }

    public Byte getQuarter() {
        return this.quarter;
    }

    public void setQuarter(Byte quarter) {
        this.quarter = quarter;
    }

    public Integer getNewUser() {
        return this.newUser;
    }

    public void setNewUser(Integer newUser) {
        this.newUser = newUser;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getWxOrderNum() {
        return this.wxOrderNum;
    }

    public void setWxOrderNum(Integer wxOrderNum) {
        this.wxOrderNum = wxOrderNum;
    }

    public Integer getCodOrderNum() {
        return this.codOrderNum;
    }

    public void setCodOrderNum(Integer codOrderNum) {
        this.codOrderNum = codOrderNum;
    }

    public Integer getBalanceOrderNum() {
        return this.balanceOrderNum;
    }

    public void setBalanceOrderNum(Integer balanceOrderNum) {
        this.balanceOrderNum = balanceOrderNum;
    }

    public Integer getScoreOrderNum() {
        return this.scoreOrderNum;
    }

    public void setScoreOrderNum(Integer scoreOrderNum) {
        this.scoreOrderNum = scoreOrderNum;
    }

    public BigDecimal getWxMoney() {
        return this.wxMoney;
    }

    public void setWxMoney(BigDecimal wxMoney) {
        this.wxMoney = wxMoney;
    }

    public BigDecimal getCardMoney() {
        return this.cardMoney;
    }

    public void setCardMoney(BigDecimal cardMoney) {
        this.cardMoney = cardMoney;
    }

    public BigDecimal getBalanceMoney() {
        return this.balanceMoney;
    }

    public void setBalanceMoney(BigDecimal balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public BigDecimal getScoreMoney() {
        return this.scoreMoney;
    }

    public void setScoreMoney(BigDecimal scoreMoney) {
        this.scoreMoney = scoreMoney;
    }

    public Integer getOrderUserNum() {
        return this.orderUserNum;
    }

    public void setOrderUserNum(Integer orderUserNum) {
        this.orderUserNum = orderUserNum;
    }

    public Integer getWxUserNum() {
        return this.wxUserNum;
    }

    public void setWxUserNum(Integer wxUserNum) {
        this.wxUserNum = wxUserNum;
    }

    public Integer getCodUserNum() {
        return this.codUserNum;
    }

    public void setCodUserNum(Integer codUserNum) {
        this.codUserNum = codUserNum;
    }

    public Integer getBalanceUserNum() {
        return this.balanceUserNum;
    }

    public void setBalanceUserNum(Integer balanceUserNum) {
        this.balanceUserNum = balanceUserNum;
    }

    public Integer getScoreUserNum() {
        return this.scoreUserNum;
    }

    public void setScoreUserNum(Integer scoreUserNum) {
        this.scoreUserNum = scoreUserNum;
    }

    public Integer getOrderGoodsNum() {
        return this.orderGoodsNum;
    }

    public void setOrderGoodsNum(Integer orderGoodsNum) {
        this.orderGoodsNum = orderGoodsNum;
    }

    public Integer getWxGoodsNum() {
        return this.wxGoodsNum;
    }

    public void setWxGoodsNum(Integer wxGoodsNum) {
        this.wxGoodsNum = wxGoodsNum;
    }

    public Integer getCodGoodsNum() {
        return this.codGoodsNum;
    }

    public void setCodGoodsNum(Integer codGoodsNum) {
        this.codGoodsNum = codGoodsNum;
    }

    public Integer getBalanceGoodsNum() {
        return this.balanceGoodsNum;
    }

    public void setBalanceGoodsNum(Integer balanceGoodsNum) {
        this.balanceGoodsNum = balanceGoodsNum;
    }

    public Integer getScoreGoodsNum() {
        return this.scoreGoodsNum;
    }

    public void setScoreGoodsNum(Integer scoreGoodsNum) {
        this.scoreGoodsNum = scoreGoodsNum;
    }

    public BigDecimal getWxOrderPay() {
        return this.wxOrderPay;
    }

    public void setWxOrderPay(BigDecimal wxOrderPay) {
        this.wxOrderPay = wxOrderPay;
    }

    public BigDecimal getCodOrderPay() {
        return this.codOrderPay;
    }

    public void setCodOrderPay(BigDecimal codOrderPay) {
        this.codOrderPay = codOrderPay;
    }

    public BigDecimal getBalanceOrderPay() {
        return this.balanceOrderPay;
    }

    public void setBalanceOrderPay(BigDecimal balanceOrderPay) {
        this.balanceOrderPay = balanceOrderPay;
    }

    public BigDecimal getScoreOrderPay() {
        return this.scoreOrderPay;
    }

    public void setScoreOrderPay(BigDecimal scoreOrderPay) {
        this.scoreOrderPay = scoreOrderPay;
    }

    public BigDecimal getWxOrderBalance() {
        return this.wxOrderBalance;
    }

    public void setWxOrderBalance(BigDecimal wxOrderBalance) {
        this.wxOrderBalance = wxOrderBalance;
    }

    public BigDecimal getCodOrderBalance() {
        return this.codOrderBalance;
    }

    public void setCodOrderBalance(BigDecimal codOrderBalance) {
        this.codOrderBalance = codOrderBalance;
    }

    public BigDecimal getBalanceOrderBalance() {
        return this.balanceOrderBalance;
    }

    public void setBalanceOrderBalance(BigDecimal balanceOrderBalance) {
        this.balanceOrderBalance = balanceOrderBalance;
    }

    public BigDecimal getScoreOrderBalance() {
        return this.scoreOrderBalance;
    }

    public void setScoreOrderBalance(BigDecimal scoreOrderBalance) {
        this.scoreOrderBalance = scoreOrderBalance;
    }

    public BigDecimal getWxOrderCard() {
        return this.wxOrderCard;
    }

    public void setWxOrderCard(BigDecimal wxOrderCard) {
        this.wxOrderCard = wxOrderCard;
    }

    public BigDecimal getCodOrderCard() {
        return this.codOrderCard;
    }

    public void setCodOrderCard(BigDecimal codOrderCard) {
        this.codOrderCard = codOrderCard;
    }

    public BigDecimal getBalanceOrderCard() {
        return this.balanceOrderCard;
    }

    public void setBalanceOrderCard(BigDecimal balanceOrderCard) {
        this.balanceOrderCard = balanceOrderCard;
    }

    public BigDecimal getScoreOrderCard() {
        return this.scoreOrderCard;
    }

    public void setScoreOrderCard(BigDecimal scoreOrderCard) {
        this.scoreOrderCard = scoreOrderCard;
    }

    public Integer getWxOrderScore() {
        return this.wxOrderScore;
    }

    public void setWxOrderScore(Integer wxOrderScore) {
        this.wxOrderScore = wxOrderScore;
    }

    public Integer getCodOrderScore() {
        return this.codOrderScore;
    }

    public void setCodOrderScore(Integer codOrderScore) {
        this.codOrderScore = codOrderScore;
    }

    public Integer getBalanceOrderScore() {
        return this.balanceOrderScore;
    }

    public void setBalanceOrderScore(Integer balanceOrderScore) {
        this.balanceOrderScore = balanceOrderScore;
    }

    public Integer getScoreOrderScore() {
        return this.scoreOrderScore;
    }

    public void setScoreOrderScore(Integer scoreOrderScore) {
        this.scoreOrderScore = scoreOrderScore;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("StatisticsShop (");

        sb.append(id);
        sb.append(", ").append(shopId);
        sb.append(", ").append(refDate);
        sb.append(", ").append(type);
        sb.append(", ").append(week);
        sb.append(", ").append(months);
        sb.append(", ").append(quarter);
        sb.append(", ").append(newUser);
        sb.append(", ").append(orderNum);
        sb.append(", ").append(wxOrderNum);
        sb.append(", ").append(codOrderNum);
        sb.append(", ").append(balanceOrderNum);
        sb.append(", ").append(scoreOrderNum);
        sb.append(", ").append(wxMoney);
        sb.append(", ").append(cardMoney);
        sb.append(", ").append(balanceMoney);
        sb.append(", ").append(scoreMoney);
        sb.append(", ").append(orderUserNum);
        sb.append(", ").append(wxUserNum);
        sb.append(", ").append(codUserNum);
        sb.append(", ").append(balanceUserNum);
        sb.append(", ").append(scoreUserNum);
        sb.append(", ").append(orderGoodsNum);
        sb.append(", ").append(wxGoodsNum);
        sb.append(", ").append(codGoodsNum);
        sb.append(", ").append(balanceGoodsNum);
        sb.append(", ").append(scoreGoodsNum);
        sb.append(", ").append(wxOrderPay);
        sb.append(", ").append(codOrderPay);
        sb.append(", ").append(balanceOrderPay);
        sb.append(", ").append(scoreOrderPay);
        sb.append(", ").append(wxOrderBalance);
        sb.append(", ").append(codOrderBalance);
        sb.append(", ").append(balanceOrderBalance);
        sb.append(", ").append(scoreOrderBalance);
        sb.append(", ").append(wxOrderCard);
        sb.append(", ").append(codOrderCard);
        sb.append(", ").append(balanceOrderCard);
        sb.append(", ").append(scoreOrderCard);
        sb.append(", ").append(wxOrderScore);
        sb.append(", ").append(codOrderScore);
        sb.append(", ").append(balanceOrderScore);
        sb.append(", ").append(scoreOrderScore);

        sb.append(")");
        return sb.toString();
    }
}

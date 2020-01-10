/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.pojos;


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
public class CardOrder implements Serializable {

    private static final long serialVersionUID = 1414192307;

    private Integer    orderId;
    private Integer    cardId;
    private String     cardNo;
    private String     orderSn;
    private Integer    userId;
    private Byte       orderStatus;
    private String     orderStatusName;
    private Integer    invoiceId;
    private String     invoiceDetail;
    private String     addMessage;
    private String     payCode;
    private String     payName;
    private String     prepayId;
    private String     paySn;
    private BigDecimal moneyPaid;
    private BigDecimal useAccount;
    private BigDecimal useScore;
    private BigDecimal orderAmount;
    private Timestamp  payTime;
    private String     sellerRemark;
    private Byte       starFlag;
    private Byte       delFlag;
    private String     aliTradeNo;
    private Byte       returnFlag;
    private BigDecimal returnScore;
    private BigDecimal returnAccount;
    private BigDecimal returnMoney;
    private Timestamp  returnTime;
    private Timestamp  delTime;
    private Timestamp  createTime;
    private Timestamp  updateTime;

    public CardOrder() {}

    public CardOrder(CardOrder value) {
        this.orderId = value.orderId;
        this.cardId = value.cardId;
        this.cardNo = value.cardNo;
        this.orderSn = value.orderSn;
        this.userId = value.userId;
        this.orderStatus = value.orderStatus;
        this.orderStatusName = value.orderStatusName;
        this.invoiceId = value.invoiceId;
        this.invoiceDetail = value.invoiceDetail;
        this.addMessage = value.addMessage;
        this.payCode = value.payCode;
        this.payName = value.payName;
        this.prepayId = value.prepayId;
        this.paySn = value.paySn;
        this.moneyPaid = value.moneyPaid;
        this.useAccount = value.useAccount;
        this.useScore = value.useScore;
        this.orderAmount = value.orderAmount;
        this.payTime = value.payTime;
        this.sellerRemark = value.sellerRemark;
        this.starFlag = value.starFlag;
        this.delFlag = value.delFlag;
        this.aliTradeNo = value.aliTradeNo;
        this.returnFlag = value.returnFlag;
        this.returnScore = value.returnScore;
        this.returnAccount = value.returnAccount;
        this.returnMoney = value.returnMoney;
        this.returnTime = value.returnTime;
        this.delTime = value.delTime;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public CardOrder(
        Integer    orderId,
        Integer    cardId,
        String     cardNo,
        String     orderSn,
        Integer    userId,
        Byte       orderStatus,
        String     orderStatusName,
        Integer    invoiceId,
        String     invoiceDetail,
        String     addMessage,
        String     payCode,
        String     payName,
        String     prepayId,
        String     paySn,
        BigDecimal moneyPaid,
        BigDecimal useAccount,
        BigDecimal useScore,
        BigDecimal orderAmount,
        Timestamp  payTime,
        String     sellerRemark,
        Byte       starFlag,
        Byte       delFlag,
        String     aliTradeNo,
        Byte       returnFlag,
        BigDecimal returnScore,
        BigDecimal returnAccount,
        BigDecimal returnMoney,
        Timestamp  returnTime,
        Timestamp  delTime,
        Timestamp  createTime,
        Timestamp  updateTime
    ) {
        this.orderId = orderId;
        this.cardId = cardId;
        this.cardNo = cardNo;
        this.orderSn = orderSn;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.orderStatusName = orderStatusName;
        this.invoiceId = invoiceId;
        this.invoiceDetail = invoiceDetail;
        this.addMessage = addMessage;
        this.payCode = payCode;
        this.payName = payName;
        this.prepayId = prepayId;
        this.paySn = paySn;
        this.moneyPaid = moneyPaid;
        this.useAccount = useAccount;
        this.useScore = useScore;
        this.orderAmount = orderAmount;
        this.payTime = payTime;
        this.sellerRemark = sellerRemark;
        this.starFlag = starFlag;
        this.delFlag = delFlag;
        this.aliTradeNo = aliTradeNo;
        this.returnFlag = returnFlag;
        this.returnScore = returnScore;
        this.returnAccount = returnAccount;
        this.returnMoney = returnMoney;
        this.returnTime = returnTime;
        this.delTime = delTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCardId() {
        return this.cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOrderSn() {
        return this.orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return this.orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public Integer getInvoiceId() {
        return this.invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceDetail() {
        return this.invoiceDetail;
    }

    public void setInvoiceDetail(String invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public String getAddMessage() {
        return this.addMessage;
    }

    public void setAddMessage(String addMessage) {
        this.addMessage = addMessage;
    }

    public String getPayCode() {
        return this.payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayName() {
        return this.payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPrepayId() {
        return this.prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPaySn() {
        return this.paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public BigDecimal getMoneyPaid() {
        return this.moneyPaid;
    }

    public void setMoneyPaid(BigDecimal moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    public BigDecimal getUseAccount() {
        return this.useAccount;
    }

    public void setUseAccount(BigDecimal useAccount) {
        this.useAccount = useAccount;
    }

    public BigDecimal getUseScore() {
        return this.useScore;
    }

    public void setUseScore(BigDecimal useScore) {
        this.useScore = useScore;
    }

    public BigDecimal getOrderAmount() {
        return this.orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Timestamp getPayTime() {
        return this.payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public String getSellerRemark() {
        return this.sellerRemark;
    }

    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark;
    }

    public Byte getStarFlag() {
        return this.starFlag;
    }

    public void setStarFlag(Byte starFlag) {
        this.starFlag = starFlag;
    }

    public Byte getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public String getAliTradeNo() {
        return this.aliTradeNo;
    }

    public void setAliTradeNo(String aliTradeNo) {
        this.aliTradeNo = aliTradeNo;
    }

    public Byte getReturnFlag() {
        return this.returnFlag;
    }

    public void setReturnFlag(Byte returnFlag) {
        this.returnFlag = returnFlag;
    }

    public BigDecimal getReturnScore() {
        return this.returnScore;
    }

    public void setReturnScore(BigDecimal returnScore) {
        this.returnScore = returnScore;
    }

    public BigDecimal getReturnAccount() {
        return this.returnAccount;
    }

    public void setReturnAccount(BigDecimal returnAccount) {
        this.returnAccount = returnAccount;
    }

    public BigDecimal getReturnMoney() {
        return this.returnMoney;
    }

    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Timestamp getReturnTime() {
        return this.returnTime;
    }

    public void setReturnTime(Timestamp returnTime) {
        this.returnTime = returnTime;
    }

    public Timestamp getDelTime() {
        return this.delTime;
    }

    public void setDelTime(Timestamp delTime) {
        this.delTime = delTime;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CardOrder (");

        sb.append(orderId);
        sb.append(", ").append(cardId);
        sb.append(", ").append(cardNo);
        sb.append(", ").append(orderSn);
        sb.append(", ").append(userId);
        sb.append(", ").append(orderStatus);
        sb.append(", ").append(orderStatusName);
        sb.append(", ").append(invoiceId);
        sb.append(", ").append(invoiceDetail);
        sb.append(", ").append(addMessage);
        sb.append(", ").append(payCode);
        sb.append(", ").append(payName);
        sb.append(", ").append(prepayId);
        sb.append(", ").append(paySn);
        sb.append(", ").append(moneyPaid);
        sb.append(", ").append(useAccount);
        sb.append(", ").append(useScore);
        sb.append(", ").append(orderAmount);
        sb.append(", ").append(payTime);
        sb.append(", ").append(sellerRemark);
        sb.append(", ").append(starFlag);
        sb.append(", ").append(delFlag);
        sb.append(", ").append(aliTradeNo);
        sb.append(", ").append(returnFlag);
        sb.append(", ").append(returnScore);
        sb.append(", ").append(returnAccount);
        sb.append(", ").append(returnMoney);
        sb.append(", ").append(returnTime);
        sb.append(", ").append(delTime);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}

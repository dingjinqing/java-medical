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
public class GiveVoucher implements Serializable {

    private static final long serialVersionUID = 1982778151;

    private Integer    id;
    private String     actName;
    private Integer    number;
    private Integer    havePay;
    private Integer    noPay;
    private Integer    maxCount;
    private Integer    minCount;
    private String     cardId;
    private String     tagId;
    private Integer    actId;
    private Timestamp  inTime;
    private BigDecimal maxAvePrice;
    private BigDecimal minAvePrice;
    private Timestamp  updateTime;
    private String     user;
    private String     sendCondition;
    private Byte       sendStatus;
    private Byte       sendAction;
    private Timestamp  startTime;

    public GiveVoucher() {}

    public GiveVoucher(GiveVoucher value) {
        this.id = value.id;
        this.actName = value.actName;
        this.number = value.number;
        this.havePay = value.havePay;
        this.noPay = value.noPay;
        this.maxCount = value.maxCount;
        this.minCount = value.minCount;
        this.cardId = value.cardId;
        this.tagId = value.tagId;
        this.actId = value.actId;
        this.inTime = value.inTime;
        this.maxAvePrice = value.maxAvePrice;
        this.minAvePrice = value.minAvePrice;
        this.updateTime = value.updateTime;
        this.user = value.user;
        this.sendCondition = value.sendCondition;
        this.sendStatus = value.sendStatus;
        this.sendAction = value.sendAction;
        this.startTime = value.startTime;
    }

    public GiveVoucher(
        Integer    id,
        String     actName,
        Integer    number,
        Integer    havePay,
        Integer    noPay,
        Integer    maxCount,
        Integer    minCount,
        String     cardId,
        String     tagId,
        Integer    actId,
        Timestamp  inTime,
        BigDecimal maxAvePrice,
        BigDecimal minAvePrice,
        Timestamp  updateTime,
        String     user,
        String     sendCondition,
        Byte       sendStatus,
        Byte       sendAction,
        Timestamp  startTime
    ) {
        this.id = id;
        this.actName = actName;
        this.number = number;
        this.havePay = havePay;
        this.noPay = noPay;
        this.maxCount = maxCount;
        this.minCount = minCount;
        this.cardId = cardId;
        this.tagId = tagId;
        this.actId = actId;
        this.inTime = inTime;
        this.maxAvePrice = maxAvePrice;
        this.minAvePrice = minAvePrice;
        this.updateTime = updateTime;
        this.user = user;
        this.sendCondition = sendCondition;
        this.sendStatus = sendStatus;
        this.sendAction = sendAction;
        this.startTime = startTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActName() {
        return this.actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getHavePay() {
        return this.havePay;
    }

    public void setHavePay(Integer havePay) {
        this.havePay = havePay;
    }

    public Integer getNoPay() {
        return this.noPay;
    }

    public void setNoPay(Integer noPay) {
        this.noPay = noPay;
    }

    public Integer getMaxCount() {
        return this.maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getMinCount() {
        return this.minCount;
    }

    public void setMinCount(Integer minCount) {
        this.minCount = minCount;
    }

    public String getCardId() {
        return this.cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTagId() {
        return this.tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Integer getActId() {
        return this.actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public Timestamp getInTime() {
        return this.inTime;
    }

    public void setInTime(Timestamp inTime) {
        this.inTime = inTime;
    }

    public BigDecimal getMaxAvePrice() {
        return this.maxAvePrice;
    }

    public void setMaxAvePrice(BigDecimal maxAvePrice) {
        this.maxAvePrice = maxAvePrice;
    }

    public BigDecimal getMinAvePrice() {
        return this.minAvePrice;
    }

    public void setMinAvePrice(BigDecimal minAvePrice) {
        this.minAvePrice = minAvePrice;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSendCondition() {
        return this.sendCondition;
    }

    public void setSendCondition(String sendCondition) {
        this.sendCondition = sendCondition;
    }

    public Byte getSendStatus() {
        return this.sendStatus;
    }

    public void setSendStatus(Byte sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Byte getSendAction() {
        return this.sendAction;
    }

    public void setSendAction(Byte sendAction) {
        this.sendAction = sendAction;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GiveVoucher (");

        sb.append(id);
        sb.append(", ").append(actName);
        sb.append(", ").append(number);
        sb.append(", ").append(havePay);
        sb.append(", ").append(noPay);
        sb.append(", ").append(maxCount);
        sb.append(", ").append(minCount);
        sb.append(", ").append(cardId);
        sb.append(", ").append(tagId);
        sb.append(", ").append(actId);
        sb.append(", ").append(inTime);
        sb.append(", ").append(maxAvePrice);
        sb.append(", ").append(minAvePrice);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(user);
        sb.append(", ").append(sendCondition);
        sb.append(", ").append(sendStatus);
        sb.append(", ").append(sendAction);
        sb.append(", ").append(startTime);

        sb.append(")");
        return sb.toString();
    }
}

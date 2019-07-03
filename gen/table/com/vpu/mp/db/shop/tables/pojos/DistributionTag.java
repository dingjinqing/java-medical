/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
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
public class DistributionTag implements Serializable {

    private static final long serialVersionUID = -1118270691;

    private Date       refDate;
    private Byte       type;
    private String     tag;
    private Integer    payOrderNum;
    private BigDecimal payOrderMoney;
    private Integer    payUserNum;
    private Integer    payGoodsNumber;
    private Integer    hasMobileNum;
    private Integer    hasUserNum;
    private Timestamp  addTime;

    public DistributionTag() {}

    public DistributionTag(DistributionTag value) {
        this.refDate = value.refDate;
        this.type = value.type;
        this.tag = value.tag;
        this.payOrderNum = value.payOrderNum;
        this.payOrderMoney = value.payOrderMoney;
        this.payUserNum = value.payUserNum;
        this.payGoodsNumber = value.payGoodsNumber;
        this.hasMobileNum = value.hasMobileNum;
        this.hasUserNum = value.hasUserNum;
        this.addTime = value.addTime;
    }

    public DistributionTag(
        Date       refDate,
        Byte       type,
        String     tag,
        Integer    payOrderNum,
        BigDecimal payOrderMoney,
        Integer    payUserNum,
        Integer    payGoodsNumber,
        Integer    hasMobileNum,
        Integer    hasUserNum,
        Timestamp  addTime
    ) {
        this.refDate = refDate;
        this.type = type;
        this.tag = tag;
        this.payOrderNum = payOrderNum;
        this.payOrderMoney = payOrderMoney;
        this.payUserNum = payUserNum;
        this.payGoodsNumber = payGoodsNumber;
        this.hasMobileNum = hasMobileNum;
        this.hasUserNum = hasUserNum;
        this.addTime = addTime;
    }

    public Date getRefDate() {
        return this.refDate;
    }

    public void setRefDate(Date refDate) {
        this.refDate = refDate;
    }

    public Byte getType() {
        return this.type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getPayOrderNum() {
        return this.payOrderNum;
    }

    public void setPayOrderNum(Integer payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    public BigDecimal getPayOrderMoney() {
        return this.payOrderMoney;
    }

    public void setPayOrderMoney(BigDecimal payOrderMoney) {
        this.payOrderMoney = payOrderMoney;
    }

    public Integer getPayUserNum() {
        return this.payUserNum;
    }

    public void setPayUserNum(Integer payUserNum) {
        this.payUserNum = payUserNum;
    }

    public Integer getPayGoodsNumber() {
        return this.payGoodsNumber;
    }

    public void setPayGoodsNumber(Integer payGoodsNumber) {
        this.payGoodsNumber = payGoodsNumber;
    }

    public Integer getHasMobileNum() {
        return this.hasMobileNum;
    }

    public void setHasMobileNum(Integer hasMobileNum) {
        this.hasMobileNum = hasMobileNum;
    }

    public Integer getHasUserNum() {
        return this.hasUserNum;
    }

    public void setHasUserNum(Integer hasUserNum) {
        this.hasUserNum = hasUserNum;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DistributionTag (");

        sb.append(refDate);
        sb.append(", ").append(type);
        sb.append(", ").append(tag);
        sb.append(", ").append(payOrderNum);
        sb.append(", ").append(payOrderMoney);
        sb.append(", ").append(payUserNum);
        sb.append(", ").append(payGoodsNumber);
        sb.append(", ").append(hasMobileNum);
        sb.append(", ").append(hasUserNum);
        sb.append(", ").append(addTime);

        sb.append(")");
        return sb.toString();
    }
}

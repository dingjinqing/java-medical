/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.pojos;


import java.io.Serializable;
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
public class SmsSendRecord implements Serializable {

    private static final long serialVersionUID = -142673314;

    private Integer   id;
    private String    accountName;
    private Integer   userId;
    private String    mobile;
    private String    requestMsg;
    private String    responseCode;
    private String    responseMsg;
    private String    ext;
    private String    sms;
    private Timestamp createTime;
    private Timestamp updateTime;

    public SmsSendRecord() {}

    public SmsSendRecord(SmsSendRecord value) {
        this.id = value.id;
        this.accountName = value.accountName;
        this.userId = value.userId;
        this.mobile = value.mobile;
        this.requestMsg = value.requestMsg;
        this.responseCode = value.responseCode;
        this.responseMsg = value.responseMsg;
        this.ext = value.ext;
        this.sms = value.sms;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public SmsSendRecord(
        Integer   id,
        String    accountName,
        Integer   userId,
        String    mobile,
        String    requestMsg,
        String    responseCode,
        String    responseMsg,
        String    ext,
        String    sms,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.accountName = accountName;
        this.userId = userId;
        this.mobile = mobile;
        this.requestMsg = requestMsg;
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.ext = ext;
        this.sms = sms;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRequestMsg() {
        return this.requestMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return this.responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getSms() {
        return this.sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
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
        StringBuilder sb = new StringBuilder("SmsSendRecord (");

        sb.append(id);
        sb.append(", ").append(accountName);
        sb.append(", ").append(userId);
        sb.append(", ").append(mobile);
        sb.append(", ").append(requestMsg);
        sb.append(", ").append(responseCode);
        sb.append(", ").append(responseMsg);
        sb.append(", ").append(ext);
        sb.append(", ").append(sms);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}

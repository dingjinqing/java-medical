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
public class CardExamine implements Serializable {

    private static final long serialVersionUID = 866053032;

    private Integer   id;
    private Integer   cardId;
    private String    cardNo;
    private Integer   userId;
    private Byte      status;
    private String    desc;
    private String    realName;
    private String    cid;
    private Integer   provinceCode;
    private Integer   cityCode;
    private Integer   districtCode;
    private String    sex;
    private Integer   birthdayYear;
    private Integer   birthdayMonth;
    private Integer   birthdayDay;
    private Byte      maritalStatus;
    private Byte      education;
    private Byte      industryInfo;
    private Timestamp addTime;
    private Timestamp passTime;
    private Timestamp refuseTime;
    private String    refuseDesc;
    private Byte      delFlag;
    private Timestamp defTime;

    public CardExamine() {}

    public CardExamine(CardExamine value) {
        this.id = value.id;
        this.cardId = value.cardId;
        this.cardNo = value.cardNo;
        this.userId = value.userId;
        this.status = value.status;
        this.desc = value.desc;
        this.realName = value.realName;
        this.cid = value.cid;
        this.provinceCode = value.provinceCode;
        this.cityCode = value.cityCode;
        this.districtCode = value.districtCode;
        this.sex = value.sex;
        this.birthdayYear = value.birthdayYear;
        this.birthdayMonth = value.birthdayMonth;
        this.birthdayDay = value.birthdayDay;
        this.maritalStatus = value.maritalStatus;
        this.education = value.education;
        this.industryInfo = value.industryInfo;
        this.addTime = value.addTime;
        this.passTime = value.passTime;
        this.refuseTime = value.refuseTime;
        this.refuseDesc = value.refuseDesc;
        this.delFlag = value.delFlag;
        this.defTime = value.defTime;
    }

    public CardExamine(
        Integer   id,
        Integer   cardId,
        String    cardNo,
        Integer   userId,
        Byte      status,
        String    desc,
        String    realName,
        String    cid,
        Integer   provinceCode,
        Integer   cityCode,
        Integer   districtCode,
        String    sex,
        Integer   birthdayYear,
        Integer   birthdayMonth,
        Integer   birthdayDay,
        Byte      maritalStatus,
        Byte      education,
        Byte      industryInfo,
        Timestamp addTime,
        Timestamp passTime,
        Timestamp refuseTime,
        String    refuseDesc,
        Byte      delFlag,
        Timestamp defTime
    ) {
        this.id = id;
        this.cardId = cardId;
        this.cardNo = cardNo;
        this.userId = userId;
        this.status = status;
        this.desc = desc;
        this.realName = realName;
        this.cid = cid;
        this.provinceCode = provinceCode;
        this.cityCode = cityCode;
        this.districtCode = districtCode;
        this.sex = sex;
        this.birthdayYear = birthdayYear;
        this.birthdayMonth = birthdayMonth;
        this.birthdayDay = birthdayDay;
        this.maritalStatus = maritalStatus;
        this.education = education;
        this.industryInfo = industryInfo;
        this.addTime = addTime;
        this.passTime = passTime;
        this.refuseTime = refuseTime;
        this.refuseDesc = refuseDesc;
        this.delFlag = delFlag;
        this.defTime = defTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getProvinceCode() {
        return this.provinceCode;
    }

    public void setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Integer getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getDistrictCode() {
        return this.districtCode;
    }

    public void setDistrictCode(Integer districtCode) {
        this.districtCode = districtCode;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getBirthdayYear() {
        return this.birthdayYear;
    }

    public void setBirthdayYear(Integer birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public Integer getBirthdayMonth() {
        return this.birthdayMonth;
    }

    public void setBirthdayMonth(Integer birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    public Integer getBirthdayDay() {
        return this.birthdayDay;
    }

    public void setBirthdayDay(Integer birthdayDay) {
        this.birthdayDay = birthdayDay;
    }

    public Byte getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(Byte maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Byte getEducation() {
        return this.education;
    }

    public void setEducation(Byte education) {
        this.education = education;
    }

    public Byte getIndustryInfo() {
        return this.industryInfo;
    }

    public void setIndustryInfo(Byte industryInfo) {
        this.industryInfo = industryInfo;
    }

    public Timestamp getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public Timestamp getPassTime() {
        return this.passTime;
    }

    public void setPassTime(Timestamp passTime) {
        this.passTime = passTime;
    }

    public Timestamp getRefuseTime() {
        return this.refuseTime;
    }

    public void setRefuseTime(Timestamp refuseTime) {
        this.refuseTime = refuseTime;
    }

    public String getRefuseDesc() {
        return this.refuseDesc;
    }

    public void setRefuseDesc(String refuseDesc) {
        this.refuseDesc = refuseDesc;
    }

    public Byte getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Timestamp getDefTime() {
        return this.defTime;
    }

    public void setDefTime(Timestamp defTime) {
        this.defTime = defTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CardExamine (");

        sb.append(id);
        sb.append(", ").append(cardId);
        sb.append(", ").append(cardNo);
        sb.append(", ").append(userId);
        sb.append(", ").append(status);
        sb.append(", ").append(desc);
        sb.append(", ").append(realName);
        sb.append(", ").append(cid);
        sb.append(", ").append(provinceCode);
        sb.append(", ").append(cityCode);
        sb.append(", ").append(districtCode);
        sb.append(", ").append(sex);
        sb.append(", ").append(birthdayYear);
        sb.append(", ").append(birthdayMonth);
        sb.append(", ").append(birthdayDay);
        sb.append(", ").append(maritalStatus);
        sb.append(", ").append(education);
        sb.append(", ").append(industryInfo);
        sb.append(", ").append(addTime);
        sb.append(", ").append(passTime);
        sb.append(", ").append(refuseTime);
        sb.append(", ").append(refuseDesc);
        sb.append(", ").append(delFlag);
        sb.append(", ").append(defTime);

        sb.append(")");
        return sb.toString();
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.CardExamine;

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
public class CardExamineRecord extends UpdatableRecordImpl<CardExamineRecord> {

    private static final long serialVersionUID = 1425076734;

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.id</code>. 订单id
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.id</code>. 订单id
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.card_id</code>. 会云卡id
     */
    public void setCardId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.card_id</code>. 会云卡id
     */
    public Integer getCardId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.card_no</code>. 会员卡NO
     */
    public void setCardNo(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.card_no</code>. 会员卡NO
     */
    public String getCardNo() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.user_id</code>. 用户id
     */
    public void setUserId(UInteger value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.user_id</code>. 用户id
     */
    public UInteger getUserId() {
        return (UInteger) get(3);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.status</code>. 审核状态 1审核中 2通过 3拒绝
     */
    public void setStatus(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.status</code>. 审核状态 1审核中 2通过 3拒绝
     */
    public Byte getStatus() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.desc</code>. 备注
     */
    public void setDesc(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.desc</code>. 备注
     */
    public String getDesc() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.real_name</code>. 真是姓名
     */
    public void setRealName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.real_name</code>. 真是姓名
     */
    public String getRealName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.cid</code>. 身份证号
     */
    public void setCid(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.cid</code>. 身份证号
     */
    public String getCid() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.province_code</code>. 所在地
     */
    public void setProvinceCode(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.province_code</code>. 所在地
     */
    public Integer getProvinceCode() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.city_code</code>. 所在地
     */
    public void setCityCode(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.city_code</code>. 所在地
     */
    public Integer getCityCode() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.district_code</code>. 所在地
     */
    public void setDistrictCode(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.district_code</code>. 所在地
     */
    public Integer getDistrictCode() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.sex</code>. 性别
     */
    public void setSex(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.sex</code>. 性别
     */
    public String getSex() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.birthday_year</code>. 生日
     */
    public void setBirthdayYear(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.birthday_year</code>. 生日
     */
    public Integer getBirthdayYear() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.birthday_month</code>. 生日
     */
    public void setBirthdayMonth(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.birthday_month</code>. 生日
     */
    public Integer getBirthdayMonth() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.birthday_day</code>. 生日
     */
    public void setBirthdayDay(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.birthday_day</code>. 生日
     */
    public Integer getBirthdayDay() {
        return (Integer) get(14);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.marital_status</code>. 婚姻状况
     */
    public void setMaritalStatus(Byte value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.marital_status</code>. 婚姻状况
     */
    public Byte getMaritalStatus() {
        return (Byte) get(15);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.education</code>. 教育程度
     */
    public void setEducation(Byte value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.education</code>. 教育程度
     */
    public Byte getEducation() {
        return (Byte) get(16);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.industry_info</code>. 所在行业
     */
    public void setIndustryInfo(Byte value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.industry_info</code>. 所在行业
     */
    public Byte getIndustryInfo() {
        return (Byte) get(17);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.add_time</code>. 提交时间
     */
    public void setAddTime(Timestamp value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.add_time</code>. 提交时间
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(18);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.pass_time</code>. 通过时间
     */
    public void setPassTime(Timestamp value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.pass_time</code>. 通过时间
     */
    public Timestamp getPassTime() {
        return (Timestamp) get(19);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.refuse_time</code>. 拒绝时间
     */
    public void setRefuseTime(Timestamp value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.refuse_time</code>. 拒绝时间
     */
    public Timestamp getRefuseTime() {
        return (Timestamp) get(20);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.refuse_desc</code>. 拒绝理由
     */
    public void setRefuseDesc(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.refuse_desc</code>. 拒绝理由
     */
    public String getRefuseDesc() {
        return (String) get(21);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.del_flag</code>. 删除
     */
    public void setDelFlag(Byte value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.del_flag</code>. 删除
     */
    public Byte getDelFlag() {
        return (Byte) get(22);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_card_examine.def_time</code>. 删除时间
     */
    public void setDefTime(Timestamp value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_card_examine.def_time</code>. 删除时间
     */
    public Timestamp getDefTime() {
        return (Timestamp) get(23);
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
     * Create a detached CardExamineRecord
     */
    public CardExamineRecord() {
        super(CardExamine.CARD_EXAMINE);
    }

    /**
     * Create a detached, initialised CardExamineRecord
     */
    public CardExamineRecord(UInteger id, Integer cardId, String cardNo, UInteger userId, Byte status, String desc, String realName, String cid, Integer provinceCode, Integer cityCode, Integer districtCode, String sex, Integer birthdayYear, Integer birthdayMonth, Integer birthdayDay, Byte maritalStatus, Byte education, Byte industryInfo, Timestamp addTime, Timestamp passTime, Timestamp refuseTime, String refuseDesc, Byte delFlag, Timestamp defTime) {
        super(CardExamine.CARD_EXAMINE);

        set(0, id);
        set(1, cardId);
        set(2, cardNo);
        set(3, userId);
        set(4, status);
        set(5, desc);
        set(6, realName);
        set(7, cid);
        set(8, provinceCode);
        set(9, cityCode);
        set(10, districtCode);
        set(11, sex);
        set(12, birthdayYear);
        set(13, birthdayMonth);
        set(14, birthdayDay);
        set(15, maritalStatus);
        set(16, education);
        set(17, industryInfo);
        set(18, addTime);
        set(19, passTime);
        set(20, refuseTime);
        set(21, refuseDesc);
        set(22, delFlag);
        set(23, defTime);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cUserDetail;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


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
public class B2cUserDetailRecord extends UpdatableRecordImpl<B2cUserDetailRecord> {

    private static final long serialVersionUID = -542030355;

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.username</code>. 昵称
     */
    public void setUsername(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.username</code>. 昵称
     */
    public String getUsername() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.sex</code>. 性别：女f,男m
     */
    public void setSex(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.sex</code>. 性别：女f,男m
     */
    public String getSex() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.birthday_year</code>. 生日年份
     */
    public void setBirthdayYear(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.birthday_year</code>. 生日年份
     */
    public Integer getBirthdayYear() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.birthday_month</code>.
     */
    public void setBirthdayMonth(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.birthday_month</code>.
     */
    public Integer getBirthdayMonth() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.birthday_day</code>.
     */
    public void setBirthdayDay(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.birthday_day</code>.
     */
    public Integer getBirthdayDay() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.email</code>. 邮箱
     */
    public void setEmail(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.email</code>. 邮箱
     */
    public String getEmail() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.real_name</code>. 真实姓名
     */
    public void setRealName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.real_name</code>. 真实姓名
     */
    public String getRealName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.province_code</code>. 所在地省编号
     */
    public void setProvinceCode(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.province_code</code>. 所在地省编号
     */
    public Integer getProvinceCode() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.city_code</code>. 所在地市编号
     */
    public void setCityCode(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.city_code</code>. 所在地市编号
     */
    public Integer getCityCode() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.district_code</code>. 所在地区编号
     */
    public void setDistrictCode(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.district_code</code>. 所在地区编号
     */
    public Integer getDistrictCode() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.address</code>. 所在地
     */
    public void setAddress(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.address</code>. 所在地
     */
    public String getAddress() {
        return (String) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.marital_status</code>. 婚姻状况：1未婚，2已婚，3保密
     */
    public void setMaritalStatus(Byte value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.marital_status</code>. 婚姻状况：1未婚，2已婚，3保密
     */
    public Byte getMaritalStatus() {
        return (Byte) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.monthly_income</code>. 月收入
     */
    public void setMonthlyIncome(Byte value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.monthly_income</code>. 月收入
     */
    public Byte getMonthlyIncome() {
        return (Byte) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.cid</code>. 身份证号码
     */
    public void setCid(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.cid</code>. 身份证号码
     */
    public String getCid() {
        return (String) get(16);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.education</code>. 教育程度
     */
    public void setEducation(Byte value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.education</code>. 教育程度
     */
    public Byte getEducation() {
        return (Byte) get(17);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.industry_info</code>. 所在行业
     */
    public void setIndustryInfo(Byte value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.industry_info</code>. 所在行业
     */
    public Byte getIndustryInfo() {
        return (Byte) get(18);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.big_image</code>. 头像
     */
    public void setBigImage(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.big_image</code>. 头像
     */
    public String getBigImage() {
        return (String) get(19);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.bank_user_name</code>. 开户行姓名
     */
    public void setBankUserName(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.bank_user_name</code>. 开户行姓名
     */
    public String getBankUserName() {
        return (String) get(20);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.shop_bank</code>. 开户行
     */
    public void setShopBank(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.shop_bank</code>. 开户行
     */
    public String getShopBank() {
        return (String) get(21);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.bank_no</code>. 开户行卡号
     */
    public void setBankNo(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.bank_no</code>. 开户行卡号
     */
    public String getBankNo() {
        return (String) get(22);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.withdraw_passwd</code>. 提现密码验证
     */
    public void setWithdrawPasswd(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.withdraw_passwd</code>. 提现密码验证
     */
    public String getWithdrawPasswd() {
        return (String) get(23);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_detail.user_avatar</code>. 用户头像
     */
    public void setUserAvatar(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_detail.user_avatar</code>. 用户头像
     */
    public String getUserAvatar() {
        return (String) get(24);
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
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cUserDetailRecord
     */
    public B2cUserDetailRecord() {
        super(B2cUserDetail.B2C_USER_DETAIL);
    }

    /**
     * Create a detached, initialised B2cUserDetailRecord
     */
    public B2cUserDetailRecord(Integer id, Integer userId, Integer shopId, String username, String sex, Integer birthdayYear, Integer birthdayMonth, Integer birthdayDay, String email, String realName, Integer provinceCode, Integer cityCode, Integer districtCode, String address, Byte maritalStatus, Byte monthlyIncome, String cid, Byte education, Byte industryInfo, String bigImage, String bankUserName, String shopBank, String bankNo, String withdrawPasswd, String userAvatar) {
        super(B2cUserDetail.B2C_USER_DETAIL);

        set(0, id);
        set(1, userId);
        set(2, shopId);
        set(3, username);
        set(4, sex);
        set(5, birthdayYear);
        set(6, birthdayMonth);
        set(7, birthdayDay);
        set(8, email);
        set(9, realName);
        set(10, provinceCode);
        set(11, cityCode);
        set(12, districtCode);
        set(13, address);
        set(14, maritalStatus);
        set(15, monthlyIncome);
        set(16, cid);
        set(17, education);
        set(18, industryInfo);
        set(19, bigImage);
        set(20, bankUserName);
        set(21, shopBank);
        set(22, bankNo);
        set(23, withdrawPasswd);
        set(24, userAvatar);
    }
}

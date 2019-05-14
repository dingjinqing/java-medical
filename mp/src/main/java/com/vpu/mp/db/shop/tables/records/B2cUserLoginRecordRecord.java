/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cUserLoginRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;


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
public class B2cUserLoginRecordRecord extends UpdatableRecordImpl<B2cUserLoginRecordRecord> implements Record14<ULong, Integer, Timestamp, String, Integer, Timestamp, String, String, String, String, String, String, String, String> {

    private static final long serialVersionUID = -1185842166;

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.id</code>.
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.id</code>.
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.user_id</code>. 登陆用户id
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.user_id</code>. 登陆用户id
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.add_time</code>. 每日登陆时间
     */
    public void setAddTime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.add_time</code>. 每日登陆时间
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.user_ip</code>. 用户登录ip
     */
    public void setUserIp(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.user_ip</code>. 用户登录ip
     */
    public String getUserIp() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.count</code>. 每日登陆次数
     */
    public void setCount(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.count</code>. 每日登陆次数
     */
    public Integer getCount() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.update_time</code>. 最后登录时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.update_time</code>. 最后登录时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.province_code</code>. 省
     */
    public void setProvinceCode(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.province_code</code>. 省
     */
    public String getProvinceCode() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.province</code>. 省
     */
    public void setProvince(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.province</code>. 省
     */
    public String getProvince() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.city_code</code>. 市
     */
    public void setCityCode(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.city_code</code>. 市
     */
    public String getCityCode() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.city</code>. 市
     */
    public void setCity(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.city</code>. 市
     */
    public String getCity() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.district_code</code>. 区
     */
    public void setDistrictCode(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.district_code</code>. 区
     */
    public String getDistrictCode() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.district</code>. 区
     */
    public void setDistrict(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.district</code>. 区
     */
    public String getDistrict() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.lat</code>. 经度
     */
    public void setLat(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.lat</code>. 经度
     */
    public String getLat() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_login_record.lng</code>. 纬度
     */
    public void setLng(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_login_record.lng</code>. 纬度
     */
    public String getLng() {
        return (String) get(13);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<ULong> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<ULong, Integer, Timestamp, String, Integer, Timestamp, String, String, String, String, String, String, String, String> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<ULong, Integer, Timestamp, String, Integer, Timestamp, String, String, String, String, String, String, String, String> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.USER_IP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.PROVINCE_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.PROVINCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.CITY_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.CITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.DISTRICT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.DISTRICT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.LAT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return B2cUserLoginRecord.B2C_USER_LOGIN_RECORD.LNG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component1() {
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
    public Timestamp component3() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getUserIp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getProvinceCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getProvince();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getCityCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getCity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getDistrictCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getDistrict();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getLat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component14() {
        return getLng();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value1() {
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
    public Timestamp value3() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getUserIp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getProvinceCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getProvince();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getCityCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getCity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getDistrictCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getDistrict();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getLat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getLng();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value3(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value4(String value) {
        setUserIp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value5(Integer value) {
        setCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value7(String value) {
        setProvinceCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value8(String value) {
        setProvince(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value9(String value) {
        setCityCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value10(String value) {
        setCity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value11(String value) {
        setDistrictCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value12(String value) {
        setDistrict(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value13(String value) {
        setLat(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord value14(String value) {
        setLng(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserLoginRecordRecord values(ULong value1, Integer value2, Timestamp value3, String value4, Integer value5, Timestamp value6, String value7, String value8, String value9, String value10, String value11, String value12, String value13, String value14) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cUserLoginRecordRecord
     */
    public B2cUserLoginRecordRecord() {
        super(B2cUserLoginRecord.B2C_USER_LOGIN_RECORD);
    }

    /**
     * Create a detached, initialised B2cUserLoginRecordRecord
     */
    public B2cUserLoginRecordRecord(ULong id, Integer userId, Timestamp addTime, String userIp, Integer count, Timestamp updateTime, String provinceCode, String province, String cityCode, String city, String districtCode, String district, String lat, String lng) {
        super(B2cUserLoginRecord.B2C_USER_LOGIN_RECORD);

        set(0, id);
        set(1, userId);
        set(2, addTime);
        set(3, userIp);
        set(4, count);
        set(5, updateTime);
        set(6, provinceCode);
        set(7, province);
        set(8, cityCode);
        set(9, city);
        set(10, districtCode);
        set(11, district);
        set(12, lat);
        set(13, lng);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.UserLoginRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
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
public class UserLoginRecordRecord extends UpdatableRecordImpl<UserLoginRecordRecord> implements Record9<UInteger, Integer, String, Integer, Short, String, Timestamp, String, Short> {

    private static final long serialVersionUID = -3373005;

    /**
     * Setter for <code>mini_main.b2c_user_login_record.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user_login_record.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_user_login_record.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user_login_record.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_user_login_record.shop_name</code>. 店铺名称
     */
    public void setShopName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user_login_record.shop_name</code>. 店铺名称
     */
    public String getShopName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_user_login_record.sys_id</code>. 主账户ID
     */
    public void setSysId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user_login_record.sys_id</code>. 主账户ID
     */
    public Integer getSysId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_user_login_record.user_id</code>. 登陆用户id
     */
    public void setUserId(Short value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user_login_record.user_id</code>. 登陆用户id
     */
    public Short getUserId() {
        return (Short) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_user_login_record.user_name</code>. 登陆用户名
     */
    public void setUserName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user_login_record.user_name</code>. 登陆用户名
     */
    public String getUserName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_user_login_record.add_time</code>. 每日登陆时间
     */
    public void setAddTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user_login_record.add_time</code>. 每日登陆时间
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_user_login_record.user_ip</code>. 用户登录ip
     */
    public void setUserIp(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user_login_record.user_ip</code>. 用户登录ip
     */
    public String getUserIp() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_user_login_record.count</code>. 每日登陆次数
     */
    public void setCount(Short value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user_login_record.count</code>. 每日登陆次数
     */
    public Short getCount() {
        return (Short) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<UInteger, Integer, String, Integer, Short, String, Timestamp, String, Short> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<UInteger, Integer, String, Integer, Short, String, Timestamp, String, Short> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return UserLoginRecord.USER_LOGIN_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UserLoginRecord.USER_LOGIN_RECORD.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return UserLoginRecord.USER_LOGIN_RECORD.SHOP_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return UserLoginRecord.USER_LOGIN_RECORD.SYS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field5() {
        return UserLoginRecord.USER_LOGIN_RECORD.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return UserLoginRecord.USER_LOGIN_RECORD.USER_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return UserLoginRecord.USER_LOGIN_RECORD.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return UserLoginRecord.USER_LOGIN_RECORD.USER_IP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field9() {
        return UserLoginRecord.USER_LOGIN_RECORD.COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getShopName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getSysId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component5() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getUserName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getUserIp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component9() {
        return getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getShopName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getSysId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value5() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getUserName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getUserIp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value9() {
        return getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLoginRecordRecord value1(UInteger value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLoginRecordRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLoginRecordRecord value3(String value) {
        setShopName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLoginRecordRecord value4(Integer value) {
        setSysId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLoginRecordRecord value5(Short value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLoginRecordRecord value6(String value) {
        setUserName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLoginRecordRecord value7(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLoginRecordRecord value8(String value) {
        setUserIp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLoginRecordRecord value9(Short value) {
        setCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserLoginRecordRecord values(UInteger value1, Integer value2, String value3, Integer value4, Short value5, String value6, Timestamp value7, String value8, Short value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserLoginRecordRecord
     */
    public UserLoginRecordRecord() {
        super(UserLoginRecord.USER_LOGIN_RECORD);
    }

    /**
     * Create a detached, initialised UserLoginRecordRecord
     */
    public UserLoginRecordRecord(UInteger id, Integer shopId, String shopName, Integer sysId, Short userId, String userName, Timestamp addTime, String userIp, Short count) {
        super(UserLoginRecord.USER_LOGIN_RECORD);

        set(0, id);
        set(1, shopId);
        set(2, shopName);
        set(3, sysId);
        set(4, userId);
        set(5, userName);
        set(6, addTime);
        set(7, userIp);
        set(8, count);
    }
}

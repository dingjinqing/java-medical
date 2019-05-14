/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.B2cSystemChildAccount;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
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
public class B2cSystemChildAccountRecord extends UpdatableRecordImpl<B2cSystemChildAccountRecord> implements Record7<Integer, Integer, String, String, Integer, Timestamp, String> {

    private static final long serialVersionUID = 994865580;

    /**
     * Setter for <code>mini_main.b2c_system_child_account.account_id</code>.
     */
    public void setAccountId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_child_account.account_id</code>.
     */
    public Integer getAccountId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_system_child_account.system_user_id</code>. 店铺ID
     */
    public void setSystemUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_child_account.system_user_id</code>. 店铺ID
     */
    public Integer getSystemUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_system_child_account.account_name</code>. 子账号用户名
     */
    public void setAccountName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_child_account.account_name</code>. 子账号用户名
     */
    public String getAccountName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_system_child_account.account_pwd</code>. 子账号密码
     */
    public void setAccountPwd(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_child_account.account_pwd</code>. 子账号密码
     */
    public String getAccountPwd() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_system_child_account.role_id</code>. 角色ID
     */
    public void setRoleId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_child_account.role_id</code>. 角色ID
     */
    public Integer getRoleId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_system_child_account.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_child_account.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_system_child_account.mobile</code>. 手机号
     */
    public void setMobile(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_system_child_account.mobile</code>. 手机号
     */
    public String getMobile() {
        return (String) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, String, String, Integer, Timestamp, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, String, String, Integer, Timestamp, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cSystemChildAccount.B2C_SYSTEM_CHILD_ACCOUNT.ACCOUNT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cSystemChildAccount.B2C_SYSTEM_CHILD_ACCOUNT.SYSTEM_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return B2cSystemChildAccount.B2C_SYSTEM_CHILD_ACCOUNT.ACCOUNT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cSystemChildAccount.B2C_SYSTEM_CHILD_ACCOUNT.ACCOUNT_PWD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return B2cSystemChildAccount.B2C_SYSTEM_CHILD_ACCOUNT.ROLE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return B2cSystemChildAccount.B2C_SYSTEM_CHILD_ACCOUNT.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return B2cSystemChildAccount.B2C_SYSTEM_CHILD_ACCOUNT.MOBILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getAccountId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getSystemUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getAccountName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getAccountPwd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getRoleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getAccountId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getSystemUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getAccountName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getAccountPwd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getRoleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemChildAccountRecord value1(Integer value) {
        setAccountId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemChildAccountRecord value2(Integer value) {
        setSystemUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemChildAccountRecord value3(String value) {
        setAccountName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemChildAccountRecord value4(String value) {
        setAccountPwd(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemChildAccountRecord value5(Integer value) {
        setRoleId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemChildAccountRecord value6(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemChildAccountRecord value7(String value) {
        setMobile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cSystemChildAccountRecord values(Integer value1, Integer value2, String value3, String value4, Integer value5, Timestamp value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cSystemChildAccountRecord
     */
    public B2cSystemChildAccountRecord() {
        super(B2cSystemChildAccount.B2C_SYSTEM_CHILD_ACCOUNT);
    }

    /**
     * Create a detached, initialised B2cSystemChildAccountRecord
     */
    public B2cSystemChildAccountRecord(Integer accountId, Integer systemUserId, String accountName, String accountPwd, Integer roleId, Timestamp createTime, String mobile) {
        super(B2cSystemChildAccount.B2C_SYSTEM_CHILD_ACCOUNT);

        set(0, accountId);
        set(1, systemUserId);
        set(2, accountName);
        set(3, accountPwd);
        set(4, roleId);
        set(5, createTime);
        set(6, mobile);
    }
}

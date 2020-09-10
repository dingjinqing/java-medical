/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;



import java.sql.Timestamp;

import javax.annotation.Generated;

import com.vpu.mp.db.shop.tables.StoreAccount;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 门店账户表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StoreAccountRecord extends UpdatableRecordImpl<StoreAccountRecord> implements Record13<Integer, Integer, Integer, String, String, String, Timestamp, Byte, Byte, Byte, String, String, Timestamp> {

    private static final long serialVersionUID = 1002724407;

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.account_id</code>. 门店账号ID
     */
    public void setAccountId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.account_id</code>. 门店账号ID
     */
    public Integer getAccountId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.shop_id</code>. 所属店铺id
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.shop_id</code>. 所属店铺id
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.sys_id</code>. 所属账户id
     */
    public void setSysId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.sys_id</code>. 所属账户id
     */
    public Integer getSysId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.mobile</code>. 手机号
     */
    public void setMobile(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.mobile</code>. 手机号
     */
    public String getMobile() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.account_name</code>. 账户名称
     */
    public void setAccountName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.account_name</code>. 账户名称
     */
    public String getAccountName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.wx_nick_name</code>. 门店账户绑定微信昵称
     */
    public void setWxNickName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.wx_nick_name</code>. 门店账户绑定微信昵称
     */
    public String getWxNickName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.account_type</code>. 账户类型1:店员，2：店长
     */
    public void setAccountType(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.account_type</code>. 账户类型1:店员，2：店长
     */
    public Byte getAccountType() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.status</code>. 账户状态0:禁用，1：启用
     */
    public void setStatus(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.status</code>. 账户状态0:禁用，1：启用
     */
    public Byte getStatus() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.del_flag</code>. 是否已删除0:否，1：是
     */
    public void setDelFlag(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.del_flag</code>. 是否已删除0:否，1：是
     */
    public Byte getDelFlag() {
        return (Byte) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.account_passwd</code>. 账号密码
     */
    public void setAccountPasswd(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.account_passwd</code>. 账号密码
     */
    public String getAccountPasswd() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.store_list</code>. 可用门店id,逗号隔开
     */
    public void setStoreList(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.store_list</code>. 可用门店id,逗号隔开
     */
    public String getStoreList() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_account.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_account.update_time</code>. 修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(12);
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
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Integer, Integer, String, String, String, Timestamp, Byte, Byte, Byte, String, String, Timestamp> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Integer, Integer, String, String, String, Timestamp, Byte, Byte, Byte, String, String, Timestamp> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return StoreAccount.STORE_ACCOUNT.ACCOUNT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return StoreAccount.STORE_ACCOUNT.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return StoreAccount.STORE_ACCOUNT.SYS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return StoreAccount.STORE_ACCOUNT.MOBILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return StoreAccount.STORE_ACCOUNT.ACCOUNT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return StoreAccount.STORE_ACCOUNT.WX_NICK_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return StoreAccount.STORE_ACCOUNT.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return StoreAccount.STORE_ACCOUNT.ACCOUNT_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return StoreAccount.STORE_ACCOUNT.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return StoreAccount.STORE_ACCOUNT.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return StoreAccount.STORE_ACCOUNT.ACCOUNT_PASSWD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return StoreAccount.STORE_ACCOUNT.STORE_LIST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return StoreAccount.STORE_ACCOUNT.UPDATE_TIME;
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
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getSysId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getAccountName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getWxNickName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component8() {
        return getAccountType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component9() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component10() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getAccountPasswd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getStoreList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component13() {
        return getUpdateTime();
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
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getSysId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getAccountName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getWxNickName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getAccountType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getAccountPasswd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getStoreList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value13() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value1(Integer value) {
        setAccountId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value3(Integer value) {
        setSysId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value4(String value) {
        setMobile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value5(String value) {
        setAccountName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value6(String value) {
        setWxNickName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value8(Byte value) {
        setAccountType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value9(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value10(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value11(String value) {
        setAccountPasswd(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value12(String value) {
        setStoreList(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord value13(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreAccountRecord values(Integer value1, Integer value2, Integer value3, String value4, String value5, String value6, Timestamp value7, Byte value8, Byte value9, Byte value10, String value11, String value12, Timestamp value13) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StoreAccountRecord
     */
    public StoreAccountRecord() {
        super(StoreAccount.STORE_ACCOUNT);
    }

    /**
     * Create a detached, initialised StoreAccountRecord
     */
    public StoreAccountRecord(Integer accountId, Integer shopId, Integer sysId, String mobile, String accountName, String wxNickName, Timestamp createTime, Byte accountType, Byte status, Byte delFlag, String accountPasswd, String storeList, Timestamp updateTime) {
        super(StoreAccount.STORE_ACCOUNT);

        set(0, accountId);
        set(1, shopId);
        set(2, sysId);
        set(3, mobile);
        set(4, accountName);
        set(5, wxNickName);
        set(6, createTime);
        set(7, accountType);
        set(8, status);
        set(9, delFlag);
        set(10, accountPasswd);
        set(11, storeList);
        set(12, updateTime);
    }
}
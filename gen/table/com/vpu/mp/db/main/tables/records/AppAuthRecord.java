/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.AppAuth;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;


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
public class AppAuthRecord extends UpdatableRecordImpl<AppAuthRecord> implements Record13<Short, Byte, Integer, Integer, String, String, String, String, Byte, Byte, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = -752961395;

    /**
     * Setter for <code>mini_main.b2c_app_auth.id</code>.
     */
    public void setId(Short value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.id</code>.
     */
    public Short getId() {
        return (Short) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.action</code>. 1：erp 2：pos
     */
    public void setAction(Byte value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.action</code>. 1：erp 2：pos
     */
    public Byte getAction() {
        return (Byte) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.sys_id</code>.
     */
    public void setSysId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.sys_id</code>.
     */
    public Integer getSysId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.session_key</code>. 授权key
     */
    public void setSessionKey(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.session_key</code>. 授权key
     */
    public String getSessionKey() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.pos_session_key</code>. pos授权key
     */
    public void setPosSessionKey(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.pos_session_key</code>. pos授权key
     */
    public String getPosSessionKey() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.app_key</code>. crm分配的appKey
     */
    public void setAppKey(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.app_key</code>. crm分配的appKey
     */
    public String getAppKey() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.app_secret</code>. crm分配的appSecret
     */
    public void setAppSecret(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.app_secret</code>. crm分配的appSecret
     */
    public String getAppSecret() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.product</code>. 产品：1 ERP企业版 2：ERP旗舰版
     */
    public void setProduct(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.product</code>. 产品：1 ERP企业版 2：ERP旗舰版
     */
    public Byte getProduct() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.is_sync</code>. 是否已同步
     */
    public void setIsSync(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.is_sync</code>. 是否已同步
     */
    public Byte getIsSync() {
        return (Byte) get(9);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.status</code>. 1：启用 0：禁用
     */
    public void setStatus(Byte value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.status</code>. 1：启用 0：禁用
     */
    public Byte getStatus() {
        return (Byte) get(10);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.add_time</code>.
     */
    public void setAddTime(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.add_time</code>.
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>mini_main.b2c_app_auth.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app_auth.update_time</code>.
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
    public Record1<Short> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Short, Byte, Integer, Integer, String, String, String, String, Byte, Byte, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Short, Byte, Integer, Integer, String, String, String, String, Byte, Byte, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field1() {
        return AppAuth.APP_AUTH.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field2() {
        return AppAuth.APP_AUTH.ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return AppAuth.APP_AUTH.SYS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return AppAuth.APP_AUTH.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return AppAuth.APP_AUTH.SESSION_KEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return AppAuth.APP_AUTH.POS_SESSION_KEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return AppAuth.APP_AUTH.APP_KEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return AppAuth.APP_AUTH.APP_SECRET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return AppAuth.APP_AUTH.PRODUCT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return AppAuth.APP_AUTH.IS_SYNC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field11() {
        return AppAuth.APP_AUTH.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field12() {
        return AppAuth.APP_AUTH.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return AppAuth.APP_AUTH.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component2() {
        return getAction();
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
    public Integer component4() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getSessionKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getPosSessionKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getAppKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getAppSecret();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component9() {
        return getProduct();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component10() {
        return getIsSync();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component11() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component12() {
        return getAddTime();
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
    public Short value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value2() {
        return getAction();
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
    public Integer value4() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getSessionKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getPosSessionKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getAppKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getAppSecret();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getProduct();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getIsSync();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value11() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value12() {
        return getAddTime();
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
    public AppAuthRecord value1(Short value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value2(Byte value) {
        setAction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value3(Integer value) {
        setSysId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value4(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value5(String value) {
        setSessionKey(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value6(String value) {
        setPosSessionKey(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value7(String value) {
        setAppKey(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value8(String value) {
        setAppSecret(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value9(Byte value) {
        setProduct(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value10(Byte value) {
        setIsSync(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value11(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value12(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord value13(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppAuthRecord values(Short value1, Byte value2, Integer value3, Integer value4, String value5, String value6, String value7, String value8, Byte value9, Byte value10, Byte value11, Timestamp value12, Timestamp value13) {
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
     * Create a detached AppAuthRecord
     */
    public AppAuthRecord() {
        super(AppAuth.APP_AUTH);
    }

    /**
     * Create a detached, initialised AppAuthRecord
     */
    public AppAuthRecord(Short id, Byte action, Integer sysId, Integer shopId, String sessionKey, String posSessionKey, String appKey, String appSecret, Byte product, Byte isSync, Byte status, Timestamp addTime, Timestamp updateTime) {
        super(AppAuth.APP_AUTH);

        set(0, id);
        set(1, action);
        set(2, sysId);
        set(3, shopId);
        set(4, sessionKey);
        set(5, posSessionKey);
        set(6, appKey);
        set(7, appSecret);
        set(8, product);
        set(9, isSync);
        set(10, status);
        set(11, addTime);
        set(12, updateTime);
    }
}

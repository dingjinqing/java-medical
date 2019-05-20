/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.AliMiniAppVersion;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
public class AliMiniAppVersionRecord extends UpdatableRecordImpl<AliMiniAppVersionRecord> implements Record4<UInteger, String, String, Timestamp> {

    private static final long serialVersionUID = -2085011723;

    /**
     * Setter for <code>mini_main.b2c_ali_mini_app_version.rec_id</code>.
     */
    public void setRecId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_app_version.rec_id</code>.
     */
    public UInteger getRecId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_app_version.app_version</code>. 小程序版本号
     */
    public void setAppVersion(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_app_version.app_version</code>. 小程序版本号
     */
    public String getAppVersion() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_app_version.app_version_desc</code>. 小程序版本描述
     */
    public void setAppVersionDesc(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_app_version.app_version_desc</code>. 小程序版本描述
     */
    public String getAppVersionDesc() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_ali_mini_app_version.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_ali_mini_app_version.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<UInteger, String, String, Timestamp> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<UInteger, String, String, Timestamp> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return AliMiniAppVersion.ALI_MINI_APP_VERSION.REC_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return AliMiniAppVersion.ALI_MINI_APP_VERSION.APP_VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return AliMiniAppVersion.ALI_MINI_APP_VERSION.APP_VERSION_DESC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return AliMiniAppVersion.ALI_MINI_APP_VERSION.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component1() {
        return getRecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getAppVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getAppVersionDesc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getRecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getAppVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getAppVersionDesc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AliMiniAppVersionRecord value1(UInteger value) {
        setRecId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AliMiniAppVersionRecord value2(String value) {
        setAppVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AliMiniAppVersionRecord value3(String value) {
        setAppVersionDesc(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AliMiniAppVersionRecord value4(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AliMiniAppVersionRecord values(UInteger value1, String value2, String value3, Timestamp value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AliMiniAppVersionRecord
     */
    public AliMiniAppVersionRecord() {
        super(AliMiniAppVersion.ALI_MINI_APP_VERSION);
    }

    /**
     * Create a detached, initialised AliMiniAppVersionRecord
     */
    public AliMiniAppVersionRecord(UInteger recId, String appVersion, String appVersionDesc, Timestamp createTime) {
        super(AliMiniAppVersion.ALI_MINI_APP_VERSION);

        set(0, recId);
        set(1, appVersion);
        set(2, appVersionDesc);
        set(3, createTime);
    }
}

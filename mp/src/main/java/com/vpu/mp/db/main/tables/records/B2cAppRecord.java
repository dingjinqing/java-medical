/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.B2cApp;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.TableRecordImpl;


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
public class B2cAppRecord extends TableRecordImpl<B2cAppRecord> implements Record4<String, String, String, Timestamp> {

    private static final long serialVersionUID = -399143753;

    /**
     * Setter for <code>mini_main.b2c_app.app_id</code>.
     */
    public void setAppId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app.app_id</code>.
     */
    public String getAppId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_app.app_name</code>. 应用名称
     */
    public void setAppName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app.app_name</code>. 应用名称
     */
    public String getAppName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_app.app_secret</code>.
     */
    public void setAppSecret(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app.app_secret</code>.
     */
    public String getAppSecret() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_app.add_time</code>.
     */
    public void setAddTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_app.add_time</code>.
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, String, Timestamp> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, String, Timestamp> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return B2cApp.B2C_APP.APP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cApp.B2C_APP.APP_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return B2cApp.B2C_APP.APP_SECRET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return B2cApp.B2C_APP.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getAppId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getAppName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getAppSecret();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getAppId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getAppName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getAppSecret();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAppRecord value1(String value) {
        setAppId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAppRecord value2(String value) {
        setAppName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAppRecord value3(String value) {
        setAppSecret(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAppRecord value4(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cAppRecord values(String value1, String value2, String value3, Timestamp value4) {
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
     * Create a detached B2cAppRecord
     */
    public B2cAppRecord() {
        super(B2cApp.B2C_APP);
    }

    /**
     * Create a detached, initialised B2cAppRecord
     */
    public B2cAppRecord(String appId, String appName, String appSecret, Timestamp addTime) {
        super(B2cApp.B2C_APP);

        set(0, appId);
        set(1, appName);
        set(2, appSecret);
        set(3, addTime);
    }
}

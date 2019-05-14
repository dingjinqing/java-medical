/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cRebatePriceRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
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
public class B2cRebatePriceRecordRecord extends UpdatableRecordImpl<B2cRebatePriceRecordRecord> implements Record5<UInteger, Integer, String, String, Timestamp> {

    private static final long serialVersionUID = -693198626;

    /**
     * Setter for <code>mini_shop_471752.b2c_rebate_price_record.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_rebate_price_record.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_rebate_price_record.user_id</code>. 用户ID
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_rebate_price_record.user_id</code>. 用户ID
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_rebate_price_record.data_sign</code>. md5(rebate_data)
     */
    public void setDataSign(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_rebate_price_record.data_sign</code>. md5(rebate_data)
     */
    public String getDataSign() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_rebate_price_record.rebate_data</code>. 分享内容
     */
    public void setRebateData(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_rebate_price_record.rebate_data</code>. 分享内容
     */
    public String getRebateData() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_rebate_price_record.add_time</code>. 分享时间
     */
    public void setAddTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_rebate_price_record.add_time</code>. 分享时间
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<UInteger, Integer, String, String, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<UInteger, Integer, String, String, Timestamp> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return B2cRebatePriceRecord.B2C_REBATE_PRICE_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cRebatePriceRecord.B2C_REBATE_PRICE_RECORD.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return B2cRebatePriceRecord.B2C_REBATE_PRICE_RECORD.DATA_SIGN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cRebatePriceRecord.B2C_REBATE_PRICE_RECORD.REBATE_DATA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return B2cRebatePriceRecord.B2C_REBATE_PRICE_RECORD.ADD_TIME;
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
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getDataSign();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getRebateData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component5() {
        return getAddTime();
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
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDataSign();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getRebateData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cRebatePriceRecordRecord value1(UInteger value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cRebatePriceRecordRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cRebatePriceRecordRecord value3(String value) {
        setDataSign(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cRebatePriceRecordRecord value4(String value) {
        setRebateData(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cRebatePriceRecordRecord value5(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cRebatePriceRecordRecord values(UInteger value1, Integer value2, String value3, String value4, Timestamp value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cRebatePriceRecordRecord
     */
    public B2cRebatePriceRecordRecord() {
        super(B2cRebatePriceRecord.B2C_REBATE_PRICE_RECORD);
    }

    /**
     * Create a detached, initialised B2cRebatePriceRecordRecord
     */
    public B2cRebatePriceRecordRecord(UInteger id, Integer userId, String dataSign, String rebateData, Timestamp addTime) {
        super(B2cRebatePriceRecord.B2C_REBATE_PRICE_RECORD);

        set(0, id);
        set(1, userId);
        set(2, dataSign);
        set(3, rebateData);
        set(4, addTime);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cIndexFootRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class B2cIndexFootRecordRecord extends UpdatableRecordImpl<B2cIndexFootRecordRecord> implements Record6<Integer, Integer, Integer, Timestamp, Integer, Byte> {

    private static final long serialVersionUID = -140272743;

    /**
     * Setter for <code>mini_shop_471752.b2c_index_foot_record.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_index_foot_record.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_index_foot_record.page_id</code>. 自定义页面ID
     */
    public void setPageId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_index_foot_record.page_id</code>. 自定义页面ID
     */
    public Integer getPageId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_index_foot_record.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_index_foot_record.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_index_foot_record.update_time</code>. 浏览时间
     */
    public void setUpdateTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_index_foot_record.update_time</code>. 浏览时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_index_foot_record.count</code>. 浏览次数
     */
    public void setCount(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_index_foot_record.count</code>. 浏览次数
     */
    public Integer getCount() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_index_foot_record.type</code>. 0 老用户 1新用户
     */
    public void setType(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_index_foot_record.type</code>. 0 老用户 1新用户
     */
    public Byte getType() {
        return (Byte) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, Timestamp, Integer, Byte> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, Timestamp, Integer, Byte> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cIndexFootRecord.B2C_INDEX_FOOT_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cIndexFootRecord.B2C_INDEX_FOOT_RECORD.PAGE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cIndexFootRecord.B2C_INDEX_FOOT_RECORD.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return B2cIndexFootRecord.B2C_INDEX_FOOT_RECORD.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return B2cIndexFootRecord.B2C_INDEX_FOOT_RECORD.COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return B2cIndexFootRecord.B2C_INDEX_FOOT_RECORD.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getPageId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getUpdateTime();
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
    public Byte component6() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getPageId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getUpdateTime();
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
    public Byte value6() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIndexFootRecordRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIndexFootRecordRecord value2(Integer value) {
        setPageId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIndexFootRecordRecord value3(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIndexFootRecordRecord value4(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIndexFootRecordRecord value5(Integer value) {
        setCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIndexFootRecordRecord value6(Byte value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIndexFootRecordRecord values(Integer value1, Integer value2, Integer value3, Timestamp value4, Integer value5, Byte value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cIndexFootRecordRecord
     */
    public B2cIndexFootRecordRecord() {
        super(B2cIndexFootRecord.B2C_INDEX_FOOT_RECORD);
    }

    /**
     * Create a detached, initialised B2cIndexFootRecordRecord
     */
    public B2cIndexFootRecordRecord(Integer id, Integer pageId, Integer userId, Timestamp updateTime, Integer count, Byte type) {
        super(B2cIndexFootRecord.B2C_INDEX_FOOT_RECORD);

        set(0, id);
        set(1, pageId);
        set(2, userId);
        set(3, updateTime);
        set(4, count);
        set(5, type);
    }
}

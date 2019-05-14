/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cTag;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class B2cTagRecord extends UpdatableRecordImpl<B2cTagRecord> implements Record3<Integer, String, Timestamp> {

    private static final long serialVersionUID = -153776818;

    /**
     * Setter for <code>mini_shop_471752.b2c_tag.tag_id</code>.
     */
    public void setTagId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_tag.tag_id</code>.
     */
    public Integer getTagId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_tag.tag_name</code>.
     */
    public void setTagName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_tag.tag_name</code>.
     */
    public String getTagName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_tag.in_time</code>.
     */
    public void setInTime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_tag.in_time</code>.
     */
    public Timestamp getInTime() {
        return (Timestamp) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, Timestamp> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, Timestamp> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cTag.B2C_TAG.TAG_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cTag.B2C_TAG.TAG_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return B2cTag.B2C_TAG.IN_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getTagId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getTagName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component3() {
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getTagId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getTagName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTagRecord value1(Integer value) {
        setTagId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTagRecord value2(String value) {
        setTagName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTagRecord value3(Timestamp value) {
        setInTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cTagRecord values(Integer value1, String value2, Timestamp value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cTagRecord
     */
    public B2cTagRecord() {
        super(B2cTag.B2C_TAG);
    }

    /**
     * Create a detached, initialised B2cTagRecord
     */
    public B2cTagRecord(Integer tagId, String tagName, Timestamp inTime) {
        super(B2cTag.B2C_TAG);

        set(0, tagId);
        set(1, tagName);
        set(2, inTime);
    }
}

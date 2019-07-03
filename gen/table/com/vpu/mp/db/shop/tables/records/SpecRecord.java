/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.Spec;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
public class SpecRecord extends UpdatableRecordImpl<SpecRecord> implements Record4<Integer, String, Byte, Integer> {

    private static final long serialVersionUID = -99047391;

    /**
     * Setter for <code>mini_shop_471752.b2c_spec.spec_id</code>.
     */
    public void setSpecId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_spec.spec_id</code>.
     */
    public Integer getSpecId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_spec.spec_name</code>.
     */
    public void setSpecName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_spec.spec_name</code>.
     */
    public String getSpecName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_spec.del_flag</code>.
     */
    public void setDelFlag(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_spec.del_flag</code>.
     */
    public Byte getDelFlag() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_spec.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_spec.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, Byte, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, Byte, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Spec.SPEC.SPEC_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Spec.SPEC.SPEC_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return Spec.SPEC.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return Spec.SPEC.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getSpecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getSpecName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component3() {
        return getDelFlag();
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
    public Integer value1() {
        return getSpecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getSpecName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getDelFlag();
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
    public SpecRecord value1(Integer value) {
        setSpecId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpecRecord value2(String value) {
        setSpecName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpecRecord value3(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpecRecord value4(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpecRecord values(Integer value1, String value2, Byte value3, Integer value4) {
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
     * Create a detached SpecRecord
     */
    public SpecRecord() {
        super(Spec.SPEC);
    }

    /**
     * Create a detached, initialised SpecRecord
     */
    public SpecRecord(Integer specId, String specName, Byte delFlag, Integer shopId) {
        super(Spec.SPEC);

        set(0, specId);
        set(1, specName);
        set(2, delFlag);
        set(3, shopId);
    }
}

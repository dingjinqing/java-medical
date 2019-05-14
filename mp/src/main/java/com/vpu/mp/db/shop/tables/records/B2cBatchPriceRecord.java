/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cBatchPrice;

import java.math.BigDecimal;

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
public class B2cBatchPriceRecord extends UpdatableRecordImpl<B2cBatchPriceRecord> implements Record4<UInteger, String, BigDecimal, Integer> {

    private static final long serialVersionUID = 1412979219;

    /**
     * Setter for <code>mini_shop_471752.b2c_batch_price.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_batch_price.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_batch_price.prd_sn</code>. 商家编码
     */
    public void setPrdSn(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_batch_price.prd_sn</code>. 商家编码
     */
    public String getPrdSn() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_batch_price.price</code>. 规格价格
     */
    public void setPrice(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_batch_price.price</code>. 规格价格
     */
    public BigDecimal getPrice() {
        return (BigDecimal) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_batch_price.act_id</code>. 导入批次
     */
    public void setActId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_batch_price.act_id</code>. 导入批次
     */
    public Integer getActId() {
        return (Integer) get(3);
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
    public Row4<UInteger, String, BigDecimal, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<UInteger, String, BigDecimal, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return B2cBatchPrice.B2C_BATCH_PRICE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cBatchPrice.B2C_BATCH_PRICE.PRD_SN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field3() {
        return B2cBatchPrice.B2C_BATCH_PRICE.PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return B2cBatchPrice.B2C_BATCH_PRICE.ACT_ID;
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
    public String component2() {
        return getPrdSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component3() {
        return getPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getActId();
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
    public String value2() {
        return getPrdSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value3() {
        return getPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getActId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cBatchPriceRecord value1(UInteger value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cBatchPriceRecord value2(String value) {
        setPrdSn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cBatchPriceRecord value3(BigDecimal value) {
        setPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cBatchPriceRecord value4(Integer value) {
        setActId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cBatchPriceRecord values(UInteger value1, String value2, BigDecimal value3, Integer value4) {
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
     * Create a detached B2cBatchPriceRecord
     */
    public B2cBatchPriceRecord() {
        super(B2cBatchPrice.B2C_BATCH_PRICE);
    }

    /**
     * Create a detached, initialised B2cBatchPriceRecord
     */
    public B2cBatchPriceRecord(UInteger id, String prdSn, BigDecimal price, Integer actId) {
        super(B2cBatchPrice.B2C_BATCH_PRICE);

        set(0, id);
        set(1, prdSn);
        set(2, price);
        set(3, actId);
    }
}

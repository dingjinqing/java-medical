/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.ShopCfg;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;
import org.jooq.types.UShort;


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
public class ShopCfgRecord extends UpdatableRecordImpl<ShopCfgRecord> implements Record4<UShort, UInteger, String, String> {

    private static final long serialVersionUID = -866762281;

    /**
     * Setter for <code>mini_shop_1.b2c_shop_cfg.rec_id</code>.
     */
    public void setRecId(UShort value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop_cfg.rec_id</code>.
     */
    public UShort getRecId() {
        return (UShort) get(0);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop_cfg.shop_id</code>. 店铺id
     */
    public void setShopId(UInteger value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop_cfg.shop_id</code>. 店铺id
     */
    public UInteger getShopId() {
        return (UInteger) get(1);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop_cfg.k</code>.
     */
    public void setK(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop_cfg.k</code>.
     */
    public String getK() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_shop_cfg.v</code>.
     */
    public void setV(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_shop_cfg.v</code>.
     */
    public String getV() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UShort> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<UShort, UInteger, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<UShort, UInteger, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UShort> field1() {
        return ShopCfg.SHOP_CFG.REC_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field2() {
        return ShopCfg.SHOP_CFG.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ShopCfg.SHOP_CFG.K;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ShopCfg.SHOP_CFG.V;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UShort component1() {
        return getRecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getK();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getV();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UShort value1() {
        return getRecId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getK();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getV();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopCfgRecord value1(UShort value) {
        setRecId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopCfgRecord value2(UInteger value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopCfgRecord value3(String value) {
        setK(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopCfgRecord value4(String value) {
        setV(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopCfgRecord values(UShort value1, UInteger value2, String value3, String value4) {
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
     * Create a detached ShopCfgRecord
     */
    public ShopCfgRecord() {
        super(ShopCfg.SHOP_CFG);
    }

    /**
     * Create a detached, initialised ShopCfgRecord
     */
    public ShopCfgRecord(UShort recId, UInteger shopId, String k, String v) {
        super(ShopCfg.SHOP_CFG);

        set(0, recId);
        set(1, shopId);
        set(2, k);
        set(3, v);
    }
}

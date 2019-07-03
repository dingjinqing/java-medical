/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.StoreGoods;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record10;
import org.jooq.Record4;
import org.jooq.Row10;
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
public class StoreGoodsRecord extends UpdatableRecordImpl<StoreGoodsRecord> implements Record10<Integer, Integer, Integer, String, Integer, BigDecimal, Byte, Byte, Timestamp, Byte> {

    private static final long serialVersionUID = 1121550282;

    /**
     * Setter for <code>mini_shop_471752.b2c_store_goods.store_id</code>.
     */
    public void setStoreId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_goods.store_id</code>.
     */
    public Integer getStoreId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_goods.goods_id</code>.
     */
    public void setGoodsId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_goods.goods_id</code>.
     */
    public Integer getGoodsId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_goods.prd_id</code>.
     */
    public void setPrdId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_goods.prd_id</code>.
     */
    public Integer getPrdId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_goods.prd_sn</code>.
     */
    public void setPrdSn(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_goods.prd_sn</code>.
     */
    public String getPrdSn() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_goods.product_number</code>. 库存
     */
    public void setProductNumber(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_goods.product_number</code>. 库存
     */
    public Integer getProductNumber() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_goods.product_price</code>. 价格
     */
    public void setProductPrice(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_goods.product_price</code>. 价格
     */
    public BigDecimal getProductPrice() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_goods.is_sync</code>. 是否已同步
     */
    public void setIsSync(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_goods.is_sync</code>. 是否已同步
     */
    public Byte getIsSync() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_goods.is_on_sale</code>. '是否在售，1在售，0下架'
     */
    public void setIsOnSale(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_goods.is_on_sale</code>. '是否在售，1在售，0下架'
     */
    public Byte getIsOnSale() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_goods.up_time</code>.
     */
    public void setUpTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_goods.up_time</code>.
     */
    public Timestamp getUpTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_store_goods.flag</code>. 1:初始化数据，0:无效数据
     */
    public void setFlag(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_store_goods.flag</code>. 1:初始化数据，0:无效数据
     */
    public Byte getFlag() {
        return (Byte) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record4<Integer, Integer, Integer, Byte> key() {
        return (Record4) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Integer, String, Integer, BigDecimal, Byte, Byte, Timestamp, Byte> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Integer, String, Integer, BigDecimal, Byte, Byte, Timestamp, Byte> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return StoreGoods.STORE_GOODS.STORE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return StoreGoods.STORE_GOODS.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return StoreGoods.STORE_GOODS.PRD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return StoreGoods.STORE_GOODS.PRD_SN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return StoreGoods.STORE_GOODS.PRODUCT_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field6() {
        return StoreGoods.STORE_GOODS.PRODUCT_PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return StoreGoods.STORE_GOODS.IS_SYNC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return StoreGoods.STORE_GOODS.IS_ON_SALE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return StoreGoods.STORE_GOODS.UP_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return StoreGoods.STORE_GOODS.FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getStoreId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getPrdId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getPrdSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getProductNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component6() {
        return getProductPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component7() {
        return getIsSync();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component8() {
        return getIsOnSale();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component10() {
        return getFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getStoreId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getPrdId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getPrdSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getProductNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value6() {
        return getProductPrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getIsSync();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getIsOnSale();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreGoodsRecord value1(Integer value) {
        setStoreId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreGoodsRecord value2(Integer value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreGoodsRecord value3(Integer value) {
        setPrdId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreGoodsRecord value4(String value) {
        setPrdSn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreGoodsRecord value5(Integer value) {
        setProductNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreGoodsRecord value6(BigDecimal value) {
        setProductPrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreGoodsRecord value7(Byte value) {
        setIsSync(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreGoodsRecord value8(Byte value) {
        setIsOnSale(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreGoodsRecord value9(Timestamp value) {
        setUpTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreGoodsRecord value10(Byte value) {
        setFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreGoodsRecord values(Integer value1, Integer value2, Integer value3, String value4, Integer value5, BigDecimal value6, Byte value7, Byte value8, Timestamp value9, Byte value10) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StoreGoodsRecord
     */
    public StoreGoodsRecord() {
        super(StoreGoods.STORE_GOODS);
    }

    /**
     * Create a detached, initialised StoreGoodsRecord
     */
    public StoreGoodsRecord(Integer storeId, Integer goodsId, Integer prdId, String prdSn, Integer productNumber, BigDecimal productPrice, Byte isSync, Byte isOnSale, Timestamp upTime, Byte flag) {
        super(StoreGoods.STORE_GOODS);

        set(0, storeId);
        set(1, goodsId);
        set(2, prdId);
        set(3, prdSn);
        set(4, productNumber);
        set(5, productPrice);
        set(6, isSync);
        set(7, isOnSale);
        set(8, upTime);
        set(9, flag);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.PurchasePriceDefine;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
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
public class PurchasePriceDefineRecord extends UpdatableRecordImpl<PurchasePriceDefineRecord> implements Record12<Integer, String, Short, Short, String, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Byte, Integer> {

    private static final long serialVersionUID = -42871041;

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.name</code>. 活动名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.name</code>. 活动名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.level</code>. 优先级
     */
    public void setLevel(Short value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.level</code>. 优先级
     */
    public Short getLevel() {
        return (Short) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.max_change_purchase</code>. 最大换购数
     */
    public void setMaxChangePurchase(Short value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.max_change_purchase</code>. 最大换购数
     */
    public Short getMaxChangePurchase() {
        return (Short) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.goods_id</code>. 主商品
     */
    public void setGoodsId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.goods_id</code>. 主商品
     */
    public String getGoodsId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.start_time</code>. 开始时间
     */
    public void setStartTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.start_time</code>. 开始时间
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.end_time</code>. 结束时间
     */
    public void setEndTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.end_time</code>. 结束时间
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.status</code>. 状态 1: 启用 0:禁用
     */
    public void setStatus(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.status</code>. 状态 1: 启用 0:禁用
     */
    public Byte getStatus() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.add_time</code>.
     */
    public void setAddTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.add_time</code>.
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.del_flag</code>.
     */
    public void setDelFlag(Byte value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.del_flag</code>.
     */
    public Byte getDelFlag() {
        return (Byte) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_purchase_price_define.del_time</code>.
     */
    public void setDelTime(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_purchase_price_define.del_time</code>.
     */
    public Integer getDelTime() {
        return (Integer) get(11);
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
    // Record12 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<Integer, String, Short, Short, String, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Byte, Integer> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<Integer, String, Short, Short, String, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Byte, Integer> valuesRow() {
        return (Row12) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field3() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field4() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.MAX_CHANGE_PURCHASE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.START_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.END_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field11() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return PurchasePriceDefine.PURCHASE_PRICE_DEFINE.DEL_TIME;
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
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component3() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component4() {
        return getMaxChangePurchase();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component8() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component11() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getDelTime();
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
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value3() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value4() {
        return getMaxChangePurchase();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value11() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value3(Short value) {
        setLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value4(Short value) {
        setMaxChangePurchase(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value5(String value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value6(Timestamp value) {
        setStartTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value7(Timestamp value) {
        setEndTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value8(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value9(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value10(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value11(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord value12(Integer value) {
        setDelTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PurchasePriceDefineRecord values(Integer value1, String value2, Short value3, Short value4, String value5, Timestamp value6, Timestamp value7, Byte value8, Timestamp value9, Timestamp value10, Byte value11, Integer value12) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PurchasePriceDefineRecord
     */
    public PurchasePriceDefineRecord() {
        super(PurchasePriceDefine.PURCHASE_PRICE_DEFINE);
    }

    /**
     * Create a detached, initialised PurchasePriceDefineRecord
     */
    public PurchasePriceDefineRecord(Integer id, String name, Short level, Short maxChangePurchase, String goodsId, Timestamp startTime, Timestamp endTime, Byte status, Timestamp addTime, Timestamp updateTime, Byte delFlag, Integer delTime) {
        super(PurchasePriceDefine.PURCHASE_PRICE_DEFINE);

        set(0, id);
        set(1, name);
        set(2, level);
        set(3, maxChangePurchase);
        set(4, goodsId);
        set(5, startTime);
        set(6, endTime);
        set(7, status);
        set(8, addTime);
        set(9, updateTime);
        set(10, delFlag);
        set(11, delTime);
    }
}

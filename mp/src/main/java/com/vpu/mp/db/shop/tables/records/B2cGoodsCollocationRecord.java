/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cGoodsCollocation;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
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
public class B2cGoodsCollocationRecord extends UpdatableRecordImpl<B2cGoodsCollocationRecord> implements Record11<Integer, Integer, Integer, Integer, Integer, Byte, Byte, BigDecimal, Timestamp, Timestamp, Byte> {

    private static final long serialVersionUID = -1693406537;

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_collocation.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_collocation.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_collocation.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_collocation.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_collocation.goods_id</code>.
     */
    public void setGoodsId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_collocation.goods_id</code>.
     */
    public Integer getGoodsId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_collocation.parent_id</code>. 自增ID
     */
    public void setParentId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_collocation.parent_id</code>. 自增ID
     */
    public Integer getParentId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_collocation.prd_id</code>. 从商品规格ID
     */
    public void setPrdId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_collocation.prd_id</code>. 从商品规格ID
     */
    public Integer getPrdId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_collocation.positon</code>. 位置 1为主商品 2为从商品
     */
    public void setPositon(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_collocation.positon</code>. 位置 1为主商品 2为从商品
     */
    public Byte getPositon() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_collocation.type</code>. 类型 1为搭配立减 2为套餐
     */
    public void setType(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_collocation.type</code>. 类型 1为搭配立减 2为套餐
     */
    public Byte getType() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_collocation.discount</code>. 优惠
     */
    public void setDiscount(BigDecimal value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_collocation.discount</code>. 优惠
     */
    public BigDecimal getDiscount() {
        return (BigDecimal) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_collocation.in_time</code>.
     */
    public void setInTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_collocation.in_time</code>.
     */
    public Timestamp getInTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_collocation.up_time</code>.
     */
    public void setUpTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_collocation.up_time</code>.
     */
    public Timestamp getUpTime() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_goods_collocation.flag</code>. 1有效 2无效
     */
    public void setFlag(Byte value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_goods_collocation.flag</code>. 1有效 2无效
     */
    public Byte getFlag() {
        return (Byte) get(10);
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
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Integer, Integer, Integer, Integer, Byte, Byte, BigDecimal, Timestamp, Timestamp, Byte> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Integer, Integer, Integer, Integer, Byte, Byte, BigDecimal, Timestamp, Timestamp, Byte> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cGoodsCollocation.B2C_GOODS_COLLOCATION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cGoodsCollocation.B2C_GOODS_COLLOCATION.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cGoodsCollocation.B2C_GOODS_COLLOCATION.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return B2cGoodsCollocation.B2C_GOODS_COLLOCATION.PARENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return B2cGoodsCollocation.B2C_GOODS_COLLOCATION.PRD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return B2cGoodsCollocation.B2C_GOODS_COLLOCATION.POSITON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return B2cGoodsCollocation.B2C_GOODS_COLLOCATION.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field8() {
        return B2cGoodsCollocation.B2C_GOODS_COLLOCATION.DISCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return B2cGoodsCollocation.B2C_GOODS_COLLOCATION.IN_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return B2cGoodsCollocation.B2C_GOODS_COLLOCATION.UP_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field11() {
        return B2cGoodsCollocation.B2C_GOODS_COLLOCATION.FLAG;
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
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getPrdId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component6() {
        return getPositon();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component7() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component8() {
        return getDiscount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component11() {
        return getFlag();
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
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getPrdId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getPositon();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value8() {
        return getDiscount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value11() {
        return getFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord value3(Integer value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord value4(Integer value) {
        setParentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord value5(Integer value) {
        setPrdId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord value6(Byte value) {
        setPositon(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord value7(Byte value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord value8(BigDecimal value) {
        setDiscount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord value9(Timestamp value) {
        setInTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord value10(Timestamp value) {
        setUpTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord value11(Byte value) {
        setFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCollocationRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Byte value6, Byte value7, BigDecimal value8, Timestamp value9, Timestamp value10, Byte value11) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cGoodsCollocationRecord
     */
    public B2cGoodsCollocationRecord() {
        super(B2cGoodsCollocation.B2C_GOODS_COLLOCATION);
    }

    /**
     * Create a detached, initialised B2cGoodsCollocationRecord
     */
    public B2cGoodsCollocationRecord(Integer id, Integer shopId, Integer goodsId, Integer parentId, Integer prdId, Byte positon, Byte type, BigDecimal discount, Timestamp inTime, Timestamp upTime, Byte flag) {
        super(B2cGoodsCollocation.B2C_GOODS_COLLOCATION);

        set(0, id);
        set(1, shopId);
        set(2, goodsId);
        set(3, parentId);
        set(4, prdId);
        set(5, positon);
        set(6, type);
        set(7, discount);
        set(8, inTime);
        set(9, upTime);
        set(10, flag);
    }
}

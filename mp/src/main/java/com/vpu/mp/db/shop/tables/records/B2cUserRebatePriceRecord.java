/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cUserRebatePrice;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
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
public class B2cUserRebatePriceRecord extends UpdatableRecordImpl<B2cUserRebatePriceRecord> implements Record8<UInteger, Integer, Integer, Integer, BigDecimal, Timestamp, Timestamp, Timestamp> {

    private static final long serialVersionUID = 398479608;

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rebate_price.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rebate_price.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rebate_price.user_id</code>. 用户ID
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rebate_price.user_id</code>. 用户ID
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rebate_price.goods_id</code>. 商品ID
     */
    public void setGoodsId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rebate_price.goods_id</code>. 商品ID
     */
    public Integer getGoodsId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rebate_price.product_id</code>. 产品ID
     */
    public void setProductId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rebate_price.product_id</code>. 产品ID
     */
    public Integer getProductId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rebate_price.advice_price</code>. 分销价格
     */
    public void setAdvicePrice(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rebate_price.advice_price</code>. 分销价格
     */
    public BigDecimal getAdvicePrice() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rebate_price.add_time</code>. 添加时间
     */
    public void setAddTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rebate_price.add_time</code>. 添加时间
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rebate_price.expire_time</code>. 过期时间
     */
    public void setExpireTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rebate_price.expire_time</code>. 过期时间
     */
    public Timestamp getExpireTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rebate_price.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rebate_price.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<UInteger, Integer, Integer, Integer, BigDecimal, Timestamp, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<UInteger, Integer, Integer, Integer, BigDecimal, Timestamp, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return B2cUserRebatePrice.B2C_USER_REBATE_PRICE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cUserRebatePrice.B2C_USER_REBATE_PRICE.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cUserRebatePrice.B2C_USER_REBATE_PRICE.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return B2cUserRebatePrice.B2C_USER_REBATE_PRICE.PRODUCT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field5() {
        return B2cUserRebatePrice.B2C_USER_REBATE_PRICE.ADVICE_PRICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return B2cUserRebatePrice.B2C_USER_REBATE_PRICE.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return B2cUserRebatePrice.B2C_USER_REBATE_PRICE.EXPIRE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return B2cUserRebatePrice.B2C_USER_REBATE_PRICE.UPDATE_TIME;
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
    public Integer component3() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getProductId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component5() {
        return getAdvicePrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getExpireTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component8() {
        return getUpdateTime();
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
    public Integer value3() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getProductId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value5() {
        return getAdvicePrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getExpireTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserRebatePriceRecord value1(UInteger value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserRebatePriceRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserRebatePriceRecord value3(Integer value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserRebatePriceRecord value4(Integer value) {
        setProductId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserRebatePriceRecord value5(BigDecimal value) {
        setAdvicePrice(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserRebatePriceRecord value6(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserRebatePriceRecord value7(Timestamp value) {
        setExpireTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserRebatePriceRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserRebatePriceRecord values(UInteger value1, Integer value2, Integer value3, Integer value4, BigDecimal value5, Timestamp value6, Timestamp value7, Timestamp value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cUserRebatePriceRecord
     */
    public B2cUserRebatePriceRecord() {
        super(B2cUserRebatePrice.B2C_USER_REBATE_PRICE);
    }

    /**
     * Create a detached, initialised B2cUserRebatePriceRecord
     */
    public B2cUserRebatePriceRecord(UInteger id, Integer userId, Integer goodsId, Integer productId, BigDecimal advicePrice, Timestamp addTime, Timestamp expireTime, Timestamp updateTime) {
        super(B2cUserRebatePrice.B2C_USER_REBATE_PRICE);

        set(0, id);
        set(1, userId);
        set(2, goodsId);
        set(3, productId);
        set(4, advicePrice);
        set(5, addTime);
        set(6, expireTime);
        set(7, updateTime);
    }
}

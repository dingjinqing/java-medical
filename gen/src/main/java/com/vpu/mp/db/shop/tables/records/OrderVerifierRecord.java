/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.OrderVerifier;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
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
public class OrderVerifierRecord extends UpdatableRecordImpl<OrderVerifierRecord> implements Record7<UInteger, UInteger, UInteger, Integer, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1460666960;

    /**
     * Setter for <code>mini_shop_1.b2c_order_verifier.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_order_verifier.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_order_verifier.store_id</code>. 门店ID
     */
    public void setStoreId(UInteger value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_order_verifier.store_id</code>. 门店ID
     */
    public UInteger getStoreId() {
        return (UInteger) get(1);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_order_verifier.user_id</code>. 用户ID
     */
    public void setUserId(UInteger value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_order_verifier.user_id</code>. 用户ID
     */
    public UInteger getUserId() {
        return (UInteger) get(2);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_order_verifier.verify_orders</code>. 核销订单数
     */
    public void setVerifyOrders(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_order_verifier.verify_orders</code>. 核销订单数
     */
    public Integer getVerifyOrders() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_order_verifier.del_flag</code>. 删除
     */
    public void setDelFlag(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_order_verifier.del_flag</code>. 删除
     */
    public Byte getDelFlag() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_order_verifier.add_time</code>. 添加时间
     */
    public void setAddTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_order_verifier.add_time</code>. 添加时间
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_order_verifier.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_order_verifier.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<UInteger, UInteger, UInteger, Integer, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<UInteger, UInteger, UInteger, Integer, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return OrderVerifier.ORDER_VERIFIER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field2() {
        return OrderVerifier.ORDER_VERIFIER.STORE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field3() {
        return OrderVerifier.ORDER_VERIFIER.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return OrderVerifier.ORDER_VERIFIER.VERIFY_ORDERS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return OrderVerifier.ORDER_VERIFIER.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return OrderVerifier.ORDER_VERIFIER.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return OrderVerifier.ORDER_VERIFIER.UPDATE_TIME;
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
    public UInteger component2() {
        return getStoreId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getVerifyOrders();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component5() {
        return getDelFlag();
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
    public UInteger value2() {
        return getStoreId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getVerifyOrders();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getDelFlag();
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
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderVerifierRecord value1(UInteger value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderVerifierRecord value2(UInteger value) {
        setStoreId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderVerifierRecord value3(UInteger value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderVerifierRecord value4(Integer value) {
        setVerifyOrders(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderVerifierRecord value5(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderVerifierRecord value6(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderVerifierRecord value7(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderVerifierRecord values(UInteger value1, UInteger value2, UInteger value3, Integer value4, Byte value5, Timestamp value6, Timestamp value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OrderVerifierRecord
     */
    public OrderVerifierRecord() {
        super(OrderVerifier.ORDER_VERIFIER);
    }

    /**
     * Create a detached, initialised OrderVerifierRecord
     */
    public OrderVerifierRecord(UInteger id, UInteger storeId, UInteger userId, Integer verifyOrders, Byte delFlag, Timestamp addTime, Timestamp updateTime) {
        super(OrderVerifier.ORDER_VERIFIER);

        set(0, id);
        set(1, storeId);
        set(2, userId);
        set(3, verifyOrders);
        set(4, delFlag);
        set(5, addTime);
        set(6, updateTime);
    }
}

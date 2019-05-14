/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cCouponPayrewardRecord;

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
public class B2cCouponPayrewardRecordRecord extends UpdatableRecordImpl<B2cCouponPayrewardRecordRecord> implements Record6<Integer, Integer, Integer, Timestamp, String, String> {

    private static final long serialVersionUID = 1253842452;

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_payreward_record.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_payreward_record.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_payreward_record.activity_id</code>. 活动ID
     */
    public void setActivityId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_payreward_record.activity_id</code>. 活动ID
     */
    public Integer getActivityId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_payreward_record.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_payreward_record.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_payreward_record.receive_time</code>. 领取时间
     */
    public void setReceiveTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_payreward_record.receive_time</code>. 领取时间
     */
    public Timestamp getReceiveTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_payreward_record.mrking_voucher_id</code>. 已领取的优惠券
     */
    public void setMrkingVoucherId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_payreward_record.mrking_voucher_id</code>. 已领取的优惠券
     */
    public String getMrkingVoucherId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_payreward_record.order_sn</code>. 订单编号
     */
    public void setOrderSn(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_payreward_record.order_sn</code>. 订单编号
     */
    public String getOrderSn() {
        return (String) get(5);
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
    public Row6<Integer, Integer, Integer, Timestamp, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, Timestamp, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cCouponPayrewardRecord.B2C_COUPON_PAYREWARD_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cCouponPayrewardRecord.B2C_COUPON_PAYREWARD_RECORD.ACTIVITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cCouponPayrewardRecord.B2C_COUPON_PAYREWARD_RECORD.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return B2cCouponPayrewardRecord.B2C_COUPON_PAYREWARD_RECORD.RECEIVE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return B2cCouponPayrewardRecord.B2C_COUPON_PAYREWARD_RECORD.MRKING_VOUCHER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return B2cCouponPayrewardRecord.B2C_COUPON_PAYREWARD_RECORD.ORDER_SN;
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
        return getActivityId();
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
        return getReceiveTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getMrkingVoucherId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getOrderSn();
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
        return getActivityId();
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
        return getReceiveTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getMrkingVoucherId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCouponPayrewardRecordRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCouponPayrewardRecordRecord value2(Integer value) {
        setActivityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCouponPayrewardRecordRecord value3(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCouponPayrewardRecordRecord value4(Timestamp value) {
        setReceiveTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCouponPayrewardRecordRecord value5(String value) {
        setMrkingVoucherId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCouponPayrewardRecordRecord value6(String value) {
        setOrderSn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCouponPayrewardRecordRecord values(Integer value1, Integer value2, Integer value3, Timestamp value4, String value5, String value6) {
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
     * Create a detached B2cCouponPayrewardRecordRecord
     */
    public B2cCouponPayrewardRecordRecord() {
        super(B2cCouponPayrewardRecord.B2C_COUPON_PAYREWARD_RECORD);
    }

    /**
     * Create a detached, initialised B2cCouponPayrewardRecordRecord
     */
    public B2cCouponPayrewardRecordRecord(Integer id, Integer activityId, Integer userId, Timestamp receiveTime, String mrkingVoucherId, String orderSn) {
        super(B2cCouponPayrewardRecord.B2C_COUPON_PAYREWARD_RECORD);

        set(0, id);
        set(1, activityId);
        set(2, userId);
        set(3, receiveTime);
        set(4, mrkingVoucherId);
        set(5, orderSn);
    }
}

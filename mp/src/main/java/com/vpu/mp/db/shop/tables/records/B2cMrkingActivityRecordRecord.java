/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cMrkingActivityRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;
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
public class B2cMrkingActivityRecordRecord extends UpdatableRecordImpl<B2cMrkingActivityRecordRecord> implements Record14<UInteger, Integer, UInteger, UInteger, UInteger, UInteger, String, String, String, UByte, BigDecimal, String, UInteger, Timestamp> {

    private static final long serialVersionUID = 658064578;

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.coupon_id</code>.
     */
    public void setCouponId(UInteger value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.coupon_id</code>.
     */
    public UInteger getCouponId() {
        return (UInteger) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.act_type</code>. user_id不为空时1:经销营等级打折,为空时1:首次下单优惠，2减价，3打折
     */
    public void setActType(UInteger value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.act_type</code>. user_id不为空时1:经销营等级打折,为空时1:首次下单优惠，2减价，3打折
     */
    public UInteger getActType() {
        return (UInteger) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.act_id</code>.
     */
    public void setActId(UInteger value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.act_id</code>.
     */
    public UInteger getActId() {
        return (UInteger) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.user_id</code>.
     */
    public void setUserId(UInteger value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.user_id</code>.
     */
    public UInteger getUserId() {
        return (UInteger) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.user_cid</code>.
     */
    public void setUserCid(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.user_cid</code>.
     */
    public String getUserCid() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.user_openid</code>.
     */
    public void setUserOpenid(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.user_openid</code>.
     */
    public String getUserOpenid() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.order_sn</code>.
     */
    public void setOrderSn(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.order_sn</code>.
     */
    public String getOrderSn() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.type</code>. 1为减价，2为打折
     */
    public void setType(UByte value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.type</code>. 1为减价，2为打折
     */
    public UByte getType() {
        return (UByte) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.amount</code>. 打折或减价量
     */
    public void setAmount(BigDecimal value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.amount</code>. 打折或减价量
     */
    public BigDecimal getAmount() {
        return (BigDecimal) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.act_desc</code>.
     */
    public void setActDesc(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.act_desc</code>.
     */
    public String getActDesc() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.limit_order_amount</code>.
     */
    public void setLimitOrderAmount(UInteger value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.limit_order_amount</code>.
     */
    public UInteger getLimitOrderAmount() {
        return (UInteger) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_activity_record.created</code>.
     */
    public void setCreated(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_activity_record.created</code>.
     */
    public Timestamp getCreated() {
        return (Timestamp) get(13);
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
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<UInteger, Integer, UInteger, UInteger, UInteger, UInteger, String, String, String, UByte, BigDecimal, String, UInteger, Timestamp> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<UInteger, Integer, UInteger, UInteger, UInteger, UInteger, String, String, String, UByte, BigDecimal, String, UInteger, Timestamp> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field3() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.COUPON_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field4() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.ACT_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field5() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.ACT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field6() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.USER_CID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.USER_OPENID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.ORDER_SN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UByte> field10() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field11() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.ACT_DESC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field13() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.LIMIT_ORDER_AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field14() {
        return B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD.CREATED;
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
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component3() {
        return getCouponId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component4() {
        return getActType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component5() {
        return getActId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component6() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getUserCid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getUserOpenid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UByte component10() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component11() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getActDesc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component13() {
        return getLimitOrderAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component14() {
        return getCreated();
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
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value3() {
        return getCouponId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value4() {
        return getActType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value5() {
        return getActId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value6() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getUserCid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getUserOpenid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UByte value10() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value11() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getActDesc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value13() {
        return getLimitOrderAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value14() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value1(UInteger value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value3(UInteger value) {
        setCouponId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value4(UInteger value) {
        setActType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value5(UInteger value) {
        setActId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value6(UInteger value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value7(String value) {
        setUserCid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value8(String value) {
        setUserOpenid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value9(String value) {
        setOrderSn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value10(UByte value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value11(BigDecimal value) {
        setAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value12(String value) {
        setActDesc(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value13(UInteger value) {
        setLimitOrderAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord value14(Timestamp value) {
        setCreated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMrkingActivityRecordRecord values(UInteger value1, Integer value2, UInteger value3, UInteger value4, UInteger value5, UInteger value6, String value7, String value8, String value9, UByte value10, BigDecimal value11, String value12, UInteger value13, Timestamp value14) {
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
        value13(value13);
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cMrkingActivityRecordRecord
     */
    public B2cMrkingActivityRecordRecord() {
        super(B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD);
    }

    /**
     * Create a detached, initialised B2cMrkingActivityRecordRecord
     */
    public B2cMrkingActivityRecordRecord(UInteger id, Integer shopId, UInteger couponId, UInteger actType, UInteger actId, UInteger userId, String userCid, String userOpenid, String orderSn, UByte type, BigDecimal amount, String actDesc, UInteger limitOrderAmount, Timestamp created) {
        super(B2cMrkingActivityRecord.B2C_MRKING_ACTIVITY_RECORD);

        set(0, id);
        set(1, shopId);
        set(2, couponId);
        set(3, actType);
        set(4, actId);
        set(5, userId);
        set(6, userCid);
        set(7, userOpenid);
        set(8, orderSn);
        set(9, type);
        set(10, amount);
        set(11, actDesc);
        set(12, limitOrderAmount);
        set(13, created);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.CouponPackVoucher;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
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
public class CouponPackVoucherRecord extends UpdatableRecordImpl<CouponPackVoucherRecord> implements Record10<Integer, Integer, Integer, Integer, Integer, Integer, Byte, Integer, Integer, Byte> {

    private static final long serialVersionUID = -59040900;

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack_voucher.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack_voucher.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack_voucher.voucher_id</code>. 优惠券id
     */
    public void setVoucherId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack_voucher.voucher_id</code>. 优惠券id
     */
    public Integer getVoucherId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack_voucher.act_id</code>. 所属优惠券礼包id
     */
    public void setActId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack_voucher.act_id</code>. 所属优惠券礼包id
     */
    public Integer getActId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack_voucher.total_amount</code>. 总数量
     */
    public void setTotalAmount(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack_voucher.total_amount</code>. 总数量
     */
    public Integer getTotalAmount() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack_voucher.immediately_grant_amount</code>. 立即发放数量
     */
    public void setImmediatelyGrantAmount(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack_voucher.immediately_grant_amount</code>. 立即发放数量
     */
    public Integer getImmediatelyGrantAmount() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack_voucher.timing_every</code>. 每个时间单位间隔（1为无间隔）
     */
    public void setTimingEvery(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack_voucher.timing_every</code>. 每个时间单位间隔（1为无间隔）
     */
    public Integer getTimingEvery() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack_voucher.timing_unit</code>. 定时发放的时间单位，0：自然天，1：自然周，2自然月
     */
    public void setTimingUnit(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack_voucher.timing_unit</code>. 定时发放的时间单位，0：自然天，1：自然周，2自然月
     */
    public Byte getTimingUnit() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack_voucher.timing_time</code>. 定时发放的时间,周1-7，月1-31，自然天不填
     */
    public void setTimingTime(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack_voucher.timing_time</code>. 定时发放的时间,周1-7，月1-31，自然天不填
     */
    public Integer getTimingTime() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack_voucher.timing_amount</code>. 定时发放的数量
     */
    public void setTimingAmount(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack_voucher.timing_amount</code>. 定时发放的数量
     */
    public Integer getTimingAmount() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack_voucher.del_flag</code>.
     */
    public void setDelFlag(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack_voucher.del_flag</code>.
     */
    public Byte getDelFlag() {
        return (Byte) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Integer, Integer, Integer, Integer, Byte, Integer, Integer, Byte> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Integer, Integer, Integer, Integer, Byte, Integer, Integer, Byte> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CouponPackVoucher.COUPON_PACK_VOUCHER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return CouponPackVoucher.COUPON_PACK_VOUCHER.VOUCHER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return CouponPackVoucher.COUPON_PACK_VOUCHER.ACT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return CouponPackVoucher.COUPON_PACK_VOUCHER.TOTAL_AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return CouponPackVoucher.COUPON_PACK_VOUCHER.IMMEDIATELY_GRANT_AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return CouponPackVoucher.COUPON_PACK_VOUCHER.TIMING_EVERY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return CouponPackVoucher.COUPON_PACK_VOUCHER.TIMING_UNIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return CouponPackVoucher.COUPON_PACK_VOUCHER.TIMING_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return CouponPackVoucher.COUPON_PACK_VOUCHER.TIMING_AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return CouponPackVoucher.COUPON_PACK_VOUCHER.DEL_FLAG;
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
        return getVoucherId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getActId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getTotalAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getImmediatelyGrantAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getTimingEvery();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component7() {
        return getTimingUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getTimingTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getTimingAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component10() {
        return getDelFlag();
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
        return getVoucherId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getActId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getTotalAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getImmediatelyGrantAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getTimingEvery();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getTimingUnit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getTimingTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getTimingAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackVoucherRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackVoucherRecord value2(Integer value) {
        setVoucherId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackVoucherRecord value3(Integer value) {
        setActId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackVoucherRecord value4(Integer value) {
        setTotalAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackVoucherRecord value5(Integer value) {
        setImmediatelyGrantAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackVoucherRecord value6(Integer value) {
        setTimingEvery(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackVoucherRecord value7(Byte value) {
        setTimingUnit(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackVoucherRecord value8(Integer value) {
        setTimingTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackVoucherRecord value9(Integer value) {
        setTimingAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackVoucherRecord value10(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackVoucherRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Integer value6, Byte value7, Integer value8, Integer value9, Byte value10) {
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
     * Create a detached CouponPackVoucherRecord
     */
    public CouponPackVoucherRecord() {
        super(CouponPackVoucher.COUPON_PACK_VOUCHER);
    }

    /**
     * Create a detached, initialised CouponPackVoucherRecord
     */
    public CouponPackVoucherRecord(Integer id, Integer voucherId, Integer actId, Integer totalAmount, Integer immediatelyGrantAmount, Integer timingEvery, Byte timingUnit, Integer timingTime, Integer timingAmount, Byte delFlag) {
        super(CouponPackVoucher.COUPON_PACK_VOUCHER);

        set(0, id);
        set(1, voucherId);
        set(2, actId);
        set(3, totalAmount);
        set(4, immediatelyGrantAmount);
        set(5, timingEvery);
        set(6, timingUnit);
        set(7, timingTime);
        set(8, timingAmount);
        set(9, delFlag);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.UserRfmSummary;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;


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
public class UserRfmSummaryRecord extends UpdatableRecordImpl<UserRfmSummaryRecord> implements Record9<Integer, Date, Byte, Byte, BigDecimal, Integer, Timestamp, Timestamp, Integer> {

    private static final long serialVersionUID = -1434712837;

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rfm_summary.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rfm_summary.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rfm_summary.ref_date</code>. 统计日期，如2018-09-04，按照下单时间
     */
    public void setRefDate(Date value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rfm_summary.ref_date</code>. 统计日期，如2018-09-04，按照下单时间
     */
    public Date getRefDate() {
        return (Date) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rfm_summary.recency_type</code>. 最近一次消费时间类型（小达顺序，左闭右开）：1最近5天内，2最近5到10天，3最近10到30天，4最近30到90天，5最近90到180天，6最近180到365天，7最近365天以上
     */
    public void setRecencyType(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rfm_summary.recency_type</code>. 最近一次消费时间类型（小达顺序，左闭右开）：1最近5天内，2最近5到10天，3最近10到30天，4最近30到90天，5最近90到180天，6最近180到365天，7最近365天以上
     */
    public Byte getRecencyType() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rfm_summary.frequency_type</code>. 最近时间范围内用户消费频次类型：1，2，3，4，5大于等于5次
     */
    public void setFrequencyType(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rfm_summary.frequency_type</code>. 最近时间范围内用户消费频次类型：1，2，3，4，5大于等于5次
     */
    public Byte getFrequencyType() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rfm_summary.total_paid_money</code>. 总成交金额
     */
    public void setTotalPaidMoney(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rfm_summary.total_paid_money</code>. 总成交金额
     */
    public BigDecimal getTotalPaidMoney() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rfm_summary.pay_user_num</code>. 下单人数（已付款订单人数）
     */
    public void setPayUserNum(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rfm_summary.pay_user_num</code>. 下单人数（已付款订单人数）
     */
    public Integer getPayUserNum() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rfm_summary.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rfm_summary.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rfm_summary.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rfm_summary.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_user_rfm_summary.order_num</code>. 订单数量（已付款订单数）
     */
    public void setOrderNum(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_user_rfm_summary.order_num</code>. 订单数量（已付款订单数）
     */
    public Integer getOrderNum() {
        return (Integer) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Date, Byte, Byte, BigDecimal, Integer, Timestamp, Timestamp, Integer> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Date, Byte, Byte, BigDecimal, Integer, Timestamp, Timestamp, Integer> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserRfmSummary.USER_RFM_SUMMARY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field2() {
        return UserRfmSummary.USER_RFM_SUMMARY.REF_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return UserRfmSummary.USER_RFM_SUMMARY.RECENCY_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return UserRfmSummary.USER_RFM_SUMMARY.FREQUENCY_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field5() {
        return UserRfmSummary.USER_RFM_SUMMARY.TOTAL_PAID_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return UserRfmSummary.USER_RFM_SUMMARY.PAY_USER_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return UserRfmSummary.USER_RFM_SUMMARY.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return UserRfmSummary.USER_RFM_SUMMARY.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return UserRfmSummary.USER_RFM_SUMMARY.ORDER_NUM;
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
    public Date component2() {
        return getRefDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component3() {
        return getRecencyType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component4() {
        return getFrequencyType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component5() {
        return getTotalPaidMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getPayUserNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getCreateTime();
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
    public Integer component9() {
        return getOrderNum();
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
    public Date value2() {
        return getRefDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getRecencyType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getFrequencyType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value5() {
        return getTotalPaidMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getPayUserNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreateTime();
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
    public Integer value9() {
        return getOrderNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRfmSummaryRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRfmSummaryRecord value2(Date value) {
        setRefDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRfmSummaryRecord value3(Byte value) {
        setRecencyType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRfmSummaryRecord value4(Byte value) {
        setFrequencyType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRfmSummaryRecord value5(BigDecimal value) {
        setTotalPaidMoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRfmSummaryRecord value6(Integer value) {
        setPayUserNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRfmSummaryRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRfmSummaryRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRfmSummaryRecord value9(Integer value) {
        setOrderNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRfmSummaryRecord values(Integer value1, Date value2, Byte value3, Byte value4, BigDecimal value5, Integer value6, Timestamp value7, Timestamp value8, Integer value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRfmSummaryRecord
     */
    public UserRfmSummaryRecord() {
        super(UserRfmSummary.USER_RFM_SUMMARY);
    }

    /**
     * Create a detached, initialised UserRfmSummaryRecord
     */
    public UserRfmSummaryRecord(Integer id, Date refDate, Byte recencyType, Byte frequencyType, BigDecimal totalPaidMoney, Integer payUserNum, Timestamp createTime, Timestamp updateTime, Integer orderNum) {
        super(UserRfmSummary.USER_RFM_SUMMARY);

        set(0, id);
        set(1, refDate);
        set(2, recencyType);
        set(3, frequencyType);
        set(4, totalPaidMoney);
        set(5, payUserNum);
        set(6, createTime);
        set(7, updateTime);
        set(8, orderNum);
    }
}

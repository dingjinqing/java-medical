/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.DoctorSummaryTrend;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 医师信息统计
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DoctorSummaryTrendRecord extends UpdatableRecordImpl<DoctorSummaryTrendRecord> implements Record11<Integer, Date, Byte, Integer, Integer, BigDecimal, Integer, BigDecimal, Integer, BigDecimal, Timestamp> {

    private static final long serialVersionUID = -241905031;

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_summary_trend.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_summary_trend.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_summary_trend.ref_date</code>. 2018-09-04
     */
    public void setRefDate(Date value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_summary_trend.ref_date</code>. 2018-09-04
     */
    public Date getRefDate() {
        return (Date) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_summary_trend.type</code>. 1,7,30,90
     */
    public void setType(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_summary_trend.type</code>. 1,7,30,90
     */
    public Byte getType() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_summary_trend.doctor_id</code>. 医师ID
     */
    public void setDoctorId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_summary_trend.doctor_id</code>. 医师ID
     */
    public Integer getDoctorId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_summary_trend.consultation_number</code>. 接诊量
     */
    public void setConsultationNumber(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_summary_trend.consultation_number</code>. 接诊量
     */
    public Integer getConsultationNumber() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_summary_trend.inquiry_money</code>. 咨询金额
     */
    public void setInquiryMoney(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_summary_trend.inquiry_money</code>. 咨询金额
     */
    public BigDecimal getInquiryMoney() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_summary_trend.inquiry_number</code>. 咨询单数
     */
    public void setInquiryNumber(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_summary_trend.inquiry_number</code>. 咨询单数
     */
    public Integer getInquiryNumber() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_summary_trend.prescription_money</code>. 处方金额
     */
    public void setPrescriptionMoney(BigDecimal value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_summary_trend.prescription_money</code>. 处方金额
     */
    public BigDecimal getPrescriptionMoney() {
        return (BigDecimal) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_summary_trend.prescription_num</code>. 处方数
     */
    public void setPrescriptionNum(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_summary_trend.prescription_num</code>. 处方数
     */
    public Integer getPrescriptionNum() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_summary_trend.consume_money</code>. 总消费金额
     */
    public void setConsumeMoney(BigDecimal value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_summary_trend.consume_money</code>. 总消费金额
     */
    public BigDecimal getConsumeMoney() {
        return (BigDecimal) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_summary_trend.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_summary_trend.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(10);
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
    public Row11<Integer, Date, Byte, Integer, Integer, BigDecimal, Integer, BigDecimal, Integer, BigDecimal, Timestamp> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Date, Byte, Integer, Integer, BigDecimal, Integer, BigDecimal, Integer, BigDecimal, Timestamp> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DoctorSummaryTrend.DOCTOR_SUMMARY_TREND.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field2() {
        return DoctorSummaryTrend.DOCTOR_SUMMARY_TREND.REF_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return DoctorSummaryTrend.DOCTOR_SUMMARY_TREND.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return DoctorSummaryTrend.DOCTOR_SUMMARY_TREND.DOCTOR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return DoctorSummaryTrend.DOCTOR_SUMMARY_TREND.CONSULTATION_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field6() {
        return DoctorSummaryTrend.DOCTOR_SUMMARY_TREND.INQUIRY_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return DoctorSummaryTrend.DOCTOR_SUMMARY_TREND.INQUIRY_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field8() {
        return DoctorSummaryTrend.DOCTOR_SUMMARY_TREND.PRESCRIPTION_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return DoctorSummaryTrend.DOCTOR_SUMMARY_TREND.PRESCRIPTION_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field10() {
        return DoctorSummaryTrend.DOCTOR_SUMMARY_TREND.CONSUME_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field11() {
        return DoctorSummaryTrend.DOCTOR_SUMMARY_TREND.CREATE_TIME;
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
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getDoctorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getConsultationNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component6() {
        return getInquiryMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getInquiryNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component8() {
        return getPrescriptionMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getPrescriptionNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component10() {
        return getConsumeMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component11() {
        return getCreateTime();
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
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getDoctorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getConsultationNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value6() {
        return getInquiryMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getInquiryNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value8() {
        return getPrescriptionMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getPrescriptionNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value10() {
        return getConsumeMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value11() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord value2(Date value) {
        setRefDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord value3(Byte value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord value4(Integer value) {
        setDoctorId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord value5(Integer value) {
        setConsultationNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord value6(BigDecimal value) {
        setInquiryMoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord value7(Integer value) {
        setInquiryNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord value8(BigDecimal value) {
        setPrescriptionMoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord value9(Integer value) {
        setPrescriptionNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord value10(BigDecimal value) {
        setConsumeMoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord value11(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorSummaryTrendRecord values(Integer value1, Date value2, Byte value3, Integer value4, Integer value5, BigDecimal value6, Integer value7, BigDecimal value8, Integer value9, BigDecimal value10, Timestamp value11) {
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
     * Create a detached DoctorSummaryTrendRecord
     */
    public DoctorSummaryTrendRecord() {
        super(DoctorSummaryTrend.DOCTOR_SUMMARY_TREND);
    }

    /**
     * Create a detached, initialised DoctorSummaryTrendRecord
     */
    public DoctorSummaryTrendRecord(Integer id, Date refDate, Byte type, Integer doctorId, Integer consultationNumber, BigDecimal inquiryMoney, Integer inquiryNumber, BigDecimal prescriptionMoney, Integer prescriptionNum, BigDecimal consumeMoney, Timestamp createTime) {
        super(DoctorSummaryTrend.DOCTOR_SUMMARY_TREND);

        set(0, id);
        set(1, refDate);
        set(2, type);
        set(3, doctorId);
        set(4, consultationNumber);
        set(5, inquiryMoney);
        set(6, inquiryNumber);
        set(7, prescriptionMoney);
        set(8, prescriptionNum);
        set(9, consumeMoney);
        set(10, createTime);
    }
}

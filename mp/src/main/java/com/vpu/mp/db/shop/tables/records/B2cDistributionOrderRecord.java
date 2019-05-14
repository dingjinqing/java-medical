/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cDistributionOrder;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.TableRecordImpl;
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
public class B2cDistributionOrderRecord extends TableRecordImpl<B2cDistributionOrderRecord> implements Record13<String, String, String, String, UInteger, UInteger, UInteger, BigDecimal, Integer, Integer, BigDecimal, Integer, Timestamp> {

    private static final long serialVersionUID = 2131398416;

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.ref_date</code>. 2018-07
     */
    public void setRefDate(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.ref_date</code>. 2018-07
     */
    public String getRefDate() {
        return (String) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.province</code>. 省
     */
    public void setProvince(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.province</code>. 省
     */
    public String getProvince() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.city</code>. 市
     */
    public void setCity(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.city</code>. 市
     */
    public String getCity() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.district</code>. 区
     */
    public void setDistrict(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.district</code>. 区
     */
    public String getDistrict() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.province_code</code>. 省份编号
     */
    public void setProvinceCode(UInteger value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.province_code</code>. 省份编号
     */
    public UInteger getProvinceCode() {
        return (UInteger) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.city_code</code>. 城市编号
     */
    public void setCityCode(UInteger value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.city_code</code>. 城市编号
     */
    public UInteger getCityCode() {
        return (UInteger) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.district_code</code>. 区县编号
     */
    public void setDistrictCode(UInteger value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.district_code</code>. 区县编号
     */
    public UInteger getDistrictCode() {
        return (UInteger) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.pay_order_money</code>. 付款金额
     */
    public void setPayOrderMoney(BigDecimal value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.pay_order_money</code>. 付款金额
     */
    public BigDecimal getPayOrderMoney() {
        return (BigDecimal) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.pay_user_num</code>. 付款人数
     */
    public void setPayUserNum(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.pay_user_num</code>. 付款人数
     */
    public Integer getPayUserNum() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.uv</code>. 访客数
     */
    public void setUv(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.uv</code>. 访客数
     */
    public Integer getUv() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.uv_pay_ratio</code>. 转化率
     */
    public void setUvPayRatio(BigDecimal value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.uv_pay_ratio</code>. 转化率
     */
    public BigDecimal getUvPayRatio() {
        return (BigDecimal) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.order_num</code>. 订单数
     */
    public void setOrderNum(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.order_num</code>. 订单数
     */
    public Integer getOrderNum() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distribution_order.add_time</code>. 添加时间
     */
    public void setAddTime(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distribution_order.add_time</code>. 添加时间
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(12);
    }

    // -------------------------------------------------------------------------
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<String, String, String, String, UInteger, UInteger, UInteger, BigDecimal, Integer, Integer, BigDecimal, Integer, Timestamp> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<String, String, String, String, UInteger, UInteger, UInteger, BigDecimal, Integer, Integer, BigDecimal, Integer, Timestamp> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.REF_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.PROVINCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.CITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.DISTRICT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field5() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.PROVINCE_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field6() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.CITY_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field7() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.DISTRICT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field8() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.PAY_ORDER_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.PAY_USER_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field11() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.UV_PAY_RATIO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.ORDER_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return B2cDistributionOrder.B2C_DISTRIBUTION_ORDER.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getRefDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getProvince();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getCity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getDistrict();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component5() {
        return getProvinceCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component6() {
        return getCityCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component7() {
        return getDistrictCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component8() {
        return getPayOrderMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getPayUserNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component10() {
        return getUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component11() {
        return getUvPayRatio();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getOrderNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component13() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getRefDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getProvince();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getCity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDistrict();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value5() {
        return getProvinceCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value6() {
        return getCityCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value7() {
        return getDistrictCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value8() {
        return getPayOrderMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getPayUserNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value11() {
        return getUvPayRatio();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getOrderNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value13() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value1(String value) {
        setRefDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value2(String value) {
        setProvince(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value3(String value) {
        setCity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value4(String value) {
        setDistrict(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value5(UInteger value) {
        setProvinceCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value6(UInteger value) {
        setCityCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value7(UInteger value) {
        setDistrictCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value8(BigDecimal value) {
        setPayOrderMoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value9(Integer value) {
        setPayUserNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value10(Integer value) {
        setUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value11(BigDecimal value) {
        setUvPayRatio(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value12(Integer value) {
        setOrderNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord value13(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionOrderRecord values(String value1, String value2, String value3, String value4, UInteger value5, UInteger value6, UInteger value7, BigDecimal value8, Integer value9, Integer value10, BigDecimal value11, Integer value12, Timestamp value13) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cDistributionOrderRecord
     */
    public B2cDistributionOrderRecord() {
        super(B2cDistributionOrder.B2C_DISTRIBUTION_ORDER);
    }

    /**
     * Create a detached, initialised B2cDistributionOrderRecord
     */
    public B2cDistributionOrderRecord(String refDate, String province, String city, String district, UInteger provinceCode, UInteger cityCode, UInteger districtCode, BigDecimal payOrderMoney, Integer payUserNum, Integer uv, BigDecimal uvPayRatio, Integer orderNum, Timestamp addTime) {
        super(B2cDistributionOrder.B2C_DISTRIBUTION_ORDER);

        set(0, refDate);
        set(1, province);
        set(2, city);
        set(3, district);
        set(4, provinceCode);
        set(5, cityCode);
        set(6, districtCode);
        set(7, payOrderMoney);
        set(8, payUserNum);
        set(9, uv);
        set(10, uvPayRatio);
        set(11, orderNum);
        set(12, addTime);
    }
}

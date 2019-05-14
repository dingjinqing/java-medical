/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cIntegralMallDefine;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
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
public class B2cIntegralMallDefineRecord extends UpdatableRecordImpl<B2cIntegralMallDefineRecord> implements Record12<UInteger, Integer, Short, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Byte, Integer, String, String> {

    private static final long serialVersionUID = -101188157;

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.goods_id</code>. 商品ID
     */
    public void setGoodsId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.goods_id</code>. 商品ID
     */
    public Integer getGoodsId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.max_exchange_num</code>. 每个用户最大可兑换数量
     */
    public void setMaxExchangeNum(Short value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.max_exchange_num</code>. 每个用户最大可兑换数量
     */
    public Short getMaxExchangeNum() {
        return (Short) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.start_time</code>. 活动起始时间
     */
    public void setStartTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.start_time</code>. 活动起始时间
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.end_time</code>. 活动终止时间
     */
    public void setEndTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.end_time</code>. 活动终止时间
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.status</code>. 1: 正常 0：禁用
     */
    public void setStatus(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.status</code>. 1: 正常 0：禁用
     */
    public Byte getStatus() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.add_time</code>.
     */
    public void setAddTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.add_time</code>.
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.del_flag</code>.
     */
    public void setDelFlag(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.del_flag</code>.
     */
    public Byte getDelFlag() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.del_time</code>.
     */
    public void setDelTime(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.del_time</code>.
     */
    public Integer getDelTime() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.name</code>. 活动名称
     */
    public void setName(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.name</code>. 活动名称
     */
    public String getName() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_integral_mall_define.share_config</code>. 分享设置
     */
    public void setShareConfig(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_integral_mall_define.share_config</code>. 分享设置
     */
    public String getShareConfig() {
        return (String) get(11);
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
    // Record12 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<UInteger, Integer, Short, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Byte, Integer, String, String> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<UInteger, Integer, Short, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Byte, Integer, String, String> valuesRow() {
        return (Row12) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field3() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.MAX_EXCHANGE_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.START_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.END_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.DEL_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE.SHARE_CONFIG;
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
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component3() {
        return getMaxExchangeNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component5() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component6() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getAddTime();
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
    public Byte component9() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component10() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getShareConfig();
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
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value3() {
        return getMaxExchangeNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getAddTime();
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
    public Byte value9() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getShareConfig();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value1(UInteger value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value2(Integer value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value3(Short value) {
        setMaxExchangeNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value4(Timestamp value) {
        setStartTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value5(Timestamp value) {
        setEndTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value6(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value7(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value9(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value10(Integer value) {
        setDelTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value11(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord value12(String value) {
        setShareConfig(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cIntegralMallDefineRecord values(UInteger value1, Integer value2, Short value3, Timestamp value4, Timestamp value5, Byte value6, Timestamp value7, Timestamp value8, Byte value9, Integer value10, String value11, String value12) {
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
     * Create a detached B2cIntegralMallDefineRecord
     */
    public B2cIntegralMallDefineRecord() {
        super(B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE);
    }

    /**
     * Create a detached, initialised B2cIntegralMallDefineRecord
     */
    public B2cIntegralMallDefineRecord(UInteger id, Integer goodsId, Short maxExchangeNum, Timestamp startTime, Timestamp endTime, Byte status, Timestamp addTime, Timestamp updateTime, Byte delFlag, Integer delTime, String name, String shareConfig) {
        super(B2cIntegralMallDefine.B2C_INTEGRAL_MALL_DEFINE);

        set(0, id);
        set(1, goodsId);
        set(2, maxExchangeNum);
        set(3, startTime);
        set(4, endTime);
        set(5, status);
        set(6, addTime);
        set(7, updateTime);
        set(8, delFlag);
        set(9, delTime);
        set(10, name);
        set(11, shareConfig);
    }
}

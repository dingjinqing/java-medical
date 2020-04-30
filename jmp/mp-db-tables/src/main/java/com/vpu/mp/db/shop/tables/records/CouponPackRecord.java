/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.CouponPack;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record17;
import org.jooq.Row17;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.math.BigDecimal;
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
public class CouponPackRecord extends UpdatableRecordImpl<CouponPackRecord> implements Record17<Integer, String, Timestamp, Timestamp, String, Integer, Integer, Integer, Byte, BigDecimal, String, Byte, Timestamp, Timestamp, Byte, Timestamp, Byte> {

    private static final long serialVersionUID = 27602680;

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.act_name</code>. 活动名称
     */
    public void setActName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.act_name</code>. 活动名称
     */
    public String getActName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.start_time</code>. 开始时间
     */
    public void setStartTime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.start_time</code>. 开始时间
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.end_time</code>. 结束时间
     */
    public void setEndTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.end_time</code>. 结束时间
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.pack_name</code>. 礼包名称
     */
    public void setPackName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.pack_name</code>. 礼包名称
     */
    public String getPackName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.limit_get_times</code>. 单用户领取限制次数，0不限制
     */
    public void setLimitGetTimes(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.limit_get_times</code>. 单用户领取限制次数，0不限制
     */
    public Integer getLimitGetTimes() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.total_amount</code>. 总数量
     */
    public void setTotalAmount(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.total_amount</code>. 总数量
     */
    public Integer getTotalAmount() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.issued_amount</code>. 已发放数量
     */
    public void setIssuedAmount(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.issued_amount</code>. 已发放数量
     */
    public Integer getIssuedAmount() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.access_mode</code>. 获取方式，0：现金购买，1：积分购买，2直接领取
     */
    public void setAccessMode(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.access_mode</code>. 获取方式，0：现金购买，1：积分购买，2直接领取
     */
    public Byte getAccessMode() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.access_cost</code>. 价格（现金或积分，直接领取时该值为0）
     */
    public void setAccessCost(BigDecimal value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.access_cost</code>. 价格（现金或积分，直接领取时该值为0）
     */
    public BigDecimal getAccessCost() {
        return (BigDecimal) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.act_rule</code>. 活动规则
     */
    public void setActRule(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.act_rule</code>. 活动规则
     */
    public String getActRule() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.status</code>. 开启状态1:开启，0:停用
     */
    public void setStatus(Byte value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.status</code>. 开启状态1:开启，0:停用
     */
    public Byte getStatus() {
        return (Byte) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.del_flag</code>.
     */
    public void setDelFlag(Byte value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.del_flag</code>.
     */
    public Byte getDelFlag() {
        return (Byte) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.del_time</code>.
     */
    public void setDelTime(Timestamp value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.del_time</code>.
     */
    public Timestamp getDelTime() {
        return (Timestamp) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coupon_pack.show_cart</code>. 购物车是否展示，1是
     */
    public void setShowCart(Byte value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coupon_pack.show_cart</code>. 购物车是否展示，1是
     */
    public Byte getShowCart() {
        return (Byte) get(16);
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
    // Record17 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row17<Integer, String, Timestamp, Timestamp, String, Integer, Integer, Integer, Byte, BigDecimal, String, Byte, Timestamp, Timestamp, Byte, Timestamp, Byte> fieldsRow() {
        return (Row17) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row17<Integer, String, Timestamp, Timestamp, String, Integer, Integer, Integer, Byte, BigDecimal, String, Byte, Timestamp, Timestamp, Byte, Timestamp, Byte> valuesRow() {
        return (Row17) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CouponPack.COUPON_PACK.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return CouponPack.COUPON_PACK.ACT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return CouponPack.COUPON_PACK.START_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return CouponPack.COUPON_PACK.END_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return CouponPack.COUPON_PACK.PACK_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return CouponPack.COUPON_PACK.LIMIT_GET_TIMES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return CouponPack.COUPON_PACK.TOTAL_AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return CouponPack.COUPON_PACK.ISSUED_AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return CouponPack.COUPON_PACK.ACCESS_MODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field10() {
        return CouponPack.COUPON_PACK.ACCESS_COST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return CouponPack.COUPON_PACK.ACT_RULE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field12() {
        return CouponPack.COUPON_PACK.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return CouponPack.COUPON_PACK.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field14() {
        return CouponPack.COUPON_PACK.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field15() {
        return CouponPack.COUPON_PACK.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field16() {
        return CouponPack.COUPON_PACK.DEL_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field17() {
        return CouponPack.COUPON_PACK.SHOW_CART;
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
    public String component2() {
        return getActName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component3() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getPackName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getLimitGetTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getTotalAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getIssuedAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component9() {
        return getAccessMode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component10() {
        return getAccessCost();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getActRule();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component12() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component13() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component14() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component15() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component16() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component17() {
        return getShowCart();
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
    public String value2() {
        return getActName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getPackName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getLimitGetTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getTotalAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getIssuedAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getAccessMode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value10() {
        return getAccessCost();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getActRule();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value12() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value13() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value14() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value15() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value16() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value17() {
        return getShowCart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value2(String value) {
        setActName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value3(Timestamp value) {
        setStartTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value4(Timestamp value) {
        setEndTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value5(String value) {
        setPackName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value6(Integer value) {
        setLimitGetTimes(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value7(Integer value) {
        setTotalAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value8(Integer value) {
        setIssuedAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value9(Byte value) {
        setAccessMode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value10(BigDecimal value) {
        setAccessCost(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value11(String value) {
        setActRule(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value12(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value13(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value14(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value15(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value16(Timestamp value) {
        setDelTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord value17(Byte value) {
        setShowCart(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CouponPackRecord values(Integer value1, String value2, Timestamp value3, Timestamp value4, String value5, Integer value6, Integer value7, Integer value8, Byte value9, BigDecimal value10, String value11, Byte value12, Timestamp value13, Timestamp value14, Byte value15, Timestamp value16, Byte value17) {
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
        value15(value15);
        value16(value16);
        value17(value17);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CouponPackRecord
     */
    public CouponPackRecord() {
        super(CouponPack.COUPON_PACK);
    }

    /**
     * Create a detached, initialised CouponPackRecord
     */
    public CouponPackRecord(Integer id, String actName, Timestamp startTime, Timestamp endTime, String packName, Integer limitGetTimes, Integer totalAmount, Integer issuedAmount, Byte accessMode, BigDecimal accessCost, String actRule, Byte status, Timestamp createTime, Timestamp updateTime, Byte delFlag, Timestamp delTime, Byte showCart) {
        super(CouponPack.COUPON_PACK);

        set(0, id);
        set(1, actName);
        set(2, startTime);
        set(3, endTime);
        set(4, packName);
        set(5, limitGetTimes);
        set(6, totalAmount);
        set(7, issuedAmount);
        set(8, accessMode);
        set(9, accessCost);
        set(10, actRule);
        set(11, status);
        set(12, createTime);
        set(13, updateTime);
        set(14, delFlag);
        set(15, delTime);
        set(16, showCart);
    }
}

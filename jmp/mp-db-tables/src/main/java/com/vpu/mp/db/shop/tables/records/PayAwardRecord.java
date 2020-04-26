/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.PayAward;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record17;
import org.jooq.Row17;
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
public class PayAwardRecord extends UpdatableRecordImpl<PayAwardRecord> implements Record17<Integer, String, Byte, Timestamp, Timestamp, Integer, Integer, String, String, String, BigDecimal, Integer, String, Byte, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1426304437;

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.activity_names</code>. 活动名称
     */
    public void setActivityNames(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.activity_names</code>. 活动名称
     */
    public String getActivityNames() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.time_type</code>. 时间类型0固时1永久
     */
    public void setTimeType(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.time_type</code>. 时间类型0固时1永久
     */
    public Byte getTimeType() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.start_time</code>. 开始
     */
    public void setStartTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.start_time</code>. 开始
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.end_time</code>. 结束
     */
    public void setEndTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.end_time</code>. 结束
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.act_first</code>. 优先级
     */
    public void setActFirst(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.act_first</code>. 优先级
     */
    public Integer getActFirst() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.goods_area_type</code>. 商品范围类型 0全部商品 1 部分商品
     */
    public void setGoodsAreaType(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.goods_area_type</code>. 商品范围类型 0全部商品 1 部分商品
     */
    public Integer getGoodsAreaType() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.goods_ids</code>. 商品id
     */
    public void setGoodsIds(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.goods_ids</code>. 商品id
     */
    public String getGoodsIds() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.goods_cat_ids</code>. 商品平台分类
     */
    public void setGoodsCatIds(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.goods_cat_ids</code>. 商品平台分类
     */
    public String getGoodsCatIds() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.goods_sort_ids</code>. 商品商家分类
     */
    public void setGoodsSortIds(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.goods_sort_ids</code>. 商品商家分类
     */
    public String getGoodsSortIds() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.min_pay_money</code>. 最小金额
     */
    public void setMinPayMoney(BigDecimal value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.min_pay_money</code>. 最小金额
     */
    public BigDecimal getMinPayMoney() {
        return (BigDecimal) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.limit_times</code>. 每个用户可参加次数
     */
    public void setLimitTimes(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.limit_times</code>. 每个用户可参加次数
     */
    public Integer getLimitTimes() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.award_list</code>. 奖品列表json
     */
    public void setAwardList(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.award_list</code>. 奖品列表json
     */
    public String getAwardList() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.status</code>. 状态1停用
     */
    public void setStatus(Byte value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.status</code>. 状态1停用
     */
    public Byte getStatus() {
        return (Byte) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.del_flag</code>. 1删除
     */
    public void setDelFlag(Byte value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.del_flag</code>. 1删除
     */
    public Byte getDelFlag() {
        return (Byte) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_pay_award.update_time</code>. 跟新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_pay_award.update_time</code>. 跟新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(16);
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
    public Row17<Integer, String, Byte, Timestamp, Timestamp, Integer, Integer, String, String, String, BigDecimal, Integer, String, Byte, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row17) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row17<Integer, String, Byte, Timestamp, Timestamp, Integer, Integer, String, String, String, BigDecimal, Integer, String, Byte, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row17) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return PayAward.PAY_AWARD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return PayAward.PAY_AWARD.ACTIVITY_NAMES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return PayAward.PAY_AWARD.TIME_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return PayAward.PAY_AWARD.START_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return PayAward.PAY_AWARD.END_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return PayAward.PAY_AWARD.ACT_FIRST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return PayAward.PAY_AWARD.GOODS_AREA_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return PayAward.PAY_AWARD.GOODS_IDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return PayAward.PAY_AWARD.GOODS_CAT_IDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return PayAward.PAY_AWARD.GOODS_SORT_IDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field11() {
        return PayAward.PAY_AWARD.MIN_PAY_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return PayAward.PAY_AWARD.LIMIT_TIMES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return PayAward.PAY_AWARD.AWARD_LIST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field14() {
        return PayAward.PAY_AWARD.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field15() {
        return PayAward.PAY_AWARD.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field16() {
        return PayAward.PAY_AWARD.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field17() {
        return PayAward.PAY_AWARD.UPDATE_TIME;
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
        return getActivityNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component3() {
        return getTimeType();
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
    public Integer component6() {
        return getActFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getGoodsAreaType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getGoodsIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getGoodsCatIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getGoodsSortIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component11() {
        return getMinPayMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getLimitTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getAwardList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component14() {
        return getStatus();
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
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component17() {
        return getUpdateTime();
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
        return getActivityNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getTimeType();
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
    public Integer value6() {
        return getActFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getGoodsAreaType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getGoodsIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getGoodsCatIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getGoodsSortIds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value11() {
        return getMinPayMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getLimitTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getAwardList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value14() {
        return getStatus();
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
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value17() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value2(String value) {
        setActivityNames(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value3(Byte value) {
        setTimeType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value4(Timestamp value) {
        setStartTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value5(Timestamp value) {
        setEndTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value6(Integer value) {
        setActFirst(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value7(Integer value) {
        setGoodsAreaType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value8(String value) {
        setGoodsIds(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value9(String value) {
        setGoodsCatIds(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value10(String value) {
        setGoodsSortIds(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value11(BigDecimal value) {
        setMinPayMoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value12(Integer value) {
        setLimitTimes(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value13(String value) {
        setAwardList(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value14(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value15(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value16(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord value17(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PayAwardRecord values(Integer value1, String value2, Byte value3, Timestamp value4, Timestamp value5, Integer value6, Integer value7, String value8, String value9, String value10, BigDecimal value11, Integer value12, String value13, Byte value14, Byte value15, Timestamp value16, Timestamp value17) {
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
     * Create a detached PayAwardRecord
     */
    public PayAwardRecord() {
        super(PayAward.PAY_AWARD);
    }

    /**
     * Create a detached, initialised PayAwardRecord
     */
    public PayAwardRecord(Integer id, String activityNames, Byte timeType, Timestamp startTime, Timestamp endTime, Integer actFirst, Integer goodsAreaType, String goodsIds, String goodsCatIds, String goodsSortIds, BigDecimal minPayMoney, Integer limitTimes, String awardList, Byte status, Byte delFlag, Timestamp createTime, Timestamp updateTime) {
        super(PayAward.PAY_AWARD);

        set(0, id);
        set(1, activityNames);
        set(2, timeType);
        set(3, startTime);
        set(4, endTime);
        set(5, actFirst);
        set(6, goodsAreaType);
        set(7, goodsIds);
        set(8, goodsCatIds);
        set(9, goodsSortIds);
        set(10, minPayMoney);
        set(11, limitTimes);
        set(12, awardList);
        set(13, status);
        set(14, delFlag);
        set(15, createTime);
        set(16, updateTime);
    }
}

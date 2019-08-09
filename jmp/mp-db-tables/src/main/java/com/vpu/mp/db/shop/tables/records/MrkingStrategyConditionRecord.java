/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.MrkingStrategyCondition;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
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
public class MrkingStrategyConditionRecord extends UpdatableRecordImpl<MrkingStrategyConditionRecord> implements Record8<Integer, Integer, BigDecimal, BigDecimal, Integer, BigDecimal, Timestamp, Timestamp> {

    private static final long serialVersionUID = 189971669;

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_strategy_condition.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_strategy_condition.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_strategy_condition.strategy_id</code>.
     */
    public void setStrategyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_strategy_condition.strategy_id</code>.
     */
    public Integer getStrategyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_strategy_condition.full_money</code>. 满多少金额
     */
    public void setFullMoney(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_strategy_condition.full_money</code>. 满多少金额
     */
    public BigDecimal getFullMoney() {
        return (BigDecimal) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_strategy_condition.reduce_money</code>. 减多少钱
     */
    public void setReduceMoney(BigDecimal value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_strategy_condition.reduce_money</code>. 减多少钱
     */
    public BigDecimal getReduceMoney() {
        return (BigDecimal) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_strategy_condition.amount</code>. 满几件或第几件（第X件打折）
     */
    public void setAmount(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_strategy_condition.amount</code>. 满几件或第几件（第X件打折）
     */
    public Integer getAmount() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_strategy_condition.discount</code>. 打几折
     */
    public void setDiscount(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_strategy_condition.discount</code>. 打几折
     */
    public BigDecimal getDiscount() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_strategy_condition.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_strategy_condition.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_mrking_strategy_condition.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_mrking_strategy_condition.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, BigDecimal, BigDecimal, Integer, BigDecimal, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, BigDecimal, BigDecimal, Integer, BigDecimal, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return MrkingStrategyCondition.MRKING_STRATEGY_CONDITION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return MrkingStrategyCondition.MRKING_STRATEGY_CONDITION.STRATEGY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field3() {
        return MrkingStrategyCondition.MRKING_STRATEGY_CONDITION.FULL_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field4() {
        return MrkingStrategyCondition.MRKING_STRATEGY_CONDITION.REDUCE_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return MrkingStrategyCondition.MRKING_STRATEGY_CONDITION.AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field6() {
        return MrkingStrategyCondition.MRKING_STRATEGY_CONDITION.DISCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return MrkingStrategyCondition.MRKING_STRATEGY_CONDITION.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return MrkingStrategyCondition.MRKING_STRATEGY_CONDITION.UPDATE_TIME;
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
        return getStrategyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component3() {
        return getFullMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component4() {
        return getReduceMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component6() {
        return getDiscount();
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
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getStrategyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value3() {
        return getFullMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value4() {
        return getReduceMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value6() {
        return getDiscount();
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
    public MrkingStrategyConditionRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MrkingStrategyConditionRecord value2(Integer value) {
        setStrategyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MrkingStrategyConditionRecord value3(BigDecimal value) {
        setFullMoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MrkingStrategyConditionRecord value4(BigDecimal value) {
        setReduceMoney(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MrkingStrategyConditionRecord value5(Integer value) {
        setAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MrkingStrategyConditionRecord value6(BigDecimal value) {
        setDiscount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MrkingStrategyConditionRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MrkingStrategyConditionRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MrkingStrategyConditionRecord values(Integer value1, Integer value2, BigDecimal value3, BigDecimal value4, Integer value5, BigDecimal value6, Timestamp value7, Timestamp value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MrkingStrategyConditionRecord
     */
    public MrkingStrategyConditionRecord() {
        super(MrkingStrategyCondition.MRKING_STRATEGY_CONDITION);
    }

    /**
     * Create a detached, initialised MrkingStrategyConditionRecord
     */
    public MrkingStrategyConditionRecord(Integer id, Integer strategyId, BigDecimal fullMoney, BigDecimal reduceMoney, Integer amount, BigDecimal discount, Timestamp createTime, Timestamp updateTime) {
        super(MrkingStrategyCondition.MRKING_STRATEGY_CONDITION);

        set(0, id);
        set(1, strategyId);
        set(2, fullMoney);
        set(3, reduceMoney);
        set(4, amount);
        set(5, discount);
        set(6, createTime);
        set(7, updateTime);
    }
}

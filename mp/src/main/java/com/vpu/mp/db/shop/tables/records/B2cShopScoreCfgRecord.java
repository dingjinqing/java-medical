/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cShopScoreCfg;

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
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class B2cShopScoreCfgRecord extends UpdatableRecordImpl<B2cShopScoreCfgRecord> implements Record8<Integer, Integer, Integer, Integer, Timestamp, Timestamp, Integer, Integer> {

    private static final long serialVersionUID = -32769501;

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_score_cfg.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_score_cfg.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_score_cfg.growth</code>. 0:关闭，1:开启
     */
    public void setGrowth(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_score_cfg.growth</code>. 0:关闭，1:开启
     */
    public Integer getGrowth() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_score_cfg.score</code>. 0:关闭，1:开启
     */
    public void setScore(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_score_cfg.score</code>. 0:关闭，1:开启
     */
    public Integer getScore() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_score_cfg.comment</code>. comment:0:关闭评论，1：不用审批，2：先发后审，3：先审后发
     */
    public void setComment(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_score_cfg.comment</code>. comment:0:关闭评论，1：不用审批，2：先发后审，3：先审后发
     */
    public Integer getComment() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_score_cfg.in_time</code>.
     */
    public void setInTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_score_cfg.in_time</code>.
     */
    public Timestamp getInTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_score_cfg.up_time</code>.
     */
    public void setUpTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_score_cfg.up_time</code>.
     */
    public Timestamp getUpTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_score_cfg.sign</code>. 0:关闭，1:开启
     */
    public void setSign(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_score_cfg.sign</code>. 0:关闭，1:开启
     */
    public Integer getSign() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_shop_score_cfg.sys_id</code>. 商家ID
     */
    public void setSysId(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_shop_score_cfg.sys_id</code>. 商家ID
     */
    public Integer getSysId() {
        return (Integer) get(7);
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
    public Row8<Integer, Integer, Integer, Integer, Timestamp, Timestamp, Integer, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Integer, Integer, Timestamp, Timestamp, Integer, Integer> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cShopScoreCfg.B2C_SHOP_SCORE_CFG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cShopScoreCfg.B2C_SHOP_SCORE_CFG.GROWTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cShopScoreCfg.B2C_SHOP_SCORE_CFG.SCORE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return B2cShopScoreCfg.B2C_SHOP_SCORE_CFG.COMMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return B2cShopScoreCfg.B2C_SHOP_SCORE_CFG.IN_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return B2cShopScoreCfg.B2C_SHOP_SCORE_CFG.UP_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return B2cShopScoreCfg.B2C_SHOP_SCORE_CFG.SIGN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return B2cShopScoreCfg.B2C_SHOP_SCORE_CFG.SYS_ID;
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
        return getGrowth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getComment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component5() {
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getSign();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getSysId();
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
        return getGrowth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getComment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getSign();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getSysId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopScoreCfgRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopScoreCfgRecord value2(Integer value) {
        setGrowth(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopScoreCfgRecord value3(Integer value) {
        setScore(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopScoreCfgRecord value4(Integer value) {
        setComment(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopScoreCfgRecord value5(Timestamp value) {
        setInTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopScoreCfgRecord value6(Timestamp value) {
        setUpTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopScoreCfgRecord value7(Integer value) {
        setSign(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopScoreCfgRecord value8(Integer value) {
        setSysId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopScoreCfgRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Timestamp value5, Timestamp value6, Integer value7, Integer value8) {
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
     * Create a detached B2cShopScoreCfgRecord
     */
    public B2cShopScoreCfgRecord() {
        super(B2cShopScoreCfg.B2C_SHOP_SCORE_CFG);
    }

    /**
     * Create a detached, initialised B2cShopScoreCfgRecord
     */
    public B2cShopScoreCfgRecord(Integer id, Integer growth, Integer score, Integer comment, Timestamp inTime, Timestamp upTime, Integer sign, Integer sysId) {
        super(B2cShopScoreCfg.B2C_SHOP_SCORE_CFG);

        set(0, id);
        set(1, growth);
        set(2, score);
        set(3, comment);
        set(4, inTime);
        set(5, upTime);
        set(6, sign);
        set(7, sysId);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.Lottery;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record19;
import org.jooq.Row19;
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
public class LotteryRecord extends UpdatableRecordImpl<LotteryRecord> implements Record19<Integer, String, Timestamp, Timestamp, String, Integer, Byte, Integer, Byte, Integer, Integer, Integer, String, String, Byte, Byte, Timestamp, Timestamp, Byte> {

    private static final long serialVersionUID = 958792086;

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.lottery_name</code>. 抽奖名称
     */
    public void setLotteryName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.lottery_name</code>. 抽奖名称
     */
    public String getLotteryName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.start_time</code>. 开始时间
     */
    public void setStartTime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.start_time</code>. 开始时间
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.end_time</code>. 结束时间
     */
    public void setEndTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.end_time</code>. 结束时间
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.lottery_explain</code>. 抽奖说明
     */
    public void setLotteryExplain(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.lottery_explain</code>. 抽奖说明
     */
    public String getLotteryExplain() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.free_chances</code>. 免费抽奖次数 0不限制 -1不可免费抽奖 
     */
    public void setFreeChances(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.free_chances</code>. 免费抽奖次数 0不限制 -1不可免费抽奖 
     */
    public Integer getFreeChances() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.can_share</code>. 是否分享获得次数
     */
    public void setCanShare(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.can_share</code>. 是否分享获得次数
     */
    public Byte getCanShare() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.share_chances</code>. 分享最多获得次数
     */
    public void setShareChances(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.share_chances</code>. 分享最多获得次数 0不限制次数
     */
    public Integer getShareChances() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.can_use_score</code>. 是否可以积分抽奖
     */
    public void setCanUseScore(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.can_use_score</code>. 是否可以积分抽奖
     */
    public Byte getCanUseScore() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.score_per_chance</code>. 抽奖一次使用积分
     */
    public void setScorePerChance(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.score_per_chance</code>. 抽奖一次使用积分
     */
    public Integer getScorePerChance() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.score_chances</code>. 积分最多抽奖次数 0不限制
     */
    public void setScoreChances(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.score_chances</code>. 积分最多抽奖次数 0不限制
     */
    public Integer getScoreChances() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.no_award_score</code>. 未中奖奖励积分 0不赠送积分 
     */
    public void setNoAwardScore(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.no_award_score</code>. 未中奖奖励积分 0不赠送积分 
     */
    public Integer getNoAwardScore() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.no_award_image</code>. 未中奖图片
     */
    public void setNoAwardImage(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.no_award_image</code>. 未中奖图片
     */
    public String getNoAwardImage() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.no_award_icon</code>. 未中奖提示
     */
    public void setNoAwardIcon(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.no_award_icon</code>. 未中奖提示
     */
    public String getNoAwardIcon() {
        return (String) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.status</code>. 状态：0停用
     */
    public void setStatus(Byte value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.status</code>. 状态：0停用
     */
    public Byte getStatus() {
        return (Byte) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.del_flag</code>. 1删除
     */
    public void setDelFlag(Byte value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.del_flag</code>. 1删除
     */
    public Byte getDelFlag() {
        return (Byte) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(16);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(17);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.chance_type</code>. 次数限制 0每人 1每人每天
     */
    public void setChanceType(Byte value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.chance_type</code>. 次数限制 0每人 1每人每天
     */
    public Byte getChanceType() {
        return (Byte) get(18);
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
    // Record19 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, String, Timestamp, Timestamp, String, Integer, Byte, Integer, Byte, Integer, Integer, Integer, String, String, Byte, Byte, Timestamp, Timestamp, Byte> fieldsRow() {
        return (Row19) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, String, Timestamp, Timestamp, String, Integer, Byte, Integer, Byte, Integer, Integer, Integer, String, String, Byte, Byte, Timestamp, Timestamp, Byte> valuesRow() {
        return (Row19) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Lottery.LOTTERY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Lottery.LOTTERY.LOTTERY_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return Lottery.LOTTERY.START_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return Lottery.LOTTERY.END_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Lottery.LOTTERY.LOTTERY_EXPLAIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return Lottery.LOTTERY.FREE_CHANCES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return Lottery.LOTTERY.CAN_SHARE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return Lottery.LOTTERY.SHARE_CHANCES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return Lottery.LOTTERY.CAN_USE_SCORE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return Lottery.LOTTERY.SCORE_PER_CHANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return Lottery.LOTTERY.SCORE_CHANCES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return Lottery.LOTTERY.NO_AWARD_SCORE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return Lottery.LOTTERY.NO_AWARD_IMAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return Lottery.LOTTERY.NO_AWARD_ICON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field15() {
        return Lottery.LOTTERY.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field16() {
        return Lottery.LOTTERY.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field17() {
        return Lottery.LOTTERY.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field18() {
        return Lottery.LOTTERY.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field19() {
        return Lottery.LOTTERY.CHANCE_TYPE;
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
        return getLotteryName();
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
        return getLotteryExplain();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getFreeChances();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component7() {
        return getCanShare();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getShareChances();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component9() {
        return getCanUseScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component10() {
        return getScorePerChance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component11() {
        return getScoreChances();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getNoAwardScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getNoAwardImage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component14() {
        return getNoAwardIcon();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component15() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component16() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component17() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component18() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component19() {
        return getChanceType();
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
        return getLotteryName();
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
        return getLotteryExplain();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getFreeChances();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getCanShare();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getShareChances();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getCanUseScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getScorePerChance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getScoreChances();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getNoAwardScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getNoAwardImage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getNoAwardIcon();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value15() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value16() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value17() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value18() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value19() {
        return getChanceType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value2(String value) {
        setLotteryName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value3(Timestamp value) {
        setStartTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value4(Timestamp value) {
        setEndTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value5(String value) {
        setLotteryExplain(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value6(Integer value) {
        setFreeChances(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value7(Byte value) {
        setCanShare(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value8(Integer value) {
        setShareChances(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value9(Byte value) {
        setCanUseScore(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value10(Integer value) {
        setScorePerChance(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value11(Integer value) {
        setScoreChances(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value12(Integer value) {
        setNoAwardScore(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value13(String value) {
        setNoAwardImage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value14(String value) {
        setNoAwardIcon(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value15(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value16(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value17(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value18(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord value19(Byte value) {
        setChanceType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryRecord values(Integer value1, String value2, Timestamp value3, Timestamp value4, String value5, Integer value6, Byte value7, Integer value8, Byte value9, Integer value10, Integer value11, Integer value12, String value13, String value14, Byte value15, Byte value16, Timestamp value17, Timestamp value18, Byte value19) {
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
        value18(value18);
        value19(value19);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LotteryRecord
     */
    public LotteryRecord() {
        super(Lottery.LOTTERY);
    }

    /**
     * Create a detached, initialised LotteryRecord
     */
    public LotteryRecord(Integer id, String lotteryName, Timestamp startTime, Timestamp endTime, String lotteryExplain, Integer freeChances, Byte canShare, Integer shareChances, Byte canUseScore, Integer scorePerChance, Integer scoreChances, Integer noAwardScore, String noAwardImage, String noAwardIcon, Byte status, Byte delFlag, Timestamp createTime, Timestamp updateTime, Byte chanceType) {
        super(Lottery.LOTTERY);

        set(0, id);
        set(1, lotteryName);
        set(2, startTime);
        set(3, endTime);
        set(4, lotteryExplain);
        set(5, freeChances);
        set(6, canShare);
        set(7, shareChances);
        set(8, canUseScore);
        set(9, scorePerChance);
        set(10, scoreChances);
        set(11, noAwardScore);
        set(12, noAwardImage);
        set(13, noAwardIcon);
        set(14, status);
        set(15, delFlag);
        set(16, createTime);
        set(17, updateTime);
        set(18, chanceType);
    }
}

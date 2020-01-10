/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.Lottery;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
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
public class LotteryRecord extends UpdatableRecordImpl<LotteryRecord> {

    private static final long serialVersionUID = -1364900884;

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
     * Setter for <code>mini_shop_471752.b2c_lottery.free_chances</code>. 免费抽奖次数
     */
    public void setFreeChances(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.free_chances</code>. 免费抽奖次数
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
     * Getter for <code>mini_shop_471752.b2c_lottery.share_chances</code>. 分享最多获得次数
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
     * Setter for <code>mini_shop_471752.b2c_lottery.score_chances</code>. 积分最多抽奖次数
     */
    public void setScoreChances(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.score_chances</code>. 积分最多抽奖次数
     */
    public Integer getScoreChances() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.no_award_score</code>. 未中奖奖励积分
     */
    public void setNoAwardScore(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.no_award_score</code>. 未中奖奖励积分
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
     * Setter for <code>mini_shop_471752.b2c_lottery.first_award</code>. 一等奖设置（json）
     */
    public void setFirstAward(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.first_award</code>. 一等奖设置（json）
     */
    public String getFirstAward() {
        return (String) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.first_award_times</code>. 中奖数
     */
    public void setFirstAwardTimes(Integer value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.first_award_times</code>. 中奖数
     */
    public Integer getFirstAwardTimes() {
        return (Integer) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.second_award</code>. 二等奖设置（json）
     */
    public void setSecondAward(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.second_award</code>. 二等奖设置（json）
     */
    public String getSecondAward() {
        return (String) get(16);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.second_award_times</code>. 中奖数
     */
    public void setSecondAwardTimes(Integer value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.second_award_times</code>. 中奖数
     */
    public Integer getSecondAwardTimes() {
        return (Integer) get(17);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.third_award</code>. 三等奖设置（json）
     */
    public void setThirdAward(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.third_award</code>. 三等奖设置（json）
     */
    public String getThirdAward() {
        return (String) get(18);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.third_award_times</code>. 中奖数
     */
    public void setThirdAwardTimes(Integer value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.third_award_times</code>. 中奖数
     */
    public Integer getThirdAwardTimes() {
        return (Integer) get(19);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.fourth_award</code>. 四等奖设置（json）
     */
    public void setFourthAward(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.fourth_award</code>. 四等奖设置（json）
     */
    public String getFourthAward() {
        return (String) get(20);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.fourth_award_times</code>. 中奖数
     */
    public void setFourthAwardTimes(Integer value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.fourth_award_times</code>. 中奖数
     */
    public Integer getFourthAwardTimes() {
        return (Integer) get(21);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.status</code>. 状态：1停用
     */
    public void setStatus(Byte value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.status</code>. 状态：1停用
     */
    public Byte getStatus() {
        return (Byte) get(22);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.del_flag</code>. 1删除
     */
    public void setDelFlag(Byte value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.del_flag</code>. 1删除
     */
    public Byte getDelFlag() {
        return (Byte) get(23);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(24);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_lottery.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(25, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_lottery.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(25);
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
    public LotteryRecord(Integer id, String lotteryName, Timestamp startTime, Timestamp endTime, String lotteryExplain, Integer freeChances, Byte canShare, Integer shareChances, Byte canUseScore, Integer scorePerChance, Integer scoreChances, Integer noAwardScore, String noAwardImage, String noAwardIcon, String firstAward, Integer firstAwardTimes, String secondAward, Integer secondAwardTimes, String thirdAward, Integer thirdAwardTimes, String fourthAward, Integer fourthAwardTimes, Byte status, Byte delFlag, Timestamp createTime, Timestamp updateTime) {
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
        set(14, firstAward);
        set(15, firstAwardTimes);
        set(16, secondAward);
        set(17, secondAwardTimes);
        set(18, thirdAward);
        set(19, thirdAwardTimes);
        set(20, fourthAward);
        set(21, fourthAwardTimes);
        set(22, status);
        set(23, delFlag);
        set(24, createTime);
        set(25, updateTime);
    }
}

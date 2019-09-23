/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.LotteryPrize;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record18;
import org.jooq.Row18;
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
public class LotteryPrizeRecord extends UpdatableRecordImpl<LotteryPrizeRecord> implements Record18<Integer, Integer, Integer, Integer, Byte, String, String, String, Byte, Integer, Integer, Integer, Integer, Integer, Short, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1670552583;

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.lottery_id</code>. 抽奖编号
     */
    public void setLotteryId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.lottery_id</code>. 抽奖编号
     */
    public Integer getLotteryId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.chance_numerator</code>. 中奖概率--分子
     */
    public void setChanceNumerator(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.chance_numerator</code>. 中奖概率--分子
     */
    public Integer getChanceNumerator() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.chance_denominator</code>. 中奖概率--分母
     */
    public void setChanceDenominator(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.chance_denominator</code>. 中奖概率--分母
     */
    public Integer getChanceDenominator() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.lottery_grade</code>. 中奖等级：1一等奖，2二等奖，3三等奖，4四等奖 5.。。。
     */
    public void setLotteryGrade(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.lottery_grade</code>. 中奖等级：1一等奖，2二等奖，3三等奖，4四等奖 5.。。。
     */
    public Byte getLotteryGrade() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.lottery_detail</code>. 奖品信息
     */
    public void setLotteryDetail(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.lottery_detail</code>. 奖品信息
     */
    public String getLotteryDetail() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.icon_imgs_image</code>. 中奖图片
     */
    public void setIconImgsImage(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.icon_imgs_image</code>. 中奖图片
     */
    public String getIconImgsImage() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.icon_imgs</code>. 中奖提示
     */
    public void setIconImgs(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.icon_imgs</code>. 中奖提示
     */
    public String getIconImgs() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.lottery_type</code>. 选择奖类型 0积分 1 用户余额 2优惠券 3赠品 4 自定义
     */
    public void setLotteryType(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.lottery_type</code>. 选择奖类型 0积分 1 用户余额 2优惠券 3赠品 4 自定义
     */
    public Byte getLotteryType() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.lottery_number</code>. 奖品份数
     */
    public void setLotteryNumber(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.lottery_number</code>. 奖品份数
     */
    public Integer getLotteryNumber() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.award_times</code>. 已发生中奖数
     */
    public void setAwardTimes(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.award_times</code>. 已发生中奖数
     */
    public Integer getAwardTimes() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.integral_score</code>. 积分数量
     */
    public void setIntegralScore(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.integral_score</code>. 积分数量
     */
    public Integer getIntegralScore() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.coupon_id</code>. 优惠券id
     */
    public void setCouponId(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.coupon_id</code>. 优惠券id
     */
    public Integer getCouponId() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.prd_id</code>. 赠品规格id
     */
    public void setPrdId(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.prd_id</code>. 赠品规格id
     */
    public Integer getPrdId() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.prd_keep_days</code>. 赠品有效期
     */
    public void setPrdKeepDays(Short value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.prd_keep_days</code>. 赠品有效期
     */
    public Short getPrdKeepDays() {
        return (Short) get(14);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.del_flag</code>. 1删除
     */
    public void setDelFlag(Byte value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.del_flag</code>. 1删除
     */
    public Byte getDelFlag() {
        return (Byte) get(15);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(16);
    }

    /**
     * Setter for <code>mini_shop_4748160.b2c_lottery_prize.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_4748160.b2c_lottery_prize.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(17);
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
    // Record18 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row18<Integer, Integer, Integer, Integer, Byte, String, String, String, Byte, Integer, Integer, Integer, Integer, Integer, Short, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row18) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row18<Integer, Integer, Integer, Integer, Byte, String, String, String, Byte, Integer, Integer, Integer, Integer, Integer, Short, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row18) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return LotteryPrize.LOTTERY_PRIZE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return LotteryPrize.LOTTERY_PRIZE.LOTTERY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return LotteryPrize.LOTTERY_PRIZE.CHANCE_NUMERATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return LotteryPrize.LOTTERY_PRIZE.CHANCE_DENOMINATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return LotteryPrize.LOTTERY_PRIZE.LOTTERY_GRADE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return LotteryPrize.LOTTERY_PRIZE.LOTTERY_DETAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return LotteryPrize.LOTTERY_PRIZE.ICON_IMGS_IMAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return LotteryPrize.LOTTERY_PRIZE.ICON_IMGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return LotteryPrize.LOTTERY_PRIZE.LOTTERY_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return LotteryPrize.LOTTERY_PRIZE.LOTTERY_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return LotteryPrize.LOTTERY_PRIZE.AWARD_TIMES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return LotteryPrize.LOTTERY_PRIZE.INTEGRAL_SCORE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return LotteryPrize.LOTTERY_PRIZE.COUPON_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return LotteryPrize.LOTTERY_PRIZE.PRD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field15() {
        return LotteryPrize.LOTTERY_PRIZE.PRD_KEEP_DAYS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field16() {
        return LotteryPrize.LOTTERY_PRIZE.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field17() {
        return LotteryPrize.LOTTERY_PRIZE.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field18() {
        return LotteryPrize.LOTTERY_PRIZE.CREATE_TIME;
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
        return getLotteryId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getChanceNumerator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getChanceDenominator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component5() {
        return getLotteryGrade();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getLotteryDetail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getIconImgsImage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getIconImgs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component9() {
        return getLotteryType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component10() {
        return getLotteryNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component11() {
        return getAwardTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getIntegralScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component13() {
        return getCouponId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component14() {
        return getPrdId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short component15() {
        return getPrdKeepDays();
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
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component18() {
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
    public Integer value2() {
        return getLotteryId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getChanceNumerator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getChanceDenominator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getLotteryGrade();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getLotteryDetail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getIconImgsImage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getIconImgs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getLotteryType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getLotteryNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getAwardTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getIntegralScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getCouponId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value14() {
        return getPrdId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value15() {
        return getPrdKeepDays();
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
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value18() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value2(Integer value) {
        setLotteryId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value3(Integer value) {
        setChanceNumerator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value4(Integer value) {
        setChanceDenominator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value5(Byte value) {
        setLotteryGrade(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value6(String value) {
        setLotteryDetail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value7(String value) {
        setIconImgsImage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value8(String value) {
        setIconImgs(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value9(Byte value) {
        setLotteryType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value10(Integer value) {
        setLotteryNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value11(Integer value) {
        setAwardTimes(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value12(Integer value) {
        setIntegralScore(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value13(Integer value) {
        setCouponId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value14(Integer value) {
        setPrdId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value15(Short value) {
        setPrdKeepDays(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value16(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value17(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord value18(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LotteryPrizeRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Byte value5, String value6, String value7, String value8, Byte value9, Integer value10, Integer value11, Integer value12, Integer value13, Integer value14, Short value15, Byte value16, Timestamp value17, Timestamp value18) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LotteryPrizeRecord
     */
    public LotteryPrizeRecord() {
        super(LotteryPrize.LOTTERY_PRIZE);
    }

    /**
     * Create a detached, initialised LotteryPrizeRecord
     */
    public LotteryPrizeRecord(Integer id, Integer lotteryId, Integer chanceNumerator, Integer chanceDenominator, Byte lotteryGrade, String lotteryDetail, String iconImgsImage, String iconImgs, Byte lotteryType, Integer lotteryNumber, Integer awardTimes, Integer integralScore, Integer couponId, Integer prdId, Short prdKeepDays, Byte delFlag, Timestamp updateTime, Timestamp createTime) {
        super(LotteryPrize.LOTTERY_PRIZE);

        set(0, id);
        set(1, lotteryId);
        set(2, chanceNumerator);
        set(3, chanceDenominator);
        set(4, lotteryGrade);
        set(5, lotteryDetail);
        set(6, iconImgsImage);
        set(7, iconImgs);
        set(8, lotteryType);
        set(9, lotteryNumber);
        set(10, awardTimes);
        set(11, integralScore);
        set(12, couponId);
        set(13, prdId);
        set(14, prdKeepDays);
        set(15, delFlag);
        set(16, updateTime);
        set(17, createTime);
    }
}

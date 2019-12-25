/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.CoopenActivity;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record22;
import org.jooq.Row22;
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
public class CoopenActivityRecord extends UpdatableRecordImpl<CoopenActivityRecord> implements Record22<Integer, Byte, String, String, String, Integer, Timestamp, Timestamp, Integer, Byte, String, Integer, String, String, BigDecimal, BigDecimal, Integer, Byte, Byte, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = 263431026;

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.action</code>. 针对用户群体： 1: 初次访问新用户 2: 全部用户 3:未支付的用户
     */
    public void setAction(Byte value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.action</code>. 针对用户群体： 1: 初次访问新用户 2: 全部用户 3:未支付的用户
     */
    public Byte getAction() {
        return (Byte) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.name</code>. 活动名称
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.name</code>. 活动名称
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.title</code>. 宣传语
     */
    public void setTitle(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.title</code>. 宣传语
     */
    public String getTitle() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.bg_imgs</code>. 背景图
     */
    public void setBgImgs(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.bg_imgs</code>. 背景图
     */
    public String getBgImgs() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.is_forever</code>. 是否永久有效 0:无效 1:有效
     */
    public void setIsForever(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.is_forever</code>. 是否永久有效 0:无效 1:有效
     */
    public Integer getIsForever() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.start_date</code>. 有效期-起始
     */
    public void setStartDate(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.start_date</code>. 有效期-起始
     */
    public Timestamp getStartDate() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.end_date</code>. 有效期-结束
     */
    public void setEndDate(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.end_date</code>. 有效期-结束
     */
    public Timestamp getEndDate() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.first</code>. 优先级
     */
    public void setFirst(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.first</code>. 优先级
     */
    public Integer getFirst() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.activity_action</code>. 活动类型：1：活动送券 2：大转盘抽奖 3：跳转自定义链接 4: 积分 5:余额  6:分裂
     */
    public void setActivityAction(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.activity_action</code>. 活动类型：1：活动送券 2：大转盘抽奖 3：跳转自定义链接 4: 积分 5:余额  6:分裂
     */
    public Byte getActivityAction() {
        return (Byte) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.mrking_voucher_id</code>. 活动优惠券，逗号分隔
     */
    public void setMrkingVoucherId(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.mrking_voucher_id</code>. 活动优惠券，逗号分隔
     */
    public String getMrkingVoucherId() {
        return (String) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.lottery_id</code>. 抽奖活动id
     */
    public void setLotteryId(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.lottery_id</code>. 抽奖活动id
     */
    public Integer getLotteryId() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.customize_img_path</code>. 活动有礼跳转活动图片路径
     */
    public void setCustomizeImgPath(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.customize_img_path</code>. 活动有礼跳转活动图片路径
     */
    public String getCustomizeImgPath() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.customize_url</code>. 活动有礼跳转活动链接
     */
    public void setCustomizeUrl(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.customize_url</code>. 活动有礼跳转活动链接
     */
    public String getCustomizeUrl() {
        return (String) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.give_score</code>. 积分
     */
    public void setGiveScore(BigDecimal value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.give_score</code>. 积分
     */
    public BigDecimal getGiveScore() {
        return (BigDecimal) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.give_account</code>. 余额
     */
    public void setGiveAccount(BigDecimal value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.give_account</code>. 余额
     */
    public BigDecimal getGiveAccount() {
        return (BigDecimal) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.award_num</code>. 发放数量
     */
    public void setAwardNum(Integer value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.award_num</code>. 发放数量
     */
    public Integer getAwardNum() {
        return (Integer) get(16);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.status</code>. 状态： 1: 正常 0: 关闭
     */
    public void setStatus(Byte value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.status</code>. 状态： 1: 正常 0: 关闭
     */
    public Byte getStatus() {
        return (Byte) get(17);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.del_flag</code>.
     */
    public void setDelFlag(Byte value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.del_flag</code>.
     */
    public Byte getDelFlag() {
        return (Byte) get(18);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.del_time</code>.
     */
    public void setDelTime(Integer value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.del_time</code>.
     */
    public Integer getDelTime() {
        return (Integer) get(19);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(20);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_coopen_activity.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_coopen_activity.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(21);
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
    // Record22 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row22<Integer, Byte, String, String, String, Integer, Timestamp, Timestamp, Integer, Byte, String, Integer, String, String, BigDecimal, BigDecimal, Integer, Byte, Byte, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row22) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row22<Integer, Byte, String, String, String, Integer, Timestamp, Timestamp, Integer, Byte, String, Integer, String, String, BigDecimal, BigDecimal, Integer, Byte, Byte, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row22) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CoopenActivity.COOPEN_ACTIVITY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field2() {
        return CoopenActivity.COOPEN_ACTIVITY.ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return CoopenActivity.COOPEN_ACTIVITY.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return CoopenActivity.COOPEN_ACTIVITY.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return CoopenActivity.COOPEN_ACTIVITY.BG_IMGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return CoopenActivity.COOPEN_ACTIVITY.IS_FOREVER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return CoopenActivity.COOPEN_ACTIVITY.START_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return CoopenActivity.COOPEN_ACTIVITY.END_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return CoopenActivity.COOPEN_ACTIVITY.FIRST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return CoopenActivity.COOPEN_ACTIVITY.ACTIVITY_ACTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return CoopenActivity.COOPEN_ACTIVITY.MRKING_VOUCHER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return CoopenActivity.COOPEN_ACTIVITY.LOTTERY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return CoopenActivity.COOPEN_ACTIVITY.CUSTOMIZE_IMG_PATH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return CoopenActivity.COOPEN_ACTIVITY.CUSTOMIZE_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field15() {
        return CoopenActivity.COOPEN_ACTIVITY.GIVE_SCORE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field16() {
        return CoopenActivity.COOPEN_ACTIVITY.GIVE_ACCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field17() {
        return CoopenActivity.COOPEN_ACTIVITY.AWARD_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field18() {
        return CoopenActivity.COOPEN_ACTIVITY.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field19() {
        return CoopenActivity.COOPEN_ACTIVITY.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field20() {
        return CoopenActivity.COOPEN_ACTIVITY.DEL_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field21() {
        return CoopenActivity.COOPEN_ACTIVITY.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field22() {
        return CoopenActivity.COOPEN_ACTIVITY.UPDATE_TIME;
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
    public Byte component2() {
        return getAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getBgImgs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getIsForever();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getStartDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component8() {
        return getEndDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component10() {
        return getActivityAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getMrkingVoucherId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component12() {
        return getLotteryId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getCustomizeImgPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component14() {
        return getCustomizeUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component15() {
        return getGiveScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component16() {
        return getGiveAccount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component17() {
        return getAwardNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component18() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component19() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component20() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component21() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component22() {
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
    public Byte value2() {
        return getAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getBgImgs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getIsForever();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getStartDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getEndDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getActivityAction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getMrkingVoucherId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getLotteryId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getCustomizeImgPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getCustomizeUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value15() {
        return getGiveScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value16() {
        return getGiveAccount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value17() {
        return getAwardNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value18() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value19() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value20() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value21() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value22() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value2(Byte value) {
        setAction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value4(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value5(String value) {
        setBgImgs(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value6(Integer value) {
        setIsForever(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value7(Timestamp value) {
        setStartDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value8(Timestamp value) {
        setEndDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value9(Integer value) {
        setFirst(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value10(Byte value) {
        setActivityAction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value11(String value) {
        setMrkingVoucherId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value12(Integer value) {
        setLotteryId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value13(String value) {
        setCustomizeImgPath(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value14(String value) {
        setCustomizeUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value15(BigDecimal value) {
        setGiveScore(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value16(BigDecimal value) {
        setGiveAccount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value17(Integer value) {
        setAwardNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value18(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value19(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value20(Integer value) {
        setDelTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value21(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord value22(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CoopenActivityRecord values(Integer value1, Byte value2, String value3, String value4, String value5, Integer value6, Timestamp value7, Timestamp value8, Integer value9, Byte value10, String value11, Integer value12, String value13, String value14, BigDecimal value15, BigDecimal value16, Integer value17, Byte value18, Byte value19, Integer value20, Timestamp value21, Timestamp value22) {
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
        value20(value20);
        value21(value21);
        value22(value22);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CoopenActivityRecord
     */
    public CoopenActivityRecord() {
        super(CoopenActivity.COOPEN_ACTIVITY);
    }

    /**
     * Create a detached, initialised CoopenActivityRecord
     */
    public CoopenActivityRecord(Integer id, Byte action, String name, String title, String bgImgs, Integer isForever, Timestamp startDate, Timestamp endDate, Integer first, Byte activityAction, String mrkingVoucherId, Integer lotteryId, String customizeImgPath, String customizeUrl, BigDecimal giveScore, BigDecimal giveAccount, Integer awardNum, Byte status, Byte delFlag, Integer delTime, Timestamp createTime, Timestamp updateTime) {
        super(CoopenActivity.COOPEN_ACTIVITY);

        set(0, id);
        set(1, action);
        set(2, name);
        set(3, title);
        set(4, bgImgs);
        set(5, isForever);
        set(6, startDate);
        set(7, endDate);
        set(8, first);
        set(9, activityAction);
        set(10, mrkingVoucherId);
        set(11, lotteryId);
        set(12, customizeImgPath);
        set(13, customizeUrl);
        set(14, giveScore);
        set(15, giveAccount);
        set(16, awardNum);
        set(17, status);
        set(18, delFlag);
        set(19, delTime);
        set(20, createTime);
        set(21, updateTime);
    }
}

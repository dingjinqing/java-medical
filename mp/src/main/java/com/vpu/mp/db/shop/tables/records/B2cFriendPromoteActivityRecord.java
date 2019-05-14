/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cFriendPromoteActivity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;
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
public class B2cFriendPromoteActivityRecord extends UpdatableRecordImpl<B2cFriendPromoteActivityRecord> {

    private static final long serialVersionUID = 2081045826;

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.act_code</code>. 活动编码
     */
    public void setActCode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.act_code</code>. 活动编码
     */
    public String getActCode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.act_name</code>. 活动名称
     */
    public void setActName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.act_name</code>. 活动名称
     */
    public String getActName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.start_time</code>. 活动起始时间
     */
    public void setStartTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.start_time</code>. 活动起始时间
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.end_time</code>. 活动截止时间
     */
    public void setEndTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.end_time</code>. 活动截止时间
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.reward_type</code>. 奖励类型：0赠送商品，1折扣商品，2赠送优惠券
     */
    public void setRewardType(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.reward_type</code>. 奖励类型：0赠送商品，1折扣商品，2赠送优惠券
     */
    public Byte getRewardType() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.reward_content</code>. 奖励内容
     */
    public void setRewardContent(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.reward_content</code>. 奖励内容
     */
    public String getRewardContent() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.reward_duration</code>. 奖励有效期
     */
    public void setRewardDuration(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.reward_duration</code>. 奖励有效期
     */
    public Integer getRewardDuration() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.reward_duration_unit</code>. 奖励有效期单位：0小时，1天，2周，3月，4年
     */
    public void setRewardDurationUnit(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.reward_duration_unit</code>. 奖励有效期单位：0小时，1天，2周，3月，4年
     */
    public Byte getRewardDurationUnit() {
        return (Byte) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.promote_amount</code>. 所需助力值
     */
    public void setPromoteAmount(BigDecimal value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.promote_amount</code>. 所需助力值
     */
    public BigDecimal getPromoteAmount() {
        return (BigDecimal) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.promote_times</code>. 所需助力次数
     */
    public void setPromoteTimes(UInteger value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.promote_times</code>. 所需助力次数
     */
    public UInteger getPromoteTimes() {
        return (UInteger) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.launch_limit_duration</code>. 发起次数限制时长，0不限制
     */
    public void setLaunchLimitDuration(UInteger value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.launch_limit_duration</code>. 发起次数限制时长，0不限制
     */
    public UInteger getLaunchLimitDuration() {
        return (UInteger) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.launch_limit_unit</code>. 发起次数限制时长单位：0天，1周，2月，3年
     */
    public void setLaunchLimitUnit(Byte value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.launch_limit_unit</code>. 发起次数限制时长单位：0天，1周，2月，3年
     */
    public Byte getLaunchLimitUnit() {
        return (Byte) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.launch_limit_times</code>. 发起限制次数，0不限制
     */
    public void setLaunchLimitTimes(UByte value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.launch_limit_times</code>. 发起限制次数，0不限制
     */
    public UByte getLaunchLimitTimes() {
        return (UByte) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.share_add_times</code>. 好友分享可获得助力次数
     */
    public void setShareAddTimes(UByte value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.share_add_times</code>. 好友分享可获得助力次数
     */
    public UByte getShareAddTimes() {
        return (UByte) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.promote_type</code>. 单次助力值类型：0平均，1随机
     */
    public void setPromoteType(Byte value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.promote_type</code>. 单次助力值类型：0平均，1随机
     */
    public Byte getPromoteType() {
        return (Byte) get(16);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.promote_condition</code>. 好友助力条件：0可不授权个人信息，1必须授权
     */
    public void setPromoteCondition(UByte value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.promote_condition</code>. 好友助力条件：0可不授权个人信息，1必须授权
     */
    public UByte getPromoteCondition() {
        return (UByte) get(17);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.failed_send_type</code>. 助力失败赠送类型：0不赠送，1优惠券，2积分
     */
    public void setFailedSendType(UByte value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.failed_send_type</code>. 助力失败赠送类型：0不赠送，1优惠券，2积分
     */
    public UByte getFailedSendType() {
        return (UByte) get(18);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.failed_send_content</code>. 助力失败赠送内容
     */
    public void setFailedSendContent(Integer value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.failed_send_content</code>. 助力失败赠送内容
     */
    public Integer getFailedSendContent() {
        return (Integer) get(19);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.activity_share_type</code>. 助力活动分享样式类型：0默认样式，1自定义样式
     */
    public void setActivityShareType(UByte value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.activity_share_type</code>. 助力活动分享样式类型：0默认样式，1自定义样式
     */
    public UByte getActivityShareType() {
        return (UByte) get(20);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.custom_share_word</code>. 自定义分享样式文案
     */
    public void setCustomShareWord(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.custom_share_word</code>. 自定义分享样式文案
     */
    public String getCustomShareWord() {
        return (String) get(21);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.share_img_type</code>. 自定义分享图片类型：0首页截图，1自定义图片
     */
    public void setShareImgType(Byte value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.share_img_type</code>. 自定义分享图片类型：0首页截图，1自定义图片
     */
    public Byte getShareImgType() {
        return (Byte) get(22);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.custom_img_path</code>. 自定义分享样式图片路径
     */
    public void setCustomImgPath(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.custom_img_path</code>. 自定义分享样式图片路径
     */
    public String getCustomImgPath() {
        return (String) get(23);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.is_block</code>. 活动状态：0未停用，1已停用
     */
    public void setIsBlock(Byte value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.is_block</code>. 活动状态：0未停用，1已停用
     */
    public Byte getIsBlock() {
        return (Byte) get(24);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.del_flag</code>. 删除标识：0未删除，1已删除
     */
    public void setDelFlag(Byte value) {
        set(25, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.del_flag</code>. 删除标识：0未删除，1已删除
     */
    public Byte getDelFlag() {
        return (Byte) get(25);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.in_time</code>. 添加时间
     */
    public void setInTime(Timestamp value) {
        set(26, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.in_time</code>. 添加时间
     */
    public Timestamp getInTime() {
        return (Timestamp) get(26);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.up_time</code>. 更新时间
     */
    public void setUpTime(Timestamp value) {
        set(27, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.up_time</code>. 更新时间
     */
    public Timestamp getUpTime() {
        return (Timestamp) get(27);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.use_discount</code>. 是否可与会员卡折扣、优惠券叠加使用：0不可叠加，1可叠加
     */
    public void setUseDiscount(Byte value) {
        set(28, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.use_discount</code>. 是否可与会员卡折扣、优惠券叠加使用：0不可叠加，1可叠加
     */
    public Byte getUseDiscount() {
        return (Byte) get(28);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_activity.use_score</code>. 是否可使用积分抵扣部分金额：0不可抵扣，1可抵扣
     */
    public void setUseScore(Byte value) {
        set(29, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_activity.use_score</code>. 是否可使用积分抵扣部分金额：0不可抵扣，1可抵扣
     */
    public Byte getUseScore() {
        return (Byte) get(29);
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
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cFriendPromoteActivityRecord
     */
    public B2cFriendPromoteActivityRecord() {
        super(B2cFriendPromoteActivity.B2C_FRIEND_PROMOTE_ACTIVITY);
    }

    /**
     * Create a detached, initialised B2cFriendPromoteActivityRecord
     */
    public B2cFriendPromoteActivityRecord(UInteger id, Integer shopId, String actCode, String actName, Timestamp startTime, Timestamp endTime, Byte rewardType, String rewardContent, Integer rewardDuration, Byte rewardDurationUnit, BigDecimal promoteAmount, UInteger promoteTimes, UInteger launchLimitDuration, Byte launchLimitUnit, UByte launchLimitTimes, UByte shareAddTimes, Byte promoteType, UByte promoteCondition, UByte failedSendType, Integer failedSendContent, UByte activityShareType, String customShareWord, Byte shareImgType, String customImgPath, Byte isBlock, Byte delFlag, Timestamp inTime, Timestamp upTime, Byte useDiscount, Byte useScore) {
        super(B2cFriendPromoteActivity.B2C_FRIEND_PROMOTE_ACTIVITY);

        set(0, id);
        set(1, shopId);
        set(2, actCode);
        set(3, actName);
        set(4, startTime);
        set(5, endTime);
        set(6, rewardType);
        set(7, rewardContent);
        set(8, rewardDuration);
        set(9, rewardDurationUnit);
        set(10, promoteAmount);
        set(11, promoteTimes);
        set(12, launchLimitDuration);
        set(13, launchLimitUnit);
        set(14, launchLimitTimes);
        set(15, shareAddTimes);
        set(16, promoteType);
        set(17, promoteCondition);
        set(18, failedSendType);
        set(19, failedSendContent);
        set(20, activityShareType);
        set(21, customShareWord);
        set(22, shareImgType);
        set(23, customImgPath);
        set(24, isBlock);
        set(25, delFlag);
        set(26, inTime);
        set(27, upTime);
        set(28, useDiscount);
        set(29, useScore);
    }
}

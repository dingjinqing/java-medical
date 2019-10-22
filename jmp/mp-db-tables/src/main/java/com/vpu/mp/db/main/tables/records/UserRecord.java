/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.User;

import java.math.BigDecimal;
import java.sql.Date;
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
public class UserRecord extends UpdatableRecordImpl<UserRecord> {

    private static final long serialVersionUID = 504698766;

    /**
     * Setter for <code>mini_main.b2c_user.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_user.shop_id</code>.
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.shop_id</code>.
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_user.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_user.username</code>. 用户名
     */
    public void setUsername(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.username</code>. 用户名
     */
    public String getUsername() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_user.user_pwd</code>. 密码
     */
    public void setUserPwd(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.user_pwd</code>. 密码
     */
    public String getUserPwd() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_user.user_cid</code>.
     */
    public void setUserCid(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.user_cid</code>.
     */
    public String getUserCid() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_user.mobile</code>. 电话
     */
    public void setMobile(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.mobile</code>. 电话
     */
    public String getMobile() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_user.user_code</code>. 会员卡号
     */
    public void setUserCode(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.user_code</code>. 会员卡号
     */
    public String getUserCode() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_user.wx_openid</code>.
     */
    public void setWxOpenid(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.wx_openid</code>.
     */
    public String getWxOpenid() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_main.b2c_user.email</code>. 邮箱
     */
    public void setEmail(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.email</code>. 邮箱
     */
    public String getEmail() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_main.b2c_user.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>mini_main.b2c_user.wechat</code>. 微信
     */
    public void setWechat(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.wechat</code>. 微信
     */
    public String getWechat() {
        return (String) get(11);
    }

    /**
     * Setter for <code>mini_main.b2c_user.fanli_grade</code>. 返利会员级别
     */
    public void setFanliGrade(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.fanli_grade</code>. 返利会员级别
     */
    public Integer getFanliGrade() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>mini_main.b2c_user.user_grade</code>. 会员级别
     */
    public void setUserGrade(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.user_grade</code>. 会员级别
     */
    public Integer getUserGrade() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>mini_main.b2c_user.invite</code>.
     */
    public void setInvite(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.invite</code>.
     */
    public Integer getInvite() {
        return (Integer) get(14);
    }

    /**
     * Setter for <code>mini_main.b2c_user.invite_source</code>. 邀请来源
     */
    public void setInviteSource(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.invite_source</code>. 邀请来源
     */
    public String getInviteSource() {
        return (String) get(15);
    }

    /**
     * Setter for <code>mini_main.b2c_user.invitation_code</code>. 邀请码
     */
    public void setInvitationCode(Integer value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.invitation_code</code>. 邀请码
     */
    public Integer getInvitationCode() {
        return (Integer) get(16);
    }

    /**
     * Setter for <code>mini_main.b2c_user.account</code>. 用户余额
     */
    public void setAccount(BigDecimal value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.account</code>. 用户余额
     */
    public BigDecimal getAccount() {
        return (BigDecimal) get(17);
    }

    /**
     * Setter for <code>mini_main.b2c_user.discount</code>. 折扣
     */
    public void setDiscount(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.discount</code>. 折扣
     */
    public Integer getDiscount() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>mini_main.b2c_user.discount_grade</code>. 会员折扣等级
     */
    public void setDiscountGrade(Integer value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.discount_grade</code>. 会员折扣等级
     */
    public Integer getDiscountGrade() {
        return (Integer) get(19);
    }

    /**
     * Setter for <code>mini_main.b2c_user.del_flag</code>.
     */
    public void setDelFlag(Byte value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.del_flag</code>.
     */
    public Byte getDelFlag() {
        return (Byte) get(20);
    }

    /**
     * Setter for <code>mini_main.b2c_user.del_time</code>. 删除时间
     */
    public void setDelTime(Timestamp value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.del_time</code>. 删除时间
     */
    public Timestamp getDelTime() {
        return (Timestamp) get(21);
    }

    /**
     * Setter for <code>mini_main.b2c_user.growth</code>. 成长值
     */
    public void setGrowth(Integer value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.growth</code>. 成长值
     */
    public Integer getGrowth() {
        return (Integer) get(22);
    }

    /**
     * Setter for <code>mini_main.b2c_user.score</code>. 积分
     */
    public void setScore(Integer value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.score</code>. 积分
     */
    public Integer getScore() {
        return (Integer) get(23);
    }

    /**
     * Setter for <code>mini_main.b2c_user.source</code>. 门店来源-1未录入0后台&gt;0为门店
     */
    public void setSource(Integer value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.source</code>. 门店来源-1未录入0后台&gt;0为门店
     */
    public Integer getSource() {
        return (Integer) get(24);
    }

    /**
     * Setter for <code>mini_main.b2c_user.invite_id</code>. 邀请人ID
     */
    public void setInviteId(Integer value) {
        set(25, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.invite_id</code>. 邀请人ID
     */
    public Integer getInviteId() {
        return (Integer) get(25);
    }

    /**
     * Setter for <code>mini_main.b2c_user.invite_expiry_date</code>. 邀请失效时间
     */
    public void setInviteExpiryDate(Date value) {
        set(26, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.invite_expiry_date</code>. 邀请失效时间
     */
    public Date getInviteExpiryDate() {
        return (Date) get(26);
    }

    /**
     * Setter for <code>mini_main.b2c_user.wx_union_id</code>. 小程序union_id
     */
    public void setWxUnionId(String value) {
        set(27, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.wx_union_id</code>. 小程序union_id
     */
    public String getWxUnionId() {
        return (String) get(27);
    }

    /**
     * Setter for <code>mini_main.b2c_user.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(28, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(28);
    }

    /**
     * Setter for <code>mini_main.b2c_user.is_distributor</code>. 是否是分销员
     */
    public void setIsDistributor(Byte value) {
        set(29, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.is_distributor</code>. 是否是分销员
     */
    public Byte getIsDistributor() {
        return (Byte) get(29);
    }

    /**
     * Setter for <code>mini_main.b2c_user.invite_act_id</code>. 邀请来源活动ID
     */
    public void setInviteActId(Integer value) {
        set(30, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.invite_act_id</code>. 邀请来源活动ID
     */
    public Integer getInviteActId() {
        return (Integer) get(30);
    }

    /**
     * Setter for <code>mini_main.b2c_user.distributor_level</code>. 用户等级
     */
    public void setDistributorLevel(Byte value) {
        set(31, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.distributor_level</code>. 用户等级
     */
    public Byte getDistributorLevel() {
        return (Byte) get(31);
    }

    /**
     * Setter for <code>mini_main.b2c_user.ali_user_id</code>. 支付宝用户ID
     */
    public void setAliUserId(String value) {
        set(32, value);
    }

    /**
     * Getter for <code>mini_main.b2c_user.ali_user_id</code>. 支付宝用户ID
     */
    public String getAliUserId() {
        return (String) get(32);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(Long id, Integer shopId, Integer userId, String username, String userPwd, String userCid, String mobile, String userCode, String wxOpenid, String email, Timestamp createTime, String wechat, Integer fanliGrade, Integer userGrade, Integer invite, String inviteSource, Integer invitationCode, BigDecimal account, Integer discount, Integer discountGrade, Byte delFlag, Timestamp delTime, Integer growth, Integer score, Integer source, Integer inviteId, Date inviteExpiryDate, String wxUnionId, Timestamp updateTime, Byte isDistributor, Integer inviteActId, Byte distributorLevel, String aliUserId) {
        super(User.USER);

        set(0, id);
        set(1, shopId);
        set(2, userId);
        set(3, username);
        set(4, userPwd);
        set(5, userCid);
        set(6, mobile);
        set(7, userCode);
        set(8, wxOpenid);
        set(9, email);
        set(10, createTime);
        set(11, wechat);
        set(12, fanliGrade);
        set(13, userGrade);
        set(14, invite);
        set(15, inviteSource);
        set(16, invitationCode);
        set(17, account);
        set(18, discount);
        set(19, discountGrade);
        set(20, delFlag);
        set(21, delTime);
        set(22, growth);
        set(23, score);
        set(24, source);
        set(25, inviteId);
        set(26, inviteExpiryDate);
        set(27, wxUnionId);
        set(28, updateTime);
        set(29, isDistributor);
        set(30, inviteActId);
        set(31, distributorLevel);
        set(32, aliUserId);
    }
}

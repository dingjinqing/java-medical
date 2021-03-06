/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.FriendPromoteLaunch;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
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
public class FriendPromoteLaunchRecord extends UpdatableRecordImpl<FriendPromoteLaunchRecord> implements Record11<Integer, Integer, Integer, Byte, String, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1698443492;

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_launch.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_launch.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_launch.user_id</code>. 发起会员id
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_launch.user_id</code>. 发起会员id
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_launch.promote_id</code>. 助力活动id
     */
    public void setPromoteId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_launch.promote_id</code>. 助力活动id
     */
    public Integer getPromoteId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_launch.promote_status</code>. 助力状态：0助力中，1助力完成待领取，2助力完成已领取，3助力失效，4助力未完成失败
     */
    public void setPromoteStatus(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_launch.promote_status</code>. 助力状态：0助力中，1助力完成待领取，2助力完成已领取，3助力失效，4助力未完成失败
     */
    public Byte getPromoteStatus() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_launch.order_sn</code>. 助力完成生产的订单编码
     */
    public void setOrderSn(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_launch.order_sn</code>. 助力完成生产的订单编码
     */
    public String getOrderSn() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_launch.launch_time</code>. 发起时间
     */
    public void setLaunchTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_launch.launch_time</code>. 发起时间
     */
    public Timestamp getLaunchTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_launch.success_time</code>. 助力成功时间
     */
    public void setSuccessTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_launch.success_time</code>. 助力成功时间
     */
    public Timestamp getSuccessTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_launch.del_flag</code>. 删除标识：0未删除，1已删除
     */
    public void setDelFlag(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_launch.del_flag</code>. 删除标识：0未删除，1已删除
     */
    public Byte getDelFlag() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_launch.del_time</code>. 过期时间
     */
    public void setDelTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_launch.del_time</code>. 过期时间
     */
    public Timestamp getDelTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_launch.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_launch.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_launch.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_launch.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(10);
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
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Integer, Integer, Byte, String, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Timestamp> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Integer, Integer, Byte, String, Timestamp, Timestamp, Byte, Timestamp, Timestamp, Timestamp> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.PROMOTE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.PROMOTE_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.ORDER_SN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.LAUNCH_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.SUCCESS_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.DEL_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field11() {
        return FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH.UPDATE_TIME;
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
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getPromoteId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component4() {
        return getPromoteStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getLaunchTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getSuccessTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component8() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component11() {
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
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getPromoteId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getPromoteStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getLaunchTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getSuccessTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value11() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord value3(Integer value) {
        setPromoteId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord value4(Byte value) {
        setPromoteStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord value5(String value) {
        setOrderSn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord value6(Timestamp value) {
        setLaunchTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord value7(Timestamp value) {
        setSuccessTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord value8(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord value9(Timestamp value) {
        setDelTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord value10(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord value11(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FriendPromoteLaunchRecord values(Integer value1, Integer value2, Integer value3, Byte value4, String value5, Timestamp value6, Timestamp value7, Byte value8, Timestamp value9, Timestamp value10, Timestamp value11) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FriendPromoteLaunchRecord
     */
    public FriendPromoteLaunchRecord() {
        super(FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH);
    }

    /**
     * Create a detached, initialised FriendPromoteLaunchRecord
     */
    public FriendPromoteLaunchRecord(Integer id, Integer userId, Integer promoteId, Byte promoteStatus, String orderSn, Timestamp launchTime, Timestamp successTime, Byte delFlag, Timestamp delTime, Timestamp createTime, Timestamp updateTime) {
        super(FriendPromoteLaunch.FRIEND_PROMOTE_LAUNCH);

        set(0, id);
        set(1, userId);
        set(2, promoteId);
        set(3, promoteStatus);
        set(4, orderSn);
        set(5, launchTime);
        set(6, successTime);
        set(7, delFlag);
        set(8, delTime);
        set(9, createTime);
        set(10, updateTime);
    }
}

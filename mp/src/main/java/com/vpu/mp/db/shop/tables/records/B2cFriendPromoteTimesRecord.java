/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cFriendPromoteTimes;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;
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
public class B2cFriendPromoteTimesRecord extends UpdatableRecordImpl<B2cFriendPromoteTimesRecord> implements Record8<UInteger, Integer, Integer, Integer, Integer, Timestamp, Timestamp, Byte> {

    private static final long serialVersionUID = 30472846;

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_times.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_times.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_times.launch_id</code>. 助力活动发起ID
     */
    public void setLaunchId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_times.launch_id</code>. 助力活动发起ID
     */
    public Integer getLaunchId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_times.user_id</code>. 助力会员ID
     */
    public void setUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_times.user_id</code>. 助力会员ID
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_times.share_times</code>. 分享的次数
     */
    public void setShareTimes(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_times.share_times</code>. 分享的次数
     */
    public Integer getShareTimes() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_times.own_promote_times</code>. 总的所有助力次数
     */
    public void setOwnPromoteTimes(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_times.own_promote_times</code>. 总的所有助力次数
     */
    public Integer getOwnPromoteTimes() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_times.in_time</code>. 助力时间
     */
    public void setInTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_times.in_time</code>. 助力时间
     */
    public Timestamp getInTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_times.up_time</code>. 更新时间
     */
    public void setUpTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_times.up_time</code>. 更新时间
     */
    public Timestamp getUpTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_friend_promote_times.is_auth</code>. 是否有授权增加次数：0没有，1有
     */
    public void setIsAuth(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_friend_promote_times.is_auth</code>. 是否有授权增加次数：0没有，1有
     */
    public Byte getIsAuth() {
        return (Byte) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<UInteger, Integer, Integer, Integer, Integer, Timestamp, Timestamp, Byte> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<UInteger, Integer, Integer, Integer, Integer, Timestamp, Timestamp, Byte> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return B2cFriendPromoteTimes.B2C_FRIEND_PROMOTE_TIMES.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cFriendPromoteTimes.B2C_FRIEND_PROMOTE_TIMES.LAUNCH_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cFriendPromoteTimes.B2C_FRIEND_PROMOTE_TIMES.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return B2cFriendPromoteTimes.B2C_FRIEND_PROMOTE_TIMES.SHARE_TIMES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return B2cFriendPromoteTimes.B2C_FRIEND_PROMOTE_TIMES.OWN_PROMOTE_TIMES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return B2cFriendPromoteTimes.B2C_FRIEND_PROMOTE_TIMES.IN_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return B2cFriendPromoteTimes.B2C_FRIEND_PROMOTE_TIMES.UP_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return B2cFriendPromoteTimes.B2C_FRIEND_PROMOTE_TIMES.IS_AUTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getLaunchId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getShareTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getOwnPromoteTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component8() {
        return getIsAuth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getLaunchId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getShareTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getOwnPromoteTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getUpTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getIsAuth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFriendPromoteTimesRecord value1(UInteger value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFriendPromoteTimesRecord value2(Integer value) {
        setLaunchId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFriendPromoteTimesRecord value3(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFriendPromoteTimesRecord value4(Integer value) {
        setShareTimes(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFriendPromoteTimesRecord value5(Integer value) {
        setOwnPromoteTimes(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFriendPromoteTimesRecord value6(Timestamp value) {
        setInTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFriendPromoteTimesRecord value7(Timestamp value) {
        setUpTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFriendPromoteTimesRecord value8(Byte value) {
        setIsAuth(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cFriendPromoteTimesRecord values(UInteger value1, Integer value2, Integer value3, Integer value4, Integer value5, Timestamp value6, Timestamp value7, Byte value8) {
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
     * Create a detached B2cFriendPromoteTimesRecord
     */
    public B2cFriendPromoteTimesRecord() {
        super(B2cFriendPromoteTimes.B2C_FRIEND_PROMOTE_TIMES);
    }

    /**
     * Create a detached, initialised B2cFriendPromoteTimesRecord
     */
    public B2cFriendPromoteTimesRecord(UInteger id, Integer launchId, Integer userId, Integer shareTimes, Integer ownPromoteTimes, Timestamp inTime, Timestamp upTime, Byte isAuth) {
        super(B2cFriendPromoteTimes.B2C_FRIEND_PROMOTE_TIMES);

        set(0, id);
        set(1, launchId);
        set(2, userId);
        set(3, shareTimes);
        set(4, ownPromoteTimes);
        set(5, inTime);
        set(6, upTime);
        set(7, isAuth);
    }
}

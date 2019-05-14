/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cJoinGroupList;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
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
public class B2cJoinGroupListRecord extends UpdatableRecordImpl<B2cJoinGroupListRecord> implements Record15<Integer, Integer, Integer, Integer, Integer, Byte, Integer, String, Byte, Byte, Byte, Timestamp, Timestamp, Timestamp, Integer> {

    private static final long serialVersionUID = -851975522;

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.group_draw_id</code>. 拼团抽奖ID
     */
    public void setGroupDrawId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.group_draw_id</code>. 拼团抽奖ID
     */
    public Integer getGroupDrawId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.goods_id</code>. 商品ID
     */
    public void setGoodsId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.goods_id</code>. 商品ID
     */
    public Integer getGoodsId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.group_id</code>. 拼团ID
     */
    public void setGroupId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.group_id</code>. 拼团ID
     */
    public Integer getGroupId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.user_id</code>. 用户ID
     */
    public void setUserId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.user_id</code>. 用户ID
     */
    public Integer getUserId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.is_grouper</code>. 是否是团长 1是 0不是
     */
    public void setIsGrouper(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.is_grouper</code>. 是否是团长 1是 0不是
     */
    public Byte getIsGrouper() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.invite_user_id</code>. 邀请人
     */
    public void setInviteUserId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.invite_user_id</code>. 邀请人
     */
    public Integer getInviteUserId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.order_sn</code>. 订单编号
     */
    public void setOrderSn(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.order_sn</code>. 订单编号
     */
    public String getOrderSn() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.status</code>. 0:拼团中 1：已成团 2：未成团
     */
    public void setStatus(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.status</code>. 0:拼团中 1：已成团 2：未成团
     */
    public Byte getStatus() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.draw_status</code>. 0:未开奖 1：已开奖
     */
    public void setDrawStatus(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.draw_status</code>. 0:未开奖 1：已开奖
     */
    public Byte getDrawStatus() {
        return (Byte) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.is_win_draw</code>. 是否已中奖 1：已中奖
     */
    public void setIsWinDraw(Byte value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.is_win_draw</code>. 是否已中奖 1：已中奖
     */
    public Byte getIsWinDraw() {
        return (Byte) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.open_time</code>. 开团时间
     */
    public void setOpenTime(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.open_time</code>. 开团时间
     */
    public Timestamp getOpenTime() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.end_time</code>. 成团时间(达到最小成团数量就记录)
     */
    public void setEndTime(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.end_time</code>. 成团时间(达到最小成团数量就记录)
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.draw_time</code>. 开奖时间
     */
    public void setDrawTime(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.draw_time</code>. 开奖时间
     */
    public Timestamp getDrawTime() {
        return (Timestamp) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_join_group_list.invite_user_num</code>. 邀请用户数
     */
    public void setInviteUserNum(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_join_group_list.invite_user_num</code>. 邀请用户数
     */
    public Integer getInviteUserNum() {
        return (Integer) get(14);
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
    // Record15 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, Integer, Integer, Integer, Integer, Byte, Integer, String, Byte, Byte, Byte, Timestamp, Timestamp, Timestamp, Integer> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, Integer, Integer, Integer, Integer, Byte, Integer, String, Byte, Byte, Byte, Timestamp, Timestamp, Timestamp, Integer> valuesRow() {
        return (Row15) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.GROUP_DRAW_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.GROUP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.IS_GROUPER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.INVITE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.ORDER_SN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.DRAW_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field11() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.IS_WIN_DRAW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field12() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.OPEN_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.END_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field14() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.DRAW_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field15() {
        return B2cJoinGroupList.B2C_JOIN_GROUP_LIST.INVITE_USER_NUM;
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
        return getGroupDrawId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getGroupId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component6() {
        return getIsGrouper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getInviteUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component9() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component10() {
        return getDrawStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component11() {
        return getIsWinDraw();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component12() {
        return getOpenTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component13() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component14() {
        return getDrawTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component15() {
        return getInviteUserNum();
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
        return getGroupDrawId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getGoodsId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getGroupId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getIsGrouper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getInviteUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getDrawStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value11() {
        return getIsWinDraw();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value12() {
        return getOpenTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value13() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value14() {
        return getDrawTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value15() {
        return getInviteUserNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value2(Integer value) {
        setGroupDrawId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value3(Integer value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value4(Integer value) {
        setGroupId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value5(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value6(Byte value) {
        setIsGrouper(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value7(Integer value) {
        setInviteUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value8(String value) {
        setOrderSn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value9(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value10(Byte value) {
        setDrawStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value11(Byte value) {
        setIsWinDraw(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value12(Timestamp value) {
        setOpenTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value13(Timestamp value) {
        setEndTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value14(Timestamp value) {
        setDrawTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord value15(Integer value) {
        setInviteUserNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cJoinGroupListRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Byte value6, Integer value7, String value8, Byte value9, Byte value10, Byte value11, Timestamp value12, Timestamp value13, Timestamp value14, Integer value15) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cJoinGroupListRecord
     */
    public B2cJoinGroupListRecord() {
        super(B2cJoinGroupList.B2C_JOIN_GROUP_LIST);
    }

    /**
     * Create a detached, initialised B2cJoinGroupListRecord
     */
    public B2cJoinGroupListRecord(Integer id, Integer groupDrawId, Integer goodsId, Integer groupId, Integer userId, Byte isGrouper, Integer inviteUserId, String orderSn, Byte status, Byte drawStatus, Byte isWinDraw, Timestamp openTime, Timestamp endTime, Timestamp drawTime, Integer inviteUserNum) {
        super(B2cJoinGroupList.B2C_JOIN_GROUP_LIST);

        set(0, id);
        set(1, groupDrawId);
        set(2, goodsId);
        set(3, groupId);
        set(4, userId);
        set(5, isGrouper);
        set(6, inviteUserId);
        set(7, orderSn);
        set(8, status);
        set(9, drawStatus);
        set(10, isWinDraw);
        set(11, openTime);
        set(12, endTime);
        set(13, drawTime);
        set(14, inviteUserNum);
    }
}

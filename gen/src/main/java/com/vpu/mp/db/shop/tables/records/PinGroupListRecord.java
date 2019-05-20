/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.PinGroupList;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
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
public class PinGroupListRecord extends UpdatableRecordImpl<PinGroupListRecord> implements Record10<Integer, Integer, Integer, Integer, Integer, Byte, String, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1762992724;

    /**
     * Setter for <code>mini_shop_1.b2c_pin_group_list.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_pin_group_list.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_pin_group_list.pin_activity_id</code>. 拼团活动定义ID
     */
    public void setPinActivityId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_pin_group_list.pin_activity_id</code>. 拼团活动定义ID
     */
    public Integer getPinActivityId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_pin_group_list.goods_id</code>.
     */
    public void setGoodsId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_pin_group_list.goods_id</code>.
     */
    public Integer getGoodsId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_pin_group_list.group_id</code>. 拼团ID
     */
    public void setGroupId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_pin_group_list.group_id</code>. 拼团ID
     */
    public Integer getGroupId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_pin_group_list.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_pin_group_list.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_pin_group_list.is_grouper</code>. 是否为团长 1：是 0：否
     */
    public void setIsGrouper(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_pin_group_list.is_grouper</code>. 是否为团长 1：是 0：否
     */
    public Byte getIsGrouper() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_pin_group_list.order_sn</code>. 订单编号
     */
    public void setOrderSn(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_pin_group_list.order_sn</code>. 订单编号
     */
    public String getOrderSn() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_pin_group_list.status</code>. 0: 拼团中 1:拼团成功 2:拼团失败
     */
    public void setStatus(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_pin_group_list.status</code>. 0: 拼团中 1:拼团成功 2:拼团失败
     */
    public Byte getStatus() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_pin_group_list.start_time</code>. 开团时间
     */
    public void setStartTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_pin_group_list.start_time</code>. 开团时间
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_pin_group_list.end_time</code>. 成团时间
     */
    public void setEndTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_pin_group_list.end_time</code>. 成团时间
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Integer, Integer, Integer, Byte, String, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Integer, Integer, Integer, Byte, String, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return PinGroupList.PIN_GROUP_LIST.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return PinGroupList.PIN_GROUP_LIST.PIN_ACTIVITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return PinGroupList.PIN_GROUP_LIST.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return PinGroupList.PIN_GROUP_LIST.GROUP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return PinGroupList.PIN_GROUP_LIST.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return PinGroupList.PIN_GROUP_LIST.IS_GROUPER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return PinGroupList.PIN_GROUP_LIST.ORDER_SN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return PinGroupList.PIN_GROUP_LIST.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return PinGroupList.PIN_GROUP_LIST.START_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return PinGroupList.PIN_GROUP_LIST.END_TIME;
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
        return getPinActivityId();
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
    public String component7() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component8() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getEndTime();
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
        return getPinActivityId();
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
    public String value7() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getStartTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getEndTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PinGroupListRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PinGroupListRecord value2(Integer value) {
        setPinActivityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PinGroupListRecord value3(Integer value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PinGroupListRecord value4(Integer value) {
        setGroupId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PinGroupListRecord value5(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PinGroupListRecord value6(Byte value) {
        setIsGrouper(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PinGroupListRecord value7(String value) {
        setOrderSn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PinGroupListRecord value8(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PinGroupListRecord value9(Timestamp value) {
        setStartTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PinGroupListRecord value10(Timestamp value) {
        setEndTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PinGroupListRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Byte value6, String value7, Byte value8, Timestamp value9, Timestamp value10) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PinGroupListRecord
     */
    public PinGroupListRecord() {
        super(PinGroupList.PIN_GROUP_LIST);
    }

    /**
     * Create a detached, initialised PinGroupListRecord
     */
    public PinGroupListRecord(Integer id, Integer pinActivityId, Integer goodsId, Integer groupId, Integer userId, Byte isGrouper, String orderSn, Byte status, Timestamp startTime, Timestamp endTime) {
        super(PinGroupList.PIN_GROUP_LIST);

        set(0, id);
        set(1, pinActivityId);
        set(2, goodsId);
        set(3, groupId);
        set(4, userId);
        set(5, isGrouper);
        set(6, orderSn);
        set(7, status);
        set(8, startTime);
        set(9, endTime);
    }
}

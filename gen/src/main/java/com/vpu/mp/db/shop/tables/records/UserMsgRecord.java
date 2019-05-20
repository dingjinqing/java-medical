/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.UserMsg;

import java.sql.Date;
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
public class UserMsgRecord extends UpdatableRecordImpl<UserMsgRecord> implements Record10<Integer, Integer, Integer, String, String, Date, String, Byte, String, Timestamp> {

    private static final long serialVersionUID = -1369360704;

    /**
     * Setter for <code>mini_shop_1.b2c_user_msg.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_user_msg.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_user_msg.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_user_msg.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_user_msg.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_user_msg.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_user_msg.msg_type</code>. login,comment,order_finish,discount,upgrade,return_order,deliver_goods,article
     */
    public void setMsgType(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_user_msg.msg_type</code>. login,comment,order_finish,discount,upgrade,return_order,deliver_goods,article
     */
    public String getMsgType() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_user_msg.msg_desc</code>.
     */
    public void setMsgDesc(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_user_msg.msg_desc</code>.
     */
    public String getMsgDesc() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_user_msg.msg_date</code>.
     */
    public void setMsgDate(Date value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_user_msg.msg_date</code>.
     */
    public Date getMsgDate() {
        return (Date) get(5);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_user_msg.msg_id</code>. 评论时是商品id，订单是订单id
     */
    public void setMsgId(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_user_msg.msg_id</code>. 评论时是商品id，订单是订单id
     */
    public String getMsgId() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_user_msg.status</code>. 0:未读，1:已读
     */
    public void setStatus(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_user_msg.status</code>. 0:未读，1:已读
     */
    public Byte getStatus() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_user_msg.url</code>.
     */
    public void setUrl(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_user_msg.url</code>.
     */
    public String getUrl() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_user_msg.in_time</code>.
     */
    public void setInTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_user_msg.in_time</code>.
     */
    public Timestamp getInTime() {
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
    public Row10<Integer, Integer, Integer, String, String, Date, String, Byte, String, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Integer, String, String, Date, String, Byte, String, Timestamp> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserMsg.USER_MSG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UserMsg.USER_MSG.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return UserMsg.USER_MSG.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return UserMsg.USER_MSG.MSG_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return UserMsg.USER_MSG.MSG_DESC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field6() {
        return UserMsg.USER_MSG.MSG_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return UserMsg.USER_MSG.MSG_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return UserMsg.USER_MSG.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return UserMsg.USER_MSG.URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return UserMsg.USER_MSG.IN_TIME;
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
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getMsgType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getMsgDesc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component6() {
        return getMsgDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getMsgId();
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
    public String component9() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getInTime();
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
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getMsgType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getMsgDesc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value6() {
        return getMsgDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getMsgId();
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
    public String value9() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMsgRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMsgRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMsgRecord value3(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMsgRecord value4(String value) {
        setMsgType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMsgRecord value5(String value) {
        setMsgDesc(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMsgRecord value6(Date value) {
        setMsgDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMsgRecord value7(String value) {
        setMsgId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMsgRecord value8(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMsgRecord value9(String value) {
        setUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMsgRecord value10(Timestamp value) {
        setInTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMsgRecord values(Integer value1, Integer value2, Integer value3, String value4, String value5, Date value6, String value7, Byte value8, String value9, Timestamp value10) {
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
     * Create a detached UserMsgRecord
     */
    public UserMsgRecord() {
        super(UserMsg.USER_MSG);
    }

    /**
     * Create a detached, initialised UserMsgRecord
     */
    public UserMsgRecord(Integer id, Integer userId, Integer shopId, String msgType, String msgDesc, Date msgDate, String msgId, Byte status, String url, Timestamp inTime) {
        super(UserMsg.USER_MSG);

        set(0, id);
        set(1, userId);
        set(2, shopId);
        set(3, msgType);
        set(4, msgDesc);
        set(5, msgDate);
        set(6, msgId);
        set(7, status);
        set(8, url);
        set(9, inTime);
    }
}

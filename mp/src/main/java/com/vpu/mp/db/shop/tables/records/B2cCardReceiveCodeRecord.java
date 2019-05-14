/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cCardReceiveCode;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
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
public class B2cCardReceiveCodeRecord extends UpdatableRecordImpl<B2cCardReceiveCodeRecord> implements Record14<Integer, Integer, Integer, Integer, String, String, String, Integer, Timestamp, String, Timestamp, Timestamp, Byte, Timestamp> {

    private static final long serialVersionUID = -1828548389;

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.card_id</code>. 卡号ID
     */
    public void setCardId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.card_id</code>. 卡号ID
     */
    public Integer getCardId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.batch_id</code>. 批次ID
     */
    public void setBatchId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.batch_id</code>. 批次ID
     */
    public Integer getBatchId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.group_id</code>. 分组ID
     */
    public void setGroupId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.group_id</code>. 分组ID
     */
    public Integer getGroupId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.code</code>. 领取码
     */
    public void setCode(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.code</code>. 领取码
     */
    public String getCode() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.card_no</code>. 卡号
     */
    public void setCardNo(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.card_no</code>. 卡号
     */
    public String getCardNo() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.card_pwd</code>. 卡密码
     */
    public void setCardPwd(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.card_pwd</code>. 卡密码
     */
    public String getCardPwd() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.user_id</code>. 领取人
     */
    public void setUserId(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.user_id</code>. 领取人
     */
    public Integer getUserId() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.receive_time</code>. 领取时间
     */
    public void setReceiveTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.receive_time</code>. 领取时间
     */
    public Timestamp getReceiveTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.error_msg</code>. 错误说明
     */
    public void setErrorMsg(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.error_msg</code>. 错误说明
     */
    public String getErrorMsg() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.add_time</code>.
     */
    public void setAddTime(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.add_time</code>.
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.del_flag</code>.
     */
    public void setDelFlag(Byte value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.del_flag</code>.
     */
    public Byte getDelFlag() {
        return (Byte) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_card_receive_code.del_time</code>.
     */
    public void setDelTime(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_card_receive_code.del_time</code>.
     */
    public Timestamp getDelTime() {
        return (Timestamp) get(13);
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
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, Integer, Integer, Integer, String, String, String, Integer, Timestamp, String, Timestamp, Timestamp, Byte, Timestamp> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, Integer, Integer, Integer, String, String, String, Integer, Timestamp, String, Timestamp, Timestamp, Byte, Timestamp> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.CARD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.BATCH_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.GROUP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.CARD_NO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.CARD_PWD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.RECEIVE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.ERROR_MSG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field11() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.ADD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field12() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field13() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.DEL_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field14() {
        return B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE.DEL_TIME;
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
        return getCardId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getBatchId();
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
    public String component5() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getCardNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getCardPwd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getReceiveTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getErrorMsg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component11() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component12() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component13() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component14() {
        return getDelTime();
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
        return getCardId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getBatchId();
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
    public String value5() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getCardNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getCardPwd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getReceiveTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getErrorMsg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value11() {
        return getAddTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value12() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value13() {
        return getDelFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value14() {
        return getDelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value2(Integer value) {
        setCardId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value3(Integer value) {
        setBatchId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value4(Integer value) {
        setGroupId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value5(String value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value6(String value) {
        setCardNo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value7(String value) {
        setCardPwd(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value8(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value9(Timestamp value) {
        setReceiveTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value10(String value) {
        setErrorMsg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value11(Timestamp value) {
        setAddTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value12(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value13(Byte value) {
        setDelFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord value14(Timestamp value) {
        setDelTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCardReceiveCodeRecord values(Integer value1, Integer value2, Integer value3, Integer value4, String value5, String value6, String value7, Integer value8, Timestamp value9, String value10, Timestamp value11, Timestamp value12, Byte value13, Timestamp value14) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cCardReceiveCodeRecord
     */
    public B2cCardReceiveCodeRecord() {
        super(B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE);
    }

    /**
     * Create a detached, initialised B2cCardReceiveCodeRecord
     */
    public B2cCardReceiveCodeRecord(Integer id, Integer cardId, Integer batchId, Integer groupId, String code, String cardNo, String cardPwd, Integer userId, Timestamp receiveTime, String errorMsg, Timestamp addTime, Timestamp updateTime, Byte delFlag, Timestamp delTime) {
        super(B2cCardReceiveCode.B2C_CARD_RECEIVE_CODE);

        set(0, id);
        set(1, cardId);
        set(2, batchId);
        set(3, groupId);
        set(4, code);
        set(5, cardNo);
        set(6, cardPwd);
        set(7, userId);
        set(8, receiveTime);
        set(9, errorMsg);
        set(10, addTime);
        set(11, updateTime);
        set(12, delFlag);
        set(13, delTime);
    }
}

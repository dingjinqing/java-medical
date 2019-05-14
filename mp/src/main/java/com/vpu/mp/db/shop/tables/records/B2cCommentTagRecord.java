/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.B2cCommentTag;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
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
public class B2cCommentTagRecord extends UpdatableRecordImpl<B2cCommentTagRecord> implements Record7<Integer, String, Integer, Integer, String, Timestamp, Byte> {

    private static final long serialVersionUID = 39707887;

    /**
     * Setter for <code>mini_shop_471752.b2c_comment_tag.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_comment_tag.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_comment_tag.commtag</code>.
     */
    public void setCommtag(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_comment_tag.commtag</code>.
     */
    public String getCommtag() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_comment_tag.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_comment_tag.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_comment_tag.goods_id</code>.
     */
    public void setGoodsId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_comment_tag.goods_id</code>.
     */
    public Integer getGoodsId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_comment_tag.order_sn</code>.
     */
    public void setOrderSn(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_comment_tag.order_sn</code>.
     */
    public String getOrderSn() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_comment_tag.in_time</code>.
     */
    public void setInTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_comment_tag.in_time</code>.
     */
    public Timestamp getInTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_comment_tag.flag</code>. 0:未审批,1:审批通过,2:审批未通过
     */
    public void setFlag(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_comment_tag.flag</code>. 0:未审批,1:审批通过,2:审批未通过
     */
    public Byte getFlag() {
        return (Byte) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, Integer, Integer, String, Timestamp, Byte> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, Integer, Integer, String, Timestamp, Byte> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return B2cCommentTag.B2C_COMMENT_TAG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cCommentTag.B2C_COMMENT_TAG.COMMTAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return B2cCommentTag.B2C_COMMENT_TAG.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return B2cCommentTag.B2C_COMMENT_TAG.GOODS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return B2cCommentTag.B2C_COMMENT_TAG.ORDER_SN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return B2cCommentTag.B2C_COMMENT_TAG.IN_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return B2cCommentTag.B2C_COMMENT_TAG.FLAG;
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
    public String component2() {
        return getCommtag();
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
        return getGoodsId();
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
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component7() {
        return getFlag();
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
    public String value2() {
        return getCommtag();
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
        return getGoodsId();
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
        return getInTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCommentTagRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCommentTagRecord value2(String value) {
        setCommtag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCommentTagRecord value3(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCommentTagRecord value4(Integer value) {
        setGoodsId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCommentTagRecord value5(String value) {
        setOrderSn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCommentTagRecord value6(Timestamp value) {
        setInTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCommentTagRecord value7(Byte value) {
        setFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCommentTagRecord values(Integer value1, String value2, Integer value3, Integer value4, String value5, Timestamp value6, Byte value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cCommentTagRecord
     */
    public B2cCommentTagRecord() {
        super(B2cCommentTag.B2C_COMMENT_TAG);
    }

    /**
     * Create a detached, initialised B2cCommentTagRecord
     */
    public B2cCommentTagRecord(Integer id, String commtag, Integer userId, Integer goodsId, String orderSn, Timestamp inTime, Byte flag) {
        super(B2cCommentTag.B2C_COMMENT_TAG);

        set(0, id);
        set(1, commtag);
        set(2, userId);
        set(3, goodsId);
        set(4, orderSn);
        set(5, inTime);
        set(6, flag);
    }
}

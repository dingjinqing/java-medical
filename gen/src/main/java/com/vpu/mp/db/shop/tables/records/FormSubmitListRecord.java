/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.FormSubmitList;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
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
public class FormSubmitListRecord extends UpdatableRecordImpl<FormSubmitListRecord> implements Record9<UInteger, UInteger, Integer, Integer, String, String, Timestamp, Integer, String> {

    private static final long serialVersionUID = 1884073242;

    /**
     * Setter for <code>mini_shop_1.b2c_form_submit_list.submit_id</code>.
     */
    public void setSubmitId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_form_submit_list.submit_id</code>.
     */
    public UInteger getSubmitId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_form_submit_list.page_id</code>.
     */
    public void setPageId(UInteger value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_form_submit_list.page_id</code>.
     */
    public UInteger getPageId() {
        return (UInteger) get(1);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_form_submit_list.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_form_submit_list.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_form_submit_list.user_id</code>. 用户Id
     */
    public void setUserId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_form_submit_list.user_id</code>. 用户Id
     */
    public Integer getUserId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_form_submit_list.open_id</code>. 微信OpenId
     */
    public void setOpenId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_form_submit_list.open_id</code>. 微信OpenId
     */
    public String getOpenId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_form_submit_list.nick_name</code>. 微信昵称
     */
    public void setNickName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_form_submit_list.nick_name</code>. 微信昵称
     */
    public String getNickName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_form_submit_list.create_time</code>. 提交时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_form_submit_list.create_time</code>. 提交时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_form_submit_list.send_score</code>. 送积分
     */
    public void setSendScore(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_form_submit_list.send_score</code>. 送积分
     */
    public Integer getSendScore() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>mini_shop_1.b2c_form_submit_list.send_coupons</code>. 送优惠券
     */
    public void setSendCoupons(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_1.b2c_form_submit_list.send_coupons</code>. 送优惠券
     */
    public String getSendCoupons() {
        return (String) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<UInteger, UInteger, Integer, Integer, String, String, Timestamp, Integer, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<UInteger, UInteger, Integer, Integer, String, String, Timestamp, Integer, String> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return FormSubmitList.FORM_SUBMIT_LIST.SUBMIT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field2() {
        return FormSubmitList.FORM_SUBMIT_LIST.PAGE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return FormSubmitList.FORM_SUBMIT_LIST.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return FormSubmitList.FORM_SUBMIT_LIST.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return FormSubmitList.FORM_SUBMIT_LIST.OPEN_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return FormSubmitList.FORM_SUBMIT_LIST.NICK_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return FormSubmitList.FORM_SUBMIT_LIST.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return FormSubmitList.FORM_SUBMIT_LIST.SEND_SCORE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return FormSubmitList.FORM_SUBMIT_LIST.SEND_COUPONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component1() {
        return getSubmitId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component2() {
        return getPageId();
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
    public Integer component4() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getOpenId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getNickName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getSendScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getSendCoupons();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getSubmitId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value2() {
        return getPageId();
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
    public Integer value4() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getOpenId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getNickName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getSendScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getSendCoupons();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormSubmitListRecord value1(UInteger value) {
        setSubmitId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormSubmitListRecord value2(UInteger value) {
        setPageId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormSubmitListRecord value3(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormSubmitListRecord value4(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormSubmitListRecord value5(String value) {
        setOpenId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormSubmitListRecord value6(String value) {
        setNickName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormSubmitListRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormSubmitListRecord value8(Integer value) {
        setSendScore(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormSubmitListRecord value9(String value) {
        setSendCoupons(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormSubmitListRecord values(UInteger value1, UInteger value2, Integer value3, Integer value4, String value5, String value6, Timestamp value7, Integer value8, String value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FormSubmitListRecord
     */
    public FormSubmitListRecord() {
        super(FormSubmitList.FORM_SUBMIT_LIST);
    }

    /**
     * Create a detached, initialised FormSubmitListRecord
     */
    public FormSubmitListRecord(UInteger submitId, UInteger pageId, Integer shopId, Integer userId, String openId, String nickName, Timestamp createTime, Integer sendScore, String sendCoupons) {
        super(FormSubmitList.FORM_SUBMIT_LIST);

        set(0, submitId);
        set(1, pageId);
        set(2, shopId);
        set(3, userId);
        set(4, openId);
        set(5, nickName);
        set(6, createTime);
        set(7, sendScore);
        set(8, sendCoupons);
    }
}

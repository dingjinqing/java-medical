/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.ShopQuestionFeedback;

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
public class ShopQuestionFeedbackRecord extends UpdatableRecordImpl<ShopQuestionFeedbackRecord> implements Record11<Integer, Integer, Integer, String, String, Byte, Timestamp, String, String, String, String> {

    private static final long serialVersionUID = -1559867280;

    /**
     * Setter for <code>jmini_main.b2c_shop_question_feedback.question_feedback_id</code>.
     */
    public void setQuestionFeedbackId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>jmini_main.b2c_shop_question_feedback.question_feedback_id</code>.
     */
    public Integer getQuestionFeedbackId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>jmini_main.b2c_shop_question_feedback.shop_id</code>. 反馈店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>jmini_main.b2c_shop_question_feedback.shop_id</code>. 反馈店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>jmini_main.b2c_shop_question_feedback.category_id</code>. 反馈问题分类
     */
    public void setCategoryId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>jmini_main.b2c_shop_question_feedback.category_id</code>. 反馈问题分类
     */
    public Integer getCategoryId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>jmini_main.b2c_shop_question_feedback.mobile</code>. 填写的手机号
     */
    public void setMobile(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>jmini_main.b2c_shop_question_feedback.mobile</code>. 填写的手机号
     */
    public String getMobile() {
        return (String) get(3);
    }

    /**
     * Setter for <code>jmini_main.b2c_shop_question_feedback.content</code>.
     */
    public void setContent(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>jmini_main.b2c_shop_question_feedback.content</code>.
     */
    public String getContent() {
        return (String) get(4);
    }

    /**
     * Setter for <code>jmini_main.b2c_shop_question_feedback.is_look</code>. 1:已查看
     */
    public void setIsLook(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>jmini_main.b2c_shop_question_feedback.is_look</code>. 1:已查看
     */
    public Byte getIsLook() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>jmini_main.b2c_shop_question_feedback.create_time</code>. 反馈时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>jmini_main.b2c_shop_question_feedback.create_time</code>. 反馈时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>jmini_main.b2c_shop_question_feedback.qf_img</code>. 图片
     */
    public void setQfImg(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>jmini_main.b2c_shop_question_feedback.qf_img</code>. 图片
     */
    public String getQfImg() {
        return (String) get(7);
    }

    /**
     * Setter for <code>jmini_main.b2c_shop_question_feedback.version</code>. 使用系统版本
     */
    public void setVersion(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>jmini_main.b2c_shop_question_feedback.version</code>. 使用系统版本
     */
    public String getVersion() {
        return (String) get(8);
    }

    /**
     * Setter for <code>jmini_main.b2c_shop_question_feedback.submit_user</code>. 提交账号
     */
    public void setSubmitUser(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>jmini_main.b2c_shop_question_feedback.submit_user</code>. 提交账号
     */
    public String getSubmitUser() {
        return (String) get(9);
    }

    /**
     * Setter for <code>jmini_main.b2c_shop_question_feedback.submit_user_phone</code>. 提交账号绑定的手机号
     */
    public void setSubmitUserPhone(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>jmini_main.b2c_shop_question_feedback.submit_user_phone</code>. 提交账号绑定的手机号
     */
    public String getSubmitUserPhone() {
        return (String) get(10);
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
    public Row11<Integer, Integer, Integer, String, String, Byte, Timestamp, String, String, String, String> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Integer, Integer, String, String, Byte, Timestamp, String, String, String, String> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK.QUESTION_FEEDBACK_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK.CATEGORY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK.MOBILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK.CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK.IS_LOOK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK.QF_IMG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK.SUBMIT_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK.SUBMIT_USER_PHONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getQuestionFeedbackId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getCategoryId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component6() {
        return getIsLook();
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
    public String component8() {
        return getQfImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getSubmitUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getSubmitUserPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getQuestionFeedbackId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getShopId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getCategoryId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getIsLook();
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
    public String value8() {
        return getQfImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getSubmitUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getSubmitUserPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord value1(Integer value) {
        setQuestionFeedbackId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord value3(Integer value) {
        setCategoryId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord value4(String value) {
        setMobile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord value5(String value) {
        setContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord value6(Byte value) {
        setIsLook(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord value8(String value) {
        setQfImg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord value9(String value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord value10(String value) {
        setSubmitUser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord value11(String value) {
        setSubmitUserPhone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopQuestionFeedbackRecord values(Integer value1, Integer value2, Integer value3, String value4, String value5, Byte value6, Timestamp value7, String value8, String value9, String value10, String value11) {
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
     * Create a detached ShopQuestionFeedbackRecord
     */
    public ShopQuestionFeedbackRecord() {
        super(ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK);
    }

    /**
     * Create a detached, initialised ShopQuestionFeedbackRecord
     */
    public ShopQuestionFeedbackRecord(Integer questionFeedbackId, Integer shopId, Integer categoryId, String mobile, String content, Byte isLook, Timestamp createTime, String qfImg, String version, String submitUser, String submitUserPhone) {
        super(ShopQuestionFeedback.SHOP_QUESTION_FEEDBACK);

        set(0, questionFeedbackId);
        set(1, shopId);
        set(2, categoryId);
        set(3, mobile);
        set(4, content);
        set(5, isLook);
        set(6, createTime);
        set(7, qfImg);
        set(8, version);
        set(9, submitUser);
        set(10, submitUserPhone);
    }
}

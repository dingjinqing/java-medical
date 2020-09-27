/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.DoctorComment;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record20;
import org.jooq.Row20;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * 医师评价和打分
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DoctorCommentRecord extends UpdatableRecordImpl<DoctorCommentRecord> implements Record20<Integer, Integer, String, Integer, String, Integer, String, String, Integer, String, Integer, Byte, Byte, String, String, Integer, Byte, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = -154463622;

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.user_id</code>. 用户id
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.user_id</code>. 用户id
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.user_name</code>. 用户昵称
     */
    public void setUserName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.user_name</code>. 用户昵称
     */
    public String getUserName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.patient_id</code>. 患者id
     */
    public void setPatientId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.patient_id</code>. 患者id
     */
    public Integer getPatientId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.patient_name</code>. 患者名称
     */
    public void setPatientName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.patient_name</code>. 患者名称
     */
    public String getPatientName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.doctor_id</code>. 医师id
     */
    public void setDoctorId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.doctor_id</code>. 医师id
     */
    public Integer getDoctorId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.doctor_code</code>. 医师院内编码
     */
    public void setDoctorCode(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.doctor_code</code>. 医师院内编码
     */
    public String getDoctorCode() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.doctor_name</code>. 医师名称
     */
    public void setDoctorName(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.doctor_name</code>. 医师名称
     */
    public String getDoctorName() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.order_id</code>. 订单id
     */
    public void setOrderId(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.order_id</code>. 订单id
     */
    public Integer getOrderId() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.order_sn</code>. 订单编号
     */
    public void setOrderSn(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.order_sn</code>. 订单编号
     */
    public String getOrderSn() {
        return (String) get(9);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.im_session_id</code>. 会话id
     */
    public void setImSessionId(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.im_session_id</code>. 会话id
     */
    public Integer getImSessionId() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.stars</code>. 评价星级1~5
     */
    public void setStars(Byte value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.stars</code>. 评价星级1~5
     */
    public Byte getStars() {
        return (Byte) get(11);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.is_anonymou</code>. 匿名状态 0.未匿名；1.匿名
     */
    public void setIsAnonymou(Byte value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.is_anonymou</code>. 匿名状态 0.未匿名；1.匿名
     */
    public Byte getIsAnonymou() {
        return (Byte) get(12);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.tag</code>. 评价标签
     */
    public void setTag(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.tag</code>. 评价标签
     */
    public String getTag() {
        return (String) get(13);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.comm_note</code>. 评论内容
     */
    public void setCommNote(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.comm_note</code>. 评论内容
     */
    public String getCommNote() {
        return (String) get(14);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.top</code>. 置顶
     */
    public void setTop(Integer value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.top</code>. 置顶
     */
    public Integer getTop() {
        return (Integer) get(15);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.audit_status</code>. 0:未审批,1:审批通过,2:审批未通过
     */
    public void setAuditStatus(Byte value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.audit_status</code>. 0:未审批,1:审批通过,2:审批未通过
     */
    public Byte getAuditStatus() {
        return (Byte) get(16);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.is_delete</code>.
     */
    public void setIsDelete(Byte value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.is_delete</code>.
     */
    public Byte getIsDelete() {
        return (Byte) get(17);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(18);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_doctor_comment.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_doctor_comment.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(19);
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
    // Record20 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row20<Integer, Integer, String, Integer, String, Integer, String, String, Integer, String, Integer, Byte, Byte, String, String, Integer, Byte, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row20) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row20<Integer, Integer, String, Integer, String, Integer, String, String, Integer, String, Integer, Byte, Byte, String, String, Integer, Byte, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row20) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DoctorComment.DOCTOR_COMMENT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return DoctorComment.DOCTOR_COMMENT.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return DoctorComment.DOCTOR_COMMENT.USER_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return DoctorComment.DOCTOR_COMMENT.PATIENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return DoctorComment.DOCTOR_COMMENT.PATIENT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return DoctorComment.DOCTOR_COMMENT.DOCTOR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return DoctorComment.DOCTOR_COMMENT.DOCTOR_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return DoctorComment.DOCTOR_COMMENT.DOCTOR_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return DoctorComment.DOCTOR_COMMENT.ORDER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return DoctorComment.DOCTOR_COMMENT.ORDER_SN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return DoctorComment.DOCTOR_COMMENT.IM_SESSION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field12() {
        return DoctorComment.DOCTOR_COMMENT.STARS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field13() {
        return DoctorComment.DOCTOR_COMMENT.IS_ANONYMOU;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return DoctorComment.DOCTOR_COMMENT.TAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return DoctorComment.DOCTOR_COMMENT.COMM_NOTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field16() {
        return DoctorComment.DOCTOR_COMMENT.TOP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field17() {
        return DoctorComment.DOCTOR_COMMENT.AUDIT_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field18() {
        return DoctorComment.DOCTOR_COMMENT.IS_DELETE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field19() {
        return DoctorComment.DOCTOR_COMMENT.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field20() {
        return DoctorComment.DOCTOR_COMMENT.UPDATE_TIME;
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
    public String component3() {
        return getUserName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getPatientId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getPatientName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getDoctorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getDoctorCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getDoctorName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getOrderId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component11() {
        return getImSessionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component12() {
        return getStars();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component13() {
        return getIsAnonymou();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component14() {
        return getTag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component15() {
        return getCommNote();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component16() {
        return getTop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component17() {
        return getAuditStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component18() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component19() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component20() {
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
    public String value3() {
        return getUserName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getPatientId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getPatientName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getDoctorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getDoctorCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getDoctorName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getOrderId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getOrderSn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getImSessionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value12() {
        return getStars();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value13() {
        return getIsAnonymou();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getTag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getCommNote();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value16() {
        return getTop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value17() {
        return getAuditStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value18() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value19() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value20() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value3(String value) {
        setUserName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value4(Integer value) {
        setPatientId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value5(String value) {
        setPatientName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value6(Integer value) {
        setDoctorId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value7(String value) {
        setDoctorCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value8(String value) {
        setDoctorName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value9(Integer value) {
        setOrderId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value10(String value) {
        setOrderSn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value11(Integer value) {
        setImSessionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value12(Byte value) {
        setStars(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value13(Byte value) {
        setIsAnonymou(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value14(String value) {
        setTag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value15(String value) {
        setCommNote(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value16(Integer value) {
        setTop(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value17(Byte value) {
        setAuditStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value18(Byte value) {
        setIsDelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value19(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord value20(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorCommentRecord values(Integer value1, Integer value2, String value3, Integer value4, String value5, Integer value6, String value7, String value8, Integer value9, String value10, Integer value11, Byte value12, Byte value13, String value14, String value15, Integer value16, Byte value17, Byte value18, Timestamp value19, Timestamp value20) {
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
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        value20(value20);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DoctorCommentRecord
     */
    public DoctorCommentRecord() {
        super(DoctorComment.DOCTOR_COMMENT);
    }

    /**
     * Create a detached, initialised DoctorCommentRecord
     */
    public DoctorCommentRecord(Integer id, Integer userId, String userName, Integer patientId, String patientName, Integer doctorId, String doctorCode, String doctorName, Integer orderId, String orderSn, Integer imSessionId, Byte stars, Byte isAnonymou, String tag, String commNote, Integer top, Byte auditStatus, Byte isDelete, Timestamp createTime, Timestamp updateTime) {
        super(DoctorComment.DOCTOR_COMMENT);

        set(0, id);
        set(1, userId);
        set(2, userName);
        set(3, patientId);
        set(4, patientName);
        set(5, doctorId);
        set(6, doctorCode);
        set(7, doctorName);
        set(8, orderId);
        set(9, orderSn);
        set(10, imSessionId);
        set(11, stars);
        set(12, isAnonymou);
        set(13, tag);
        set(14, commNote);
        set(15, top);
        set(16, auditStatus);
        set(17, isDelete);
        set(18, createTime);
        set(19, updateTime);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.jmd.table.main.tables.records;



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
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UploadUyunRecordRecord extends UpdatableRecordImpl<UploadUyunRecordRecord> implements Record10<Long, Integer, Integer, String, String, Timestamp, Byte, Timestamp, String, Timestamp> {

    private static final long serialVersionUID = 1614495698;

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.record_id</code>.
     */
    public void setRecordId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.record_id</code>.
     */
    public Long getRecordId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.file_size</code>. 文件大小
     */
    public void setFileSize(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.file_size</code>. 文件大小
     */
    public Integer getFileSize() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.file_url</code>. 文件url
     */
    public void setFileUrl(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.file_url</code>. 文件url
     */
    public String getFileUrl() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.update_timestamp</code>. 文件更新时间戳
     */
    public void setUpdateTimestamp(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.update_timestamp</code>. 文件更新时间戳
     */
    public String getUpdateTimestamp() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.update_date</code>. 文件更新日期
     */
    public void setUpdateDate(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.update_date</code>. 文件更新日期
     */
    public Timestamp getUpdateDate() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.upload_status</code>. 文件上传状态 0：上传成功；1：上传失败（或开关关闭后未上传的文件）
     */
    public void setUploadStatus(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.upload_status</code>. 文件上传状态 0：上传成功；1：上传失败（或开关关闭后未上传的文件）
     */
    public Byte getUploadStatus() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.upload_time</code>. 文件上传时间
     */
    public void setUploadTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.upload_time</code>. 文件上传时间
     */
    public Timestamp getUploadTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.fail_reason</code>. 失败原因
     */
    public void setFailReason(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.fail_reason</code>. 失败原因
     */
    public String getFailReason() {
        return (String) get(8);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.fail_date</code>. 失败时间
     */
    public void setFailDate(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.fail_date</code>. 失败时间
     */
    public Timestamp getFailDate() {
        return (Timestamp) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Long, Integer, Integer, String, String, Timestamp, Byte, Timestamp, String, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Long, Integer, Integer, String, String, Timestamp, Byte, Timestamp, String, Timestamp> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return UploadUyunRecord.UPLOAD_UYUN_RECORD.RECORD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UploadUyunRecord.UPLOAD_UYUN_RECORD.SHOP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return UploadUyunRecord.UPLOAD_UYUN_RECORD.FILE_SIZE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return UploadUyunRecord.UPLOAD_UYUN_RECORD.FILE_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return UploadUyunRecord.UPLOAD_UYUN_RECORD.UPDATE_TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return UploadUyunRecord.UPLOAD_UYUN_RECORD.UPDATE_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return UploadUyunRecord.UPLOAD_UYUN_RECORD.UPLOAD_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return UploadUyunRecord.UPLOAD_UYUN_RECORD.UPLOAD_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return UploadUyunRecord.UPLOAD_UYUN_RECORD.FAIL_REASON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return UploadUyunRecord.UPLOAD_UYUN_RECORD.FAIL_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getRecordId();
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
        return getFileSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getFileUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getUpdateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getUpdateDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component7() {
        return getUploadStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component8() {
        return getUploadTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getFailReason();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getFailDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getRecordId();
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
        return getFileSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getFileUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getUpdateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getUpdateDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getUploadStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getUploadTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getFailReason();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getFailDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadUyunRecordRecord value1(Long value) {
        setRecordId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadUyunRecordRecord value2(Integer value) {
        setShopId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadUyunRecordRecord value3(Integer value) {
        setFileSize(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadUyunRecordRecord value4(String value) {
        setFileUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadUyunRecordRecord value5(String value) {
        setUpdateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadUyunRecordRecord value6(Timestamp value) {
        setUpdateDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadUyunRecordRecord value7(Byte value) {
        setUploadStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadUyunRecordRecord value8(Timestamp value) {
        setUploadTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadUyunRecordRecord value9(String value) {
        setFailReason(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadUyunRecordRecord value10(Timestamp value) {
        setFailDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadUyunRecordRecord values(Long value1, Integer value2, Integer value3, String value4, String value5, Timestamp value6, Byte value7, Timestamp value8, String value9, Timestamp value10) {
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
     * Create a detached UploadUyunRecordRecord
     */
    public UploadUyunRecordRecord() {
        super(UploadUyunRecord.UPLOAD_UYUN_RECORD);
    }

    /**
     * Create a detached, initialised UploadUyunRecordRecord
     */
    public UploadUyunRecordRecord(Long recordId, Integer shopId, Integer fileSize, String fileUrl, String updateTimestamp, Timestamp updateDate, Byte uploadStatus, Timestamp uploadTime, String failReason, Timestamp failDate) {
        super(UploadUyunRecord.UPLOAD_UYUN_RECORD);

        set(0, recordId);
        set(1, shopId);
        set(2, fileSize);
        set(3, fileUrl);
        set(4, updateTimestamp);
        set(5, updateDate);
        set(6, uploadStatus);
        set(7, uploadTime);
        set(8, failReason);
        set(9, failDate);
    }
}
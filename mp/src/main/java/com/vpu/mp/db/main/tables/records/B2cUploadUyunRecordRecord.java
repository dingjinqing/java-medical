/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.B2cUploadUyunRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class B2cUploadUyunRecordRecord extends UpdatableRecordImpl<B2cUploadUyunRecordRecord> implements Record6<Long, String, String, Timestamp, Byte, Timestamp> {

    private static final long serialVersionUID = -448379052;

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
     * Setter for <code>mini_main.b2c_upload_uyun_record.file_url</code>. 文件url
     */
    public void setFileUrl(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.file_url</code>. 文件url
     */
    public String getFileUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.update_timestamp</code>. 文件更新时间戳
     */
    public void setUpdateTimestamp(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.update_timestamp</code>. 文件更新时间戳
     */
    public String getUpdateTimestamp() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.update_date</code>. 文件更新日期
     */
    public void setUpdateDate(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.update_date</code>. 文件更新日期
     */
    public Timestamp getUpdateDate() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.upload_status</code>. 文件上传状态 0：上传成功；1：上传失败（或开关关闭后未上传的文件）
     */
    public void setUploadStatus(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.upload_status</code>. 文件上传状态 0：上传成功；1：上传失败（或开关关闭后未上传的文件）
     */
    public Byte getUploadStatus() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_upload_uyun_record.upload_time</code>. 文件上传时间
     */
    public void setUploadTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_upload_uyun_record.upload_time</code>. 文件上传时间
     */
    public Timestamp getUploadTime() {
        return (Timestamp) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, String, String, Timestamp, Byte, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, String, String, Timestamp, Byte, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return B2cUploadUyunRecord.B2C_UPLOAD_UYUN_RECORD.RECORD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return B2cUploadUyunRecord.B2C_UPLOAD_UYUN_RECORD.FILE_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return B2cUploadUyunRecord.B2C_UPLOAD_UYUN_RECORD.UPDATE_TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return B2cUploadUyunRecord.B2C_UPLOAD_UYUN_RECORD.UPDATE_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return B2cUploadUyunRecord.B2C_UPLOAD_UYUN_RECORD.UPLOAD_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return B2cUploadUyunRecord.B2C_UPLOAD_UYUN_RECORD.UPLOAD_TIME;
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
    public String component2() {
        return getFileUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getUpdateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getUpdateDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component5() {
        return getUploadStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component6() {
        return getUploadTime();
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
    public String value2() {
        return getFileUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getUpdateTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getUpdateDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getUploadStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getUploadTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadUyunRecordRecord value1(Long value) {
        setRecordId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadUyunRecordRecord value2(String value) {
        setFileUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadUyunRecordRecord value3(String value) {
        setUpdateTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadUyunRecordRecord value4(Timestamp value) {
        setUpdateDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadUyunRecordRecord value5(Byte value) {
        setUploadStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadUyunRecordRecord value6(Timestamp value) {
        setUploadTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUploadUyunRecordRecord values(Long value1, String value2, String value3, Timestamp value4, Byte value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached B2cUploadUyunRecordRecord
     */
    public B2cUploadUyunRecordRecord() {
        super(B2cUploadUyunRecord.B2C_UPLOAD_UYUN_RECORD);
    }

    /**
     * Create a detached, initialised B2cUploadUyunRecordRecord
     */
    public B2cUploadUyunRecordRecord(Long recordId, String fileUrl, String updateTimestamp, Timestamp updateDate, Byte uploadStatus, Timestamp uploadTime) {
        super(B2cUploadUyunRecord.B2C_UPLOAD_UYUN_RECORD);

        set(0, recordId);
        set(1, fileUrl);
        set(2, updateTimestamp);
        set(3, updateDate);
        set(4, uploadStatus);
        set(5, uploadTime);
    }
}

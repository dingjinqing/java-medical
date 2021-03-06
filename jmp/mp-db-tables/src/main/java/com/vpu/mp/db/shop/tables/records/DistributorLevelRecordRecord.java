/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables.records;


import com.vpu.mp.db.shop.tables.DistributorLevelRecord;

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
public class DistributorLevelRecordRecord extends UpdatableRecordImpl<DistributorLevelRecordRecord> implements Record10<Integer, Integer, Byte, Byte, String, Byte, String, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1431561250;

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level_record.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level_record.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level_record.user_id</code>. 用户id
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level_record.user_id</code>. 用户id
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level_record.is_go_up</code>. 升降：0降，1升
     */
    public void setIsGoUp(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level_record.is_go_up</code>. 升降：0降，1升
     */
    public Byte getIsGoUp() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level_record.old_level</code>. 旧等级
     */
    public void setOldLevel(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level_record.old_level</code>. 旧等级
     */
    public Byte getOldLevel() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level_record.old_level_name</code>. 旧等级名字
     */
    public void setOldLevelName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level_record.old_level_name</code>. 旧等级名字
     */
    public String getOldLevelName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level_record.new_level</code>. 新等级
     */
    public void setNewLevel(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level_record.new_level</code>. 新等级
     */
    public Byte getNewLevel() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level_record.new_level_name</code>. 新等级名字
     */
    public void setNewLevelName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level_record.new_level_name</code>. 新等级名字
     */
    public String getNewLevelName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level_record.update_note</code>. 更新备注
     */
    public void setUpdateNote(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level_record.update_note</code>. 更新备注
     */
    public String getUpdateNote() {
        return (String) get(7);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level_record.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level_record.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>mini_shop_471752.b2c_distributor_level_record.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_shop_471752.b2c_distributor_level_record.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
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
    public Row10<Integer, Integer, Byte, Byte, String, Byte, String, String, Timestamp, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Byte, Byte, String, Byte, String, String, Timestamp, Timestamp> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD.IS_GO_UP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD.OLD_LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD.OLD_LEVEL_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD.NEW_LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD.NEW_LEVEL_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD.UPDATE_NOTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD.UPDATE_TIME;
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
    public Byte component3() {
        return getIsGoUp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component4() {
        return getOldLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getOldLevelName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component6() {
        return getNewLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getNewLevelName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getUpdateNote();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
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
    public Byte value3() {
        return getIsGoUp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getOldLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getOldLevelName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getNewLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getNewLevelName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getUpdateNote();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecordRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecordRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecordRecord value3(Byte value) {
        setIsGoUp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecordRecord value4(Byte value) {
        setOldLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecordRecord value5(String value) {
        setOldLevelName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecordRecord value6(Byte value) {
        setNewLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecordRecord value7(String value) {
        setNewLevelName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecordRecord value8(String value) {
        setUpdateNote(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecordRecord value9(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecordRecord value10(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DistributorLevelRecordRecord values(Integer value1, Integer value2, Byte value3, Byte value4, String value5, Byte value6, String value7, String value8, Timestamp value9, Timestamp value10) {
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
     * Create a detached DistributorLevelRecordRecord
     */
    public DistributorLevelRecordRecord() {
        super(DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD);
    }

    /**
     * Create a detached, initialised DistributorLevelRecordRecord
     */
    public DistributorLevelRecordRecord(Integer id, Integer userId, Byte isGoUp, Byte oldLevel, String oldLevelName, Byte newLevel, String newLevelName, String updateNote, Timestamp createTime, Timestamp updateTime) {
        super(DistributorLevelRecord.DISTRIBUTOR_LEVEL_RECORD);

        set(0, id);
        set(1, userId);
        set(2, isGoUp);
        set(3, oldLevel);
        set(4, oldLevelName);
        set(5, newLevel);
        set(6, newLevelName);
        set(7, updateNote);
        set(8, createTime);
        set(9, updateTime);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.MpOperateLogRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class MpOperateLog extends TableImpl<MpOperateLogRecord> {

    private static final long serialVersionUID = -831477649;

    /**
     * The reference instance of <code>mini_main.b2c_mp_operate_log</code>
     */
    public static final MpOperateLog MP_OPERATE_LOG = new MpOperateLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MpOperateLogRecord> getRecordType() {
        return MpOperateLogRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_mp_operate_log.operate_id</code>. 自增ID
     */
    public final TableField<MpOperateLogRecord, Integer> OPERATE_ID = createField("operate_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "自增ID");

    /**
     * The column <code>mini_main.b2c_mp_operate_log.app_id</code>. 小程序app_id
     */
    public final TableField<MpOperateLogRecord, String> APP_ID = createField("app_id", org.jooq.impl.SQLDataType.VARCHAR(191).nullable(false).defaultValue(org.jooq.impl.DSL.inline("''", org.jooq.impl.SQLDataType.VARCHAR)), this, "小程序app_id");

    /**
     * The column <code>mini_main.b2c_mp_operate_log.template_id</code>. 小程序模板Id
     */
    public final TableField<MpOperateLogRecord, Integer> TEMPLATE_ID = createField("template_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "小程序模板Id");

    /**
     * The column <code>mini_main.b2c_mp_operate_log.operate_type</code>. 操作类型
     */
    public final TableField<MpOperateLogRecord, Byte> OPERATE_TYPE = createField("operate_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "操作类型");

    /**
     * The column <code>mini_main.b2c_mp_operate_log.operate_state</code>. 操作状态:1成功 2失败
     */
    public final TableField<MpOperateLogRecord, Byte> OPERATE_STATE = createField("operate_state", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "操作状态:1成功 2失败");

    /**
     * The column <code>mini_main.b2c_mp_operate_log.memo</code>. 失败原因
     */
    public final TableField<MpOperateLogRecord, String> MEMO = createField("memo", org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("''", org.jooq.impl.SQLDataType.CLOB)), this, "失败原因");

    /**
     * The column <code>mini_main.b2c_mp_operate_log.create_time</code>. 记录时间
     */
    public final TableField<MpOperateLogRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("current_timestamp()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "记录时间");

    /**
     * Create a <code>mini_main.b2c_mp_operate_log</code> table reference
     */
    public MpOperateLog() {
        this(DSL.name("b2c_mp_operate_log"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_mp_operate_log</code> table reference
     */
    public MpOperateLog(String alias) {
        this(DSL.name(alias), MP_OPERATE_LOG);
    }

    /**
     * Create an aliased <code>mini_main.b2c_mp_operate_log</code> table reference
     */
    public MpOperateLog(Name alias) {
        this(alias, MP_OPERATE_LOG);
    }

    private MpOperateLog(Name alias, Table<MpOperateLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private MpOperateLog(Name alias, Table<MpOperateLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MpOperateLog(Table<O> child, ForeignKey<O, MpOperateLogRecord> key) {
        super(child, key, MP_OPERATE_LOG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return MiniMain.MINI_MAIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.MP_OPERATE_LOG_APP_ID, Indexes.MP_OPERATE_LOG_OPERATE_TYPE, Indexes.MP_OPERATE_LOG_PRIMARY, Indexes.MP_OPERATE_LOG_TEMPLATE_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MpOperateLogRecord, Integer> getIdentity() {
        return Keys.IDENTITY_MP_OPERATE_LOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MpOperateLogRecord> getPrimaryKey() {
        return Keys.KEY_B2C_MP_OPERATE_LOG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MpOperateLogRecord>> getKeys() {
        return Arrays.<UniqueKey<MpOperateLogRecord>>asList(Keys.KEY_B2C_MP_OPERATE_LOG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MpOperateLog as(String alias) {
        return new MpOperateLog(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MpOperateLog as(Name alias) {
        return new MpOperateLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MpOperateLog rename(String name) {
        return new MpOperateLog(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MpOperateLog rename(Name name) {
        return new MpOperateLog(name, null);
    }
}

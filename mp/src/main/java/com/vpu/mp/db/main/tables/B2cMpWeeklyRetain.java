/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.B2cMpWeeklyRetainRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class B2cMpWeeklyRetain extends TableImpl<B2cMpWeeklyRetainRecord> {

    private static final long serialVersionUID = 538786227;

    /**
     * The reference instance of <code>mini_main.b2c_mp_weekly_retain</code>
     */
    public static final B2cMpWeeklyRetain B2C_MP_WEEKLY_RETAIN = new B2cMpWeeklyRetain();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cMpWeeklyRetainRecord> getRecordType() {
        return B2cMpWeeklyRetainRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_mp_weekly_retain.ref_date</code>. 时间，如："20180306-20180312"
     */
    public final TableField<B2cMpWeeklyRetainRecord, String> REF_DATE = createField("ref_date", org.jooq.impl.SQLDataType.CHAR(20).nullable(false), this, "时间，如：\"20180306-20180312\"");

    /**
     * The column <code>mini_main.b2c_mp_weekly_retain.visit_uv_new</code>. 新增用户留存
     */
    public final TableField<B2cMpWeeklyRetainRecord, String> VISIT_UV_NEW = createField("visit_uv_new", org.jooq.impl.SQLDataType.CLOB, this, "新增用户留存");

    /**
     * The column <code>mini_main.b2c_mp_weekly_retain.visit_uv</code>. 活跃用户留存
     */
    public final TableField<B2cMpWeeklyRetainRecord, String> VISIT_UV = createField("visit_uv", org.jooq.impl.SQLDataType.CLOB, this, "活跃用户留存");

    /**
     * The column <code>mini_main.b2c_mp_weekly_retain.add_time</code>. 添加时间
     */
    public final TableField<B2cMpWeeklyRetainRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "添加时间");

    /**
     * Create a <code>mini_main.b2c_mp_weekly_retain</code> table reference
     */
    public B2cMpWeeklyRetain() {
        this(DSL.name("b2c_mp_weekly_retain"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_mp_weekly_retain</code> table reference
     */
    public B2cMpWeeklyRetain(String alias) {
        this(DSL.name(alias), B2C_MP_WEEKLY_RETAIN);
    }

    /**
     * Create an aliased <code>mini_main.b2c_mp_weekly_retain</code> table reference
     */
    public B2cMpWeeklyRetain(Name alias) {
        this(alias, B2C_MP_WEEKLY_RETAIN);
    }

    private B2cMpWeeklyRetain(Name alias, Table<B2cMpWeeklyRetainRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cMpWeeklyRetain(Name alias, Table<B2cMpWeeklyRetainRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cMpWeeklyRetain(Table<O> child, ForeignKey<O, B2cMpWeeklyRetainRecord> key) {
        super(child, key, B2C_MP_WEEKLY_RETAIN);
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
    public B2cMpWeeklyRetain as(String alias) {
        return new B2cMpWeeklyRetain(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMpWeeklyRetain as(Name alias) {
        return new B2cMpWeeklyRetain(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cMpWeeklyRetain rename(String name) {
        return new B2cMpWeeklyRetain(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cMpWeeklyRetain rename(Name name) {
        return new B2cMpWeeklyRetain(name, null);
    }
}

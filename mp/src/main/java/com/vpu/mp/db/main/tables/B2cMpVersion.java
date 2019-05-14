/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.B2cMpVersionRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class B2cMpVersion extends TableImpl<B2cMpVersionRecord> {

    private static final long serialVersionUID = -421593275;

    /**
     * The reference instance of <code>mini_main.b2c_mp_version</code>
     */
    public static final B2cMpVersion B2C_MP_VERSION = new B2cMpVersion();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cMpVersionRecord> getRecordType() {
        return B2cMpVersionRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_mp_version.template_id</code>. 小程序模板Id
     */
    public final TableField<B2cMpVersionRecord, Integer> TEMPLATE_ID = createField("template_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "小程序模板Id");

    /**
     * The column <code>mini_main.b2c_mp_version.user_version</code>. 小程序模板版本号
     */
    public final TableField<B2cMpVersionRecord, String> USER_VERSION = createField("user_version", org.jooq.impl.SQLDataType.VARCHAR(191).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "小程序模板版本号");

    /**
     * The column <code>mini_main.b2c_mp_version.user_desc</code>. 小程序模板版本描述
     */
    public final TableField<B2cMpVersionRecord, String> USER_DESC = createField("user_desc", org.jooq.impl.SQLDataType.VARCHAR(191).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "小程序模板版本描述");

    /**
     * The column <code>mini_main.b2c_mp_version.create_time</code>. 小程序模板添加时间
     */
    public final TableField<B2cMpVersionRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "小程序模板添加时间");

    /**
     * The column <code>mini_main.b2c_mp_version.del_flag</code>. 删除标记
     */
    public final TableField<B2cMpVersionRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "删除标记");

    /**
     * The column <code>mini_main.b2c_mp_version.current_in_use</code>. 当前使用的版本
     */
    public final TableField<B2cMpVersionRecord, Byte> CURRENT_IN_USE = createField("current_in_use", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "当前使用的版本");

    /**
     * The column <code>mini_main.b2c_mp_version.source_miniprogram_appid</code>. 小程序开发appid
     */
    public final TableField<B2cMpVersionRecord, String> SOURCE_MINIPROGRAM_APPID = createField("source_miniprogram_appid", org.jooq.impl.SQLDataType.VARCHAR(191).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "小程序开发appid");

    /**
     * The column <code>mini_main.b2c_mp_version.source_miniprogram</code>. 小程序开发名称
     */
    public final TableField<B2cMpVersionRecord, String> SOURCE_MINIPROGRAM = createField("source_miniprogram", org.jooq.impl.SQLDataType.VARCHAR(191).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "小程序开发名称");

    /**
     * The column <code>mini_main.b2c_mp_version.developer</code>. 开发者
     */
    public final TableField<B2cMpVersionRecord, String> DEVELOPER = createField("developer", org.jooq.impl.SQLDataType.VARCHAR(191).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "开发者");

    /**
     * Create a <code>mini_main.b2c_mp_version</code> table reference
     */
    public B2cMpVersion() {
        this(DSL.name("b2c_mp_version"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_mp_version</code> table reference
     */
    public B2cMpVersion(String alias) {
        this(DSL.name(alias), B2C_MP_VERSION);
    }

    /**
     * Create an aliased <code>mini_main.b2c_mp_version</code> table reference
     */
    public B2cMpVersion(Name alias) {
        this(alias, B2C_MP_VERSION);
    }

    private B2cMpVersion(Name alias, Table<B2cMpVersionRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cMpVersion(Name alias, Table<B2cMpVersionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cMpVersion(Table<O> child, ForeignKey<O, B2cMpVersionRecord> key) {
        super(child, key, B2C_MP_VERSION);
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
        return Arrays.<Index>asList(Indexes.B2C_MP_VERSION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cMpVersionRecord> getPrimaryKey() {
        return Keys.KEY_B2C_MP_VERSION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cMpVersionRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cMpVersionRecord>>asList(Keys.KEY_B2C_MP_VERSION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMpVersion as(String alias) {
        return new B2cMpVersion(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMpVersion as(Name alias) {
        return new B2cMpVersion(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cMpVersion rename(String name) {
        return new B2cMpVersion(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cMpVersion rename(Name name) {
        return new B2cMpVersion(name, null);
    }
}

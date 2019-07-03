/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.DecorationTemplateRecord;

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
public class DecorationTemplate extends TableImpl<DecorationTemplateRecord> {

    private static final long serialVersionUID = 599409435;

    /**
     * The reference instance of <code>mini_main.b2c_decoration_template</code>
     */
    public static final DecorationTemplate DECORATION_TEMPLATE = new DecorationTemplate();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DecorationTemplateRecord> getRecordType() {
        return DecorationTemplateRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_decoration_template.page_id</code>.
     */
    public final TableField<DecorationTemplateRecord, Integer> PAGE_ID = createField("page_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_decoration_template.page_name</code>.
     */
    public final TableField<DecorationTemplateRecord, String> PAGE_NAME = createField("page_name", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_decoration_template.page_enabled</code>. 是否可用
     */
    public final TableField<DecorationTemplateRecord, Byte> PAGE_ENABLED = createField("page_enabled", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "是否可用");

    /**
     * The column <code>mini_main.b2c_decoration_template.page_content</code>. 页面内容，json格式存储
     */
    public final TableField<DecorationTemplateRecord, String> PAGE_CONTENT = createField("page_content", org.jooq.impl.SQLDataType.CLOB, this, "页面内容，json格式存储");

    /**
     * The column <code>mini_main.b2c_decoration_template.create_time</code>.
     */
    public final TableField<DecorationTemplateRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_main.b2c_decoration_template.page_img</code>. 装修页面封图
     */
    public final TableField<DecorationTemplateRecord, String> PAGE_IMG = createField("page_img", org.jooq.impl.SQLDataType.VARCHAR(1000).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "装修页面封图");

    /**
     * Create a <code>mini_main.b2c_decoration_template</code> table reference
     */
    public DecorationTemplate() {
        this(DSL.name("b2c_decoration_template"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_decoration_template</code> table reference
     */
    public DecorationTemplate(String alias) {
        this(DSL.name(alias), DECORATION_TEMPLATE);
    }

    /**
     * Create an aliased <code>mini_main.b2c_decoration_template</code> table reference
     */
    public DecorationTemplate(Name alias) {
        this(alias, DECORATION_TEMPLATE);
    }

    private DecorationTemplate(Name alias, Table<DecorationTemplateRecord> aliased) {
        this(alias, aliased, null);
    }

    private DecorationTemplate(Name alias, Table<DecorationTemplateRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> DecorationTemplate(Table<O> child, ForeignKey<O, DecorationTemplateRecord> key) {
        super(child, key, DECORATION_TEMPLATE);
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
        return Arrays.<Index>asList(Indexes.DECORATION_TEMPLATE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DecorationTemplateRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DECORATION_TEMPLATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DecorationTemplateRecord> getPrimaryKey() {
        return Keys.KEY_B2C_DECORATION_TEMPLATE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DecorationTemplateRecord>> getKeys() {
        return Arrays.<UniqueKey<DecorationTemplateRecord>>asList(Keys.KEY_B2C_DECORATION_TEMPLATE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DecorationTemplate as(String alias) {
        return new DecorationTemplate(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DecorationTemplate as(Name alias) {
        return new DecorationTemplate(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DecorationTemplate rename(String name) {
        return new DecorationTemplate(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DecorationTemplate rename(Name name) {
        return new DecorationTemplate(name, null);
    }
}

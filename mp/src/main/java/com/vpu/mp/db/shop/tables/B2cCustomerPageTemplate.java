/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cCustomerPageTemplateRecord;

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
public class B2cCustomerPageTemplate extends TableImpl<B2cCustomerPageTemplateRecord> {

    private static final long serialVersionUID = -948119473;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_customer_page_template</code>
     */
    public static final B2cCustomerPageTemplate B2C_CUSTOMER_PAGE_TEMPLATE = new B2cCustomerPageTemplate();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cCustomerPageTemplateRecord> getRecordType() {
        return B2cCustomerPageTemplateRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_customer_page_template.page_id</code>.
     */
    public final TableField<B2cCustomerPageTemplateRecord, UInteger> PAGE_ID = createField("page_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_customer_page_template.shop_id</code>. 店铺ID
     */
    public final TableField<B2cCustomerPageTemplateRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺ID");

    /**
     * The column <code>mini_shop_471752.b2c_customer_page_template.page_name</code>.
     */
    public final TableField<B2cCustomerPageTemplateRecord, String> PAGE_NAME = createField("page_name", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_customer_page_template.page_type</code>. 是否为首页1为首页，0非首页
     */
    public final TableField<B2cCustomerPageTemplateRecord, Byte> PAGE_TYPE = createField("page_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否为首页1为首页，0非首页");

    /**
     * The column <code>mini_shop_471752.b2c_customer_page_template.page_enabled</code>. 是否可用
     */
    public final TableField<B2cCustomerPageTemplateRecord, Byte> PAGE_ENABLED = createField("page_enabled", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "是否可用");

    /**
     * The column <code>mini_shop_471752.b2c_customer_page_template.page_tpl_type</code>. 模板类型:0自定义模板，1默认模板，2美女模板，3自定义首页
     */
    public final TableField<B2cCustomerPageTemplateRecord, Byte> PAGE_TPL_TYPE = createField("page_tpl_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "模板类型:0自定义模板，1默认模板，2美女模板，3自定义首页");

    /**
     * The column <code>mini_shop_471752.b2c_customer_page_template.page_content</code>. 页面内容，json格式存储
     */
    public final TableField<B2cCustomerPageTemplateRecord, String> PAGE_CONTENT = createField("page_content", org.jooq.impl.SQLDataType.CLOB, this, "页面内容，json格式存储");

    /**
     * The column <code>mini_shop_471752.b2c_customer_page_template.create_time</code>.
     */
    public final TableField<B2cCustomerPageTemplateRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>mini_shop_471752.b2c_customer_page_template</code> table reference
     */
    public B2cCustomerPageTemplate() {
        this(DSL.name("b2c_customer_page_template"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_customer_page_template</code> table reference
     */
    public B2cCustomerPageTemplate(String alias) {
        this(DSL.name(alias), B2C_CUSTOMER_PAGE_TEMPLATE);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_customer_page_template</code> table reference
     */
    public B2cCustomerPageTemplate(Name alias) {
        this(alias, B2C_CUSTOMER_PAGE_TEMPLATE);
    }

    private B2cCustomerPageTemplate(Name alias, Table<B2cCustomerPageTemplateRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cCustomerPageTemplate(Name alias, Table<B2cCustomerPageTemplateRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cCustomerPageTemplate(Table<O> child, ForeignKey<O, B2cCustomerPageTemplateRecord> key) {
        super(child, key, B2C_CUSTOMER_PAGE_TEMPLATE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return MiniShop_471752.MINI_SHOP_471752;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.B2C_CUSTOMER_PAGE_TEMPLATE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cCustomerPageTemplateRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_CUSTOMER_PAGE_TEMPLATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cCustomerPageTemplateRecord> getPrimaryKey() {
        return Keys.KEY_B2C_CUSTOMER_PAGE_TEMPLATE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cCustomerPageTemplateRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cCustomerPageTemplateRecord>>asList(Keys.KEY_B2C_CUSTOMER_PAGE_TEMPLATE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCustomerPageTemplate as(String alias) {
        return new B2cCustomerPageTemplate(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cCustomerPageTemplate as(Name alias) {
        return new B2cCustomerPageTemplate(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cCustomerPageTemplate rename(String name) {
        return new B2cCustomerPageTemplate(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cCustomerPageTemplate rename(Name name) {
        return new B2cCustomerPageTemplate(name, null);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.B2cShopRenewRecord;

import java.sql.Date;
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
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class B2cShopRenew extends TableImpl<B2cShopRenewRecord> {

    private static final long serialVersionUID = 1854746364;

    /**
     * The reference instance of <code>mini_main.b2c_shop_renew</code>
     */
    public static final B2cShopRenew B2C_SHOP_RENEW = new B2cShopRenew();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cShopRenewRecord> getRecordType() {
        return B2cShopRenewRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_shop_renew.id</code>.
     */
    public final TableField<B2cShopRenewRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_shop_renew.shop_id</code>. 店铺ID
     */
    public final TableField<B2cShopRenewRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "店铺ID");

    /**
     * The column <code>mini_main.b2c_shop_renew.sys_id</code>.
     */
    public final TableField<B2cShopRenewRecord, Integer> SYS_ID = createField("sys_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_shop_renew.mobile</code>.
     */
    public final TableField<B2cShopRenewRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR(32).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_shop_renew.renew_money</code>. 店铺续费金额
     */
    public final TableField<B2cShopRenewRecord, String> RENEW_MONEY = createField("renew_money", org.jooq.impl.SQLDataType.VARCHAR(50).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "店铺续费金额");

    /**
     * The column <code>mini_main.b2c_shop_renew.renew_date</code>. 店铺续费日期
     */
    public final TableField<B2cShopRenewRecord, Date> RENEW_DATE = createField("renew_date", org.jooq.impl.SQLDataType.DATE, this, "店铺续费日期");

    /**
     * The column <code>mini_main.b2c_shop_renew.expire_time</code>. 到期时间
     */
    public final TableField<B2cShopRenewRecord, Date> EXPIRE_TIME = createField("expire_time", org.jooq.impl.SQLDataType.DATE, this, "到期时间");

    /**
     * The column <code>mini_main.b2c_shop_renew.operator</code>. 操作员ID,主账号是0，子账号ID
     */
    public final TableField<B2cShopRenewRecord, Integer> OPERATOR = createField("operator", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "操作员ID,主账号是0，子账号ID");

    /**
     * The column <code>mini_main.b2c_shop_renew.renew_desc</code>. 说明
     */
    public final TableField<B2cShopRenewRecord, String> RENEW_DESC = createField("renew_desc", org.jooq.impl.SQLDataType.VARCHAR(255).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "说明");

    /**
     * Create a <code>mini_main.b2c_shop_renew</code> table reference
     */
    public B2cShopRenew() {
        this(DSL.name("b2c_shop_renew"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_renew</code> table reference
     */
    public B2cShopRenew(String alias) {
        this(DSL.name(alias), B2C_SHOP_RENEW);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_renew</code> table reference
     */
    public B2cShopRenew(Name alias) {
        this(alias, B2C_SHOP_RENEW);
    }

    private B2cShopRenew(Name alias, Table<B2cShopRenewRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cShopRenew(Name alias, Table<B2cShopRenewRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cShopRenew(Table<O> child, ForeignKey<O, B2cShopRenewRecord> key) {
        super(child, key, B2C_SHOP_RENEW);
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
        return Arrays.<Index>asList(Indexes.B2C_SHOP_RENEW_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cShopRenewRecord, Integer> getIdentity() {
        return Keys.IDENTITY_B2C_SHOP_RENEW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cShopRenewRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SHOP_RENEW_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cShopRenewRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cShopRenewRecord>>asList(Keys.KEY_B2C_SHOP_RENEW_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopRenew as(String alias) {
        return new B2cShopRenew(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopRenew as(Name alias) {
        return new B2cShopRenew(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cShopRenew rename(String name) {
        return new B2cShopRenew(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cShopRenew rename(Name name) {
        return new B2cShopRenew(name, null);
    }
}

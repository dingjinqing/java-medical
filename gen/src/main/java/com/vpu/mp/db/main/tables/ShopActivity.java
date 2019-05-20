/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.ShopActivityRecord;

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
public class ShopActivity extends TableImpl<ShopActivityRecord> {

    private static final long serialVersionUID = -778984999;

    /**
     * The reference instance of <code>mini_main.b2c_shop_activity</code>
     */
    public static final ShopActivity SHOP_ACTIVITY = new ShopActivity();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ShopActivityRecord> getRecordType() {
        return ShopActivityRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_shop_activity.id</code>.
     */
    public final TableField<ShopActivityRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_shop_activity.ref_date</code>. 2018-09-18
     */
    public final TableField<ShopActivityRecord, Date> REF_DATE = createField("ref_date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "2018-09-18");

    /**
     * The column <code>mini_main.b2c_shop_activity.type</code>. 1，7，30
     */
    public final TableField<ShopActivityRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "1，7，30");

    /**
     * The column <code>mini_main.b2c_shop_activity.activity_type</code>. 活动类型
     */
    public final TableField<ShopActivityRecord, Byte> ACTIVITY_TYPE = createField("activity_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "活动类型");

    /**
     * The column <code>mini_main.b2c_shop_activity.shop_num</code>. 有权限的店铺数
     */
    public final TableField<ShopActivityRecord, Integer> SHOP_NUM = createField("shop_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "有权限的店铺数");

    /**
     * The column <code>mini_main.b2c_shop_activity.use</code>. 有权限已使用的店铺数
     */
    public final TableField<ShopActivityRecord, Integer> USE = createField("use", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "有权限已使用的店铺数");

    /**
     * The column <code>mini_main.b2c_shop_activity.nouse</code>. 有权限未使用的店铺数
     */
    public final TableField<ShopActivityRecord, Integer> NOUSE = createField("nouse", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "有权限未使用的店铺数");

    /**
     * The column <code>mini_main.b2c_shop_activity.ingoing</code>. 有权限正在使用的店铺数
     */
    public final TableField<ShopActivityRecord, Integer> INGOING = createField("ingoing", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "有权限正在使用的店铺数");

    /**
     * The column <code>mini_main.b2c_shop_activity.num</code>. 进行中的活动
     */
    public final TableField<ShopActivityRecord, Integer> NUM = createField("num", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "进行中的活动");

    /**
     * Create a <code>mini_main.b2c_shop_activity</code> table reference
     */
    public ShopActivity() {
        this(DSL.name("b2c_shop_activity"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_activity</code> table reference
     */
    public ShopActivity(String alias) {
        this(DSL.name(alias), SHOP_ACTIVITY);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_activity</code> table reference
     */
    public ShopActivity(Name alias) {
        this(alias, SHOP_ACTIVITY);
    }

    private ShopActivity(Name alias, Table<ShopActivityRecord> aliased) {
        this(alias, aliased, null);
    }

    private ShopActivity(Name alias, Table<ShopActivityRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> ShopActivity(Table<O> child, ForeignKey<O, ShopActivityRecord> key) {
        super(child, key, SHOP_ACTIVITY);
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
        return Arrays.<Index>asList(Indexes.SHOP_ACTIVITY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ShopActivityRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SHOP_ACTIVITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ShopActivityRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SHOP_ACTIVITY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ShopActivityRecord>> getKeys() {
        return Arrays.<UniqueKey<ShopActivityRecord>>asList(Keys.KEY_B2C_SHOP_ACTIVITY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopActivity as(String alias) {
        return new ShopActivity(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopActivity as(Name alias) {
        return new ShopActivity(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ShopActivity rename(String name) {
        return new ShopActivity(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ShopActivity rename(Name name) {
        return new ShopActivity(name, null);
    }
}

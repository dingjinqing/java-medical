/*
 * This file is generated by jOOQ.
 */
package com.vpu.jmd.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.ShopTaokeRecord;

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
public class ShopTaoke extends TableImpl<ShopTaokeRecord> {

    private static final long serialVersionUID = -1078185328;

    /**
     * The reference instance of <code>mini_main.b2c_shop_taoke</code>
     */
    public static final ShopTaoke SHOP_TAOKE = new ShopTaoke();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ShopTaokeRecord> getRecordType() {
        return ShopTaokeRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_shop_taoke.id</code>.
     */
    public final TableField<ShopTaokeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_shop_taoke.shop_id</code>. 店铺Id
     */
    public final TableField<ShopTaokeRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺Id");

    /**
     * The column <code>mini_main.b2c_shop_taoke.app_type</code>. 对接类型：1：京东，2：淘宝
     */
    public final TableField<ShopTaokeRecord, Byte> APP_TYPE = createField("app_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "对接类型：1：京东，2：淘宝");

    /**
     * The column <code>mini_main.b2c_shop_taoke.app_key</code>. appKey
     */
    public final TableField<ShopTaokeRecord, String> APP_KEY = createField("app_key", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "appKey");

    /**
     * The column <code>mini_main.b2c_shop_taoke.app_secret</code>. appSecret
     */
    public final TableField<ShopTaokeRecord, String> APP_SECRET = createField("app_secret", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "appSecret");

    /**
     * The column <code>mini_main.b2c_shop_taoke.tk_shop_id</code>. 淘客店铺Id
     */
    public final TableField<ShopTaokeRecord, String> TK_SHOP_ID = createField("tk_shop_id", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "淘客店铺Id");

    /**
     * The column <code>mini_main.b2c_shop_taoke.tk_rebate_ratio</code>. 返佣比例
     */
    public final TableField<ShopTaokeRecord, Integer> TK_REBATE_RATIO = createField("tk_rebate_ratio", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("100", org.jooq.impl.SQLDataType.INTEGER)), this, "返佣比例");

    /**
     * Create a <code>mini_main.b2c_shop_taoke</code> table reference
     */
    public ShopTaoke() {
        this(DSL.name("b2c_shop_taoke"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_taoke</code> table reference
     */
    public ShopTaoke(String alias) {
        this(DSL.name(alias), SHOP_TAOKE);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_taoke</code> table reference
     */
    public ShopTaoke(Name alias) {
        this(alias, SHOP_TAOKE);
    }

    private ShopTaoke(Name alias, Table<ShopTaokeRecord> aliased) {
        this(alias, aliased, null);
    }

    private ShopTaoke(Name alias, Table<ShopTaokeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> ShopTaoke(Table<O> child, ForeignKey<O, ShopTaokeRecord> key) {
        super(child, key, SHOP_TAOKE);
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
        return Arrays.<Index>asList(Indexes.SHOP_TAOKE_APP_TYPE, Indexes.SHOP_TAOKE_PRIMARY, Indexes.SHOP_TAOKE_SHOP_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ShopTaokeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SHOP_TAOKE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ShopTaokeRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SHOP_TAOKE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ShopTaokeRecord>> getKeys() {
        return Arrays.<UniqueKey<ShopTaokeRecord>>asList(Keys.KEY_B2C_SHOP_TAOKE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopTaoke as(String alias) {
        return new ShopTaoke(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopTaoke as(Name alias) {
        return new ShopTaoke(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ShopTaoke rename(String name) {
        return new ShopTaoke(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ShopTaoke rename(Name name) {
        return new ShopTaoke(name, null);
    }
}

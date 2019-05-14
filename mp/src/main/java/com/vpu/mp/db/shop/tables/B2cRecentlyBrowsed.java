/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cRecentlyBrowsedRecord;

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
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class B2cRecentlyBrowsed extends TableImpl<B2cRecentlyBrowsedRecord> {

    private static final long serialVersionUID = 758798767;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_recently_browsed</code>
     */
    public static final B2cRecentlyBrowsed B2C_RECENTLY_BROWSED = new B2cRecentlyBrowsed();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cRecentlyBrowsedRecord> getRecordType() {
        return B2cRecentlyBrowsedRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_recently_browsed.id</code>.
     */
    public final TableField<B2cRecentlyBrowsedRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_recently_browsed.shop_id</code>. 店铺ID
     */
    public final TableField<B2cRecentlyBrowsedRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺ID");

    /**
     * The column <code>mini_shop_471752.b2c_recently_browsed.user_id</code>. 用户ID
     */
    public final TableField<B2cRecentlyBrowsedRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "用户ID");

    /**
     * The column <code>mini_shop_471752.b2c_recently_browsed.user_cid</code>.
     */
    public final TableField<B2cRecentlyBrowsedRecord, String> USER_CID = createField("user_cid", org.jooq.impl.SQLDataType.VARCHAR(64), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_recently_browsed.goods_id</code>. 商品ID
     */
    public final TableField<B2cRecentlyBrowsedRecord, Integer> GOODS_ID = createField("goods_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "商品ID");

    /**
     * The column <code>mini_shop_471752.b2c_recently_browsed.browse_number</code>. 浏览次数
     */
    public final TableField<B2cRecentlyBrowsedRecord, Integer> BROWSE_NUMBER = createField("browse_number", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "浏览次数");

    /**
     * The column <code>mini_shop_471752.b2c_recently_browsed.up_time</code>.
     */
    public final TableField<B2cRecentlyBrowsedRecord, Timestamp> UP_TIME = createField("up_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>mini_shop_471752.b2c_recently_browsed</code> table reference
     */
    public B2cRecentlyBrowsed() {
        this(DSL.name("b2c_recently_browsed"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_recently_browsed</code> table reference
     */
    public B2cRecentlyBrowsed(String alias) {
        this(DSL.name(alias), B2C_RECENTLY_BROWSED);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_recently_browsed</code> table reference
     */
    public B2cRecentlyBrowsed(Name alias) {
        this(alias, B2C_RECENTLY_BROWSED);
    }

    private B2cRecentlyBrowsed(Name alias, Table<B2cRecentlyBrowsedRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cRecentlyBrowsed(Name alias, Table<B2cRecentlyBrowsedRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cRecentlyBrowsed(Table<O> child, ForeignKey<O, B2cRecentlyBrowsedRecord> key) {
        super(child, key, B2C_RECENTLY_BROWSED);
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
        return Arrays.<Index>asList(Indexes.B2C_RECENTLY_BROWSED_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cRecentlyBrowsedRecord, Integer> getIdentity() {
        return Keys.IDENTITY_B2C_RECENTLY_BROWSED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cRecentlyBrowsedRecord> getPrimaryKey() {
        return Keys.KEY_B2C_RECENTLY_BROWSED_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cRecentlyBrowsedRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cRecentlyBrowsedRecord>>asList(Keys.KEY_B2C_RECENTLY_BROWSED_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cRecentlyBrowsed as(String alias) {
        return new B2cRecentlyBrowsed(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cRecentlyBrowsed as(Name alias) {
        return new B2cRecentlyBrowsed(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cRecentlyBrowsed rename(String name) {
        return new B2cRecentlyBrowsed(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cRecentlyBrowsed rename(Name name) {
        return new B2cRecentlyBrowsed(name, null);
    }
}

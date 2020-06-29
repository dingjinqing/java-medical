/*
 * This file is generated by jOOQ.
 */
package com.vpu.jmd.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.ShopTagRecord;

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
public class ShopTag extends TableImpl<ShopTagRecord> {

    private static final long serialVersionUID = -804967622;

    /**
     * The reference instance of <code>mini_main.b2c_shop_tag</code>
     */
    public static final ShopTag SHOP_TAG = new ShopTag();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ShopTagRecord> getRecordType() {
        return ShopTagRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_shop_tag.id</code>.
     */
    public final TableField<ShopTagRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_shop_tag.tag_id</code>.
     */
    public final TableField<ShopTagRecord, Integer> TAG_ID = createField("tag_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_shop_tag.shop_id</code>.
     */
    public final TableField<ShopTagRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>mini_main.b2c_shop_tag</code> table reference
     */
    public ShopTag() {
        this(DSL.name("b2c_shop_tag"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_tag</code> table reference
     */
    public ShopTag(String alias) {
        this(DSL.name(alias), SHOP_TAG);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_tag</code> table reference
     */
    public ShopTag(Name alias) {
        this(alias, SHOP_TAG);
    }

    private ShopTag(Name alias, Table<ShopTagRecord> aliased) {
        this(alias, aliased, null);
    }

    private ShopTag(Name alias, Table<ShopTagRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> ShopTag(Table<O> child, ForeignKey<O, ShopTagRecord> key) {
        super(child, key, SHOP_TAG);
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
        return Arrays.<Index>asList(Indexes.SHOP_TAG_PRIMARY, Indexes.SHOP_TAG_SHOP_ID, Indexes.SHOP_TAG_TAG_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ShopTagRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SHOP_TAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ShopTagRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SHOP_TAG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ShopTagRecord>> getKeys() {
        return Arrays.<UniqueKey<ShopTagRecord>>asList(Keys.KEY_B2C_SHOP_TAG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopTag as(String alias) {
        return new ShopTag(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopTag as(Name alias) {
        return new ShopTag(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ShopTag rename(String name) {
        return new ShopTag(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ShopTag rename(Name name) {
        return new ShopTag(name, null);
    }
}

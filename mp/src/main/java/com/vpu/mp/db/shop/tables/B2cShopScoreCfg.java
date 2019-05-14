/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cShopScoreCfgRecord;

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
public class B2cShopScoreCfg extends TableImpl<B2cShopScoreCfgRecord> {

    private static final long serialVersionUID = 469140361;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_shop_score_cfg</code>
     */
    public static final B2cShopScoreCfg B2C_SHOP_SCORE_CFG = new B2cShopScoreCfg();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cShopScoreCfgRecord> getRecordType() {
        return B2cShopScoreCfgRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_shop_score_cfg.id</code>.
     */
    public final TableField<B2cShopScoreCfgRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_shop_score_cfg.growth</code>. 0:关闭，1:开启
     */
    public final TableField<B2cShopScoreCfgRecord, Integer> GROWTH = createField("growth", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0:关闭，1:开启");

    /**
     * The column <code>mini_shop_471752.b2c_shop_score_cfg.score</code>. 0:关闭，1:开启
     */
    public final TableField<B2cShopScoreCfgRecord, Integer> SCORE = createField("score", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0:关闭，1:开启");

    /**
     * The column <code>mini_shop_471752.b2c_shop_score_cfg.comment</code>. comment:0:关闭评论，1：不用审批，2：先发后审，3：先审后发
     */
    public final TableField<B2cShopScoreCfgRecord, Integer> COMMENT = createField("comment", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "comment:0:关闭评论，1：不用审批，2：先发后审，3：先审后发");

    /**
     * The column <code>mini_shop_471752.b2c_shop_score_cfg.in_time</code>.
     */
    public final TableField<B2cShopScoreCfgRecord, Timestamp> IN_TIME = createField("in_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_shop_score_cfg.up_time</code>.
     */
    public final TableField<B2cShopScoreCfgRecord, Timestamp> UP_TIME = createField("up_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_shop_score_cfg.sign</code>. 0:关闭，1:开启
     */
    public final TableField<B2cShopScoreCfgRecord, Integer> SIGN = createField("sign", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0:关闭，1:开启");

    /**
     * The column <code>mini_shop_471752.b2c_shop_score_cfg.sys_id</code>. 商家ID
     */
    public final TableField<B2cShopScoreCfgRecord, Integer> SYS_ID = createField("sys_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "商家ID");

    /**
     * Create a <code>mini_shop_471752.b2c_shop_score_cfg</code> table reference
     */
    public B2cShopScoreCfg() {
        this(DSL.name("b2c_shop_score_cfg"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_shop_score_cfg</code> table reference
     */
    public B2cShopScoreCfg(String alias) {
        this(DSL.name(alias), B2C_SHOP_SCORE_CFG);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_shop_score_cfg</code> table reference
     */
    public B2cShopScoreCfg(Name alias) {
        this(alias, B2C_SHOP_SCORE_CFG);
    }

    private B2cShopScoreCfg(Name alias, Table<B2cShopScoreCfgRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cShopScoreCfg(Name alias, Table<B2cShopScoreCfgRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cShopScoreCfg(Table<O> child, ForeignKey<O, B2cShopScoreCfgRecord> key) {
        super(child, key, B2C_SHOP_SCORE_CFG);
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
        return Arrays.<Index>asList(Indexes.B2C_SHOP_SCORE_CFG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cShopScoreCfgRecord, Integer> getIdentity() {
        return Keys.IDENTITY_B2C_SHOP_SCORE_CFG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cShopScoreCfgRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SHOP_SCORE_CFG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cShopScoreCfgRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cShopScoreCfgRecord>>asList(Keys.KEY_B2C_SHOP_SCORE_CFG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopScoreCfg as(String alias) {
        return new B2cShopScoreCfg(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cShopScoreCfg as(Name alias) {
        return new B2cShopScoreCfg(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cShopScoreCfg rename(String name) {
        return new B2cShopScoreCfg(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cShopScoreCfg rename(Name name) {
        return new B2cShopScoreCfg(name, null);
    }
}

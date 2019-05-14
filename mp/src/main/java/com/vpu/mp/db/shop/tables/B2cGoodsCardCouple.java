/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cGoodsCardCoupleRecord;

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
public class B2cGoodsCardCouple extends TableImpl<B2cGoodsCardCoupleRecord> {

    private static final long serialVersionUID = -1200961647;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_goods_card_couple</code>
     */
    public static final B2cGoodsCardCouple B2C_GOODS_CARD_COUPLE = new B2cGoodsCardCouple();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cGoodsCardCoupleRecord> getRecordType() {
        return B2cGoodsCardCoupleRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_goods_card_couple.id</code>. 会员卡专属商品关联ID
     */
    public final TableField<B2cGoodsCardCoupleRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "会员卡专属商品关联ID");

    /**
     * The column <code>mini_shop_471752.b2c_goods_card_couple.card_id</code>. 会员卡ID
     */
    public final TableField<B2cGoodsCardCoupleRecord, String> CARD_ID = createField("card_id", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "会员卡ID");

    /**
     * The column <code>mini_shop_471752.b2c_goods_card_couple.gcta_id</code>. 商品或类型ID
     */
    public final TableField<B2cGoodsCardCoupleRecord, Integer> GCTA_ID = createField("gcta_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "商品或类型ID");

    /**
     * The column <code>mini_shop_471752.b2c_goods_card_couple.type</code>. 标签关联类型： 1：关联商品 2：关联商家分类 3：关联平台分类
     */
    public final TableField<B2cGoodsCardCoupleRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "标签关联类型： 1：关联商品 2：关联商家分类 3：关联平台分类");

    /**
     * Create a <code>mini_shop_471752.b2c_goods_card_couple</code> table reference
     */
    public B2cGoodsCardCouple() {
        this(DSL.name("b2c_goods_card_couple"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_goods_card_couple</code> table reference
     */
    public B2cGoodsCardCouple(String alias) {
        this(DSL.name(alias), B2C_GOODS_CARD_COUPLE);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_goods_card_couple</code> table reference
     */
    public B2cGoodsCardCouple(Name alias) {
        this(alias, B2C_GOODS_CARD_COUPLE);
    }

    private B2cGoodsCardCouple(Name alias, Table<B2cGoodsCardCoupleRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cGoodsCardCouple(Name alias, Table<B2cGoodsCardCoupleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cGoodsCardCouple(Table<O> child, ForeignKey<O, B2cGoodsCardCoupleRecord> key) {
        super(child, key, B2C_GOODS_CARD_COUPLE);
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
        return Arrays.<Index>asList(Indexes.B2C_GOODS_CARD_COUPLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cGoodsCardCoupleRecord, Integer> getIdentity() {
        return Keys.IDENTITY_B2C_GOODS_CARD_COUPLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cGoodsCardCoupleRecord> getPrimaryKey() {
        return Keys.KEY_B2C_GOODS_CARD_COUPLE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cGoodsCardCoupleRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cGoodsCardCoupleRecord>>asList(Keys.KEY_B2C_GOODS_CARD_COUPLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCardCouple as(String alias) {
        return new B2cGoodsCardCouple(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsCardCouple as(Name alias) {
        return new B2cGoodsCardCouple(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cGoodsCardCouple rename(String name) {
        return new B2cGoodsCardCouple(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cGoodsCardCouple rename(Name name) {
        return new B2cGoodsCardCouple(name, null);
    }
}

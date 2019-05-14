/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cImportGoodsRecord;

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
public class B2cImportGoods extends TableImpl<B2cImportGoodsRecord> {

    private static final long serialVersionUID = 2027169006;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_import_goods</code>
     */
    public static final B2cImportGoods B2C_IMPORT_GOODS = new B2cImportGoods();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cImportGoodsRecord> getRecordType() {
        return B2cImportGoodsRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_import_goods.id</code>.
     */
    public final TableField<B2cImportGoodsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_import_goods.imp_batch_no</code>. 导入批次号
     */
    public final TableField<B2cImportGoodsRecord, String> IMP_BATCH_NO = createField("imp_batch_no", org.jooq.impl.SQLDataType.VARCHAR(255), this, "导入批次号");

    /**
     * The column <code>mini_shop_471752.b2c_import_goods.site_type</code>. 导入网站类型:taobao,tmall
     */
    public final TableField<B2cImportGoodsRecord, String> SITE_TYPE = createField("site_type", org.jooq.impl.SQLDataType.VARCHAR(255), this, "导入网站类型:taobao,tmall");

    /**
     * The column <code>mini_shop_471752.b2c_import_goods.imp_url</code>. 导入URL
     */
    public final TableField<B2cImportGoodsRecord, String> IMP_URL = createField("imp_url", org.jooq.impl.SQLDataType.VARCHAR(255), this, "导入URL");

    /**
     * The column <code>mini_shop_471752.b2c_import_goods.item_id</code>. 源商品ID
     */
    public final TableField<B2cImportGoodsRecord, String> ITEM_ID = createField("item_id", org.jooq.impl.SQLDataType.VARCHAR(255), this, "源商品ID");

    /**
     * The column <code>mini_shop_471752.b2c_import_goods.goods_id</code>. 导入后商品ID
     */
    public final TableField<B2cImportGoodsRecord, Integer> GOODS_ID = createField("goods_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "导入后商品ID");

    /**
     * The column <code>mini_shop_471752.b2c_import_goods.goods_name</code>. 商品名称
     */
    public final TableField<B2cImportGoodsRecord, String> GOODS_NAME = createField("goods_name", org.jooq.impl.SQLDataType.VARCHAR(255), this, "商品名称");

    /**
     * The column <code>mini_shop_471752.b2c_import_goods.imp_time</code>.
     */
    public final TableField<B2cImportGoodsRecord, Timestamp> IMP_TIME = createField("imp_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>mini_shop_471752.b2c_import_goods</code> table reference
     */
    public B2cImportGoods() {
        this(DSL.name("b2c_import_goods"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_import_goods</code> table reference
     */
    public B2cImportGoods(String alias) {
        this(DSL.name(alias), B2C_IMPORT_GOODS);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_import_goods</code> table reference
     */
    public B2cImportGoods(Name alias) {
        this(alias, B2C_IMPORT_GOODS);
    }

    private B2cImportGoods(Name alias, Table<B2cImportGoodsRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cImportGoods(Name alias, Table<B2cImportGoodsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cImportGoods(Table<O> child, ForeignKey<O, B2cImportGoodsRecord> key) {
        super(child, key, B2C_IMPORT_GOODS);
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
        return Arrays.<Index>asList(Indexes.B2C_IMPORT_GOODS_GOODS_ID, Indexes.B2C_IMPORT_GOODS_IMP_BATCH_NO, Indexes.B2C_IMPORT_GOODS_IMP_TIME, Indexes.B2C_IMPORT_GOODS_ITEM_ID, Indexes.B2C_IMPORT_GOODS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cImportGoodsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_B2C_IMPORT_GOODS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cImportGoodsRecord> getPrimaryKey() {
        return Keys.KEY_B2C_IMPORT_GOODS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cImportGoodsRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cImportGoodsRecord>>asList(Keys.KEY_B2C_IMPORT_GOODS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cImportGoods as(String alias) {
        return new B2cImportGoods(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cImportGoods as(Name alias) {
        return new B2cImportGoods(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cImportGoods rename(String name) {
        return new B2cImportGoods(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cImportGoods rename(Name name) {
        return new B2cImportGoods(name, null);
    }
}

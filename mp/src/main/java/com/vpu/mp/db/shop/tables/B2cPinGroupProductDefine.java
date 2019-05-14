/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cPinGroupProductDefineRecord;

import java.math.BigDecimal;
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
public class B2cPinGroupProductDefine extends TableImpl<B2cPinGroupProductDefineRecord> {

    private static final long serialVersionUID = 996209243;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_pin_group_product_define</code>
     */
    public static final B2cPinGroupProductDefine B2C_PIN_GROUP_PRODUCT_DEFINE = new B2cPinGroupProductDefine();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cPinGroupProductDefineRecord> getRecordType() {
        return B2cPinGroupProductDefineRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_pin_group_product_define.id</code>.
     */
    public final TableField<B2cPinGroupProductDefineRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_pin_group_product_define.pin_activity_id</code>. 拼团定义ID
     */
    public final TableField<B2cPinGroupProductDefineRecord, Integer> PIN_ACTIVITY_ID = createField("pin_activity_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "拼团定义ID");

    /**
     * The column <code>mini_shop_471752.b2c_pin_group_product_define.product_id</code>. 商品规格ID
     */
    public final TableField<B2cPinGroupProductDefineRecord, Integer> PRODUCT_ID = createField("product_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "商品规格ID");

    /**
     * The column <code>mini_shop_471752.b2c_pin_group_product_define.pin_group_price</code>. 拼团价
     */
    public final TableField<B2cPinGroupProductDefineRecord, BigDecimal> PIN_GROUP_PRICE = createField("pin_group_price", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "拼团价");

    /**
     * The column <code>mini_shop_471752.b2c_pin_group_product_define.stock</code>. 库存
     */
    public final TableField<B2cPinGroupProductDefineRecord, Short> STOCK = createField("stock", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "库存");

    /**
     * The column <code>mini_shop_471752.b2c_pin_group_product_define.sale_num</code>. 销量
     */
    public final TableField<B2cPinGroupProductDefineRecord, Short> SALE_NUM = createField("sale_num", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "销量");

    /**
     * The column <code>mini_shop_471752.b2c_pin_group_product_define.grouper_price</code>. 团长优惠价
     */
    public final TableField<B2cPinGroupProductDefineRecord, BigDecimal> GROUPER_PRICE = createField("grouper_price", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "团长优惠价");

    /**
     * Create a <code>mini_shop_471752.b2c_pin_group_product_define</code> table reference
     */
    public B2cPinGroupProductDefine() {
        this(DSL.name("b2c_pin_group_product_define"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_pin_group_product_define</code> table reference
     */
    public B2cPinGroupProductDefine(String alias) {
        this(DSL.name(alias), B2C_PIN_GROUP_PRODUCT_DEFINE);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_pin_group_product_define</code> table reference
     */
    public B2cPinGroupProductDefine(Name alias) {
        this(alias, B2C_PIN_GROUP_PRODUCT_DEFINE);
    }

    private B2cPinGroupProductDefine(Name alias, Table<B2cPinGroupProductDefineRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cPinGroupProductDefine(Name alias, Table<B2cPinGroupProductDefineRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cPinGroupProductDefine(Table<O> child, ForeignKey<O, B2cPinGroupProductDefineRecord> key) {
        super(child, key, B2C_PIN_GROUP_PRODUCT_DEFINE);
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
        return Arrays.<Index>asList(Indexes.B2C_PIN_GROUP_PRODUCT_DEFINE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cPinGroupProductDefineRecord, Integer> getIdentity() {
        return Keys.IDENTITY_B2C_PIN_GROUP_PRODUCT_DEFINE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cPinGroupProductDefineRecord> getPrimaryKey() {
        return Keys.KEY_B2C_PIN_GROUP_PRODUCT_DEFINE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cPinGroupProductDefineRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cPinGroupProductDefineRecord>>asList(Keys.KEY_B2C_PIN_GROUP_PRODUCT_DEFINE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cPinGroupProductDefine as(String alias) {
        return new B2cPinGroupProductDefine(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cPinGroupProductDefine as(Name alias) {
        return new B2cPinGroupProductDefine(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cPinGroupProductDefine rename(String name) {
        return new B2cPinGroupProductDefine(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cPinGroupProductDefine rename(Name name) {
        return new B2cPinGroupProductDefine(name, null);
    }
}

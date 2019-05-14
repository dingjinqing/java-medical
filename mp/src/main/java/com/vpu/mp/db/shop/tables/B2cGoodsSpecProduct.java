/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cGoodsSpecProductRecord;

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
public class B2cGoodsSpecProduct extends TableImpl<B2cGoodsSpecProductRecord> {

    private static final long serialVersionUID = 295602365;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_goods_spec_product</code>
     */
    public static final B2cGoodsSpecProduct B2C_GOODS_SPEC_PRODUCT = new B2cGoodsSpecProduct();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cGoodsSpecProductRecord> getRecordType() {
        return B2cGoodsSpecProductRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.prd_id</code>.
     */
    public final TableField<B2cGoodsSpecProductRecord, UInteger> PRD_ID = createField("prd_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.shop_id</code>.
     */
    public final TableField<B2cGoodsSpecProductRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.goods_id</code>.
     */
    public final TableField<B2cGoodsSpecProductRecord, UInteger> GOODS_ID = createField("goods_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.prd_price</code>.
     */
    public final TableField<B2cGoodsSpecProductRecord, BigDecimal> PRD_PRICE = createField("prd_price", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.prd_market_price</code>. 市场价
     */
    public final TableField<B2cGoodsSpecProductRecord, BigDecimal> PRD_MARKET_PRICE = createField("prd_market_price", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "市场价");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.prd_cost_price</code>. 成本价
     */
    public final TableField<B2cGoodsSpecProductRecord, BigDecimal> PRD_COST_PRICE = createField("prd_cost_price", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "成本价");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.prd_number</code>. 当前规格组合产品库存
     */
    public final TableField<B2cGoodsSpecProductRecord, Integer> PRD_NUMBER = createField("prd_number", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "当前规格组合产品库存");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.prd_sn</code>. 商家编码
     */
    public final TableField<B2cGoodsSpecProductRecord, String> PRD_SN = createField("prd_sn", org.jooq.impl.SQLDataType.VARCHAR(65).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "商家编码");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.prd_codes</code>. 商品条码
     */
    public final TableField<B2cGoodsSpecProductRecord, String> PRD_CODES = createField("prd_codes", org.jooq.impl.SQLDataType.VARCHAR(500).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "商品条码");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.prd_specs</code>.
     */
    public final TableField<B2cGoodsSpecProductRecord, String> PRD_SPECS = createField("prd_specs", org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.prd_desc</code>. 规格描述，格式例子：颜色:红色 尺码:S
     */
    public final TableField<B2cGoodsSpecProductRecord, String> PRD_DESC = createField("prd_desc", org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "规格描述，格式例子：颜色:红色 尺码:S");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.del_flag</code>.
     */
    public final TableField<B2cGoodsSpecProductRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.self_flag</code>. 1:商家自己添加商品，其他没用
     */
    public final TableField<B2cGoodsSpecProductRecord, Byte> SELF_FLAG = createField("self_flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "1:商家自己添加商品，其他没用");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.low_shop_price</code>. 最低售出价格
     */
    public final TableField<B2cGoodsSpecProductRecord, String> LOW_SHOP_PRICE = createField("low_shop_price", org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.VARCHAR)), this, "最低售出价格");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.prd_img</code>. 图片地址
     */
    public final TableField<B2cGoodsSpecProductRecord, String> PRD_IMG = createField("prd_img", org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "图片地址");

    /**
     * The column <code>mini_shop_471752.b2c_goods_spec_product.price_flag</code>. 0:商家未改价，1：商家改价，2：批量改价，3：毛利改价
     */
    public final TableField<B2cGoodsSpecProductRecord, Byte> PRICE_FLAG = createField("price_flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:商家未改价，1：商家改价，2：批量改价，3：毛利改价");

    /**
     * Create a <code>mini_shop_471752.b2c_goods_spec_product</code> table reference
     */
    public B2cGoodsSpecProduct() {
        this(DSL.name("b2c_goods_spec_product"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_goods_spec_product</code> table reference
     */
    public B2cGoodsSpecProduct(String alias) {
        this(DSL.name(alias), B2C_GOODS_SPEC_PRODUCT);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_goods_spec_product</code> table reference
     */
    public B2cGoodsSpecProduct(Name alias) {
        this(alias, B2C_GOODS_SPEC_PRODUCT);
    }

    private B2cGoodsSpecProduct(Name alias, Table<B2cGoodsSpecProductRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cGoodsSpecProduct(Name alias, Table<B2cGoodsSpecProductRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cGoodsSpecProduct(Table<O> child, ForeignKey<O, B2cGoodsSpecProductRecord> key) {
        super(child, key, B2C_GOODS_SPEC_PRODUCT);
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
        return Arrays.<Index>asList(Indexes.B2C_GOODS_SPEC_PRODUCT_GSP_GOODS_CODES, Indexes.B2C_GOODS_SPEC_PRODUCT_GSP_GOODS_ID, Indexes.B2C_GOODS_SPEC_PRODUCT_GSP_PRD_SN, Indexes.B2C_GOODS_SPEC_PRODUCT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cGoodsSpecProductRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_GOODS_SPEC_PRODUCT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cGoodsSpecProductRecord> getPrimaryKey() {
        return Keys.KEY_B2C_GOODS_SPEC_PRODUCT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cGoodsSpecProductRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cGoodsSpecProductRecord>>asList(Keys.KEY_B2C_GOODS_SPEC_PRODUCT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsSpecProduct as(String alias) {
        return new B2cGoodsSpecProduct(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cGoodsSpecProduct as(Name alias) {
        return new B2cGoodsSpecProduct(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cGoodsSpecProduct rename(String name) {
        return new B2cGoodsSpecProduct(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cGoodsSpecProduct rename(Name name) {
        return new B2cGoodsSpecProduct(name, null);
    }
}

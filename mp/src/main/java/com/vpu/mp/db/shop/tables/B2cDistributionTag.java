/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cDistributionTagRecord;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
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
public class B2cDistributionTag extends TableImpl<B2cDistributionTagRecord> {

    private static final long serialVersionUID = -962919245;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_distribution_tag</code>
     */
    public static final B2cDistributionTag B2C_DISTRIBUTION_TAG = new B2cDistributionTag();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cDistributionTagRecord> getRecordType() {
        return B2cDistributionTagRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_distribution_tag.ref_date</code>. 日期
     */
    public final TableField<B2cDistributionTagRecord, Date> REF_DATE = createField("ref_date", org.jooq.impl.SQLDataType.DATE, this, "日期");

    /**
     * The column <code>mini_shop_471752.b2c_distribution_tag.type</code>. 1,7,30
     */
    public final TableField<B2cDistributionTagRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT, this, "1,7,30");

    /**
     * The column <code>mini_shop_471752.b2c_distribution_tag.tag</code>. 标签
     */
    public final TableField<B2cDistributionTagRecord, String> TAG = createField("tag", org.jooq.impl.SQLDataType.VARCHAR(50), this, "标签");

    /**
     * The column <code>mini_shop_471752.b2c_distribution_tag.pay_order_num</code>. 付款订单数
     */
    public final TableField<B2cDistributionTagRecord, Integer> PAY_ORDER_NUM = createField("pay_order_num", org.jooq.impl.SQLDataType.INTEGER, this, "付款订单数");

    /**
     * The column <code>mini_shop_471752.b2c_distribution_tag.pay_order_money</code>. 付款金额
     */
    public final TableField<B2cDistributionTagRecord, BigDecimal> PAY_ORDER_MONEY = createField("pay_order_money", org.jooq.impl.SQLDataType.DECIMAL(10, 2), this, "付款金额");

    /**
     * The column <code>mini_shop_471752.b2c_distribution_tag.pay_user_num</code>. 付款人数
     */
    public final TableField<B2cDistributionTagRecord, Integer> PAY_USER_NUM = createField("pay_user_num", org.jooq.impl.SQLDataType.INTEGER, this, "付款人数");

    /**
     * The column <code>mini_shop_471752.b2c_distribution_tag.pay_goods_number</code>. 付款商品件数
     */
    public final TableField<B2cDistributionTagRecord, Integer> PAY_GOODS_NUMBER = createField("pay_goods_number", org.jooq.impl.SQLDataType.INTEGER, this, "付款商品件数");

    /**
     * The column <code>mini_shop_471752.b2c_distribution_tag.has_mobile_num</code>. 下单有手机号的用户
     */
    public final TableField<B2cDistributionTagRecord, Integer> HAS_MOBILE_NUM = createField("has_mobile_num", org.jooq.impl.SQLDataType.INTEGER, this, "下单有手机号的用户");

    /**
     * The column <code>mini_shop_471752.b2c_distribution_tag.has_user_num</code>. 用户数
     */
    public final TableField<B2cDistributionTagRecord, Integer> HAS_USER_NUM = createField("has_user_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "用户数");

    /**
     * The column <code>mini_shop_471752.b2c_distribution_tag.add_time</code>. 添加时间
     */
    public final TableField<B2cDistributionTagRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "添加时间");

    /**
     * Create a <code>mini_shop_471752.b2c_distribution_tag</code> table reference
     */
    public B2cDistributionTag() {
        this(DSL.name("b2c_distribution_tag"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_distribution_tag</code> table reference
     */
    public B2cDistributionTag(String alias) {
        this(DSL.name(alias), B2C_DISTRIBUTION_TAG);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_distribution_tag</code> table reference
     */
    public B2cDistributionTag(Name alias) {
        this(alias, B2C_DISTRIBUTION_TAG);
    }

    private B2cDistributionTag(Name alias, Table<B2cDistributionTagRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cDistributionTag(Name alias, Table<B2cDistributionTagRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cDistributionTag(Table<O> child, ForeignKey<O, B2cDistributionTagRecord> key) {
        super(child, key, B2C_DISTRIBUTION_TAG);
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
        return Arrays.<Index>asList(Indexes.B2C_DISTRIBUTION_TAG_DATE_TYPE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionTag as(String alias) {
        return new B2cDistributionTag(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cDistributionTag as(Name alias) {
        return new B2cDistributionTag(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cDistributionTag rename(String name) {
        return new B2cDistributionTag(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cDistributionTag rename(Name name) {
        return new B2cDistributionTag(name, null);
    }
}

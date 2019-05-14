/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cStoreOrderRecord;

import java.math.BigDecimal;
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
public class B2cStoreOrder extends TableImpl<B2cStoreOrderRecord> {

    private static final long serialVersionUID = 646720777;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_store_order</code>
     */
    public static final B2cStoreOrder B2C_STORE_ORDER = new B2cStoreOrder();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cStoreOrderRecord> getRecordType() {
        return B2cStoreOrderRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_store_order.order_id</code>. 订单id
     */
    public final TableField<B2cStoreOrderRecord, UInteger> ORDER_ID = createField("order_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "订单id");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.store_id</code>. 门店id
     */
    public final TableField<B2cStoreOrderRecord, Integer> STORE_ID = createField("store_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "门店id");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.order_sn</code>. 订单编号
     */
    public final TableField<B2cStoreOrderRecord, String> ORDER_SN = createField("order_sn", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "订单编号");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.user_id</code>. 用户id
     */
    public final TableField<B2cStoreOrderRecord, UInteger> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "用户id");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.order_status</code>. 订单状态
     */
    public final TableField<B2cStoreOrderRecord, Byte> ORDER_STATUS = createField("order_status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "订单状态");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.order_status_name</code>. 订单状态名称
     */
    public final TableField<B2cStoreOrderRecord, String> ORDER_STATUS_NAME = createField("order_status_name", org.jooq.impl.SQLDataType.VARCHAR(32), this, "订单状态名称");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.invoice_id</code>. 发票id
     */
    public final TableField<B2cStoreOrderRecord, Integer> INVOICE_ID = createField("invoice_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "发票id");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.invoice_detail</code>. 发票内容：json存储
     */
    public final TableField<B2cStoreOrderRecord, String> INVOICE_DETAIL = createField("invoice_detail", org.jooq.impl.SQLDataType.CLOB, this, "发票内容：json存储");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.add_message</code>. 客户留言
     */
    public final TableField<B2cStoreOrderRecord, String> ADD_MESSAGE = createField("add_message", org.jooq.impl.SQLDataType.VARCHAR(191).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "客户留言");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.pay_code</code>. 支付代号
     */
    public final TableField<B2cStoreOrderRecord, String> PAY_CODE = createField("pay_code", org.jooq.impl.SQLDataType.VARCHAR(30), this, "支付代号");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.pay_name</code>. 支付名称
     */
    public final TableField<B2cStoreOrderRecord, String> PAY_NAME = createField("pay_name", org.jooq.impl.SQLDataType.VARCHAR(120), this, "支付名称");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.prepay_id</code>. 微信支付Id，用于发送模板消息
     */
    public final TableField<B2cStoreOrderRecord, String> PREPAY_ID = createField("prepay_id", org.jooq.impl.SQLDataType.VARCHAR(191), this, "微信支付Id，用于发送模板消息");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.pay_sn</code>. 支付流水号
     */
    public final TableField<B2cStoreOrderRecord, String> PAY_SN = createField("pay_sn", org.jooq.impl.SQLDataType.VARCHAR(32), this, "支付流水号");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.money_paid</code>. 订单应付金额
     */
    public final TableField<B2cStoreOrderRecord, BigDecimal> MONEY_PAID = createField("money_paid", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "订单应付金额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.member_card_no</code>. 会员卡NO
     */
    public final TableField<B2cStoreOrderRecord, String> MEMBER_CARD_NO = createField("member_card_no", org.jooq.impl.SQLDataType.VARCHAR(32).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "会员卡NO");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.member_card_redunce</code>. 会员卡抵扣金额
     */
    public final TableField<B2cStoreOrderRecord, BigDecimal> MEMBER_CARD_REDUNCE = createField("member_card_redunce", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "会员卡抵扣金额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.member_card_balance</code>. 会员卡消费金额
     */
    public final TableField<B2cStoreOrderRecord, BigDecimal> MEMBER_CARD_BALANCE = createField("member_card_balance", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "会员卡消费金额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.score_discount</code>. 积分抵扣金额
     */
    public final TableField<B2cStoreOrderRecord, BigDecimal> SCORE_DISCOUNT = createField("score_discount", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "积分抵扣金额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.use_account</code>. 用户消费余额
     */
    public final TableField<B2cStoreOrderRecord, BigDecimal> USE_ACCOUNT = createField("use_account", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "用户消费余额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.order_amount</code>. 订单总金额
     */
    public final TableField<B2cStoreOrderRecord, BigDecimal> ORDER_AMOUNT = createField("order_amount", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "订单总金额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.add_time</code>. 订单提交时间
     */
    public final TableField<B2cStoreOrderRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "订单提交时间");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.pay_time</code>. 支付时间
     */
    public final TableField<B2cStoreOrderRecord, Timestamp> PAY_TIME = createField("pay_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "支付时间");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.seller_remark</code>. 卖家备注
     */
    public final TableField<B2cStoreOrderRecord, String> SELLER_REMARK = createField("seller_remark", org.jooq.impl.SQLDataType.VARCHAR(512).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "卖家备注");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.star_flag</code>. 标星订单：0 未标星 1 标星
     */
    public final TableField<B2cStoreOrderRecord, Byte> STAR_FLAG = createField("star_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "标星订单：0 未标星 1 标星");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.del_flag</code>. 删除
     */
    public final TableField<B2cStoreOrderRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "删除");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.ali_trade_no</code>. 支付宝交易单号
     */
    public final TableField<B2cStoreOrderRecord, String> ALI_TRADE_NO = createField("ali_trade_no", org.jooq.impl.SQLDataType.VARCHAR(60).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "支付宝交易单号");

    /**
     * Create a <code>mini_shop_471752.b2c_store_order</code> table reference
     */
    public B2cStoreOrder() {
        this(DSL.name("b2c_store_order"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_store_order</code> table reference
     */
    public B2cStoreOrder(String alias) {
        this(DSL.name(alias), B2C_STORE_ORDER);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_store_order</code> table reference
     */
    public B2cStoreOrder(Name alias) {
        this(alias, B2C_STORE_ORDER);
    }

    private B2cStoreOrder(Name alias, Table<B2cStoreOrderRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cStoreOrder(Name alias, Table<B2cStoreOrderRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cStoreOrder(Table<O> child, ForeignKey<O, B2cStoreOrderRecord> key) {
        super(child, key, B2C_STORE_ORDER);
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
        return Arrays.<Index>asList(Indexes.B2C_STORE_ORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cStoreOrderRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_STORE_ORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cStoreOrderRecord> getPrimaryKey() {
        return Keys.KEY_B2C_STORE_ORDER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cStoreOrderRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cStoreOrderRecord>>asList(Keys.KEY_B2C_STORE_ORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cStoreOrder as(String alias) {
        return new B2cStoreOrder(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cStoreOrder as(Name alias) {
        return new B2cStoreOrder(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cStoreOrder rename(String name) {
        return new B2cStoreOrder(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cStoreOrder rename(Name name) {
        return new B2cStoreOrder(name, null);
    }
}

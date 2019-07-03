/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.StoreOrderRecord;

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
public class StoreOrder extends TableImpl<StoreOrderRecord> {

    private static final long serialVersionUID = -867970769;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_store_order</code>
     */
    public static final StoreOrder STORE_ORDER = new StoreOrder();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StoreOrderRecord> getRecordType() {
        return StoreOrderRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_store_order.order_id</code>. 订单id
     */
    public final TableField<StoreOrderRecord, Integer> ORDER_ID = createField("order_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "订单id");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.store_id</code>. 门店id
     */
    public final TableField<StoreOrderRecord, Integer> STORE_ID = createField("store_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "门店id");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.order_sn</code>. 订单编号
     */
    public final TableField<StoreOrderRecord, String> ORDER_SN = createField("order_sn", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "订单编号");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.user_id</code>. 用户id
     */
    public final TableField<StoreOrderRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "用户id");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.order_status</code>. 订单状态
     */
    public final TableField<StoreOrderRecord, Byte> ORDER_STATUS = createField("order_status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "订单状态");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.order_status_name</code>. 订单状态名称
     */
    public final TableField<StoreOrderRecord, String> ORDER_STATUS_NAME = createField("order_status_name", org.jooq.impl.SQLDataType.VARCHAR(32), this, "订单状态名称");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.invoice_id</code>. 发票id
     */
    public final TableField<StoreOrderRecord, Integer> INVOICE_ID = createField("invoice_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "发票id");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.invoice_detail</code>. 发票内容：json存储
     */
    public final TableField<StoreOrderRecord, String> INVOICE_DETAIL = createField("invoice_detail", org.jooq.impl.SQLDataType.CLOB, this, "发票内容：json存储");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.add_message</code>. 客户留言
     */
    public final TableField<StoreOrderRecord, String> ADD_MESSAGE = createField("add_message", org.jooq.impl.SQLDataType.VARCHAR(191).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "客户留言");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.pay_code</code>. 支付代号
     */
    public final TableField<StoreOrderRecord, String> PAY_CODE = createField("pay_code", org.jooq.impl.SQLDataType.VARCHAR(30), this, "支付代号");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.pay_name</code>. 支付名称
     */
    public final TableField<StoreOrderRecord, String> PAY_NAME = createField("pay_name", org.jooq.impl.SQLDataType.VARCHAR(120), this, "支付名称");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.prepay_id</code>. 微信支付Id，用于发送模板消息
     */
    public final TableField<StoreOrderRecord, String> PREPAY_ID = createField("prepay_id", org.jooq.impl.SQLDataType.VARCHAR(191), this, "微信支付Id，用于发送模板消息");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.pay_sn</code>. 支付流水号
     */
    public final TableField<StoreOrderRecord, String> PAY_SN = createField("pay_sn", org.jooq.impl.SQLDataType.VARCHAR(32), this, "支付流水号");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.money_paid</code>. 订单应付金额
     */
    public final TableField<StoreOrderRecord, BigDecimal> MONEY_PAID = createField("money_paid", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "订单应付金额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.member_card_no</code>. 会员卡NO
     */
    public final TableField<StoreOrderRecord, String> MEMBER_CARD_NO = createField("member_card_no", org.jooq.impl.SQLDataType.VARCHAR(32).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "会员卡NO");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.member_card_redunce</code>. 会员卡抵扣金额
     */
    public final TableField<StoreOrderRecord, BigDecimal> MEMBER_CARD_REDUNCE = createField("member_card_redunce", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "会员卡抵扣金额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.member_card_balance</code>. 会员卡消费金额
     */
    public final TableField<StoreOrderRecord, BigDecimal> MEMBER_CARD_BALANCE = createField("member_card_balance", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "会员卡消费金额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.score_discount</code>. 积分抵扣金额
     */
    public final TableField<StoreOrderRecord, BigDecimal> SCORE_DISCOUNT = createField("score_discount", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "积分抵扣金额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.use_account</code>. 用户消费余额
     */
    public final TableField<StoreOrderRecord, BigDecimal> USE_ACCOUNT = createField("use_account", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "用户消费余额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.order_amount</code>. 订单总金额
     */
    public final TableField<StoreOrderRecord, BigDecimal> ORDER_AMOUNT = createField("order_amount", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "订单总金额");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.add_time</code>. 订单提交时间
     */
    public final TableField<StoreOrderRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "订单提交时间");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.pay_time</code>. 支付时间
     */
    public final TableField<StoreOrderRecord, Timestamp> PAY_TIME = createField("pay_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "支付时间");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.seller_remark</code>. 卖家备注
     */
    public final TableField<StoreOrderRecord, String> SELLER_REMARK = createField("seller_remark", org.jooq.impl.SQLDataType.VARCHAR(512).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "卖家备注");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.star_flag</code>. 标星订单：0 未标星 1 标星
     */
    public final TableField<StoreOrderRecord, Byte> STAR_FLAG = createField("star_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "标星订单：0 未标星 1 标星");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.del_flag</code>. 删除
     */
    public final TableField<StoreOrderRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "删除");

    /**
     * The column <code>mini_shop_471752.b2c_store_order.ali_trade_no</code>. 支付宝交易单号
     */
    public final TableField<StoreOrderRecord, String> ALI_TRADE_NO = createField("ali_trade_no", org.jooq.impl.SQLDataType.VARCHAR(60).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "支付宝交易单号");

    /**
     * Create a <code>mini_shop_471752.b2c_store_order</code> table reference
     */
    public StoreOrder() {
        this(DSL.name("b2c_store_order"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_store_order</code> table reference
     */
    public StoreOrder(String alias) {
        this(DSL.name(alias), STORE_ORDER);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_store_order</code> table reference
     */
    public StoreOrder(Name alias) {
        this(alias, STORE_ORDER);
    }

    private StoreOrder(Name alias, Table<StoreOrderRecord> aliased) {
        this(alias, aliased, null);
    }

    private StoreOrder(Name alias, Table<StoreOrderRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> StoreOrder(Table<O> child, ForeignKey<O, StoreOrderRecord> key) {
        super(child, key, STORE_ORDER);
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
        return Arrays.<Index>asList(Indexes.STORE_ORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<StoreOrderRecord, Integer> getIdentity() {
        return Keys.IDENTITY_STORE_ORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StoreOrderRecord> getPrimaryKey() {
        return Keys.KEY_B2C_STORE_ORDER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StoreOrderRecord>> getKeys() {
        return Arrays.<UniqueKey<StoreOrderRecord>>asList(Keys.KEY_B2C_STORE_ORDER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreOrder as(String alias) {
        return new StoreOrder(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreOrder as(Name alias) {
        return new StoreOrder(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StoreOrder rename(String name) {
        return new StoreOrder(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public StoreOrder rename(Name name) {
        return new StoreOrder(name, null);
    }
}

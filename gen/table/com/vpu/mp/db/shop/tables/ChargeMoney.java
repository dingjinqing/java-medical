/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.ChargeMoneyRecord;

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
public class ChargeMoney extends TableImpl<ChargeMoneyRecord> {

    private static final long serialVersionUID = 1993707312;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_charge_money</code>
     */
    public static final ChargeMoney CHARGE_MONEY = new ChargeMoney();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ChargeMoneyRecord> getRecordType() {
        return ChargeMoneyRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.id</code>.
     */
    public final TableField<ChargeMoneyRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.user_id</code>. 用户id
     */
    public final TableField<ChargeMoneyRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "用户id");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.card_id</code>. 会员卡id
     */
    public final TableField<ChargeMoneyRecord, Integer> CARD_ID = createField("card_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "会员卡id");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.add_time</code>. 充值时间
     */
    public final TableField<ChargeMoneyRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "充值时间");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.charge</code>. 充值的钱
     */
    public final TableField<ChargeMoneyRecord, BigDecimal> CHARGE = createField("charge", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "充值的钱");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.count</code>. 充值次数
     */
    public final TableField<ChargeMoneyRecord, Short> COUNT = createField("count", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "充值次数");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.payment</code>. 支付方式
     */
    public final TableField<ChargeMoneyRecord, String> PAYMENT = createField("payment", org.jooq.impl.SQLDataType.VARCHAR(90).nullable(false), this, "支付方式");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.type</code>. 消费类型 0是普通卡 1限次卡
     */
    public final TableField<ChargeMoneyRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "消费类型 0是普通卡 1限次卡");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.reason</code>. 充值原因
     */
    public final TableField<ChargeMoneyRecord, String> REASON = createField("reason", org.jooq.impl.SQLDataType.VARCHAR(191), this, "充值原因");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.prepay_id</code>. 微信支付Id，用于发送模板消息
     */
    public final TableField<ChargeMoneyRecord, String> PREPAY_ID = createField("prepay_id", org.jooq.impl.SQLDataType.VARCHAR(191), this, "微信支付Id，用于发送模板消息");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.message</code>. 备注
     */
    public final TableField<ChargeMoneyRecord, String> MESSAGE = createField("message", org.jooq.impl.SQLDataType.VARCHAR(191).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "备注");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.order_sn</code>.
     */
    public final TableField<ChargeMoneyRecord, String> ORDER_SN = createField("order_sn", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.order_status</code>. 订单状态 0：待支付，1：已取消，2：已完成
     */
    public final TableField<ChargeMoneyRecord, Byte> ORDER_STATUS = createField("order_status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "订单状态 0：待支付，1：已取消，2：已完成");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.money_paid</code>. 订单应付金额
     */
    public final TableField<ChargeMoneyRecord, BigDecimal> MONEY_PAID = createField("money_paid", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "订单应付金额");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.charge_type</code>. 0按规则 1自定义
     */
    public final TableField<ChargeMoneyRecord, Byte> CHARGE_TYPE = createField("charge_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0按规则 1自定义");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.card_no</code>. 会员卡号
     */
    public final TableField<ChargeMoneyRecord, String> CARD_NO = createField("card_no", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "会员卡号");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.ali_trade_no</code>. 支付宝交易单号
     */
    public final TableField<ChargeMoneyRecord, String> ALI_TRADE_NO = createField("ali_trade_no", org.jooq.impl.SQLDataType.VARCHAR(60).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "支付宝交易单号");

    /**
     * The column <code>mini_shop_471752.b2c_charge_money.exchang_count</code>. 兑换充值次数
     */
    public final TableField<ChargeMoneyRecord, Short> EXCHANG_COUNT = createField("exchang_count", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "兑换充值次数");

    /**
     * Create a <code>mini_shop_471752.b2c_charge_money</code> table reference
     */
    public ChargeMoney() {
        this(DSL.name("b2c_charge_money"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_charge_money</code> table reference
     */
    public ChargeMoney(String alias) {
        this(DSL.name(alias), CHARGE_MONEY);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_charge_money</code> table reference
     */
    public ChargeMoney(Name alias) {
        this(alias, CHARGE_MONEY);
    }

    private ChargeMoney(Name alias, Table<ChargeMoneyRecord> aliased) {
        this(alias, aliased, null);
    }

    private ChargeMoney(Name alias, Table<ChargeMoneyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> ChargeMoney(Table<O> child, ForeignKey<O, ChargeMoneyRecord> key) {
        super(child, key, CHARGE_MONEY);
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
        return Arrays.<Index>asList(Indexes.CHARGE_MONEY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ChargeMoneyRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CHARGE_MONEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ChargeMoneyRecord> getPrimaryKey() {
        return Keys.KEY_B2C_CHARGE_MONEY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ChargeMoneyRecord>> getKeys() {
        return Arrays.<UniqueKey<ChargeMoneyRecord>>asList(Keys.KEY_B2C_CHARGE_MONEY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoney as(String alias) {
        return new ChargeMoney(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargeMoney as(Name alias) {
        return new ChargeMoney(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ChargeMoney rename(String name) {
        return new ChargeMoney(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ChargeMoney rename(Name name) {
        return new ChargeMoney(name, null);
    }
}

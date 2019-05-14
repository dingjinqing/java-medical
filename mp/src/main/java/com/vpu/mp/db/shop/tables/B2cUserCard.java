/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cUserCardRecord;

import java.math.BigDecimal;
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
public class B2cUserCard extends TableImpl<B2cUserCardRecord> {

    private static final long serialVersionUID = 1117632241;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_user_card</code>
     */
    public static final B2cUserCard B2C_USER_CARD = new B2cUserCard();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cUserCardRecord> getRecordType() {
        return B2cUserCardRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_user_card.user_id</code>. 会员ID
     */
    public final TableField<B2cUserCardRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "会员ID");

    /**
     * The column <code>mini_shop_471752.b2c_user_card.card_id</code>. 会员卡ID
     */
    public final TableField<B2cUserCardRecord, Integer> CARD_ID = createField("card_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "会员卡ID");

    /**
     * The column <code>mini_shop_471752.b2c_user_card.add_time</code>.
     */
    public final TableField<B2cUserCardRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_user_card.flag</code>. 0:正常，1:删除
     */
    public final TableField<B2cUserCardRecord, Byte> FLAG = createField("flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:正常，1:删除");

    /**
     * The column <code>mini_shop_471752.b2c_user_card.card_no</code>. 会员卡号
     */
    public final TableField<B2cUserCardRecord, String> CARD_NO = createField("card_no", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "会员卡号");

    /**
     * The column <code>mini_shop_471752.b2c_user_card.expire_time</code>.
     */
    public final TableField<B2cUserCardRecord, Timestamp> EXPIRE_TIME = createField("expire_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_user_card.update_time</code>. 更新时间
     */
    public final TableField<B2cUserCardRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "更新时间");

    /**
     * The column <code>mini_shop_471752.b2c_user_card.is_default</code>. 1:默认会员卡
     */
    public final TableField<B2cUserCardRecord, Byte> IS_DEFAULT = createField("is_default", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "1:默认会员卡");

    /**
     * The column <code>mini_shop_471752.b2c_user_card.money</code>. 卡余额
     */
    public final TableField<B2cUserCardRecord, BigDecimal> MONEY = createField("money", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "卡余额");

    /**
     * The column <code>mini_shop_471752.b2c_user_card.surplus</code>. 卡剩余次数
     */
    public final TableField<B2cUserCardRecord, Integer> SURPLUS = createField("surplus", org.jooq.impl.SQLDataType.INTEGER, this, "卡剩余次数");

    /**
     * The column <code>mini_shop_471752.b2c_user_card.activation_time</code>. 激活时间
     */
    public final TableField<B2cUserCardRecord, Timestamp> ACTIVATION_TIME = createField("activation_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "激活时间");

    /**
     * The column <code>mini_shop_471752.b2c_user_card.exchang_surplus</code>. 卡剩余兑换次数
     */
    public final TableField<B2cUserCardRecord, Integer> EXCHANG_SURPLUS = createField("exchang_surplus", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "卡剩余兑换次数");

    /**
     * Create a <code>mini_shop_471752.b2c_user_card</code> table reference
     */
    public B2cUserCard() {
        this(DSL.name("b2c_user_card"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_user_card</code> table reference
     */
    public B2cUserCard(String alias) {
        this(DSL.name(alias), B2C_USER_CARD);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_user_card</code> table reference
     */
    public B2cUserCard(Name alias) {
        this(alias, B2C_USER_CARD);
    }

    private B2cUserCard(Name alias, Table<B2cUserCardRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cUserCard(Name alias, Table<B2cUserCardRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cUserCard(Table<O> child, ForeignKey<O, B2cUserCardRecord> key) {
        super(child, key, B2C_USER_CARD);
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
        return Arrays.<Index>asList(Indexes.B2C_USER_CARD_CARD_NO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cUserCardRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cUserCardRecord>>asList(Keys.KEY_B2C_USER_CARD_CARD_NO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserCard as(String alias) {
        return new B2cUserCard(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cUserCard as(Name alias) {
        return new B2cUserCard(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cUserCard rename(String name) {
        return new B2cUserCard(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cUserCard rename(Name name) {
        return new B2cUserCard(name, null);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;

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
 * 会员绑定的会员卡信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserCard extends TableImpl<UserCardRecord> {

    private static final long serialVersionUID = 2072695310;

    /**
     * The reference instance of <code>jmini_shop_489258.b2c_user_card</code>
     */
    public static final UserCard USER_CARD = new UserCard();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserCardRecord> getRecordType() {
        return UserCardRecord.class;
    }

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.user_id</code>. 会员id
     */
    public final TableField<UserCardRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "会员id");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.card_id</code>. 会员卡id
     */
    public final TableField<UserCardRecord, Integer> CARD_ID = createField("card_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "会员卡id");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.flag</code>. 0:正常，1:删除
     */
    public final TableField<UserCardRecord, Byte> FLAG = createField("flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:正常，1:删除");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.card_no</code>. 会员卡号
     */
    public final TableField<UserCardRecord, String> CARD_NO = createField("card_no", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "会员卡号");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.expire_time</code>.
     */
    public final TableField<UserCardRecord, Timestamp> EXPIRE_TIME = createField("expire_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.is_default</code>. 1:默认会员卡
     */
    public final TableField<UserCardRecord, Byte> IS_DEFAULT = createField("is_default", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "1:默认会员卡");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.money</code>. 卡余额
     */
    public final TableField<UserCardRecord, BigDecimal> MONEY = createField("money", org.jooq.impl.SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "卡余额");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.surplus</code>. 卡剩余次数
     */
    public final TableField<UserCardRecord, Integer> SURPLUS = createField("surplus", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "卡剩余次数");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.activation_time</code>. 激活时间
     */
    public final TableField<UserCardRecord, Timestamp> ACTIVATION_TIME = createField("activation_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "激活时间");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.exchang_surplus</code>. 卡剩余兑换次数
     */
    public final TableField<UserCardRecord, Integer> EXCHANG_SURPLUS = createField("exchang_surplus", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "卡剩余兑换次数");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.create_time</code>.
     */
    public final TableField<UserCardRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.update_time</code>. 最后修改时间
     */
    public final TableField<UserCardRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "最后修改时间");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.id</code>.
     */
    public final TableField<UserCardRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.free_limit</code>. -1：不包邮，0:不限制，1：持卡有效期内，2：年，3：季，4：月，5：周，6：日
     */
    public final TableField<UserCardRecord, Byte> FREE_LIMIT = createField("free_limit", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("-1", org.jooq.impl.SQLDataType.TINYINT)), this, "-1：不包邮，0:不限制，1：持卡有效期内，2：年，3：季，4：月，5：周，6：日");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.free_num</code>. 周期内包邮次数
     */
    public final TableField<UserCardRecord, Integer> FREE_NUM = createField("free_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "周期内包邮次数");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.give_away_status</code>. 0:正常，1:转赠中，2转赠成功
     */
    public final TableField<UserCardRecord, Byte> GIVE_AWAY_STATUS = createField("give_away_status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:正常，1:转赠中，2转赠成功");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.give_away_surplus</code>. 卡剩余赠送次数
     */
    public final TableField<UserCardRecord, Integer> GIVE_AWAY_SURPLUS = createField("give_away_surplus", org.jooq.impl.SQLDataType.INTEGER, this, "卡剩余赠送次数");

    /**
     * The column <code>jmini_shop_489258.b2c_user_card.card_source</code>. 卡来源 0:正常  2 别人转赠 
     */
    public final TableField<UserCardRecord, Byte> CARD_SOURCE = createField("card_source", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "卡来源 0:正常  2 别人转赠 ");

    /**
     * Create a <code>jmini_shop_489258.b2c_user_card</code> table reference
     */
    public UserCard() {
        this(DSL.name("b2c_user_card"), null);
    }

    /**
     * Create an aliased <code>jmini_shop_489258.b2c_user_card</code> table reference
     */
    public UserCard(String alias) {
        this(DSL.name(alias), USER_CARD);
    }

    /**
     * Create an aliased <code>jmini_shop_489258.b2c_user_card</code> table reference
     */
    public UserCard(Name alias) {
        this(alias, USER_CARD);
    }

    private UserCard(Name alias, Table<UserCardRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserCard(Name alias, Table<UserCardRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("会员绑定的会员卡信息"));
    }

    public <O extends Record> UserCard(Table<O> child, ForeignKey<O, UserCardRecord> key) {
        super(child, key, USER_CARD);
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
        return Arrays.<Index>asList(Indexes.USER_CARD_CARD_NO, Indexes.USER_CARD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserCardRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_CARD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserCardRecord> getPrimaryKey() {
        return Keys.KEY_B2C_USER_CARD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserCardRecord>> getKeys() {
        return Arrays.<UniqueKey<UserCardRecord>>asList(Keys.KEY_B2C_USER_CARD_CARD_NO, Keys.KEY_B2C_USER_CARD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCard as(String alias) {
        return new UserCard(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCard as(Name alias) {
        return new UserCard(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserCard rename(String name) {
        return new UserCard(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserCard rename(Name name) {
        return new UserCard(name, null);
    }
}

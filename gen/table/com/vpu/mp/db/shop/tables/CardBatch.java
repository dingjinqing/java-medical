/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.CardBatchRecord;

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
public class CardBatch extends TableImpl<CardBatchRecord> {

    private static final long serialVersionUID = -1492049031;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_card_batch</code>
     */
    public static final CardBatch CARD_BATCH = new CardBatch();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CardBatchRecord> getRecordType() {
        return CardBatchRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.id</code>.
     */
    public final TableField<CardBatchRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.card_id</code>. 卡号ID
     */
    public final TableField<CardBatchRecord, Integer> CARD_ID = createField("card_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "卡号ID");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.action</code>. 领取码获得方式 1：自动生成 2：导入
     */
    public final TableField<CardBatchRecord, Byte> ACTION = createField("action", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "领取码获得方式 1：自动生成 2：导入");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.name</code>. 批次名称
     */
    public final TableField<CardBatchRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false), this, "批次名称");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.code_size</code>. 领取码位数
     */
    public final TableField<CardBatchRecord, Byte> CODE_SIZE = createField("code_size", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "领取码位数");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.card_size</code>. 卡号位数
     */
    public final TableField<CardBatchRecord, Byte> CARD_SIZE = createField("card_size", org.jooq.impl.SQLDataType.TINYINT, this, "卡号位数");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.card_pwd_size</code>. 卡密码位数
     */
    public final TableField<CardBatchRecord, Byte> CARD_PWD_SIZE = createField("card_pwd_size", org.jooq.impl.SQLDataType.TINYINT, this, "卡密码位数");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.number</code>. 发放数量
     */
    public final TableField<CardBatchRecord, Integer> NUMBER = createField("number", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "发放数量");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.code_prefix</code>. 领取码前缀
     */
    public final TableField<CardBatchRecord, String> CODE_PREFIX = createField("code_prefix", org.jooq.impl.SQLDataType.VARCHAR(10), this, "领取码前缀");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.card_prefix</code>. 卡前缀
     */
    public final TableField<CardBatchRecord, String> CARD_PREFIX = createField("card_prefix", org.jooq.impl.SQLDataType.VARCHAR(10), this, "卡前缀");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.add_time</code>.
     */
    public final TableField<CardBatchRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.update_time</code>.
     */
    public final TableField<CardBatchRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.del_flag</code>.
     */
    public final TableField<CardBatchRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_card_batch.del_time</code>.
     */
    public final TableField<CardBatchRecord, Timestamp> DEL_TIME = createField("del_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>mini_shop_471752.b2c_card_batch</code> table reference
     */
    public CardBatch() {
        this(DSL.name("b2c_card_batch"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_card_batch</code> table reference
     */
    public CardBatch(String alias) {
        this(DSL.name(alias), CARD_BATCH);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_card_batch</code> table reference
     */
    public CardBatch(Name alias) {
        this(alias, CARD_BATCH);
    }

    private CardBatch(Name alias, Table<CardBatchRecord> aliased) {
        this(alias, aliased, null);
    }

    private CardBatch(Name alias, Table<CardBatchRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> CardBatch(Table<O> child, ForeignKey<O, CardBatchRecord> key) {
        super(child, key, CARD_BATCH);
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
        return Arrays.<Index>asList(Indexes.CARD_BATCH_ACTION, Indexes.CARD_BATCH_CARD_ID, Indexes.CARD_BATCH_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CardBatchRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CARD_BATCH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CardBatchRecord> getPrimaryKey() {
        return Keys.KEY_B2C_CARD_BATCH_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CardBatchRecord>> getKeys() {
        return Arrays.<UniqueKey<CardBatchRecord>>asList(Keys.KEY_B2C_CARD_BATCH_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardBatch as(String alias) {
        return new CardBatch(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardBatch as(Name alias) {
        return new CardBatch(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CardBatch rename(String name) {
        return new CardBatch(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CardBatch rename(Name name) {
        return new CardBatch(name, null);
    }
}

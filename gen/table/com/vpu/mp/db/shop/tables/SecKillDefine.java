/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.SecKillDefineRecord;

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
public class SecKillDefine extends TableImpl<SecKillDefineRecord> {

    private static final long serialVersionUID = 347756046;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_sec_kill_define</code>
     */
    public static final SecKillDefine SEC_KILL_DEFINE = new SecKillDefine();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SecKillDefineRecord> getRecordType() {
        return SecKillDefineRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.sk_id</code>. 秒杀活动ID
     */
    public final TableField<SecKillDefineRecord, Integer> SK_ID = createField("sk_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "秒杀活动ID");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.shop_id</code>. 店铺ID
     */
    public final TableField<SecKillDefineRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "店铺ID");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.goods_id</code>. 商品ID
     */
    public final TableField<SecKillDefineRecord, Integer> GOODS_ID = createField("goods_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "商品ID");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.name</code>. 活动名称
     */
    public final TableField<SecKillDefineRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "活动名称");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.limit_amount</code>. 每人限购数量
     */
    public final TableField<SecKillDefineRecord, Short> LIMIT_AMOUNT = createField("limit_amount", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "每人限购数量");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.limit_paytime</code>. 规定的有效支付时间
     */
    public final TableField<SecKillDefineRecord, Short> LIMIT_PAYTIME = createField("limit_paytime", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "规定的有效支付时间");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.start_time</code>. 开始时间
     */
    public final TableField<SecKillDefineRecord, Timestamp> START_TIME = createField("start_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "开始时间");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.end_time</code>. 结束时间
     */
    public final TableField<SecKillDefineRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "结束时间");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.stock</code>. 总库存
     */
    public final TableField<SecKillDefineRecord, Short> STOCK = createField("stock", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "总库存");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.sale_num</code>. 销量
     */
    public final TableField<SecKillDefineRecord, Short> SALE_NUM = createField("sale_num", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "销量");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.del_flag</code>.
     */
    public final TableField<SecKillDefineRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.status</code>. 状态： 1：启用  0： 禁用
     */
    public final TableField<SecKillDefineRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "状态： 1：启用  0： 禁用");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.free_freight</code>. 是否免运费： 1：免运费  0： 原先商品的运费
     */
    public final TableField<SecKillDefineRecord, Byte> FREE_FREIGHT = createField("free_freight", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "是否免运费： 1：免运费  0： 原先商品的运费");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.add_time</code>.
     */
    public final TableField<SecKillDefineRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.update_time</code>.
     */
    public final TableField<SecKillDefineRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.del_time</code>.
     */
    public final TableField<SecKillDefineRecord, Integer> DEL_TIME = createField("del_time", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.card_id</code>. 专属会员卡
     */
    public final TableField<SecKillDefineRecord, String> CARD_ID = createField("card_id", org.jooq.impl.SQLDataType.CLOB, this, "专属会员卡");

    /**
     * The column <code>mini_shop_471752.b2c_sec_kill_define.share_config</code>. 分享配置
     */
    public final TableField<SecKillDefineRecord, String> SHARE_CONFIG = createField("share_config", org.jooq.impl.SQLDataType.CLOB, this, "分享配置");

    /**
     * Create a <code>mini_shop_471752.b2c_sec_kill_define</code> table reference
     */
    public SecKillDefine() {
        this(DSL.name("b2c_sec_kill_define"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_sec_kill_define</code> table reference
     */
    public SecKillDefine(String alias) {
        this(DSL.name(alias), SEC_KILL_DEFINE);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_sec_kill_define</code> table reference
     */
    public SecKillDefine(Name alias) {
        this(alias, SEC_KILL_DEFINE);
    }

    private SecKillDefine(Name alias, Table<SecKillDefineRecord> aliased) {
        this(alias, aliased, null);
    }

    private SecKillDefine(Name alias, Table<SecKillDefineRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> SecKillDefine(Table<O> child, ForeignKey<O, SecKillDefineRecord> key) {
        super(child, key, SEC_KILL_DEFINE);
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
        return Arrays.<Index>asList(Indexes.SEC_KILL_DEFINE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<SecKillDefineRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SEC_KILL_DEFINE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SecKillDefineRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SEC_KILL_DEFINE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SecKillDefineRecord>> getKeys() {
        return Arrays.<UniqueKey<SecKillDefineRecord>>asList(Keys.KEY_B2C_SEC_KILL_DEFINE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecKillDefine as(String alias) {
        return new SecKillDefine(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecKillDefine as(Name alias) {
        return new SecKillDefine(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SecKillDefine rename(String name) {
        return new SecKillDefine(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SecKillDefine rename(Name name) {
        return new SecKillDefine(name, null);
    }
}

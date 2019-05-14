/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cWxpResponseCfgRecord;

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
public class B2cWxpResponseCfg extends TableImpl<B2cWxpResponseCfgRecord> {

    private static final long serialVersionUID = 440045887;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_wxp_response_cfg</code>
     */
    public static final B2cWxpResponseCfg B2C_WXP_RESPONSE_CFG = new B2cWxpResponseCfg();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cWxpResponseCfgRecord> getRecordType() {
        return B2cWxpResponseCfgRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.rec_id</code>.
     */
    public final TableField<B2cWxpResponseCfgRecord, UInteger> REC_ID = createField("rec_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.shop_id</code>. 店铺ID
     */
    public final TableField<B2cWxpResponseCfgRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺ID");

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.type</code>. 0 关注时回复，1关键词回复，2默认回复,3菜单回复
     */
    public final TableField<B2cWxpResponseCfgRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0 关注时回复，1关键词回复，2默认回复,3菜单回复");

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.menu_key</code>. 当type=3有效，菜单key，与菜单表中的key对应
     */
    public final TableField<B2cWxpResponseCfgRecord, String> MENU_KEY = createField("menu_key", org.jooq.impl.SQLDataType.VARCHAR(128).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "当type=3有效，菜单key，与菜单表中的key对应");

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.key_words</code>. 当type=1时有效，触发关键词
     */
    public final TableField<B2cWxpResponseCfgRecord, String> KEY_WORDS = createField("key_words", org.jooq.impl.SQLDataType.VARCHAR(40).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "当type=1时有效，触发关键词");

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.match_type</code>. 当type=1时有效，匹配类型，0精确，1模糊
     */
    public final TableField<B2cWxpResponseCfgRecord, Byte> MATCH_TYPE = createField("match_type", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "当type=1时有效，匹配类型，0精确，1模糊");

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.res_type</code>. 回复类型：0文本 1 单图文 2 多图文 3音乐，4 link
     */
    public final TableField<B2cWxpResponseCfgRecord, Byte> RES_TYPE = createField("res_type", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "回复类型：0文本 1 单图文 2 多图文 3音乐，4 link");

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.res_content</code>. 回复内容
     */
    public final TableField<B2cWxpResponseCfgRecord, String> RES_CONTENT = createField("res_content", org.jooq.impl.SQLDataType.CLOB, this, "回复内容");

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.res_rec_id</code>. 素材wxp_material的rec_id
     */
    public final TableField<B2cWxpResponseCfgRecord, Integer> RES_REC_ID = createField("res_rec_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "素材wxp_material的rec_id");

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.create_time</code>.
     */
    public final TableField<B2cWxpResponseCfgRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.update_time</code>.
     */
    public final TableField<B2cWxpResponseCfgRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_wxp_response_cfg.micro_id</code>. 所属微信公众号ID,关联b2c_wxp_list的micro_id
     */
    public final TableField<B2cWxpResponseCfgRecord, Integer> MICRO_ID = createField("micro_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "所属微信公众号ID,关联b2c_wxp_list的micro_id");

    /**
     * Create a <code>mini_shop_471752.b2c_wxp_response_cfg</code> table reference
     */
    public B2cWxpResponseCfg() {
        this(DSL.name("b2c_wxp_response_cfg"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_wxp_response_cfg</code> table reference
     */
    public B2cWxpResponseCfg(String alias) {
        this(DSL.name(alias), B2C_WXP_RESPONSE_CFG);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_wxp_response_cfg</code> table reference
     */
    public B2cWxpResponseCfg(Name alias) {
        this(alias, B2C_WXP_RESPONSE_CFG);
    }

    private B2cWxpResponseCfg(Name alias, Table<B2cWxpResponseCfgRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cWxpResponseCfg(Name alias, Table<B2cWxpResponseCfgRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cWxpResponseCfg(Table<O> child, ForeignKey<O, B2cWxpResponseCfgRecord> key) {
        super(child, key, B2C_WXP_RESPONSE_CFG);
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
        return Arrays.<Index>asList(Indexes.B2C_WXP_RESPONSE_CFG_MICRO_ID, Indexes.B2C_WXP_RESPONSE_CFG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cWxpResponseCfgRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_B2C_WXP_RESPONSE_CFG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cWxpResponseCfgRecord> getPrimaryKey() {
        return Keys.KEY_B2C_WXP_RESPONSE_CFG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cWxpResponseCfgRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cWxpResponseCfgRecord>>asList(Keys.KEY_B2C_WXP_RESPONSE_CFG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cWxpResponseCfg as(String alias) {
        return new B2cWxpResponseCfg(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cWxpResponseCfg as(Name alias) {
        return new B2cWxpResponseCfg(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cWxpResponseCfg rename(String name) {
        return new B2cWxpResponseCfg(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cWxpResponseCfg rename(Name name) {
        return new B2cWxpResponseCfg(name, null);
    }
}

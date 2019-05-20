/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_1;
import com.vpu.mp.db.shop.tables.records.CommentTagRecord;

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
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CommentTag extends TableImpl<CommentTagRecord> {

    private static final long serialVersionUID = -1416387396;

    /**
     * The reference instance of <code>mini_shop_1.b2c_comment_tag</code>
     */
    public static final CommentTag COMMENT_TAG = new CommentTag();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CommentTagRecord> getRecordType() {
        return CommentTagRecord.class;
    }

    /**
     * The column <code>mini_shop_1.b2c_comment_tag.id</code>.
     */
    public final TableField<CommentTagRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_1.b2c_comment_tag.commtag</code>.
     */
    public final TableField<CommentTagRecord, String> COMMTAG = createField("commtag", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_shop_1.b2c_comment_tag.user_id</code>.
     */
    public final TableField<CommentTagRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>mini_shop_1.b2c_comment_tag.goods_id</code>.
     */
    public final TableField<CommentTagRecord, Integer> GOODS_ID = createField("goods_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>mini_shop_1.b2c_comment_tag.order_sn</code>.
     */
    public final TableField<CommentTagRecord, String> ORDER_SN = createField("order_sn", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_shop_1.b2c_comment_tag.in_time</code>.
     */
    public final TableField<CommentTagRecord, Timestamp> IN_TIME = createField("in_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_shop_1.b2c_comment_tag.flag</code>. 0:未审批,1:审批通过,2:审批未通过
     */
    public final TableField<CommentTagRecord, Byte> FLAG = createField("flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:未审批,1:审批通过,2:审批未通过");

    /**
     * Create a <code>mini_shop_1.b2c_comment_tag</code> table reference
     */
    public CommentTag() {
        this(DSL.name("b2c_comment_tag"), null);
    }

    /**
     * Create an aliased <code>mini_shop_1.b2c_comment_tag</code> table reference
     */
    public CommentTag(String alias) {
        this(DSL.name(alias), COMMENT_TAG);
    }

    /**
     * Create an aliased <code>mini_shop_1.b2c_comment_tag</code> table reference
     */
    public CommentTag(Name alias) {
        this(alias, COMMENT_TAG);
    }

    private CommentTag(Name alias, Table<CommentTagRecord> aliased) {
        this(alias, aliased, null);
    }

    private CommentTag(Name alias, Table<CommentTagRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> CommentTag(Table<O> child, ForeignKey<O, CommentTagRecord> key) {
        super(child, key, COMMENT_TAG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return MiniShop_1.MINI_SHOP_1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.COMMENT_TAG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CommentTagRecord, Integer> getIdentity() {
        return Keys.IDENTITY_COMMENT_TAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CommentTagRecord> getPrimaryKey() {
        return Keys.KEY_B2C_COMMENT_TAG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CommentTagRecord>> getKeys() {
        return Arrays.<UniqueKey<CommentTagRecord>>asList(Keys.KEY_B2C_COMMENT_TAG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommentTag as(String alias) {
        return new CommentTag(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommentTag as(Name alias) {
        return new CommentTag(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CommentTag rename(String name) {
        return new CommentTag(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CommentTag rename(Name name) {
        return new CommentTag(name, null);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.B2cMpJumpRecord;

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
public class B2cMpJump extends TableImpl<B2cMpJumpRecord> {

    private static final long serialVersionUID = 304185130;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_mp_jump</code>
     */
    public static final B2cMpJump B2C_MP_JUMP = new B2cMpJump();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<B2cMpJumpRecord> getRecordType() {
        return B2cMpJumpRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_mp_jump.id</code>.
     */
    public final TableField<B2cMpJumpRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_mp_jump.app_id</code>.
     */
    public final TableField<B2cMpJumpRecord, String> APP_ID = createField("app_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_mp_jump.app_name</code>.
     */
    public final TableField<B2cMpJumpRecord, String> APP_NAME = createField("app_name", org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_mp_jump.flag</code>. 0:可用，1:停用
     */
    public final TableField<B2cMpJumpRecord, Byte> FLAG = createField("flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:可用，1:停用");

    /**
     * The column <code>mini_shop_471752.b2c_mp_jump.is_delete</code>. 0:未删除，1:已删除
     */
    public final TableField<B2cMpJumpRecord, Byte> IS_DELETE = createField("is_delete", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:未删除，1:已删除");

    /**
     * The column <code>mini_shop_471752.b2c_mp_jump.add_time</code>.
     */
    public final TableField<B2cMpJumpRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_mp_jump.update_time</code>.
     */
    public final TableField<B2cMpJumpRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>mini_shop_471752.b2c_mp_jump</code> table reference
     */
    public B2cMpJump() {
        this(DSL.name("b2c_mp_jump"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_mp_jump</code> table reference
     */
    public B2cMpJump(String alias) {
        this(DSL.name(alias), B2C_MP_JUMP);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_mp_jump</code> table reference
     */
    public B2cMpJump(Name alias) {
        this(alias, B2C_MP_JUMP);
    }

    private B2cMpJump(Name alias, Table<B2cMpJumpRecord> aliased) {
        this(alias, aliased, null);
    }

    private B2cMpJump(Name alias, Table<B2cMpJumpRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> B2cMpJump(Table<O> child, ForeignKey<O, B2cMpJumpRecord> key) {
        super(child, key, B2C_MP_JUMP);
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
        return Arrays.<Index>asList(Indexes.B2C_MP_JUMP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<B2cMpJumpRecord, Integer> getIdentity() {
        return Keys.IDENTITY_B2C_MP_JUMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<B2cMpJumpRecord> getPrimaryKey() {
        return Keys.KEY_B2C_MP_JUMP_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<B2cMpJumpRecord>> getKeys() {
        return Arrays.<UniqueKey<B2cMpJumpRecord>>asList(Keys.KEY_B2C_MP_JUMP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMpJump as(String alias) {
        return new B2cMpJump(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public B2cMpJump as(Name alias) {
        return new B2cMpJump(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cMpJump rename(String name) {
        return new B2cMpJump(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public B2cMpJump rename(Name name) {
        return new B2cMpJump(name, null);
    }
}

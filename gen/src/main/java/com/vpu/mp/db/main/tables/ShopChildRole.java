/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.ShopChildRoleRecord;

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
public class ShopChildRole extends TableImpl<ShopChildRoleRecord> {

    private static final long serialVersionUID = -941243594;

    /**
     * The reference instance of <code>mini_main.b2c_shop_child_role</code>
     */
    public static final ShopChildRole SHOP_CHILD_ROLE = new ShopChildRole();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ShopChildRoleRecord> getRecordType() {
        return ShopChildRoleRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_shop_child_role.rec_id</code>.
     */
    public final TableField<ShopChildRoleRecord, Integer> REC_ID = createField("rec_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_shop_child_role.sys_id</code>. 主账户ID
     */
    public final TableField<ShopChildRoleRecord, Integer> SYS_ID = createField("sys_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "主账户ID");

    /**
     * The column <code>mini_main.b2c_shop_child_role.account_id</code>. 子账户ID
     */
    public final TableField<ShopChildRoleRecord, Integer> ACCOUNT_ID = createField("account_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "子账户ID");

    /**
     * The column <code>mini_main.b2c_shop_child_role.shop_id</code>. 店铺ID
     */
    public final TableField<ShopChildRoleRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺ID");

    /**
     * The column <code>mini_main.b2c_shop_child_role.role_id</code>. 角色ID
     */
    public final TableField<ShopChildRoleRecord, Integer> ROLE_ID = createField("role_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "角色ID");

    /**
     * Create a <code>mini_main.b2c_shop_child_role</code> table reference
     */
    public ShopChildRole() {
        this(DSL.name("b2c_shop_child_role"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_child_role</code> table reference
     */
    public ShopChildRole(String alias) {
        this(DSL.name(alias), SHOP_CHILD_ROLE);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_child_role</code> table reference
     */
    public ShopChildRole(Name alias) {
        this(alias, SHOP_CHILD_ROLE);
    }

    private ShopChildRole(Name alias, Table<ShopChildRoleRecord> aliased) {
        this(alias, aliased, null);
    }

    private ShopChildRole(Name alias, Table<ShopChildRoleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> ShopChildRole(Table<O> child, ForeignKey<O, ShopChildRoleRecord> key) {
        super(child, key, SHOP_CHILD_ROLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return MiniMain.MINI_MAIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.SHOP_CHILD_ROLE_ACCOUNT_ID, Indexes.SHOP_CHILD_ROLE_PRIMARY, Indexes.SHOP_CHILD_ROLE_SYS_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ShopChildRoleRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SHOP_CHILD_ROLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ShopChildRoleRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SHOP_CHILD_ROLE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ShopChildRoleRecord>> getKeys() {
        return Arrays.<UniqueKey<ShopChildRoleRecord>>asList(Keys.KEY_B2C_SHOP_CHILD_ROLE_PRIMARY, Keys.KEY_B2C_SHOP_CHILD_ROLE_ACCOUNT_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopChildRole as(String alias) {
        return new ShopChildRole(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopChildRole as(Name alias) {
        return new ShopChildRole(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ShopChildRole rename(String name) {
        return new ShopChildRole(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ShopChildRole rename(Name name) {
        return new ShopChildRole(name, null);
    }
}

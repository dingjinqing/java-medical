/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_1;
import com.vpu.mp.db.shop.tables.records.UserGradeRecord;

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
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserGrade extends TableImpl<UserGradeRecord> {

    private static final long serialVersionUID = -1683521648;

    /**
     * The reference instance of <code>mini_shop_1.b2c_user_grade</code>
     */
    public static final UserGrade USER_GRADE = new UserGrade();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserGradeRecord> getRecordType() {
        return UserGradeRecord.class;
    }

    /**
     * The column <code>mini_shop_1.b2c_user_grade.id</code>.
     */
    public final TableField<UserGradeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_1.b2c_user_grade.shop_id</code>. 店铺ID
     */
    public final TableField<UserGradeRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺ID");

    /**
     * The column <code>mini_shop_1.b2c_user_grade.grade</code>. 会员等级：reg:注册,bronze:铜,silver:银,gold:金,diamond:钻石
     */
    public final TableField<UserGradeRecord, String> GRADE = createField("grade", org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false), this, "会员等级：reg:注册,bronze:铜,silver:银,gold:金,diamond:钻石");

    /**
     * The column <code>mini_shop_1.b2c_user_grade.grade_name</code>. 等级名称
     */
    public final TableField<UserGradeRecord, String> GRADE_NAME = createField("grade_name", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false), this, "等级名称");

    /**
     * The column <code>mini_shop_1.b2c_user_grade.min_val</code>. 最小成长值
     */
    public final TableField<UserGradeRecord, Integer> MIN_VAL = createField("min_val", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "最小成长值");

    /**
     * The column <code>mini_shop_1.b2c_user_grade.max_val</code>. 最大成长史
     */
    public final TableField<UserGradeRecord, Integer> MAX_VAL = createField("max_val", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "最大成长史");

    /**
     * The column <code>mini_shop_1.b2c_user_grade.reduce</code>. 一年后扣除的成长值
     */
    public final TableField<UserGradeRecord, Integer> REDUCE = createField("reduce", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "一年后扣除的成长值");

    /**
     * The column <code>mini_shop_1.b2c_user_grade.in_time</code>.
     */
    public final TableField<UserGradeRecord, Timestamp> IN_TIME = createField("in_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_1.b2c_user_grade.up_time</code>.
     */
    public final TableField<UserGradeRecord, Timestamp> UP_TIME = createField("up_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_1.b2c_user_grade.settle_time</code>. 结算时间
     */
    public final TableField<UserGradeRecord, Timestamp> SETTLE_TIME = createField("settle_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "结算时间");

    /**
     * The column <code>mini_shop_1.b2c_user_grade.divide</code>. 分成
     */
    public final TableField<UserGradeRecord, BigDecimal> DIVIDE = createField("divide", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "分成");

    /**
     * The column <code>mini_shop_1.b2c_user_grade.discount</code>. 折扣
     */
    public final TableField<UserGradeRecord, BigDecimal> DISCOUNT = createField("discount", org.jooq.impl.SQLDataType.DECIMAL(10, 2).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "折扣");

    /**
     * Create a <code>mini_shop_1.b2c_user_grade</code> table reference
     */
    public UserGrade() {
        this(DSL.name("b2c_user_grade"), null);
    }

    /**
     * Create an aliased <code>mini_shop_1.b2c_user_grade</code> table reference
     */
    public UserGrade(String alias) {
        this(DSL.name(alias), USER_GRADE);
    }

    /**
     * Create an aliased <code>mini_shop_1.b2c_user_grade</code> table reference
     */
    public UserGrade(Name alias) {
        this(alias, USER_GRADE);
    }

    private UserGrade(Name alias, Table<UserGradeRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserGrade(Name alias, Table<UserGradeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> UserGrade(Table<O> child, ForeignKey<O, UserGradeRecord> key) {
        super(child, key, USER_GRADE);
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
        return Arrays.<Index>asList(Indexes.USER_GRADE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserGradeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_GRADE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserGradeRecord> getPrimaryKey() {
        return Keys.KEY_B2C_USER_GRADE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserGradeRecord>> getKeys() {
        return Arrays.<UniqueKey<UserGradeRecord>>asList(Keys.KEY_B2C_USER_GRADE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGrade as(String alias) {
        return new UserGrade(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGrade as(Name alias) {
        return new UserGrade(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserGrade rename(String name) {
        return new UserGrade(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserGrade rename(Name name) {
        return new UserGrade(name, null);
    }
}

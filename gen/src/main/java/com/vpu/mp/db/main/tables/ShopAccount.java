/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.ShopAccountRecord;

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
public class ShopAccount extends TableImpl<ShopAccountRecord> {

    private static final long serialVersionUID = 173239609;

    /**
     * The reference instance of <code>mini_main.b2c_shop_account</code>
     */
    public static final ShopAccount SHOP_ACCOUNT = new ShopAccount();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ShopAccountRecord> getRecordType() {
        return ShopAccountRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_shop_account.sys_id</code>. 店铺ID
     */
    public final TableField<ShopAccountRecord, Integer> SYS_ID = createField("sys_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "店铺ID");

    /**
     * The column <code>mini_main.b2c_shop_account.user_name</code>. 用户名
     */
    public final TableField<ShopAccountRecord, String> USER_NAME = createField("user_name", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("-- dd ##", org.jooq.impl.SQLDataType.VARCHAR)), this, "用户名");

    /**
     * The column <code>mini_main.b2c_shop_account.password</code>. 密码
     */
    public final TableField<ShopAccountRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "密码");

    /**
     * The column <code>mini_main.b2c_shop_account.state</code>. 1 入驻申请，2审核通过，3审核不通过,4已禁用
     */
    public final TableField<ShopAccountRecord, Byte> STATE = createField("state", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "1 入驻申请，2审核通过，3审核不通过,4已禁用");

    /**
     * The column <code>mini_main.b2c_shop_account.business_state</code>. 营业状态 0未营业 1营业
     */
    public final TableField<ShopAccountRecord, Byte> BUSINESS_STATE = createField("business_state", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "营业状态 0未营业 1营业");

    /**
     * The column <code>mini_main.b2c_shop_account.shop_grade</code>. 店铺等级：4旗舰店、3精品店、2专营店、1普通店
     */
    public final TableField<ShopAccountRecord, Byte> SHOP_GRADE = createField("shop_grade", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "店铺等级：4旗舰店、3精品店、2专营店、1普通店");

    /**
     * The column <code>mini_main.b2c_shop_account.max_sku_num</code>. 最大上传sku数量，不同等级不同数量，管理员可修改
     */
    public final TableField<ShopAccountRecord, Integer> MAX_SKU_NUM = createField("max_sku_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "最大上传sku数量，不同等级不同数量，管理员可修改");

    /**
     * The column <code>mini_main.b2c_shop_account.max_shop_num</code>. 最多小程序数量，不同等级不同数量，管理员可修改
     */
    public final TableField<ShopAccountRecord, Integer> MAX_SHOP_NUM = createField("max_shop_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "最多小程序数量，不同等级不同数量，管理员可修改");

    /**
     * The column <code>mini_main.b2c_shop_account.add_time</code>. 创建时间
     */
    public final TableField<ShopAccountRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>mini_main.b2c_shop_account.buy_time</code>. 首次续费时间
     */
    public final TableField<ShopAccountRecord, Timestamp> BUY_TIME = createField("buy_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "首次续费时间");

    /**
     * The column <code>mini_main.b2c_shop_account.end_time</code>. 到期时间
     */
    public final TableField<ShopAccountRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "到期时间");

    /**
     * The column <code>mini_main.b2c_shop_account.last_login_shop_id</code>. 上次登录店铺ID
     */
    public final TableField<ShopAccountRecord, Integer> LAST_LOGIN_SHOP_ID = createField("last_login_shop_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "上次登录店铺ID");

    /**
     * The column <code>mini_main.b2c_shop_account.mobile</code>. 店铺账户的手机号
     */
    public final TableField<ShopAccountRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR(32).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "店铺账户的手机号");

    /**
     * The column <code>mini_main.b2c_shop_account.account_name</code>. 账户昵称
     */
    public final TableField<ShopAccountRecord, String> ACCOUNT_NAME = createField("account_name", org.jooq.impl.SQLDataType.VARCHAR(32).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "账户昵称");

    /**
     * The column <code>mini_main.b2c_shop_account.shop_avatar</code>. 账户头像
     */
    public final TableField<ShopAccountRecord, String> SHOP_AVATAR = createField("shop_avatar", org.jooq.impl.SQLDataType.VARCHAR(255).defaultValue(org.jooq.impl.DSL.inline("/image/admin/head_icon.png", org.jooq.impl.SQLDataType.VARCHAR)), this, "账户头像");

    /**
     * The column <code>mini_main.b2c_shop_account.company</code>. 公司名称
     */
    public final TableField<ShopAccountRecord, String> COMPANY = createField("company", org.jooq.impl.SQLDataType.VARCHAR(255).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "公司名称");

    /**
     * The column <code>mini_main.b2c_shop_account.salesperson</code>. 销售员
     */
    public final TableField<ShopAccountRecord, String> SALESPERSON = createField("salesperson", org.jooq.impl.SQLDataType.VARCHAR(32).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "销售员");

    /**
     * The column <code>mini_main.b2c_shop_account.province_code</code>. 省
     */
    public final TableField<ShopAccountRecord, String> PROVINCE_CODE = createField("province_code", org.jooq.impl.SQLDataType.VARCHAR(10).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "省");

    /**
     * The column <code>mini_main.b2c_shop_account.city_code</code>. 市
     */
    public final TableField<ShopAccountRecord, String> CITY_CODE = createField("city_code", org.jooq.impl.SQLDataType.VARCHAR(10).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "市");

    /**
     * The column <code>mini_main.b2c_shop_account.district_code</code>. 区
     */
    public final TableField<ShopAccountRecord, String> DISTRICT_CODE = createField("district_code", org.jooq.impl.SQLDataType.VARCHAR(10).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "区");

    /**
     * The column <code>mini_main.b2c_shop_account.address</code>. 详细地址
     */
    public final TableField<ShopAccountRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR(200).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "详细地址");

    /**
     * The column <code>mini_main.b2c_shop_account.base_sale</code>. 初始销量配置开关：0关闭，1开启
     */
    public final TableField<ShopAccountRecord, Byte> BASE_SALE = createField("base_sale", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "初始销量配置开关：0关闭，1开启");

    /**
     * The column <code>mini_main.b2c_shop_account.backlog</code>. 待办事项列表
     */
    public final TableField<ShopAccountRecord, String> BACKLOG = createField("backlog", org.jooq.impl.SQLDataType.CLOB, this, "待办事项列表");

    /**
     * Create a <code>mini_main.b2c_shop_account</code> table reference
     */
    public ShopAccount() {
        this(DSL.name("b2c_shop_account"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_account</code> table reference
     */
    public ShopAccount(String alias) {
        this(DSL.name(alias), SHOP_ACCOUNT);
    }

    /**
     * Create an aliased <code>mini_main.b2c_shop_account</code> table reference
     */
    public ShopAccount(Name alias) {
        this(alias, SHOP_ACCOUNT);
    }

    private ShopAccount(Name alias, Table<ShopAccountRecord> aliased) {
        this(alias, aliased, null);
    }

    private ShopAccount(Name alias, Table<ShopAccountRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> ShopAccount(Table<O> child, ForeignKey<O, ShopAccountRecord> key) {
        super(child, key, SHOP_ACCOUNT);
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
        return Arrays.<Index>asList(Indexes.SHOP_ACCOUNT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ShopAccountRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SHOP_ACCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ShopAccountRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SHOP_ACCOUNT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ShopAccountRecord>> getKeys() {
        return Arrays.<UniqueKey<ShopAccountRecord>>asList(Keys.KEY_B2C_SHOP_ACCOUNT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopAccount as(String alias) {
        return new ShopAccount(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopAccount as(Name alias) {
        return new ShopAccount(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ShopAccount rename(String name) {
        return new ShopAccount(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ShopAccount rename(Name name) {
        return new ShopAccount(name, null);
    }
}

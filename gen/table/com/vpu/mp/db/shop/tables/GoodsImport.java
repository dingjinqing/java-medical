/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.GoodsImportRecord;

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
public class GoodsImport extends TableImpl<GoodsImportRecord> {

    private static final long serialVersionUID = 1994133128;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_goods_import</code>
     */
    public static final GoodsImport GOODS_IMPORT = new GoodsImport();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GoodsImportRecord> getRecordType() {
        return GoodsImportRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_goods_import.id</code>.
     */
    public final TableField<GoodsImportRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_import.total_num</code>. 导入总数
     */
    public final TableField<GoodsImportRecord, Integer> TOTAL_NUM = createField("total_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "导入总数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_import.success_num</code>. 导入成功数
     */
    public final TableField<GoodsImportRecord, Integer> SUCCESS_NUM = createField("success_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "导入成功数");

    /**
     * The column <code>mini_shop_471752.b2c_goods_import.add_time</code>.
     */
    public final TableField<GoodsImportRecord, Timestamp> ADD_TIME = createField("add_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_import.update_time</code>.
     */
    public final TableField<GoodsImportRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_import.del_flag</code>.
     */
    public final TableField<GoodsImportRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_import.del_time</code>.
     */
    public final TableField<GoodsImportRecord, Timestamp> DEL_TIME = createField("del_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>mini_shop_471752.b2c_goods_import.import_file_path</code>. 导入源文件地址
     */
    public final TableField<GoodsImportRecord, String> IMPORT_FILE_PATH = createField("import_file_path", org.jooq.impl.SQLDataType.VARCHAR(120).nullable(false), this, "导入源文件地址");

    /**
     * The column <code>mini_shop_471752.b2c_goods_import.is_update</code>. 是否更新：0新增，1更新
     */
    public final TableField<GoodsImportRecord, Byte> IS_UPDATE = createField("is_update", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否更新：0新增，1更新");

    /**
     * Create a <code>mini_shop_471752.b2c_goods_import</code> table reference
     */
    public GoodsImport() {
        this(DSL.name("b2c_goods_import"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_goods_import</code> table reference
     */
    public GoodsImport(String alias) {
        this(DSL.name(alias), GOODS_IMPORT);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_goods_import</code> table reference
     */
    public GoodsImport(Name alias) {
        this(alias, GOODS_IMPORT);
    }

    private GoodsImport(Name alias, Table<GoodsImportRecord> aliased) {
        this(alias, aliased, null);
    }

    private GoodsImport(Name alias, Table<GoodsImportRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> GoodsImport(Table<O> child, ForeignKey<O, GoodsImportRecord> key) {
        super(child, key, GOODS_IMPORT);
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
        return Arrays.<Index>asList(Indexes.GOODS_IMPORT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<GoodsImportRecord, Integer> getIdentity() {
        return Keys.IDENTITY_GOODS_IMPORT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<GoodsImportRecord> getPrimaryKey() {
        return Keys.KEY_B2C_GOODS_IMPORT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<GoodsImportRecord>> getKeys() {
        return Arrays.<UniqueKey<GoodsImportRecord>>asList(Keys.KEY_B2C_GOODS_IMPORT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsImport as(String alias) {
        return new GoodsImport(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GoodsImport as(Name alias) {
        return new GoodsImport(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GoodsImport rename(String name) {
        return new GoodsImport(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GoodsImport rename(Name name) {
        return new GoodsImport(name, null);
    }
}

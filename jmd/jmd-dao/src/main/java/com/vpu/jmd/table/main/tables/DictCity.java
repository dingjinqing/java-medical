/*
 * This file is generated by jOOQ.
 */
package com.vpu.jmd.table.main.tables;




import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import com.vpu.jmd.table.main.Indexes;
import com.vpu.jmd.table.main.Keys;
import com.vpu.jmd.table.main.MiniMain;
import com.vpu.jmd.table.main.tables.records.DictCityRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class DictCity extends TableImpl<DictCityRecord> {

    private static final long serialVersionUID = 1388067963;

    /**
     * The reference instance of <code>mini_main.b2c_dict_city</code>
     */
    public static final DictCity DICT_CITY = new DictCity();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DictCityRecord> getRecordType() {
        return DictCityRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_dict_city.city_id</code>.
     */
    public final TableField<DictCityRecord, Integer> CITY_ID = createField("city_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_dict_city.name</code>.
     */
    public final TableField<DictCityRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_dict_city.province_id</code>.
     */
    public final TableField<DictCityRecord, Integer> PROVINCE_ID = createField("province_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_dict_city.postcode</code>.
     */
    public final TableField<DictCityRecord, String> POSTCODE = createField("postcode", org.jooq.impl.SQLDataType.VARCHAR(20).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_dict_city.short_name</code>.
     */
    public final TableField<DictCityRecord, String> SHORT_NAME = createField("short_name", org.jooq.impl.SQLDataType.VARCHAR(32).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_dict_city.pinyin</code>.
     */
    public final TableField<DictCityRecord, String> PINYIN = createField("pinyin", org.jooq.impl.SQLDataType.VARCHAR(128).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_dict_city.modified</code>.
     */
    public final TableField<DictCityRecord, Timestamp> MODIFIED = createField("modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>mini_main.b2c_dict_city</code> table reference
     */
    public DictCity() {
        this(DSL.name("b2c_dict_city"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_dict_city</code> table reference
     */
    public DictCity(String alias) {
        this(DSL.name(alias), DICT_CITY);
    }

    /**
     * Create an aliased <code>mini_main.b2c_dict_city</code> table reference
     */
    public DictCity(Name alias) {
        this(alias, DICT_CITY);
    }

    private DictCity(Name alias, Table<DictCityRecord> aliased) {
        this(alias, aliased, null);
    }

    private DictCity(Name alias, Table<DictCityRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> DictCity(Table<O> child, ForeignKey<O, DictCityRecord> key) {
        super(child, key, DICT_CITY);
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
        return Arrays.<Index>asList(Indexes.DICT_CITY_IX_DICT_CITY_MODIFIED, Indexes.DICT_CITY_PRIMARY, Indexes.DICT_CITY_PROVINCE_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DictCityRecord> getPrimaryKey() {
        return Keys.KEY_B2C_DICT_CITY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DictCityRecord>> getKeys() {
        return Arrays.<UniqueKey<DictCityRecord>>asList(Keys.KEY_B2C_DICT_CITY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCity as(String alias) {
        return new DictCity(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCity as(Name alias) {
        return new DictCity(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DictCity rename(String name) {
        return new DictCity(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DictCity rename(Name name) {
        return new DictCity(name, null);
    }
}
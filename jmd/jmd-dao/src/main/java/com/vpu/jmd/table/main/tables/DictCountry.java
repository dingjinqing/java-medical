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
import com.vpu.jmd.table.main.tables.records.DictCountryRecord;
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
public class DictCountry extends TableImpl<DictCountryRecord> {

    private static final long serialVersionUID = 1282908385;

    /**
     * The reference instance of <code>mini_main.b2c_dict_country</code>
     */
    public static final DictCountry DICT_COUNTRY = new DictCountry();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DictCountryRecord> getRecordType() {
        return DictCountryRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_dict_country.country_id</code>.
     */
    public final TableField<DictCountryRecord, Integer> COUNTRY_ID = createField("country_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_dict_country.en_short_name</code>.
     */
    public final TableField<DictCountryRecord, String> EN_SHORT_NAME = createField("en_short_name", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_dict_country.name</code>.
     */
    public final TableField<DictCountryRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_dict_country.short_name</code>.
     */
    public final TableField<DictCountryRecord, String> SHORT_NAME = createField("short_name", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_dict_country.en_name</code>.
     */
    public final TableField<DictCountryRecord, String> EN_NAME = createField("en_name", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false).defaultValue(DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>mini_main.b2c_dict_country.created</code>.
     */
    public final TableField<DictCountryRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>mini_main.b2c_dict_country</code> table reference
     */
    public DictCountry() {
        this(DSL.name("b2c_dict_country"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_dict_country</code> table reference
     */
    public DictCountry(String alias) {
        this(DSL.name(alias), DICT_COUNTRY);
    }

    /**
     * Create an aliased <code>mini_main.b2c_dict_country</code> table reference
     */
    public DictCountry(Name alias) {
        this(alias, DICT_COUNTRY);
    }

    private DictCountry(Name alias, Table<DictCountryRecord> aliased) {
        this(alias, aliased, null);
    }

    private DictCountry(Name alias, Table<DictCountryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> DictCountry(Table<O> child, ForeignKey<O, DictCountryRecord> key) {
        super(child, key, DICT_COUNTRY);
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
        return Arrays.<Index>asList(Indexes.DICT_COUNTRY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DictCountryRecord> getPrimaryKey() {
        return Keys.KEY_B2C_DICT_COUNTRY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DictCountryRecord>> getKeys() {
        return Arrays.<UniqueKey<DictCountryRecord>>asList(Keys.KEY_B2C_DICT_COUNTRY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountry as(String alias) {
        return new DictCountry(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountry as(Name alias) {
        return new DictCountry(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DictCountry rename(String name) {
        return new DictCountry(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DictCountry rename(Name name) {
        return new DictCountry(name, null);
    }
}
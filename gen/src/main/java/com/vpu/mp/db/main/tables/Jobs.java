/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.JobsRecord;

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
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;


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
public class Jobs extends TableImpl<JobsRecord> {

    private static final long serialVersionUID = 2029190321;

    /**
     * The reference instance of <code>mini_main.b2c_jobs</code>
     */
    public static final Jobs JOBS = new Jobs();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JobsRecord> getRecordType() {
        return JobsRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_jobs.id</code>.
     */
    public final TableField<JobsRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_jobs.queue</code>.
     */
    public final TableField<JobsRecord, String> QUEUE = createField("queue", org.jooq.impl.SQLDataType.VARCHAR(191).nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_jobs.payload</code>.
     */
    public final TableField<JobsRecord, String> PAYLOAD = createField("payload", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_jobs.attempts</code>.
     */
    public final TableField<JobsRecord, UByte> ATTEMPTS = createField("attempts", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_jobs.reserved_at</code>.
     */
    public final TableField<JobsRecord, UInteger> RESERVED_AT = createField("reserved_at", org.jooq.impl.SQLDataType.INTEGERUNSIGNED, this, "");

    /**
     * The column <code>mini_main.b2c_jobs.available_at</code>.
     */
    public final TableField<JobsRecord, UInteger> AVAILABLE_AT = createField("available_at", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>mini_main.b2c_jobs.created_at</code>.
     */
    public final TableField<JobsRecord, UInteger> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

    /**
     * Create a <code>mini_main.b2c_jobs</code> table reference
     */
    public Jobs() {
        this(DSL.name("b2c_jobs"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_jobs</code> table reference
     */
    public Jobs(String alias) {
        this(DSL.name(alias), JOBS);
    }

    /**
     * Create an aliased <code>mini_main.b2c_jobs</code> table reference
     */
    public Jobs(Name alias) {
        this(alias, JOBS);
    }

    private Jobs(Name alias, Table<JobsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Jobs(Name alias, Table<JobsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Jobs(Table<O> child, ForeignKey<O, JobsRecord> key) {
        super(child, key, JOBS);
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
        return Arrays.<Index>asList(Indexes.JOBS_JOBS_QUEUE_INDEX, Indexes.JOBS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<JobsRecord, ULong> getIdentity() {
        return Keys.IDENTITY_JOBS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JobsRecord> getPrimaryKey() {
        return Keys.KEY_B2C_JOBS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JobsRecord>> getKeys() {
        return Arrays.<UniqueKey<JobsRecord>>asList(Keys.KEY_B2C_JOBS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Jobs as(String alias) {
        return new Jobs(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Jobs as(Name alias) {
        return new Jobs(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Jobs rename(String name) {
        return new Jobs(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Jobs rename(Name name) {
        return new Jobs(name, null);
    }
}

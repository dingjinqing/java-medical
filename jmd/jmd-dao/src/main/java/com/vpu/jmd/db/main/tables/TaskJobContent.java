/*
 * This file is generated by jOOQ.
 */
package com.vpu.jmd.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.TaskJobContentRecord;

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
public class TaskJobContent extends TableImpl<TaskJobContentRecord> {

    private static final long serialVersionUID = 811661836;

    /**
     * The reference instance of <code>mini_main.b2c_task_job_content</code>
     */
    public static final TaskJobContent TASK_JOB_CONTENT = new TaskJobContent();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TaskJobContentRecord> getRecordType() {
        return TaskJobContentRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_task_job_content.id</code>.
     */
    public final TableField<TaskJobContentRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_task_job_content.content</code>. 消息内容
     */
    public final TableField<TaskJobContentRecord, String> CONTENT = createField("content", org.jooq.impl.SQLDataType.CLOB, this, "消息内容");

    /**
     * The column <code>mini_main.b2c_task_job_content.create_time</code>.
     */
    public final TableField<TaskJobContentRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_main.b2c_task_job_content.update_time</code>. 最后修改时间
     */
    public final TableField<TaskJobContentRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "最后修改时间");

    /**
     * The column <code>mini_main.b2c_task_job_content.del_flag</code>. 删除标识：0未删除，1已删除
     */
    public final TableField<TaskJobContentRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "删除标识：0未删除，1已删除");

    /**
     * Create a <code>mini_main.b2c_task_job_content</code> table reference
     */
    public TaskJobContent() {
        this(DSL.name("b2c_task_job_content"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_task_job_content</code> table reference
     */
    public TaskJobContent(String alias) {
        this(DSL.name(alias), TASK_JOB_CONTENT);
    }

    /**
     * Create an aliased <code>mini_main.b2c_task_job_content</code> table reference
     */
    public TaskJobContent(Name alias) {
        this(alias, TASK_JOB_CONTENT);
    }

    private TaskJobContent(Name alias, Table<TaskJobContentRecord> aliased) {
        this(alias, aliased, null);
    }

    private TaskJobContent(Name alias, Table<TaskJobContentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> TaskJobContent(Table<O> child, ForeignKey<O, TaskJobContentRecord> key) {
        super(child, key, TASK_JOB_CONTENT);
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
        return Arrays.<Index>asList(Indexes.TASK_JOB_CONTENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TaskJobContentRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TASK_JOB_CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TaskJobContentRecord> getPrimaryKey() {
        return Keys.KEY_B2C_TASK_JOB_CONTENT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TaskJobContentRecord>> getKeys() {
        return Arrays.<UniqueKey<TaskJobContentRecord>>asList(Keys.KEY_B2C_TASK_JOB_CONTENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskJobContent as(String alias) {
        return new TaskJobContent(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskJobContent as(Name alias) {
        return new TaskJobContent(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TaskJobContent rename(String name) {
        return new TaskJobContent(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TaskJobContent rename(Name name) {
        return new TaskJobContent(name, null);
    }
}

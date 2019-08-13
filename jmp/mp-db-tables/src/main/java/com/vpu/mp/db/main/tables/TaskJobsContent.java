/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.TaskJobsContentRecord;

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
public class TaskJobsContent extends TableImpl<TaskJobsContentRecord> {

    private static final long serialVersionUID = -995064719;

    /**
     * The reference instance of <code>mini_main.b2c_task_jobs_content</code>
     */
    public static final TaskJobsContent TASK_JOBS_CONTENT = new TaskJobsContent();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TaskJobsContentRecord> getRecordType() {
        return TaskJobsContentRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_task_jobs_content.ID</code>.
     */
    public final TableField<TaskJobsContentRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_task_jobs_content.TASK_ID</code>. taskID
     */
    public final TableField<TaskJobsContentRecord, Integer> TASK_ID = createField("TASK_ID", org.jooq.impl.SQLDataType.INTEGER.defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "taskID");

    /**
     * The column <code>mini_main.b2c_task_jobs_content.CONTENT</code>. 消息内容
     */
    public final TableField<TaskJobsContentRecord, String> CONTENT = createField("CONTENT", org.jooq.impl.SQLDataType.CLOB, this, "消息内容");

    /**
     * The column <code>mini_main.b2c_task_jobs_content.CREATE_TIME</code>.
     */
    public final TableField<TaskJobsContentRecord, Timestamp> CREATE_TIME = createField("CREATE_TIME", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_main.b2c_task_jobs_content.UPDATE_TIME</code>. 最后修改时间
     */
    public final TableField<TaskJobsContentRecord, Timestamp> UPDATE_TIME = createField("UPDATE_TIME", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "最后修改时间");

    /**
     * The column <code>mini_main.b2c_task_jobs_content.DEL_FLAG</code>. 删除标识：0未删除，1已删除
     */
    public final TableField<TaskJobsContentRecord, Byte> DEL_FLAG = createField("DEL_FLAG", org.jooq.impl.SQLDataType.TINYINT.defaultValue(DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "删除标识：0未删除，1已删除");

    /**
     * Create a <code>mini_main.b2c_task_jobs_content</code> table reference
     */
    public TaskJobsContent() {
        this(DSL.name("b2c_task_jobs_content"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_task_jobs_content</code> table reference
     */
    public TaskJobsContent(String alias) {
        this(DSL.name(alias), TASK_JOBS_CONTENT);
    }

    /**
     * Create an aliased <code>mini_main.b2c_task_jobs_content</code> table reference
     */
    public TaskJobsContent(Name alias) {
        this(alias, TASK_JOBS_CONTENT);
    }

    private TaskJobsContent(Name alias, Table<TaskJobsContentRecord> aliased) {
        this(alias, aliased, null);
    }

    private TaskJobsContent(Name alias, Table<TaskJobsContentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> TaskJobsContent(Table<O> child, ForeignKey<O, TaskJobsContentRecord> key) {
        super(child, key, TASK_JOBS_CONTENT);
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
        return Arrays.<Index>asList(Indexes.TASK_JOBS_CONTENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TaskJobsContentRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TASK_JOBS_CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TaskJobsContentRecord> getPrimaryKey() {
        return Keys.KEY_B2C_TASK_JOBS_CONTENT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TaskJobsContentRecord>> getKeys() {
        return Arrays.<UniqueKey<TaskJobsContentRecord>>asList(Keys.KEY_B2C_TASK_JOBS_CONTENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskJobsContent as(String alias) {
        return new TaskJobsContent(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskJobsContent as(Name alias) {
        return new TaskJobsContent(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TaskJobsContent rename(String name) {
        return new TaskJobsContent(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TaskJobsContent rename(Name name) {
        return new TaskJobsContent(name, null);
    }
}

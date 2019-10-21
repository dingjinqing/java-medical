/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.TaskJobMainRecord;

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
public class TaskJobMain extends TableImpl<TaskJobMainRecord> {

    private static final long serialVersionUID = 280955274;

    /**
     * The reference instance of <code>mini_main.b2c_task_job_main</code>
     */
    public static final TaskJobMain TASK_JOB_MAIN = new TaskJobMain();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TaskJobMainRecord> getRecordType() {
        return TaskJobMainRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_task_job_main.id</code>.
     */
    public final TableField<TaskJobMainRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_task_job_main.shop_id</code>. 店铺ID
     */
    public final TableField<TaskJobMainRecord, Integer> SHOP_ID = createField("shop_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "店铺ID");

    /**
     * The column <code>mini_main.b2c_task_job_main.content_id</code>. MQ消息内容ID
     */
    public final TableField<TaskJobMainRecord, Integer> CONTENT_ID = createField("content_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "MQ消息内容ID");

    /**
     * The column <code>mini_main.b2c_task_job_main.status</code>. 任务状态：0待执行,1执行中,2已完成
     */
    public final TableField<TaskJobMainRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "任务状态：0待执行,1执行中,2已完成");

    /**
     * The column <code>mini_main.b2c_task_job_main.progress</code>. 任务进度：0-100
     */
    public final TableField<TaskJobMainRecord, Byte> PROGRESS = createField("progress", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "任务进度：0-100");

    /**
     * The column <code>mini_main.b2c_task_job_main.class_name</code>. 反序列化类名（全称）
     */
    public final TableField<TaskJobMainRecord, String> CLASS_NAME = createField("class_name", org.jooq.impl.SQLDataType.VARCHAR(100).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "反序列化类名（全称）");

    /**
     * The column <code>mini_main.b2c_task_job_main.execution_type</code>. 执行类型:任务类型标识
     */
    public final TableField<TaskJobMainRecord, Integer> EXECUTION_TYPE = createField("execution_type", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "执行类型:任务类型标识");

    /**
     * The column <code>mini_main.b2c_task_job_main.cycle</code>. 轮循间隔(单位:秒)
     */
    public final TableField<TaskJobMainRecord, Integer> CYCLE = createField("cycle", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "轮循间隔(单位:秒)");

    /**
     * The column <code>mini_main.b2c_task_job_main.type</code>. task任务类型(立刻执行；定时执行；循环执行)
     */
    public final TableField<TaskJobMainRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "task任务类型(立刻执行；定时执行；循环执行)");

    /**
     * The column <code>mini_main.b2c_task_job_main.next_execute_time</code>. 下次执行开始日期
     */
    public final TableField<TaskJobMainRecord, Timestamp> NEXT_EXECUTE_TIME = createField("next_execute_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "下次执行开始日期");

    /**
     * The column <code>mini_main.b2c_task_job_main.start_time</code>. 周期开始时间
     */
    public final TableField<TaskJobMainRecord, Timestamp> START_TIME = createField("start_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "周期开始时间");

    /**
     * The column <code>mini_main.b2c_task_job_main.end_time</code>. 周期结束时间
     */
    public final TableField<TaskJobMainRecord, Timestamp> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "周期结束时间");

    /**
     * The column <code>mini_main.b2c_task_job_main.create_time</code>.
     */
    public final TableField<TaskJobMainRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_main.b2c_task_job_main.update_time</code>. 最后修改时间
     */
    public final TableField<TaskJobMainRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "最后修改时间");

    /**
     * The column <code>mini_main.b2c_task_job_main.del_flag</code>. 删除标识：0未删除，1已删除
     */
    public final TableField<TaskJobMainRecord, Byte> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "删除标识：0未删除，1已删除");

    /**
     * Create a <code>mini_main.b2c_task_job_main</code> table reference
     */
    public TaskJobMain() {
        this(DSL.name("b2c_task_job_main"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_task_job_main</code> table reference
     */
    public TaskJobMain(String alias) {
        this(DSL.name(alias), TASK_JOB_MAIN);
    }

    /**
     * Create an aliased <code>mini_main.b2c_task_job_main</code> table reference
     */
    public TaskJobMain(Name alias) {
        this(alias, TASK_JOB_MAIN);
    }

    private TaskJobMain(Name alias, Table<TaskJobMainRecord> aliased) {
        this(alias, aliased, null);
    }

    private TaskJobMain(Name alias, Table<TaskJobMainRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> TaskJobMain(Table<O> child, ForeignKey<O, TaskJobMainRecord> key) {
        super(child, key, TASK_JOB_MAIN);
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
        return Arrays.<Index>asList(Indexes.TASK_JOB_MAIN_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TaskJobMainRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TASK_JOB_MAIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TaskJobMainRecord> getPrimaryKey() {
        return Keys.KEY_B2C_TASK_JOB_MAIN_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TaskJobMainRecord>> getKeys() {
        return Arrays.<UniqueKey<TaskJobMainRecord>>asList(Keys.KEY_B2C_TASK_JOB_MAIN_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskJobMain as(String alias) {
        return new TaskJobMain(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskJobMain as(Name alias) {
        return new TaskJobMain(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TaskJobMain rename(String name) {
        return new TaskJobMain(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TaskJobMain rename(Name name) {
        return new TaskJobMain(name, null);
    }
}

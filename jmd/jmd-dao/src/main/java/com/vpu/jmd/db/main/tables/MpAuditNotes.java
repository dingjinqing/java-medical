/*
 * This file is generated by jOOQ.
 */
package com.vpu.jmd.db.main.tables;


import com.vpu.mp.db.main.Indexes;
import com.vpu.mp.db.main.Keys;
import com.vpu.mp.db.main.MiniMain;
import com.vpu.mp.db.main.tables.records.MpAuditNotesRecord;

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
public class MpAuditNotes extends TableImpl<MpAuditNotesRecord> {

    private static final long serialVersionUID = 1061541729;

    /**
     * The reference instance of <code>mini_main.b2c_mp_audit_notes</code>
     */
    public static final MpAuditNotes MP_AUDIT_NOTES = new MpAuditNotes();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MpAuditNotesRecord> getRecordType() {
        return MpAuditNotesRecord.class;
    }

    /**
     * The column <code>mini_main.b2c_mp_audit_notes.id</code>.
     */
    public final TableField<MpAuditNotesRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>mini_main.b2c_mp_audit_notes.mp_audit_id</code>. 审核单ID
     */
    public final TableField<MpAuditNotesRecord, Integer> MP_AUDIT_ID = createField("mp_audit_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "审核单ID");

    /**
     * The column <code>mini_main.b2c_mp_audit_notes.operate_status</code>. 0: 待处理 1：关闭 2：拒绝 3：忽略 4：运营审核通过 5：微信审核中 6：微信审核成功 7：微信审核失败 8:备注
     */
    public final TableField<MpAuditNotesRecord, Byte> OPERATE_STATUS = createField("operate_status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0: 待处理 1：关闭 2：拒绝 3：忽略 4：运营审核通过 5：微信审核中 6：微信审核成功 7：微信审核失败 8:备注");

    /**
     * The column <code>mini_main.b2c_mp_audit_notes.remark</code>. 备注
     */
    public final TableField<MpAuditNotesRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.CLOB, this, "备注");

    /**
     * The column <code>mini_main.b2c_mp_audit_notes.create_time</code>. 记录时间
     */
    public final TableField<MpAuditNotesRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "记录时间");

    /**
     * The column <code>mini_main.b2c_mp_audit_notes.account_id</code>. 审核人id
     */
    public final TableField<MpAuditNotesRecord, Integer> ACCOUNT_ID = createField("account_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "审核人id");

    /**
     * Create a <code>mini_main.b2c_mp_audit_notes</code> table reference
     */
    public MpAuditNotes() {
        this(DSL.name("b2c_mp_audit_notes"), null);
    }

    /**
     * Create an aliased <code>mini_main.b2c_mp_audit_notes</code> table reference
     */
    public MpAuditNotes(String alias) {
        this(DSL.name(alias), MP_AUDIT_NOTES);
    }

    /**
     * Create an aliased <code>mini_main.b2c_mp_audit_notes</code> table reference
     */
    public MpAuditNotes(Name alias) {
        this(alias, MP_AUDIT_NOTES);
    }

    private MpAuditNotes(Name alias, Table<MpAuditNotesRecord> aliased) {
        this(alias, aliased, null);
    }

    private MpAuditNotes(Name alias, Table<MpAuditNotesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MpAuditNotes(Table<O> child, ForeignKey<O, MpAuditNotesRecord> key) {
        super(child, key, MP_AUDIT_NOTES);
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
        return Arrays.<Index>asList(Indexes.MP_AUDIT_NOTES_MP_AUDIT_ID, Indexes.MP_AUDIT_NOTES_OPERATE_STATUS, Indexes.MP_AUDIT_NOTES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MpAuditNotesRecord, Integer> getIdentity() {
        return Keys.IDENTITY_MP_AUDIT_NOTES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MpAuditNotesRecord> getPrimaryKey() {
        return Keys.KEY_B2C_MP_AUDIT_NOTES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MpAuditNotesRecord>> getKeys() {
        return Arrays.<UniqueKey<MpAuditNotesRecord>>asList(Keys.KEY_B2C_MP_AUDIT_NOTES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MpAuditNotes as(String alias) {
        return new MpAuditNotes(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MpAuditNotes as(Name alias) {
        return new MpAuditNotes(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MpAuditNotes rename(String name) {
        return new MpAuditNotes(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MpAuditNotes rename(Name name) {
        return new MpAuditNotes(name, null);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.shop.tables;


import com.vpu.mp.db.shop.Indexes;
import com.vpu.mp.db.shop.Keys;
import com.vpu.mp.db.shop.MiniShop_471752;
import com.vpu.mp.db.shop.tables.records.ServiceTechnicianGroupRecord;

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
public class ServiceTechnicianGroup extends TableImpl<ServiceTechnicianGroupRecord> {

    private static final long serialVersionUID = 77465801;

    /**
     * The reference instance of <code>mini_shop_471752.b2c_service_technician_group</code>
     */
    public static final ServiceTechnicianGroup SERVICE_TECHNICIAN_GROUP = new ServiceTechnicianGroup();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ServiceTechnicianGroupRecord> getRecordType() {
        return ServiceTechnicianGroupRecord.class;
    }

    /**
     * The column <code>mini_shop_471752.b2c_service_technician_group.group_id</code>. 技师分组
     */
    public final TableField<ServiceTechnicianGroupRecord, Integer> GROUP_ID = createField("group_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "技师分组");

    /**
     * The column <code>mini_shop_471752.b2c_service_technician_group.group_name</code>. 分组名称
     */
    public final TableField<ServiceTechnicianGroupRecord, String> GROUP_NAME = createField("group_name", org.jooq.impl.SQLDataType.VARCHAR(90).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "分组名称");

    /**
     * The column <code>mini_shop_471752.b2c_service_technician_group.store_id</code>. 门店id
     */
    public final TableField<ServiceTechnicianGroupRecord, Integer> STORE_ID = createField("store_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "门店id");

    /**
     * The column <code>mini_shop_471752.b2c_service_technician_group.create_time</code>.
     */
    public final TableField<ServiceTechnicianGroupRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>mini_shop_471752.b2c_service_technician_group.update_time</code>. 最后修改时间
     */
    public final TableField<ServiceTechnicianGroupRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "最后修改时间");

    /**
     * The column <code>mini_shop_471752.b2c_service_technician_group.del_flag</code>. 0使用，1删除
     */
    public final TableField<ServiceTechnicianGroupRecord, Short> DEL_FLAG = createField("del_flag", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "0使用，1删除");

    /**
     * Create a <code>mini_shop_471752.b2c_service_technician_group</code> table reference
     */
    public ServiceTechnicianGroup() {
        this(DSL.name("b2c_service_technician_group"), null);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_service_technician_group</code> table reference
     */
    public ServiceTechnicianGroup(String alias) {
        this(DSL.name(alias), SERVICE_TECHNICIAN_GROUP);
    }

    /**
     * Create an aliased <code>mini_shop_471752.b2c_service_technician_group</code> table reference
     */
    public ServiceTechnicianGroup(Name alias) {
        this(alias, SERVICE_TECHNICIAN_GROUP);
    }

    private ServiceTechnicianGroup(Name alias, Table<ServiceTechnicianGroupRecord> aliased) {
        this(alias, aliased, null);
    }

    private ServiceTechnicianGroup(Name alias, Table<ServiceTechnicianGroupRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> ServiceTechnicianGroup(Table<O> child, ForeignKey<O, ServiceTechnicianGroupRecord> key) {
        super(child, key, SERVICE_TECHNICIAN_GROUP);
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
        return Arrays.<Index>asList(Indexes.SERVICE_TECHNICIAN_GROUP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ServiceTechnicianGroupRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SERVICE_TECHNICIAN_GROUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ServiceTechnicianGroupRecord> getPrimaryKey() {
        return Keys.KEY_B2C_SERVICE_TECHNICIAN_GROUP_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ServiceTechnicianGroupRecord>> getKeys() {
        return Arrays.<UniqueKey<ServiceTechnicianGroupRecord>>asList(Keys.KEY_B2C_SERVICE_TECHNICIAN_GROUP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceTechnicianGroup as(String alias) {
        return new ServiceTechnicianGroup(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceTechnicianGroup as(Name alias) {
        return new ServiceTechnicianGroup(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ServiceTechnicianGroup rename(String name) {
        return new ServiceTechnicianGroup(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ServiceTechnicianGroup rename(Name name) {
        return new ServiceTechnicianGroup(name, null);
    }
}

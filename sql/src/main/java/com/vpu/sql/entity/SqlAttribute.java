package com.vpu.sql.entity;

import com.vpu.sql.constant.DbOperator;

/**
 * @author luguangyao
 */
public class SqlAttribute {

    private String tableName;


    private DbOperator dbOperator;



    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DbOperator getDbOperator() {
        return dbOperator;
    }

    public void setDbOperator(DbOperator dbOperator) {
        this.dbOperator = dbOperator;
    }


}

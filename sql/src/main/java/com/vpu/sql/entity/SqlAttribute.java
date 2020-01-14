package com.vpu.sql.entity;

import com.vpu.sql.constant.DBAlterOperator;
import com.vpu.sql.constant.DBOperator;

public class SqlAttribute {

    private String tableName;


    private DBOperator dbOperator;



    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DBOperator getDbOperator() {
        return dbOperator;
    }

    public void setDbOperator(DBOperator dbOperator) {
        this.dbOperator = dbOperator;
    }


}

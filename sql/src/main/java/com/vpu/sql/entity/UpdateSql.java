package com.vpu.sql.entity;

import com.vpu.sql.constant.DbOperator;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author luguangyao
 */
public class UpdateSql {


    public UpdateSql(String tableName){
        this.tableName = tableName;
    }
    public UpdateSql(){
    }

    /**
     * 表的名称
     */
    private String tableName;

    /**
     * 相关的执行sql
     */
    private LinkedList<String> sql;

    /**
     * 涉及到的字段以及操作类型
     */
    private Map<String, DbOperator> sqlOperatorMap;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LinkedList<String> getSql() {
        return sql;
    }

    public void setSql(LinkedList<String> sql) {
        this.sql = sql;
    }

    public Map<String, DbOperator> getSqlOperatorMap() {
        return sqlOperatorMap;
    }

    public void setSqlOperatorMap(Map<String, DbOperator> sqlOperatorMap) {
        this.sqlOperatorMap = sqlOperatorMap;
    }
}

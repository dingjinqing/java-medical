package com.vpu.sql.util;

import com.vpu.sql.constant.DBOperator;
import com.vpu.sql.entity.SqlAttribute;
import com.vpu.sql.entity.UpdateSql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {


    /**
     * create sql 的tableName的正则匹配
     */
    private static final Pattern CREATE_PATTERN =
            Pattern.compile("(?<=create table).*?(?=\\()",Pattern.CASE_INSENSITIVE);
    /**
     * update sql 的tableName的正则匹配
     */
    private static final Pattern UPDATE_PATTERN =
            Pattern.compile("(?<=`).*?(?=`)",Pattern.CASE_INSENSITIVE);
    /**
     * init data sql 的tableName的正则匹配
     */
    private static final Pattern DATA_PATTERN =
            Pattern.compile("(?<=`).*?(?=`\\()",Pattern.CASE_INSENSITIVE);

    /**
     * 字段重复的exception的正则匹配
     */
    private static final Pattern ERROR_COLUMN_DUPLICATE =
            Pattern.compile("(?<=Duplicate column name).*?(?=;)",Pattern.CASE_INSENSITIVE);

    /**
     * 索引重复的exception的正则匹配
     */
    private static final Pattern ERROR_INDEX_DUPLICATE =
            Pattern.compile("(?<=index).*?(?=already exists;)",Pattern.CASE_INSENSITIVE);

    /**
     * 根据create table的sql获取到创建的table的名称
     * @param sql create table的sql
     * @return table name
     */
    public static String getTableName(String sql){
        String result = "";

        Matcher m = CREATE_PATTERN.matcher(sql);
        while (m.find()){
            result = m.group().replaceAll("`","").trim();
        }
        return result;
    }
    /**
     * 根据error message获取到重复的字段名称
     * @param errorMsg error message
     * @return column Name
     */
    public static String getDuplicateColumn(String errorMsg){
        String result = "";

        Matcher m = ERROR_COLUMN_DUPLICATE.matcher(errorMsg);
        while (m.find()){
            result = m.group().replaceAll("\"","").trim();
        }
        return result;
    }
    /**
     * 根据error message获取到重复的索引名称
     * @param errorMsg error message
     * @return index name
     */
    public static String getDuplicateIndex(String errorMsg){
        String result = "";

        Matcher m = ERROR_INDEX_DUPLICATE.matcher(errorMsg);
        while (m.find()){
            result = m.group().replaceAll("\"","").trim();
        }
        return result;
    }

    public static SqlAttribute getTableNameByUpdateSql(String sql){
        SqlAttribute sqlAttribute = new SqlAttribute();
        sql = sql.toLowerCase().replaceAll(" +"," ").replaceAll("`","").trim();
        String[] sqlArray = sql.split(" ");
        if(DBOperator.DROP.getOperator().equals(sqlArray[0]) ){
            throw new RuntimeException("update sql file can't execute drop table");
        }
        sqlAttribute.setDbOperator(DBOperator.getDBOperator(sqlArray[0]));
        sqlAttribute.setTableName(sqlArray[2]);
        return sqlAttribute;
    }

    public static SqlAttribute getTableNameByDataSql(String sql){
        SqlAttribute sqlAttribute = new SqlAttribute();
        String tableName = "";
        Matcher m = DATA_PATTERN.matcher(sql.replaceAll(" ",""));
        if( m.find() ){
            tableName =  m.group();
        }
        sql = sql.toLowerCase().replaceAll(" +"," ").replaceAll("`","").trim();
        String[] sqlArray = sql.split(" ");

        if( DBOperator.UPDATE.getOperator().equals(sqlArray[0]) ){
            sqlAttribute.setTableName(sqlArray[1].replaceAll("\\(",""));
        }else{
            sqlAttribute.setTableName(tableName);
        }
        sqlAttribute.setDbOperator(DBOperator.getDBOperator(sqlArray[0]));

        return sqlAttribute;
    }
}

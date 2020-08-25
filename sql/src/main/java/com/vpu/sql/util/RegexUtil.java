package com.vpu.sql.util;

import com.google.common.collect.Lists;
import com.vpu.sql.constant.DbOperator;
import com.vpu.sql.entity.SqlAttribute;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author luguangyao
 */
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
     * 根据数据库名称获取到当前数据库对应的shopId
     */
    private static final Pattern NUMBER =
            Pattern.compile("(?=[0-9]).*?(?<=[0-9]$)",Pattern.CASE_INSENSITIVE);

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
        if(DbOperator.DROP.getOperator().equals(sqlArray[0]) ){
            throw new RuntimeException("update sql file can't execute drop table");
        }
        DbOperator dbOperator = DbOperator.getDBOperator(sqlArray[0]);
        String tableName = "";
        if(Objects.equals(dbOperator, DbOperator.CREATE)){
            tableName = sqlArray[5];
        }else if( Objects.equals(dbOperator, DbOperator.ALTER)  ){
            tableName = sqlArray[2];
        }else if( Objects.equals(dbOperator, DbOperator.INSERT) ){
            tableName = sqlArray[3];
        }
        sqlAttribute.setDbOperator(dbOperator);
        sqlAttribute.setTableName(tableName);
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

        if( DbOperator.UPDATE.getOperator().equals(sqlArray[0]) ){
            sqlAttribute.setTableName(sqlArray[1].replaceAll("\\(",""));
        }else{
            sqlAttribute.setTableName(tableName);
        }
        sqlAttribute.setDbOperator(DbOperator.getDBOperator(sqlArray[0]));

        return sqlAttribute;
    }

    public static String getCompressionSql(String old){
        return old.replaceAll(" ","").replaceAll("`","").toLowerCase();
    }

    public static boolean isChangColumnException(String sql,String errorMsg){
        return isChangColumnException(errorMsg) && isChangColumnSql(sql);
    }
    private static boolean isChangColumnException(String errorMsg){
        String msg = errorMsg.toLowerCase().replaceAll(" ","");
        return msg.indexOf("unknowncolumn") == 0;
    }
    private static boolean isChangColumnSql(String sql){
        String changeName = "change";
        String columnName = "column";
        String[] sqlArrays = sql.toLowerCase().split(" ");
        List<String> list = Lists.newArrayList(sqlArrays);
        if( list.contains(changeName) ){
            int columnIndex = list.indexOf(changeName)+1;
            if( columnName.equals(list.get(columnIndex)) ){
                return !list.get(columnIndex + 2).equals(list.get(columnIndex + 3));
            }
        }
        return false;
    }

    /**
     * 根据数据库名称获取店铺的ID
     * @param dbName 数据库名称
     * @return shopID
     */
    public static int getShopIdByTableName(String dbName){
        int  result = 0;

        Matcher m = NUMBER.matcher(dbName);
        while (m.find()){
            result = Integer.parseInt(m.group());
        }
        return result;
    }
}

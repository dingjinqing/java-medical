package com.vpu.sql.util;

import com.vpu.sql.exception.DuplicateColumnException;
import com.vpu.sql.exception.DuplicateIndexException;
import com.vpu.sql.exception.SQLRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DBUtil {


    private static final String DELETE_TABLE_SQL = "drop table if exists ";

    public static volatile AtomicInteger errorNumbers = new AtomicInteger(0);

    public static volatile AtomicInteger executeNumbers = new AtomicInteger(0);

    public static volatile AtomicInteger changeColumnNumbers = new AtomicInteger(0);




    /**
     * 执行sql
     * @param con 连接
     * @param sql sql
     */
    public static void executeSQL(Connection con, String sql){
        try {
            con.setAutoCommit(false);
            con.prepareStatement(sql).executeUpdate();
            con.commit();
            executeNumbers.getAndIncrement();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();

            }
            String column = RegexUtil.getDuplicateColumn(e.getMessage()+";");
            String index = RegexUtil.getDuplicateIndex(e.getMessage());
            if( !StringUtils.isEmpty(column) ){
                throw new DuplicateColumnException(column);
            }else if( !StringUtils.isEmpty(index) ){
                throw new DuplicateIndexException(index);
            }else if( RegexUtil.isChangColumnException(sql,e.getMessage()) ){
                changeColumnNumbers.getAndIncrement();
                log.warn("重复执行的修改表字段的sql-->{}",sql);
            }else{
                errorNumbers.getAndIncrement();
                throw new SQLRunTimeException(e.getMessage());

            }

        }
    }
    /**
     * 执行sql
     * @param con 连接
     * @param sql sql
     */
    public static void realExecuteSQL(Connection con, String sql){
        try {
            con.setAutoCommit(false);
            con.prepareStatement(sql).executeUpdate();
            con.commit();
            executeNumbers.getAndIncrement();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();

            }
            String column = RegexUtil.getDuplicateColumn(e.getMessage()+";");
            String index = RegexUtil.getDuplicateIndex(e.getMessage());
            if( !StringUtils.isEmpty(column) ){
                log.debug("列已存在");
            }else if( !StringUtils.isEmpty(index) ){
                log.debug("索引已存在");
            }else if( RegexUtil.isChangColumnException(sql,e.getMessage()) ){
                changeColumnNumbers.getAndIncrement();
                log.warn("重复执行的修改表字段的sql-->{}",sql);
            }else if("Multiple primary key defined".equals(e.getMessage().trim())){

            }else{
                errorNumbers.getAndIncrement();
                throw new SQLRunTimeException(e.getMessage());

            }

        }
    }
    /**
     * 执行sql
     * @param con 连接
     * @param createTableSql sql
     */
    public static void executeSQL(Connection con, String createTableSql, List<String> updateSql){
        try {

            con.prepareStatement(createTableSql).execute();
            repeatExecute(con,updateSql);
        } catch (SQLException e) {
            e.printStackTrace();
            errorNumbers.getAndIncrement();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private static void repeatExecute(Connection con,List<String> sql){
        sql.forEach(x-> {
            try {
                log.info("执行update sql---{}",x);
                con.prepareStatement(x);
            } catch (SQLException e) {
                e.printStackTrace();
                errorNumbers.getAndIncrement();
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * 执行查询sql并打印查询字段数据
     * @param con 连接
     * @param sql sql
     * @param filedName 打印值的字段名
     */
    public static void executeQuerySQL(Connection con, String sql,String... filedName){
        try {
            log.info("执行sql---{}",sql);
            ResultSet resultSet = con.prepareStatement(sql).executeQuery();
            while (resultSet.next()){
                for (int i = 0; i <filedName.length ; i++) {
                    System.out.println(resultSet.getString(filedName[i]));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorNumbers.getAndIncrement();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void deleteTable(Connection con, String tableName){

        try {
            con.prepareStatement(DELETE_TABLE_SQL+tableName).execute();
        } catch (SQLException e) {
            e.printStackTrace();
            errorNumbers.getAndIncrement();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void executeSQLFile(Connection con, String path){
        List<String> sqlList = FileUtil.readSqlFile(path);
        sqlList.forEach(x-> executeSQL(con,x));
    }
    public static void executeSQLFileByJar(Connection con, String path){
        List<String> sqlList = FileUtil.readSqlFileByJar(path);
        sqlList.forEach(x-> executeSQL(con,x));
    }
}

package com.vpu.sql.constant;

public class SqlTemplate {


    public static final String GET_EXECUTED_SQL = "select * from %s where sql_md5 = ? and shop_id = ?";


    public static final String INSERT_HISTORY_SQL = "insert into %s(`id`,`shop_id`,`sql_md5`,`sql_detail`) values(?,?,?,?);";
}

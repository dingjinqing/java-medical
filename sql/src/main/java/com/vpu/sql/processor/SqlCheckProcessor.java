package com.vpu.sql.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vpu.sql.config.H2DataSource;
import com.vpu.sql.constant.DBOperator;
import com.vpu.sql.entity.SqlAttribute;
import com.vpu.sql.entity.UpdateSql;
import com.vpu.sql.exception.DuplicateColumnException;
import com.vpu.sql.exception.DuplicateIndexException;
import com.vpu.sql.util.DBUtil;
import com.vpu.sql.util.FileUtil;
import com.vpu.sql.util.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * 对初始化的sql进行校验
 * @author 卢光耀
 * @date 2020-01-02 16:02
 *
*/
@Component
@Slf4j
public class SqlCheckProcessor implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private final H2DataSource h2DataSource;

    public SqlCheckProcessor(H2DataSource h2DataSource) {
        this.h2DataSource = h2DataSource;
    }

    private static final String SHOP_TABLE_INIT_FILE = "/shop/db_shop_init.sql";

    private static final String SHOP_TABLE_UPDATE_FILE = "/shop/shop_update.sql";

    private static final String SHOP_DATA_INIT_FILE= "/shop/db_shop_data.sql";

    private static final String MAIN_TABLE_INIT_FILE = "/main/db_main.sql";

    private static final String MAIN_TABLE_UPDATE_FILE = "/main/main_update.sql";

    private static final String MAIN_DATA_INIT_FILE= "/main/db_main_data.sql";


    //测试专用
    private static final String TEST_INIT = "/test/test_init.sql";

    private static final String TEST_UPDATE = "/test/test_update.sql";




    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if( System.getProperty("check") == null ){
            return ;
        }
        String path = System.getProperty("checkPath");
        if( StringUtils.isEmpty(path) ){
            throw  new NullPointerException("can't find checkPath");
        }
        checkMain(path);

        checkShop(path);
        log.info("本次检查共有{}处error",DBUtil.errorNumbers.intValue());
    }

    /**
     * 店铺相关的sql校验
     * @param generalPath 通用的sql文件目录
     */
    private void checkShop(String generalPath) {
        List<String> createSqlList = FileUtil.readSqlFile(generalPath+SHOP_TABLE_INIT_FILE);
        List<String> updateSqlList = FileUtil.readSqlFile(generalPath+SHOP_TABLE_UPDATE_FILE);
        Map<String,String> shopTableMap = assemblyTableSql(createSqlList);

        Map<String,UpdateSql> shopUpdateMap = assemblyUpdateSqlMap(updateSqlList);
        try(Connection con = h2DataSource.getShopDataSource().getConnection()) {
            checkTable(shopTableMap,con);

            checkUpdateSql(shopTableMap,shopUpdateMap,con);
        } catch (SQLException e) {
            e.setNextException(new SQLException(e.getMessage(),e.getSQLState(),2));
        }
    }

    /**
     * 封装处理updateSQL,同一张表的sql放在一起
     * @param updateSqlList Files.readAllLine()返回的List
     * @return TableName --> update SQL List
     */
    private Map<String, UpdateSql> assemblyUpdateSqlMap(List<String> updateSqlList) {
        Map<String,UpdateSql> resultMap = Maps.newHashMap();
        for( String sql: updateSqlList ){
            SqlAttribute sqlAttribute = RegexUtil.getTableNameByUpdateSql(sql);
            String tableName = sqlAttribute.getTableName();
            UpdateSql updateSql;
            if ( resultMap.containsKey(tableName) ){
                updateSql = resultMap.get(tableName);
                updateSql.getSqlOperatorMap().put(sql,sqlAttribute.getDbOperator());
            }else{
                updateSql = new UpdateSql(tableName);
                LinkedList<String> sqlList = Lists.newLinkedList();
                sqlList.add(sql);
                updateSql.setSql(sqlList);
                Map<String, DBOperator> sqlOperatorMap = Maps.newLinkedHashMap();
                sqlOperatorMap.put(sql,sqlAttribute.getDbOperator());
                updateSql.setSqlOperatorMap(sqlOperatorMap);
            }
            resultMap.put(tableName,updateSql);
        }
        return resultMap;
    }

    /**
     * 主库的sql校验
     * @param generalPath 通用的sql文件目录
     */
    private void checkMain(String generalPath){
        List<String> createSqlList = FileUtil.readSqlFile(generalPath+MAIN_TABLE_INIT_FILE);

        List<String> updateSqlList = FileUtil.readSqlFile(generalPath+MAIN_TABLE_UPDATE_FILE);

        Map<String, UpdateSql> updateSqlMap = assemblyUpdateSqlMap(updateSqlList);
        Map<String,String> mainTableMap = assemblyTableSql(createSqlList);
        try(Connection con = h2DataSource.getMainDataSource().getConnection()) {
            checkTable(mainTableMap,con);

            checkUpdateSql(mainTableMap,updateSqlMap,con);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * 封装处理create SQL
     * @param sqlList Files.readAllLine()返回的List
     * @return TableName --> create SQL
     */
    private Map<String,String> assemblyTableSql(List<String> sqlList){
        Map<String,String>  resultMap = Maps.newHashMap();
        for( String sql: sqlList ){
            String tableName = RegexUtil.getTableName(sql);
            resultMap.put(tableName,sql);
        }
        return resultMap;
    }

    /**
     * 校验创建table的sql
     * @param sqlMap all create sql
     * @param con 和数据库的连接
     */
    private void checkTable(Map<String,String> sqlMap,Connection con){
        log.info("检查根sql文件的createTable的sql...");
        for(Map.Entry<String,String> entry : sqlMap.entrySet() ){
            log.info("检查【{}】的create SQL--->{}",entry.getKey(),entry.getValue());
            DBUtil.executeSQL(con,entry.getValue());
            if(entry.getValue().toUpperCase().indexOf("DROP")!=0) {
                DBUtil.deleteTable(con, entry.getKey());
            }
        }
    }

    /**
     * 校验update sql
     * @param sqlMap 创建table的sql
     * @param updateSqlMap all update sql
     * @param con 和数据库的连接
     */
    private void checkUpdateSql(Map<String,String> sqlMap,Map<String,UpdateSql> updateSqlMap,Connection con){
        StringBuilder duplicateError = new StringBuilder("\n");
        for(Map.Entry<String,UpdateSql> entry : updateSqlMap.entrySet() ){
            UpdateSql updateSql = entry.getValue();
            boolean existTable =  false;
            log.info("开始对表【{}】的update语句进行校验...",updateSql.getTableName());


            for (Map.Entry<String,DBOperator> sqlEntry: updateSql.getSqlOperatorMap().entrySet()){
                /*如果不是在update.sql里创建的table，那么需要去根sql文件里获取创建table的sql并执行*/
                if( !existTable && !sqlEntry.getValue().equals(DBOperator.CREATE) ){
                    /*校验updateSQL首先需要创建对应的table*/
                    if( sqlMap.containsKey(updateSql.getTableName()) ){
                        log.info("表【{}】的创建语句在update中不存在，在根sql文件里获取创建语句...",updateSql.getTableName());
                        DBUtil.executeSQL(con,sqlMap.get(entry.getKey()));
                        existTable = true;
                    }else{
                        log.error("表【{}】的创建语句在update中不存在，在根sql文件里也未找到...",updateSql.getTableName());
                    }
                }
                if( sqlEntry.getValue().equals(DBOperator.CREATE) ){
                    existTable = true;
                }
                log.info("执行【{}】update的语句--->\n{}",sqlEntry.getValue().getOperator(),sqlEntry.getKey());
                try{
                    DBUtil.executeSQL(con,sqlEntry.getKey());
                }catch (DuplicateColumnException e){
                    duplicateError.append("表【").
                            append(updateSql.getTableName()).
                            append("】的【").
                            append(e.getMessage()).
                            append("】字段已存在").
                            append("\n");
                }catch(DuplicateIndexException e){
                    duplicateError.append("表【").
                            append(updateSql.getTableName()).
                            append("】的【").
                            append(e.getMessage()).
                            append("】索引已存在").
                            append("\n");
                }

            }
            DBUtil.deleteTable(con, updateSql.getTableName());

            log.info("表【{}】的update语句校验成功...",updateSql.getTableName());
        }
        if( !StringUtils.isEmpty(duplicateError.toString().replaceAll("\n","")) ){
            log.error(duplicateError.toString());
        }
    }

    /**
     * 执行顺序
     * @return 最先判断
     */
    @Override
    public int getOrder() {
        return 0;
    }

}

package com.vpu.sql.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vpu.sql.config.H2DataSource;
import com.vpu.sql.constant.DbOperator;
import com.vpu.sql.entity.SqlAttribute;
import com.vpu.sql.entity.UpdateSql;
import com.vpu.sql.exception.DuplicateColumnException;
import com.vpu.sql.exception.DuplicateIndexException;
import com.vpu.sql.exception.SQLRunTimeException;
import com.vpu.sql.template.SQLErrorMessageTemplate;
import com.vpu.sql.util.DbUtil;
import com.vpu.sql.util.FileUtil;
import com.vpu.sql.util.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
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

    private static final String SHOP_TABLE_FILE = "/shop/db_shop.sql";

    private static final String SHOP_TABLE_UPDATE_FILE = "/shop/shop_update.sql";

    private static final String SHOP_DATA_INIT_FILE= "/shop/db_shop_data.sql";

    private static final String MAIN_TABLE_FILE = "/main/db_main.sql";

    private static final String MAIN_TABLE_UPDATE_FILE = "/main/main_update.sql";

    private static final String MAIN_DATA_INIT_FILE= "/main/db_main_data.sql";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        String checkName = "check";
        String checkPathName = "checkPath";
        if( System.getProperty(checkName) == null ){
            return ;
        }
        String path = System.getProperty(checkPathName);
        if( StringUtils.isEmpty(path) ){
            throw  new NullPointerException("can't find checkPath");
        }
        checkMain(path);

        checkShop(path);
        log.info("本次检查共有{}处error", DbUtil.errorNumbers.intValue());
    }

    /**
     * 主库的sql校验
     * @param generalPath 通用的sql文件目录
     */
    private void checkMain(String generalPath){
        // 读取对应的sql语句，已经经多行的sql转换为单行
        List<String> createSqlList = FileUtil.readSqlFile(generalPath+ MAIN_TABLE_FILE);
        List<String> updateSqlList = FileUtil.readSqlFile(generalPath+MAIN_TABLE_UPDATE_FILE);
        List<String> dataSqlList = FileUtil.readSqlFile(generalPath+MAIN_DATA_INIT_FILE);

        // 获取表名称和对应create语句的映射
        Map<String,String> mainTableMap = assemblyTableSql(createSqlList);
        // 获取表名和对应表修改语句集合的映射
        Map<String, UpdateSql> updateSqlMap = assemblyUpdateSqlMap(updateSqlList,dataSqlList);

        try(Connection con = h2DataSource.getMainDataSource().getConnection()) {
            log.info("开始检查【db_main.sql】...");
            // 将所有语句在h2内执行一遍，查看是否存在异常
            checkTable(mainTableMap,con);
            log.info("【db_main.sql】检查结束");
            log.info("开始检查【db_main_data.sql】和【main_update.sql】...");
            checkUpdateSql(mainTableMap,updateSqlMap,con);
            log.info("【db_main_data.sql】和【main_update.sql】检查结束");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * 店铺相关的sql校验
     * @param generalPath 通用的sql文件目录
     */
    private void checkShop(String generalPath) {
        List<String> createSqlList = FileUtil.readSqlFile(generalPath+ SHOP_TABLE_FILE);
        List<String> updateSqlList = FileUtil.readSqlFile(generalPath+SHOP_TABLE_UPDATE_FILE);
        List<String> dataSqlList = FileUtil.readSqlFile(generalPath+SHOP_DATA_INIT_FILE);

        Map<String,String> shopTableMap = assemblyTableSql(createSqlList);
        Map<String, UpdateSql> shopUpdateMap = assemblyUpdateSqlMap(updateSqlList,dataSqlList);

        try(Connection con = h2DataSource.getShopDataSource().getConnection()) {
            log.info("开始检查【db_shop.sql】...");
            checkTable(shopTableMap,con);
            log.info("【db_shop.sql】检查结束");
            log.info("开始检查【db_shop_data.sql】和【shop_update.sql】...");
            checkUpdateSql(shopTableMap,shopUpdateMap,con);
            log.info("【db_shop_data.sql】和【main_shop.sql】检查结束");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 封装处理updateSQL,同一张表的sql放在一起
     * @param updateSqlList Files.readAllLine()返回的List
     * @return TableName --> update SQL List
     */
    private Map<String, UpdateSql> assemblyUpdateSqlMap(List<String> updateSqlList, List<String> dataSqlList) {
        Map<String, UpdateSql> resultMap = Maps.newHashMap();
        for( String sql: updateSqlList ){
            SqlAttribute sqlAttribute = RegexUtil.getTableNameByUpdateSql(sql);
            assemblyUpdateSqlDataMap(resultMap,sqlAttribute,sql);
        }
        for(String sql:dataSqlList){
            SqlAttribute sqlAttribute = RegexUtil.getTableNameByDataSql(sql);
            assemblyUpdateSqlDataMap(resultMap,sqlAttribute,sql);
        }
        return resultMap;
    }


    private void assemblyUpdateSqlDataMap(Map<String, UpdateSql> resultMap, SqlAttribute sqlAttribute, String sql  ){
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
            Map<String, DbOperator> sqlOperatorMap = Maps.newLinkedHashMap();
            sqlOperatorMap.put(sql,sqlAttribute.getDbOperator());
            updateSql.setSqlOperatorMap(sqlOperatorMap);
        }
        resultMap.put(tableName,updateSql);
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

        for(Map.Entry<String,String> entry : sqlMap.entrySet() ){
            log.debug("检查【{}】的create SQL--->{}",entry.getKey(),entry.getValue());
            DbUtil.executeSql(con,entry.getValue());
            if(entry.getValue().toUpperCase().indexOf("DROP")!=0) {
                DbUtil.deleteTable(con, entry.getKey());
            }
        }

    }

    /**
     * 校验update sql
     * @param sqlMap 创建table的sql
     * @param updateSqlMap all update sql
     * @param con 和数据库的连接
     */
    private void checkUpdateSql(Map<String,String> sqlMap, Map<String, UpdateSql> updateSqlMap, Connection con){
        /* update sql执行时重复字段导致的异常统计输出 */
        StringBuilder duplicateError = new StringBuilder();
        StringBuilder executeError = new StringBuilder();

        for(Map.Entry<String, UpdateSql> entry : updateSqlMap.entrySet() ){
            UpdateSql updateSql = entry.getValue();
            boolean existTable =  false;
            log.debug("开始对表【{}】的update语句进行校验...",updateSql.getTableName());

            for (Map.Entry<String, DbOperator> sqlEntry: updateSql.getSqlOperatorMap().entrySet()){
                String sql = sqlEntry.getKey();
                /*如果不是在update.sql里创建的table，那么需要去根sql文件里获取创建table的sql并执行*/
                if( !existTable && !sqlEntry.getValue().equals(DbOperator.CREATE) ){
                    /*校验updateSQL首先需要创建对应的table*/
                    if( sqlMap.containsKey(updateSql.getTableName()) ){
                        log.debug("表【{}】的创建语句在update中不存在，在根sql文件里获取创建语句...",updateSql.getTableName());
                        DbUtil.executeSql(con,sqlMap.get(entry.getKey()));
                        existTable = true;
                    }else{
                        log.error("表【{}】的创建语句在update中不存在，在根sql文件里也未找到...",updateSql.getTableName());
                        log.error("根sql中已有的建表语句：");
                        for (Map.Entry<String, String> entry2 : sqlMap.entrySet()) {
                            log.error(entry2.getKey());
                        }
                    }
                }
                if( sqlEntry.getValue().equals(DbOperator.CREATE) ){
                    existTable = true;
                }
                log.debug("执行【{}】update的语句--->\n{}",sqlEntry.getValue().getOperator(),sql);
                try{
                    DbUtil.executeSql(con,sql);
                }catch (DuplicateColumnException e){
                    duplicateError.append(
                            MessageFormat.format(
                                    SQLErrorMessageTemplate.DUPLICATE_COLUMN_ERROR_MESSAGE,
                                    updateSql.getTableName(),
                                    e.getMessage()
                            )
                    );
                }catch(DuplicateIndexException e){
                    duplicateError.append(
                            MessageFormat.format(
                                    SQLErrorMessageTemplate.DUPLICATE_INDEX_ERROR_MESSAGE,
                                    updateSql.getTableName(),
                                    e.getMessage()
                            )
                    );
                }catch (SQLRunTimeException e){
                    executeError.append(
                            MessageFormat.format(
                                    SQLErrorMessageTemplate.EXECUTE_SQL_ERROR_MESSAGE,
                                    updateSql.getTableName(),
                                    e.getMessage()
                            )
                    );
                }

            }
            DbUtil.deleteTable(con, updateSql.getTableName());

            log.debug ("表【{}】的update语句校验结束...",updateSql.getTableName());
        }
        if( !StringUtils.isEmpty(duplicateError.toString()) ){
            log.error(duplicateError.toString());
        }
        if( !StringUtils.isEmpty(executeError.toString()) ){
            log.error(executeError.toString());
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

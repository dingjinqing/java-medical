package com.vpu.sql.config;


import com.google.common.collect.Lists;
import com.vpu.sql.config.source.MainDataSource;
import com.vpu.sql.config.source.ShopDataSource;
import com.vpu.sql.constant.Scope;
import com.vpu.sql.constant.SqlTemplate;
import com.vpu.sql.entity.DbConfig;
import com.vpu.sql.entity.DbSource;
import com.vpu.sql.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lugauangyao
 */
@Component
@Slf4j
public class DataConfigSource {

    private final ConfigurableBeanFactory beanFactory;

    private final ShopDataSource shopDataSource;

    private final MainDataSource mainDataSource;

    private final DataSource sqlLiteDataSource;


    private List<String> sqlSource  = Lists.newArrayList();

    private List<DbSource> dbSources = Lists.newArrayList();


    private static UUIDUtil ID = new UUIDUtil(1,1);




    public DataConfigSource(ConfigurableBeanFactory beanFactory, ShopDataSource shopDataSource,
                            MainDataSource mainDataSource, @Qualifier("sqlLiteSource") DataSource sqlLiteSource){
        this.beanFactory = beanFactory;
        this.shopDataSource = shopDataSource;
        this.mainDataSource = mainDataSource;
        this.sqlLiteDataSource = sqlLiteSource;
    }

    public List<DbSource> getDataSource(){
        return this.dbSources;
    }
    public void initSqlSource(List<String> sql){
        this.sqlSource = sql;
    }

    public void initDataSource(Scope scope, List<Integer> shopIds) throws SQLException {
        if( scope == Scope.main ){
            dbSources.add(new DbSource(mainDataSource.getMainDataSource(),scope));

        }else if( scope == Scope.shop ){
            List<String> allShop;
            if(CollectionUtils.isEmpty(shopIds)){
                allShop =  mainDataSource.getShopDbConfig(null);

            }else{
                allShop = mainDataSource.getShopDbConfig(shopIds);
            }
            if( CollectionUtils.isEmpty(allShop) ){
                log.warn("can't find shop");
                return ;
            }
            List<DbConfig> configs = allShop.stream().
                    map(x-> JsonUtil.toEntityAndIgnoreExtraFields(x, DbConfig.class)).
                    collect(Collectors.toList());
            Map<String,DataSource> sources = shopDataSource.getShopDataSource(configs);
            for( Map.Entry<String,DataSource> entry:sources.entrySet() ){
                DbSource source = new DbSource(entry.getValue(),scope);
                List<String> dataBase = Lists.newArrayList();
                configs.forEach(x->{
                    if( entry.getKey().contains(x.getHost()) ){
                        dataBase.add(x.getDataBase());
                    }
                });
                source.setDataBases(dataBase);
                dbSources.add(source);
            }
        }
    }
    public void execute(){
        initSourceTable();
        String initSql = "use ";
        if( CollectionUtils.isEmpty(dbSources) ){
            return ;
        }
        log.info("开始执行SQL...");
        for( DbSource source: dbSources ){
            try (Connection con = source.getDataSource().getConnection()){

                if( Scope.main.equals(source.getScope()) ){
                    log.info("主库执行:");
                    for( String sql :sqlSource ){
                        String md5Sql = Md5Util.md5(RegexUtil.getCompressionSql(sql));
                        if( checkRepeatSql(md5Sql,0,"main_sql_temp") ){
                            insertIntoDb(sql,0,md5Sql,"main_sql");
                        }else{
                            DbUtil.realExecuteSql(con,sql);
                            insertIntoDb(sql,0,md5Sql,"main_sql");
                        }


                    }
                }else{
                    for( String db: source.getDataBases() ){
                        log.info("shop执行:{}",db);
                        int shopId = RegexUtil.getShopIdByTableName(db);
                        DbUtil.realExecuteSql(con,initSql+db);
                        for( String sql : sqlSource ){
                            log.debug(sql);
                            String md5Sql = Md5Util.md5(RegexUtil.getCompressionSql(sql));
                            if( checkRepeatSql(md5Sql,shopId,"shop_sql_temp") ){
                                insertIntoDb(sql,shopId,md5Sql,"shop_sql");
                            }else{
                                DbUtil.realExecuteSql(con,sql);

                                insertIntoDb(sql,shopId,md5Sql,"shop_sql");
                            }
                        }

                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询当前sql的md5值在历史记录中是否存在
     * @param md5SqlStr md5(sql)
     * @param shopId 店铺id
     * @return 存在 true|不存在 false
     */
    private boolean checkRepeatSql(String md5SqlStr, int shopId, String tableName){
        try ( Connection con = sqlLiteDataSource.getConnection()){
            PreparedStatement ps = Objects.requireNonNull(con).prepareStatement(String.format(SqlTemplate.GET_EXECUTED_SQL,tableName));
            ps.setString(1, md5SqlStr);
            ps.setInt(2,shopId);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 向执行记录表里添加执行记录
     * @param sql 执行的记录
     * @param shopId shopID
     * @param md5 md5(sql)
     * @param tableName 插入表的name
     */
    private void insertIntoDb(String sql, int shopId, String md5, String tableName){
        try( Connection con = sqlLiteDataSource.getConnection()) {
            con.setAutoCommit(false);
            String insertSql = String.format(SqlTemplate.INSERT_HISTORY_SQL,tableName);
            PreparedStatement ps = Objects.requireNonNull(con.prepareStatement(insertSql));
            ps.setLong(1,ID.nextId());
            ps.setInt(2,shopId);
            ps.setString(3,md5);
            ps.setString(4,sql);
            ps.execute();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void initSourceTable(){
        try( Connection con = sqlLiteDataSource.getConnection()) {
            if( con != null ){

                DbUtil.executeSQLFileByJar(con, "db/init.sql");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

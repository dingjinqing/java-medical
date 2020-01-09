package com.vpu.sql.config;


import com.google.common.collect.Lists;
import com.vpu.sql.config.source.MainDataSource;
import com.vpu.sql.config.source.ShopDataSource;
import com.vpu.sql.constant.Scope;
import com.vpu.sql.entity.DBConfig;
import com.vpu.sql.entity.DBSource;
import com.vpu.sql.util.DBUtil;
import com.vpu.sql.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DataConfigSource {

    private final ConfigurableBeanFactory beanFactory;

    private final ShopDataSource shopDataSource;

    private final MainDataSource mainDataSource;



    private List<String> sqlSource  = Lists.newArrayList();

    private List<DBSource> dbSources = Lists.newArrayList();





    public DataConfigSource(ConfigurableBeanFactory beanFactory, ShopDataSource shopDataSource,
                            MainDataSource mainDataSource){
        this.beanFactory = beanFactory;
        this.shopDataSource = shopDataSource;
        this.mainDataSource = mainDataSource;
    }

    public List<DBSource> getDataSource(){
        return this.dbSources;
    }
    public void initSqlSource(List<String> sql){
        this.sqlSource = sql;
    }

    public void initDataSource(Scope scope,List<Integer> shopIds) throws SQLException {
        if( scope == Scope.main ){
            dbSources.add(new DBSource(mainDataSource.getMainDataSource(),scope));

        }else if( scope == Scope.shop ){
            List<String> allShop;
            if(CollectionUtils.isEmpty(shopIds)){
                allShop =  mainDataSource.getShopDbConfig(null);

            }else{
                allShop = mainDataSource.getShopDbConfig(shopIds);
            }
            if( CollectionUtils.isEmpty(allShop) ){
                throw new NullPointerException("can't find shop");
            }
            List<DBConfig> configs = allShop.stream().
                    map(x->JsonUtil.toEntityAndIgnoreExtraFields(x,DBConfig.class)).
                    collect(Collectors.toList());
            Map<String,DataSource> sources = shopDataSource.getShopDataSource(configs);
            for( Map.Entry<String,DataSource> entry:sources.entrySet() ){
                DBSource source = new DBSource(entry.getValue(),scope);
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
        String initSQL = "use ";
        if( CollectionUtils.isEmpty(dbSources) ){
            return ;
        }
        log.info("开始执行SQL...");
        for( DBSource source: dbSources ){
            try (Connection con = source.getDataSource().getConnection()){
                if( Scope.main.equals(source.getScope()) ){
                    log.info("主库执行:");
                    sqlSource.forEach(x->{

                        DBUtil.executeSQL(con,x);
                    });
                }else{
                    for( String db: source.getDataBases() ){
                        log.info("shop执行:{}",db);
                        DBUtil.executeSQL(con,initSQL+db);
                        sqlSource.forEach(x->DBUtil.executeSQL(con,x));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

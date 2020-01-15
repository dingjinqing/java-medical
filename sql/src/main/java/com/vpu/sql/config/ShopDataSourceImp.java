package com.vpu.sql.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vpu.sql.config.source.ShopDataSource;
import com.vpu.sql.constant.BeanNameConstant;
import com.vpu.sql.entity.DBConfig;
import com.vpu.sql.util.JsonUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component("shopDataSource")
@Lazy
public class ShopDataSourceImp implements ShopDataSource {

    private static final String CONNECTION_TEMPLATE = "jdbc:mysql://{0}:{1}/?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";



    private HikariDataSource getShopDataSource(DBConfig dbConfig){
        HikariConfig config = new HikariConfig();
//        String jdbcUrl = MessageFormat.format(CONNECTION_TEMPLATE,dbConfig.getHost(),dbConfig.getPort()+"");
        String jdbcUrl = MessageFormat.format(CONNECTION_TEMPLATE,"127.0.0.1","9910");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(dbConfig.getUserName());
        config.setPassword(dbConfig.getPassword());
        config.setMaximumPoolSize(10);
        return new HikariDataSource(config);
    }
    @Override
    public Map<String,DataSource> getShopDataSource(List<DBConfig> configs){
        Map<String,DataSource> resultMap = Maps.newHashMap();
        log.info("\nall shop dataBase config:{}",configs.toString());
        List<DBConfig> needInits = deduplicationDbConfig(configs);
        for( DBConfig dbConfig: needInits ){
            resultMap.put(dbConfig.getHost(),getShopDataSource(dbConfig));
        }
        return resultMap;
    }

    private List<DBConfig> deduplicationDbConfig(List<DBConfig> configList){
        List<DBConfig> result = Lists.newArrayList();
        List<String>  deduplicationList = Lists.newLinkedList();
        configList.stream().filter(x->!deduplicationList.contains(x.getHost())).forEach(x->{
            result.add(x);
            deduplicationList.add(x.getHost());
        });
        return result;

    }
    @Override
    public void execute(String sql) {

    }

    @Override
    public void execute(Path path) {

    }
}

package com.vpu.sql.processor;

import com.google.common.collect.Lists;
import com.vpu.sql.config.DataConfigSource;
import com.vpu.sql.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;


@Component
@Slf4j
public class SqlPathProcessor implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    final DataConfigSource dataConfigSource;

    public SqlPathProcessor(DataConfigSource dataConfigSource) {
        this.dataConfigSource = dataConfigSource;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<String> sqlList = Lists.newArrayList();

        String sqlPath = System.getProperty("sqlPath");
        String version = System.getProperty("version");
        if( StringUtils.isEmpty(sqlPath) ){
           return ;
        }
        log.info("开始获取需要执行的的sql...");
        if( StringUtils.isEmpty(version) ){
            sqlList.addAll(FileUtil.readSqlFile(sqlPath));
        }else{
            sqlList.addAll(FileUtil.readSqlFile(sqlPath,version));
        }

        log.info("一共需要执行{}条",sqlList.size());
        dataConfigSource.initSqlSource(sqlList);
    }


    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE+3;
    }
}

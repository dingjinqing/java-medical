package com.vpu.sql.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class SqlLiteDataSource {




    @Bean("sqlLiteSource")
    @Qualifier("sqlLiteSource")
    @ConfigurationProperties(prefix = "sqllite.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }
}

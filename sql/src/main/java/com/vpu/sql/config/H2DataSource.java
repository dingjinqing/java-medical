package com.vpu.sql.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;

@Configuration
public class H2DataSource {

    public static final String H2_DRIVER = "org.h2.Driver";


    public static final String H2_MAIN = "jdbc:h2:mem:mainDB;MODE=MYSQL;DB_CLOSE_DELAY=-1";


    public static final String H2_SHOP = "jdbc:h2:mem:mainDB;MODE=MYSQL;DB_CLOSE_DELAY=-1";


    public DataSource getMainDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(H2_DRIVER);
        dataSourceBuilder.url(H2_MAIN);
        return dataSourceBuilder.build();
    }
    public DataSource getShopDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(H2_DRIVER);
        dataSourceBuilder.url(H2_SHOP);
        return dataSourceBuilder.build();
    }
}

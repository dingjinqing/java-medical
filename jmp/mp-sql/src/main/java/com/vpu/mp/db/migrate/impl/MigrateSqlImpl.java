package com.vpu.mp.db.migrate.impl;

import com.vpu.mp.db.migrate.MigrateSql;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.*;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author lixinguo
 */
@Component
public class MigrateSqlImpl implements MigrateSql {

    /**
     * 主库SQL版本位置
     */
    final String MAIN_DB_LOCATION = "classpath:migration/main";

    /**
     * 店铺库SQL版本位置
     */
    final String SHOP_DB_LOCATION = "classpath:migration/shop";

    /**
     * 已存在的主库数据库的基线版本，根据实际数据库SQL版本进行设置
     */
    @Value(value = "${flyway.baseLineVersion:3.3.4}")
    protected String mainBaseLineVersion;

    /**
     * 已存在的店铺库数据库的基线版本，根据实际数据库SQL版本进行设置
     */
    @Value(value = "${flyway.shopBaseLineVersion:3.3.2}")
    protected String shopBaseLineVersion;


    @Override
    public boolean needUpdateSqlVersion(DataSource dataSource, String database, Boolean isMainDb) throws FlywayException {
        Flyway flyway = config(dataSource, database, isMainDb, false).load();
        MigrationInfoService info = flyway.info();
        for (MigrationInfo migrationInfo : info.all()) {
            if (migrationInfo.getState() == MigrationState.PENDING) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void migrateDb(DataSource dataSource, String database, Boolean isMainDb, boolean isCreateDb) throws FlywayException {
        Flyway flyway = config(dataSource, database, isMainDb, isCreateDb).load();
        flyway.migrate();
    }


    /**
     * flyway配置信息
     *
     * @param dataSource 数据源,不包含库名
     * @param database   数据库名字
     * @param isMainDb   是否为主库，true主库，false店铺库
     * @param isCreateDb 是否创建库
     */
    private FluentConfiguration config(DataSource dataSource, String database, Boolean isMainDb, boolean isCreateDb) {
        String initSql = "SET NAMES utf8mb4;\n";
        if (isCreateDb) {
            initSql = initSql + " create database " + database
                + "  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; \n use " + database + " ;";
        }
        return Flyway.configure()
            .schemas(database)
            .initSql(initSql)
            .outOfOrder(true)
            .baselineOnMigrate(true)
            .baselineVersion(isMainDb ? MigrationVersion.fromVersion(mainBaseLineVersion)
                : MigrationVersion.fromVersion(shopBaseLineVersion))
            .locations(isMainDb ? MAIN_DB_LOCATION : SHOP_DB_LOCATION)
            .dataSource(dataSource);

    }
}

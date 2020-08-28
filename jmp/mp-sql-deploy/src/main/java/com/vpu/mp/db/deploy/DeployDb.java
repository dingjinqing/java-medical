package com.vpu.mp.db.deploy;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vpu.mp.db.migrate.MigrateSql;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.FlywayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixinguo
 */
@Component
@Slf4j
public class DeployDb {

    @Value(value = "${db.host}")
    protected String host;

    @Value(value = "${db.port}")
    protected Integer port;

    @Value(value = "${db.database}")
    protected String database;

    @Value(value = "${db.username}")
    protected String username;

    @Value(value = "${db.password}")
    protected String password;

    @Value(value = "${flyway.dryRunHost}")
    protected String dryRunHost;

    @Value(value = "${flyway.dryRunPort}")
    protected Integer dryRunPort;

    @Value(value = "${flyway.dryRunMainDatabase}")
    protected String dryRunMainDatabase;

    @Value(value = "${flyway.dryRunShopDatabase}")
    protected String dryRunShopDatabase;

    @Value(value = "${flyway.dryRunUsername}")
    protected String dryRunUsername;

    @Value(value = "${flyway.dryRunPassword}")
    protected String dryRunPassword;


    @Autowired
    protected MigrateSql migrateSql;

    protected ConcurrentHashMap<String, DataSource> dataSources = new ConcurrentHashMap<>();

    /**
     * 部署数据库
     *
     * @throws FlywayException 部署异常
     */
    public void deploy() throws FlywayException, IOException {

        dryRun(true);

        dryRun(false);

        if (needUpdateSqlVersion(true)) {
            migrateMainDb();
        }

        if (needUpdateSqlVersion(false)) {
            migrateShopDbs();
        }
    }

    /**
     * 创建主库
     *
     * @throws FlywayException 部署异常
     */
    public void installMainDb() throws FlywayException {
        DbConfig mainConfig = mainDbConfig();
        DataSource mainDataSource = getDatasource(mainConfig);
        migrateSql.migrateDb(mainDataSource, mainConfig.getDatabase(), mainConfig.getIsMainDb(), true);
    }

    /**
     * 创建店铺库
     *
     * @param host     数据库Ip
     * @param port     数据库端口
     * @param database 数据库名称
     * @param username 数据库用户名
     * @param password 数据库密码
     * @throws FlywayException 部署异常
     */
    public void installShopDb(String host, Integer port, String database, String username, String password) throws FlywayException {
        DbConfig dbConfig = new DbConfig(host, port, database, username, password, false);
        DataSource mainDataSource = getDatasource(dbConfig);
        migrateSql.migrateDb(mainDataSource, database, false, true);
    }

    /**
     * 校验整体SQL语法
     */
    public void dryRun(Boolean isMain) {
        if (isMain) {
            DbConfig dryMainRunDbConfig = dryRunDbConfig(true);
            DataSource dryMainDataSource = getDatasource(dryMainRunDbConfig);
            JdbcTemplate mainJdbcTemplate = new JdbcTemplate(dryMainDataSource);
            log.info("dryRun MainDb:{}", dryMainRunDbConfig.getDatabase());
            try {
                migrateSql.migrateDb(dryMainDataSource, dryMainRunDbConfig.getDatabase(), true, true);
                log.info("dryRun MainDb: {} Ok", dryMainRunDbConfig.getDatabase());
            } finally {
                mainJdbcTemplate.execute("drop database if exists " + dryMainRunDbConfig.getDatabase());
            }

        } else {
            DbConfig dryShopRunDbConfig = dryRunDbConfig(false);
            DataSource dryShopDataSource = getDatasource(dryShopRunDbConfig);
            JdbcTemplate shopJdbcTemplate = new JdbcTemplate(dryShopDataSource);
            log.info("dryRun ShopDb:{}", dryShopRunDbConfig.getDatabase());
            try {
                migrateSql.migrateDb(dryShopDataSource, dryShopRunDbConfig.getDatabase(), false, true);
                log.info("dryRun ShopDb: {} Ok", dryShopRunDbConfig.getDatabase());
            } finally {
                shopJdbcTemplate.execute("drop database if exists " + dryShopRunDbConfig.getDatabase());
            }
        }
    }


    /**
     * 检查是否需要升级版本
     *
     * @return 是否需要升级
     */
    public boolean needUpdateSqlVersion(Boolean isMainDb) {
        if (isMainDb) {
            DbConfig mainConfig = mainDbConfig();
            DataSource mainDataSource = getDatasource(mainConfig);
            return migrateSql.needUpdateSqlVersion(mainDataSource, mainConfig.getDatabase(), mainConfig.isMainDb);
        } else {
            DbConfig shopDbConfig = getFirstShopDbConfig();
            if (shopDbConfig != null) {
                return migrateSql.needUpdateSqlVersion(getDatasource(shopDbConfig), shopDbConfig.getDatabase(), shopDbConfig.isMainDb);
            }
            return false;
        }
    }

    public void migrateMainDb() {
        DbConfig mainConfig = mainDbConfig();
        log.info("migrateMainDb:{}", mainConfig.getDatabase());
        migrateSql.migrateDb(getDatasource(mainConfig), mainConfig.getDatabase(), mainConfig.getIsMainDb(), false);
    }

    /**
     * 部署升级SQL版本,店铺库
     */
    public void migrateShopDbs() throws IOException {
        log.info("migrateShopDbs");
        DbConfig mainConfig = mainDbConfig();
        DataSource mainDataSource = getDatasource(mainConfig);
        JdbcTemplate mainJdbcTemplate = new JdbcTemplate(mainDataSource);
        List<Map<String, Object>> list = mainJdbcTemplate.queryForList("select db_config from " + mainConfig.getDatabase() + ".b2c_shop");
        for (Map<String, Object> row : list) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String dbConfigStr = row.get("db_config").toString();
            DbConfig dbConfig = mapper.readValue(dbConfigStr, DbConfig.class);
            dbConfig.setIsMainDb(false);
            log.info("migrate ShopDb:{}", dbConfig.getDatabase());
            migrateSql.migrateDb(getDatasource(dbConfig), dbConfig.getDatabase(), dbConfig.getIsMainDb(), false);
        }
    }

    /**
     * 得到第一个店铺配置，用于判断店铺SQL是否需要升级
     *
     * @return 第一个店铺配置
     */
    private DbConfig getFirstShopDbConfig() {
        DbConfig mainConfig = mainDbConfig();
        JdbcTemplate mainJdbcTemplate = new JdbcTemplate(getDatasource(mainConfig));
        List<Map<String, Object>> list = mainJdbcTemplate.queryForList("select db_config from " + mainConfig.getDatabase() + ".b2c_shop limit 1");
        if (list.isEmpty()) {
            return null;
        }
        String dbConfigStr = list.get(0).get("db_config").toString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            DbConfig dbConfig = mapper.readValue(dbConfigStr, DbConfig.class);
            dbConfig.setIsMainDb(false);
            return dbConfig;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 主库配置
     *
     * @return 主库配置
     */
    private DbConfig mainDbConfig() {
        return new DbConfig(host, port, database, username, password, true);
    }

    /**
     * dryRun数据库配置
     *
     * @param isMain 是否主库
     * @return dryRun数据库配置
     */
    private DbConfig dryRunDbConfig(boolean isMain) {
        return new DbConfig(dryRunHost, dryRunPort, isMain ? dryRunMainDatabase : dryRunShopDatabase, dryRunUsername, dryRunPassword, isMain);
    }


    public DataSource getDatasource(DbConfig dbConfig) {
        DataSource ds = dataSources.get(dbConfig.getDatasourceKey());
        if (ds != null) {
            return ds;
        }
        String url = "jdbc:mysql://" + host + ":" + port + "/"
            + "?serverTimezone=Hongkong&useSSL=false&useUnicode=true&character_set_server=utf8mb4&zeroDateTimeBehavior=convertToNull";
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(1);
        dataSource.setMinimumIdle(0);
        dataSources.put(dbConfig.getDatasourceKey(), dataSource);
        return dataSource;
    }
}



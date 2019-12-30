package com.vpu.mp.service.foundation.database;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.config.DatabaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Data;

/**
 * 
 * @author lixinguo
 *
 */
@Service
@Data
public class DatasourceManager {

	@Autowired
	protected DatabaseConfig databaseConfig;

	/**
	 * 数据源列表，多线程共用。
	 */
	protected Map<String, HikariDataSource> datasources = Collections
			.synchronizedMap(new HashMap<String, HikariDataSource>());

	/**
	 * 得到主数据库配置
	 * 
	 * @return
	 */
	public DbConfig getMainDbConfig() {
		return new DbConfig(databaseConfig.getHost(), databaseConfig.getPort(), databaseConfig.getDatabase(),
				databaseConfig.getUsername(), databaseConfig.getPassword());
	}

	/**
	 * 得到主库数据源
	 */
	public HikariDataSource getMainDbDatasource() {
		return this.getDatasource(getMainDbConfig());
	}

	/**
	 * 得到即将创建店铺库的数据源
	 */
	public HikariDataSource getToCreateShopDbDatasource() {
		return this.getDatasource(new DbConfig(databaseConfig.getShopHost(), databaseConfig.getShopPort(), "",
				databaseConfig.getShopUsername(), databaseConfig.getShopPassword()));
	}

	/**
	 * 得到数据源
	 */
	protected HikariDataSource getDatasource(DbConfig dbConfig) {
		String key = dbConfig.getDatasourceKey();
		if (!datasources.containsKey(key)) {
//			datasources.put(key, dataSource(getJdbcUrl(dbConfig.host, dbConfig.port, ""), dbConfig.username, dbConfig.password));
            datasources.put(key,dataSource(getJdbcUrl("127.0.0.1",9910, ""), dbConfig.username, dbConfig.password));
		}
		return datasources.get(key);
	}

	/**
	 * 得到配置好的数据源
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @param driver
	 * @return
	 */
	protected HikariDataSource dataSource(String url, String username, String password) {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(databaseConfig.getDriver());
		if (databaseConfig.getMaxPoolSize() > 0) {
			dataSource.setMaximumPoolSize(databaseConfig.getMaxPoolSize());
		}
		if (databaseConfig.getMinIdle() > 0) {
			dataSource.setMinimumIdle(databaseConfig.getMinIdle());
		}
		return dataSource;
	}

	/**
	 * 得到JDBC串
	 * 
	 * @param host
	 * @param port
	 * @param database
	 * @return
	 */
	public String getJdbcUrl(String host, Integer port, String database) {
		return "jdbc:mysql://" + host + ":" + port + "/" + database
				+ "?serverTimezone=Hongkong&useSSL=false&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	}

	/**
	 * 得到安装数据库配置
	 * 
	 * @param shopId
	 * @return
	 */
	public DbConfig getInstallShopDbConfig(Integer shopId) {
		return new DbConfig(databaseConfig.getShopHost(), databaseConfig.getShopPort(),
				databaseConfig.getShopDbPrefix() + shopId.toString(), databaseConfig.getShopUsername(),
				databaseConfig.getShopPassword());
	}
}

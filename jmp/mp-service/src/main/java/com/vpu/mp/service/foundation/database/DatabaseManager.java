package com.vpu.mp.service.foundation.database;

import static com.vpu.mp.db.main.tables.Shop.SHOP;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.TransactionProvider;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.impl.DefaultTransactionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.vpu.mp.config.DatabaseConfig;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.service.QueryFilter;
import com.vpu.mp.service.foundation.util.Util;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 数据库管理，单例。需要考虑多线程互斥情况
 *
 * @author lixinguo
 *
 */
@Service
public class DatabaseManager {

	@Autowired
	protected DatabaseConfig databaseConfig;

	@Autowired
	protected DatasourceManager datasourceManager;

	Logger loger = LoggerFactory.getLogger(DatabaseManager.class);

	/**
	 * 主库连接，每个线程一个连接
	 */
	private ThreadLocal<MpDefaultDslContext> mainDsl = new ThreadLocal<MpDefaultDslContext>();

	/**
	 * 店铺库连接，每个线程有一个正用的数据库连接，可以随时切换
	 */
	private ThreadLocal<MpDefaultDslContext> shopDsl = new ThreadLocal<MpDefaultDslContext>();

	/**
	 * 主库连接
	 */
	public DefaultDSLContext mainDb() {
		MpDefaultDslContext db = mainDsl.get();
		if (db == null) {
			HikariDataSource ds = datasourceManager.getMainDbDatasource();
			db = this.getDsl(ds, datasourceManager.getMainDbConfig(), 0);
		}
		mainDsl.remove();
		mainDsl.set(db);
		return mainDsl.get();
	}

	/**
	 * 切换当前数据库连接
	 *
	 * @param shopId
	 * @return
	 */
	public DatabaseManager switchShopDb(Integer shopId) {
		loger.debug("switchShopDb===" + shopId);
		MpDefaultDslContext db = shopDsl.get();
		if (db == null || !db.getShopId().equals(shopId)) {
			ShopRecord shop = mainDb().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchAny();
			if (shop != null) {
				DbConfig dbConfig = Util.parseJson(shop.getDbConfig(), DbConfig.class);
				if (dbConfig == null) {
					throw new RuntimeException("ShopId " + shopId + " Db not found");
				}
				HikariDataSource ds = datasourceManager.getDatasource(dbConfig);
				db = getDsl(ds, dbConfig, shopId);
				shopDsl.remove();
				shopDsl.set(db);
			} else {
				throw new RuntimeException("ShopId " + shopId + " Db not found");
			}
		}
		return this;
	}

	/**
	 * 得到当前数据库店铺ID
	 *
	 * @return
	 */
	public Integer getCurrentShopId() {
		MpDefaultDslContext db = shopDsl.get();
		assert (db != null) : "DB NULL";
		return db.getShopId();
	}

	/**
	 * 当前店铺库连接
	 */
	public MpDefaultDslContext currentShopDb() {
		MpDefaultDslContext db = shopDsl.get();
		assert (db != null);
		return db;
	}

	/**
	 * 从数据源获取一个连接
	 */
	protected MpDefaultDslContext getDsl(HikariDataSource ds, DbConfig dbConfig, Integer shopId) {
		MpDefaultDslContext db = new MpDefaultDslContext(configuration(ds, dbConfig.getDatabase()));
		db.setShopId(shopId);
		db.setDbConfig(dbConfig);
		db.execute("SET NAMES utf8mb4");
		db.execute("Set sql_mode='ONLY_FULL_GROUP_BY'");
		return db;
	}

	/**
	 * 安装店铺数据库
	 */
	public boolean installShopDb(DbConfig dbConfig) {
		try {
			String sql = "create database " + dbConfig.database + " default charset utf8mb4 collate utf8mb4_unicode_ci";
			HikariDataSource ds = datasourceManager.getToCreateShopDbDatasource();
			getDsl(ds, dbConfig, 0).execute(sql);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}

		boolean ret = execScript(dbConfig, "db/shop/db_shop.sql");
		// 测试用，测完删log
		loger.debug("db_shop.sql执行结果" + ret);
		if (ret) {
			loger.debug("准备执行db_shop.sql的dbConfig" + dbConfig);
			ret = execScript(dbConfig, "db/shop/db_shop_data.sql");
			loger.debug("db_shop_data.sql执行结果" + ret);
		}
		return ret;
	}

	/**
	 * 执行SQL脚本
	 */
	public boolean execScript(DbConfig dbConfig, String sqlPath) {
		ClassPathResource cpr = new ClassPathResource(sqlPath);
		String sql = "";
		try {
			byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
			sql = new String(bdata, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		sql = this.filterSql(sql);
		final class SqlExecuter extends SQLExec {
			public SqlExecuter() {
				Project project = new Project();
				project.init();
				setProject(project);
				setTaskType("sql");
				setTaskName("sql");
				this.setDelimiter(";");
				this.setAutocommit(true);
				this.setEncoding("UTF-8");
			}
		}

		SqlExecuter executer = new SqlExecuter();
		executer.addText(sql);
		executer.setDriver(databaseConfig.getDriver());
		executer.setPassword(dbConfig.password);
		executer.setUserid(dbConfig.username);
		executer.setUrl(datasourceManager.getJdbcUrl(dbConfig.host, dbConfig.port, dbConfig.database));
		try {
			executer.execute();
		} catch (BuildException e) {
			loger.debug(e.getMessage(), e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 过滤SQL注释
	 */
	public String filterSql(String sql) {
		String[] lines = sql.split("\n");
		StringBuffer sqlBuffer = new StringBuffer();
		boolean startMultiLineComments = false;
		int p1 = -1;
		int p2 = -1;

		sqlBuffer.append("SET NAMES utf8mb4;").append("\n");
		sqlBuffer.append("Set sql_mode='ONLY_FULL_GROUP_BY';").append("\n");

		for (String line : lines) {
			line = line.trim();
			if (!startMultiLineComments) {
				if (line.startsWith("//") || line.startsWith("#") || line.startsWith("--")) {
					continue;
				}
			}

			if (startMultiLineComments) {
				p2 = line.indexOf("*/");
				if (p2 == -1) {
					continue;
				} else {
					startMultiLineComments = false;
					line = line.substring(p2 + 2);
				}
			}

			while (true) {
				p1 = line.indexOf("/*");
				if (p1 != -1) {
					sqlBuffer.append(line.substring(0, p1));
					p2 = line.indexOf("*/");
					if (p2 == -1) {
						startMultiLineComments = true;
						break;
					} else {
						line = line.substring(p2 + 2);
						continue;
					}
				} else {
					sqlBuffer.append(line).append("\n");
					break;
				}
			}
			if (startMultiLineComments) {
				continue;
			}

		}
		return sqlBuffer.toString();
	}

	/**
	 * 根据数据源得到JOOQ的配置
	 *
	 * @param dataSource
	 * @return
	 */
	protected DefaultConfiguration configuration(HikariDataSource dataSource, String databaseName) {

		String jooqMainSchema = "mini_main";
		String jooqShopSchema = "mini_shop_471752";

		ConnectionProvider connectionProvider = new DataSourceConnectionProvider(dataSource);
		TransactionProvider transactionProvider = new DefaultTransactionProvider(connectionProvider);
		DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
		jooqConfiguration.set(connectionProvider);
		jooqConfiguration.set(transactionProvider);
		jooqConfiguration.set(new DefaultExecuteListenerProvider(new SqlExcuteListener()));
		SQLDialect dialect = SQLDialect.valueOf(databaseConfig.getDialect());
		jooqConfiguration.set(dialect);

		Settings settings = new Settings();

		// 设置schema映射
		if (!StringUtils.isBlank(databaseName)) {
			String inputSchema = databaseConfig.getDatabase().equals(databaseName) ? jooqMainSchema : jooqShopSchema;
			settings.withRenderMapping(new RenderMapping()
					.withSchemata(new MappedSchema().withInput(inputSchema).withOutput(databaseName)));
		}

		settings.withRenderCatalog(false);
		jooqConfiguration.setSettings(settings);
		// 不安全操作拦截器
		QueryFilter queryFilter = new QueryFilter();
		jooqConfiguration.set(queryFilter);
		return jooqConfiguration;
	}

	/**
	 * 得到当前线程店铺Db名称
	 *
	 * @return
	 */
	public String getCurrentShopDbSchema() {
		return shopDsl.get() != null ? shopDsl.get().getDbConfig().getDatabase() : "";
	}

	/**
	 * 得到当前线程店铺Db名称
	 *
	 * @return
	 */
	public String getMainDbSchema() {
		return databaseConfig.getDatabase();
	}
}

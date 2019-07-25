package com.vpu.mp.service.foundation.database;

import static com.vpu.mp.db.main.tables.Shop.SHOP;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
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

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.util.Util;

/**
 * 数据库管理，单例。需要考虑多线程互斥情况
 * 
 * @author lixinguo
 *
 */
@Service
public class DatabaseManager {

	@Autowired
	protected DatasourceManager datasourceManager;

	/**
	 * 当前操作的店铺ID
	 */
	private ThreadLocal<Integer> currentShopId = new ThreadLocal<Integer>() {
		@Override
		public Integer initialValue() {
			return 0;
		}
	};

	/**
	 * 上一次操作店铺ID
	 */
	private ThreadLocal<Integer> lastShopId = new ThreadLocal<Integer>() {
		@Override
		public Integer initialValue() {
			return 0;
		}
	};

	Logger loger = LoggerFactory.getLogger(DatabaseManager.class);

	/**
	 * 主库连接，每个线程一个连接
	 */
	private ThreadLocal<MpDefaultDSLContext> mainDsl = new ThreadLocal<MpDefaultDSLContext>();

	/**
	 * 店铺库连接，每个线程有一个正用的数据库连接，可以随时切换
	 */
	private ThreadLocal<MpDefaultDSLContext> shopDsl = new ThreadLocal<MpDefaultDSLContext>();

	/**
	 * 主库连接
	 */
	public DefaultDSLContext mainDb() {
		MpDefaultDSLContext db = mainDsl.get();
		if (db == null) {
			BasicDataSource ds = datasourceManager.getMainDbDatasource();
			db = this.getDsl(ds, datasourceManager.getMainDbConfig());
		}
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
		if (shopId == currentShopId.get()) {
			return this;
		}
		MpDefaultDSLContext db = shopDsl.get();
		if (db == null) {
			ShopRecord shop = mainDb().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchAny();
			if (shop != null) {
				DbConfig dbConfig = Util.parseJson(shop.getDbConfig(), DbConfig.class);
				if (dbConfig == null) {
					throw new RuntimeException();
				}

				BasicDataSource ds = datasourceManager.getDatasource(dbConfig);
				db = getDsl(ds, dbConfig);
			} else {
				throw new RuntimeException();
			}
		}
		shopDsl.set(db);
		lastShopId.set(currentShopId.get());
		currentShopId.set(shopId);
		return this;
	}

	/**
	 * 恢复上次切换的DB
	 */
	public void restoreLastShopDb() {
		this.switchShopDb(lastShopId.get());
	}

	/**
	 * 得到当前数据库店铺ID
	 * 
	 * @return
	 */
	public Integer getCurrentShopId() {
		return currentShopId.get();
	}

	/**
	 * 当前店铺库连接
	 */
	public MpDefaultDSLContext currentShopDb() {
		MpDefaultDSLContext db = shopDsl.get();
		assert (db != null);
		return shopDsl.get();
	}

	/**
	 * 从数据源获取一个连接
	 */
	protected MpDefaultDSLContext getDsl(BasicDataSource ds, DbConfig dbConfig) {
		MpDefaultDSLContext db = new MpDefaultDSLContext(configuration(ds, dbConfig.getDatabase()));
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
			BasicDataSource ds = datasourceManager.getToCreateShopDbDatasource();
			getDsl(ds, dbConfig).execute(sql);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}

		boolean ret = execScript(dbConfig, "db/shop/db_shop.sql");
		// 测试用，测完删log
		loger.info("db_shop.sql执行结果" + ret);
		if (ret) {
			loger.info("准备执行db_shop.sql的dbConfig" + dbConfig);
			ret = execScript(dbConfig, "db/shop/db_shop_data.sql");
			loger.info("db_shop_data.sql执行结果" + ret);
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
		executer.setDriver(datasourceManager.getDriver());
		executer.setPassword(dbConfig.password);
		executer.setUserid(dbConfig.username);
		executer.setUrl(datasourceManager.getJdbcUrl(dbConfig.host, dbConfig.port, dbConfig.database));
		try {
			executer.execute();
		} catch (BuildException e) {
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
	protected DefaultConfiguration configuration(BasicDataSource dataSource, String databaseName) {

		String jooqMainSchema = "mini_main";
		String jooqShopSchema = "mini_shop_471752";

		ConnectionProvider connectionProvider =  new DataSourceConnectionProvider(dataSource);
		TransactionProvider transactionProvider = new DefaultTransactionProvider(connectionProvider);
		DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
		jooqConfiguration.set(connectionProvider);
		jooqConfiguration.set(transactionProvider);
		jooqConfiguration.set(new DefaultExecuteListenerProvider(new SqlExcuteListener()));
		SQLDialect dialect = SQLDialect.valueOf(datasourceManager.getDialect());
		jooqConfiguration.set(dialect);

		Settings settings = new Settings();

		// 设置schema映射
		if (!StringUtils.isBlank(databaseName)) {
			String inputSchema = datasourceManager.getDatabase().equals(databaseName) ? jooqMainSchema : jooqShopSchema;
			settings.withRenderMapping(new RenderMapping()
					.withSchemata(new MappedSchema().withInput(inputSchema).withOutput(databaseName)));
		}

		settings.withRenderCatalog(false);
		jooqConfiguration.setSettings(settings);
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
		return datasourceManager.getDatabase();
	}

	@Override
	protected void finalize() {
		MpDefaultDSLContext db = mainDsl.get();
		if (db != null) {
			mainDsl.remove();
			db = null;
		}

		db = shopDsl.get();
		if (db != null) {
			shopDsl.remove();
			db = null;
		}
		if (currentShopId.get() != null) {
			currentShopId.remove();
		}
		if (lastShopId.get() != null) {
			lastShopId.remove();
		}
	}
}

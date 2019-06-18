package com.vpu.mp.service.foundation;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import static com.vpu.mp.db.main.tables.Shop.SHOP;
import com.vpu.mp.db.main.tables.records.ShopRecord;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * 
 * @author 新国
 *
 */
public class DataManager {

	protected String host;
	protected String database;
	protected String username;
	protected String password;
	protected String dbPrefix;
	protected String shopHost;
	protected String shopUsername;
	protected String shopPassword;
	protected String shopDbPrefix;

	protected String driver = "com.mysql.cj.jdbc.Driver";
	protected String dialect = "MYSQL";

	private DefaultDSLContext db = null;

	private HashMap<Integer, DefaultDSLContext> shopDbList = new HashMap<Integer, DefaultDSLContext>();

	private static DataManager dm = null;
	
	public static DataManager instance() {
		if (dm == null) {
			dm = new DataManager();
		}
		return dm;
	}
	
	protected DataManager() {
		host = Util.getProperty("db.host");
		database = Util.getProperty("db.database");
		username = Util.getProperty("db.username");
		password = Util.getProperty("db.password");
		dbPrefix = Util.getProperty("db.prefix");
		shopHost = Util.getProperty("db.shop.host");
		shopUsername = Util.getProperty("db.shop.username");
		shopPassword = Util.getProperty("db.shop.password");
		shopDbPrefix = Util.getProperty("db.shop.prefix");
	}

	

	public DefaultDSLContext db() {
		if (db == null) {
			BasicDataSource ds = dataSource(getJdbcUrl(host, database), username, password, driver);
			db = new DefaultDSLContext(configuration(ds));
			db.setSchema(this.database);
			db.execute("SET NAMES utf8mb4");
			db.execute("Set sql_mode='ONLY_FULL_GROUP_BY'");
		}
		return db;
	}

	public DefaultDSLContext db(Integer shopId) {
		if (shopDbList.containsKey(shopId)) {
			return shopDbList.get(shopId);
		}

		ShopRecord shop = db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchAny();
		if (shop != null) {
			DbConfig dbConfig = Util.parseJSON(shop.getDbConfig(), DbConfig.class);
			if (dbConfig == null) {
				return null;
			}

			String url = getJdbcUrl(dbConfig.host, dbConfig.database);
			BasicDataSource ds = dataSource(url, dbConfig.username, dbConfig.password, driver);			
			DefaultDSLContext dsl = new DefaultDSLContext(configuration(ds));
			shopDbList.put(shopId, dsl);
			dsl.setSchema(dbConfig.database);
			dsl.execute("SET NAMES utf8mb4");
			dsl.execute("Set sql_mode='ONLY_FULL_GROUP_BY'");
			return dsl;
		}
		return null;
	}

	public DefaultDSLContext getShopDsl() {
		String url = getJdbcUrl(shopHost, "");
		BasicDataSource ds = dataSource(url, shopUsername, shopPassword, driver);
		DefaultDSLContext dsl = new DefaultDSLContext(configuration(ds));
		dsl.execute("SET NAMES utf8mb4");
		dsl.execute("Set sql_mode='ONLY_FULL_GROUP_BY'");
		return dsl;
	}

	public boolean installShopDb(DbConfig dbConfig) {
		try {
			String sql = "create database " + dbConfig.database + " default charset utf8mb4 collate utf8mb4_unicode_ci";
			getShopDsl().execute(sql);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}

		boolean ret = execScript(dbConfig.host, dbConfig.database, dbConfig.username, dbConfig.password,
				"db/shop/db_shop.sql");
		if (ret) {
			ret = execScript(dbConfig.host, dbConfig.database, dbConfig.username, dbConfig.password,
					"db/shop/db_shop_data.sql");
		}
		return ret;
	}

	public boolean execScript(String host, String database, String username, String password, String sqlPath) {
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
		executer.setDriver(driver);
		executer.setPassword(password);
		executer.setUserid(username);
		executer.setUrl(this.getJdbcUrl(host, database));
		try {
			executer.execute();
		} catch (BuildException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

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

	public DbConfig getInstallShopDbConfig(Integer shopId) {
		DbConfig dbConfig = new DbConfig();
		dbConfig.host = shopHost;
		dbConfig.database = shopDbPrefix + shopId.toString();
		dbConfig.username = shopUsername;
		dbConfig.password = shopPassword;
		return dbConfig;
	}

	protected String getJdbcUrl(String host, String database) {
		return "jdbc:mysql://" + host + "/" + database
				+ "?serverTimezone=Hongkong&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
	}

	protected BasicDataSource dataSource(String url, String username, String password, String driver) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driver);
		return dataSource;
	}

	protected DefaultConfiguration configuration(BasicDataSource dataSource) {
		DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
		jooqConfiguration.set(new DataSourceConnectionProvider(dataSource));
		jooqConfiguration.set(new DefaultExecuteListenerProvider(new SqlExcuteListener()));
		SQLDialect dialect = SQLDialect.valueOf(this.dialect);
		jooqConfiguration.set(dialect);
		
		Settings settings = new Settings();
		settings.withRenderCatalog(false).withRenderSchema(false);
		jooqConfiguration.setSettings(settings);
		return jooqConfiguration;
	}

}

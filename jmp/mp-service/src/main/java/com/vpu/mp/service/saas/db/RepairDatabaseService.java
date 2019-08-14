package com.vpu.mp.service.saas.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.database.DatabaseManager;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.db.Column;
import com.vpu.mp.service.pojo.saas.db.ConnectStr;
import com.vpu.mp.service.pojo.saas.db.Index;
import com.vpu.mp.service.pojo.saas.db.Table;

/**
 * 
 * @author lixinguo
 *
 */
@Service
public class RepairDatabaseService extends MainBaseService {

	@Autowired
	DatabaseManager databaseManager;
	
	/**
	 * 修复主库
	 */
	public void repairMainDb() {
		String sql = Util.loadResource("db/main/db_main.sql");
		List<Table> tables = this.parseSql(sql);
		repairDb(tables, databaseManager.mainDb());
	}

	/**
	 * 修复店铺库
	 * @param shopId
	 */
	public void repairShopDb(Integer shopId) {
		String sql = Util.loadResource("db/main/db_shop.sql");
		List<Table> tables = this.parseSql(sql);
		databaseManager.switchShopDb(shopId);
		repairDb(tables, databaseManager.currentShopDb());
		databaseManager.restoreLastShopDb();
	}

	/**
	 * 修复所有店铺库
	 */
	public void repairAllShopDb() {
		String sql = Util.loadResource("db/main/db_shop.sql");
		List<Table> tables = this.parseSql(sql);
		Result<ShopRecord> shops = saas().shop.getAll();
		for (ShopRecord shop : shops) {
			databaseManager.switchShopDb(shop.getShopId());
			repairDb(tables, databaseManager.currentShopDb());
			databaseManager.restoreLastShopDb();
		}
	}

	/**
	 * 修复数据库所有表字段和索引
	 * 
	 * @param tables
	 * @param db
	 */
	public void repairDb(List<Table> tables, DefaultDSLContext db) {
		for (Table table : tables) {
			// if (table.getTableName().equals("b2c_upload_uyun_record"))
			{
				this.processTable(table, db);
			}

		}

	}

	/**
	 * 处理单表
	 * 
	 * @param table
	 * @param db
	 */
	public void processTable(Table table, DefaultDSLContext db) {
		if (isTableExists(table.tableName, db)) {
			Result<Record> columnRecords = db.fetch("show columns from " + table.tableName);
			for (int i = 0; i < table.columns.size(); i++) {
				this.processColumn(table, i, columnRecords, db);
			}

			Result<Record> indexRecords = db.fetch("show indexes from " + table.tableName);
			for (int i = 0; i < table.indexes.size(); i++) {
				this.processIndex(table, i, indexRecords, db);
			}
		} else {
			logger().debug("tableSql:" + table.createSql);
			// db.execute(table.createSql);
		}
	}

	/**
	 * 修复表字段
	 * 
	 * @param table
	 * @param colIdx
	 * @param records
	 * @param db
	 */
	public void processColumn(Table table, int colIdx, Result<Record> records, DefaultDSLContext db) {
		Column col = table.columns.get(colIdx);
		boolean found = false;
		String regex0 = "(\\w+)\\((\\d+),(\\d+)\\)\\s+unsigned";
		String regex1 = "(\\w+)\\((\\d+),(\\d+)\\)";
		String regex2 = "(\\w+)\\((\\d+)\\)";
		String regex3 = "(\\w+)";
		String sql = "";
		for (Record r : records) {
			if (StringUtils.equalsIgnoreCase(r.get("Field").toString(), col.getField())) {
				found = true;
				String type = r.get("Type").toString();
				Column colFromDb = new Column();
				colFromDb.setField(col.getField());
				colFromDb.setNullType(r.get("Null").toString());
				colFromDb.setDefaultValue(r.get("Default") == null ? null : r.get("Default").toString());
				Matcher m;
				if ((m = Pattern.compile(regex0, Pattern.CASE_INSENSITIVE).matcher(type)).find()) {
					colFromDb.setType(m.group(1));
					colFromDb.setTypeRange1(m.group(2));
					colFromDb.setTypeRange2(m.group(3));
					colFromDb.setTypeUnsigned("unsigned");
				} else if ((m = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE).matcher(type)).find()) {
					colFromDb.setType(m.group(1));
					colFromDb.setTypeRange1(m.group(2));
					colFromDb.setTypeRange2(m.group(3));
				} else if ((m = Pattern.compile(regex2, Pattern.CASE_INSENSITIVE).matcher(type)).find()) {
					colFromDb.setType(m.group(1));
					colFromDb.setTypeRange1(m.group(2));
				} else if ((m = Pattern.compile(regex3, Pattern.CASE_INSENSITIVE).matcher(type)).find()) {
					colFromDb.setType(m.group(1));
				}

				// 默认精度处理
				if (!StringUtils.isBlank(colFromDb.getTypeRange1()) && StringUtils.isBlank(col.getTypeRange1())) {
					col.setTypeRange1(colFromDb.getTypeRange1());
				}
				if (!StringUtils.isBlank(colFromDb.getTypeRange2()) && StringUtils.isBlank(col.getTypeRange2())) {
					col.setTypeRange2(colFromDb.getTypeRange2());
				}

				if (!Column.isEquals(col, colFromDb)) {
					// 列不同,则进行字段修改
					sql = "alter table " + table.getTableName() + " modify column " + col.getCreateSql();
				}
				break;

			}
		}
		if (!found) {
			sql = "alter table " + table.getTableName() + " add column " + col.getCreateSql();
		}
		if (!StringUtils.isBlank(sql)) {
			logger().debug(sql);
			// db.execute(sql);
		}
		return;

	}

	/**
	 * 修复表索引
	 * 
	 * @param table
	 * @param indexIdx
	 * @param records
	 * @param db
	 */
	public void processIndex(Table table, int indexIdx, Result<Record> records, DefaultDSLContext db) {
		Index index = table.indexes.get(indexIdx);
		int findIndexCols = 0;
		boolean findKeyName = false;
		String sql = "";
		for (Record r : records) {
			if (StringUtils.equalsIgnoreCase(r.get("Key_name").toString(), index.getKeyName())) {
				findKeyName = true;
				if (StringUtils.equalsAnyIgnoreCase(r.get("Column_name").toString(),
						index.getColumnNames().toArray(new String[0]))) {
					findIndexCols++;
				}
			}
		}

		if (findKeyName) {
			if (findIndexCols != index.getColumnNames().size()) {
				sql = indexSql(index, table.getTableName(), true);
			}
		} else {
			sql = indexSql(index, table.getTableName(), false);
		}
		if (!StringUtils.isBlank(sql)) {
			logger().debug(sql);
			// db.execute(sql);
		}
		return;
	}

	/**
	 * 索引语句生成
	 * 
	 * @param index
	 * @param tableName
	 * @param modify
	 * @return
	 */
	protected String indexSql(Index index, String tableName, boolean modify) {
		String format = "alter table %s %s add %s key %s";
		String keyProp = "";
		String primary = "PRIMARY";
		String unique = "0";
		String dropKey = "";
		String cols = StringUtils.join(index.getColumnNames(), ",");
		
		if (modify) {
			if (primary.equals(index.getKeyName())) {
				dropKey = "drop primary key,";
			} else {
				dropKey = "drop index `" + index.getKeyName() + "`,";
			}
		}
		
		
		if (primary.equals(index.getKeyName())) {
			keyProp = "primary";
		} else if (unique.equals(index.getNonUnique())) {
			keyProp = "unique";
		} 
		
		return String.format(format,tableName,dropKey,keyProp,cols);
	}

	/**
	 * 判断表是否存在
	 * 
	 * @param tableName
	 * @param db
	 * @return
	 */
	public boolean isTableExists(String tableName, DefaultDSLContext db) {
		Result<Record> tables = db.fetch("show tables like '" + tableName + "'");
		return tables.size() > 0;
	}

	/**
	 * 解析SQL文件，分析出表 字段 和 索引
	 * 
	 * @param sql
	 * @return
	 */
	public List<Table> parseSql(String sql) {
		List<Table> tables = new ArrayList<Table>();
		sql = sql.replaceAll("`", "");
		String createTableRegex = "create\\s+table\\s+(.*?)\\s*\\((.*?)\\)[^\\)]*?;";
		Pattern pattern = Pattern.compile(createTableRegex,
				Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(sql);
		while (matcher.find()) {
			Table table = new Table();
			table.tableName = matcher.group(1);
			table.createSql = String.format("create table %s(%s)", matcher.group(1), matcher.group(2));
			String[] columns = matcher.group(2).split(",\\s*\r\n");
			for (String col : columns) {
				String[] tokens = col.trim().replaceAll("comment\\s+.*", "").split("\\s+");
				tokens = this.connect(tokens);

				if (tokens.length == 0) {
					logger().error("tokens.length == 0");
					continue;
				}
				if (isIndex(tokens[0])) {
					this.parseIndex(tokens, table, col.trim());
				} else {
					this.parseColumn(tokens, table, col.trim());
				}
			}
			tables.add(table);
		}

		return tables;
	}

	/**
	 * 解析表字段
	 * 
	 * @param tokens
	 * @param table
	 * @param sql
	 */
	public void parseColumn(String[] tokens, Table table, String sql) {

		int len = tokens.length;
		Column col = new Column();
		col.setField(tokens[0]);
		col.setCreateSql(sql);

		int i = 1;
		while (i < len) {

			if (i == 1) {
				int p = tokens[i].indexOf("(");
				if (p == -1) {
					col.setType(tokens[i]);
					i++;
					if (i < len) {
						p = tokens[i].indexOf("(");
					}

				} else {
					col.setType(tokens[i].substring(0, p));
				}
				if (p != -1) {
					{
						String[] props = tokens[i].substring(p + 1, tokens[i].length() - 1).split(",");
						col.setTypeRange1(props.length > 0 ? props[0] : "");
						col.setTypeRange2(props.length > 1 ? props[1] : "");
						i++;
					}
				}
				continue;
			}

			// process other
			switch (tokens[i].toUpperCase()) {
			case "NOT": {
				col.setNullType("NO");
				i += 2;
				break;
			}
			case "UNSIGNED": {
				col.setTypeUnsigned("unsigned");
				i++;
				break;
			}
			case "NULL":
			case "AUTO_INCREMENT": {
				i++;
				break;
			}
			case "COMMENT": {
				i += 2;
				break;
			}
			case "COLLATE": {
				i += 2;
				break;
			}
			case "ON": {
				i += 3;
				break;
			}
			case "DEFAULT": {
				i++;
				if (tokens[i].startsWith("\"") || tokens[i].startsWith("'")) {
					col.setDefaultValue(tokens[i].substring(1, tokens[i].length() - 1));
				} else {
					col.setDefaultValue(tokens[i].equalsIgnoreCase("null") ? null : tokens[i]);
				}
				i++;
				break;
			}
			default: {
				logger().warn("column token " + tokens[i] + "not found");
				i++;
			}
			}
		}
		table.columns.add(col);
	}

	/**
	 * 解析表索引
	 * 
	 * @param tokens
	 * @param table
	 * @param sql
	 */
	public void parseIndex(String[] tokens, Table table, String sql) {
		int len = tokens.length;
		Index index = new Index();
		index.setCreateSql(sql);

		int i = 0;
		while (i < len) {

			switch (tokens[i].toUpperCase()) {
			case "PRIMARY": {
				index.setKeyName("PRIMARY");
				index.setNonUnique("0");
				i += 1;
				break;

			}
			case "UNIQUE": {
				index.setNonUnique("0");
				i += 1;
				break;
			}

			case "KEY":
			case "INDEX": {
				i++;
				int p = tokens[i].indexOf("(");
				if (p == -1) {
					index.setKeyName(tokens[i]);
					i++;
					p = 0;
				}

				String[] props = tokens[i].substring(p + 1, tokens[i].length() - 1).split(",");
				index.setColumnNames(Arrays.asList(props));
				if (StringUtils.isBlank(index.getKeyName())) {
					index.setKeyName(props[0]);
				}
				i++;
				break;
			}
			case "USING": {
				i += 2;
				break;
			}
			default: {
				logger().warn("unkown index token " + tokens[i]);
				i++;

			}
			}
		}
		table.indexes.add(index);
	}

	/**
	 * 辅助函数，连接括号 单 双引号
	 * 
	 * @param tokens
	 * @return
	 */
	public String[] connect(String[] tokens) {
		List<String> result = new ArrayList<String>();
		int i = 0;
		while (i < tokens.length) {
			tokens[i] = tokens[i].trim();
			if (tokens[i].contains("(") && !tokens[i].contains(")")) {
				ConnectStr c = getConnectStr(tokens, i, ")");
				i = c.getLastIndex() + 1;
				result.add(c.str);
				continue;
			}
			if (tokens[i].startsWith("\"") && !tokens[i].endsWith("\"")) {
				ConnectStr c = getConnectStr(tokens, i, "\"");
				i = c.getLastIndex() + 1;
				result.add(c.str);
				continue;
			}

			if (tokens[i].startsWith("'") && !tokens[i].endsWith("'")) {
				ConnectStr c = getConnectStr(tokens, i, "'");
				i = c.getLastIndex() + 1;
				result.add(c.str);
				continue;
			}
			String token = tokens[i].trim();
			if (!StringUtils.isBlank(token)) {
				result.add(token);
			}
			i++;

		}
		return result.toArray(new String[0]);

	}

	/**
	 * 得到连接串，连接括号 单 双引号
	 * 
	 * @param tokens
	 * @param i
	 * @param t
	 * @return
	 */
	public ConnectStr getConnectStr(String[] tokens, int i, String t) {
		ConnectStr c = new ConnectStr();
		c.setLastIndex(i);
		while (i < tokens.length) {
			c.setLastIndex(i);
			if (c.str.equals("")) {
				c.str += tokens[i];
			} else {
				c.str += (t.equals(")") ? "" : " ") + tokens[i];
			}
			if (tokens[i].endsWith(t)) {
				break;
			}
			i++;
		}
		return c;
	}

	/**
	 * 判断串是否双索引语句
	 * 
	 * @param key
	 * @return
	 */
	protected boolean isIndex(String key) {
		return (StringUtils.equalsAnyIgnoreCase(key, "primary", "key", "unique", "index", "constraint"));
	}

}

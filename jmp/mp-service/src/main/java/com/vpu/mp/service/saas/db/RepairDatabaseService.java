package com.vpu.mp.service.saas.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.database.DatabaseManager;
import com.vpu.mp.service.foundation.database.MpDefaultDslContext;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.db.Column;
import com.vpu.mp.service.pojo.saas.db.Index;
import com.vpu.mp.service.pojo.saas.db.Table;

import lombok.Getter;
import lombok.Setter;

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
	 * 是否只是检查SQL，不执行SQL
	 */
	@Getter
	@Setter
	protected Boolean onlyCheck = false;

	/**
	 * 修复主库
	 */
	public void repairMainDb(Boolean onlyCheck) {
		logger().info("repairMainDb(onlyCheck={}) start.", onlyCheck);
		this.onlyCheck = onlyCheck;
		String sql = Util.loadResource("db/main/db_main.sql");
		List<Table> tables = this.parseSql(sql);
		repairDb(tables, (MpDefaultDslContext) databaseManager.mainDb());
		logger().info("repairMainDb(onlyCheck={}) done.", onlyCheck);
	}

	/**
	 * 修复店铺库
	 * 
	 * @param shopId
	 */
	public void repairShopDb(Boolean onlyCheck, Integer shopId) {
		logger().info("repairShopDb(onlyCheck={})  shopId:{} start.", onlyCheck, shopId);
		this.onlyCheck = onlyCheck;
		String sql = Util.loadResource("db/main/db_shop.sql");
		List<Table> tables = this.parseSql(sql);
		databaseManager.switchShopDb(shopId);
		repairDb(tables, databaseManager.currentShopDb());
		logger().info("repairShopDb(onlyCheck={})  shopId:{} done.", onlyCheck, shopId);
	}

	/**
	 * 修复所有店铺库
	 */
	public void repairAllShopDb(Boolean onlyCheck) {
		logger().info("repairAllShopDb(onlyCheck={}) start.", onlyCheck);
		this.onlyCheck = onlyCheck;
		String sql = Util.loadResource("db/shop/db_shop.sql");
		List<Table> tables = this.parseSql(sql);
		Result<ShopRecord> shops = saas().shop.getAll();
		for (ShopRecord shop : shops) {
			logger().info("repairDb(onlyCheck={})  shopId:{} ...", onlyCheck, shop.getShopId());
			databaseManager.switchShopDb(shop.getShopId());
			repairDb(tables, databaseManager.currentShopDb());
		}
		logger().info("repairAllShopDb(onlyCheck={}) done.", onlyCheck);
	}

	/**
	 * 修复数据库所有表字段和索引
	 * 
	 * @param tables
	 * @param db
	 */
	public void repairDb(List<Table> tables, MpDefaultDslContext db) {
		for (Table table : tables) {
			if (table.getTableName().equals("b2c_order_info"))
			{
				table.setDatabseName(db.getDbConfig().getDatabase());
				table.setFullTableName(table.getDatabseName() + "." + table.getTableName());
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
	public void processTable(Table table, MpDefaultDslContext db) {
		if (isTableExists(table, db)) {
			Result<Record> columnRecords = db.fetch("show columns from " + table.getFullTableName());
			for (int i = 0; i < table.columns.size(); i++) {
				this.processColumn(table, i, columnRecords, db);
			}

			Result<Record> indexRecords = db.fetch("show indexes from " + table.getFullTableName());
			for (int i = 0; i < table.indexes.size(); i++) {
				this.processIndex(table, i, indexRecords, db);
			}
		} else {
			String sql = table.createSql.replace(table.getTableName(), table.getFullTableName());
			this.executeSql(db, sql);
		}
	}

	protected void executeSql(MpDefaultDslContext db, String sql) {
		try {
			if (this.onlyCheck) {
				logger().info("Check: Need Execute SQL: {}", sql);
				return;
			}
			logger().info("Repair Db, Execute SQL: {}", sql);
			db.execute(sql);
		} catch (DataAccessException e) {
			logger().error("Execute SQL Exception, message: {} ", e.getMessage());
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
	public void processColumn(Table table, int colIdx, Result<Record> records, MpDefaultDslContext db) {
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
					sql = "alter table " + table.getFullTableName() + " modify column " + col.getCreateSql();
				}
				break;

			}
		}
		if (!found) {
			sql = "alter table " + table.getFullTableName() + " add column " + col.getCreateSql();
		}
		if (!StringUtils.isBlank(sql)) {
			this.executeSql(db, sql);
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
	public void processIndex(Table table, int indexIdx, Result<Record> records, MpDefaultDslContext db) {
		Index index = table.indexes.get(indexIdx);
		int findIndexCols = 0;
		boolean findKeyName = false;
		String sql = "";
		for (Record r : records) {
			if (StringUtils.equalsIgnoreCase(r.get("Key_name").toString(), index.getKeyName())) {
				findKeyName = true;
				String col = r.get("Column_name").toString();
				for (String indexCol : index.getColumnNames()) {
					if (indexCol.equals(col)) {
						findIndexCols++;
					}
				}
			}
		}

		if (findKeyName) {
			if (findIndexCols != index.getColumnNames().size()) {
				sql = indexSql(index, table.getFullTableName(), true);
			}
		} else {
			sql = indexSql(index, table.getFullTableName(), false);
		}
		if (!StringUtils.isBlank(sql)) {
			this.executeSql(db, sql);
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
		String format = "alter table %s %s add %s key %s (%s)";
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

		return String.format(format, tableName, dropKey, keyProp, index.getKeyName(), cols);
	}

	/**
	 * 判断表是否存在
	 * 
	 * @param table
	 * @param db
	 * @return
	 */
	public boolean isTableExists(Table table, MpDefaultDslContext db) {
		Result<Record> tables = db
				.fetch("show tables from " + table.getDatabseName() + " like '" + table.getTableName() + "'");
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
		String createTableRegex = "create\\s+table\\s+(.*?)\\s*\\((.*?)\n\\s*\\)[^\\)]*?;";
		Pattern pattern = Pattern.compile(createTableRegex,
				Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(sql);
		while (matcher.find()) {
			Table table = new Table();
			table.tableName = matcher.group(1);
//			if(!"b2c_presale".equals(table.tableName)) {
//				continue;
//			}
			table.createSql = String.format("create table %s(%s)", matcher.group(1), matcher.group(2));
			String[] columns = matcher.group(2).split(",\\s*\n");
			for (String col : columns) {
				if(StringUtils.isBlank(col.trim())) {
					continue;
				}
				String[] tokens = this.parseTokens(col).toArray(new String[0]);
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

	protected List<String> parseTokens(String str) {
		List<String> result = new ArrayList<>();
		str = str.trim();
		int i = 0;
		StringBuffer buf = new StringBuffer();
		while (i < str.length()) {
			char ch = str.charAt(i);
			if (ch == '"' || ch == '\'' || ch == '(') {
				bufAddAndReset(result, buf);

				char lastCh = ch == '(' ? ')' : ch;
				buf.append(ch);
				i++;
				while (i < str.length()) {
					buf.append(str.charAt(i));
					if (str.charAt(i) == lastCh) {
						break;
					}
					i++;
				}
				bufAddAndReset(result, buf);
			} else {
				if (!String.valueOf(ch).matches("\\s")) {
					buf.append(ch);
				} else {
					bufAddAndReset(result, buf);
				}
			}
			i++;
		}
		bufAddAndReset(result, buf);
		return result;
	}

	protected void bufAddAndReset(List<String> result, StringBuffer buf) {
		if (buf.length() > 0) {
			result.add(buf.toString());
			buf.setLength(0);
		}
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
				col.setType(tokens[i]);
				i++;
				if (i < len && tokens[i].startsWith("(") && tokens[i].endsWith(")")) {
					String[] props = tokens[i].substring(1, tokens[i].length() - 1).replaceAll("\\s", "").split(",");
					col.setTypeRange1(props[0]);
					col.setTypeRange2(props.length > 1 ? props[1] : "");
					if(StringUtils.equals("timestamp", tokens[i])) {
						col.setTypeRange1("");
					}
					i++;
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
			case "CHARACTER":
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
			case "SET": {
				i += 2;
				break;
			}
			case "ON": {
				i += 3;
				if(i <len && tokens[i].startsWith("(")) {
					i++;
				}
				break;
			}
			case "DEFAULT": {
				i++;
				if (tokens[i].startsWith("\"") || tokens[i].startsWith("'")) {
					col.setDefaultValue(tokens[i].substring(1, tokens[i].length() - 1));
				} else {
					col.setDefaultValue(tokens[i].equalsIgnoreCase("null") ? null : tokens[i]);
					if(i+1 < len && tokens[i+1].startsWith("(")) {
						col.setDefaultValue(tokens[i]+tokens[i+1]);
						i++;
					}
				}
				i++;
				break;
			}
			default: {
				logger().warn("{} column tokens{}, i={},{} not found!", table.getTableName(), tokens.toString(),tokens[i]);
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
				if (!tokens[i].startsWith("(")) {
					index.setKeyName(tokens[i]);
					i++;
				}
				if (tokens[i].startsWith("(") && tokens[i].endsWith(")")) {
					String[] cols = tokens[i].substring(1, tokens[i].length() - 1).replaceAll("\\s", "").split(",");
					if (StringUtils.isBlank(index.getKeyName())) {
						index.setKeyName(cols[0]);
					}
					index.setColumnNames(Arrays.asList(cols));
					i++;
				}
				break;
			}
			case "USING": {
				i += 2;
				break;
			}
			default: {
				logger().warn("{} found unkown index token {}.", table.getTableName(), tokens[i]);
				i++;

			}
			}
		}
		table.indexes.add(index);
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

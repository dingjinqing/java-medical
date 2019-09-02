package com.vpu.mp.service.pojo.saas.db;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author lixinguo
 *
 */
@Data
public class Table {
	public String databseName;
	public String tableName;
	public String fullTableName;
	public List<Column> columns = new ArrayList<Column>();
	public List<Index> indexes = new ArrayList<Index>();
	public String createSql;
}

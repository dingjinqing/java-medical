package com.vpu.mp.service.pojo.saas.db;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class Table {
	public String tableName;
	public List<Column> columns = new ArrayList<Column>();
	public List<Index> indexes = new ArrayList<Index>();
	public String createSql;
}

package com.vpu.mp.service.foundation;

import java.sql.SQLException;
import java.util.List;

import org.jooq.ExecuteContext;
import org.jooq.impl.DefaultExecuteListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 新国
 *
 */
public class SqlExcuteListener extends DefaultExecuteListener {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void executeStart(ExecuteContext ctx) {
		String ignoreSql = "SET ";
		String sql = ctx.sql();
		if (ctx.query() != null) {
			List<Object> binds = ctx.query().getBindValues();
			StringBuffer buf = new StringBuffer();
			int p;
			int i = 0;
			while (true) {
				p = sql.indexOf('?');
				if (p == -1) {
					buf.append(sql);
					break;
				} else {
					buf.append(sql.substring(0, p)).append(binds.get(i));
					sql = sql.substring(p + 1);
					i++;
				}
			}
			sql = buf.toString();
		}
		if (sql.startsWith(ignoreSql)) {
			return;
		}
		String schema = "";
		try {
			schema = ctx.connection().getSchema();
		} catch (SQLException e) {
		}
		String message = "SQL DB: " + schema + " -- " + sql;
		logger.debug(message);
	}
}

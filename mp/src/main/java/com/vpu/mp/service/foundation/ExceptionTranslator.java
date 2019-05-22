package com.vpu.mp.service.foundation;

import java.util.List;

import org.jooq.ExecuteContext;
import org.jooq.impl.DefaultExecuteListener;

/**
 * 
 * @author 新国
 *
 */
public class ExceptionTranslator extends DefaultExecuteListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void bindEnd(ExecuteContext ctx) {
		String ignoreSql = "SET ";
		String sql = ctx.sql();
		if(ctx.query() != null) {
			List<Object> binds = ctx.query().getBindValues();
			StringBuffer buf = new StringBuffer();
			int p;
			int i = 0;
			while(true) {
				p=sql.indexOf('?' );
				if(p == -1) {
					buf.append(sql);
					break;
				}else {
					buf.append(sql.substring(0,p)).append(binds.get(i));
					sql = sql.substring(p+1);
					i++;
				}
			}
			sql = buf.toString();
		}
		if (sql.startsWith(ignoreSql)) {
			return;
		}
		System.out.println("executeStart SQL:" + sql);
	}

	@Override
	public void executeStart(ExecuteContext ctx) {

	}

	@Override
	public void exception(ExecuteContext context) {
		System.out.println("Access database using jOOQ Exception:" + context.sql() + ", Message:"
				+ context.sqlException().getMessage());
	}
}

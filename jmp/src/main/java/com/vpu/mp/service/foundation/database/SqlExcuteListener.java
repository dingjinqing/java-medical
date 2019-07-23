package com.vpu.mp.service.foundation.database;

import static java.lang.Boolean.TRUE;
import static org.jooq.impl.DSL.val;
import static org.jooq.tools.StringUtils.abbreviate;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteType;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.VisitContext;
import org.jooq.VisitListener;
import org.jooq.VisitListenerProvider;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.impl.DefaultVisitListener;
import org.jooq.impl.DefaultVisitListenerProvider;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.support.SpringUtil;

/**
 * 
 * @author 新国
 *
 */

public class SqlExcuteListener extends DefaultExecuteListener {

	private static final JooqLogger LOGGER = JooqLogger.getLogger(SqlExcuteListener.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Override
//	public void executeStart(ExecuteContext ctx) {
//		String ignoreSql = "SET ";
//		String sql = ctx.sql();
//		if (ctx.query() != null) {
//			List<Object> binds = ctx.query().getBindValues();
//			StringBuffer buf = new StringBuffer();
//			int p;
//			int i = 0;
//			while (true) {
//				p = sql.indexOf('?');
//				if (p == -1) {
//					buf.append(sql);
//					break;
//				} else {
//					buf.append(sql.substring(0, p)).append(binds.get(i));
//					sql = sql.substring(p + 1);
//					i++;
//				}
//			}
//			sql = buf.toString();
//		}
//		if (sql.startsWith(ignoreSql)) {
//			return;
//		}
//		String schema = "";
//		try {
//			schema = ctx.connection().getSchema();
//		} catch (SQLException e) {
//		}
//		String message = "SQL DB: " + schema + " -- " + sql;
////		logger.debug(message);
//	}

	private static final int BIND_PARAM_MAX_LENGTH = 2000;

	@Override
	public void renderEnd(ExecuteContext ctx) {

		if (LOGGER.isDebugEnabled()) {
			Configuration configuration = ctx.configuration();
			String newline = TRUE.equals(configuration.settings().isRenderFormatted()) ? "\n" : "";

			// [#2939] Prevent excessive logging of bind variables only in DEBUG mode, not
			// in TRACE mode.
			if (!LOGGER.isTraceEnabled()) {
				configuration = abbreviateBindVariables(configuration);
			}
			String[] batchSql = ctx.batchSQL();
			if (ctx.query() != null) {

				// Actual SQL passed to JDBC
				LOGGER.debug("Executing query", newline + ctx.sql());

				// [#1278] DEBUG log also SQL with inlined bind values, if
				// that is not the same as the actual SQL passed to JDBC
				String inlined = DSL.using(configuration).renderInlined(ctx.query());
				if (!ctx.sql().equals(inlined)) {
					LOGGER.debug("-> with bind values", newline + inlined);
				}
			}

			// [#2987] Log routines
			else if (ctx.routine() != null) {
				LOGGER.debug("Calling routine", newline + ctx.sql());

				String inlined = DSL.using(configuration).renderInlined(ctx.routine());

				if (!ctx.sql().equals(inlined)) {
					LOGGER.debug("-> with bind values", newline + inlined);
				}
			}

			else if (!StringUtils.isBlank(ctx.sql())) {

				// [#1529] Batch queries should be logged specially
				if (ctx.type() == ExecuteType.BATCH) {
					LOGGER.debug("Executing batch query", newline + ctx.sql());
				} else {
					LOGGER.debug("Executing query", newline + ctx.sql());
				}
			}

			// [#2532] Log a complete BatchMultiple query
			else if (batchSql.length > 0) {
				if (batchSql[batchSql.length - 1] != null) {
					for (String sql : batchSql) {
						LOGGER.debug("Executing batch query", newline + sql);
					}
				}
			}
		}

		// 替换店铺库的Schema
		final String defaultShopDbSchema ="mini_shop_471752";
		String currentSql = ctx.sql();
		if (currentSql != null && currentSql.contains(defaultShopDbSchema)) {
			DatabaseManager databaseManager = (DatabaseManager) SpringUtil.getBean("databaseManager");
			currentSql = ctx.sql().replaceAll(defaultShopDbSchema, databaseManager.getCurrentShopDbSchema());
			System.out.println(currentSql);
			ctx.sql(currentSql);
		}
	}


	/**
	 * Add a {@link VisitListener} that transforms all bind variables by
	 * abbreviating them.
	 */
	private final Configuration abbreviateBindVariables(Configuration configuration) {
		VisitListenerProvider[] oldProviders = configuration.visitListenerProviders();
		VisitListenerProvider[] newProviders = new VisitListenerProvider[oldProviders.length + 1];
		System.arraycopy(oldProviders, 0, newProviders, 0, oldProviders.length);
		newProviders[newProviders.length - 1] = new DefaultVisitListenerProvider(new BindValueAbbreviator());

		return configuration.derive(newProviders);
	}

	private static class BindValueAbbreviator extends DefaultVisitListener {

		private boolean anyAbbreviations = false;

		@Override
		public void visitStart(VisitContext context) {
			if (context.renderContext() != null) {
				QueryPart part = context.queryPart();

				if (part instanceof Param<?>) {
					Param<?> param = (Param<?>) part;
					Object value = param.getValue();

					if (value instanceof String && ((String) value).length() > BIND_PARAM_MAX_LENGTH) {
						anyAbbreviations = true;
						context.queryPart(val(abbreviate((String) value, BIND_PARAM_MAX_LENGTH)));
					} else if (value instanceof byte[] && ((byte[]) value).length > BIND_PARAM_MAX_LENGTH) {
						anyAbbreviations = true;
						context.queryPart(val(Arrays.copyOf((byte[]) value, BIND_PARAM_MAX_LENGTH)));
					}
				}
			}
		}

		@Override
		public void visitEnd(VisitContext context) {
			if (anyAbbreviations) {
				if (context.queryPartsLength() == 1) {
					context.renderContext().sql(
							" -- Bind values may have been abbreviated for DEBUG logging. Use TRACE logging for very large bind variables.");
				}
			}
		}
	}

}

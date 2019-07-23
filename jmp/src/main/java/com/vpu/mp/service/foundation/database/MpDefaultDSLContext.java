package com.vpu.mp.service.foundation.database;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultDSLContext;

import lombok.Getter;
import lombok.Setter;

public class MpDefaultDSLContext extends DefaultDSLContext {

	@Getter @Setter protected DbConfig dbConfig;
	@Getter @Setter protected Integer shopId = 0;
	@Getter @Setter protected BasicDataSource ds;


	/**
	 * 
	 */
	private static final long serialVersionUID = 2971649046754906812L;

	public MpDefaultDSLContext(SQLDialect dialect) {
		super(dialect);
	}

	public MpDefaultDSLContext(Configuration configuration) {
		super(configuration);
	}

	public MpDefaultDSLContext(SQLDialect dialect, Settings settings) {
		super(dialect, settings);
	}

	public MpDefaultDSLContext(Connection connection, SQLDialect dialect) {
		super(connection, dialect);
	}

	public MpDefaultDSLContext(DataSource datasource, SQLDialect dialect) {
		super(datasource, dialect);
	}

	public MpDefaultDSLContext(ConnectionProvider connectionProvider, SQLDialect dialect) {
		super(connectionProvider, dialect);
	}

	public MpDefaultDSLContext(Connection connection, SQLDialect dialect, Settings settings) {
		super(connection, dialect, settings);
	}

	public MpDefaultDSLContext(DataSource datasource, SQLDialect dialect, Settings settings) {
		super(datasource, dialect, settings);
	}

	public MpDefaultDSLContext(ConnectionProvider connectionProvider, SQLDialect dialect, Settings settings) {
		super(connectionProvider, dialect, settings);
	}

}

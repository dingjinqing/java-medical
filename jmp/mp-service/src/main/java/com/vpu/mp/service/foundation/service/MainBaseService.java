package com.vpu.mp.service.foundation.service;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.Deque;

import org.jooq.Configuration;
import org.jooq.ContextTransactionalRunnable;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.SQLDataType;

/**
 * 
 * @author lixinguo
 *
 */
public class MainBaseService extends AbstractCommonBaseService {

	/**
	 * Main DB连接事务配置，线程内单例
	 */
	private static ThreadLocal<Deque<Configuration>> mainDbConfiguration = ThreadLocal.withInitial(ArrayDeque<Configuration>::new);

	/**
	 * 当前数据库
	 */
	@Override
	protected DefaultDSLContext db() {
		Deque<Configuration> config = mainDbConfiguration.get();
		if (config.peek() != null) {
			return (DefaultDSLContext) DSL.using(config.peek());
		}
		return databaseManager.mainDb();
	}

	/**
	 * 事务处理
	 * @param transactional
	 */
	protected  void transaction(ContextTransactionalRunnable transactional) {
		db().transaction((configuration) -> {
			Deque<Configuration> config = mainDbConfiguration.get();
			config.push(configuration);
			try {
				transactional.run();
			} finally {
				config.pop();
			}
		});
	}

    public Field<String> dateFormat(Field<Timestamp> field, String format) {
        return DSL.field("date_format({0}, {1})", SQLDataType.VARCHAR,
            field, DSL.inline(format));
    }
}

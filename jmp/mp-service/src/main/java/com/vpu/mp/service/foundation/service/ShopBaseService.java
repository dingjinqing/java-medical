package com.vpu.mp.service.foundation.service;

import java.util.ArrayDeque;
import java.util.Deque;

import org.jooq.Configuration;
import org.jooq.ContextTransactionalRunnable;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;

import com.vpu.mp.db.main.tables.records.ShopRecord;


/**
 * 
 * @author lixinguo
 *
 */
public class ShopBaseService extends AbstractCommonBaseService {

	/**
	 * Shop DB连接事务配置，线程内单例
	 */
	private static ThreadLocal<Deque<Configuration>> shopDbConfiguration = ThreadLocal.withInitial(ArrayDeque<Configuration>::new);



	/**
	 * 当前店铺连接
	 */
	@Override
	protected DefaultDSLContext db() {
		Deque<Configuration> config = shopDbConfiguration.get();
		if (config.peek() != null) {
			return (DefaultDSLContext) DSL.using(config.peek());
		}
		return databaseManager.currentShopDb();
	}

	/**
	 * 事务
	 * @param transactional
	 */
	protected void transaction(ContextTransactionalRunnable transactional) {
		db().transaction((configuration) -> {
			Deque<Configuration> config = shopDbConfiguration.get();
			config.push(configuration);
			try {
				transactional.run();
			} finally {
				config.pop();
			}
		});
	}
	
	/**
	 * 当前店铺Id
	 * 
	 * @return
	 */
	public Integer getShopId() {
		return databaseManager.getCurrentShopId();
	}
	
	/**
	 * 当前店铺对于SysId
	 * @return
	 */
	public Integer getSysId() {
		ShopRecord shop = saas.shop.getShopById(this.getShopId());
		return shop == null ? 0 : shop.getSysId();
	}
}

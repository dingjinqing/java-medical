package com.vpu.mp.service.foundation.service;

import org.jooq.ContextTransactionalRunnable;
import org.jooq.impl.DefaultDSLContext;


/**
 * 
 * @author lixinguo
 *
 */
public class ShopBaseService extends AbstractCommonBaseService {


	@Override
	protected DefaultDSLContext db() {
		return this.shopDb();
	}

	@Override
	protected void transaction(ContextTransactionalRunnable transactional) {
		this.shopTransaction(transactional);
	}
	
	/**
	 * 当前店铺Id
	 * 
	 * @return
	 */
	public Integer getShopId() {
		return databaseManager.getCurrentShopId();
	}
}

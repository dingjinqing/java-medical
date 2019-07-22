package com.vpu.mp.service.foundation.service;

import org.jooq.ContextTransactionalRunnable;
import org.jooq.impl.DefaultDSLContext;

import com.vpu.mp.service.foundation.database.DatabaseManager;



public class ShopBaseService extends CommonBaseService {


	@Override
	protected DefaultDSLContext db() {
		return this.shopDb();
	}

	@Override
	protected void transaction(ContextTransactionalRunnable transactional) {
		this.shopTransaction(transactional);
	}

	/**
	 * 切换当前店铺ID
	 * 
	 * @param shopId
	 */
	public void switchShopDb(Integer shopId) {
		databaseManager.switchShopDb(shopId);
	}
	
	/**
	 * 当前店铺Id
	 * 
	 * @return
	 */
	public Integer getShopId() {
		return DatabaseManager.getCurrentShopId();
	}
}

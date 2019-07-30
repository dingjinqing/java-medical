package com.vpu.mp.service.foundation.service;

import org.jooq.ContextTransactionalRunnable;
import org.jooq.impl.DefaultDSLContext;

/**
 * 
 * @author lixinguo
 *
 */
public class MainBaseService extends AbstractCommonBaseService {

	@Override
	protected DefaultDSLContext db() {
		return this.mainDb();
	}

	@Override
	protected  void transaction(ContextTransactionalRunnable transactional) {
		this.mainTransaction(transactional);
	}

}

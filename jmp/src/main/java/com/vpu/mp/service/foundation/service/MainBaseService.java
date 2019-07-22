package com.vpu.mp.service.foundation.service;

import org.jooq.ContextTransactionalRunnable;
import org.jooq.impl.DefaultDSLContext;

public class MainBaseService extends CommonBaseService {

	@Override
	protected DefaultDSLContext db() {
		return this.mainDb();
	}

	@Override
	protected  void transaction(ContextTransactionalRunnable transactional) {
		this.mainTransaction(transactional);
	}

}

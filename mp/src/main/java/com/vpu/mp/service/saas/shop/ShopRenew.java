package com.vpu.mp.service.saas.shop;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.tools.Convert;

import com.vpu.mp.db.main.tables.B2cShopAccount;
import com.vpu.mp.db.main.tables.B2cShopRenew;
import com.vpu.mp.service.foundation.BaseComponent;

/**
 * 
 * @author 新国
 *
 */
public class ShopRenew extends BaseComponent {

	protected B2cShopRenew tableShopRenew = B2cShopRenew.B2C_SHOP_RENEW;
	protected B2cShopAccount tableShopAccount = B2cShopAccount.B2C_SHOP_ACCOUNT;

	public Integer getShopNumber(Integer sysId) {
		return (Integer) db().select(DSL.count(tableShopRenew.SYS_ID)).from(tableShopRenew)
				.where(tableShopRenew.SYS_ID.eq(sysId)).fetchOne().get(0);
	}

	public Result<Record> getRenewList(Integer sysId) {
		return db().select().from(tableShopRenew).where(tableShopRenew.SYS_ID.eq(sysId))
				.orderBy(tableShopRenew.EXPIRE_TIME.desc()).fetch();
	}

	public double getRenewTotal(Integer sysId) {
		Result<Record> result = getRenewList(sysId);
		double total = 0.0;
		for (Record r : result) {
			double v = (double) Convert.convert(r.get(tableShopRenew.RENEW_MONEY), Number.class);
			total += v;
		}
		return total;
	}
}

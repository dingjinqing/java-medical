package com.vpu.mp.service.saas.shop;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.tools.Convert;

import static com.vpu.mp.db.main.tables.ShopRenew.SHOP_RENEW;

import java.sql.Date;

import com.vpu.mp.service.foundation.BaseComponent;

/**
 * 
 * @author 新国
 *
 */
public class CShopRenew extends BaseComponent {


	public void insertRenewDate() {
		
	}
	public Integer getShopNumber(Integer sysId) {
		return (Integer) db().select(DSL.count(SHOP_RENEW.SYS_ID)).from(SHOP_RENEW)
				.where(SHOP_RENEW.SYS_ID.eq(sysId)).fetchOne().get(0);
	}

	public Result<Record> getRenewList(Integer sysId) {
		return db().select().from(SHOP_RENEW).where(SHOP_RENEW.SYS_ID.eq(sysId))
				.orderBy(SHOP_RENEW.EXPIRE_TIME.desc()).fetch();
	}
	
	public Result<Record> getShopRenewList(Integer shopId) {
		return db().select().from(SHOP_RENEW).where(SHOP_RENEW.SHOP_ID.eq(shopId))
				.orderBy(SHOP_RENEW.EXPIRE_TIME.desc()).fetch();
	}

	public double getRenewTotal(Integer sysId) {
		Result<Record> result = getRenewList(sysId);
		double total = 0.0;
		for (Record r : result) {
			double v = (double) Convert.convert(r.get(SHOP_RENEW.RENEW_MONEY), Number.class);
			total += v;
		}
		return total;
	}
	
	public double getShopRenewTotal(Integer shopId) {
		Result<Record> result = getShopRenewList(shopId);
		double total = 0.0;
		for (Record r : result) {
			double v = (double) Convert.convert(r.get(SHOP_RENEW.RENEW_MONEY), Number.class);
			total += v;
		}
		return total;
	}
	
	public Date getShopRenewExpireTimel(Integer shopId) {
		Result<Record> result = getShopRenewList(shopId);
		if(result.size() > 0) {
			return result.get(0).get(SHOP_RENEW.EXPIRE_TIME);
		}
		return null;
	}
}

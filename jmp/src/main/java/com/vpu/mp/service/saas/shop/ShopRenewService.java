package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.ShopRenew.SHOP_RENEW;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.tools.Convert;

import com.vpu.mp.db.main.tables.records.ShopRenewRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.FieldsUtil;
import com.vpu.mp.service.pojo.saas.auth.SystemTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.auth.ShopRenewReq;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 新国
 *
 */
@Service
@Scope("prototype")
public class ShopRenewService extends BaseService {

	public void insertRenewDate() {

	}

	public Integer getShopNumber(Integer sysId) {
		return (Integer) db().select(DSL.count(SHOP_RENEW.SYS_ID)).from(SHOP_RENEW).where(SHOP_RENEW.SYS_ID.eq(sysId))
				.fetchAny(0);
	}

	public Result<Record> getRenewList(Integer sysId) {
		return db().select().from(SHOP_RENEW).where(SHOP_RENEW.SYS_ID.eq(sysId)).orderBy(SHOP_RENEW.EXPIRE_TIME.desc())
				.fetch();
	}

	public Result<Record> getShopRenewList(Integer shopId) {
		return db().select().from(SHOP_RENEW).where(SHOP_RENEW.SHOP_ID.eq(shopId))
				.orderBy(SHOP_RENEW.EXPIRE_TIME.desc()).fetch();
	}

	public BigDecimal getRenewTotal(Integer sysId) {
		Object total = db().select(DSL.sum(SHOP_RENEW.RENEW_MONEY)).from(SHOP_RENEW).where(SHOP_RENEW.SYS_ID.eq(sysId))
				.fetchAny(0);

		return total == null ? new BigDecimal("0") : Convert.convert(total, BigDecimal.class);
	}

	public BigDecimal getShopRenewTotal(Integer shopId) {
		Object total = db().select(DSL.sum(SHOP_RENEW.RENEW_MONEY)).from(SHOP_RENEW)
				.where(SHOP_RENEW.SHOP_ID.eq(shopId)).fetchAny(0);

		return total == null ? new BigDecimal(0) : Convert.convert(total, BigDecimal.class);
	}

	public Timestamp getShopRenewExpireTime(Integer shopId) {
		return db().select().from(SHOP_RENEW).where(SHOP_RENEW.SHOP_ID.eq(shopId))
				.orderBy(SHOP_RENEW.EXPIRE_TIME.desc()).fetchAny(SHOP_RENEW.EXPIRE_TIME);
	}

	public int insertShopRenew(ShopRenewReq sReq,SystemTokenAuthInfo info) {
		ShopRenewRecord sRecord=new ShopRenewRecord();
		FieldsUtil.assignNotNull(sReq, sRecord);
		if(info.isSubLogin()) {
			//子账户登录
			sRecord.setOperator(info.getSubAccountId());
		}else {
			sRecord.setOperator(0);
		}
		return db().executeInsert(sRecord);
	}
}

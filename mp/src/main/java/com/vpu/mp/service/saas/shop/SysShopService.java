package com.vpu.mp.service.saas.shop;

import org.apache.commons.lang3.ArrayUtils;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;

import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;
import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.ShopAccount.SHOP_ACCOUNT;
import static com.vpu.mp.db.main.tables.ShopRenew.SHOP_RENEW;

import java.util.Map;
import java.util.Random;

import com.vpu.mp.db.main.tables.pojos.Shop;
import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DbConfig;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;

/**
 * 
 * @author 新国
 *
 */
public class SysShopService extends BaseService {

	public ShopAccountService accout;
	public ShopRenewService renew;
	public ShopVersionService version;

	final public static class ShopListQueryParam {
		public Integer page;
		public String keywords;
		public Integer sysId;
		public Integer isUse;
		public String shopType;
		public String principalName;
		public String accountKey;
		public Byte shopFlag;
		public Byte isEnabled;
		public Byte hidBottom;
		public String act;

		public String getAct() {
			return act;
		}

		public void setAct(String act) {
			this.act = act;
		}

		public Integer getPage() {
			return page;
		}

		public void setPage(Integer page) {
			this.page = page;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public Integer getSysId() {
			return sysId;
		}

		public void setSysId(Integer sysId) {
			this.sysId = sysId;
		}

		public Integer getIsUse() {
			return isUse;
		}

		public void setIsUse(Integer isUse) {
			this.isUse = isUse;
		}

		public String getShopType() {
			return shopType;
		}

		public void setShopType(String shopType) {
			this.shopType = shopType;
		}

		public String getPrincipalName() {
			return principalName;
		}

		public void setPrincipalName(String principalName) {
			this.principalName = principalName;
		}

		public String getAccountKey() {
			return accountKey;
		}

		public void setAccountKey(String accountKey) {
			this.accountKey = accountKey;
		}

		public Byte getShopFlag() {
			return shopFlag;
		}

		public void setShopFlag(Byte shopFlag) {
			this.shopFlag = shopFlag;
		}

		public Byte getIsEnabled() {
			return isEnabled;
		}

		public void setIsEnabled(Byte isEnabled) {
			this.isEnabled = isEnabled;
		}

		public Byte getHidBottom() {
			return hidBottom;
		}

		public void setHidBottom(Byte hidBottom) {
			this.hidBottom = hidBottom;
		}

	};

	public PageResult getPageList(ShopListQueryParam param) {
		SelectWhereStep<Record> select = db()
				.select(SHOP.asterisk(),
						MP_AUTH_SHOP.APP_ID,
						MP_AUTH_SHOP.IS_AUTH_OK,
						MP_AUTH_SHOP.NICK_NAME,
						MP_AUTH_SHOP.PRINCIPAL_NAME)
				.from(SHOP)
				.join(SHOP_ACCOUNT).on(SHOP.SYS_ID.eq(SHOP_ACCOUNT.SYS_ID))
				.leftJoin(MP_AUTH_SHOP).on(SHOP.SHOP_ID.eq(DSL.cast(MP_AUTH_SHOP.SHOP_ID, Integer.class)));
		select = this.buildOptions(select, param);
		select.orderBy(SHOP.CREATED.desc());
		PageResult result = this.getPageResult(select, param.page);
		for (Map<String, Object> record : result.dataList) {
			Integer shopId = Util.convert(record.get("shop_id"), Integer.class, 0);
			Integer sysId = Util.convert(record.get("sys_id"), Integer.class, 0);
			ShopAccountRecord accountInfo = this.accout.getAccountInfoForID(sysId);
			record.put("renew_money", this.renew.getShopRenewTotal(shopId));
			record.put("expire_time", this.renew.getShopRenewExpireTimel(shopId));
			record.put("account_info", accountInfo == null ? null : accountInfo.intoMap());
		}
		return result;
	}

	public SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select, ShopListQueryParam param) {
		if (param == null) {
			return select;
		}
		if (!StringUtils.isEmpty(param.keywords)) {
			Integer shopId = Util.convert(param.keywords, Integer.class, 0);
			String keywords = likeValue(param.keywords);
			select.where(SHOP.SHOP_NAME.like(keywords)
					.or(SHOP.MOBILE.like(keywords))
					.or(MP_AUTH_SHOP.NICK_NAME.like(keywords))
					.or(SHOP.SHOP_ID.eq(shopId)));
		}

		if (param.sysId != null) {
			select.where(SHOP.SYS_ID.eq(param.sysId));
		}

		Integer shopUsingStatus = 1;
		Integer shopExpiredStatus = 2;
		if (param.isUse != null && param.isUse.equals(shopUsingStatus)) {
			// 店铺在使用中
			select.where(SHOP.SHOP_ID.in(db().selectDistinct(SHOP_RENEW.SHOP_ID)
					.from(SHOP_RENEW)
					.where(SHOP_RENEW.SHOP_ID.eq(SHOP.SHOP_ID)
							.and(SHOP_RENEW.EXPIRE_TIME.ge(DSL.currentDate())))));
		}

		if (param.isUse != null && param.isUse.equals(shopExpiredStatus)) {
			// 店铺已过期
			select.where(SHOP.SHOP_ID.in(db().selectDistinct(SHOP_RENEW.SHOP_ID)
					.from(SHOP_RENEW)
					.where(SHOP_RENEW.SHOP_ID.eq(SHOP.SHOP_ID))
					.and(SHOP_RENEW.EXPIRE_TIME.lt(DSL.currentDate())
							.or(SHOP_RENEW.EXPIRE_TIME.isNull()))));
		}

		if (!StringUtils.isEmpty(param.shopType)) {
			select.where(SHOP.SHOP_TYPE.eq(param.shopType));
		}
		if (param.shopFlag != null) {
			if (param.shopFlag == 0) {
				select.where(SHOP.SHOP_FLAG.eq(param.shopFlag).or(SHOP.SHOP_FLAG.isNull()));
			} else {
				select.where(SHOP.SHOP_FLAG.eq(param.shopFlag));
			}
		}

		if (!StringUtils.isEmpty(param.accountKey)) {
			String key = this.likeValue(param.accountKey);
			select.where(SHOP_ACCOUNT.COMPANY.like(key)
					.or(MP_AUTH_SHOP.PRINCIPAL_NAME.like(key))
					.or(SHOP.SHOP_ID.like(key)));
		}

		if (param.isEnabled != null) {
			select.where(SHOP.IS_ENABLED.eq(param.isEnabled));
		}
		if (param.hidBottom != null) {
			select.where(SHOP.HID_BOTTOM.eq(param.hidBottom));
		}
		return select;
	}

	public ShopRecord addShop(Shop shop) {
		shop.setShopId(getCanUseShopId());
		shop.setIsEnabled(shop.getIsEnabled() == null ? 0 : shop.getIsEnabled());
		DbConfig dbConfig = dm.getInstallShopDbConfig(shop.getShopId());
		shop.setDbConfig(Util.toJSON(dbConfig));
		dm.installShopDb(dbConfig);

		ShopRecord record = db().newRecord(SHOP, shop);
		db().executeInsert(record);
		return record;
	}

	public Integer getCanUseShopId() {
		int maxNumber = 1000;
		for (int i = 0; i < maxNumber; i++) {
			Integer shopId = Util.randomInteger(100000, 1000000);
			if (db().fetchCount(SHOP, SHOP.SHOP_ID.eq(shopId)) == 0) {
				return shopId;
			}
		}
		Integer maxShopId = (Integer) db().select(DSL.max(SHOP.SHOP_ID)).from(SHOP).fetch().getValue(0, 0);
		return maxShopId + 1;
	}

	public ShopRecord updateShop(Shop shop) {
		shop.setIsEnabled(shop.getIsEnabled() == null ? 0 : shop.getIsEnabled());
		shop.setIsEnabled(shop.getHidBottom() == null ? 0 : shop.getHidBottom());
		ShopRecord record = db().newRecord(SHOP, shop);
		db().executeUpdate(record);
		return record;
	}

	public boolean hasMobile(String mobile) {
		return getShopByMobile(mobile) != null;
	}

	public ShopRecord getShopById(Integer shopId) {
		return db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchOne();
	}

	public ShopRecord getShopByMobile(String mobile) {
		return db().selectFrom(SHOP).where(SHOP.MOBILE.eq(mobile)).fetchOne();
	}
}

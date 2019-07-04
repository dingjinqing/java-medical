package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;
import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.ShopAccount.SHOP_ACCOUNT;
import static com.vpu.mp.db.main.tables.ShopChildRole.SHOP_CHILD_ROLE;
import static com.vpu.mp.db.main.tables.ShopRenew.SHOP_RENEW;

import java.sql.Timestamp;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DbConfig;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.saas.shop.ShopListQueryParam;
import com.vpu.mp.service.pojo.saas.shop.ShopPojo;

/**
 * 
 * @author 新国
 *
 */
public class ShopService extends BaseService {

	public ShopAccountService accout;
	public ShopRenewService renew;
	public ShopVersionService version;
	public ShopChildAccountService subAccount;
	public ShopRoleService role;
	public ShopMenuService menu;
	public MpDecorationService decoration;
	public MpAuthShopService mp;


	public PageResult<Object> getPageList(ShopListQueryParam param) {
//		SelectWhereStep<Record> select = db()
//				.select(SHOP.asterisk(),
//						MP_AUTH_SHOP.APP_ID,
//						MP_AUTH_SHOP.IS_AUTH_OK,
//						MP_AUTH_SHOP.NICK_NAME,
//						MP_AUTH_SHOP.PRINCIPAL_NAME)
//				.from(SHOP)
//				.join(SHOP_ACCOUNT).on(SHOP.SYS_ID.eq(SHOP_ACCOUNT.SYS_ID))
//				.leftJoin(MP_AUTH_SHOP).on(SHOP.SHOP_ID.eq(DSL.cast(MP_AUTH_SHOP.SHOP_ID, Integer.class)));
//		select = this.buildOptions(select, param);
//		select.orderBy(SHOP.CREATED.desc());
////		PageResult<HashMap> result = this.getPageResult(select, param.page);
////		for (Map<String, Object> record : result.dataList) {
////			Integer shopId = Util.convert(record.get("shop_id"), Integer.class, 0);
////			Integer sysId = Util.convert(record.get("sys_id"), Integer.class, 0);
////			ShopAccountRecord accountInfo = this.accout.getAccountInfoForID(sysId);
////			record.put("renew_money", this.renew.getShopRenewTotal(shopId));
////			record.put("expire_time", this.renew.getShopRenewExpireTime(shopId));
////			record.put("account_info", accountInfo == null ? null : accountInfo.intoMap());
////		}
		return null;
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
							.and(SHOP_RENEW.EXPIRE_TIME.ge(DSL.currentTimestamp())))));
		}

		if (param.isUse != null && param.isUse.equals(shopExpiredStatus)) {
			// 店铺已过期
			select.where(SHOP.SHOP_ID.in(db().selectDistinct(SHOP_RENEW.SHOP_ID)
					.from(SHOP_RENEW)
					.where(SHOP_RENEW.SHOP_ID.eq(SHOP.SHOP_ID))
					.and(SHOP_RENEW.EXPIRE_TIME.lt(DSL.currentTimestamp())
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

	public ShopRecord addShop(ShopPojo shop) {
		shop.setShopId(getCanUseShopId());
		shop.setIsEnabled(shop.getIsEnabled() == null ? 0 : shop.getIsEnabled());
		shop.setIsEnabled(shop.getHidBottom() == null ? 0 : shop.getHidBottom());
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

	public Integer updateShop(ShopPojo shop) {
		shop.setIsEnabled(shop.getIsEnabled() == null ? 0 : shop.getIsEnabled());
		shop.setIsEnabled(shop.getHidBottom() == null ? 0 : shop.getHidBottom());
		ShopRecord record = db().newRecord(SHOP, shop);
		return db().executeUpdate(record);
	}

	public boolean hasMobile(String mobile) {
		return getShopByMobile(mobile) != null;
	}

	public ShopRecord getShopById(Integer shopId) {
		return db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchAny();
	}

	public ShopRecord getShopByMobile(String mobile) {
		return db().selectFrom(SHOP).where(SHOP.MOBILE.eq(mobile)).fetchAny();
	}

	public Integer getShopAccessRoleId(Integer sysId, Integer shopId, Integer subAccountId) {
		if (subAccountId == 0) {
			ShopRecord shop = this.getShopById(shopId);
			if (shop != null && shop.getSysId() == sysId) {
				return 0;
			}
			return -1;
		}
		Record1<Integer> role = db().select(SHOP_CHILD_ROLE.ROLE_ID)
				.from(SHOP)
				.leftJoin(SHOP_CHILD_ROLE).on(SHOP.SHOP_ID.eq(SHOP_CHILD_ROLE.SHOP_ID))
				.where(SHOP.SHOP_ID.eq(shopId))
				.and(SHOP.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ROLE.ACCOUNT_ID.eq(subAccountId))
				.fetchAny();
		if (role != null) {
			return role.value1();
		}
		return -1;
	}

	/**
	 * 得到角色权限对应店铺列表 `state` '0 入驻申请，1审核通过，2审核不通过',审核不通过不能登录到店铺后台 `business_state`
	 * '营业状态 0未营业 1营业',未营业不能下单，加入购物车，下单接口提示 expire_time 过期可以登录到后台，店铺是未营业状态
	 * 
	 * @param sysId
	 * @param subAccountId
	 * @return
	 */
	public Result<Record9<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String>> getRoleShopList(
			Integer sysId, Integer subAccountId) {
		SelectWhereStep<Record9<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String>> select = db()
				.selectDistinct(
						SHOP.SHOP_ID,
						SHOP.SYS_ID,
						SHOP.SHOP_NAME,
						SHOP.SHOP_AVATAR,
						SHOP.CREATED,
						SHOP.STATE,
						SHOP.BUSINESS_STATE,
						SHOP.IS_ENABLED,
						SHOP.SHOP_TYPE)
				.from(SHOP)
				.leftJoin(SHOP_CHILD_ROLE).on(SHOP.SHOP_ID.eq(SHOP_CHILD_ROLE.SHOP_ID));
		select.where(SHOP.SYS_ID.eq(sysId));
		if (subAccountId > 0) {
			select.where(SHOP_CHILD_ROLE.ACCOUNT_ID.eq(subAccountId));
		}
		return select.orderBy(SHOP.CREATED.desc())
				.fetch();
	}

	public String[] getShopStyle(Integer shopId) {
		String[] defaultStyle = { "#ff6666", "#fee6e6" };
		ShopRecord record = this.getShopById(shopId);
		if (record != null) {
			String shopStyle = record.getShopStyle();
			if (shopStyle != null) {
				String[] arr = shopStyle.split(";");
				if (arr.length > 1) {
					return arr[1].split(",");
				}
			}
		}
		return defaultStyle;
	}
	
	public ShopPojo getShopBaseInfoById(Integer shopId) {
		return db().select(SHOP.SHOP_AVATAR,SHOP.SHOP_NAME,SHOP.BUSINESS_STATE,SHOP.CREATED,SHOP.BUSINESS_STATE).from(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchOne().into(ShopPojo.class);
	}
	
	public Integer updateShopBaseInfo(ShopPojo shop) {
		return db().update(SHOP)
				.set(SHOP.SHOP_NAME, shop.getShopName())
				.set(SHOP.SHOP_AVATAR, shop.getShopAvatar())
                .set(SHOP.BUSINESS_STATE, shop.getBusinessState())
                .where(SHOP.SHOP_ID.eq(shop.getShopId()))
				.execute();
	}
	
	public Integer updateShareCfg(ShopPojo shop) {
		return db().update(SHOP)
				.set(SHOP.SHARE_CONFIG, shop.getShareConfig())
                .where(SHOP.SHOP_ID.eq(shop.getShopId()))
				.execute();
	}

}

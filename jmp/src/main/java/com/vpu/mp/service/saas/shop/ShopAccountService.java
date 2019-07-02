package com.vpu.mp.service.saas.shop;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;
import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.ShopAccount.SHOP_ACCOUNT;

import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;

/**
 * 
 * @author 新国
 *
 */
public class ShopAccountService extends BaseService {

	final public static class ShopAccountListQueryParam {
		public Byte state;
		public Integer page;
		public String keywords;
		public String company;
		
		

		public Byte getState() {
			return state;
		}

		public void setState(Byte state) {
			this.state = state;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public Integer getpage() {
			return page;
		}

		public void setpage(Integer page) {
			this.page = page;
		}

	};

	public PageResult getPageList(ShopAccountListQueryParam param) {
		SelectWhereStep<Record> select = db().select().from(SHOP_ACCOUNT);
		select = this.buildOptions(select, param);
		select.orderBy(SHOP_ACCOUNT.SYS_ID.desc());
		return this.getPageResult(select, param.page);
	}

	public SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select, ShopAccountListQueryParam param) {
		if (param == null) {
			return select;
		}
		if (!StringUtils.isEmpty(param.keywords)) {
			select.where(
					SHOP_ACCOUNT.USER_NAME.like(param.keywords).or(SHOP_ACCOUNT.ACCOUNT_NAME.like(param.keywords)));
		}
		if (param.state != null && param.state != 0) {
			select.where(SHOP_ACCOUNT.STATE.eq(param.state));
		}

		if (!StringUtils.isEmpty(param.company)) {
			select.where(SHOP_ACCOUNT.COMPANY.like(param.company));
		}

		return select;
	}

	public ShopAccountRecord verify(String username, String password) {
		return db().selectFrom(SHOP_ACCOUNT)
				.where(SHOP_ACCOUNT.USER_NAME.eq(username))
				.and(SHOP_ACCOUNT.PASSWORD.eq(Util.md5(password)))
				.fetchAny();
	}
	
	public ShopAccountRecord checkByIdAndNameOnMain(String username, Integer sysid) {
		return db().selectFrom(SHOP_ACCOUNT)
				.where(SHOP_ACCOUNT.USER_NAME.eq(username))
				.and(SHOP_ACCOUNT.SYS_ID.eq(sysid))
				.fetchAny();
	}

	public ShopAccountRecord getAccountInfo(String username) {
		return db().selectFrom(SHOP_ACCOUNT)
				.where(SHOP_ACCOUNT.USER_NAME.eq(username))
				.fetchAny();
	}

	public Integer getShopAccountNumber(String startTime, String endTime) {
		SelectWhereStep<Record1<Integer>> select = db().selectCount().from(SHOP_ACCOUNT);
		if (startTime != null) {
			Timestamp ts = Util.convertToTimestamp(startTime);
			select.where(SHOP_ACCOUNT.ADD_TIME.ge(ts));
		}
		if (endTime != null) {
			Timestamp ts = Util.convertToTimestamp(endTime);
			select.where(SHOP_ACCOUNT.ADD_TIME.le(ts));
		}
		return (Integer) select.limit(1).fetchSingle(0);
	}

	/**
	 * 统计将要过期账号数量
	 * 
	 * @param startTime
	 * @return
	 */
	public Integer getEndShopAccountNumber(String startTime) {
		SelectWhereStep<Record1<Integer>> select = db().selectCount().from(SHOP_ACCOUNT);
		Timestamp startTimestamp = new Timestamp((new Date()).getTime());
		if (startTime != null) {
			startTimestamp = Util.convertToTimestamp(startTime);
		}
		select.where(SHOP_ACCOUNT.END_TIME.ge(startTimestamp));

		Timestamp endTimestamp = new Timestamp((new Date()).getTime() - 30 * 3600 * 24);
		select.where(SHOP_ACCOUNT.END_TIME.le(endTimestamp));
		return Util.getInteger(select.fetchAny(0));
	}

	public List<String> getPrincipalName(Integer sysId) {
		return db().select().from(SHOP_ACCOUNT)
				.join(SHOP).on(SHOP_ACCOUNT.SYS_ID.eq(SHOP.SYS_ID))
				.join(MP_AUTH_SHOP).on(SHOP.SHOP_ID.eq(DSL.cast(MP_AUTH_SHOP.SHOP_ID, Integer.class)))
				.fetch(MP_AUTH_SHOP.PRINCIPAL_NAME);
	}

	public ShopAccountRecord getAccountInfoForID(Integer sysId) {
		return db().selectFrom(SHOP_ACCOUNT)
				.where(SHOP_ACCOUNT.SYS_ID.eq(sysId))
				.fetchAny();
	}

	public ShopAccountRecord getAccountInfoForID(String nameOrMobile) {
		return db().selectFrom(SHOP_ACCOUNT)
				.where(SHOP_ACCOUNT.USER_NAME.eq(nameOrMobile).or(SHOP_ACCOUNT.MOBILE.eq(nameOrMobile)))
				.fetchAny();
	}

	public ShopAccountRecord addAccountInfo(com.vpu.mp.db.main.tables.pojos.ShopAccount account) {
		ShopAccountRecord record = db().newRecord(SHOP_ACCOUNT, account);
		db().executeInsert(record);
		return record;
	}
	
	public ShopAccountRecord addAccountInfo(ShopAccountRecord addAccountInfo) {
		db().executeInsert(addAccountInfo);
		return addAccountInfo;
	}

	public ShopAccountRecord updateAccountInfo(com.vpu.mp.db.main.tables.pojos.ShopAccount account) {
		ShopAccountRecord record = db().newRecord(SHOP_ACCOUNT, account);
		db().executeUpdate(record);
		return record;
	}
}

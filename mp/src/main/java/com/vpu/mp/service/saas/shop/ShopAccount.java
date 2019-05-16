package com.vpu.mp.service.saas.shop;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.Convert;
import com.vpu.mp.db.main.tables.B2cMpAuthShop;
import com.vpu.mp.db.main.tables.B2cShop;
import com.vpu.mp.db.main.tables.B2cShopAccount;
import com.vpu.mp.db.main.tables.records.B2cShopAccountRecord;
import com.vpu.mp.service.foundation.BaseComponent;
import com.vpu.mp.service.foundation.Page;
import com.vpu.mp.service.foundation.Util;

public class ShopAccount extends BaseComponent {

	protected B2cShopAccount tableShopAccount = B2cShopAccount.B2C_SHOP_ACCOUNT;
	protected B2cMpAuthShop tableMpAuthShop = B2cMpAuthShop.B2C_MP_AUTH_SHOP;
	protected B2cShop tableShop = B2cShop.B2C_SHOP;

	public Result<B2cShopAccountRecord> getPageList(Map<String, String> options, int totalRows, Page page) {
		SelectWhereStep<B2cShopAccountRecord> select = db().selectFrom(tableShopAccount);
		select = this.buildOptions(select, options);
		return select.orderBy(tableShopAccount.SYS_ID.desc())
				.limit((page.currentPage - 1) * page.pageRows, page.pageRows).fetch();
	}

	public SelectWhereStep<B2cShopAccountRecord> buildOptions(SelectWhereStep<B2cShopAccountRecord> select,
			Map<String, String> options) {
		if (options.containsKey("keywords")) {
			String keywords = likeValue(options.get("keywords"));
			select.where(tableShopAccount.USER_NAME.like(keywords).or(tableShopAccount.ACCOUNT_NAME.like(keywords)));
		}
		if (options.containsKey("state")) {
			Byte state = Convert.convert(options.containsKey("state"), Byte.class);
			select.where(tableShopAccount.STATE.eq(state));
		}

		if (options.containsKey("business_state")) {
			Byte business_state = Convert.convert(options.containsKey("business_state"), Byte.class);
			if (business_state != -1)
				select.where(tableShopAccount.BUSINESS_STATE.eq(business_state));
		}

		if (options.containsKey("company")) {
			String company = this.likeValue(options.get("company"));
			select.where(tableShopAccount.COMPANY.like(company));
		}

		if (options.containsKey("principal_name")) {
			String principal_name = this.likeValue(options.get("principal_name"));
			List<Integer> shopIds = db().selectFrom(tableMpAuthShop)
					.where(tableMpAuthShop.PRINCIPAL_NAME.like(principal_name))
					.fetch(tableMpAuthShop.SHOP_ID, Integer.class);
			List<Integer> sysIds = db().selectFrom(tableShop).where(tableShop.SHOP_ID.in(shopIds))
					.fetch(tableShop.SYS_ID, Integer.class);
			select.where(tableShopAccount.SYS_ID.in(sysIds));
		}

		if (options.containsKey("user_name")) {
			String user_name = this.likeValue(options.get("user_name"));
			select.where(tableShopAccount.USER_NAME.like(user_name));
		}
		return select;
	}

	public Page getPage(int currentPage, int pageRows) {
		int totalRows = db().fetchCount(tableShopAccount);
		return Page.getPage(totalRows, currentPage, pageRows);
	}

	public Page getPage(int currentPage) {
		int totalRows = db().fetchCount(tableShopAccount);
		return Page.getPage(totalRows, currentPage, 20);
	}

	public B2cShopAccountRecord verify(String username, String password) {
		return db().selectFrom(tableShopAccount).where(tableShopAccount.USER_NAME.eq(username))
				.and(tableShopAccount.PASSWORD.eq(Util.md5(password))).fetchOne();
	}

	public B2cShopAccountRecord getAccountInfo(String username) {
		return db().selectFrom(tableShopAccount).where(tableShopAccount.USER_NAME.eq(username)).fetchOne();
	}

	public Integer getShopAccountNumber(String startTime, String endTime) {
		SelectWhereStep<Record1<Integer>> select = db().selectCount().from(tableShopAccount);
		if (startTime != null) {
			Timestamp ts = Util.convertToTimestamp(startTime);
			select.where(tableShopAccount.ADD_TIME.ge(ts));
		}
		if (endTime != null) {
			Timestamp ts = Util.convertToTimestamp(endTime);
			select.where(tableShopAccount.ADD_TIME.le(ts));
		}
		return (Integer) select.fetchOne().get(0);
	}

	/**
	 * 统计将要过期账号数量
	 * 
	 * @param startTime
	 * @return
	 */
	public Integer getEndShopAccountNumber(String startTime) {
		SelectWhereStep<Record1<Integer>> select = db().selectCount().from(tableShopAccount);
		Timestamp startTimestamp = new Timestamp((new Date()).getTime());
		if (startTime != null) {
			startTimestamp = Util.convertToTimestamp(startTime);
		}
		select.where(tableShopAccount.END_TIME.ge(startTimestamp));

		Timestamp endTimestamp = new Timestamp((new Date()).getTime() - 30 * 3600 * 24);
		select.where(tableShopAccount.END_TIME.le(endTimestamp));
		return (Integer) select.fetchOne().get(0);
	}

	public List<String> getPrincipalName(Integer sysId) {
		B2cShopAccount a = tableShopAccount.as("a");
		B2cShop b = tableShop.as("b");
		B2cMpAuthShop c = tableMpAuthShop.as("c");
		return db().select().from(a).join(b).on(a.SYS_ID.eq(b.SYS_ID)).join(c).on(b.SHOP_ID.eq(DSL.sign(c.SHOP_ID)))
				.fetch(c.PRINCIPAL_NAME);
	}

	public B2cShopAccountRecord getAccountInfoForID(Integer sysId) {
		return db().selectFrom(tableShopAccount).where(tableShopAccount.SYS_ID.eq(sysId)).fetchOne();
	}

	public B2cShopAccountRecord getAccountInfoForID(String nameOrMobile) {
		return db().selectFrom(tableShopAccount)
				.where(tableShopAccount.USER_NAME.eq(nameOrMobile).or(tableShopAccount.MOBILE.eq(nameOrMobile)))
				.fetchOne();
	}

}

package com.vpu.mp.service.saas.shop;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.main.tables.B2cMpAuthShop;
import com.vpu.mp.db.main.tables.B2cShop;
import com.vpu.mp.db.main.tables.B2cShopAccount;
import com.vpu.mp.db.main.tables.records.B2cShopAccountRecord;
import com.vpu.mp.service.foundation.BaseComponent;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;

/**
 * 
 * @author 新国
 *
 */
public class ShopAccount extends BaseComponent {

	protected B2cShopAccount tableShopAccount = B2cShopAccount.B2C_SHOP_ACCOUNT;
	protected B2cMpAuthShop tableMpAuthShop = B2cMpAuthShop.B2C_MP_AUTH_SHOP;
	protected B2cShop tableShop = B2cShop.B2C_SHOP;

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
		SelectWhereStep<Record> select = db().select().from(tableShopAccount);
		select = this.buildOptions(select, param);
		select.orderBy(tableShopAccount.SYS_ID.desc());
		return this.getPageResult(select, param.page);
	}

	public SelectWhereStep<Record> buildOptions(SelectWhereStep<Record> select, ShopAccountListQueryParam param) {
		if (param == null) {
			return select;
		}
		if (!StringUtils.isEmpty(param.keywords)) {
			select.where(tableShopAccount.USER_NAME.like(param.keywords)
					.or(tableShopAccount.ACCOUNT_NAME.like(param.keywords)));
		}
		if (param.state != null && param.state != 0) {
			select.where(tableShopAccount.STATE.eq(param.state));
		}

		if (!StringUtils.isEmpty(param.company)) {
			select.where(tableShopAccount.COMPANY.like(param.company));
		}

		return select;
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

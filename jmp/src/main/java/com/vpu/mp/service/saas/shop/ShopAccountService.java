package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;
import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.ShopAccount.SHOP_ACCOUNT;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.shop.ShopAccountListQueryParam;
import com.vpu.mp.service.pojo.saas.shop.ShopAccountPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 新国
 *
 */
@Service

public class ShopAccountService extends MainBaseService {
	@Autowired
	protected JedisManager jedis;

	public PageResult<ShopAccountPojo> getPageList(ShopAccountListQueryParam param) {
		SelectWhereStep<? extends Record> select = db()
				.select(SHOP_ACCOUNT.SYS_ID, SHOP_ACCOUNT.USER_NAME, SHOP_ACCOUNT.STATE, SHOP_ACCOUNT.BUSINESS_STATE,
						SHOP_ACCOUNT.ADD_TIME, SHOP_ACCOUNT.BUY_TIME, SHOP_ACCOUNT.END_TIME, SHOP_ACCOUNT.MOBILE)
				.from(SHOP_ACCOUNT);
		select = this.buildOptions(select, param);
		select.orderBy(SHOP_ACCOUNT.SYS_ID.desc());
		return this.getPageResult(select, param.currentPage, param.pageRows, ShopAccountPojo.class);
	}

	public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends Record> select,
			ShopAccountListQueryParam param) {
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
		return db().selectFrom(SHOP_ACCOUNT).where(SHOP_ACCOUNT.USER_NAME.eq(username))
				.and(SHOP_ACCOUNT.PASSWORD.eq(Util.md5(password))).fetchAny();
	}

	public ShopAccountRecord checkByIdAndNameOnMain(String username, Integer sysid) {
		return db().selectFrom(SHOP_ACCOUNT).where(SHOP_ACCOUNT.USER_NAME.eq(username))
				.and(SHOP_ACCOUNT.SYS_ID.eq(sysid)).fetchAny();
	}

	public ShopAccountRecord getAccountInfo(String username) {
		return db().selectFrom(SHOP_ACCOUNT).where(SHOP_ACCOUNT.USER_NAME.eq(username)).fetchAny();
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
		return db().select().from(SHOP_ACCOUNT).join(SHOP).on(SHOP_ACCOUNT.SYS_ID.eq(SHOP.SYS_ID)).join(MP_AUTH_SHOP)
				.on(SHOP.SHOP_ID.eq(DSL.cast(MP_AUTH_SHOP.SHOP_ID, Integer.class))).fetch(MP_AUTH_SHOP.PRINCIPAL_NAME);
	}

	public ShopAccountRecord getAccountInfoForId(Integer sysId) {
		return db().selectFrom(SHOP_ACCOUNT).where(SHOP_ACCOUNT.SYS_ID.eq(sysId)).fetchAny();
	}

	public ShopAccountRecord getAccountInfoForId(String nameOrMobile) {
		return db().selectFrom(SHOP_ACCOUNT)
				.where(SHOP_ACCOUNT.USER_NAME.eq(nameOrMobile).or(SHOP_ACCOUNT.MOBILE.eq(nameOrMobile))).fetchAny();
	}

	public ShopAccountRecord addAccountInfo(ShopAccountPojo account) {
		ShopAccountRecord record = db().newRecord(SHOP_ACCOUNT, account);
		db().executeInsert(record);
		return record;
	}

	public ShopAccountRecord addAccountInfo(ShopAccountRecord addAccountInfo) {
		db().executeInsert(addAccountInfo);
		return addAccountInfo;
	}

	public ShopAccountRecord updateAccountInfo(ShopAccountPojo account) {
		ShopAccountRecord record = db().newRecord(SHOP_ACCOUNT, account);
		db().executeUpdate(record);
		return record;
	}
	
	public int updateAccountInfo(ShopAccountRecord updateAccountInfo) {
		return db().executeUpdate(updateAccountInfo);
		
	}
	public int updateById(ShopAccountRecord record) {
		return db().executeUpdate(record);
	}

	/**
	 * 商家账户添加
	 * 
	 * @param account
	 * @return
	 */
	public boolean addShopAccountService(ShopAccountPojo account) {
		if (account.getUserName() == null || account.getPassword() == null) {
			return false;
		}
		ShopAccountRecord shop = this.getAccountInfoForId(account.getUserName());
		if (shop != null) {
			return false;
		}
		account.setPassword(Util.md5(account.getPassword()));
		ShopAccountRecord shop2 = new ShopAccountRecord();
		FieldsUtil.assignNotNull(account, shop2);
		this.addAccountInfo(shop2);
		return true;

	}
	
	public JsonResultCode editShopAccountService(ShopAccountPojo account) {
		if (StringUtils.isEmpty(account.getUserName()) || account.getSysId() == null) {
			//用户名或者sysid为空
			return JsonResultCode.CODE_ACCOUNT_USERNAME_NOT_NULL;
		}
		ShopAccountRecord shop = this.getAccountInfoForId(account.getSysId());
		if (shop == null) {
			//sysId不存在
			return JsonResultCode.CODE_ACCOUNT_SYSID_IS_NULL;
		}
		if(!StringUtils.isEmpty(account.getPassword())) {
			account.setPassword(Util.md5(account.getPassword()));
		}
		ShopAccountRecord shop2 = new ShopAccountRecord();
		FieldsUtil.assignNotNull(account, shop2);
		if(this.updateAccountInfo(shop2)==1) {
			return JsonResultCode.CODE_SUCCESS;
		}
		return JsonResultCode.CODE_FAIL;
	}

}

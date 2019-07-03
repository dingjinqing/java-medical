package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.ShopChildAccount.SHOP_CHILD_ACCOUNT;
import static com.vpu.mp.db.main.tables.ShopRole.SHOP_ROLE;
import static com.vpu.mp.db.main.tables.ShopChildRole.SHOP_CHILD_ROLE;
import static com.vpu.mp.db.main.tables.Shop.SHOP;


import org.jooq.Record;
import org.jooq.Record6;
import org.jooq.Result;
import org.jooq.SelectLimitStep;
import org.jooq.SelectWhereStep;

import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.saas.shop.ShopChildAccountListQueryParam;
import com.vpu.mp.service.pojo.saas.shop.ShopChildAccountPojo;

/**
 * 
 * @author 新国
 *
 */
public class ShopChildAccountService extends BaseService {

	public ShopChildAccountRecord verify(Integer sysId, String username, String password) {
		return db().selectFrom(SHOP_CHILD_ACCOUNT).where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ACCOUNT.ACCOUNT_NAME.eq(username))
				.and(SHOP_CHILD_ACCOUNT.ACCOUNT_PWD.eq(Util.md5(password))).fetchAny();
	}

	public ShopChildAccountRecord getUserFromAccountName(Integer sysId, String username) {
		return db().selectFrom(SHOP_CHILD_ACCOUNT).where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ACCOUNT.ACCOUNT_NAME.eq(username)).fetchAny();
	}

	public ShopChildAccountRecord getUserFromMobile(Integer sysId, String mobile) {
		return db().selectFrom(SHOP_CHILD_ACCOUNT).where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ACCOUNT.MOBILE.eq(mobile)).fetchAny();
	}

	public PageResult<ShopChildAccountPojo> getPageList(ShopChildAccountListQueryParam param) {
		SelectWhereStep<Record> select = db().select().from(SHOP_CHILD_ACCOUNT);
		select.where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(param.sysId));
		select.orderBy(SHOP_CHILD_ACCOUNT.ACCOUNT_ID.desc());
		return this.getPageResult(select, param.page, ShopChildAccountPojo.class);
	}

	public int removeAccount(Integer sysId, Integer accountId) {
		return db().delete(SHOP_CHILD_ACCOUNT).where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ACCOUNT.ACCOUNT_ID.eq(accountId)).execute();
	}

	public ShopChildAccountRecord updateAccount(ShopChildAccountPojo childAccount) {
		ShopChildAccountRecord record = db().newRecord(SHOP_CHILD_ACCOUNT, childAccount);
		db().executeUpdate(record);
		return record;
	}

	public Result<Record6<Integer, Integer, Integer, Integer, String, String>> getShopRole(Integer sysId,
			Integer accountId) {
		return db()
				.select(SHOP_CHILD_ROLE.SYS_ID, SHOP_CHILD_ROLE.SHOP_ID, SHOP_CHILD_ROLE.ACCOUNT_ID,
						SHOP_CHILD_ROLE.ROLE_ID, SHOP_ROLE.ROLE_NAME, SHOP.SHOP_NAME)
				.from(SHOP_CHILD_ROLE).join(SHOP_ROLE).on(SHOP_CHILD_ROLE.ROLE_ID.eq(SHOP_ROLE.ROLE_ID)).join(SHOP)
				.on(SHOP_CHILD_ROLE.SHOP_ID.eq(SHOP.SHOP_ID)).where(SHOP_CHILD_ROLE.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ROLE.ACCOUNT_ID.eq(accountId)).fetch();
	}

	public PageResult<ShopChildAccountPojo> getNotAccountPageList(ShopChildAccountListQueryParam param) {
		SelectLimitStep<?> select = db().select(SHOP_CHILD_ACCOUNT.asterisk()).from(SHOP_CHILD_ACCOUNT)
				.join(SHOP_CHILD_ROLE).on(SHOP_CHILD_ACCOUNT.ACCOUNT_ID.notEqual(SHOP_CHILD_ROLE.ROLE_ID))
				.where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(param.sysId)).orderBy(SHOP_CHILD_ACCOUNT.ACCOUNT_ID.desc());
		return this.getPageResult(select, param.page, ShopChildAccountPojo.class);
	}

	public Result<ShopChildAccountRecord> getAccount(Integer sysId) {
		return db().selectFrom(SHOP_CHILD_ACCOUNT).where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId)).fetch();
	}

	public ShopChildAccountRecord getSubAccountInfo(Integer sysId, Integer subAccountId) {
		return db().selectFrom(SHOP_CHILD_ACCOUNT).where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ACCOUNT.ACCOUNT_ID.eq(subAccountId)).fetchAny();
	}

	public Result<ShopChildAccountRecord> getSubAccountUser(String nameOrMobile) {
		return db().selectFrom(SHOP_CHILD_ACCOUNT).where(SHOP_CHILD_ACCOUNT.ACCOUNT_NAME.eq(nameOrMobile))
				.or(SHOP_CHILD_ACCOUNT.MOBILE.eq(nameOrMobile)).fetch();
	}

	public ShopChildAccountRecord checkByIdAndNameOnChild(Integer sysId, String accountname, Integer accountId) {
		return db().selectFrom(SHOP_CHILD_ACCOUNT).where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ACCOUNT.ACCOUNT_NAME.eq(accountname)).and(SHOP_CHILD_ACCOUNT.ACCOUNT_ID.eq(accountId))
				.fetchAny();
	}

}

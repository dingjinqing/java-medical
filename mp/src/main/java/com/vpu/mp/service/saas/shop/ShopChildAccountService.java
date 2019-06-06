package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.ShopAccount.SHOP_ACCOUNT;
import static com.vpu.mp.db.main.tables.ShopChildAccount.SHOP_CHILD_ACCOUNT;
import static com.vpu.mp.db.main.tables.ShopRole.SHOP_ROLE;
import static com.vpu.mp.db.main.tables.ShopChildRole.SHOP_CHILD_ROLE;
import static com.vpu.mp.db.main.tables.Shop.SHOP;

import java.util.Map;

import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Result;
import org.jooq.Select;
import org.jooq.SelectLimitStep;
import org.jooq.SelectSeekStep1;
import org.jooq.SelectWhereStep;

import com.vpu.mp.db.main.tables.pojos.ShopChildAccount;
import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;

/**
 * 
 * @author 新国
 *
 */
public class ShopChildAccountService extends BaseService {
	
	public ShopChildAccountRecord verify(Integer sysId, String username, String password) {
		return db().selectFrom(SHOP_CHILD_ACCOUNT)
				.where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ACCOUNT.ACCOUNT_NAME.eq(username))
				.and(SHOP_CHILD_ACCOUNT.ACCOUNT_PWD.eq(Util.md5(password)))
				.fetchAny();
	}

	public ShopChildAccountRecord getUserFromAccountName(Integer sysId, String username) {
		return db().selectFrom(SHOP_CHILD_ACCOUNT)
				.where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ACCOUNT.ACCOUNT_NAME.eq(username))
				.fetchAny();
	}

	public ShopChildAccountRecord getUserFromMobile(Integer sysId, String mobile) {
		return db().selectFrom(SHOP_CHILD_ACCOUNT)
				.where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ACCOUNT.MOBILE.eq(mobile))
				.fetchAny();
	}

	final public static class ShopChildAccountListQueryParam {
		Integer sysId;
		Integer page;

		public Integer getSysId() {
			return sysId;
		}

		public void setSysId(Integer sysId) {
			this.sysId = sysId;
		}

		public Integer getPage() {
			return page;
		}

		public void setPage(Integer page) {
			this.page = page;
		}
	}

	public PageResult getPageList(ShopChildAccountListQueryParam param) {
		SelectWhereStep<Record> select = db().select().from(SHOP_CHILD_ACCOUNT);
		select.where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(param.sysId));
		select.orderBy(SHOP_CHILD_ACCOUNT.ACCOUNT_ID.desc());
		return this.getPageResult(select, param.page);
	}

	public int removeAccount(Integer sysId, Integer accountId) {
		return db().delete(SHOP_CHILD_ACCOUNT)
				.where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ACCOUNT.ACCOUNT_ID.eq(accountId))
				.execute();
	}

	public ShopChildAccountRecord updateAccount(ShopChildAccount childAccount) {
		ShopChildAccountRecord record = db().newRecord(SHOP_CHILD_ACCOUNT, childAccount);
		db().executeUpdate(record);
		return record;
	}

	public Result<Record6<Integer, Integer, Integer, Integer, String, String>> getShopRole(Integer sysId,
			Integer accountId) {
		return db()
				.select(SHOP_CHILD_ROLE.SYS_ID, SHOP_CHILD_ROLE.SHOP_ID,
						SHOP_CHILD_ROLE.ACCOUNT_ID, SHOP_CHILD_ROLE.ROLE_ID,
						SHOP_ROLE.ROLE_NAME, SHOP.SHOP_NAME)
				.from(SHOP_CHILD_ROLE)
				.join(SHOP_ROLE).on(SHOP_CHILD_ROLE.ROLE_ID.eq(SHOP_ROLE.ROLE_ID))
				.join(SHOP).on(SHOP_CHILD_ROLE.SHOP_ID.eq(SHOP.SHOP_ID))
				.where(SHOP_CHILD_ROLE.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ROLE.ACCOUNT_ID.eq(accountId))
				.fetch();
	}

	/**
	 * 主账号管理子账号按页查询列表
	 * 
	 * @param param
	 * @return
	 */
	public PageResult getAccountUserList(ShopChildAccountListQueryParam param) {
		PageResult result = this.getPageList(param);
		for (Map<String, Object> r : result.dataList) {
			Integer sysId = Util.convert(r.get("sys_id"), Integer.class, 0);
			Integer accountId = Util.convert(r.get("account_id"), Integer.class, 0);
			r.put("shop_role", this.getShopRole(sysId, accountId).intoMaps());
		}
		return result;
	}

	public PageResult getNotAccountPageList(ShopChildAccountListQueryParam param) {
		SelectLimitStep<?> select = db()
				.select(SHOP_CHILD_ACCOUNT.ACCOUNT_ID, SHOP_CHILD_ACCOUNT.ACCOUNT_NAME)
				.from(SHOP_CHILD_ACCOUNT)
				.join(SHOP_CHILD_ROLE).on(SHOP_CHILD_ACCOUNT.ACCOUNT_ID.notEqual(SHOP_CHILD_ROLE.ROLE_ID))
				.where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(param.sysId))
				.orderBy(SHOP_CHILD_ACCOUNT.ACCOUNT_ID.desc());
		return this.getPageResult(select, param.page);
	}

	public Result<ShopChildAccountRecord> getAccount(Integer sysId) {
		return db()
				.selectFrom(SHOP_CHILD_ACCOUNT)
				.where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.fetch();
	}

	public ShopChildAccountRecord getSubAccountInfo(Integer sysId, Integer subAccountId) {
		return db()
				.selectFrom(SHOP_CHILD_ACCOUNT)
				.where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId))
				.and(SHOP_CHILD_ACCOUNT.ACCOUNT_ID.eq(subAccountId))
				.fetchAny();
	}

	public Result<ShopChildAccountRecord> getSubAccountUser(String nameOrMobile) {
		return db()
				.selectFrom(SHOP_CHILD_ACCOUNT)
				.where(SHOP_CHILD_ACCOUNT.ACCOUNT_NAME.eq(nameOrMobile))
				.or(SHOP_CHILD_ACCOUNT.MOBILE.eq(nameOrMobile))
				.fetch();
	}

}

package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.ShopChildAccount.SHOP_CHILD_ACCOUNT;
import static com.vpu.mp.db.main.tables.ShopChildRole.SHOP_CHILD_ROLE;
import static com.vpu.mp.db.main.tables.ShopRole.SHOP_ROLE;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Record6;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectLimitStep;
import org.jooq.SelectWhereStep;

import com.vpu.mp.db.main.tables.ShopChildAccount;
import com.vpu.mp.db.main.tables.ShopChildRole;
import com.vpu.mp.db.main.tables.ShopRole;
import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildRoleRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.saas.shop.ShopChildAccountListQueryParam;
import com.vpu.mp.service.pojo.saas.shop.ShopChildAccountPojo;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.config.group.ShopChildAccountVo;
import com.vpu.mp.service.pojo.shop.config.group.ShopRoleAddListVo;
import com.vpu.mp.service.pojo.shop.config.group.ShopRoleAddParam;
import com.vpu.mp.service.pojo.shop.config.group.ShopRoleUpdateParam;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 新国
 *
 */
@Service
@Scope("prototype")
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

	public ShopChildRoleRecord checkByRecode(Integer roleId, AdminTokenAuthInfo info) {
		return db().selectFrom(SHOP_CHILD_ROLE)
				.where(SHOP_CHILD_ROLE.SYS_ID.eq(info.getSysId())
						.and(SHOP_CHILD_ROLE.ROLE_ID.eq(roleId).and(SHOP_CHILD_ROLE.SHOP_ID.eq(info.getLoginShopId()))))
				.fetchAny();
	}

	public List<ShopChildAccountVo> getInfoBySysId(Integer sysId) {
		SelectConditionStep<Record3<Integer, String, String>> sRecord = db()
				.select(SHOP_CHILD_ACCOUNT.ACCOUNT_ID, SHOP_CHILD_ACCOUNT.ACCOUNT_NAME, SHOP_CHILD_ACCOUNT.MOBILE)
				.from(SHOP_CHILD_ACCOUNT).where(SHOP_CHILD_ACCOUNT.SYS_ID.eq(sysId));
		sRecord.orderBy(SHOP_CHILD_ACCOUNT.CREATE_TIME).fetch();
		List<ShopChildAccountVo> childAccountList = new ArrayList<>();
		for (Record3<Integer, String, String> record : sRecord) {
			ShopChildAccountVo vo = new ShopChildAccountVo();
			vo.setAccountId(record.get(SHOP_CHILD_ACCOUNT.ACCOUNT_ID));
			vo.setAccountName(record.get(SHOP_CHILD_ACCOUNT.ACCOUNT_NAME));
			vo.setMobile(record.get(SHOP_CHILD_ACCOUNT.MOBILE));
			childAccountList.add(vo);
		}
		return childAccountList;
	}

	public int insertshopChildRole(ShopRoleAddParam sAddParam, AdminTokenAuthInfo info) {
		ShopChildRoleRecord scrr = new ShopChildRoleRecord();
		scrr.setSysId(info.getSysId());
		scrr.setAccountId(sAddParam.getAccountId());
		scrr.setShopId(info.getLoginShopId());
		scrr.setRoleId(sAddParam.getRoleId());
		return db().executeInsert(scrr);
	}

	public List<ShopRoleAddListVo> queryRoleAndAccount(Integer sysId) {
		ShopChildAccount a = SHOP_CHILD_ACCOUNT.as("a");
		ShopChildRole b = SHOP_CHILD_ROLE.as("b");
		ShopRole c = SHOP_ROLE.as("c");

		Result<Record6<Integer, String, String, Integer, String, Integer>> result = db()
				.select(a.ACCOUNT_ID, a.ACCOUNT_NAME, a.MOBILE, c.ROLE_ID, c.ROLE_NAME, b.REC_ID).from(a).join(b)
				.on(b.SYS_ID.eq(a.SYS_ID).and(b.ACCOUNT_ID.eq(a.ACCOUNT_ID))).join(c)
				.on(c.ROLE_ID.eq(b.ROLE_ID).and(b.SYS_ID.eq(c.SYS_ID))).and(c.SYS_ID.eq(sysId))
				.orderBy(a.CREATE_TIME.desc()).fetch();
		List<ShopRoleAddListVo> list = new ArrayList<>();
		for (Record6<Integer, String, String, Integer, String, Integer> record : result) {
			ShopRoleAddListVo vo = new ShopRoleAddListVo();
			vo.setAccountId(record.get(a.ACCOUNT_ID));
			vo.setAccountName(record.get(a.ACCOUNT_NAME));
			vo.setMobile(record.get(a.MOBILE));
			vo.setRoleId(record.get(c.ROLE_ID));
			vo.setRoleName(record.get(c.ROLE_NAME));
			vo.setRecId(record.get(b.REC_ID));
			list.add(vo);
		}
		return list;
	}

	public ShopChildRoleRecord checkByRecodeAndAccId(Integer roleId, Integer accountId, AdminTokenAuthInfo info) {
		return db().selectFrom(SHOP_CHILD_ROLE)
				.where(SHOP_CHILD_ROLE.SYS_ID.eq(info.getSysId()).and(SHOP_CHILD_ROLE.ACCOUNT_ID.eq(accountId)).and(SHOP_CHILD_ROLE.SHOP_ID.eq(info.getLoginShopId())))
				.fetchAny();
	}

	public int updateShopChildRole(ShopRoleUpdateParam sParam, AdminTokenAuthInfo info) {
		ShopChildRoleRecord scrr = new ShopChildRoleRecord();
		scrr.setAccountId(sParam.getAccountId());
		scrr.setRoleId(sParam.getRoleId());
		scrr.setRecId(sParam.getRecId());
		return db().executeUpdate(scrr);
	}

	public int deleteShopChildRole(ShopRoleUpdateParam sUpdatePara) {
		return db().deleteFrom(SHOP_CHILD_ROLE)
				.where(SHOP_CHILD_ROLE.REC_ID.eq(sUpdatePara.getRecId()).and(SHOP_CHILD_ROLE.ACCOUNT_ID
						.eq(sUpdatePara.getAccountId()).and(SHOP_CHILD_ROLE.ROLE_ID.eq(sUpdatePara.getRoleId()))))
				.execute();
	}
}

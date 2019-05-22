package com.vpu.mp.service.saas.privilege;

import static com.vpu.mp.db.main.tables.SystemChildAccount.SYSTEM_CHILD_ACCOUNT;
import static com.vpu.mp.db.main.tables.SystemRole.SYSTEM_ROLE;

import org.jooq.Record;
import org.jooq.Result;

import com.vpu.mp.db.main.tables.records.SystemChildAccountRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;

/**
 * 
 * @author 新国
 *
 */
public class ChildAccountService extends BaseService {

	public SystemChildAccountRecord verify(String username, String password) {
		SystemChildAccountRecord user = db().selectFrom(SYSTEM_CHILD_ACCOUNT)
				.where(SYSTEM_CHILD_ACCOUNT.ACCOUNT_NAME.eq(username)).or(SYSTEM_CHILD_ACCOUNT.MOBILE.eq(username))
				.fetchOne();
		if (user != null) {
			if (user.getAccountPwd() == Util.md5(password)) {
				return user;
			}
		}

		db().selectFrom(SYSTEM_CHILD_ACCOUNT).limit(0, 20).fetch();
		return null;
	}

	public SystemChildAccountRecord getUserFromAccountName(String username) {
		return db().selectFrom(SYSTEM_CHILD_ACCOUNT)
				.where(SYSTEM_CHILD_ACCOUNT.ACCOUNT_NAME.eq(username))
				.fetchOne();
	}

	public SystemChildAccountRecord getUserFromMobile(String mobile) {
		return db().selectFrom(SYSTEM_CHILD_ACCOUNT)
				.where(SYSTEM_CHILD_ACCOUNT.MOBILE.eq(mobile))
				.fetchOne();
	}

	public Result<Record> getPageList(Integer pageNo, Integer pageRows) {
		return db().select().from(SYSTEM_CHILD_ACCOUNT)
				.join(SYSTEM_ROLE)
				.on(SYSTEM_CHILD_ACCOUNT.ROLE_ID.eq(SYSTEM_ROLE.ROLE_ID))
				.limit(pageNo * pageRows, pageRows).fetch();

	}
}

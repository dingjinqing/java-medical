package com.vpu.mp.service.saas.privilege;

import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.main.tables.B2cSystemChildAccount;
import com.vpu.mp.db.main.tables.B2cSystemRole;
import com.vpu.mp.db.main.tables.records.B2cSystemChildAccountRecord;
import com.vpu.mp.service.foundation.BaseComponent;
import com.vpu.mp.service.foundation.Util;

@Component
public class ChildAccount extends BaseComponent {
	protected B2cSystemChildAccount tableAccount = B2cSystemChildAccount.B2C_SYSTEM_CHILD_ACCOUNT;
	protected B2cSystemRole tableRole = B2cSystemRole.B2C_SYSTEM_ROLE;

	public B2cSystemChildAccountRecord verify(String username, String password) {
		B2cSystemChildAccountRecord user = dm.db().selectFrom(tableAccount)
				.where(tableAccount.ACCOUNT_NAME.eq(username)).or(tableAccount.MOBILE.eq(username)).fetchOne();
		if (user != null) {
			if (user.getAccountPwd() == Util.md5(password)) {
				return user;
			}
		}
		return null;
	}

	public B2cSystemChildAccountRecord getUserFromAccountName(String username) {
		return dm.db().selectFrom(tableAccount).where(tableAccount.ACCOUNT_NAME.eq(username)).fetchOne();
	}

	public B2cSystemChildAccountRecord getUserFromMobile(String mobile) {
		return dm.db().selectFrom(tableAccount).where(tableAccount.MOBILE.eq(mobile)).fetchOne();
	}

	public Result<Record> getPageList(Integer pageNo, Integer pageRows) {
		return dm.db().select().from(tableAccount).join(tableRole).on(tableAccount.ROLE_ID.eq(tableRole.ROLE_ID))
				.limit(pageNo * pageRows, pageRows).fetch();

	}
}

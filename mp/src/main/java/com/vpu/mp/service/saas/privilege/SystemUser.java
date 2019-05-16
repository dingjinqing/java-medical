package com.vpu.mp.service.saas.privilege;

import org.jooq.types.UInteger;
import com.vpu.mp.db.main.tables.B2cSystemUser;
import com.vpu.mp.db.main.tables.records.B2cSystemUserRecord;
import com.vpu.mp.service.foundation.BaseComponent;
import com.vpu.mp.service.foundation.Util;

public class SystemUser extends BaseComponent {
	public B2cSystemUser tableUser = B2cSystemUser.B2C_SYSTEM_USER;

	public B2cSystemUserRecord verify(String username, String password) {
		B2cSystemUserRecord user = dm.db().selectFrom(tableUser).where(tableUser.USER_NAME.eq(username))
				.or(tableUser.MOBILE.eq(username)).fetchOne();
		if (user != null) {
			String md5Pass = Util.md5(password);
			if (user.getPassword().equals(md5Pass)) {
				return user;
			}
		}
		return null;
	}

	public boolean checkNewPass(String oldPassword, UInteger userId) {
		B2cSystemUserRecord user = dm.db().selectFrom(tableUser).where(tableUser.SYSTEM_USER_ID.eq(userId))
				.and(tableUser.PASSWORD.eq(Util.md5(oldPassword))).fetchOne();
		return user != null;
	}

	public int updateNewPass(String newPassword, UInteger userId) {
		return dm.db().update(tableUser).set(tableUser.PASSWORD, Util.md5(newPassword))
				.where(tableUser.SYSTEM_USER_ID.eq(userId)).execute();
	}
	
	public int updateLoginIp(String lastLoginIp, Integer userId) {
		return dm.db().update(tableUser).set(tableUser.LAST_LOGIN_IP, lastLoginIp)
				.where(tableUser.SYSTEM_USER_ID.eq(UInteger.valueOf(userId))).execute();
	}
}

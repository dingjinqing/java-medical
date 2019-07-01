package com.vpu.mp.service.saas.privilege;

import static com.vpu.mp.db.main.tables.SystemUser.SYSTEM_USER;
import com.vpu.mp.db.main.tables.records.SystemUserRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;

/**
 * 
 * @author 新国
 *
 */
public class SystemUserService extends BaseService {

	public SystemUserRecord verify(String username, String password) {
		SystemUserRecord user = dm.db()
				.selectFrom(SYSTEM_USER)
				.where(SYSTEM_USER.USER_NAME.eq(username))
				.or(SYSTEM_USER.MOBILE.eq(username))
				.fetchAny();
		if (user != null) {
			String md5Pass = Util.md5(password);
			if (user.getPassword().equals(md5Pass)) {
				return user;
			}
		}
		return null;
	}
	
	public SystemUserRecord checkByIdAndNameOnMain(String username, Integer systemUserId) {
		SystemUserRecord user = dm.db()
				.selectFrom(SYSTEM_USER)
				.where(SYSTEM_USER.USER_NAME.eq(username)).and(SYSTEM_USER.SYSTEM_USER_ID.eq(systemUserId))
				.fetchAny();
		if (user != null) {
			return user;
		}
		return null;
	}	

	public boolean checkNewPass(String oldPassword, Integer userId) {
		SystemUserRecord user = dm.db().selectFrom(SYSTEM_USER)
				.where(SYSTEM_USER.SYSTEM_USER_ID.eq(userId))
				.and(SYSTEM_USER.PASSWORD.eq(Util.md5(oldPassword)))
				.fetchAny();
		return user != null;
	}

	public int updateNewPass(String newPassword, Integer userId) {
		return dm.db().update(SYSTEM_USER).set(SYSTEM_USER.PASSWORD, Util.md5(newPassword))
				.where(SYSTEM_USER.SYSTEM_USER_ID.eq(userId)).execute();
	}

	public int updateLoginIp(String lastLoginIp, Integer userId) {
		return dm.db().update(SYSTEM_USER).set(SYSTEM_USER.LAST_LOGIN_IP, lastLoginIp)
				.where(SYSTEM_USER.SYSTEM_USER_ID.eq(Integer.valueOf(userId))).execute();
	}
}

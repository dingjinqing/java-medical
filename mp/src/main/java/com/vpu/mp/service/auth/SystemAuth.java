package com.vpu.mp.service.auth;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.main.tables.records.B2cSystemChildAccountRecord;
import com.vpu.mp.db.main.tables.records.B2cSystemUserRecord;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.Saas;

@Component
public class SystemAuth {

	@Autowired
	HttpServletRequest request;

	@Autowired
	Saas saas;

	public boolean isLogin() {
		HashMap<String, Object> user = user();
		if (user != null) {
			return (Integer) user.get("system_user_id") > 0;
		}
		return false;
	}

	public HttpSession session() {
		return request.getSession();
	}

	public Object session(String name) {
		return session().getAttribute(name);
	}

	public void session(String name, Object value) {
		session().setAttribute(name, value);
	}

	public boolean login(String username, String password) {
		Integer userId = 0;
		B2cSystemUserRecord user = saas.sysUser.verify(username, password);
		if (user == null) {
			B2cSystemChildAccountRecord account = saas.childAccount.verify(username, password);
			if (account == null)
				return false;
			userId = account.getSystemUserId();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("system_user_id", account.getSystemUserId());
			map.put("user_name", account.getAccountName());
			map.put("mobile", account.getMobile());
			map.put("is_sub_account", true);
			map.put("sub_account_id", account.getAccountId());
			map.put("user", account);
			map.put("role_id", account.getRoleId());
			session("sys_login_user",map);
		} else {
			userId = user.getSystemUserId().intValue();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("system_user_id", user.getSystemUserId());
			map.put("user_name", user.getUserName());
			map.put("mobile", user.getMobile());
			map.put("is_sub_account", false);
			map.put("sub_account_id", 0);
			map.put("user", user);
			map.put("role_id", 0);
			session("sys_login_user",map);
		}
		saas.sysUser.updateLoginIp(Util.getCleintIp(request), userId);
		return true;
	}

	public void logout() {
		session().removeAttribute("sys_login_user");
	}

	public boolean attempt(String username, String password) {
		B2cSystemUserRecord user = saas.sysUser.verify(username, password);
		return user != null;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> user() {
		return (HashMap<String, Object>) session("sys_login_user");
	}

	public boolean isChildLogin()
    {
		HashMap<String, Object> user = user();
		if (user != null) {
			return (boolean) user.get("is_sub_account");
		}
        return false;
    }

    public Integer roleId()
    {
        return (Integer)user().get("role_id");
    }

}

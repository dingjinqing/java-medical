package com.vpu.mp.service.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.vpu.mp.db.main.tables.records.SystemChildAccountRecord;
import com.vpu.mp.db.main.tables.records.SystemUserRecord;
import com.vpu.mp.service.foundation.JwtUtil;
import com.vpu.mp.service.foundation.TokenService;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * 
 * @author 新国
 *
 */
@Component
public class SystemAuth {

	@Autowired
	HttpServletRequest request;

	@Autowired
	private TokenService tokenService;

	private static String strue="true";
	private static String sfalse="false";
	
	Logger log=LoggerFactory.getLogger(SystemAuth.class);
	
	SaasApplication saas = SaasApplication.instance();

	public boolean isLogin() {
		HashMap<String, Object> user = user();
		if (user != null) {
			return Util.convert(user.get("system_user_id"), Integer.class, 0) > 0;
		}
		return false;
	}

	public boolean isLoginByToken(String token) {
		Map<String, String> tokenMap=null;
		try {
			tokenMap = JwtUtil.verifyToken(token);
		} catch (Exception e) {
			log.info("传入的token错误");
			e.printStackTrace();
			return false;
		}
		if (tokenMap.isEmpty()) {
			return false;
		}
		String systemUserId = tokenMap.get("system_user_id");
		String subAccountId = tokenMap.get("sub_account_id");
		String userName = tokenMap.get("user_name");
		String isSubAccount = tokenMap.get("is_sub_account");
		if (StringUtils.isEmpty(systemUserId) || StringUtils.isEmpty(subAccountId) || StringUtils.isEmpty(userName)
				|| StringUtils.isEmpty(isSubAccount)) {
			return false;
		}
		String tokenKey = "system__" + systemUserId + "#" + subAccountId;
		if (tokenService.get(tokenKey) != null) {
			// token存在
			if (isSubAccount.equals(strue)) {
				// 子账户
				SystemChildAccountRecord account = saas.childAccount.checkByIdAndNameOnChild(
						Integer.parseInt(subAccountId), userName, Integer.parseInt(systemUserId));
				if (account == null) {
					return false;
				}
			}
			if (isSubAccount.equals(sfalse)) {
				// 主账户

				SystemUserRecord user = saas.sysUser.checkByIdAndNameOnMain(userName,
						Integer.parseInt(systemUserId));
				if (user == null) {
					return false;
				}
			} else {
				// TODO log
				return false;
			}
			return true;
		} else {
			// token不存在
			return false;
		}

	}

	public void updateToken(String token) {
		tokenService.updateToken(tokenToKey(token));
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

	public String setToken(Map<String, String> map) {
		if (!map.isEmpty()) {
			String token = tokenService.generateToken(map);
			tokenService.save("system__" + map.get("system_user_id") + "#" + map.get("sub_account_id"), token);
			return token;
		} else {
			// TODO log
			return null;
		}
	}

	public void deleteToken(String token) {
		tokenService.deleteToken(token);
	}

	/**
	 * 获取token中的信息
	 * @param token
	 * @return
	 */
	public Map<Object, Object> getTokenInfo(String token) {
		return tokenService.getInnerHashInfo(token);
	}

	public Map<String, Object> login(String username, String password) {
		Integer userId = 0;
		SystemUserRecord user = saas.sysUser.verify(username, password);
		String result = null;
		HashMap<String, Object> map = new HashMap<String, Object>(0);
		Map<String, String> tokenMap = new HashMap(0);
		if (user == null) {
			SystemChildAccountRecord account = saas.childAccount.verify(username, password);
			if (account == null) {
				return null;
			}
			

			userId = account.getSystemUserId();
			map.put("system_user_id", account.getSystemUserId().toString());
			map.put("user_name", account.getAccountName().toString());
			map.put("mobile", account.getMobile().toString());
			map.put("is_sub_account", "true");
			map.put("sub_account_id", account.getAccountId().toString());
			//map.put("user", account);
			map.put("role_id", account.getRoleId().toString());
			// session("sys_login_user",map);

			tokenMap.put("system_user_id", account.getSystemUserId().toString());
			tokenMap.put("user_name", account.getAccountName().toString());
			tokenMap.put("sub_account_id", account.getAccountId().toString());
			tokenMap.put("is_sub_account", "true");
			result = setToken(tokenMap);
			tokenService.systemSaveHashMainOrChild(true, userId.toString(),map);
			map.put("token", result);
		} else {
			userId = user.getSystemUserId().intValue();
			map.put("system_user_id", user.getSystemUserId().toString());
			map.put("user_name", user.getUserName().toString());
			map.put("mobile", user.getMobile().toString());
			map.put("is_sub_account", "false");
			map.put("sub_account_id", "0");
			//map.put("user", user);
			map.put("role_id", "0");

			tokenMap.put("system_user_id", user.getSystemUserId().toString());
			tokenMap.put("user_name", user.getUserName());
			tokenMap.put("sub_account_id", "0");
			tokenMap.put("is_sub_account", "false");
			result = setToken(tokenMap);
			// session("sys_login_user",map);
			tokenService.systemSaveHashMainOrChild(false, userId.toString(),map);
			map.put("token", result);
		}
		saas.sysUser.updateLoginIp(Util.getCleintIp(request), userId);
		return map;
	}

	public void logout(String token) {
		deleteToken(tokenToKey(token));
	}

	protected String tokenToKey(String token) {
		Map<String, String> tokenMap = JwtUtil.verifyToken(token);
		String systemUserId = tokenMap.get("system_user_id");
		String subaccountId = tokenMap.get("sub_account_id");
		String tokenKey = "system__" + systemUserId + "#" + subaccountId;
		return tokenKey;
	}

	public boolean attempt(String username, String password) {
		SystemUserRecord user = saas.sysUser.verify(username, password);
		return user != null;
	}

	/**
	 * 查询子账户
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean attemptChild(String username, String password) {
		SystemChildAccountRecord childUser = saas.childAccount.verify(username, password);
		return childUser != null;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> user() {
		return (HashMap<String, Object>) session("sys_login_user");
	}

	public boolean isChildLogin() {
		HashMap<String, Object> user = user();
		if (user != null) {
			return (boolean) user.get("is_sub_account");
		}
		return false;
	}

	public Integer roleId() {
		return Util.convert(user().get("role_id"), Integer.class, 0);
	}

}

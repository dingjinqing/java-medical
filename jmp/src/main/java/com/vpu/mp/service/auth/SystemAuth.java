package com.vpu.mp.service.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.main.tables.records.SystemChildAccountRecord;
import com.vpu.mp.db.main.tables.records.SystemUserRecord;
import com.vpu.mp.service.foundation.JedisManager;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.saas.auth.SystemLoginParam;
import com.vpu.mp.service.pojo.saas.auth.SystemTokenAuthInfo;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * 
 * @author 新国
 *
 */
@Component
public class SystemAuth{

	@Autowired
	protected HttpServletRequest request;

	protected SaasApplication saas = SaasApplication.instance();
	protected JedisManager jedis = JedisManager.instance();
	protected Logger log = LoggerFactory.getLogger(SystemAuth.class);

	final String TOKEN = "V-Token";
	final String AUTH_SECRET = "auth.secret";
	final String AUTH_TIMEOUT = "auth.timeout";
	final String TOKEN_PREFIX = "SYS@";

	/**
	 * 登出
	 */
	public boolean logout() {
		String token =  getToken();
		if (isValidToken(token)) {
			if (jedis.get(token) != null) {
				jedis.delete(token);
				return true;
			}
		}
		return false;
	}
	
	protected String getToken() {
		return request.getHeader(TOKEN);
	}

	/**
	 * 是否有效system登录TOKEN
	 * 
	 * @param token
	 * @return
	 */
	public boolean isValidToken(String token) {
		return token != null && StringUtils.startsWith(token, TOKEN_PREFIX);
	}

	/**
	 * 登录账户
	 * 
	 * @param param
	 * @return
	 */
	public SystemTokenAuthInfo login(SystemLoginParam param) {
		SystemChildAccountRecord subAccount = null;
		SystemUserRecord account = saas.sysUser.verify(param.username, param.password);
		if (account == null) {
			subAccount = saas.childAccount.verify(param.username, param.password);
			if (subAccount == null) {
				return null;
			}
		}

		SystemTokenAuthInfo info = new SystemTokenAuthInfo();
		info.setSystemUserId(account != null ? account.getSystemUserId() : 0);
		info.setUserName(account != null ? account.getUserName() : "");
		info.setSubAccountId(subAccount != null ? subAccount.getAccountId() : 0);
		info.setSubUserName(subAccount != null ? subAccount.getAccountName() : "");
		info.setSubLogin(subAccount != null);
		String token = this.getToken(info);
		info.setToken(token);

		Integer timeout = Util.getInteger(Util.getProperty(AUTH_TIMEOUT));
		jedis.set(token, Util.toJSON(info), timeout);
		return info;
	}

	/**
	 * 得到登录账户的token
	 * 
	 * @param info
	 * @return
	 */
	protected String getToken(SystemTokenAuthInfo info) {
		String str = String.format("system_%d_%d_%s", info.getSystemUserId(), info.getSubAccountId(),
				Util.getProperty(AUTH_SECRET));
		return TOKEN_PREFIX + Util.md5(str);
	}


	/**
	 * 得到当前登录用户
	 * 
	 * @return
	 */
	public SystemTokenAuthInfo user() {
		String token =  getToken();
		if (this.isValidToken(token)) {
			String json = jedis.get(token);
			if (!StringUtils.isBlank(json)) {
				return Util.parseJSON(json, SystemTokenAuthInfo.class);
			}
		}
		return null;
	}
	
}

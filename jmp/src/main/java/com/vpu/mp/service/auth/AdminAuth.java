package com.vpu.mp.service.auth;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.JedisManager;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.auth.ShopLoginParam;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * 
 * @author 新国
 *
 */
@Component
public class AdminAuth{

	@Autowired
	protected HttpServletRequest request;

	protected SaasApplication saas = SaasApplication.instance();
	protected JedisManager jedis = JedisManager.instance();
	protected Logger log = LoggerFactory.getLogger(AdminAuth.class);

	final String TOKEN = "token";
	final String AUTH_SECRET = "auth.secret";
	final String AUTH_TIMEOUT = "auth.timeout";
	final String TOKEN_PREFIX = "ADM@";

	/**
	 * 登出
	 */
	public boolean logout() {
		String token = request.getParameter(TOKEN);
		if (isValidToken(token)) {
			if (jedis.get(token) != null) {
				jedis.delete(token);
				return true;
			}
		}
		return false;
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
	public AdminTokenAuthInfo login(ShopLoginParam param) {
		ShopAccountRecord account = saas.shop.accout.getAccountInfo(param.username);
		if (account == null) {
			return null;
		}
		ShopChildAccountRecord subAccount = null;
		if (param.isSubLogin) {
			subAccount = saas.shop.subAccount.verify(account.getSysId(), param.subUsername, param.password);
			if (subAccount == null) {
				return null;
			}
		} else {
			if (!account.getPassword().equals(Util.md5(param.password))) {
				return null;
			}
		}

		AdminTokenAuthInfo info = new AdminTokenAuthInfo();
		info.setSysId(account.getSysId());
		info.setUserName(account.getUserName());
		info.setSubAccountId(subAccount != null ? subAccount.getAccountId() : 0);
		info.setSubUserName(subAccount != null ? subAccount.getAccountName() : "");
		info.setSubLogin(subAccount != null);
		info.setLoginShopId(0);
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
	protected String getToken(AdminTokenAuthInfo info) {
		String str = String.format("admin_%d_%d_%s", info.getSysId(), info.getSubAccountId(),
				Util.getProperty(AUTH_SECRET));
		return TOKEN_PREFIX+Util.md5(str);
	}

	/**
	 * 切换店铺
	 * @param shopId
	 * @return
	 */
	public boolean switchShopLogin(Integer shopId) {
		AdminTokenAuthInfo info = user();
		if (info == null) {
			return false;
		}
		if (info.getLoginShopId() == shopId) {
			return true;
		}
		ShopRecord shop = saas.shop.getShopById(shopId);
		if (shop == null) {
			return false;
		}
		if (shop.getSysId() != info.getSysId()) {
			return false;
		}

		Integer roleId = 0;
		if (info.isSubLogin()) {
			roleId = saas.shop.getShopAccessRoleId(info.getSysId(), shopId, info.getSubAccountId());
			if (roleId == -1) {
				return false;
			}
		}
		info.setLoginShopId(shopId);
		info.setShopLogin(true);
		Integer timeout = Util.getInteger(Util.getProperty(AUTH_TIMEOUT));
		jedis.set(info.token, Util.toJSON(info), timeout);
		return true;
	}

	/**
	 * 得到当前登录用户
	 * 
	 * @return
	 */
	public AdminTokenAuthInfo user() {
		String token = request.getParameter(TOKEN);
		if (!StringUtils.isBlank(token)) {
			String json = jedis.get(token);
			if (StringUtils.isBlank(json)) {
				return Util.parseJSON(json, AdminTokenAuthInfo.class);
			}
		}
		return null;
	}
}

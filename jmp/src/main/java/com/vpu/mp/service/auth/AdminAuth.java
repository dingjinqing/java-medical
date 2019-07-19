package com.vpu.mp.service.auth;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.main.tables.records.UserLoginRecordRecord;
import com.vpu.mp.service.foundation.JedisManager;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.auth.ShopLoginParam;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * 
 * @author 新国
 *
 */
@Component
public class AdminAuth {

	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected SaasApplication saas ;
	protected JedisManager jedis = JedisManager.instance();
	protected Logger log = LoggerFactory.getLogger(AdminAuth.class);

	final String TOKEN = "V-Token";
	final String AUTH_SECRET = "auth.secret";
	final String AUTH_TIMEOUT = "auth.timeout";
	final String TOKEN_PREFIX = "ADM@";

	/**
	 * 登出
	 */
	public boolean logout() {
		AdminTokenAuthInfo info = user();
		if(info == null) {
			return false;
		}
		this.deleteTokenInfo(info);
		return true;
	}

	protected String getToken() {
		return request != null ? request.getHeader(TOKEN) : null;
	}

	/**
	 * 是否有效system登录TOKEN
	 * 
	 * @param  token
	 * @return
	 */
	public boolean isValidToken(String token) {
		return token != null && StringUtils.startsWith(token, TOKEN_PREFIX);
	}

	/**
	 * 登录账户
	 * 
	 * @param  param
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
		
		// 如果当前登录用户与正在登录的用户相同，则使用当前登录用户的Token
		AdminTokenAuthInfo user = user();
		if(user!=null && user.getSysId() == info.getSysId() && user.getSubAccountId() == info.getSubAccountId()) {
			info.setToken(user.getToken());
		}
		
		this.saveTokenInfo(info);
		return info;
	}

	/**
	 * 保存登录信息
	 * 
	 * @param info
	 */
	public void saveTokenInfo(AdminTokenAuthInfo info) {
		if (StringUtils.isBlank(info.getToken())) {
			String loginToken = TOKEN_PREFIX
					+ Util.md5(String.format("admin_login_%d_%d_%s_%d", info.getSysId(), info.getSubAccountId(),
							Util.getProperty(AUTH_SECRET), Calendar.getInstance().getTimeInMillis()));
			info.setToken(loginToken);
		}
		Integer timeout = Util.getInteger(Util.getProperty(AUTH_TIMEOUT));
		jedis.set(info.token, Util.toJson(info), timeout);
	}

	/**
	 * 删除登录信息
	 * 
	 * @param info
	 */
	public void deleteTokenInfo(AdminTokenAuthInfo info) {
		jedis.delete(info.token);
	}

	/**
	 * 切换店铺
	 * 
	 * @param  shopId
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
		if (saas.shop.checkExpire(shopId)) {
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
		this.saveTokenInfo(info);
		insert(info, shop);
		return true;
	}

	/**
	 * 得到当前登录用户
	 * 
	 * @return
	 */
	public AdminTokenAuthInfo user() {
		String token = getToken();
		if (!StringUtils.isBlank(token)) {
			String json = jedis.get(token);
			if (!StringUtils.isBlank(json)) {
				return Util.parseJson(json, AdminTokenAuthInfo.class);
			}
		}
		return null;
	}

	/**
	 * 重置token的存活时间
	 * 
	 * @param token
	 */
	public void reTokenTtl() {
		AdminTokenAuthInfo info = user();
		if (null != info) {
			this.saveTokenInfo(info);
		}
	}

	/**
	 * 登录时间表更新
	 * @param info
	 * @param shop
	 * @return
	 */
	public int insert(AdminTokenAuthInfo info, ShopRecord shop) {
		UserLoginRecordRecord record = new UserLoginRecordRecord();
		record.setUserName(info.getUserName());
		record.setUserId(info.getSysId());
		if (info.isSubLogin()) {
			record.setUserName(info.getSubUserName());
			record.setUserId(info.getSubAccountId());
			
		}
		record.setSysId(info.getSysId());
		record.setShopName(shop.getShopName());
		record.setShopId(shop.getShopId());
		record.setUserIp(Util.getCleintIp(request));
		return saas.shop.insertUserLoginRecord(record);
	}
}

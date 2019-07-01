package com.vpu.mp.service.auth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jooq.tools.json.JSONParser;
import org.jooq.tools.json.JSONValue;
import org.jooq.tools.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.JwtUtil;
import com.vpu.mp.service.foundation.TokenService;
import com.vpu.mp.service.saas.SaasApplication;

import lombok.Data;

/**
 * 
 * @author 新国
 *
 */
@Component
public class AdminAuth {

	@Data
	public static class ShopLoginParam {
		public String username;
		public String subUsername;
		public String password;
		public Boolean isSubLogin;
	};

	private static String strue="true";
	private static String sfalse="false";
	private static String SYS_ID="sys_id";
	Logger log=LoggerFactory.getLogger(AdminAuth.class);
	
	@Data
	public static class ShopLoginSession implements Serializable {
		private static final long serialVersionUID = 1L;
		public ShopRecord shop;
		public ShopAccountRecord mainAccount;
		public ShopChildAccountRecord subAccount;
		public Integer roleId = 0;
	};

	@Autowired
	HttpServletRequest request;

	@Autowired
	private TokenService tokenService;

	SaasApplication saas = SaasApplication.instance();

	public boolean isLogin() {
		return user() != null;
	}

	public boolean isLoginByToken(String token) {
		Map<String, String> tokenMap=null;
		try {
			tokenMap = JwtUtil.verifyToken(token);
		} catch (Exception e) {
			log.info("传入token错误");
			e.printStackTrace();
			return false;
		}
		if (tokenMap.isEmpty()) {
			return false;
		}
		String isSubLogin = tokenMap.get("isSubLogin");
		if (isSubLogin.isEmpty()) {
			return false;
		}
		String sysId = tokenMap.get("sys_id");
		String accountId = tokenMap.get("account_id");
		if (StringUtils.isEmpty(sysId) || StringUtils.isEmpty(accountId)) {
			return false;
		}
		String tokenKey = "admin_" + sysId + "#" + accountId;
		if (tokenService.get(tokenKey) != null) {
			if (isSubLogin.equals(strue)) {
				// 子账户
				String accountName = tokenMap.get("account_name");
				if (accountName.isEmpty()) {
					return false;
				}
				ShopChildAccountRecord subAccount = saas.shop.subAccount
						.checkByIdAndNameOnChild(Integer.parseInt(sysId), accountName, Integer.parseInt(accountId));
				if (subAccount == null) {
					return false;
				}
			}
			if (isSubLogin.equals(sfalse)) {
				// 主账户
				String userName = tokenMap.get("user_name");
				if (userName.isEmpty()) {
					return false;
				}
				ShopAccountRecord mainAccount = saas.shop.accout.checkByIdAndNameOnMain(userName,
						Integer.parseInt(sysId));
				if (mainAccount == null) {
					return false;
				}
			} else {
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	
	public boolean isShopLoginByToken(String token) {
		
		Map<Object,Object> map=getTokenInfo(token);
		Set<Object> keys=map.keySet();
		for(Object key:keys) {
			if("shopId".equals(key)) {
				if(!StringUtils.isEmpty(map.get(key))) {
					return true;
				}
			}
			break;
		}
		return false;
	}

	public boolean isShopLogin() {
		return isLogin() && user().shop != null;
	}

	public void logout(String token) {
		tokenService.deleteToken(tokenToKey(token));

	}

	public boolean attempt(ShopLoginParam param) {
		ShopAccountRecord user = saas.shop.accout.getAccountInfo(param.username);
		if (user == null) {
			return false;
		}
		if (param.isSubLogin != null && param.isSubLogin) {
			ShopChildAccountRecord subAccount = saas.shop.subAccount.verify(user.getSysId(), param.subUsername,
					param.password);
			return subAccount != null;
		} else {
			ShopAccountRecord mainAccount = saas.shop.accout.verify(param.username, param.password);
			return mainAccount != null;
		}
	}

	public Map<String, Object> login(ShopLoginParam param) {
		ShopAccountRecord user = saas.shop.accout.verify(param.username, param.password);
		Map<String, Object> map = new HashMap(0);
		if (user == null) {
			return map;
		}
		if (param.isSubLogin != null && param.isSubLogin) {
			// 子账户
			ShopChildAccountRecord subAccount = saas.shop.subAccount.verify(user.getSysId(), param.subUsername,
					param.password);
			// 存入缓存
			map = setAdminLoginToken(null, subAccount, param);
		} else {
			// 主账户
			ShopAccountRecord mainAccount = saas.shop.accout.verify(param.username, param.password);
			map = setAdminLoginToken(mainAccount, null, param);
		}

		return map;
	}

	protected Map<String, Object> setAdminLoginToken(ShopAccountRecord mainAccount, ShopChildAccountRecord subAccount,
			ShopLoginParam param) {
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		Map<String, String> tokenMap = new HashMap<>(0);
		String sysId = null;
		String userName = null;
		String accountId = "0";
		if (mainAccount != null) {
			// 主账户
			sysId = mainAccount.getSysId().toString();
			userName = mainAccount.getUserName();
			resultMap.put("sys_id", sysId);
			resultMap.put("user_name", userName);
			resultMap.put("account_name", null);
			resultMap.put("isSubLogin", "false");
			resultMap.put("account_id", null);

			// 用户数据缓存存到redis
			tokenService.adminSaveHashMainOrChild(false, sysId, resultMap);
			tokenMap.put("sys_id", sysId);
			tokenMap.put("user_name", userName);
			tokenMap.put("isSubLogin", "false");
			tokenMap.put("account_id", accountId);
			tokenMap.put("account_name", null);

		}
		if (subAccount != null) {
			// 子账户
			sysId = subAccount.getSysId().toString();
			userName = subAccount.getAccountName();
			resultMap.put("sys_id", sysId);
			resultMap.put("user_name", param.username);
			resultMap.put("account_name", userName);
			resultMap.put("isSubLogin", true);
			resultMap.put("account_id", subAccount.getAccountId());

			accountId = subAccount.getAccountId().toString();

			// 用户数据缓存存到redis
			tokenService.adminSaveHashMainOrChild(true, subAccount.getAccountId().toString(), resultMap);

			tokenMap.put("sys_id", sysId);
			tokenMap.put("user_name", param.username);
			tokenMap.put("isSubLogin", "true");
			tokenMap.put("account_id", subAccount.getAccountId().toString());
			tokenMap.put("account_name", userName);
		}
		String token = tokenService.generateToken(tokenMap);
		// "system__" + map.get("system_user_id")+"#"+map.get("sub_account_id")
		tokenService.save("admin_" + sysId + "#" + accountId, token);

		resultMap.put("token", token);

		return resultMap;
	}

	protected void setLoginSession(ShopAccountRecord mainAccount, ShopChildAccountRecord subAccount, ShopRecord shop,
			Integer roleId) {
		ShopLoginSession user = new ShopLoginSession();
		user.setMainAccount(mainAccount);
		user.setShop(shop);
		user.setSubAccount(subAccount);
		user.setRoleId(roleId);
		session("shop_login_user", user);
	}

	protected Map<String, Object> setAdminLoginToken(ShopAccountRecord mainAccount, ShopChildAccountRecord subAccount,
			ShopRecord shop, Integer roleId) {

		// session("shop_login_user", user);
		Map<String, Object> mapuser = new HashMap(0);
		Map<String, Object> map = new HashMap<String, Object>(0);
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		mapuser.put("mainAccount", mainAccount.intoMap());
		mapuser.put("shop", shop.intoMap());
		mapuser.put("subAccount", subAccount.intoMap());
		mapuser.put("roleId", roleId);
		/* map.put("shop_login_user", mapuser); */
		String sysId = null;
		String userName = null;
		Map<String, String> tokenMap = new HashMap<>(0);
		if (mainAccount != null) {
			sysId = mainAccount.getSysId().toString();
			userName = mainAccount.getUserName();
			resultMap.put("user_name", userName);
			resultMap.put("account_name", null);
			resultMap.put("isSubLogin", false);
			resultMap.put("account_id", null);

			tokenMap.put("sys_id", sysId);
			tokenMap.put("user_name", userName);
			tokenMap.put("isSubLogin", "false");

		}
		if (subAccount != null) {
			sysId = subAccount.getSysId().toString();
			userName = subAccount.getAccountName();
			resultMap.put("user_name", null);
			resultMap.put("account_name", userName);
			resultMap.put("isSubLogin", true);
			resultMap.put("account_id", subAccount.getAccountId());
		}
		// 保存token

		// 加子账户名
		String token = tokenService.generateToken(tokenMap);
		tokenService.save("admin_" + sysId, token);
		resultMap.put("sys_id", sysId);
		resultMap.put("token", token);
		return resultMap;
	}

	public boolean switchShopLoginByToken(Integer shopId, String token) {
		Map<Object, Object> map = getTokenInfo(token);
		ShopRecord shop = saas.shop.getShopById(shopId);
		String sysId = map.get("sys_id").toString();
		String accountId = map.get("account_id").toString();
		String isSubLogin = map.get("isSubLogin").toString();
		if (shop == null) {
			return false;
		}
		if (shop.getSysId().equals(map.get(SYS_ID))) {
			return false;
		}
		Integer roleId = 0;
		if (isSubLogin.equals(strue)) {
			//子账户
			roleId = saas.shop.getShopAccessRoleId(Integer.parseInt(sysId), shopId, Integer.parseInt(accountId));
			if (roleId == -1) {
				return false;
			}
		}

		if (isSubLogin.equals(sfalse)) {
			//主账户
			ShopAccountRecord mainAccount = saas.shop.accout.getAccountInfoForID(Integer.parseInt(sysId));
			if(mainAccount==null) {
				return false;
			}
			System.out.println("mainAccount"+mainAccount);
			//setLoginSession(mainAccount, null, shop, roleId);
		} else {
			//子账户
			ShopChildAccountRecord subAccount = saas.shop.subAccount.getSubAccountInfo(Integer.parseInt(sysId), Integer.parseInt(accountId));
			if(subAccount==null){
				return false;
			}
			System.out.println("subAccount"+subAccount);
			//setLoginSession(null, subAccount, shop, roleId);
		}
		//数据添加到redis
		tokenService.setHashAdd(tokenToKey(token), "shopId", shopId.toString());
		return true;
	}

	public boolean switchShopLogin(Integer shopId) {
		if (!isLogin()) {
			return false;
		}
		if (this.isShopLogin() && user().getShop().getShopId() == shopId) {
			return true;
		}
		ShopRecord shop = saas.shop.getShopById(shopId);
		if (shop == null) {
			return false;
		}
		if (shop.getSysId() != sysId()) {
			return false;
		}
		Integer roleId = 0;
		if (this.isChildLogin()) {
			roleId = saas.shop.getShopAccessRoleId(sysId(), shopId(), subAccountId());
			if (roleId == -1) {
				return false;
			}
		}

		if (!isChildLogin()) {
			ShopAccountRecord mainAccount = saas.shop.accout.getAccountInfoForID(sysId());
			setLoginSession(mainAccount, null, shop, roleId);
		} else {
			ShopChildAccountRecord subAccount = saas.shop.subAccount.getSubAccountInfo(sysId(), subAccountId());
			setLoginSession(null, subAccount, shop, roleId);
		}
		return true;
	}

	public ShopLoginSession user() {
		return (ShopLoginSession) session("shop_login_user");
	}

	public ShopLoginSession userInRedis(String token) {
		Object o = tokenService.get(token);
		JSONParser jsp = new JSONParser();
		try {
			Object o2 = jsp.parse(o.toString());
			Object o3 = JSONValue.parseWithException(o.toString());
			System.out.println("o2 " + o2.getClass().getName());
			System.out.println("o3 " + o3.getClass().getName());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public Map<String, Object> userInfo(String token) {
		Map<String, Object> map = new HashMap<String, Object>(11);

		if (!isLoginByToken(token)) {
			return map;
		}
		ShopLoginSession userInfo = userInRedis(token);
		map.put("sys_id", sysId());
		map.put("user_name",
				isChildLogin() ? userInfo.subAccount.getAccountName() : userInfo.mainAccount.getUserName());
		map.put("account_name",
				isChildLogin() ? userInfo.subAccount.getAccountName() : userInfo.mainAccount.getAccountName());
		map.put("mobile", isChildLogin() ? userInfo.subAccount.getMobile() : userInfo.mainAccount.getMobile());
		map.put("sub_account_id", subAccountId());
		map.put("is_sub_account", isChildLogin() ? 1 : 0);
		map.put("role_id", roleId());
		map.put("shop", this.isShopLogin() ? userInfo.shop.intoMap() : null);
		map.put("user", isChildLogin() ? userInfo.subAccount.intoMap() : userInfo.mainAccount.intoMap());
		map.put("shop_id", shopId());
		map.put("shop_avatar", this.isShopLogin() ? userInfo.shop.getShopAvatar() : null);
		return map;
	}

	public boolean isChildLogin() {
		return isLogin() && user().subAccount != null;
	}

	public Integer roleId() {
		return isLogin() ? user().roleId : -1;
	}

	public Integer sysId() {
		return isLogin() ? (isChildLogin() ? user().subAccount.getSysId() : user().mainAccount.getSysId()) : -1;
	}

	public Integer sysIdNew(Boolean isSubLogin, String sysId, String subSysId) {
		return true ? (isSubLogin ? Integer.parseInt(subSysId) : Integer.parseInt(sysId)) : -1;
	}

	public Integer shopId() {
		return isShopLogin() ? user().shop.getShopId() : -1;
	}

	public Integer subAccountId() {
		return isChildLogin() ? user().subAccount.getAccountId() : -1;
	}

	public Integer subAccountIdNew(Boolean isSubLogin, String accountId) {
		return isSubLogin ? Integer.parseInt(accountId) : -1;
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

	/**
	 * 重置token时间
	 * @param token
	 */
	public void updateToken(String token) {
		tokenService.updateToken(tokenToKey(token));
	}

	/**
	 * 把token转换成key
	 * @param token
	 * @return
	 */
	protected String tokenToKey(String token) {
		Map<String, String> tokenMap = JwtUtil.verifyToken(token);
		String sysId = tokenMap.get("sys_id");
		String accountId = tokenMap.get("account_id");
		String isSubLogin = tokenMap.get("isSubLogin");
		String key = "Admin_";
		if (isSubLogin.equals(strue)) {
			// 子账户
			key = key + "Child_" + accountId;
		} else {
			// 主账户
			key = key + "Main_" + sysId;
		}
		return key;
	}

	/**
	 * 获取token中的信息
	 * @param token
	 * @return
	 */
	public Map<Object, Object> getTokenInfo(String token) {
		return tokenService.getInnerHashInfo(tokenToKey(token));
	}
}

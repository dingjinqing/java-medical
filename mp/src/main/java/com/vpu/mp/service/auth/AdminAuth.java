package com.vpu.mp.service.auth;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * 
 * @author 新国
 *
 */
@Component
public class AdminAuth {

	final public static class ShopLoginParam {
		public String username;
		public String subUsername;
		public String password;
		public Integer isSubLogin;

		public Integer getIsSubLogin() {
			return isSubLogin;
		}

		public void setIsSubLogin(Integer isSubLogin) {
			this.isSubLogin = isSubLogin;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getSubUsername() {
			return subUsername;
		}

		public void setSubUsername(String subUsername) {
			this.subUsername = subUsername;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	};

	@Autowired
	HttpServletRequest request;

	SaasApplication saas = SaasApplication.instance();

	public boolean isLogin() {
		HashMap<String, Object> user = user();
		if (user != null) {
			return Util.getInteger(user.get("sys_id")) > 0;
		}
		return false;
	}

	public boolean isShopLogin() {
		HashMap<String, Object> user = user();
		if (user != null) {
			return Util.convert(user.get("shop_id"), Integer.class, 0) > 0;
		}
		return false;
	}

	public void logout() {
		session().removeAttribute("shop_login_user");
	}

	public boolean attempt(ShopLoginParam param) {
		ShopAccountRecord user = saas.shop.accout.getAccountInfo(param.username);
		if (user == null) {
			return false;
		}
		if (param.isSubLogin != null && param.isSubLogin == 1) {
			ShopChildAccountRecord subAccount = saas.shop.subAccount.verify(user.getSysId(), param.subUsername,
					param.password);
			return subAccount != null;
		} else {
			ShopAccountRecord mainAccount = saas.shop.accout.verify(param.username, param.password);
			return mainAccount != null;
		}
	}

	public boolean login(ShopLoginParam param) {
		ShopAccountRecord user = saas.shop.accout.getAccountInfo(param.username);
		if (user == null) {
			return false;
		}
		if (param.isSubLogin != null && param.isSubLogin == 1) {
			ShopChildAccountRecord subAccount = saas.shop.subAccount.verify(user.getSysId(), param.subUsername,
					param.password);
			setLoginSession(false, subAccount, null);
		} else {
			ShopAccountRecord mainAccount = saas.shop.accout.verify(param.username, param.password);
			setLoginSession(true, mainAccount, null);
		}

		return true;
	}

	protected void setLoginSession(boolean isMainAccount, Object user, ShopRecord shop) {
		HashMap<String, Object> map = new HashMap<String, Object>(10);
		if (isMainAccount) {
			ShopAccountRecord mainAccount = (ShopAccountRecord) user;
			map.put("sys_id", mainAccount.getSysId());
			map.put("user_name", mainAccount.getUserName());
			map.put("account_name", mainAccount.getAccountName());
			map.put("mobile", mainAccount.getMobile());
			map.put("sub_account_id", 0);
			map.put("is_sub_account", 0);
			map.put("shop", shop == null ? null : shop.intoMap());
			map.put("user", mainAccount.intoMap());
			map.put("shop_id", shop == null ? 0 : shop.getShopId());
			map.put("shop_avatar", shop == null ? "" : shop.getShopAvatar());
			map.put("role_id", 0);
			session("shop_login_user", map);
		} else {
			ShopChildAccountRecord subAccount = (ShopChildAccountRecord) user;
			map.put("sys_id", subAccount.getSysId());
			map.put("user_name", subAccount.getAccountName());
			map.put("account_name", subAccount.getAccountName());
			map.put("mobile", subAccount.getMobile());
			map.put("sub_account_id", subAccount.getAccountId());
			map.put("is_sub_account", 1);
			map.put("shop", shop == null ? null : shop.intoMap());
			map.put("user", subAccount.intoMap());
			map.put("shop_id", shop == null ? 0 : shop.getShopId());
			map.put("shop_avatar", shop == null ? "" : shop.getShopAvatar());
			Integer roleId = -1;
			if (shop != null) {
				roleId = saas.shop.getShopAccessRoleId(subAccount.getSysId(), shop.getShopId(),
						subAccount.getAccountId());
			}
			map.put("role_id", roleId);
			session("shop_login_user", map);
		}
	}

	public boolean switchShopLogin(Integer shopId) {
		if (!isLogin()) {
			return false;
		}
		if (shopId() == shopId) {
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
		if (subAccountId() == 0) {
			ShopAccountRecord mainAccount = saas.shop.accout.getAccountInfoForID(sysId());
			setLoginSession(true, mainAccount, shop);
		} else {
			ShopChildAccountRecord subAccount = saas.shop.subAccount.getSubAccountInfo(sysId(), subAccountId());
			setLoginSession(false, subAccount, shop);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> user() {
		return (HashMap<String, Object>) session("sys_login_user");
	}

	public boolean isChildLogin() {
		return isLogin() && Util.getInteger(user().get("is_sub_account")) == 1;
	}

	public Integer roleId() {
		return isLogin() ? Util.getInteger(user().get("role_id")) : -1;
	}

	public Integer sysId() {
		return isLogin() ? Util.getInteger(user().get("sys_id")) : 0;
	}

	public Integer shopId() {
		return isLogin() ? Util.getInteger(user().get("shop_id")) : 0;
	}

	public Integer subAccountId() {
		return isChildLogin() ? Util.getInteger(user().get("sub_account_id")) : 0;
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
}

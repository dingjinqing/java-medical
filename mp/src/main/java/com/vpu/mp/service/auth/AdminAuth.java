package com.vpu.mp.service.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.Util;
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
		public Integer isSubLogin;
	};

	@Data
	public static class ShopLoginSession {
		public ShopRecord shop;
		public ShopAccountRecord mainAccount;
		public ShopChildAccountRecord subAccount;
		public Integer roleId = 0;
	};

	@Autowired
	HttpServletRequest request;

	SaasApplication saas = SaasApplication.instance();

	public boolean isLogin() {
		return user() != null;
	}

	public boolean isShopLogin() {
		return isLogin() && user().shop != null;
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
			setLoginSession(null, subAccount, null,0);
		} else {
			ShopAccountRecord mainAccount = saas.shop.accout.verify(param.username, param.password);
			setLoginSession(mainAccount, null, null,0);
		}

		return true;
	}

	protected void setLoginSession(ShopAccountRecord mainAccount, ShopChildAccountRecord subAccount, ShopRecord shop,Integer roleId) {
		ShopLoginSession user = new ShopLoginSession();
		user.setMainAccount(mainAccount);
		user.setShop(shop);
		user.setSubAccount(subAccount);
		user.setRoleId(roleId);
		session("shop_login_user", user);
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
			setLoginSession( mainAccount, null,shop,roleId);
		} else {
			ShopChildAccountRecord subAccount = saas.shop.subAccount.getSubAccountInfo(sysId(), subAccountId());
			setLoginSession(null, subAccount, shop,roleId);
		}
		return true;
	}

	public ShopLoginSession user() {
		return (ShopLoginSession) session("shop_login_user");
	}
	
	public Map<String,Object> userInfo(){
		Map<String,Object> map = new HashMap<String,Object>();
		if(!isLogin()) {
			return map;
		}
		map.put("sys_id", sysId());
		map.put("user_name",isChildLogin() ? user().subAccount.getAccountName() :user().mainAccount.getUserName());
		map.put("account_name",isChildLogin() ? user().subAccount.getAccountName() :user().mainAccount.getAccountName());
		map.put("mobile",isChildLogin() ? user().subAccount.getMobile() :user().mainAccount.getMobile());
		map.put("sub_account_id", subAccountId());
		map.put("is_sub_account", isChildLogin() ? 1: 0);
		map.put("role_id", roleId());
		map.put("shop", this.isShopLogin() ?  user().shop.intoMap() : null);
		map.put("user", isChildLogin() ? user().subAccount.intoMap() : user().mainAccount.intoMap() );
		map.put("shop_id", shopId());
		map.put("shop_avatar", this.isShopLogin() ?  user().shop.getShopAvatar() : null);
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

	public Integer shopId() {
		return isShopLogin() ? user().shop.getShopId() : -1;
	}

	public Integer subAccountId() {
		return isChildLogin() ? user().subAccount.getAccountId() : -1;
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

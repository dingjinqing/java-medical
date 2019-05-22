package com.vpu.mp.service.saas;

import java.util.HashMap;

import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.privilege.ChildAccountService;
import com.vpu.mp.service.saas.privilege.MenuService;
import com.vpu.mp.service.saas.privilege.RoleService;
import com.vpu.mp.service.saas.privilege.SystemUserService;
import com.vpu.mp.service.saas.region.RegionService;
import com.vpu.mp.service.saas.shop.SysShopService;
import com.vpu.mp.service.shop.ShopApplication;


/**
 * 
 * @author 新国
 *
 */
public class SaasApplication {

	public SystemUserService sysUser;
	public ChildAccountService childAccount;
	public RoleService role;
	public MenuService menu;
	public SysShopService sysShop;
	public RegionService region;

	private static SaasApplication saas = null;

	public static SaasApplication instance() {
		if (saas == null) {
			saas = new SaasApplication();
		}
		return saas;
	}

	protected SaasApplication() {
		Util.initComponents(this);
	}

	protected HashMap<Integer, ShopApplication> shopList = new HashMap<Integer, ShopApplication>();

	public ShopApplication shop(Integer shopId) {
		if (!shopList.containsKey(shopId)) {
			shopList.put(shopId, new ShopApplication(shopId));
		}
		return shopList.get(shopId);
	}

}

package com.vpu.mp.service.saas;

import java.util.HashMap;

import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.article.ArticleService;
import com.vpu.mp.service.saas.privilege.ChildAccountService;
import com.vpu.mp.service.saas.privilege.MenuService;
import com.vpu.mp.service.saas.privilege.RoleService;
import com.vpu.mp.service.saas.privilege.SystemUserService;
import com.vpu.mp.service.saas.region.RegionService;
import com.vpu.mp.service.saas.shop.ShopService;
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
	public ShopService shop;
	public RegionService region;
	public ArticleService article;

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

	public synchronized ShopApplication  getShopApp(Integer shopId) {
		if (!shopList.containsKey(shopId)) {
			shopList.put(shopId, new ShopApplication(shopId));
		}
		return shopList.get(shopId);
	}

}

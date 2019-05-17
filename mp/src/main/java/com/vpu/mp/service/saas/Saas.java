package com.vpu.mp.service.saas;

import java.util.HashMap;

import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.privilege.ChildAccount;
import com.vpu.mp.service.saas.privilege.MenuManager;
import com.vpu.mp.service.saas.privilege.Role;
import com.vpu.mp.service.saas.privilege.SystemUser;
import com.vpu.mp.service.saas.region.Region;
import com.vpu.mp.service.saas.shop.SysShop;
import com.vpu.mp.service.shop.Shop;


/**
 * 
 * @author 新国
 *
 */
public class Saas {

	public SystemUser sysUser;
	public ChildAccount childAccount;
	public Role role;
	public MenuManager menu;
	public SysShop sysShop;
	public Region region;

	private static Saas saas = null;

	public static Saas instance() {
		if (saas == null) {
			saas = new Saas();
		}
		return saas;
	}

	protected Saas() {
		Util.initComponents(this);
	}

	protected HashMap<Integer, Shop> shopList = new HashMap<Integer, Shop>();

	public Shop shop(Integer shopId) {
		if (!shopList.containsKey(shopId)) {
			shopList.put(shopId, new Shop(shopId));
		}
		return shopList.get(shopId);
	}

}

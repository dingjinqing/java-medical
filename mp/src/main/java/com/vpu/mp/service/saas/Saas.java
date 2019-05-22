package com.vpu.mp.service.saas;

import java.util.HashMap;

import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.privilege.CChildAccount;
import com.vpu.mp.service.saas.privilege.CMenuManager;
import com.vpu.mp.service.saas.privilege.CRole;
import com.vpu.mp.service.saas.privilege.CSystemUser;
import com.vpu.mp.service.saas.region.CRegion;
import com.vpu.mp.service.saas.shop.CSysShop;
import com.vpu.mp.service.shop.Shop;


/**
 * 
 * @author 新国
 *
 */
public class Saas {

	public CSystemUser sysUser;
	public CChildAccount childAccount;
	public CRole role;
	public CMenuManager menu;
	public CSysShop sysShop;
	public CRegion region;

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

package com.vpu.mp.service.saas;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.SpringConfig;
import com.vpu.mp.service.saas.privilege.ChildAccount;
import com.vpu.mp.service.saas.privilege.MenuManager;
import com.vpu.mp.service.saas.privilege.Role;
import com.vpu.mp.service.saas.privilege.SystemUser;
import com.vpu.mp.service.shop.Shop;

@Component
public class Saas {
	
	@Autowired
	public SystemUser sysUser;
	
	@Autowired
	public ChildAccount childAccount;
	
	@Autowired
	public Role role;
	
	@Autowired
	public MenuManager menu;
		
	protected HashMap<Integer,Shop> shopList = new HashMap<Integer,Shop>();
	
	public Shop shop(Integer shopId) {
		if(!shopList.containsKey(shopId)) {
			Shop shop = (Shop)SpringConfig.getBean("shop");
			shop.setShopId(shopId);
			shop.initComponents();
			shopList.put(shopId, shop);
		}
		return shopList.get(shopId);			 
	}
	
	
}

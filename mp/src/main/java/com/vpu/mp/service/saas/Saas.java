package com.vpu.mp.service.saas;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.vpu.mp.service.saas.privilege.ChildAccount;
import com.vpu.mp.service.saas.privilege.MenuManager;
import com.vpu.mp.service.saas.privilege.Role;
import com.vpu.mp.service.saas.privilege.SystemUser;
import com.vpu.mp.service.shop.Shop;

@Component
public class Saas {
	
	public SystemUser sysUser;	
	public ChildAccount childAccount;	
	public Role role;	
	public MenuManager menu;
		
	protected HashMap<Integer,Shop> shopList = new HashMap<Integer,Shop>();

	public Shop shop(Integer shopId) {
		if(!shopList.containsKey(shopId)) {
			shopList.put(shopId, new Shop(shopId));
		}
		return shopList.get(shopId);			 
	}
	
	
}

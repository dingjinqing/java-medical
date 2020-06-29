package com.vpu.jmd.service.base;


import com.vpu.jmd.database.DatabaseManager;
import com.vpu.jmd.service.shop.ShopApplication;
import com.vpu.jmd.service.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 新国
 *
 */
@Service
public class SaasApplication {

	@Autowired
	public DatabaseManager databaseManager;

	@Autowired
	protected ShopApplication shopApplication;
	@Autowired
	public ShopService shop;

	public ShopApplication getShopApp(Integer shopId) {
		databaseManager.switchShopDb(shopId);
		return shopApplication;
	}

}

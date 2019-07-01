package com.vpu.mp.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.auth.AdminAuth;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.service.shop.decoration.MpDecorationService;

/**
 * 
 * @author 新国
 *
 */
public class AdminBaseController extends BaseController {
	
	@Autowired
	protected AdminAuth adminAuth;
	

	protected Integer isGraspShop() {
		if(!adminAuth.isShopLogin()) {
			return 0;
		}
		ShopRecord shop = saas.shop.getShopById(adminAuth.shopId());
		Byte opai = 1, secoo = 2;
		return (shop.getShopFlag() == opai || shop.getShopFlag() == secoo) ? 1 : 0;
	}

	protected boolean isShowGoodsImport() {
		Integer[] showShopIds = { 6261249, 7963871, 4748160, 1398927, 2048756, 7893594 };
		for (Integer shopId : showShopIds) {
			if (shopId == adminAuth.shopId()) {
				return true;
			}
		}
		return false;
	}


	protected Byte getShopFlag() {
		if(!adminAuth.isShopLogin()) {
			return 0;
		}
		ShopRecord shop = saas.shop.getShopById(adminAuth.shopId());
		return (byte) (shop == null ? 0 : Util.getInteger(shop.getShopFlag()));
	}
	
	/**
	 * 得到当前登录店铺
	 * @return
	 */
	protected ShopApplication shop() {
		assert(adminAuth.shopId() > 0);
		return saas.getShopApp(adminAuth.shopId());
	}
	
	/**
	 * 得到登录店铺ID
	 * @return
	 */
	protected Integer shopId() {
		assert(adminAuth.shopId() > 0);
		return adminAuth.shopId();
	}
	
	/**
	 * 日志
	 * @return
	 */
	protected Logger logger() {
		return LoggerFactory.getLogger(this.getClass());
	}
}

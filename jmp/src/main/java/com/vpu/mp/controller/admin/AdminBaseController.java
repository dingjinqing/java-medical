package com.vpu.mp.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.auth.AdminAuth;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.shop.ShopApplication;

/**
 * 
 * @author 新国
 *
 */
public class AdminBaseController extends BaseController {
	
	@Autowired
	protected AdminAuth adminAuth;
	
	
	/**
	 * 得到当前登录店铺
	 * @return
	 */
	protected ShopApplication shop() {
		AdminTokenAuthInfo user = adminAuth.user();
		assert(user!=null && user.isShopLogin());
		return saas.getShopApp(user.getLoginShopId());
	}
	
	/**
	 * 得到登录店铺ID
	 * @return
	 */
	protected Integer shopId() {
		AdminTokenAuthInfo user = adminAuth.user();
		assert(user!=null && user.isShopLogin());
		return user.getLoginShopId();
	}
	
	/**
	 * 日志
	 * @return
	 */
	protected Logger logger() {
		return LoggerFactory.getLogger(this.getClass());
	}
}

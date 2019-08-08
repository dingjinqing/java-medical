package com.vpu.mp.controller.wxapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.auth.WxAppAuth;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.shop.ShopApplication;

/**
 * 
 * @author lixinguo
 *
 */
public class WxAppBaseController  extends BaseController {

	@Autowired
	protected WxAppAuth wxAppAuth;
	

	/**
	 * 得到当前小程序店铺
	 * @return
	 */
	protected ShopApplication shop() {
		return saas.getShopApp(shopId());
	}
	
	/**
	 * 得到当前小程序ID
	 * @return
	 */
	protected Integer shopId() {
		String shopId = this.request.getHeader("V-ShopId");
		return Util.getInteger(shopId);
	}
	
	
	/**
	 * 日志
	 * @return
	 */
	protected Logger logger() {
		return LoggerFactory.getLogger(this.getClass());
	}
}

package com.vpu.mp.controller.wxapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpu.mp.auth.WxAppAuth;
import com.vpu.mp.controller.BaseController;
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
	 * 小程序前端语言，是由店铺控制的，需要在店铺读取 
	 */
	@Override
	protected String getLang() {
		// TODO:待实现
		return "zh_CN";
	}
	
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
		return wxAppAuth.shopId();
	}
	
	
	/**
	 * 日志
	 * @return
	 */
	protected Logger logger() {
		return LoggerFactory.getLogger(this.getClass());
	}
}

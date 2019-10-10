package com.vpu.mp.controller.wxapp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.wxapp.account.WxAppAccountParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.shop.ShopApplication;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 
 * @author zhaojianqiang
 *
 * 2019年10月9日 下午3:36:59
 */
@RestController
public class WxAppAccountController extends WxAppBaseController {

	/**
	 *更新用户昵称，头像
	 * 
	 * @throws WxErrorException
	 */
	@PostMapping("/api/wxapp/account/updateUser")
	public JsonResult updateUser(@RequestBody WxAppAccountParam param) throws WxErrorException {
		logger().info("更新用户昵称，头像");
		Integer shopId = wxAppAuth.shopId();
		ShopApplication shopApp = saas.getShopApp(shopId);
		boolean updateUser = shopApp.user.updateUser(param);
		if(updateUser) {
			return success();
		}
		return fail();
	}

	
}

package com.vpu.mp.controller.wxapp;

import java.util.List;
import java.util.Map;

import com.vpu.mp.auth.WxAppAuth;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.wxapp.account.UserAccoountInfoVo;
import com.vpu.mp.service.pojo.wxapp.account.UserAccountSetParam;
import com.vpu.mp.service.pojo.wxapp.account.UserAccountSetVo;
import com.vpu.mp.service.pojo.wxapp.account.WxAppAccountParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppCommonParam;
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
		boolean updateUser = shopApp.user.updateUser(param, WxAppAuth.TOKEN_PREFIX);
		if(updateUser) {
			return success();
		}
		return fail();
	}
	
	/**
	 * 新版个人中心
	 */
	@PostMapping("/api/wxapp/account/usercenter")
	public JsonResult getNewUserAccoountInfo() {
		logger().info("新版个人中心");
		Integer shopId = wxAppAuth.shopId();
		ShopApplication shopApp = saas.getShopApp(shopId);
		logger().info("接受"+wxAppAuth.user().toString());
		List<Map<String, Object>> moduleData = shopApp.user.parseCenterModule(wxAppAuth.user().getUserId());
		if(moduleData==null) {
			logger().info("用户不存在");
			return fail();
		}
		logger().info("个人中心准备返回"+moduleData);
		UserAccoountInfoVo vo=new UserAccoountInfoVo();
		vo.setModuleData(moduleData);
		vo.setOtherData(new String[0]);
		return success(vo);
	}
	
	
	/**
	 * 账号设置
	 * @return
	 */
	@PostMapping("/api/wxapp/account/setting")
	public JsonResult accountSetting(@RequestBody UserAccountSetParam param) {
		Integer shopId = wxAppAuth.shopId();
		ShopApplication shopApp = saas.getShopApp(shopId);
		JsonResultCode code = shopApp.user.accountSetting(param, wxAppAuth.user());
		UserAccountSetVo data = shopApp.user.accountSetting(wxAppAuth.user().getUserId(), param.getIsSetting());
		if(data!=null) {
			return success(data);
		}
		if(code!=JsonResultCode.CODE_SUCCESS) {
			return success(code);
		}
		return fail(code);
		
	}
}

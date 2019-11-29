package com.vpu.mp.controller.wxapp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.image.UserCenterTraitVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 
 * @author lixinguo
 *
 */
@RestController
public class WxAppLoginController extends WxAppBaseController {

	/**
	 * 小程序
	 * 
	 * @throws WxErrorException
	 */
	@PostMapping("/api/wxapp/login")
	public JsonResult login(@RequestBody WxAppLoginParam param) throws WxErrorException {
		logger().info("小程序登录");
		WxAppSessionUser user = wxAppAuth.login(param,request);
		if(user==null) {
			//登录失败
			return fail(JsonResultCode.ERR_CODE_LOGIN_FAILED);
		}
		return success(user);
	}

	
	/**
	 * 获取分享二维码
	 * @return
	 */
	@PostMapping("/api/wxapp/user/qrcode")
	public JsonResult getUserQrCode() {
		logger().info("进入分享二维码");
		Integer userId = wxAppAuth.user().getUserId();
		UserCenterTraitVo userCenter = shop().ucTraitService.getUserCenter(userId);
		if(userCenter.getStatus().equals((byte)0)) {
			return fail(userCenter.getMsg());
		}
		return success(userCenter.getImage());
	}
}

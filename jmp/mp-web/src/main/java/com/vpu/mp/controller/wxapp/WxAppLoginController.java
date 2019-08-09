package com.vpu.mp.controller.wxapp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.login.WxAppCommonParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;

import me.chanjar.weixin.common.error.WxErrorException;

@RestController
public class WxAppLoginController extends WxAppBaseController {

	/**
	 * 小程序
	 * 
	 * @throws WxErrorException
	 */
	@PostMapping("/api/wxapp/login")
	public JsonResult login(@RequestBody WxAppLoginParam param) throws WxErrorException {
		WxAppSessionUser user = wxAppAuth.login(param);
		return success(user);
	}

	/**
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/api/wxapp/cfg/bottom")
	public JsonResult config(@RequestBody WxAppCommonParam param) {
		return success(this.shop().config.getAppConfig());
	}
}

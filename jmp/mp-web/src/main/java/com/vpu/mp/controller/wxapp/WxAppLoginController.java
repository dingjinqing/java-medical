package com.vpu.mp.controller.wxapp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam;

import me.chanjar.weixin.common.error.WxErrorException;

@RestController
public class WxAppLoginController extends WxAppBaseController {

	/**
	 * 小程序
	 * 
	 * @throws WxErrorException
	 */
	@PostMapping("/api/wxapp/login")
	public JsonResult revenueprofile(@RequestBody WxAppLoginParam param) throws WxErrorException {

		return success(wxAppAuth.login(param));
	}
}

package com.vpu.mp.controller.wxapp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.decorate.WxAppPageParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppCommonParam;

@RestController
public class WxAppConfigControllor extends WxAppBaseController {

	/**
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/api/wxapp/cfg/bottom")
	public JsonResult config(@RequestBody WxAppCommonParam param) {
		return success(this.shop().config.getAppConfig());
	}
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/api/wxapp/index")
	public JsonResult index(@RequestBody WxAppPageParam param) {
		return success(this.shop().mpDecoration.getPageInfo(param));
	}
	
}

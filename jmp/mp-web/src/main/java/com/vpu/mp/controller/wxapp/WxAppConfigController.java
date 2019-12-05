package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.decorate.WxAppPageModuleParam;
import com.vpu.mp.service.pojo.wxapp.decorate.WxAppPageParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppCommonParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author lixinguo
 *
 */
@RestController
public class WxAppConfigController extends WxAppBaseController {

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
	 * @param param
	 * @return
	 */
	@PostMapping("/api/wxapp/index")
	public JsonResult index(@RequestBody WxAppPageParam param) {
		return success(this.shop().mpDecoration.getPageInfo(param));
	}

	@PostMapping("/api/wxapp/page/module")
    public JsonResult module(@RequestBody WxAppPageModuleParam param) {
	    return success(this.shop().mpDecoration.getPageModuleInfo(param));
    }

	@PostMapping("/api/wxapp/locale/get")
	public JsonResult getLocalePack(@RequestBody WxAppCommonParam param) {
		return success(this.shop().config.getLocalePack(getLang()));
	}
	
	
}

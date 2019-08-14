package com.vpu.mp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.wechat.OpenPlatform;

/**
 * 
 * @author lixinguo
 *
 */
@RestController
public class AdminTestController extends AdminBaseController {

	@Autowired
	protected OpenPlatform open;
	
	@RequestMapping(value = "/admin/test")
	public JsonResult test() throws Exception {
		saas.getShopApp(123456).config.bottomCfg.test();
		return success("OK");
	}
}

package com.vpu.mp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.wechat.OpenPlatform;
import com.vpu.mp.service.wechat.bean.open.WxOpenAuthorizerListResult;

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
		WxOpenAuthorizerListResult result = open.getComponentExtService().getAuthorizerList("wx85400b70274e9929",0,10);
		return success(result);
	}
}

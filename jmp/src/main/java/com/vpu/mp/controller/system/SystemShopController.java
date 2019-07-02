package com.vpu.mp.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;


/**
 * 
 * @author 新国
 *
 */
@Controller
public class SystemShopController extends SystemBaseController {

	@PostMapping(value = "/system/shop/check/mobile")
	@ResponseBody
	public JsonResult checkMobile(@RequestParam String mobile) {
		if (saas.shop.hasMobile(mobile)) {
			return fail(JsonResultCode.CODE_ACCOUNT_MODILE_REGISTERED);
		}
		return success();
	}
}

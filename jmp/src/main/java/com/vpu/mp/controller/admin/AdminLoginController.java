package com.vpu.mp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.auth.ShopLoginParam;
import com.vpu.mp.support.LineConvertHump;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminLoginController extends AdminBaseController {
	@ResponseBody
	@RequestMapping(value = "/admin/login")
	public JsonResult login(@LineConvertHump ShopLoginParam param) {
		AdminTokenAuthInfo info = adminAuth.login(param);
		if (info != null) {
			return this.success(info);
		} else {
			return this.fail(JsonResultCode.CODE_ACCOUNT_OR_PWD_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/admin/logout")
	public JsonResult logout(@RequestParam(value = "token", required = true) String token) {
		if (token != null) {
			if (adminAuth.isValidToken(token)) {
				adminAuth.logout();
				return success(JsonResultCode.CODE_LOGOUT_SUCCESS);
			} else {
				return fail(JsonResultCode.CODE_LOGOUT_FAILED);
			}
		} else {
			return fail(JsonResultCode.CODE_LOGOUT_FAILED);
		}
	}

}

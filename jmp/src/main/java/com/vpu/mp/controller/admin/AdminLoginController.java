package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.auth.ShopLoginParam;

/**
 * 
 * @author 新国
 *
 */
@RestController
public class AdminLoginController extends AdminBaseController {

	@PostMapping(value = "/admin/login")
	public JsonResult login(@RequestBody ShopLoginParam param) {
		AdminTokenAuthInfo info = adminAuth.login(param);
		if (info != null) {
			return this.success(info);
		} else {
			return this.fail(JsonResultCode.CODE_ACCOUNT_OR_PWD_ERROR);
		}
	}

	@PostMapping(value = "/admin/logout")
	public JsonResult logout() {
		adminAuth.logout();
		return success(JsonResultCode.CODE_LOGOUT_SUCCESS);
	}

}

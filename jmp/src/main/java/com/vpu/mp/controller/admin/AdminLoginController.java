package com.vpu.mp.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.service.auth.AdminAuth.ShopLoginParam;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.support.LineConvertHump;
/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminLoginController extends AdminBaseController {
	
	@RequestMapping(value = "/admin/login")
	@ResponseBody
	public JsonResult login(HttpServletRequest request,@LineConvertHump  ShopLoginParam param) {
		if (adminAuth.login(param)) {
			return success();
		} else {
			return fail(JsonResultCode.CODE_ACCOUNT_OR_PWD_ERROR);
		}
	}

	@RequestMapping(value = "/admin/logout")
	public ModelAndView logout() {
		adminAuth.logout();
		return this.jsonSuccess();
	}
	
}

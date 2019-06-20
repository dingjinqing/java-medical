package com.vpu.mp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.service.auth.AdminAuth.ShopLoginParam;
import com.vpu.mp.support.LineConvertHump;
/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminLoginController extends AdminBaseController {
	
	@RequestMapping(value = "/admin/login")
	public ModelAndView login(@LineConvertHump  ShopLoginParam param) {
		if (adminAuth.login(param)) {
			return this.jsonSuccess();
		} else {
			return this.jsonFail("用户名或密码不正确，请重新输入");
		}
	}

	@RequestMapping(value = "/admin/logout")
	public ModelAndView logout() {
		adminAuth.logout();
		return this.jsonSuccess();
	}
	
}

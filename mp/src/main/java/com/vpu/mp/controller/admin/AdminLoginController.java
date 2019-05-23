package com.vpu.mp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.service.auth.AdminAuth.ShopLoginParam;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.support.LineConvertHump;
/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminLoginController extends AdminBaseController {
	

	@GetMapping(value = "/admin/login")
	public ModelAndView login() {
		saas.getShopApp(1).menu.getMenu();
		return view("admin/login");
	}

	@PostMapping(value = "/admin/login")
	public ModelAndView login(@LineConvertHump  ShopLoginParam param) {
		if (adminAuth.login(param)) {
			return view("redirect:/admin/index");
		} else {
			return showMessage("登录失败");
		}
	}

	@RequestMapping(value = "/admin/logout")
	public ModelAndView logout() {
		adminAuth.logout();
		return redirect("admin/login");
	}

	@RequestMapping(value = "/admin/login/attempt")
	@ResponseBody
	public JsonResult attempt(@LineConvertHump  ShopLoginParam param) {
		if (adminAuth.attempt(param)) {
			return JsonResult.success("ok");
		} else {
			return JsonResult.fail("用户名或密码不正确，请重新输入", -1);
		}
	}
	
}

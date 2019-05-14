package com.vpu.mp.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.service.auth.SystemAuth;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.saas.Saas;

@Controller
public class SystemLoginController extends SystemBaseController {

	@Autowired
	protected SystemAuth sysAuth;

	@Autowired
	protected Saas saas;

	@RequestMapping(value = "/system/login")
	public ModelAndView login() {
		if (isPost()) {
			if (sysAuth.login(request.getParameter("username"), request.getParameter("password"))) {
				return view("redirect:/system/index");
			} else {
				return showMessage("登录失败");
			}
		}
		return view("system/login");
	}

	@RequestMapping(value = "/system/logout")
	public ModelAndView logout() {
		sysAuth.logout();
		return redirect("system/login");
	}
	
	@RequestMapping(value = "/system/login/attempt")
	@ResponseBody
	public JsonResult attempt() {
		if (sysAuth.attempt(request.getParameter("username"), request.getParameter("password"))) {
			return JsonResult.instance().success("ok");
		} else {
			return JsonResult.instance().fail("用户名或密码不正确，请重新输入",-1);
		}
	}
}

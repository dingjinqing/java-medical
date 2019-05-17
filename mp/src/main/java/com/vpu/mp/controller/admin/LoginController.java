package com.vpu.mp.controller.admin;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.service.foundation.JsonResult;
/**
 * 
 * @author 新国
 *
 */
@Controller
public class LoginController extends BaseController {

	@Autowired
	protected Environment env;
	
	@RequestMapping(value = "/admin/login")
	public ModelAndView login(HttpServletRequest request) {
		if (isPost()) {

		}
		return view("admin/login");
	}

	@RequestMapping(value = "/admin/logout")
	public ModelAndView logout(HttpServletRequest request) {
		// $auth->logout();
		return view("redirect:/admin/login");
	}

	@RequestMapping(value = "/admin/login/attempt")
	@ResponseBody
	public JsonResult attempt(HttpServletRequest request) {
		JsonResult json = new JsonResult();
		return json;
	}

}

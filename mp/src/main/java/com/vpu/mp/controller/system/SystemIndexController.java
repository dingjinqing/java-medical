package com.vpu.mp.controller.system;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


public class SystemIndexController extends SystemBaseController {

	@RequestMapping(value = "/system/login")
	public ModelAndView index() {

		ModelMap model = new ModelMap();
		model.addAttribute("start_main_url", "");
		model.addAttribute("user", this.sysAuth.user());
		model.addAttribute("menu_list", saas.menu.getRoleMenuList(this.sysAuth.roleId()));
		model.addAttribute("first_menu", saas.menu.getTopMenuList());
		model.addAttribute("role_id", this.sysAuth.roleId());
		return view("system/index", model);
	}
	
	@RequestMapping(value = "/system/welcome")
	public ModelAndView welcome() {
		return view("system/welcome");
	}
	
	@RequestMapping(value = "/system/passwordSet")
	public ModelAndView passwordSet() {
		return view("system/passwordSet");
	}
	
//	@RequestMapping(value = "/system/passwordSet")
//	@ResponseBody
//	public JsonResult updatePassword() {
//		return view("system/passwordSet");
//	}


}

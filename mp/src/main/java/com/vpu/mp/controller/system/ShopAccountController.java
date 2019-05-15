package com.vpu.mp.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShopAccountController extends SystemBaseController {
	@RequestMapping(value = "/system/shop/account/list")
	public ModelAndView getShopAccountList() {
		ModelMap model = new ModelMap();
		model.addAttribute("start_main_url", "/system/welcome");
		model.addAttribute("user", this.sysAuth.user());
		model.addAttribute("menu_list", saas.menu.getRoleMenuList(this.sysAuth.roleId()));
		model.addAttribute("first_menu", saas.menu.getTopMenuList());
		model.addAttribute("role_id", this.sysAuth.roleId());
		return view("system/index", model);
	}
}

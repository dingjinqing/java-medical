package com.vpu.mp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class BaseController {

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected Environment env;

	protected ModelAndView view(String path) {
		return getDefaultModelAndView(path);
	}

	protected ModelAndView redirect(String path) {
		return view("redirect:" + path);
	}

	protected ModelAndView redirect(String path, Map<String, ?> model) {
		return view("redirect:" + path, model);
	}

	protected ModelAndView view(String path, Map<String, ?> model) {
		ModelAndView mv = getDefaultModelAndView(path);
		mv.addAllObjects(model);
		return mv;
	}

	protected ModelAndView getDefaultModelAndView(String path) {
		ModelAndView mv = new ModelAndView(path);
		ModelMap model = new ModelMap();
		model.addAttribute("global_title", "微铺宝小程序商家后台");
		model.addAttribute("main_domain", env.getProperty("domain.main"));
		model.addAttribute("image_domain", env.getProperty("domain.image"));
		mv.addAllObjects(model);
		return mv;
	}

	protected boolean isPost() {
		return request.getMethod() == "POST";
	}

}

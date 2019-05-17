package com.vpu.mp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 
 * @author 新国
 *
 */
public class BaseController {

	final static String REDIRECT_PREFIX = "redirect:";
	
	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected Environment env;

	protected ModelAndView view(String path) {
		return getDefaultModelAndView(path);
	}

	protected ModelAndView redirect(String path) {
		return view(REDIRECT_PREFIX + path);
	}

	protected ModelAndView redirect(String path, Map<String, ?> model) {
		return view(REDIRECT_PREFIX + path, model);
	}

	protected ModelAndView view(String path, Map<String, ?> model) {
		ModelAndView mv = getDefaultModelAndView(path);
		mv.addAllObjects(model);
		return mv;
	}

	protected ModelAndView getDefaultModelAndView(String path) {
		if (path.startsWith(REDIRECT_PREFIX)) {
			path = path.substring(9);
			return new ModelAndView(new RedirectView(path));
		}

		ModelAndView mv = new ModelAndView(path);
		ModelMap model = new ModelMap();
		model.addAttribute("global_title", "微铺宝小程序商家后台");
		model.addAttribute("main_domain", env.getProperty("domain.main"));
		model.addAttribute("image_domain", env.getProperty("domain.image"));
		mv.addAllObjects(model);
		return mv;
	}

	protected boolean isPost() {
		return "POST".equals(request.getMethod());
	}

	protected String post(String key) {
		return isPost() ? request.getParameter(key) : null;
	}

	protected String input(String key) {
		return request.getParameter(key);
	}

	protected Map<String, String[]> input() {
		return request.getParameterMap();
	}

	protected Map<String, String> inputMap(String delim) {
		Map<String, String> result = new HashMap<String, String>(0);
		Map<String, String[]> maps = this.input();
		for (String key : maps.keySet()) {
			String value = StringUtils.arrayToDelimitedString(maps.get(key), delim);
			result.put(key, value);
		}
		return result;
	}

	protected Map<String, String> inputMap() {
		return inputMap(",");
	}
}

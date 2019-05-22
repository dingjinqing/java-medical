package com.vpu.mp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import com.vpu.mp.service.saas.SaasApplication;

/**
 * 
 * @author 新国
 *
 */
public class BaseController {

	final static String REDIRECT_PREFIX = "redirect:";

	protected SaasApplication saas = SaasApplication.instance();

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected Environment env;

	protected ModelAndView view(String path) {
		return getDefaultModelAndView(path);
	}

	protected ModelAndView view(String path, Map<String, ?> model) {
		if (this.isRedirectPath(path)) {
			return new ModelAndView(path,model);
		}
		ModelAndView mv = getDefaultModelAndView(path);
		mv.addAllObjects(model);
		return mv;
	}

	protected ModelAndView redirect(String path) {
		return view(getRedirectPath(path));
	}

	protected ModelAndView redirect(String path, Map<String, ?> model) {
		return view(getRedirectPath(path), model);
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

	protected boolean isRedirectPath(String path) {
		return path.startsWith(REDIRECT_PREFIX);
	}

	protected String getRealPath(String path) {
		if (isRedirectPath(path)) {
			return path.substring(9);
		}
		return path;
	}

	protected String getRedirectPath(String path) {
		if (isRedirectPath(path)) {
			return path;
		}
		return REDIRECT_PREFIX + path;
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

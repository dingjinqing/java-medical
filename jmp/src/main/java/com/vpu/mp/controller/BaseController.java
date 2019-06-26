package com.vpu.mp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * 
 * @author 新国
 *
 */

public class BaseController {

	protected SaasApplication saas = SaasApplication.instance();

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected Environment env;

	/**
	 * 
	 * @param module
	 * @param resultCode
	 * @param content
	 * @return
	 */
	public ModelAndView json(JsonResultCode resultCode, Object content) {
		String language = request.getParameter("lang");
		JsonResult r = JsonResult.result(language, resultCode, content);
		ModelMap model = new ModelMap();
		model.addAttribute("error", r.getError());
		model.addAttribute("message", r.getMessage());
		model.addAttribute("content", r.getContent());
		model.addAttribute("lanuage", r.getLanguage());
		return this.json(model);
	}

	public ModelAndView jsonSuccess() {
		return json(JsonResultCode.CODE_SUCCESS, null);
	}

	public ModelAndView jsonSuccess(Object content) {
		return json(JsonResultCode.CODE_SUCCESS, content);
	}

	public ModelAndView jsonFail(JsonResultCode resultCode) {
		return json(resultCode,null);
	}
	
	public ModelAndView jsonFail() {
		return json(JsonResultCode.CODE_FAIL,null);
	}
	
	public JsonResult result(JsonResultCode resultCode, Object content) {
		String language = request.getParameter("lang");
		return JsonResult.result(language, resultCode, content);
	}
	
	public JsonResult success() {
		return result(JsonResultCode.CODE_SUCCESS, null);
	}

	public JsonResult success(Object content) {
		return result(JsonResultCode.CODE_SUCCESS, content);
	}

	public JsonResult fail(JsonResultCode resultCode) {
		return result(resultCode,null);
	}
	
	public JsonResult fail() {
		return result(JsonResultCode.CODE_FAIL,null);
	}
	
	protected ModelAndView json(Map<String, ?> model) {
		ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
		mv.addAllObjects(model);
		return mv;
	}
	
	@Deprecated
	public ModelAndView view(String path,Object o) {
		assert(false);
		return null;
	}
	
	@Deprecated
	public ModelAndView view(String path) {
		assert(false);
		return null;
	}
	
	@Deprecated
	public ModelAndView showMessage(Object msg) {
		return null;
	}
	
	@Deprecated
	protected ModelAndView redirect(String path) {
		return new ModelAndView("redirect:/"+path);
	}

	protected boolean isPost() {
		return "POST".equals(request.getMethod());
	}

	protected boolean isAjax() {
		return (!StringUtils.isEmpty(request.getHeader("x-requested-with"))
				&& request.getHeader("x-requested-with").equals("XMLHttpRequest"));
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

	public String mainUrl(String path) {
		return Util.imageUrl(path, request.getScheme());
	}

	public String imageUrl(String path) {
		return Util.imageUrl(path, request.getScheme());
	}
}

package com.vpu.mp.controller.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class SystemLoginController extends SystemBaseController {

	/**
	 * 登陆 并将相关信息塞入session
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/system/login")
	public JsonResult login(@RequestParam(value = "username") String userName,
			@RequestParam(value = "password") String password, HttpServletRequest httpRequest) {
		Map<String, Object> result = sysAuth.login(userName, password);
		String lange=request.getParameter("lang");
		if (result != null) {
			Map<String, Object> map = new HashMap<String, Object>(6);
			map.put("status", "ok");
			map.put("token", result.get("token"));

			map.put("start_main_url", "/system/welcome");
			// map.put("user", map.get(""));
			map.put("menu_list", saas.menu.getRoleMenuList(Integer.parseInt(result.get("role_id").toString())));
			map.put("first_menu", saas.menu.getTopMenuList());
			map.put("role_id", result.get("role_id"));

			return JsonResult.success(lange, map);
		} else {
			return JsonResult.fail(lange, JsonResultCode.CODE_ACCOUNT_OR_PWD_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/system/logout")
	public JsonResult logout(HttpServletRequest httpRequest) {
		String token=httpRequest.getHeader("token");
		String lange=request.getParameter("lang");
		if(token!=null) {
			if(!sysAuth.isLoginByToken(token)) {
				sysAuth.logout(token);			
				return JsonResult.success(lange, JsonResultCode.CODE_LOGOUT_SUCCESS);				
			}else {
				return JsonResult.fail(lange,JsonResultCode.CODE_LOGOUT_FAILED);
			}
		}else {
			return JsonResult.fail(lange,JsonResultCode.CODE_LOGOUT_FAILED);
		}
	}

}

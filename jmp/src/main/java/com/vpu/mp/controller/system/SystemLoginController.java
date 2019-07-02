package com.vpu.mp.controller.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.saas.auth.SystemLoginParam;
import com.vpu.mp.service.pojo.saas.auth.SystemTokenAuthInfo;
import com.vpu.mp.support.LineConvertHump;

/**
 * 
 * @author 新国
 *
 */
@RestController
public class SystemLoginController extends SystemBaseController {

	/**
	 * 登陆 并将相关信息塞入session
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@PostMapping(value = "/system/login")
	public JsonResult login(@RequestBody SystemLoginParam param) {
		SystemTokenAuthInfo result = sysAuth.login(param);
		if (result != null) {
			return success(result);
		} else {
			return fail(JsonResultCode.CODE_ACCOUNT_OR_PASSWORD_INCRRECT);
		}
	}

	@RequestMapping(value = "/system/logout")
	public JsonResult logout() {
		sysAuth.logout();
		return success(JsonResultCode.CODE_SUCCESS);
	}

}

package com.vpu.mp.controller.system;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.saas.auth.SystemLoginParam;
import com.vpu.mp.service.pojo.saas.auth.SystemTokenAuthInfo;

/**
 * 
 * @author 新国
 *
 */
@RestController
@RequestMapping("/api")
public class SystemLoginController extends SystemBaseController {

	/**
	 * 登陆 并将相关信息塞入session
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@PostMapping(value = "/system/login")
	public JsonResult login(@RequestBody @Valid SystemLoginParam param, BindingResult bResult) {
		if (bResult.hasErrors()) {
			return this.fail(bResult.getFieldError().getDefaultMessage());
		}
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

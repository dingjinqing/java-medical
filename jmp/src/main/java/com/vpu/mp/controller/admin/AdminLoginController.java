package com.vpu.mp.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vpu.mp.service.auth.AdminAuth.ShopLoginParam;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.support.LineConvertHump;
/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminLoginController extends AdminBaseController {
	@ResponseBody
	@RequestMapping(value = "/admin/login")
	public JsonResult login(@LineConvertHump  ShopLoginParam param) {
		String lange=request.getParameter("lang");
		Map<String,Object> map=adminAuth.login(param);
		if (map.size()!=0) {
			return JsonResult.success(null, map);
		} else {
			return JsonResult.fail(lange, JsonResultCode.CODE_FAIL);
		}
	}

	
	@ResponseBody
	@RequestMapping(value = "/admin/logout")
	public JsonResult logout(HttpServletRequest httpRequest) {
		
		String token=httpRequest.getHeader("token");
		String lange=request.getParameter("lang");
		if(token!=null) {
			if(!adminAuth.isLoginByToken(token)) {
				adminAuth.logout(token);			
				return JsonResult.success(lange, JsonResultCode.CODE_LOGOUT_SUCCESS);				
			}else {
				return JsonResult.fail(lange,JsonResultCode.CODE_LOGOUT_FAILED);
			}
		}else {
			return JsonResult.fail(lange,JsonResultCode.CODE_LOGOUT_FAILED);
		}
	}
	
}

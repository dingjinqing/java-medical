package com.vpu.mp.service.auth;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.Util;

/**
 * 
 * @author 新国
 *
 */
@Component
public class SystemAuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	protected SystemAuth sysAuth;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("token");
		if (token == null || token.equals("")) {
			PrintWriter writer = response.getWriter();
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			writer.write(Util.toJSON(JsonResult.fail("No login", JsonResultCode.CODE_FAIL)));
			writer.close();
			response.flushBuffer();
			return false;
		} else {
			if (!sysAuth.isLoginByToken(token)) {
				// token不成功
				// ajax的验证，要是发送的是ajax，以后可以打开
				/*
				 * boolean isAjaxRequest =
				 * (!StringUtils.isBlank(request.getHeader("x-requested-with")) &&
				 * request.getHeader("x-requested-with").equals("XMLHttpRequest"));
				 * 
				 */
				PrintWriter writer = response.getWriter();
				response.setContentType("application/json;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				writer.write(Util.toJSON(JsonResult.fail("Login Time Out", JsonResultCode.CODE_FAIL)));
				writer.close();
				response.flushBuffer();
				return false;
			}
			//验证成功重置token时间
			sysAuth.updateToken(token);
			return true;
		}

	}
}

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
		if (!sysAuth.isLogin()) {
			boolean isAjaxRequest = (!StringUtils.isBlank(request.getHeader("x-requested-with"))
					&& request.getHeader("x-requested-with").equals("XMLHttpRequest"));
			if (isAjaxRequest) {
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(Util.toJSON(JsonResult.fail(request.getParameter("lang"), JsonResultCode.CODE_LOGIN_EXPIRED)));
				writer.close();
				response.flushBuffer();
			} else {
				response.sendRedirect("/system/login");
			}
			return false;
		}
		return true;
	}
}

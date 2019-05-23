package com.vpu.mp.service.auth;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.Util;

/**
 * 
 * @author 新国
 *
 */
@Component
public class AdminAuthInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	protected AdminAuth adminAuth;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!adminAuth.isLogin()) {
			boolean isAjaxRequest = (!StringUtils.isBlank(request.getHeader("x-requested-with"))
					&& request.getHeader("x-requested-with").equals("XMLHttpRequest"));
			if (isAjaxRequest) {
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(Util.toJSON(JsonResult.fail("登录过期，请登录", -9999)));
				writer.close();
				response.flushBuffer();
			} else {
				response.sendRedirect("/admin/login");
			}
			return false;
		}
		return true;
	}
}

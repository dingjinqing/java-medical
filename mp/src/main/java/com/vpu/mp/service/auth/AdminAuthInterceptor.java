package com.vpu.mp.service.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.SaasApplication;

/****
 ** 
 ** @author 新国
 **
 **/
@Component
public class AdminAuthInterceptor extends HandlerInterceptorAdapter {

	private static final int AJAX_CODE_NO_AUTH = -9997;

	private static final String MSG_NO_AUTH = "无权访问此URL";

	private static final String URL_NO_AUTH = "/admin/authority/not?type=subaccount";

	private static final int AJAX_CODE_SELECT_SHOP = -9998;

	private static final String MSG_SELECT_SHOP = "店铺未登录，请先选择店铺";

	private static final String URL_SELECT_SHOP = "/admin/account/shop/select";

	private static final int AJAX_CODE_LOGIN_EXPIRED = -9999;

	private static final String MSG_LOGIN_EXPIRED = "登录过期，请重新登录";

	private static final String URL_LOGIN = "/admin/login";

	@Autowired
	protected AdminAuth adminAuth;

	protected SaasApplication saas = SaasApplication.instance();

	/**
	 * 账号登录例外URL
	 */
	protected String[] accountLoginExcept = {
			"/admin/login",
			"/admin/login/*",
			"/admin/logout",
			"/region/*",
			"/wechat/mini/*",
			"/wechat/no/authorization",
			"/wechat/start/authorization",
			"/wechat/official/account/authorization",
			"/wechat/authorization/callback",
			"/wechat/component/event/callback",
			"/wechat/app/event/*",
			"/wechat/oauth/start/",
			"/wechat/payment/*",
			"/admin/notice/*",
			"/admin/subPasswordModify",
			"/admin/password",
			"/admin/official/*",
			"/admin/public/service/auth/list",
			"/admin/public/service/auth/detail",
			"/admin/public/image/account/*",
			"/admin/authority/*",
	};

	/**
	 * 账号登录后，店铺未登录例外URL
	 */
	protected String[] shopLoginExcept = {
			"/admin/account/user/list",
			"/admin/account/user/query",
			"/admin/account/user/edit",
			"/admin/account/shop/select",
			"/admin/account/shop/switch",
			"/admin/account/shop/switch",
			"/admin/passwordModify",
			"/admin/subPasswordModify",
			"/admin/service/auth/list",
			"/wechat/official/account/authorization",
			"/admin/service/auth/detail",
			"/admin/public/image/account/*",
			"/admin/frame/image/dialog/select",
			"/admin/authority/not",
			"/admin/frame/*",
			"/admin/ajax/*",
			"/admin/account/*",
			"/admin/public/*"
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getRequestURI();

		if (!adminAuth.isLogin()) {
			// 账号未登录，判断例外URL
			if (match(accountLoginExcept, path)) {
				return true;
			}
			errorResponse(request, response, URL_LOGIN, MSG_LOGIN_EXPIRED, AJAX_CODE_LOGIN_EXPIRED);
			return false;
		} else {
			if (!adminAuth.isShopLogin()) {
				// 账号登录，判断店铺登录例外URL
				if (match(shopLoginExcept, path)) {
					return true;
				}
				errorResponse(request, response, URL_SELECT_SHOP, MSG_SELECT_SHOP, AJAX_CODE_SELECT_SHOP);
				return false;
			} else {
				// 账号和店铺都登录，判断路径权限
				if (!saas.shop.menu.isRoleAccess(adminAuth.roleId(), path)) {
					errorResponse(request, response, URL_NO_AUTH, MSG_NO_AUTH, AJAX_CODE_NO_AUTH);
					return false;
				}
			}
		}
		return true;
	}

	protected void errorResponse(HttpServletRequest request, HttpServletResponse response, String path,
			String ajaxMessage, Integer ajaxCode)
			throws Exception {
		boolean isAjaxRequest = (!StringUtils.isBlank(request.getHeader("x-requested-with"))
				&& request.getHeader("x-requested-with").equals("XMLHttpRequest"));
		if (isAjaxRequest) {
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(Util.toJSON(JsonResult.fail(ajaxMessage, ajaxCode)));
			writer.close();
			response.flushBuffer();
		} else {
			response.sendRedirect(path);
		}
	}

	public boolean match(String[] regexps, String path) {
		for (String regexp : regexps) {
			if (match(regexp, path)) {
				return true;
			}
		}
		return false;
	}

	public boolean match(String regexp, String path) {
		if (regexp.charAt(regexp.length() - 1) == '*') {
			regexp = regexp.substring(0, regexp.length() - 1);
			return path.startsWith(regexp);
		} else {
			return regexp.equals(path);
		}
	}
}

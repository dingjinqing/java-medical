package com.vpu.mp.service.auth;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.saas.SaasApplication;

/****
 ** 
 ** @author 新国
 **
 **/
@Component
public class AdminAuthInterceptor extends HandlerInterceptorAdapter {

	private static final String URL_NO_AUTH = "/admin/authority/not?type=subaccount";
	private static final String URL_SELECT_SHOP = "/admin/account/shop/select";
	private static final String URL_LOGIN = "/admin/login";

	@Autowired
	protected AdminAuth adminAuth;

	protected SaasApplication saas = SaasApplication.instance();
	
	final String LANG = "V-Lang";

	/**
	 * 账号登录例外URL
	 */
	protected String[] accountLoginExcept = { "/admin/login", "/admin/login/*", "/admin/logout", "/region/*",
			"/wechat/proxy/*", "/admin/notice/*", "/admin/subPasswordModify", "/admin/password", "/admin/official/*",
			"/admin/public/service/auth/list", "/admin/public/service/auth/detail", "/admin/public/image/account/*",
			"/admin/authority/*", "/admin/message" };

	/**
	 * 账号登录后，店铺未登录例外URL
	 */
	protected String[] shopLoginExcept = { "/admin/account/user/list", "/admin/account/user/query",
			"/admin/account/user/edit", "/admin/account/shop/select", "/admin/account/shop/switch",
			"/admin/account/shop/switch", "/admin/passwordModify", "/admin/subPasswordModify",
			"/admin/service/auth/list", "/wechat/official/account/authorization", "/admin/service/auth/detail",
			"/admin/public/image/account/*", "/admin/frame/image/dialog/select", "/admin/authority/not",
			"/admin/frame/*", "/admin/ajax/*", "/admin/account/*", "/admin/public/*" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getRequestURI();
		String language = request.getHeader(LANG);

		// 如果为账户登录例外URL，直接通过
		if (match(this.accountLoginExcept, path)) {
			return true;
		}
		
		AdminTokenAuthInfo user = adminAuth.user();
		if (user == null) {
			errorResponse(request, response, URL_LOGIN,  (new JsonResult()).fail(language, JsonResultCode.CODE_LOGIN_EXPIRED));
			return false;
		} else {
			if (!user.isShopLogin()) {
				// 账号登录，判断店铺登录例外URL
				if (match(shopLoginExcept, path)) {
					return true;
				}
				errorResponse(request, response, URL_SELECT_SHOP,
						(new JsonResult()).fail(language, JsonResultCode.CODE_ROLE__NO_SELECT_SHOP));
				return false;
			} else {
				// 账号和店铺都登录，判断路径权限
				Integer roleId = saas.shop.getShopAccessRoleId(user.getSysId(), user.getLoginShopId(), user.getSubAccountId());
				if (!saas.shop.menu.isRoleAccess(roleId, path)) {
					errorResponse(request, response, URL_NO_AUTH,
							(new JsonResult()).fail(language, JsonResultCode.CODE_ROLE__NO_AUTH));
					return false;
				}
			}
		}
		return true;
	}

	protected void errorResponse(HttpServletRequest request, HttpServletResponse response, String path,
			JsonResult result) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(Util.toJSON(result));
		writer.close();
		response.flushBuffer();
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
		char asterisk = '*';
		if (regexp.charAt(regexp.length() - 1) == asterisk) {
			regexp = regexp.substring(0, regexp.length() - 1);
			return path.startsWith(regexp);
		} else {
			return regexp.equals(path);
		}
	}
}

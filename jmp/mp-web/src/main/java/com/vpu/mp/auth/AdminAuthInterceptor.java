package com.vpu.mp.auth;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.language.LanguageManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.saas.SaasApplication;


/****
 ** 
 ** @author 新国
 **
 **/
@Component
public class AdminAuthInterceptor extends HandlerInterceptorAdapter {

	private static final String URL_NO_AUTH = "/api/admin/authority/not?type=subaccount";
	private static final String URL_SELECT_SHOP = "/api/admin/account/shop/select";
	private static final String URL_LOGIN = "/api/admin/login";

	Logger log=LoggerFactory.getLogger(AdminAuthInterceptor.class);
	
	@Autowired
	protected AdminAuth adminAuth;
	
	@Autowired
	protected SaasApplication saas;
	
	final String LANG = "V-Lang";

	/**
	 * 账号登录例外URL
	 */
	protected String[] accountLoginExcept = { "/api/admin/login", "/api/admin/login/*", "/api/admin/logout", "/region/*",
			"/wechat/notify/*", "/api/admin/notice/*", "/api/admin/subPasswordModify", "/api/admin/password", "/api/admin/official/*",
			"/api/admin/public/service/auth/list", "/api/admin/public/service/auth/detail", "/api/admin/public/image/account/*",
			"/api/admin/authority/*", "/api/admin/message" };

	/**
	 * 账号登录后，店铺未登录例外URL
	 */
	protected String[] shopLoginExcept = { "/api/admin/account/user/list", "/api/admin/account/user/query",
			"/api/admin/account/user/edit", "/api/admin/account/shop/select", "/api/admin/account/shop/switch",
			"/api/admin/account/shop/switch", "/api/admin/passwordModify", "/api/admin/subPasswordModify",
			"/api/admin/service/auth/list", "/wechat/official/account/authorization", "/api/admin/service/auth/detail",
			"/api/admin/public/image/account/*", "/api/admin/frame/image/dialog/select", "/api/admin/authority/not",
			"/api/admin/frame/*", "/api/admin/ajax/*", "/api/admin/account/*", "/api/admin/public/*" };
	
	
	/**
	 * 一些特殊的api，不校验
	 */
	protected String[] specialExcept = { "/api/admin/checkMenu", "/api/admin/showMenu" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getRequestURI();
		String language = request.getHeader(LANG);
		String enName = request.getHeader("V-EnName");
		
		// 按钮的权限时候传
		String prName = request.getHeader("V-PrName");
		
		// 需要密码的请求验证密码
		String passwd = request.getHeader("V-RolePass");
		
		// 版本控制传的值
		String vsName = request.getHeader("V-VsName");
		
		// 切换语言
		LanguageManager.switchLanguage(language);

		// 如果为账户登录例外URL，直接通过
		if (match(this.accountLoginExcept, path)) {
			return true;
		}

		AdminTokenAuthInfo user = adminAuth.user();
		
		// 设置当前线程登录用户
		ShopBaseService.setCurrentAdminLoginUser(user);
		
		if (user == null) {
			errorResponse(request, response, URL_LOGIN,
					(new JsonResult()).fail(language, JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED));
			return false;
		} else {
			adminAuth.reTokenTtl();
			if (!user.isShopLogin()) {
				// 账号登录，判断店铺登录例外URL
				if (match(shopLoginExcept, path)) {
					return true;
				}
				errorResponse(request, response, URL_SELECT_SHOP,
						(new JsonResult()).fail(language, JsonResultCode.CODE_ACCOUNT_ROLE__SHOP_SELECT));
				log.debug("请求"+path+"选择店铺后操作");
				return false;
			} else {
				if(match(specialExcept, path)) {
					return true;
				}
				// 账号和店铺都登录，判断权限
				Integer roleId = saas.shop.getShopAccessRoleId(user.getSysId(), user.getLoginShopId(),user.getSubAccountId());

				// 版本权限的校验
				JsonResultCode versionAccess = saas.shop.menu.versionAccess(user.loginShopId, path, enName, vsName);
				if (!versionAccess.equals(JsonResultCode.CODE_SUCCESS)) {
					errorResponse(request, response, URL_NO_AUTH, (new JsonResult()).fail(language, versionAccess));
					return false;
					//等添加好之后再放开
					//return true;
				}

				// 判断页面对应api权限
				if (!saas.shop.menu.apiAccess(roleId, path, enName)) {
					errorResponse(request, response, URL_NO_AUTH,
							(new JsonResult()).fail(language, JsonResultCode.CODE_ACCOUNT_ROLE__AUTH_INSUFFICIENT));
					return false;
				}

				// 判断功能权限对应（按钮可否点击可对应的密码）
				JsonResultCode jsoCode = saas.shop.menu.passwdAccess(roleId, path, prName, passwd);
				if (!jsoCode.equals(JsonResultCode.CODE_SUCCESS)) {
					errorResponse(request, response, URL_NO_AUTH, (new JsonResult()).fail(language, jsoCode));
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
		writer.write(Util.toJson(result));
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

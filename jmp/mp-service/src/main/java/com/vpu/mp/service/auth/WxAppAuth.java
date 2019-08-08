package com.vpu.mp.service.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.saas.SaasApplication;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenMaService;

/**
 * 
 * @author 新国
 *
 */
@Component
public class WxAppAuth {

	@Value(value = "${auth.timeout}")
	protected Integer timeout;

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected SaasApplication saas;

	@Autowired
	protected JedisManager jedis;

	public static final String TOKEN = "V-Token";

	public static final String TOKEN_PREFIX = "WXAPP@";

	/**
	 * 
	 * @return
	 */
	protected String getToken() {
		return request.getHeader(TOKEN);
	}
	
	/**
	 * 得到当前小程序ID
	 * @return
	 */
	protected Integer shopId() {
		String shopId = this.request.getHeader("V-ShopId");
		return Util.getInteger(shopId);
	}

	/**
	 * 是否有效system登录TOKEN
	 * 
	 * @param token
	 * @return
	 */
	public boolean isValidToken(String token) {
		return token != null && StringUtils.startsWith(token, TOKEN_PREFIX);
	}
	

	/**
	 * 登录账户
	 * 
	 * @param param
	 * @return
	 * @throws WxErrorException
	 */
	public WxAppSessionUser login(WxAppLoginParam param) throws WxErrorException {
		Integer shopId = shopId();
		WxOpenMaService maService = saas.shop.mp.getMaServiceByShopId(shopId);
		WxMaJscode2SessionResult result = maService.jsCode2SessionInfo(param.getCode());

		// TODO:保存用户信息

		// 保存session
		String token = TOKEN_PREFIX
				+ Util.md5(String.format("%d_%s_%s", shopId, result.getOpenid(), result.getSessionKey()));
		WxAppSessionUser sessionUser = new WxAppSessionUser();
		sessionUser.setOpenId(result.getOpenid());
		sessionUser.setToken(token);
		sessionUser.setShopId(shopId);
		jedis.set(token, Util.toJson(sessionUser));
		return sessionUser;
	}

	/**
	 * 得到当前登录用户
	 * 
	 * @return
	 */
	public WxAppSessionUser user() {
		String token = getToken();
		if (this.isValidToken(token)) {
			String json = jedis.get(token);
			if (!StringUtils.isBlank(json)) {
				return Util.parseJson(json, WxAppSessionUser.class);
			}
		}
		return null;
	}
}

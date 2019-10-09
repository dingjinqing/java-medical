package com.vpu.mp.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.config.AuthConfig;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.shop.ShopApplication;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 
 * @author 新国
 *
 */
@Component
public class WxAppAuth {

	@Autowired
	protected AuthConfig authConfig;

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected SaasApplication saas;

	@Autowired
	protected JedisManager jedis;

	public static final String TOKEN = "V-Token";

	public static final String TOKEN_PREFIX = "WXAPP@";

	public static final String SHOP_ID = "V-ShopId";
	
	private final Logger log= LoggerFactory.getLogger(WxAppAuth.class);

	/**
	 * 
	 * @return
	 */
	protected String getToken() {
		return request.getHeader(TOKEN);
	}

	/**
	 * 得到当前小程序ID
	 * 
	 * @return
	 */
	public Integer shopId() {
		Integer shopId = Util.getInteger(this.request.getHeader(SHOP_ID));
		if(shopId == 0) {
			throw new IllegalArgumentException("Invalid shopId");
		}
		return shopId;
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
		log.info("登录店铺"+shopId);
		ShopApplication shopApp = saas.getShopApp(shopId);
		ShopRecord shop = saas.shop.getShopById(shopId);
		UserRecord user = shopApp.user.loginGetUser(param);
		if(user==null) {
			return null;
		}
		
		UserDetailRecord userDetail = shopApp.user.userDetail.getUserDetailByUserId(user.getUserId());

		WxAppSessionUser.WxUserInfo wxUser = WxAppSessionUser.WxUserInfo.builder()
				.openId(user.getWxOpenid())
				.unionid(user.getWxUnionId())
				.mobile(user.getMobile())
				.build();
		
		String token = TOKEN_PREFIX + Util.md5(shopId + "_" + user.getUserId());
		WxAppSessionUser sessionUser = new WxAppSessionUser();
		sessionUser.setWxUser(wxUser);
		sessionUser.setToken(token);
		sessionUser.setShopId(shopId);
		sessionUser.setShopFlag(shop.getShopFlag());
		sessionUser.setUserId(user.getUserId());
		sessionUser.setUserAvatar(userDetail == null ? null : userDetail.getUserAvatar());
		sessionUser.setUsername(userDetail == null ? null :userDetail.getUsername());
		sessionUser.setGeoLocation(shopApp.config.shopCommonConfigService.getGeoLocation());
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

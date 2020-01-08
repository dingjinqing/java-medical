package com.vpu.mp.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.config.AuthConfig;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.db.shop.tables.records.UserScoreRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.member.score.UserScoreVo;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.wxapp.account.UserLoginRecordVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.service.shop.image.ImageService;

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
	
	@Autowired
	protected ImageService imageService;

	public static final String TOKEN = "V-Token";

	public static final String TOKEN_PREFIX = "WXAPP@";

	public static final String SHOP_ID = "V-ShopId";

	private final Logger log = LoggerFactory.getLogger(WxAppAuth.class);

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
		if (shopId == 0) {
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
	 * @param request
	 * @return
	 * @throws WxErrorException
	 */
	public WxAppSessionUser login(WxAppLoginParam param, HttpServletRequest request) throws WxErrorException {
		Integer shopId = shopId();
		log.info("登录店铺" + shopId);
		ShopApplication shopApp = saas.getShopApp(shopId);
		ShopRecord shop = saas.shop.getShopById(shopId);
		UserRecord user = shopApp.user.loginGetUser(param);
		if (user == null) {
			log.info("登录失败，user为null");
			return null;
		}
		// 更新记录表
		UserLoginRecordVo record2 = new UserLoginRecordVo();
		record2.setUserId(user.getUserId());
		record2.setUserIp(Util.getCleintIp(request));
		shopApp.userLoginRecordService.userLoginRecord(user.getUserId(), record2);
		log.info("积分相关操作");
		// 积分相关操作
		UserScoreRecord scoreInDay = shopApp.member.score.getScoreInDay(user.getUserId(), "score_login");
		// 获取登录送积分开关配置
		ShopCfgRecord isLoginScore = shopApp.score.getScoreNum("login_score");
		if (scoreInDay != null || (isLoginScore == null || isLoginScore.getV() == "0")) {
			// 没有登录送积分设置
			log.info("没有设置登录送积分");
			// return
		} else {
			log.info("设置登录送积分");
			ShopCfgRecord scoreNum = shopApp.score.getScoreNum("score_login");
			if (scoreNum.getV() != "0") {
				UserScoreVo data = new UserScoreVo();
				data.setUserId(user.getUserId());
				// php注释掉后面addUserScore中对ScoreDis的处理
				// data.setScoreDis(shopApp.user.getUserByUserId(user.getUserId()).getScore());
//				data.setScore(Integer.parseInt(scoreNum.getV()));
//				data.setDesc("score_login");
//				data.setRemarkCode(RemarkTemplate.LOGIN_EVERY_DAY_SEND.code);
//				//data.setRemark("每日登录送积分");
//				data.setShopId(shopId);
//				data.setExpireTime(shopApp.member.score.getScoreExpireTime());
//				shopApp.member.score.addUserScore(data, "0", (byte) 5, (byte) 1);
				ScoreParam param2=new ScoreParam();
				param2.setDesc("score_login");
				param2.setScore(Integer.parseInt(scoreNum.getV()));
				param2.setRemarkCode(RemarkTemplate.LOGIN_EVERY_DAY_SEND.code);
				param2.setExpiredTime(shopApp.member.score.getScoreExpireTime());
				try {
					param2.setUserId(user.getUserId());
					shopApp.member.score.updateMemberScore(param2, 0, RecordTradeEnum.TYPE_SCORE_LOGIN.val(), RecordTradeEnum.UACCOUNT_CONSUMPTION.val());
				} catch (MpException e) {
					log.info("每日登录送积分失败");
					log.info(e.getMessage(),e);
				}
			}
		}

		UserDetailRecord userDetail = shopApp.user.userDetail.getUserDetailByUserId(user.getUserId());
		WxAppSessionUser.WxUserInfo wxUser = WxAppSessionUser.WxUserInfo.builder().openId(user.getWxOpenid())
				.unionid(user.getWxUnionId()).mobile(user.getMobile() != null ? user.getMobile() : "").build();

		String token = TOKEN_PREFIX + Util.md5(shopId + "_" + user.getUserId());
		WxAppSessionUser sessionUser = new WxAppSessionUser();
		sessionUser.setWxUser(wxUser);
		sessionUser.setToken(token);
		sessionUser.setShopId(shopId);
		sessionUser.setShopFlag(shop.getShopFlag());
		sessionUser.setUserId(user.getUserId());
		sessionUser.setUserAvatar(userDetail == null ? null : userDetail.getUserAvatar());
		sessionUser.setUsername(userDetail == null ? null : userDetail.getUsername());
		sessionUser.setGeoLocation(shopApp.config.shopCommonConfigService.getGeoLocation());
		jedis.set(token, Util.toJson(sessionUser));
		sessionUser.setImageHost(imageService.getImageHost());
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

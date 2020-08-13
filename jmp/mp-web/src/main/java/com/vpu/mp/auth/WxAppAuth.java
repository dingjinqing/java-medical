package com.vpu.mp.auth;

import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.config.AuthConfig;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.db.shop.tables.records.UserScoreRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.wxapp.account.UserLoginRecordVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.service.shop.doctor.DoctorService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.user.AdminUserService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.vpu.mp.service.pojo.shop.auth.AuthConstant.AUTH_TYPE_DOCTOR_USER;

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

	@Autowired
	protected AdminUserService adminUserService;

	@Autowired
	protected DoctorService doctorService;

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
        String closeLoginScore = "0";
        boolean noSettingLoginScore = scoreInDay != null || (isLoginScore == null || isLoginScore.getV().equals(closeLoginScore));
        if (noSettingLoginScore) {
			// 没有登录送积分设置
			log.info("没有设置登录送积分");
			// return
		} else {
			log.info("设置登录送积分");
			ShopCfgRecord scoreNum = shopApp.score.getScoreNum("score_login");
            String zeroScore = "0";
            if (!scoreNum.getV().equals(zeroScore)) {
				ScoreParam param2=new ScoreParam();
				param2.setDesc("score_login");
				param2.setScore(Integer.parseInt(scoreNum.getV()));
				param2.setRemarkCode(RemarkTemplate.LOGIN_EVERY_DAY_SEND.code);
				param2.setExpiredTime(shopApp.member.score.getScoreExpireTime());
				param2.setUserId(user.getUserId());
				try {
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
		//获取用户角色
        Byte userType = user.getUserType();
		WxAppSessionUser sessionUser = new WxAppSessionUser();
		sessionUser.setWxUser(wxUser);
		sessionUser.setToken(token);
		sessionUser.setShopId(shopId);
		sessionUser.setShopFlag(shop.getShopFlag());
		sessionUser.setUserId(user.getUserId());
		sessionUser.setUserAvatar(userDetail == null ? null : userDetail.getUserAvatar());
		sessionUser.setUsername(userDetail == null ? null : userDetail.getUsername());
		sessionUser.setGeoLocation(shopApp.config.shopCommonConfigService.getGeoLocation());

		//添加用户个人角色信息
        sessionUser.setUserType(user.getUserType());
        if (userType == 0){
            sessionUser.setDoctorId(0);
            sessionUser.setPharmacistId(0);
        }
        //如果当前用户是医师，那么直接进入医师界面
        if (userType == 1) {
            Integer doctorId = adminUserService.getDoctorId(user.getUserId());
            sessionUser.setDoctorId(doctorId);
        }

        //TODO 后期药师角色验证
        //如果当前用户是药师，直接进入药师界面
//        if (userType == 2) {
//
//        }

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

    /**
     * 更新当前医师缓存信息
     */
	public void updateUserType(Integer doctorId){
        String json = jedis.get(getToken());
        if (!StringUtils.isBlank(json)) {
            WxAppSessionUser wxAppSessionUser = Util.parseJson(json, WxAppSessionUser.class);
            assert wxAppSessionUser != null;
            wxAppSessionUser.setUserType(AUTH_TYPE_DOCTOR_USER);
            wxAppSessionUser.setDoctorId(doctorId);
            jedis.set(getToken(), Util.toJson(wxAppSessionUser));
            doctorService.updateUserToken(doctorId,getToken());
        }
    }
}

package com.vpu.mp.service.shop.user.user;

import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserDetail.USER_DETAIL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenMaService;

@Service
public class UserService extends ShopBaseService {

	/**
	 * 通过openId获取用户
	 * 
	 * @param openId
	 * @return
	 */
	public UserRecord getUserByOpenId(String openId) {
		return db().fetchAny(USER, USER.WX_OPENID.eq(openId));
	}

	/**
	 * 通过userId获取用户
	 * 
	 * @param userId
	 * @return
	 */
	public UserRecord getUserByUserId(Integer userId) {
		return db().fetchAny(USER, USER.USER_ID.eq(userId));
	}

	/**
	 * 登陆用户 TODO:简单登陆，以后添加复杂功能。
	 * @param loginUser
	 * @return
	 * @throws WxErrorException
	 */
	public UserRecord loginUser(WxAppLoginParam loginUser) throws WxErrorException {
		Integer shopId = this.getShopId();
		WxOpenMaService maService = saas.shop.mp.getMaServiceByShopId(shopId);
		WxMaJscode2SessionResult result = maService.jsCode2SessionInfo(loginUser.getCode());
		UserRecord user = getUserByOpenId(result.getOpenid());
		if (user == null) {
			user = db().newRecord(USER);
			user.setWxOpenid(result.getOpenid());
			// 新用户，处理来源
			// TODO: user.setScene(value);
			// TODO: user.setInviteSource(value);
			user.setUsername(result.getOpenid());
			String unionId = (!StringUtils.isBlank(result.getUnionid())) ? result.getUnionid() : "";
			user.setWxUnionId(unionId);
		}else {
			if((!StringUtils.isBlank(result.getUnionid()))) {
				user.setWxUnionId(result.getUnionid());
			}
		}
		user.store();
		return user;
	}

	
	public UserDetailRecord getUserDetail(Integer userId) {
		return db().fetchAny(USER_DETAIL, USER_DETAIL.USER_ID.eq(userId));
	}
}

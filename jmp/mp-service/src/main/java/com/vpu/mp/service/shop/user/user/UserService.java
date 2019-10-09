package com.vpu.mp.service.shop.user.user;

import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserDetail.USER_DETAIL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.ChannelRecord;
import com.vpu.mp.db.shop.tables.records.FriendPromoteActivityRecord;
import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam.PathQuery;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenMaService;

@Service
public class UserService extends ShopBaseService {

	@Autowired
	public UserDetailService userDetail;
	private int userActiveEnter[] = { 1001, 1005, 1006, 1019, 1020, 1024, 1026, 1027, 1023, 1028, 1034, 1035, 1037,
			1038, 1042, 1014, 1043, 1045, 1046, 1052, 1053, 1056, 1057, 1058, 1064, 1067, 1068, 1071, 1072, 1073, 1074,
			1078, 1079, 1081, 1082, 1084, 1089, 1090, 1091, 1092, 1095, 1097, 1102, 1039, 1103, 1104, 1129, 1099, 1059,
			1054, 1022, 1030 };

	private int userPassiveEnter[] = { 1007, 1008, 1017, 1029, 1036, 1044, 1047, 1048, 1049, 1096 };

	private int scanqrode[] = { 1011, 1012, 1013, 1025, 1031, 1032 };

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
	 * 
	 * @param loginUser
	 * @return
	 * @throws WxErrorException
	 */
	public UserRecord loginGetUser(WxAppLoginParam loginUser) throws WxErrorException {
		Integer shopId = this.getShopId();
		WxOpenMaService maService = saas.shop.mp.getMaServiceByShopId(shopId);
		WxMaJscode2SessionResult result = maService.jsCode2SessionInfo(loginUser.getCode());
		UserRecord record =null;
		if(StringUtils.isNotEmpty(result.getOpenid())) {
			record = getUserId(result.getOpenid(), loginUser.getName(), loginUser.getAvatar(), loginUser.getPathQuery(), result);
		}
		return record;
	}

	public List<UserRecord> getUserRecordByIds(List<Integer> idList) {
		return db().select(USER.WX_OPENID, USER.WX_UNION_ID, USER.USER_ID).from(USER).where(USER.USER_ID.in(idList))
				.fetchInto(USER);
	}
	
	//根据openid获取user_id
	public UserRecord getUserId(String openid,String userName,String avatar,PathQuery pathQuery,WxMaJscode2SessionResult result) {
		UserRecord ret = db().selectFrom(USER).where(USER.WX_OPENID.eq(openid)).fetchAny();
		if(ret!=null) {
			if(StringUtils.isNotEmpty(userName)&&(ret.getWxOpenid().equals(ret.getUsername()) || (!ret.getUsername().equals(userName)))) {
				db().update(USER).set(USER.USERNAME,userName).where(USER.WX_OPENID.eq(openid)).execute();
			}
			if(StringUtils.isNotBlank(avatar)) {
				UserDetailRecord userDetail = this.getUserDetail(ret.getUserId());
				if(userDetail!=null) {
					if(StringUtils.isEmpty(userDetail.getUserAvatar())|| (!avatar.equals(userDetail.getUserAvatar()))) {
						db().update(USER_DETAIL).set(USER_DETAIL.USERNAME,userName).set(USER_DETAIL.USER_AVATAR,avatar).where(USER_DETAIL.USER_ID.eq(ret.getUserId())).execute();
					}
				}
			}
			return ret;
		}else {
			//新用户
			Map<String, String> source = getInviteSource(pathQuery);
			//根据微信场景值判断会员来源
			int userSource = getuserSource(pathQuery.getScene());
			//主库是否插入
			UserRecord user = db().newRecord(USER);
			user.setWxOpenid(openid);
			user.setScene(userSource);
			user.setUsername(userName==null?openid:userName);
			user.setInviteSource(source.get("invite_source"));
			user.setInviteActId(Integer.parseInt(source.get("invite_act_id")));
			String unionId = (!StringUtils.isBlank(result.getUnionid())) ? result.getUnionid() : "";
			user.setWxUnionId(unionId);
			user.insert();
			//avatar==null?"/image/admin/head_icon.png":avatar
			
			Integer userId = getUserByOpenId(openid).getUserId();
			UserDetailRecord uDetailRecord=db().newRecord(USER_DETAIL);
			uDetailRecord.setUserId(userId);
			uDetailRecord.setUsername(userName==null?openid:userName);
			uDetailRecord.setUserAvatar(avatar==null?"/image/admin/head_icon.png":avatar);
			uDetailRecord.insert();

			String path = pathQuery.getPath();
			Map<String, String> query = pathQuery.getQuery();
			if (path.equals("pages/pinlotteryinfo/pinlotteryinfo")&& pathQuery.getQuery().get("group_draw_id")!=null && pathQuery.getQuery().get("invite_id")!=null) {
				pathQuery.getQuery().put("user_id", userId.toString());
				saas.getShopApp(this.getShopId()).groupDrawInvite.createInviteRecord(path, query, (byte)1);
			}
			if (path.equals("pages/index/index")||path.equals("pages/item/item")&& pathQuery.getQuery().get("channel")!=null ) {
				saas.getShopApp(getShopId()).channelService.recordChannel(pathQuery.getChannel(), userId, (byte)1);
			}
			return user;
		}
	}

	public UserDetailRecord getUserDetail(Integer userId) {
		return db().fetchAny(USER_DETAIL, USER_DETAIL.USER_ID.eq(userId));
	}

	public int getuserSource(Integer scene) {
		int userSource = -4; //未获取
		if (IntStream.of(userActiveEnter).anyMatch(x -> x == scene)) {
			userSource = -1;//主动进入
		}
		if (IntStream.of(userPassiveEnter).anyMatch(x -> x == scene)) {
			userSource = -2;//被动进入
		}
		if (IntStream.of(scanqrode).anyMatch(x -> x == scene)) {
			userSource = -1; //扫码进入
		}
		return userSource;
	}

	public Map<String, String> getInviteSource(PathQuery pathQuery) {
		String path = pathQuery.getPath();
		Map<String, String> map = new HashMap<String, String>();
		if (path.equals("pages/groupbuyitem/groupbuyitem") || path.equals("pages/groupbuyinfo/groupbuyinfo")) {
			map.put("invite_source", "groupbuy");// 拼团
			map.put("invite_act_id", pathQuery.getQuery().get("pin_group_id"));
		}
		if (path.equals("pages/bargainitem/bargainitem") || path.equals("pages/bargaininfo/bargaininfo")) {
			map.put("invite_source", "bargain");// 砍价
			map.put("invite_act_id", pathQuery.getQuery().get("bargain_id"));
		}
		if (path.equals("pages/seckillitem/seckillitem")) {
			map.put("invite_source", "seckill");// 秒杀
			map.put("invite_act_id", pathQuery.getQuery().get("sk_id"));
		}
		if (path.equals("pages/integralitem/integralitem")) {
			map.put("invite_source", "integral");// 积分购买
			map.put("invite_act_id", pathQuery.getQuery().get("integral_goods_id"));
		}
		if (path.equals("pages/lottery/lottery")) {
			map.put("invite_source", "lottery");// 抽奖
			map.put("invite_act_id", pathQuery.getQuery().get("lottery_id"));
		}
		if (path.equals("pages/form/form")) {
			map.put("invite_source", "form");// 表单
			map.put("invite_act_id", pathQuery.getQuery().get("page_id"));
		}
		if (path.equals("pages/usercardinfo/usercardinfo")) {
			map.put("invite_source", "membercard");// 会员卡
			map.put("invite_act_id", pathQuery.getQuery().get("card_id"));
		}
		if (path.equals("pages/item/item")) {
			if (!StringUtils.isEmpty(pathQuery.getQuery().get("channel"))) {
				// 渠道页
				ChannelRecord channelInfo = saas.getShopApp(this.getShopId()).channelService
						.getChannelInfo(pathQuery.getQuery().get("channel"));
				if (channelInfo != null) {
					map.put("invite_source", "channel");
					map.put("invite_act_id", channelInfo.getId().toString());
				} else {
					map.put("invite_source", "scanqrcode");
					// TODO php上有问题 app/Service/Shop/User/User/User.php
					map.put("invite_act_id", "1");
				}
			}
			if (!StringUtils.isEmpty(pathQuery.getQuery().get("shareAward"))) {
				map.put("invite_source", "share_award");// 分享有礼
				map.put("invite_act_id", pathQuery.getQuery().get("share_id"));
			} else {
				map.put("invite_source", "goods"); // 商品
				map.put("invite_act_id", pathQuery.getQuery().get("good_id"));
			}
		}
		if (path.equals("pages/index/index")) {
			// TODO 有问题
			ChannelRecord channelInfo = saas.getShopApp(this.getShopId()).channelService
					.getChannelInfo(pathQuery.getQuery().get("channel"));
			if (channelInfo != null) {
				map.put("invite_source", "channel");
				map.put("invite_act_id", channelInfo.getId().toString());
			} else {
				map.put("invite_source", "scanqrcode");
				// TODO php上有问题 app/Service/Shop/User/User/User.php
				map.put("invite_act_id", "1");
			}
		}
		if (path.equals("pages/maingoodslist/maingoodslist")) {
			map.put("invite_source", "purchase_price");// 加价购
			map.put("invite_act_id", pathQuery.getQuery().get("identity_id"));
		}
		if (path.equals("pages/fullprice/fullprice")) {
			map.put("invite_source", "purchase_price");// 满折满减
			map.put("invite_act_id", pathQuery.getQuery().get("identity_id"));
		}
		if (path.equals("pages/pinlotteryinfo/pinlotteryinfo")) {
			map.put("invite_source", "group_draw");// 拼团抽奖
			map.put("invite_act_id", pathQuery.getQuery().get("group_draw_id"));
		}
		if (path.equals("pages/pinintegration/pinintegration")) {
			map.put("invite_source", "pin_integration");// 拼团抽奖
			map.put("invite_act_id", pathQuery.getQuery().get("pinInte_id"));
		}
		if (path.equals("pages1/promoteinfo/promoteinfo")) {
			map.put("invite_source", "friend_promote");// 好友助力
			FriendPromoteActivityRecord promoteInfo = saas.getShopApp(this.getShopId()).friendPromoteService
					.promoteInfo(0, pathQuery.getQuery().get("actCode"));
			map.put("invite_act_id", promoteInfo.getId().toString());
		}
		return map;
	}
}

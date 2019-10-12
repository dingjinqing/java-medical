package com.vpu.mp.service.shop.user.user;

import static com.vpu.mp.db.shop.Tables.SHOP_CFG;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserDetail.USER_DETAIL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.ChannelRecord;
import com.vpu.mp.db.shop.tables.records.FriendPromoteActivityRecord;
import com.vpu.mp.db.shop.tables.records.OrderVerifierRecord;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserImportDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.shop.version.VersionConfig;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.score.CheckSignVo;
import com.vpu.mp.service.pojo.wxapp.account.WxAppAccountParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam.PathQuery;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.goods.FootPrintService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.store.store.StoreService;
import com.vpu.mp.service.shop.user.user.collection.UserCollectionService;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenMaService;

@Service
public class UserService extends ShopBaseService {

	@Autowired
	public UserDetailService userDetail;

	@Autowired
	public UserCardService userCard;
	
	@Autowired
	public CouponService coupon;
	
	@Autowired
	public StoreService storeService;
	
	@Autowired
	public UserCollectionService collection;
	
	@Autowired
	private OrderInfoService orderInfo;
	
	@Autowired
	private FootPrintService footPrintService;
	
	@Autowired
	private ConfigService config;
	
	private int userActiveEnter[] = { 1001, 1005, 1006, 1019, 1020, 1024, 1026, 1027, 1023, 1028, 1034, 1035, 1037,
			1038, 1042, 1014, 1043, 1045, 1046, 1052, 1053, 1056, 1057, 1058, 1064, 1067, 1068, 1071, 1072, 1073, 1074,
			1078, 1079, 1081, 1082, 1084, 1089, 1090, 1091, 1092, 1095, 1097, 1102, 1039, 1103, 1104, 1129, 1099, 1059,
			1054, 1022, 1030 };

	private int userPassiveEnter[] = { 1007, 1008, 1017, 1029, 1036, 1044, 1047, 1048, 1049, 1096 };

	private int scanqrode[] = { 1011, 1012, 1013, 1025, 1031, 1032 };

	private String SESSION_SIGN_KEY = "weipubao!@#miniprogram";

	final protected String userCenterJson = "user.center.json";

	@Autowired
	protected JedisManager jedis;

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
		logger().info("获取登录后的session信息. " + result.toString());
		UserRecord record = null;
		if (StringUtils.isNotEmpty(result.getOpenid())) {
			logger().info("进入getUserId");
			record = getUserId(result.getOpenid(), loginUser.getName(), loginUser.getAvatar(), loginUser.getPathQuery(),
					result);
		}
		// 记录unionid
		if (record != null && StringUtils.isNotEmpty(result.getUnionid())) {
			if (!result.getUnionid().equals(record.getWxUnionId())) {
				record.setWxUnionId(result.getUnionid());
				int update = record.update();
				logger().info("更新unionid" + update);
				// TODO syncUserToCrm
			}
		}
		if (StringUtils.isNotEmpty(loginUser.getSystemVersion())
				&& (!loginUser.getSystemVersion().equals(record.getDevice()))) {
			record.setDevice(loginUser.getSystemVersion());
			int update = record.update();
			logger().info("更新Device" + update);
		}
		String sessionKey = getSessionKey(shopId, record.getUserId());
		jedis.set(sessionKey, result.getSessionKey(), 60 * 60 * 24);
		logger().info("更新sessionKey");
		return record;
	}

	private String getSessionKey(Integer shopId, Integer userId) {
		return SESSION_SIGN_KEY + Util.md5(shopId + "_" + userId);
	}

	public List<UserRecord> getUserRecordByIds(List<Integer> idList) {
		return db().select(USER.WX_OPENID, USER.WX_UNION_ID, USER.USER_ID).from(USER).where(USER.USER_ID.in(idList))
				.fetchInto(USER);
	}

	// 根据openid获取user_id
	public UserRecord getUserId(String openid, String userName, String avatar, PathQuery pathQuery,
			WxMaJscode2SessionResult result) {
		UserRecord ret = db().selectFrom(USER).where(USER.WX_OPENID.eq(openid)).fetchAny();
		if (ret != null) {
			if (StringUtils.isNotEmpty(userName)
					&& (ret.getWxOpenid().equals(ret.getUsername()) || (!ret.getUsername().equals(userName)))) {
				db().update(USER).set(USER.USERNAME, userName).where(USER.WX_OPENID.eq(openid)).execute();
			}
			if (StringUtils.isNotBlank(avatar)) {
				UserDetailRecord userDetail = this.getUserDetail(ret.getUserId());
				if (userDetail != null) {
					if (StringUtils.isEmpty(userDetail.getUserAvatar())
							|| (!avatar.equals(userDetail.getUserAvatar()))) {
						db().update(USER_DETAIL).set(USER_DETAIL.USERNAME, userName)
								.set(USER_DETAIL.USER_AVATAR, avatar).where(USER_DETAIL.USER_ID.eq(ret.getUserId()))
								.execute();
					}
				}
			}
			logger().info(openid + " 老用户");
			return ret;
		} else {
			logger().info(openid + " 新用户");
			// 新用户
			Map<String, String> source = getInviteSource(pathQuery);
			// 根据微信场景值判断会员来源
			int userSource = getuserSource(pathQuery.getScene());
			// 主库是否插入
			UserRecord user = db().newRecord(USER);
			user.setWxOpenid(openid);
			user.setScene(userSource);
			user.setUsername(userName == null ? openid : userName);
			if (!source.isEmpty()) {
				logger().info("邀请来源不空" + source.toString());
				user.setInviteSource(source.get("invite_source"));
				user.setInviteActId(Integer.parseInt(source.get("invite_act_id")));
			} else {
				logger().info("邀请来源为空");
			}
			String unionId = (!StringUtils.isBlank(result.getUnionid())) ? result.getUnionid() : "";
			user.setWxUnionId(unionId);
			int insert = user.insert();
			logger().info("插入user结果 " + insert);
			// avatar==null?"/image/admin/head_icon.png":avatar
			if (insert < 0) {
				return null;
			}
			Integer userId = getUserByOpenId(openid).getUserId();
			if (userName == null) {
				String name = "用户" + (10000 + userId);
				user.setUsername(name);
				user.update();
				logger().info("更新用户名为" + name);
			}
			UserDetailRecord uDetailRecord = db().newRecord(USER_DETAIL);
			uDetailRecord.setUserId(userId);
			uDetailRecord.setUsername(user.getUsername());
			uDetailRecord.setUserAvatar(avatar == null ? "/image/admin/head_icon.png" : avatar);
			uDetailRecord.insert();

			String path = pathQuery.getPath();
			Map<String, String> query = pathQuery.getQuery();
			if (path.equals("pages/pinlotteryinfo/pinlotteryinfo") && pathQuery.getQuery().get("group_draw_id") != null
					&& pathQuery.getQuery().get("invite_id") != null) {
				pathQuery.getQuery().put("user_id", userId.toString());
				saas.getShopApp(this.getShopId()).groupDrawInvite.createInviteRecord(path, query, (byte) 1);
			}
			if (path.equals("pages/index/index")
					|| path.equals("pages/item/item") && pathQuery.getQuery().get("channel") != null) {
				saas.getShopApp(getShopId()).channelService.recordChannel(pathQuery.getChannel(), userId, (byte) 1);
			}
			return user;
		}
	}

	public UserDetailRecord getUserDetail(Integer userId) {
		return db().fetchAny(USER_DETAIL, USER_DETAIL.USER_ID.eq(userId));
	}

	public int getuserSource(Integer scene) {
		int userSource = -4; // 未获取
		if (IntStream.of(userActiveEnter).anyMatch(x -> x == scene)) {
			userSource = -1;// 主动进入
		}
		if (IntStream.of(userPassiveEnter).anyMatch(x -> x == scene)) {
			userSource = -2;// 被动进入
		}
		if (IntStream.of(scanqrode).anyMatch(x -> x == scene)) {
			userSource = -1; // 扫码进入
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

	/**
	 * 更新用户昵称，头像
	 * 
	 * @param param
	 * @return
	 */
	public boolean updateUser(WxAppAccountParam param) {
		Integer userId = param.getUserId();
		String username = param.getUsername();
		String userAvatar = param.getUserAvatar();
		UserRecord record = getUserByUserId(userId);
		// 更新昵称
		if (StringUtils.isNotEmpty(username)
				&& (StringUtils.isEmpty(record.getUsername()) || (!username.equals(record.getUsername())))) {
			db().update(USER).set(USER.USERNAME, username).where(USER.USER_ID.eq(param.getUserId())).execute();
			db().update(USER_DETAIL).set(USER_DETAIL.USERNAME, username)
					.where(USER_DETAIL.USER_ID.eq(param.getUserId())).execute();
		}
		// 更新头像
		UserDetailRecord userDetailRecord = getUserDetail(userId);
		if (StringUtils.isNotEmpty(userAvatar) && (StringUtils.isEmpty(userDetailRecord.getUserAvatar())
				|| (!userAvatar.equals(userDetailRecord.getUserAvatar())))) {
			db().update(USER_DETAIL).set(USER_DETAIL.USER_AVATAR, userAvatar)
					.where(USER_DETAIL.USER_ID.eq(param.getUserId())).execute();
		}
		Integer shopId = this.getShopId();
		WxOpenMaService maService = saas.shop.mp.getMaServiceByShopId(shopId);
		WxMaUserInfo userInfo = maService.getUserService().getUserInfo(getSessionKey(shopId, userId),
				param.getEncryptedData(), param.getIv());
		if (userInfo != null) {
			if (!userInfo.getUnionId().equals(record.getWxUnionId())) {
				db().update(USER).set(USER.WX_UNION_ID, userInfo.getUnionId()).where(USER.USER_ID.eq(userId)).execute();
				// TODO $crmResult = shop($shopId)->serviceRequest->crmApi->init();
			}
		} else {
			logger().error("wxDecryptData error:" + userInfo.toString());
			return false;
		}
		return true;
	}

	/**
	 * 个人中心数据
	 * 
	 * @param userId
	 * @return 
	 */
	public List<Map<String, Object>> parseCenterModule(Integer userId) {
		List<Map<String, Object>> moduleData = getCenterModule();
		UserRecord userByUserId = getUserByUserId(userId);
		
		for(Map<String, Object> module:moduleData) {
			if (module.get("module_name").equals("center_header")) {
				logger().info("进入center_header");
				module=parseCenterHeader(userId, module);
				logger().info("完成center_header");
			}
			if (module.get("module_name").equals("account_money")) {
				logger().info("进入account_money");
				module.put("content", parseAccountMoney(userByUserId, (List<Map<String, Object>>)module.get("content")));
				logger().info("完成account_money");
			}
			if (module.get("module_name").equals("order")) {
				logger().info("进入order");
				module.put("content", parseMyOrder(userByUserId.getUserId(), (List<Map<String, Object>>)module.get("content")));
				logger().info("完成order");
			}
			if (module.get("module_name").equals("appointment")) {
				logger().info("进入appointment");
				module.put("appointment_info", storeService.serviceOrder.getUserLastOrderInfo(userId));
				logger().info("完成appointment");
			}
			if (module.get("module_name").equals("use_record")) {
				logger().info("进入use_record");
				module.put("collect", collection.getUserCollectNumber(userId));
				module.put("buy_history", orderInfo.getUserBuyGoodsNum(userId));
				module.put("footprint", footPrintService.getfootPrintNum(userId));
				logger().info("完成use_record");
			}
			if (module.get("module_name").equals("service")) {
				logger().info("进入service");
				module.put("content", parseMyService(userByUserId, (List<Map<String, Object>>)module.get("content")));
				logger().info("完成service");
			}
		}
		logger().info("parseCenterModule出");
		return moduleData;
	}
	
	/**
	 * 我的服务
	 * @param userId
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> parseMyService(UserRecord user, List<Map<String, Object>> data) {
		for (Map<String, Object> iconItem : data) {
			if (iconItem.get("icon_name").equals("distribution")) {
				DistributionParam rebate = config.distributionCfg.getDistributionCfg();
				iconItem.put("is_distributor", user.getIsDistributor());
				iconItem.put("judge_status", rebate.getJudgeStatus());
				iconItem.put("is_rebate", rebate.getStatus());
				iconItem.put("rebate_center_name", rebate.getRebateCenterName()==null?"分销中心":rebate.getRebateCenterName());
			}
			if (iconItem.get("icon_name").equals("user_activate")) {
				Boolean activateIsNotice = isNoticeUserActivation(user.getUserId());
				iconItem.put("activate_is_notice", activateIsNotice);
				iconItem.put("is_show", isOne(iconItem.get("is_show"))?(activateIsNotice?1:0):0);
			}
			if (iconItem.get("icon_name").equals("order_verify")) {
				Result<OrderVerifierRecord> oRecords = storeService.storeVerifier.getUserVerifyStores(user.getUserId());
				iconItem.put("is_verifier", oRecords.size()>0?1:0);
				iconItem.put("is_show", isOne(iconItem.get("is_show"))?iconItem.get("is_verifier"):0);
			}
		}
		return data;
	}
	
	
	private Boolean isOne(Object object) {
		return object.equals(1)||object.equals("1");
	}
	
	/**
	 * 我的订单
	 * @param userId
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> parseMyOrder(Integer userId,List<Map<String, Object>> data) {
		//TODO 等王帅写$orderStatusNum = $shop->order->getUserOrderTypeNumber($userId,false);
		for (Map<String, Object> iconItem : data) {
			if(iconItem.get("icon_name").equals("wait_pay")) {
				iconItem.put("num", 1);
			}
			if(iconItem.get("icon_name").equals("wait_deliver")) {
				iconItem.put("num", 2);
			}
			if(iconItem.get("icon_name").equals("wait_receive")) {
				iconItem.put("num", 3);
			}
			if(iconItem.get("icon_name").equals("wait_comment")) {
				iconItem.put("num", 4);
			}
			if(iconItem.get("icon_name").equals("refund")) {
				iconItem.put("num", 6);
			}
		}
		return data;
	}
	
	
	/**
	 * 我的资产
	 * @param record
	 * @param data
	 * @return
	 */
	public List<Map<String, Object>> parseAccountMoney(UserRecord record,List<Map<String, Object>> data) {
		logger().info("我的资产");
		for (Map<String, Object> iconItem : data) {
			if(iconItem.get("icon_name").equals("account")) {
				iconItem.put("num", record.getAccount());
			}
			if(iconItem.get("icon_name").equals("score")) {
				iconItem.put("num", record.getScore());
			}
			if(iconItem.get("icon_name").equals("coupon")) {
				iconItem.put("num", coupon.getCanUseCouponNum(record.getUserId()));
			}
			if(iconItem.get("icon_name").equals("card")) {
				//TODO 等壮壮提供getValidCardList
				iconItem.put("num", 0);
			}
		}
		logger().info("我的资产结束");
		return data;
	}
	

	/**
	 * 个人信息
	 * 
	 * @param userId
	 * @param data
	 * @return
	 */
	public Map<String, Object> parseCenterHeader(Integer userId, Map data) {
		// checkSignInScore
		Map<String, Object> map = new HashMap<String, Object>();
		CheckSignVo checkData = userCard.scoreService.checkSignInScore(userId);
		map.put("sign_score", checkData);
		// TODO 暂时不写
		// $data['qrcode'] = $shop->image->qrcode->getQrcodeInfo(1, $userId);
		map.put("qrcode", "");
		logger().info("用户等级判断");
		// 用户等级判断
		String userGrade = userCard.getUserGrade(userId);
		logger().info("用户等级"+userGrade);
		Integer[] integers = new Integer[1];
		integers[0] = userId;
		if (userGrade.equals(CardConstant.LOWEST_GRADE)) {
			// TODO 等updateGrade写完
			logger().info("进入用户等级为0");
			/*
			 * try { userCard.updateGrade(integers, null, (byte) 1); } catch (Exception e) {
			 * logger().error("userGrade为0时报错"); e.printStackTrace(); }
			 */
		} else {
			// TODO 等updateGrade写完
			logger().info("进入用户等级为其他");
			/*
			 * try { userCard.updateGrade(integers, null, (byte) 0); // 上面方法返回值is_get int
			 * isGet = 0; if (isGet > 0) { map.put("get_grade", 1); } else {
			 * map.put("get_grade", isGet); } } catch (Exception e) {
			 * logger().error("userGrade不为0时报错"); e.printStackTrace(); }
			 */
		}
		logger().info("用户等级判断返回"+map);
		return map;
	}

	/**
	 * 获得个人中心配置数据
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getCenterModule() {
		logger().info("进入获得个人中心配置数据");
		ShopCfgRecord record = db().selectFrom(SHOP_CFG).where(SHOP_CFG.K.eq("user_center")).fetchAny();
		String data = null;
		List<Map<String, Object>> parseJson = new ArrayList<Map<String, Object>>();
		if (record == null || StringUtils.isEmpty(record.getV())) {
			data = Util.loadResource(userCenterJson);
		}
		VersionConfig mergeVersion = saas.shop.version.mergeVersion(getShopId());
		if (mergeVersion.getMainConfig().getSub4().size() > 0) {
			List<String> sub4 = mergeVersion.getMainConfig().getSub4();
			List<String> iconNames = new ArrayList<String>();
			iconNames.add("distribution");
			iconNames.add("bargain");
			parseJson = Util.parseJson(data, List.class);
			for (Map<String, Object> module : parseJson) {
				if (module.get("module_name").equals("service")) {
					List<Map<String, Object>> object = (List<Map<String, Object>>) module.get("content");
					for (Map<String, Object> icon_name : object) {
						if (iconNames.contains(icon_name.get("icon_name"))
								&& (!sub4.contains(icon_name.get("icon_name")))) {
							icon_name.put("is_show", 0);
						}
					}
				}
			}
		}
		return parseJson;
	}
	
	
	/**
	 * 是否展示激活公告
	 * @param userId
	 * @return
	 */
	public Boolean isNoticeUserActivation(Integer userId) {
		Boolean isEnable = checkModuleIsShow("service", "user_activate");
		if(!isEnable) {
			return false;
		}
		UserRecord user = getUserByUserId(userId);
		if(StringUtils.isEmpty(user.getMobile())) {
			return true;
		}
		
		UserImportDetailRecord importUser = userCard.scoreService.member.getUserByMobile(user.getMobile());
		if(importUser!=null||importUser.getIsActivate()==1) {
			return false;
		}
		return true;
		
	}
	
	
	
	
	/**
	 * 检查模块开关是否开启
	 * @param moduleName
	 * @param iconName
	 * @return
	 */
	public Boolean checkModuleIsShow(String moduleName, String iconName) {
		List<Map<String, Object>> moduleData = getCenterModule();
		for (Map<String, Object> module : moduleData) {
			if (module.get("module_name").equals(moduleName) && isOne(module.get("is_show"))) {
				if (StringUtils.isNotEmpty(iconName)) {
					List<Map<String, Object>> cList = (List<Map<String, Object>>) module.get("content");
					for (Map<String, Object> iconItem : cList) {
						if (iconItem.get("icon_name").equals(iconName) && isOne(iconItem.get("is_show"))) {
							return true;
						}
					}
					return false;
				}
				return true;
			}
		}
		return false;
	}

}

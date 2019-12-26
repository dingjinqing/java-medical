package com.vpu.mp.service.shop.user.user;

import static com.vpu.mp.db.shop.Tables.SHOP_CFG;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserCard.USER_CARD;
import static com.vpu.mp.db.shop.tables.UserDetail.USER_DETAIL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.DictCityRecord;
import com.vpu.mp.db.main.tables.records.DictDistrictRecord;
import com.vpu.mp.db.main.tables.records.DictProvinceRecord;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.db.shop.tables.UserDetail;
import com.vpu.mp.db.shop.tables.records.ChannelRecord;
import com.vpu.mp.db.shop.tables.records.FriendPromoteActivityRecord;
import com.vpu.mp.db.shop.tables.records.OrderVerifierRecord;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserImportDetailRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.shop.version.VersionConfig;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.shop.member.score.CheckSignVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.account.UserAccountSetParam;
import com.vpu.mp.service.pojo.wxapp.account.UserAccountSetVo;
import com.vpu.mp.service.pojo.wxapp.account.UserInfo;
import com.vpu.mp.service.pojo.wxapp.account.WxAppAccountParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppLoginParam.PathQuery;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser.WxUserInfo;
import com.vpu.mp.service.saas.shop.ShopImageManageService;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.goods.FootPrintService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.member.wxapp.WxUserCardService;
import com.vpu.mp.service.shop.order.info.MpOrderInfoService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.store.store.StoreService;
import com.vpu.mp.service.shop.user.user.collection.UserCollectionService;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
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

	@Autowired
	public ShopImageManageService image;

	@Autowired
	public QrCodeService qrCode;

	@Autowired
	public MpOrderInfoService mpOrderInfoService;
	
	@Autowired
	public WxUserCardService wxUserCardService;
	
	
	private int[] userActiveEnter = { 1001, 1005, 1006, 1019, 1020, 1024, 1026, 1027, 1023, 1028, 1034, 1035, 1037,
			1038, 1042, 1014, 1043, 1045, 1046, 1052, 1053, 1056, 1057, 1058, 1064, 1067, 1068, 1071, 1072, 1073, 1074,
			1078, 1079, 1081, 1082, 1084, 1089, 1090, 1091, 1092, 1095, 1097, 1102, 1039, 1103, 1104, 1129, 1099, 1059,
			1054, 1022, 1030 };

	private int[] userPassiveEnter = { 1007, 1008, 1017, 1029, 1036, 1044, 1047, 1048, 1049, 1096 };

	private int[] scanqrode = { 1011, 1012, 1013, 1025, 1031, 1032 };

	private String SESSION_SIGN_KEY = "weipubao!@#miniprogram";

	final protected String userCenterJson = "user.center.json";

	public static final Byte SYCUPDATE= 1;
	public static final Byte SYCINSERT= 0;

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
				//syncMainUser(record);
				logger().info("更新unionid" + update);
				// TODO syncUserToCrm
			}
		}
		if (StringUtils.isNotEmpty(loginUser.getSystemVersion())
				&& (!loginUser.getSystemVersion().equals(record.getDevice()))) {
			record.setDevice(loginUser.getSystemVersion());
			int update = record.update();
			//syncMainUser(record);
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
				ret.setWxOpenid(openid);
				//syncMainUser(ret);
			}
			if (StringUtils.isNotBlank(avatar)) {
				UserDetailRecord userDetail = this.getUserDetail(ret.getUserId());
				if (userDetail != null) {
					if (StringUtils.isEmpty(userDetail.getUserAvatar())
							|| (!avatar.equals(userDetail.getUserAvatar()))) {
						db().update(USER_DETAIL).set(USER_DETAIL.USERNAME, userName)
								.set(USER_DETAIL.USER_AVATAR, avatar).where(USER_DETAIL.USER_ID.eq(ret.getUserId()))
								.execute();
						userDetail.setUserAvatar(avatar);
						userDetail.setUsername(userName);
						//syncMainUserDetail(userDetail);
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
			//syncMainUser(user);
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
				//syncMainUser(user);
			}
			UserDetailRecord uDetailRecord = db().newRecord(USER_DETAIL);
			uDetailRecord.setUserId(userId);
			uDetailRecord.setUsername(user.getUsername());
			uDetailRecord.setUserAvatar(avatar == null ? "/image/admin/head_icon.png" : avatar);
			uDetailRecord.insert();
			logger().info("开始同步Detail");
			//syncMainUserDetail(uDetailRecord);

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
					map.put("invite_act_id","0");
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
	public boolean updateUser(WxAppAccountParam param,String tokenPrefix) {
		Integer userId = param.getUserId();
		String username = param.getUsername();
		String userAvatar = param.getUserAvatar();
		UserRecord record = getUserByUserId(userId);
		UserDetailRecord userDetailRecord = getUserDetail(userId);
		// 更新昵称
		if ((StringUtils.isNotEmpty(username)
				&& (StringUtils.isEmpty(record.getUsername())) || (!username.equals(record.getUsername())))) {
			db().update(USER).set(USER.USERNAME, username).where(USER.USER_ID.eq(param.getUserId())).execute();
			db().update(USER_DETAIL).set(USER_DETAIL.USERNAME, username)
					.where(USER_DETAIL.USER_ID.eq(param.getUserId())).execute();
			logger().info("更新昵称");
			record.setUsername(username);
			//syncMainUser(record);
			if(userDetailRecord!=null) {
				userDetailRecord.setUsername(username);
				//syncMainUserDetail(userDetailRecord);
			}

		}
		// 更新头像
		if (StringUtils.isNotEmpty(userAvatar) && (StringUtils.isEmpty(userDetailRecord.getUserAvatar())
				|| (!userAvatar.equals(userDetailRecord.getUserAvatar())))) {
			db().update(USER_DETAIL).set(USER_DETAIL.USER_AVATAR, userAvatar)
					.where(USER_DETAIL.USER_ID.eq(param.getUserId())).execute();
			userDetailRecord.setUserAvatar(userAvatar);
			//syncMainUserDetail(userDetailRecord);
		}
		Integer shopId = this.getShopId();
		WxOpenMaService maService = saas.shop.mp.getMaServiceByShopId(shopId);
		String sessionKey = jedis.get(getSessionKey(shopId, userId));
		logger().info("获取sessionKey"+StringUtils.isEmpty(sessionKey));
		WxMaUserInfo userInfo = maService.getUserService().getUserInfo(sessionKey,
				param.getEncryptedData(), param.getIv());
		logger().info("获取用户信息"+userInfo.toString());
		if (userInfo != null) {
			if (!userInfo.getUnionId().equals(record.getWxUnionId())) {
				db().update(USER).set(USER.WX_UNION_ID, userInfo.getUnionId()).where(USER.USER_ID.eq(userId)).execute();
				record.setWxUnionId(userInfo.getUnionId());
				//syncMainUser(record);
				// TODO $crmResult = shop($shopId)->serviceRequest->crmApi->init();
			}
		} else {
			logger().error("wxDecryptData error:" + userInfo.toString());
			return false;
		}
		String token = tokenPrefix + Util.md5(shopId + "_" +userId);
		String json = jedis.get(token);
		WxAppSessionUser parseJson = Util.parseJson(json, WxAppSessionUser.class);
		if((!parseJson.getUserAvatar().equals(userAvatar))||(!parseJson.getUsername().equals(username))) {
			logger().info("redis更新用户名和头像地址");
			parseJson.setUserAvatar(userAvatar);
			parseJson.setUsername(username);
			jedis.set(token, Util.toJson(parseJson));
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
		if(userByUserId==null) {
			return null;
		}

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
			if(StringUtils.isNotEmpty(String.valueOf(module.get("bg_img")))) {
				module.put("bg_img", image.imageUrl(String.valueOf(module.get("bg_img"))));
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
			iconItem.put("icon", image.imageUrl(String.valueOf(iconItem.get("icon"))));
			if (iconItem.get("icon_name").equals("distribution")) {
				// $default = '{"status":0,"judge_status":0,"rank_status":0,"protect_date":-1}';

				DistributionParam rebate = config.distributionCfg.getDistributionCfg();
				if(rebate==null) {
					rebate = new DistributionParam();
					rebate.setStatus((byte)0);
					rebate.setJudgeStatus((byte)0);
					rebate.setRankStatus((byte)0);
					rebate.setProtectDate((byte)-1);
				}
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
	public List<Map<String, Object>> parseMyOrder(Integer userId, List<Map<String, Object>> data) {
		Map<Byte, Integer> orderStatusNum = mpOrderInfoService.getOrderStatusNum(userId, false);
		for (Map<String, Object> iconItem : data) {
			iconItem.put("icon", image.imageUrl(String.valueOf(iconItem.get("icon"))));
			if (iconItem.get("icon_name").equals("wait_pay")) {
				iconItem.put("num", orderStatusNum.get(OrderConstant.WAIT_PAY));
			}
			if (iconItem.get("icon_name").equals("wait_deliver")) {
				iconItem.put("num", orderStatusNum.get(OrderConstant.WAIT_DELIVERY));
			}
			if (iconItem.get("icon_name").equals("wait_receive")) {
				iconItem.put("num", orderStatusNum.get(OrderConstant.SHIPPED));
			}
			if (iconItem.get("icon_name").equals("wait_comment")) {
				iconItem.put("num", orderStatusNum.get(OrderConstant.FINISHED));
			}
			if (iconItem.get("icon_name").equals("refund")) {
				iconItem.put("num", orderStatusNum.get(OrderConstant.RETURNING));
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
		logger().info("UserRecord"+record);
		for (Map<String, Object> iconItem : data) {
			if(iconItem.get("icon_name").equals("account")) {
				iconItem.put("num", record.getAccount()==null?0:record.getAccount());
			}
			if(iconItem.get("icon_name").equals("score")) {
				iconItem.put("num", record.getScore()==null?0:record.getScore());
			}
			if(iconItem.get("icon_name").equals("coupon")) {
				iconItem.put("num", coupon.getCanUseCouponNum(record.getUserId()));
			}
			if(iconItem.get("icon_name").equals("card")) {
				List<ValidUserCardBean> cardList = userCard.userCardDao.getValidCardList(record.getUserId());
				iconItem.put("num", cardList!=null?cardList.size():0);
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
	public Map<String, Object> parseCenterHeader(Integer userId, Map<String, Object> data) {
		// checkSignInScore
		CheckSignVo checkData = userCard.scoreService.checkSignInScore(userId);
		data.put("sign_score", checkData);
		data.put("qrcode", qrCode.getMpQrCode(QrCodeTypeEnum.PAGE_BOTTOM,"invite_id="+userId));
		logger().info("用户等级判断");
		// 用户等级判断
		String userGrade = userCard.getUserGrade(userId);
		logger().info("用户等级"+userGrade);
		if (userGrade.equals(CardConstant.LOWEST_GRADE)) {
			logger().info("进入用户等级为0");
			try {
				data.put("get_grade", 0);
				userCard.updateGrade(userId, null, (byte) 1);
			} catch (Exception e) {
				logger().error("userGrade为0时报错");
				e.printStackTrace();
			}

		} else {
			logger().info("进入用户等级为其他");
			try {
				int isGet = userCard.updateGrade(userId, null, (byte) 0); // 上面方法返回值is_get
				logger().info("isGet的值为"+isGet);
				if (isGet > 0) {
					data.put("get_grade", 1);
				} else {
					data.put("get_grade", isGet);
				}
			} catch (Exception e) {
				logger().error("userGrade不为0时报错");
				e.printStackTrace();
			}


		}
		logger().info("用户等级判断返回"+data);
		return data;
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
		}else {
			data=record.getV();
		}
		VersionConfig mergeVersion = saas.shop.version.mergeVersion(getShopId());
		if (mergeVersion.getMainConfig().getSub4().size() > 0) {
			List<String> sub4 = mergeVersion.getMainConfig().getSub4();
			List<String> iconNames = new ArrayList<String>();
			iconNames.add("distribution");
			iconNames.add("bargain");
			parseJson = Util.parseJson(data, List.class);
			logger().info("读取配置完,size"+parseJson.size());
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
		logger().info("getCenterModule运行完");
		return parseJson;
	}


	/**
	 * 是否展示激活公告
	 * @param userId
	 * @return
	 */
	public Boolean isNoticeUserActivation(Integer userId) {
		logger().info("是否展示激活公告");
		Boolean isEnable = checkModuleIsShow("service", "user_activate");
		if(!isEnable) {
			return false;
		}
		UserRecord user = getUserByUserId(userId);
		if(StringUtils.isEmpty(user.getMobile())) {
			return true;
		}

		UserImportDetailRecord importUser = userCard.scoreService.member.getUserByMobile(user.getMobile());
		if(importUser==null) {
			return true;
		}
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

	/**
	 * 用户详细信息
	 * @param userId
	 * @return
	 */
	public UserInfo getUserInfo(Integer userId) {
		User a = USER.as("a");
		UserDetail b = USER_DETAIL.as("b");
		User c = USER.as("c");
		return  db().select(a.USER_ID, a.USERNAME,a.INVITE_ID, a.USER_CID, a.MOBILE, a.USER_CODE, a.WX_OPENID, a.CREATE_TIME,a.WECHAT,
				a.FANLI_GRADE, a.USER_GRADE, a.ACCOUNT, a.DISCOUNT, a.WX_UNION_ID, a.DEVICE,a.UNIT_PRICE,a.IS_DISTRIBUTOR,
				a.INVITE_GROUP,a.DISTRIBUTOR_LEVEL,a.INVITE_TIME,a.DISCOUNT_GRADE, a.DEL_FLAG, a.DEL_TIME, a.GROWTH, a.SCORE,a.INVITATION_CODE,
				b.SEX, b.BIRTHDAY_YEAR, b.BIRTHDAY_MONTH, b.BIRTHDAY_DAY, b.REAL_NAME, b.PROVINCE_CODE,b.CITY_CODE, b.DISTRICT_CODE, b.ADDRESS, b.MARITAL_STATUS,
				b.MONTHLY_INCOME, b.CID,b.EDUCATION, b.INDUSTRY_INFO, b.BIG_IMAGE, b.BANK_USER_NAME, b.SHOP_BANK,b.BANK_NO, b.USER_AVATAR, c.USERNAME.as("invite_name")).from(a)
		.leftJoin(b).on(a.USER_ID.eq(b.USER_ID)).leftJoin(c).on(a.INVITE_ID.eq(c.USER_ID)).where(a.USER_ID.eq(userId)).fetchAny().into(UserInfo.class);
	}


	/**
	 * 账号设置
	 * @param param
	 * @param user
	 * @return
	 */
	public JsonResultCode accountSetting( UserAccountSetParam param,WxAppSessionUser user) {
		UserInfo userInfo = getUserInfo(user.getUserId());
		UserDetailRecord userDetailRecord=db().newRecord(USER_DETAIL);
		userDetailRecord.setUserId(userInfo.getUserId());
		userDetailRecord.setBirthdayYear(param.getBirthdayYear());
		userDetailRecord.setBirthdayMonth(param.getBirthdayMonth());
		userDetailRecord.setBirthdayDay(param.getBirthdayDay());
		userDetailRecord.setRealName(param.getRealName());
		userDetailRecord.setSex(param.getSex());
		if (param.getIsSetting() == 1 || param.getIsSetting() == 2) {
			String provinceName = param.getProvinceCode();
			String cityName = param.getCityCode();
			String districtName = param.getDistrictCode();
			if (StringUtils.isNotEmpty(provinceName) && StringUtils.isNotEmpty(cityName) && StringUtils.isNotEmpty(districtName)) {
				Integer provinceId=100000;
				//省
				DictProvinceRecord provinceRecord = saas.region.province.getProvinceName(provinceName);
				if (provinceRecord != null) {
					provinceId = provinceRecord.getProvinceId();
					if (provinceId < 100000) {
						DictProvinceRecord provinceName2 = saas.region.province.getProvinceName(provinceName.substring(0, 2));
						if (provinceName2 != null) {
							provinceId = provinceName2.getProvinceId();
							provinceId = provinceId < 100000 ? 100000 : provinceId;
						}
					}
				}
				//市
				Integer cityId=110000;
				DictCityRecord cityRecord = saas.region.city.getCityId(cityName, provinceId);
				if(cityRecord!=null) {
					cityId=cityRecord.getCityId();
				}else {
					  //没有市时新加
					cityId = saas.region.city.addNewCity(provinceId, cityName);
				}

				//地区
				Integer districtId=110100;
				DictDistrictRecord districtRecord = saas.region.district.getDistrictName(districtName, cityId);
				if(districtRecord!=null) {
					districtId=districtRecord.getDistrictId();
				}else {
					districtId = saas.region.district.addNewDistrict(cityId, districtName);
				}
				userDetailRecord.setProvinceCode(provinceId);
				userDetailRecord.setCityCode(cityId);
				userDetailRecord.setDistrictCode(districtId);
			}

		}
		if(userInfo!=null) {
			if (param.getIsSetting() == 1) {
				userDetail.updateRow(userDetailRecord);
				//syncMainUserDetail(userDetailRecord);
				return JsonResultCode.CODE_SUCCESS;
			}
			if (param.getIsSetting() == 2) {
				//激活卡标志
				userDetail.updateRow(userDetailRecord);
				//syncMainUserDetail(userDetailRecord);
				UserCardRecord newRecord = USER_CARD.newRecord();
				newRecord.setActivationTime(DateUtil.getSqlTimestamp());
				int ret = userCard.updateUserCardByNo(param.getCardNo(),newRecord);
				if(ret>0) {
					//return $this->response(0, '', '激活成功');
					return JsonResultCode.CODE_CARD_ACTIVATE_SUCCESS;
				}else {
					// return $this->response(1, '', '激活失败 ');
					return JsonResultCode.CODE_CARD_ACTIVATE_FAIL;
				}
			}
		}
		return JsonResultCode.CODE_FAIL;

	}

	/**
	 * 查询用户详细信息，原来在accountSetting中
	 * @param userId
	 * @return
	 */
	public UserAccountSetVo accountSetting(Integer userId,Byte isSetting)  {
		if(isSetting==1||isSetting==2) {
			return null;
		}
		UserInfo userInfo = getUserInfo(userId);
		UserAccountSetVo vo=null;
		if(userInfo!=null) {
			vo=new UserAccountSetVo();
			Integer provinceId = userInfo.getProvinceCode() != null ? userInfo.getProvinceCode() : 100000;
			Integer cityId = userInfo.getCityCode() != null ? userInfo.getCityCode() : 110000;
			Integer districtId = userInfo.getDistrictCode() != null ? userInfo.getDistrictCode() : 110100;

			vo.setUserInfo(userInfo);
			DictProvinceRecord provinceName = saas.region.province.getProvinceName(provinceId);
			DictCityRecord cityName = saas.region.city.getCityName(cityId);
			DictDistrictRecord districtName = saas.region.district.getDistrictName(districtId);
			vo.setProvinceCode(provinceName!=null?provinceName.getName():null);
			vo.setCityCode(cityName!=null?cityName.getName():null);
			vo.setDistrictCode(districtName!=null?districtName.getName():null);
		}
		return vo;
	}

	/**
	 * 店铺库的user同步到主库
	 * @param shopRecord
	 */
	public void syncMainUser(UserRecord shopRecord) {
		saas().wxUserService.syncMainUser(getShopId(),shopRecord.getUserId(),shopRecord);
	}


	/**
	 * 店铺库的userdetail同步到主库
	 * @param shopRecords
	 * @param type
	 */
	public void syncMainUserDetail(UserDetailRecord shopRecord) {
		saas().wxUserService.syncMainUserDetail(getShopId(),shopRecord.getUserId(),shopRecord);
	}

    /**
     * Get source integer.更新字段值
     *
     * @param condition the condition
     * @param fields    the fields
     */
    public void updateFields(Condition condition, Map<Field<?>, ?> fields) {
        db().update(USER).set(fields).where(condition).execute();
    }
    /**
     * 根据用户openId获取用户数据
     * @param openId
     * @return UserRecord
     */
	public UserRecord getUserFromOpenId(String openId) {
		if(StringUtils.isBlank(openId)) {
			return null;
		}
		return db().selectFrom(USER).where(USER.WX_OPENID.eq(openId)).fetchAny();
	}
	
	//解析手机号
	public WxMaPhoneNumberInfo wxDecryptData(WxAppAccountParam param,String tokenPrefix) {
		Integer userId=param.getUserId();
		Integer shopId = this.getShopId();
		WxOpenMaService maService = saas.shop.mp.getMaServiceByShopId(shopId);
		String sessionKey = jedis.get(getSessionKey(shopId, userId));
		logger().info("解析手机号获取sessionKey"+StringUtils.isEmpty(sessionKey));
		WxMaPhoneNumberInfo phoneNoInfo = maService.getUserService().getPhoneNoInfo(sessionKey,param.getEncryptedData(), param.getIv());
		logger().info("获取手机号"+phoneNoInfo);
		if(phoneNoInfo!=null) {
			UserInfo userInfo = getUserInfo(userId);
			if(StringUtils.isEmpty(userInfo.getMobile())) {
				//TODO 短信平台
				 //$this->shop()->serviceRequest->smsPlatform->addMobile($shopInfo->seller_account_action, $shopInfo->seller_account, $objData['data']->purePhoneNumber);
			}
			if(StringUtils.isEmpty(phoneNoInfo.getPhoneNumber())) {
				logger().info("userId："+userId+"没有手机号");
				return null;
			}
			String phoneNumber = phoneNoInfo.getPhoneNumber();
			int execute = db().update(USER).set(USER.MOBILE,phoneNumber).where(USER.USER_ID.eq(userId)).execute();
			logger().info("更新用户userId："+userId+"手机号,结果"+execute);
			String token = tokenPrefix + Util.md5(shopId + "_" +userId);
			String json = jedis.get(token);
			WxAppSessionUser parseJson = Util.parseJson(json, WxAppSessionUser.class);
			if(!phoneNumber.equals(parseJson.getWxUser().getMobile())) {
				logger().info("redis更新用户手机号");
				WxUserInfo wxUser = parseJson.getWxUser();
				wxUser.setMobile(phoneNumber);
				parseJson.setWxUser(wxUser);
				jedis.set(token, Util.toJson(parseJson));
			}
			return phoneNoInfo;
		}
		return null;
		
	}
	
	public UserRecord getUserByUnionId(String wxUnionId) {
		return db().fetchAny(USER, USER.WX_UNION_ID.eq(wxUnionId));
	}
}

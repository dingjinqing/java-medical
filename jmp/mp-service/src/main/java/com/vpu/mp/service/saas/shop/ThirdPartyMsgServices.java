package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.ThirdPartyServices.THIRD_PARTY_SERVICES;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.db.main.tables.records.MpOfficialAccountUserRecord;
import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant.TaskJobEnum;
import com.vpu.mp.service.pojo.saas.shop.ShopChildAccountPojo;
import com.vpu.mp.service.pojo.saas.shop.officeAccount.CanSendVo;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.saas.shop.official.MpOfficialAccountUserService;

/**
 * 概况绑定公众号发送消息功能
 * 
 * @author zhaojianqiang 2020年4月13日下午2:39:06
 */
@Service
public class ThirdPartyMsgServices extends MainBaseService {
	public static final Byte BIND = 1;
	public static final Byte NOBIND = 0;

	@Value(value = "${official.appId}")
	private String bindAppId;

	@Autowired
	private ShopAccountService account;

	@Autowired
	private ShopChildAccountService subAccount;

	@Autowired
	private MpOfficialAccountUserService mpOfficialAccountUserService;

	/**
	 * 发送订单消息
	 * @param order
	 */
	public void thirdPartService(OrderInfoRecord order) {
		CanSendVo canService = isCanService(order.getShopId());
		if (canService.getCanSend()) {
			sendMsg(canService, order);
		}

	}

	private void sendMsg(CanSendVo param, OrderInfoRecord order) {
		ShopAccountRecord accountInfo = param.getAccountInfo();
		List<String> bindOpenId = new ArrayList<String>();
		List<ShopChildAccountPojo> subccountList = param.getSubccountList();
		if (accountInfo != null && accountInfo.getIsBind().equals(BIND)
				&& !bindOpenId.contains(accountInfo.getOfficialOpenId())) {
			bindOpenId.add(accountInfo.getOfficialOpenId());
			logger().info("主账户发送");
			sendSingleMessage(order, accountInfo.getOfficialOpenId());
		}

		for (ShopChildAccountPojo account : subccountList) {
			logger().info("子账户发送");
			sendSingleMessage(order, account.getOfficialOpenId());
		}
	}

	private boolean sendSingleMessage(OrderInfoRecord order, String officialOpenId) {
		MpOfficialAccountUserRecord userAccount = mpOfficialAccountUserService.getUser(bindAppId, officialOpenId);
		if (userAccount == null) {
			return false;
		}
		Integer mpTempleType = RabbitParamConstant.Type.MP_TEMPLE_TYPE;
		List<Integer> userIdList = new ArrayList<Integer>();
		if (StringUtils.isEmpty(userAccount.getUnionid())) {
			logger().info("没有登录过小程序");
			com.vpu.mp.db.shop.tables.records.MpOfficialAccountUserRecord user = saas
					.getShopApp(order.getShopId()).officialAccountUser.getUser(bindAppId, userAccount.getOpenid());
			userIdList.add(user.getRecId());
			mpTempleType = RabbitParamConstant.Type.MP_TEMPLE_TYPE_NO_USER;
		} else {
			logger().info("登录过小程序");
			UserRecord user = saas.getShopApp(order.getShopId()).user.getUserByUnionId(userAccount.getUnionid());
			userIdList.add(user.getUserId());
		}
		String goodsName = saas.getShopApp(order.getShopId()).messageTemplateService.orderPayService
				.getGoodsNameForPay(order.getOrderSn());
		BigDecimal money = order.getScoreDiscount().add(order.getUseAccount()).add(order.getMoneyPaid())
				.add(order.getMemberCardBalance());
		DecimalFormat df1 = new DecimalFormat("0.00");
		String formatMoney = df1.format(money);
		UserRecord userRecord = saas.getShopApp(order.getShopId()).user.getUserByUserId(order.getUserId());
		String userName = userRecord.getUsername();
		String page = "pages/orderinfo/orderinfo?order_sn=" + order.getOrderSn();
		String[][] data = new String[][] { { "店铺新订单成交通知", "#173177" }, { formatMoney, "#173177" },
				{ goodsName, "#173177" }, { order.getOrderSn(), "#173177" }, { userName, "#173177" } };
		RabbitMessageParam sendParam = RabbitMessageParam.builder()
				.mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.NEW_ORDER_REMIND).data(data).build())
				.page(page).shopId(order.getShopId()).userIdList(userIdList).type(mpTempleType).build();
		logger().info("准备发");
		saas.taskJobMainService.dispatchImmediately(sendParam, RabbitMessageParam.class.getName(), order.getShopId(),
				TaskJobEnum.SEND_MESSAGE.getExecutionType());
		return true;
	}

	private CanSendVo isCanService(Integer shopId) {
		CanSendVo vo = new CanSendVo();
		boolean canSend = false;
		ShopRecord shop = db().selectFrom(SHOP).where(SHOP.SHOP_ID.eq(shopId)).fetchAny();
		if (shop == null) {
			return vo;
		}
		ShopAccountRecord shopAccount = account.getAccountInfoForId(shop.getSysId());
		if (shopAccount.getIsBind().equals(BIND)) {
			canSend = true;
		}
		List<ShopChildAccountPojo> subAccountList = subAccount.getAccountByBindThird(shop.getSysId());
		if (subAccountList.size() > 0) {
			canSend = true;
		}
		if (canSend) {
			Integer num = db()
					.select(DSL.count(THIRD_PARTY_SERVICES.ACCOUNT_ID)).from(THIRD_PARTY_SERVICES).where(
							THIRD_PARTY_SERVICES.SHOP_ID.eq(shopId)
									.and(dateFormat(THIRD_PARTY_SERVICES.ADD_TIME, DateUtil.DATE_FORMAT_SIMPLE)
											.eq(DateUtil.dateFormat(DateUtil.DATE_FORMAT_SIMPLE))))
					.fetchAnyInto(Integer.class);
			if (num != null && num >= 5) {
				canSend = false;
			} else {
				canSend = true;
			}
		}
		vo = new CanSendVo(canSend, shopAccount, subAccountList);
		return vo;
	}
}

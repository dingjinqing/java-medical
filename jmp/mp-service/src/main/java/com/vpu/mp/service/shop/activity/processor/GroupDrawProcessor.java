package com.vpu.mp.service.shop.activity.processor;

import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.IS_GROUPER_N;
import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.IS_GROUPER_Y;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.GroupDrawRecord;
import com.vpu.mp.db.shop.tables.records.JoinGroupListRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawInfoParam;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawReturn;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam.Goods;
import com.vpu.mp.service.shop.market.groupdraw.GroupDrawService;

import lombok.extern.slf4j.Slf4j;

/**
 * 拼团抽奖
 * 
 * @author zhaojianqiang
 * @time 上午10:44:55
 */
@Service
@Slf4j
public class GroupDrawProcessor implements CreateOrderProcessor {
	@Autowired
	private GroupDrawService groupDrawService;

	@Override
	public Byte getPriority() {
		return GoodsConstant.ACTIVITY_GROUP_BUY_PRIORITY;
	}

	@Override
	public Byte getActivityType() {
		return BaseConstant.ACTIVITY_TYPE_GROUP_DRAW;
	}

	@Override
	public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
		log.info("拼团抽奖的判断processInitCheckedOrderCreate");
		// 拼团不使用优惠券和会员卡
		param.setMemberCardNo(StringUtils.EMPTY);
		param.setCouponSn(StringUtils.EMPTY);
		// 团长,团id
		Byte isGrouper = param.getGroupId() == null ? IS_GROUPER_Y : IS_GROUPER_N;
		log.debug("拼团订单");
		if (isGrouper.equals(IS_GROUPER_Y)) {
			log.info("是团长");
			param.setIsGrouper(IS_GROUPER_Y);
		} else {
			log.info("不是团长");
			param.setIsGrouper(IS_GROUPER_N);
		}
		List<Goods> goodsList = param.getGoods();
		if (goodsList.size() > 1) {
			// 只能买一个商品
			throw new MpException(JsonResultCode.GROUP_ONLY_ONE, null);
		}
		Goods goods = goodsList.get(0);
		GroupDrawReturn result = groupDrawService.canCreateGroupOrder(param.getWxUserInfo().getUserId(),
				param.getActivityId(), goods.getGoodsId(), param.getGroupId(), true);
		JsonResultCode code = result.getCode();
		log.info("拼团抽奖的processInitCheckedOrderCreate判断的code" + code);
		if (!code.equals(JsonResultCode.CODE_SUCCESS)) {
			throw new MpException(code, null);
		}
		GroupDrawRecord groupDraw = groupDrawService.getById(param.getActivityId());
		BigDecimal payMoney = groupDraw.getPayMoney();
		log.info("价格改为"+payMoney);
		goods.setProductPrice(payMoney);
	}

	@Override
	public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
		// 拼团抽奖的判断
		log.info("拼团抽奖的判断processSaveOrderInfo");
		String orderSn = order.getOrderSn();
		JoinGroupListRecord groupInfo = groupDrawService.getGroupInfoByOrderSn(orderSn);
		GroupDrawReturn result = groupDrawService.canCreateGroupOrder(groupInfo.getUserId(), groupInfo.getGroupDrawId(),
				groupInfo.getGoodsId(), groupInfo.getGroupId(), false);
		JsonResultCode code = result.getCode();
		log.info("拼团抽奖的判断的code" + code);
		if (!code.equals(JsonResultCode.CODE_SUCCESS)) {
			throw new MpException(code, null);
		}
		groupDrawService.generateGroupRecord(order, order.getActivityId(), (byte) -1);
	}

	@Override
	public void processOrderEffective(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
		groupDrawService.updateGroupInfoByOrderSn(order.getOrderSn(), (byte) 0);
	}

}

package com.vpu.mp.service.shop.activity.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.JoinGroupListRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.groupDraw.GroupDrawReturn;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
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

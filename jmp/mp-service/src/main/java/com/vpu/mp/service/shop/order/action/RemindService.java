package com.vpu.mp.service.shop.order.action;

import java.util.Arrays;

import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.wxapp.order.OrderInfoMpVo;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.info.OrderInfoService;

/**
 * 	提醒发货
 * @author 王帅
 *
 */

@Component
public class RemindService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam, OrderOperateQueryParam>{
	
	@Autowired
	private OrderInfoService orderInfo;
	
	@Autowired
	public RecordAdminActionService record;
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.REMIND;
	}

	@Override
	public Object query(OrderOperateQueryParam param) throws MpException {
		return null;
	}

	@Override
	public ExecuteResult execute(OrderOperateQueryParam param) {
		OrderInfoMpVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoMpVo.class);
		if(order == null) {
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST);
		}
		if(!OrderOperationJudgment.isShowRemindShip(order)) {
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_REMIND_OPERATION_NOT_SUPPORTED);
		}
		if(order.getOrderRemind() == 3) {
			//限制三次
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_REMIND_OPERATION_LIMIT);
		}
		if(order.getOrderRemindTime() != null && DateUtil.TimestampIsNowDay(order.getOrderRemindTime())) {
			//限制一天一次
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_REMIND_OPERATION_LIMIT_TODAY);
		}
		//提醒
		orderInfo.remind(order);
		//TODO 发送通知
		//操作记录
		record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_REMIND.code }), new String[] {param.getOrderSn()});
		return null;
	}

}

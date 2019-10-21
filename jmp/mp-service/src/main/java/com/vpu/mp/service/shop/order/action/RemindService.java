package com.vpu.mp.service.shop.order.action;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.mp.order.OrderListMpVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
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
public class RemindService extends ShopBaseService implements IorderOperate{
	
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
	public JsonResultCode execute(Object obj) {
		if(!(obj instanceof OrderOperateQueryParam)) {
			return JsonResultCode.CODE_ORDER_OPERATE_NO_INSTANCEOF;
		}
		OrderOperateQueryParam param = (OrderOperateQueryParam)obj;
		OrderListMpVo order = orderInfo.getByOrderId(param.getOrderId(), OrderListMpVo.class);
		if(order == null) {
			return JsonResultCode.CODE_ORDER_NOT_EXIST;
		}
		if(!OrderOperationJudgment.isShowRemindShip(order)) {
			return JsonResultCode.CODE_ORDER_REMIND_OPERATION_NOT_SUPPORTED;
		}
		if(order.getOrderRemind() == 3) {
			//限制三次
			return JsonResultCode.CODE_ORDER_REMIND_OPERATION_LIMIT;
		}
		if(DateUtil.TimestampIsNowDay(order.getOrderRemindTime())) {
			//限制一天一次
			return JsonResultCode.CODE_ORDER_REMIND_OPERATION_LIMIT_TODAY;
		}
		//提醒
		orderInfo.remind(order);
		//TODO 发送通知
		//操作记录
		record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_REMIND.code }), new String[] {param.getOrderSn()});
		return null;
	}

}

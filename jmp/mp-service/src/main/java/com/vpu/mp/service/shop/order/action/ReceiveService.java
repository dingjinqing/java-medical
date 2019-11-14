package com.vpu.mp.service.shop.order.action;

import java.util.Arrays;

import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.record.OrderActionService;
import com.vpu.mp.service.shop.order.ship.ShipInfoService;

/**
 * 	收货
 * @author 王帅
 *
 */

@Component
public class ReceiveService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam, OrderOperateQueryParam>{
	
	@Autowired
	private OrderInfoService orderInfo;
	
	@Autowired
	private ShipInfoService ship;
	
	@Autowired
	public RecordAdminActionService record;
	
	@Autowired
	private OrderActionService orderAction;
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.RECEIVE;
	}

	@Override
	public Object query(OrderOperateQueryParam param) throws MpException {
		return null;
	}

	/**
	 * 	订单收货目前支持已发货状态下商品全部收货（不支持部分收货）
	 */
	@Override
	public ExecuteResult execute(OrderOperateQueryParam param) {
		OrderInfoVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoVo.class);
		if(order == null) {
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST);
		}
		if(!OrderOperationJudgment.isReceive(order)) {
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_RECEIVE_OPERATION_NOT_SUPPORTED);
		}
		
		transaction(()->{
			ship.receive(order.getOrderSn());
			orderInfo.setOrderstatus(order.getOrderSn(), OrderConstant.ORDER_RECEIVED);
		});
		//TODO 发送通知
		//订单状态记录
		orderAction.addRecord(order, param, order.getOrderStatus() , "订单收货");
		//操作记录
		record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_RECEIVE.code }), new String[] {param.getOrderSn()});
		return null;
	}

}

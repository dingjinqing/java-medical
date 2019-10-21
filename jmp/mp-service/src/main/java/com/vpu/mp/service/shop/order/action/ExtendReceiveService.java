package com.vpu.mp.service.shop.order.action;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.mp.order.OrderInfoMpVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.order.OrderReadService;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.ship.ShipInfoService;

/**
 * 	延长收货时间
 * @author 王帅
 *
 */

@Component
public class ExtendReceiveService extends ShopBaseService implements IorderOperate{
	
	@Autowired
	private OrderInfoService orderInfo;
	
	@Autowired
	private ShipInfoService ship;
	
	@Autowired
	public RecordAdminActionService record;
	
	@Autowired
	private OrderReadService orderRead;
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.EXTEND_RECEIVE;
	}

	@Override
	public Object query(OrderOperateQueryParam param) throws MpException {
		return null;
	}
	
	@Override
	public JsonResultCode execute(Object obj) {
		//TODO 延长收货
		if(!(obj instanceof OrderOperateQueryParam)) {
			return JsonResultCode.CODE_ORDER_OPERATE_NO_INSTANCEOF;
		}
		OrderOperateQueryParam param = (OrderOperateQueryParam)obj;
		OrderInfoMpVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoMpVo.class);
		if(order == null) {
			return JsonResultCode.CODE_ORDER_NOT_EXIST;
		}
		if(!OrderOperationJudgment.isExtendReceive(order, orderRead.getExtendReceiveDays() )) {
			//状态或者配置不满足
			return JsonResultCode.CODE_ORDER_RECEIVE_OPERATION_NOT_SUPPORTED;
		}
		if(order.getExtendReceiveAction() != 3 && order.getExtendReceiveTime() != null) {
			//已延长收货，请勿重复操作
			return JsonResultCode.CODE_ORDER_RECEIVE_OPERATION_NOT_SUPPORTED;
		}
		transaction(()->{
			ship.receive(order.getOrderSn());
			orderInfo.setOrderstatus(order.getOrderSn(), OrderConstant.ORDER_RECEIVED);
		});
		//TODO 发送通知
		//操作记录
		record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_EXTEND_RECEIVE.code }), new String[] {param.getOrderSn()});
		return null;
	}

}

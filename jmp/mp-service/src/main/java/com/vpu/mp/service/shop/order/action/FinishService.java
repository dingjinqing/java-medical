package com.vpu.mp.service.shop.order.action;

import java.util.Arrays;
import java.util.Map;

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
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.record.OrderActionService;
import com.vpu.mp.service.shop.order.refund.ReturnOrderService;

/**
 * @author 王帅 
 */
@Component
public class FinishService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam,OrderOperateQueryParam> {
	@Autowired
	private OrderInfoService orderInfo;
	
	@Autowired
	public OrderGoodsService orderGoods;
	
	@Autowired
	OrderActionService orderAction;
	
	@Autowired
	public RecordAdminActionService record;
	
	@Autowired
	public ReturnOrderService returnOrder;
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.FINISH;
	}

	@Override
	public Object query(OrderOperateQueryParam param) throws MpException {
		return null;
	}

	@Override
	public ExecuteResult execute(OrderOperateQueryParam param) {

		OrderInfoVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoVo.class);
		
		//查询订单订单是否存在退款中订单
		Map<Integer, Integer> returningCount = returnOrder.getOrderCount(new Integer[] {order.getOrderId()}, OrderConstant.REFUND_STATUS_AUDITING , OrderConstant.REFUND_STATUS_AUDIT_PASS , OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING);
		
		if (!OrderOperationJudgment.mpIsFinish(order , returningCount.get(order.getOrderId()))) {
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_FINISH_OPERATION_NOT_SUPPORTED);
		}

		
		transaction(()->{
			//TODO 分销订单添加返利记录
			
			//TODO 返利金额
			
			orderInfo.setOrderstatus(order.getOrderSn(), OrderConstant.ORDER_FINISHED);
			
			//TODO else
		});
		//action操作
		orderAction.addRecord(order, param, OrderConstant.ORDER_RECEIVED, "完成订单");
		//TODO 操作记录 b2c_record_admin_action  需要测试记录
		record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_FINISH.code }), new String[] {param.getOrderSn()});
		return null;
	}

}

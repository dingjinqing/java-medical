package com.vpu.mp.service.shop.order.action;

import java.util.Arrays;

import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import org.springframework.beans.factory.annotation.Autowired;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.wxapp.order.OrderListMpVo;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.springframework.stereotype.Component;

/**
 * 删除订单
 * @author 王帅
 *
 */
@Component
public class DeleteService  extends ShopBaseService implements IorderOperate <OrderOperateQueryParam,OrderOperateQueryParam>{

	@Autowired
	private OrderInfoService orderInfo;
	
	@Autowired
	public RecordAdminActionService record;
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.DELETE;
	}

	@Override
	public Object query(OrderOperateQueryParam param) {
		return null;
	}

	/**
	 * 	订单收货目前支持已发货状态下商品全部收货（不支持部分收货）
	 */
	@Override
	public ExecuteResult execute(OrderOperateQueryParam param) {
        logger().info("订单删除start");
		OrderListMpVo order = orderInfo.getByOrderId(param.getOrderId(), OrderListMpVo.class);
		if(order == null) {
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST);
		}
		if(!OrderOperationJudgment.isDelete(order)) {
			return ExecuteResult.create(JsonResultCode.CODE_ORDER_DELETE_OPERATION_NOT_SUPPORTED);
		}		
		orderInfo.delete(order);
		//操作记录
		record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.ORDER_DELETE.code }), new String[] {param.getOrderSn()});
        logger().info("订单删除end");
        return null;
	}
}

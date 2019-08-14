package com.vpu.mp.service.shop.order.action;

import org.springframework.stereotype.Component;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.money.RefundMoneyParam;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;

@Component 
public class ReturnService extends ShopBaseService implements IorderOperate {
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.MP_REFUND_MONEY_APPLY;
	}

	@Override
	public JsonResultCode execute(Object obj) {
		if(obj instanceof RefundMoneyParam) {
			return JsonResultCode.CODE_ORDER_OPERATE_NO_INSTANCEOF;
		}
		RefundMoneyParam param = (RefundMoneyParam)obj;	
		/**
		 * 校验_状态、数量、
		 */
		transaction(()->{
			//insert b2c_order_action
			
			//insert b2c_return_order
			
			//insert b2c_return_order_goods
			
			//insert b2c_return_status_change
			
			//update b2c_order_info
			
			//update b2c_order_goods
			
		});
		return null;
	}

	@Override
	public Object query(OrderOperateQueryParam param) {
		// TODO Auto-generated method stub
		return null;
	}

}

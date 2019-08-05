package com.vpu.mp.service.shop.order.action;

import org.springframework.stereotype.Component;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;

@Component
public class ShipService extends ShopBaseService implements IorderOperate {
	
	@Override
	public OrderServiceCode getServiceCode() {
		return OrderServiceCode.ADMIN_SHIP;
	}

	@Override
	public JsonResultCode execute(Object obj) {
		// TODO Auto-generated method stub
		System.out.println("i'am ShipService");
		return null;
	}

}

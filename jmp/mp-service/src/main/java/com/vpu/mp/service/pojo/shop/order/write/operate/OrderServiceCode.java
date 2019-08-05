package com.vpu.mp.service.pojo.shop.order.write.operate;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.order.action.RefundMoneyService;
import com.vpu.mp.service.shop.order.action.ShipService;

/**
 * 订单状态操作标识service枚举类
 * @author 王帅
 *
 */
public enum OrderServiceCode {
	//admin后台发货ShipService
	ADMIN_SHIP(ShipService.class),
	//小程序端发起退款请求 ReturnMoneyApple
	MP_REFUND_MONEY_APPLY(RefundMoneyService.class);
	//TODO 小程序端发起退货请求
	//MP_REFUND_GOODS_APPLY;
	
	@SuppressWarnings("unused")
	private Class<? extends ShopBaseService> clz;
	private OrderServiceCode(Class<? extends ShopBaseService> clz){
		this.clz = clz;
	}
}

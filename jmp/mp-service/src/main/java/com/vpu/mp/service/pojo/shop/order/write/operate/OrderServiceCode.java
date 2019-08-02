package com.vpu.mp.service.pojo.shop.order.write.operate;
/**
 * 订单状态操作标识service枚举类
 * @author 王帅
 *
 */
public enum OrderServiceCode {
	//admin后台发货ShipService
	ADMIN_SHIP,
	//小程序端发起退款请求 ReturnMoneyApple
	MP_REFUND_MONEY_APPLY,
	//小程序端发起退货请求
	MP_REFUND_GOODS_APPLY,
}

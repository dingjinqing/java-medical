package com.vpu.mp.service.pojo.shop.order.write.operate;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.order.action.CancelService;
import com.vpu.mp.service.shop.order.action.CloseService;
import com.vpu.mp.service.shop.order.action.ReturnService;
import com.vpu.mp.service.shop.order.action.ShipService;

/**
 * 订单状态操作标识service枚举类;此类中的clz只是为了方便查找业务的对于service；
 * 具体关联是IOrderBase实现类与其getServiceCode里的OrderServiceCode对应
 * @author 王帅
 *
 */
public enum OrderServiceCode {
	//0:admin后台发货ShipService
	ADMIN_SHIP(ShipService.class),
	//1:退款 退货 ReturnMoneyApple
	RETURN(ReturnService.class),
	//2:取消
	CANCEL(CancelService.class),
	//3:关闭
	CLOSE(CloseService.class);
	private OrderServiceCode(Class<? extends ShopBaseService> clz){}
}

package com.vpu.mp.service.pojo.shop.order.write.operate.refund.money;

import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.shop.order.action.base.IOrderBase;

import lombok.Getter;
import lombok.Setter;
/**
 * 仅退款
 * @author 王帅
 *
 */
@Getter
@Setter
public final class RefundMoneyParam implements IOrderBase{
	
	@Override
	public OrderServiceCode getServiceCode() {
		
		return OrderServiceCode.ADMIN_SHIP;
	}
	private String orderSn;
	private Byte type;
	

}

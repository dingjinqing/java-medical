package com.vpu.mp.service.pojo.shop.order.write.operate.ship;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;

import lombok.Getter;
import lombok.Setter;

/**
 * 发货
 * 
 * @author 王帅
 *
 */
@Getter
@Setter
public final class ShipParam extends OrderOperateQueryParam{

	/**
	 * TODO 
	 */
	@NotBlank(message = JsonResultMessage.MSG_ORDER_SHIPPING_SHIPPINGNO_NOT_NULL)
	private String shippingNo;
	@NotNull(message = JsonResultMessage.MSG_ORDER_SHIPPING_SHIPPINGID_NOT_NULL)
	private Byte shippingId;
	@NotNull(message = JsonResultMessage.MSG_ORDER_SHIPPING_SHIPGOODS_NOT_NULL)
	private ShipGoods[] shipGoods;
	
	@Getter
	@Setter
	public static class ShipGoods {
		/** order_goods主键 */
		private Integer recId;
		private Integer sendNumber;
	}
}

package com.vpu.mp.service.pojo.shop.order.write.ship;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 发货
 * 
 * @author 王帅
 *
 */
@Data
public class ShipParam {
	/**0查询可发货;1发货*/
	public final static byte TYPE_QUERY= 0;
	public final static byte TYPE_UPDATE= 1;
	
	/**
	 * 0查询可发货商品,1(部分)发货
	 */
	private Byte type;
	private String orderSn;
	private String shippingNo;
	private Byte shippingId;
	@Getter
	@Setter
	public class ShipGoods{
		/**order_goods主键*/
		private Integer recId;
		private Integer sendNumber;
	}
	private ShipGoods[] shipGoods;
	/**
	 * 是否部分发货
	 */
	private Byte partShipFlag;
	
	
}

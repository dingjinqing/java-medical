package com.vpu.mp.service.pojo.shop.order.write.ship;

import java.util.List;

import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;

import lombok.Data;

/**
 * 发货
 * 
 * @author 王帅
 *
 */
@Data
public class ShipVo {
	/**收件人姓名*/
	private String consignee;
	/**下单人手机号*/
	private String userMobile;
	/**完整收货地址*/
	private String completeAddress;
	private List<OrderGoodsVo> OrderGoodsVo;
}

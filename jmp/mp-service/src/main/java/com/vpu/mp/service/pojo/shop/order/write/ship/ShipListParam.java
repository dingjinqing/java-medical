package com.vpu.mp.service.pojo.shop.order.write.ship;

import javax.validation.constraints.NotBlank;

import com.vpu.mp.service.foundation.data.JsonResultMessage;

import lombok.Data;

/**
 *	 可发货商品查询
 * 
 * @author 王帅
 *
 */
@Data
public class ShipListParam {
	//TODO
	@NotBlank(message = JsonResultMessage.MSG_ORDER)
	private String orderSn;
}

package com.vpu.mp.service.pojo.shop.order.write.operate;

import javax.validation.constraints.NotBlank;

import com.vpu.mp.service.foundation.data.JsonResultMessage;

import lombok.Data;

/**
 *	 订单操作前置查询，得到可以操作的商品
 * 
 * @author 王帅
 *
 */
@Data
public class OrderOperateQueryParam {
	//TODO
	@NotBlank(message = JsonResultMessage.MSG_ORDER)
	private String orderSn;
}

package com.vpu.mp.service.pojo.shop.order.write.operate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.data.JsonResultMessage;

import lombok.Getter;
import lombok.Setter;

/**
 *	 订单操作前置查询，得到可以操作的商品
 * 
 * @author 王帅
 *
 */
@Getter
@Setter
public class OrderOperateQueryParam extends AbstractOrderOperateQueryParam{
	/**
	 * TODO 
	 */
	@NotNull(message = JsonResultMessage.MSG_ORDER_ORDER_ID_NOT_NULL)
	private Integer orderId;
	@NotBlank(message = JsonResultMessage.MSG_ORDER_ORDER_SN_NOT_NULL)
	private String orderSn;

}

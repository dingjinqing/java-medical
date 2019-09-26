package com.vpu.mp.service.pojo.shop.order.refund;

import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.order.OrderParam;

import lombok.Getter;
import lombok.Setter;

/**
 * 退款订单详情
 * @author wangshuai
 */
@Getter
@Setter
public class ReturnOrderParam extends OrderParam{
	@NotNull(message = JsonResultMessage.MSG_ORDER_RETID_NOT_NULL)
	private Integer retId;
}

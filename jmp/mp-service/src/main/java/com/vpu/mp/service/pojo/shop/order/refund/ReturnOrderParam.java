package com.vpu.mp.service.pojo.shop.order.refund;

import javax.validation.constraints.NotBlank;

import com.vpu.mp.common.foundation.data.JsonResultMessage;

import lombok.Getter;
import lombok.Setter;

/**
 * 退款订单详情
 * @author wangshuai
 */
@Getter
@Setter
public class ReturnOrderParam {
	@NotBlank(message = JsonResultMessage.MSG_ORDER_RETURN_ORDER_SN_NOT_NULL)
	private String returnOrderSn;
}

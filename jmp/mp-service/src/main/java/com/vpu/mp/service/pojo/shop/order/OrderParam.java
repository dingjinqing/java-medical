package com.vpu.mp.service.pojo.shop.order;

import javax.validation.constraints.NotBlank;

import com.vpu.mp.service.foundation.data.JsonResultMessage;

import lombok.Data;

/**
 * 
 * @author wangshuai
 */

@Data
public class OrderParam {
	@NotBlank(message = JsonResultMessage.MSG_ORDER_ORDER_SN_NOT_NULL)
	private String orderSn;
}

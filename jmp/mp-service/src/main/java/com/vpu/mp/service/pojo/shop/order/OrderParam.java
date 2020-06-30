package com.vpu.mp.service.pojo.shop.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

import com.vpu.mp.common.foundation.data.JsonResultMessage;

/**
 * 
 * @author wangshuai
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderParam {
	@NotBlank(message = JsonResultMessage.MSG_ORDER_ORDER_SN_NOT_NULL)
	private String orderSn;
}

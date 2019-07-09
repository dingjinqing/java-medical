package com.vpu.mp.service.pojo.shop.config;

import com.vpu.mp.config.JacksonConfig;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.JsonResultMessage;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author 新国
 * @description 店铺风格样例 {"shopStyleId":"5","shopStyleValue":"#feb609,#333333"} 
 */
@Data
public class ShopStyleConfig {
	
	/**
	 * 店铺风格ID
	 */
	@NotNull(message = JsonResultMessage.DECORATE_STYLE_PARAM_ID_NULL)
	public Integer shopStyleId;
	
	/**
	 * 店铺风格值
	 */
	@NotBlank(message = JsonResultMessage.DECORATE_STYLE_PARAM_VALUE_NULL)
	public String shopStyleValue;
}

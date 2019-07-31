package com.vpu.mp.service.pojo.shop.goods.deliver;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author liangchen
 * @date 2019年7月12日
 */
@Data
public class GoodsDeliverTemplateLimitParam {
	@JsonProperty(value = "limit_deliver_area")
	private Integer 	limitDeliverArea;
	@JsonProperty(value = "area_list")
	private String 		areaList;
	@JsonProperty(value = "area_text")
	private String 		areaText;
	@JsonProperty(value = "first_num")
	private Integer 	firstNum;
	@JsonProperty(value = "first_fee")
	private BigDecimal 	firstFee;
	@JsonProperty(value = "continue_num")
	private Integer 	continueNum;
	@JsonProperty(value = "continue_fee")
	private BigDecimal 	continueFee;
	
}

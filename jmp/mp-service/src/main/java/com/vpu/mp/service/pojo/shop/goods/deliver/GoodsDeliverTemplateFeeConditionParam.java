package com.vpu.mp.service.pojo.shop.goods.deliver;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * @author liangchen
 * @date 2019年7月12日
 */

@Data
public class GoodsDeliverTemplateFeeConditionParam {
	@JsonProperty(value = "area_list")
	private String areaList;
	@JsonProperty(value = "area_text")
	private String areaText;
	@JsonProperty(value = "fee_0_condition")
	private Integer fee0Condition;
	@JsonProperty(value = "fee_0_con1_num")
	private Integer fee0Con1Num;
	@JsonProperty(value = "fee_0_con2_num")
	private Integer fee0Con2Fee;
	@JsonProperty(value = "fee_0_con3_num")
	private Integer fee0Con3Num;
	@JsonProperty(value = "fee_0_con3_fee")
	private Integer fee0Con3Fee;
}

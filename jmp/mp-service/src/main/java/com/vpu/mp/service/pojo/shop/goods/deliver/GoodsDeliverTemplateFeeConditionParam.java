package com.vpu.mp.service.pojo.shop.goods.deliver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
/**
 * @author liangchen
 * @date 2019年7月12日
 */

@Data
public class GoodsDeliverTemplateFeeConditionParam {
    /** 地区编号 */
	@JsonProperty(value = "area_list")
	private String areaList;
    /** 地区名称 */
	@JsonProperty(value = "area_text")
	private String areaText;
	/** 包邮类型（1:件数,2:金额,3:件数+金额） */
	@JsonProperty(value = "fee_0_condition")
	private Integer fee0Condition;
	/** 类型1，满N件包邮 */
	@JsonProperty(value = "fee_0_con1_num")
	private Integer fee0Con1Num;
	/** 类型2，满N元包邮 */
	@JsonProperty(value = "fee_0_con2_num")
	private Integer fee0Con2Fee;
	/** 类型3，满N件包邮 */
	@JsonProperty(value = "fee_0_con3_num")
	private Integer fee0Con3Num;
    /** 类型3，满N元包邮 */
	@JsonProperty(value = "fee_0_con3_fee")
	private Integer fee0Con3Fee;
}

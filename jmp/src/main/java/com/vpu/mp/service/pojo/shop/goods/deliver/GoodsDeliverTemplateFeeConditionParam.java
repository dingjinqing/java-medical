package com.vpu.mp.service.pojo.shop.goods.deliver;

import lombok.Data;
/**
 * @author liangchen
 * @date 2019年7月12日
 */

@Data
public class GoodsDeliverTemplateFeeConditionParam {
	
	private String areaList;
	private String areaText;
	private Integer fee0Condition;
	private Integer fee0Con1Num;
	private Integer fee0Con2Fee;
	private Integer fee0Con3Num;
	private Integer fee0Con3Fee;
}

package com.vpu.mp.service.pojo.shop.goods.deliver;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
/**
 * @author liangchen
 * @date 2019年7月12日
 */

@Data
public class GoodsDeliverTemplateParam {
	
	private Integer deliverTemplateId;
	private String templateName;
	private Byte flag;
	GoodsDeliverTemplateLimitParam goodsDeliverTemplateLimitParam;
	ArrayList<GoodsDeliverTemplateAreaParam> goodsDeliverTemplateAreaParam;
 	GoodsDeliverTemplateFeeParam goodsDeliverTemplateFeeParam;
	List<GoodsDeliverTemplateFeeConditionParam> goodsDeliverTemplateFeeConditionParam;

}

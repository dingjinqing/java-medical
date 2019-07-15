package com.vpu.mp.service.pojo.shop.goods.deliver;
import java.util.ArrayList;

import lombok.Data;


@Data
public class GoodsDeliverTemplateParam {
	
	private String templateName;
	private Byte flag;
	GoodsDeliverTemplateLimitParam goodsDeliverTemplateLimitParam;
	ArrayList<GoodsDeliverTemplateAreaParam> goodsDeliverTemplateAreaParam;
 	
}

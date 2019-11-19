package com.vpu.mp.service.pojo.shop.goods.deliver;

import lombok.Data;
/**
 * @author liangchen
 * @date 2019年7月12日
 */
@Data
public class GoodsDeliverTemplateVo {
	
	private Integer deliverTemplateId;
	private String templateName;
	private String templateContent;
	private Byte flag;
	
}

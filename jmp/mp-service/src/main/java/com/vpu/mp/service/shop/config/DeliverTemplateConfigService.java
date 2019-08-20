package com.vpu.mp.service.shop.config;


import org.springframework.stereotype.Service;

import com.vpu.mp.service.pojo.shop.config.DeliverTemplateConfig;

@Service

public class DeliverTemplateConfigService extends BaseShopConfigService{
	
	final public static String K_DELIVER_TEMPLATE = "deliverTemplate";
	
	 /**
     * 获取默认运费模板配置
     *
     * @param jsonString
     * @return 
     */
	
	public String getDefaultDeliverTemplate() {
		return this.get(K_DELIVER_TEMPLATE);
	}
	
	 /**
     * 修改默认运费模板配置
     *
     * @param jsonString
     * @return 
     */
	
	public int setDefaultDeliverTemplate(DeliverTemplateConfig param) {
		return this.setJsonObject(K_DELIVER_TEMPLATE, param);
	}
	
	
}

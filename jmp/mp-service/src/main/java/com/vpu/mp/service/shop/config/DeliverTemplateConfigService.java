package com.vpu.mp.service.shop.config;


import org.springframework.stereotype.Service;

import com.vpu.mp.service.pojo.shop.config.DeliverTemplateConfig;

@Service

public class DeliverTemplateConfigService extends BaseShopConfigService{
	
	final public static String K_DELIVER_TEMPLATE = "deliverTemplate";
	
	
	/**
	 * 传入默认运费模板信息
	 *
	 * @param param
	 * @return jsonString
	 */
	public String getJsonString(DeliverTemplateConfig param) {
		
		String defaultDeliverTemplate = param.toString();
		return defaultDeliverTemplate;
	}
	
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
	
	public int setDefaultDeliverTemplate(String value) {
		return this.set(K_DELIVER_TEMPLATE,value);
	}
	
	
}

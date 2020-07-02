package com.vpu.jmd.service.config;


import com.vpu.jmd.service.config.bo.DeliverTemplateConfig;
import org.springframework.stereotype.Service;

@Service

public class DeliverTemplateConfigService extends BaseShopConfigService{

	final public static String K_DELIVER_TEMPLATE = "deliver_template";

	 /**
     * 获取默认运费模板配置
     *
     * @param
     * @return
     */

	public String getDefaultDeliverTemplate() {
		return this.get(K_DELIVER_TEMPLATE);
	}

	 /**
     * 修改默认运费模板配置
     *
     * @param
     * @return
     */

	public int setDefaultDeliverTemplate(DeliverTemplateConfig param) {
		return this.setJsonObject(K_DELIVER_TEMPLATE, param);
	}


}
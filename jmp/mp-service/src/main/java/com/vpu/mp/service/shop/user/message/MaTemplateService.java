package com.vpu.mp.service.shop.user.message;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateConfig;

@Service
public class MaTemplateService extends ShopBaseService {

	
	public void sendTemplateMessage(String openId, Byte templateType, String mpLinkIdentity, String[] keywordVallues,
			MaTemplateConfig templateConfig, String page, String formId) {
		
	}
}

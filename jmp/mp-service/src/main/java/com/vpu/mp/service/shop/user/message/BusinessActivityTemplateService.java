package com.vpu.mp.service.shop.user.message;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.pojo.shop.user.message.MaTemplateConfig;

@Service
public class BusinessActivityTemplateService extends MaTemplateService {
	
	public static final Integer[] BUSINESS_IDS = {1, 2};
	public static final MaTemplateConfig activityConfig= new MaTemplateConfig(
			 "AT0654",
		        "申请进度通知",
		         "业务名称{{keyword1.DATA}}业务状态{{keyword2.DATA}}",
		         BusinessActivityTemplateService.BUSINESS_IDS,
		        1
			);
		
	void sendBusinessActivityMessage(String userId, Byte templateConfigId, String title, String content,
			String page) {
		// TODO:
	}
}

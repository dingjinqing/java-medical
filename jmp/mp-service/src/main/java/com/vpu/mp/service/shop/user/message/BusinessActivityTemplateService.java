package com.vpu.mp.service.shop.user.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.saas.shop.MpAuthShopService;

import cn.binarywang.wx.miniapp.api.WxMaTemplateService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateAddResult;
import me.chanjar.weixin.common.error.WxErrorException;

@Service
public class BusinessActivityTemplateService extends ShopBaseService {
    @Autowired
    private MpAuthShopService mpAuthShopService;
    @Autowired
    private MaTemplateService maTemplateService;
	

		
	public void sendBusinessActivityMessage(WxMaTemplateMessage msg) throws WxErrorException {
        maTemplateService.sendTemplateMessage(msg);
    }

	public String addTemplate(String templateId, List<Integer> keywordIds) throws WxErrorException {
        WxMaTemplateService maTemplateService = mpAuthShopService.getMaServiceByShopId(getShopId()).getTemplateService();
        WxMaTemplateAddResult result = maTemplateService.addTemplate(templateId, keywordIds);
        return result.getTemplateId();

    }
}

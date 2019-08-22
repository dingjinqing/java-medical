package com.vpu.mp.service.shop.user.message;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaTemplateService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.vpu.mp.service.saas.shop.MpAuthShopService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateConfig;

@Service
public class MaTemplateService extends ShopBaseService {

    @Autowired
    private MpAuthShopService mpAuthShopService;
	
	public void sendTemplateMessage(WxMaTemplateMessage msg) throws WxErrorException {
        mpAuthShopService.getMaServiceByShopId(getShopId()).getMsgService().sendTemplateMsg(msg);
	}

//	public String getTemplateId(String title,String content){
//	    mpAuthShopService.getMaServiceByShopId(getShopId())
//
//    }
}

package com.vpu.mp.controller.wxapp;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.subscribe.TemplateParam;
import com.vpu.mp.service.pojo.wxapp.subscribe.TemplateVo;
import com.vpu.mp.service.pojo.wxapp.subscribe.UpdateTemplateParam;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.service.shop.user.message.maConfig.SubcribeTemplateCategory;
import com.vpu.mp.service.shop.user.message.maConfig.TypesNames;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 模板消息
 * @author zhaojianqiang
 *
 * 2019年12月5日 下午3:26:22
 */
@RestController
public class WxAppSubscribeMessageController extends WxAppBaseController {

	
	/**
	 * 获取小程序需要的模板Id
	 * 
	 */
	@PostMapping("/api/wxapp/subscribe/getNeedTemplateId")
	public JsonResult getNeedTemplateId(@RequestBody TemplateParam param) {
		logger().info("获取小程序需要的模板Id");
		if(StringUtils.isEmpty(param.getTyps())) {
			return fail();
		}
		String[] data= {};
		switch (param.getTyps()) {
		case TypesNames.AUDIT_UPGRADE:
			data= new String[]{SubcribeTemplateCategory.AUDIT,SubcribeTemplateCategory.USER_GRADE};
			break;
		case TypesNames.ADD_ORDER:
			data= new String[]{SubcribeTemplateCategory.ORDER_DELIVER,SubcribeTemplateCategory.SCORE_CHANGE};
			break;
		case TypesNames.INVITE:
			data= new String[]{SubcribeTemplateCategory.INVITE_SUCCESS};
			break;
		case TypesNames.GROUP_DRAW:
			data= new String[]{SubcribeTemplateCategory.INVITE_SUCCESS,SubcribeTemplateCategory.DRAW_RESULT};
			break;
		case TypesNames.GIVE_GIFT:
			data= new String[]{SubcribeTemplateCategory.DRAW_RESULT};
			break;
		case TypesNames.ORDER_REFUND:
			data= new String[]{SubcribeTemplateCategory.REFUND_RESULT};
			break;
		default:
			logger().info(param.getTyps()+"没有匹配");
			break;
		}
		Integer shopId = wxAppAuth.shopId();
		ShopApplication shopApp = saas.getShopApp(shopId);
		TemplateVo[] templateId= {};
		try {
			templateId = shopApp.subservice.getTemplateId(data);
		} catch (WxErrorException e) {
			logger().error(e.getMessage(),e);
			return fail();
		}
		return success(templateId);
	}
	
	/**
	 * 授权后将信息传输给后端时候调用
	 * @param param
	 * @return
	 */
	@PostMapping("/api/wxapp/subscribe/updateTemplate")
	public JsonResult updateTemplate(@RequestBody UpdateTemplateParam param) {
		Integer shopId = wxAppAuth.shopId();
		ShopApplication shopApp = saas.getShopApp(shopId);
		shopApp.subservice.updateStatusAndNum(wxAppAuth.user().getUserId(),param);
		return success();
		
	}
	
}

package com.vpu.mp.controller.wxapp;

import java.lang.reflect.Field;
import java.util.Objects;

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
		String typs = param.getTyps();
		switch (typs) {
		case TypesNames.AUDIT_UPGRADE:
			data= new String[]{SubcribeTemplateCategory.AUDIT,SubcribeTemplateCategory.USER_GRADE};
			break;
		case TypesNames.ADD_ORDER:
			data= new String[]{SubcribeTemplateCategory.ORDER_DELIVER,SubcribeTemplateCategory.SCORE_CHANGE,SubcribeTemplateCategory.BALANCE_CHANGE};
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
		case TypesNames.WITH_DRAW:
			data= new String[]{SubcribeTemplateCategory.AUDIT};
			break;
		default:
			logger().info(param.getTyps()+"没有匹配");
			break;
		}
		data = getData(data, typs);
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

	private String[] getData(String[] data, String typs) {
		if ((!StringUtils.isEmpty(typs)) && data.length == 0) {
			String[] typesNames= {};
			if (typs.contains(",")) {
				typesNames = typs.split(",");
			}else {
				typesNames=new String[]{typs};
			}
			data = new String[typesNames.length];
			Field[] declaredFields = SubcribeTemplateCategory.class.getDeclaredFields();
			for (int i = 0; i < typesNames.length; i++) {
				for (Field field : declaredFields) {
					Object object = null;
					try {
						object = field.get(field.getName());
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (Objects.equals(typesNames[i], object)) {
						data[i] = typesNames[i];
					}
					
				}
			}
		}
		return data;
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

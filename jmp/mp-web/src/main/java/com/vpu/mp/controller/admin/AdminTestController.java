package com.vpu.mp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.config.ShopStyleConfig;
import com.vpu.mp.service.wechat.OpenPlatform;

/**
 * 
 * @author lixinguo
 *
 */
@RestController
public class AdminTestController extends AdminBaseController {

	@Autowired
	protected OpenPlatform open;
	
	@RequestMapping(value = "/admin/test")
	public JsonResult test() throws Exception {
		ShopStyleConfig config = new ShopStyleConfig();
		config.setShopStyleId(1);
		config.setShopStyleValue(" rgb(51, 51, 51), rgb(254, 182, 9)");
		String[] result = saas.getShopApp(123456).config.convertShopStyle(config);
		return success(result);
	}
}

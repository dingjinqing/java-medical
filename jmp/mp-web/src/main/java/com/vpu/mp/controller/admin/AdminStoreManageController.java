package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthListParam;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthVo;

/**
 * 门店权限管理
 * 
 * @author zhaojianqiang
 * @time 上午10:34:38
 */
@RestController
public class AdminStoreManageController extends AdminBaseController {

	/**
	 * 获取门店权限列表和选中状态
	 * 
	 * @return
	 */
	@PostMapping("/api/admin/store/account/getSetting")
	public JsonResult getSettingInfo() {
		StoreAuthVo getstoreJson = saas.shop.storeManageService.getstoreJson(shopId());
		return success(getstoreJson);

	}

	/**
	 * 设置门店页面权限
	 * 
	 * @return
	 */
	@PostMapping("/api/admin/store/account/setting")
	public JsonResult settingInfo(@RequestBody StoreAuthListParam param) {
		boolean setting = saas.shop.storeManageService.setting(param, shopId());
		return setting ? success() : fail();
	}
	
	@PostMapping("/api//admin/store/account/list")
	public JsonResult getAccount() {
		return null;
		
	}
}

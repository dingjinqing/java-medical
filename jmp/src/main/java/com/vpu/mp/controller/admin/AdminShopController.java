package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.saas.shop.ShopPojo;
import com.vpu.mp.service.pojo.shop.config.ShopBaseConfig;
import com.vpu.mp.service.pojo.shop.config.ShopCommonCfgInfo;




/**
 * @author 王兵兵
 *
 * @date 2019年6月25日
 */
@RestController
public class AdminShopController extends AdminBaseController {

	@GetMapping(value = "/api/admin/config/shop/common/get")
	public JsonResult getShopCommonInfo()   {
		return success(shop().config.shopCommonConfigService.getShopCommonCfg());
	}
	
	@GetMapping(value = "api/admin/config/shop/base/get")
	public JsonResult getShopBaseInfo() {
		ShopBaseConfig shopBaseCfgInfo = new ShopBaseConfig();
		ShopPojo shop = saas.shop.getShopBaseInfoById(this.shopId());
		shopBaseCfgInfo.setExpireTime(saas.shop.renew.getShopRenewExpireTime(this.shopId()));
		shopBaseCfgInfo.setShopName(shop.getShopName());
		shopBaseCfgInfo.setShopAvatar(shop.getShopAvatar());
		shopBaseCfgInfo.setCreated(shop.getCreated());
		shopBaseCfgInfo.setBusinessState(shop.getBusinessState());
		return this.success(shopBaseCfgInfo);
	}
	
	@PostMapping(value = "api/admin/config/shop/base/update")
	public JsonResult updateShopBaseInfo(@RequestBody @Valid ShopPojo shop) {
		shop.setShopId(this.shopId());
		Integer res = saas.shop.updateShopBaseInfo(shop);
		if(res > 0) {
			return this.success();
		}else {
			return this.fail(JsonResultCode.CODE_FAIL);
		}
	}
	
	@PostMapping(value = "api/admin/config/shop/common/update")
	public JsonResult updateShopCommonInfo(@RequestBody @Valid ShopCommonCfgInfo commonCfg) {
		if(shop().config.shopCommonConfigService.updateShopCommonInfo(commonCfg)) {
			return this.success();
		}else {
			return this.fail(JsonResultCode.CODE_FAIL);
		}
	}
}

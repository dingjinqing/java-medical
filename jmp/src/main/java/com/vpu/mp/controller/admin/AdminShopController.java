package com.vpu.mp.controller.admin;

import java.util.List;
import static java.util.Arrays.asList;

import java.util.ArrayList;

import javax.validation.Valid;

import org.jooq.Record1;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.saas.shop.ShopPojo;
import com.vpu.mp.service.pojo.shop.config.ShopBaseCfgInfo;
import com.vpu.mp.service.pojo.shop.config.ShopCfg;
import com.vpu.mp.service.pojo.shop.config.ShopCfgPojo;




/**
 * @author 王兵兵
 *
 * @date 2019年6月25日
 */
@RestController
public class AdminShopController extends AdminBaseController {

	@GetMapping(value = "/api/admin/config/shop/getCommonInfo")
	public JsonResult getShopCommonInfo()   {
		return null;
		//		List<String> keys = asList("show_logo","logo_link","cancel_time","invoice","bind_mobile","sales_number","order_realName","order_cid","consignee_realName","consignee_cid","custom","custom_title","del_market","custom_service","show_cart","service_name","service_choose","service_terms","share_cfg","shop_style");
//		List<ShopCfg> shopCommonInfo = new ArrayList<ShopCfg>();
//		for(String key : keys) {
//			ShopCfg shopCfg = new ShopCfg();
//			shopCfg.setK(key);
//			shopCfg.setV(shop().shopCfg.getShopCfg(key));
//			shopCommonInfo.add(shopCfg);
//		}
//		return this.success(shopCommonInfo);
	}
	
	@GetMapping(value = "api/admin/config/shop/getBaseInfo")
	public JsonResult getShopBaseInfo() {
		ShopBaseCfgInfo shopBaseCfgInfo = new ShopBaseCfgInfo();
		ShopPojo shop = saas.shop.getShopBaseInfoById(this.shopId());
		shopBaseCfgInfo.setExpireTime(saas.shop.renew.getShopRenewExpireTime(this.shopId()));
		shopBaseCfgInfo.setShopName(shop.getShopName());
		shopBaseCfgInfo.setShopAvatar(shop.getShopAvatar());
		shopBaseCfgInfo.setCreated(shop.getCreated());
		shopBaseCfgInfo.setBusinessState(shop.getBusinessState());
		return this.success(shopBaseCfgInfo);
	}
	
	@PostMapping(value = "api/admin/config/shop/updateBaseInfo")
	public JsonResult updateShopBaseInfo(@RequestBody @Valid ShopPojo shop) {
		shop.setShopId(this.shopId());
		Integer res = saas.shop.updateShopBaseInfo(shop);
		if(res > 0) {
			return this.success();
		}else {
			return this.fail(JsonResultCode.CODE_FAIL);
		}
	}
	
	@PostMapping(value = "api/admin/config/shop/updateCommonInfo")
	public JsonResult updateShopCommonInfo(@RequestBody @Valid List<ShopCfgPojo> cfgs) {
		return null;
//		if(shop().shopCfg.updateShopCommonInfo(cfgs)) {
//			return this.success();
//		}else {
//			return this.fail(JsonResultCode.CODE_FAIL);
//		}
	}
}

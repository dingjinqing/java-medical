package com.vpu.mp.controller.admin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.main.tables.pojos.Shop;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.ShopCfgRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.config.ShopCfg;


/**
 * @author 王兵兵
 *
 * @date 2019年6月25日
 */
@RestController
public class AdminShopController extends AdminBaseController {

	@RequestMapping(value = "/api/admin/config/shop/getCommonInfo")
	public JsonResult getShopCommonInfo() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		LinkedHashMap<String,Object> result = new LinkedHashMap<String,Object>();
		
		List<ShopCfgRecord> shopCfgRecordList =  shop().shopCfg.getAllShopCfg();
		ShopCfg shopCfg = new ShopCfg();
		for(ShopCfgRecord shopCfgRecord : shopCfgRecordList) {
			if(Util.getObjectProperty(shopCfg, Util.underlineToHump(shopCfgRecord.getK())) != null) {
				java.lang.reflect.Field f = shopCfg.getClass().getDeclaredField(Util.underlineToHump(shopCfgRecord.getK()));
				f.setAccessible(true);
	            f.set(shopCfg, shopCfgRecord.getV());
			}
		}
		result.put("shopCfg",shopCfg);
		
		ShopRecord shop = saas.shop.getShopById(this.shopId());
		result.put("shareCfg",shop.getShareConfig());
		if(shop.getSmsAccount() != null) {
			//短信账号
			//result.put("smsAccount",saas.shop.serviceRequest.smsPlatform.getBalance(shop.getSmsAccount());
		}else {
			result.put("shareCfg",null);
		}
		result.put("shopStyle",shop.getShopStyle());
		//下单需要填写必填信息的商品
		//result.put("goodsPackage",shop().shopCfg.getGoodsPackage());
		
		MpAuthShopRecord mp = saas.shop.mp.getAuthShopByShopId(this.shopId());
		if(null != mp) {
			               
			result.put("mp",(HashMap<String, ?>) mp.intoMap());
			//二维码
			//result.put("qrcode",(HashMap<String, ?>) shop().image.qrcode.getQrcodeInfo().intoMap());
			//版本
			//result.put("current_version",(HashMap<String, ?>) shop().image.qrcode.getQrcodeInfo.intoMap());
			//审核状态
			//result.put("audit_state_map",(HashMap<String, ?>) mp.getAuditStateMap().intoMap());
			//公众号列表
			//result.put("officialList",(HashMap<String, ?>) shop().officialAccount.getOfficialAccountBySysId(this.auth.user().sys_id).intoMap());
			//公众号账号
			//result.put("officialAccount",(HashMap<String, ?>) shop().officialAccount.getAccount(mp.link_official_app_id).intoMap());
			//是否开启微信购物单功能
			//result.put("enabled_wx_shopping_list",(HashMap<String, ?>) shop().shopCfg.getShopCfg("wx_shopping_list_enbaled").intoMap());
			//是否有微信购物单授权权限
			//result.put("has_wx_shopping_list_authority",(HashMap<String, ?>) shop().user.shoppingList。hasShoppingList.Authority().intoMap());
			//推荐
			//result.put("wx_shopping_recommend",(HashMap<String, ?>) shop().shopCfg.getShopCfg("wx_shopping_recommend").intoMap());
			//是否开启微信购物单功能
			//result.put("enabled_wx_shopping_list",(HashMap<String, ?>) shop().shopCfg.getShopCfg("wx_shopping_list_enbaled").intoMap());
			
			//TODO 商户小程序相关
			
			
		}
		return this.success(result);
	}
	
	@RequestMapping(value = "api/admin/config/shop/getBaseInfo")
	public JsonResult getShopBaseInfo() {
		LinkedHashMap<String,Object> result = new LinkedHashMap<String,Object>();
		Shop shop = saas.shop.getShopBaseInfoById(this.shopId());
		result.put("expireTime",saas.shop.renew.getShopRenewExpireTime(this.shopId()));
		result.put("shopName",shop.getShopName());
		result.put("shopAvatar",shop.getShopAvatar());
		result.put("created",shop.getCreated());
		result.put("businessState",shop.getBusinessState());
		return this.success(result);
	}
	
	@RequestMapping(value = "api/admin/config/shop/updateBaseInfo")
	public JsonResult updateShopBaseInfo(Shop shop) {
		shop.setShopId(this.shopId());
		Integer res = saas.shop.updateShopBaseInfo(shop);
		if(res > 0) {
			return this.success();
		}else {
			return this.fail();
		}
	}
	
	@RequestMapping(value = "api/admin/config/shop/updateCommonInfo")
	public JsonResult updateShopCommonInfo(ShopCfg shopCfg) {
		Integer res = shop().shopCfg.updateShopCommonInfo(shopCfg);
		if(res > 0) {
			return this.success();
		}else {
			return this.fail();
		}
	}
}

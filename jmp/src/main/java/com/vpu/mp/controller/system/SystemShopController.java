package com.vpu.mp.controller.system;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.saas.auth.SystemTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.auth.ShopMobileReq;
import com.vpu.mp.service.pojo.shop.auth.ShopRenewReq;
import com.vpu.mp.service.pojo.shop.auth.ShopReq;

/**
 * 
 * @author 新国
 *
 */
@RestController
@RequestMapping("/api")
public class SystemShopController extends SystemBaseController {
	final String TOKEN = "V-Token";

	/**
	 * 验证手机号是否重复
	 * 
	 * @param mobile
	 * @return
	 */
	@PostMapping("/system/shop/check/mobile")
	public JsonResult checkMobile(@RequestBody ShopMobileReq shopMobileReq) {
		if (StringUtils.isEmpty(shopMobileReq.mobile)) {
			return fail(JsonResultCode.CODE_FAIL);
		}
		ShopRecord recode = saas.shop.getShopByMobile(shopMobileReq.mobile);
		if (recode != null) {
			return fail(JsonResultCode.CODE_ACCOUNT_MODILE_APPLIED);
		}
		return success(JsonResultCode.CODE_SUCCESS);
	}

	/**
	 * 添加店铺
	 * 
	 * @param shopReq
	 * @return
	 */
	@PostMapping("/system/shop/add")
	public JsonResult shopAdd(@RequestBody ShopReq shopReq) {
		ShopAccountRecord accountInfo = saas.shop.accout.getAccountInfoForID(shopReq.getSysId());
		if (accountInfo == null) {
			return fail();
		}
		if (saas.shop.addShop(shopReq)) {
			return success(JsonResultCode.CODE_SUCCESS);
		}
		return fail();
	}

	/**
	 * 续费
	 * @param sReq
	 * @return
	 */
	@PostMapping("/system/shop/renew")
	public JsonResult shopRenew(@RequestBody ShopRenewReq sReq) {
		int num = saas.shop.renew.insertShopRenew(sReq, sysAuth.user());
		if(num<1) {
			return fail(JsonResultCode.CODE_FAIL);
		}
		return success(JsonResultCode.CODE_SUCCESS);
	}
}

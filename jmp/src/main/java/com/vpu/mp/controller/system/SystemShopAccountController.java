package com.vpu.mp.controller.system;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.saas.shop.ShopAccountListQueryParam;
import com.vpu.mp.service.pojo.saas.shop.ShopAccountPojo;
import com.vpu.mp.service.pojo.shop.auth.ShopAccountResp;

/**
 * 
 * @author 新国
 *
 */
@RestController
@RequestMapping("/api")
public class SystemShopAccountController extends SystemBaseController {
	/**
	 * 添加商家账户
	 * 
	 * @param account
	 * @return
	 */
	@PostMapping(value = "/system/shop/account/add")
	public JsonResult addShopAccount(@RequestBody ShopAccountPojo account) {
		if (saas.shop.accout.addShopAccountService(account)) {
			return success(JsonResultCode.CODE_SUCCESS);
		} else {
			return fail(JsonResultCode.CODE_ACCOUNT_SAME);
		}
	}

	/**
	 * 商家账户列表查询
	 * @param param
	 * @return
	 */
	@PostMapping("/system/shop/account/list")
	public JsonResult getShopAccountList(@RequestBody ShopAccountListQueryParam param) {
		PageResult<ShopAccountPojo> result = saas.shop.accout.getPageList(param);
		for (ShopAccountPojo sap : result.dataList) {
			// sysId
			sap.setShopNumber(saas.shop.renew.getShopNumber((Integer) sap.getSysId()));
			sap.setRenewMoney(saas.shop.renew.getRenewTotal((Integer) sap.getSysId()));
		}
		ShopAccountResp shopAccountResp = new ShopAccountResp();
		shopAccountResp.setDataList(result.dataList);
		shopAccountResp.setPage(result.page);
		return success(shopAccountResp);
	}
}

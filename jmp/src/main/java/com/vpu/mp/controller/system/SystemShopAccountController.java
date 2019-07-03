package com.vpu.mp.controller.system;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.saas.shop.ShopAccountPojo;

/**
 * 
 * @author 新国
 *
 */
@RestController
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
			// TODO:需给出具体原因！！
			return fail(JsonResultCode.CODE_FAIL);
		}
	}
}

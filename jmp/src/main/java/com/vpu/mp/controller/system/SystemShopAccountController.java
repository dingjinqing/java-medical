package com.vpu.mp.controller.system;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.main.tables.pojos.ShopAccount;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.support.LineConvertHump;
/**
 * 
 * @author 新国
 *
 */
@RestController
public class SystemShopAccountController extends SystemBaseController {
	/**
	 * 添加商家账户
	 * @param account
	 * @return
	 */
	@PostMapping(value = "/system/shop/account/add")
	public JsonResult addShopAccount(@LineConvertHump ShopAccount account) {
		return success(JsonResultCode.CODE_SUCCESS);
//		if (sysAuth.addShopAccountService(account)) {
//			return success(JsonResultCode.CODE_SUCCESS);
//		} else {
//			return fail(JsonResultCode.CODE_ACCOUNT_SAME);
//		}
	}
}

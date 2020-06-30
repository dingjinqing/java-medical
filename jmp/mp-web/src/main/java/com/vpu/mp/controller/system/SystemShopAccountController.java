package com.vpu.mp.controller.system;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.service.pojo.saas.shop.ShopAccountListQueryParam;
import com.vpu.mp.service.pojo.saas.shop.ShopAccountOneParam;
import com.vpu.mp.service.pojo.saas.shop.ShopAccountOnePojo;
import com.vpu.mp.service.pojo.saas.shop.ShopAccountPojo;

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
	public JsonResult addShopAccount(@RequestBody  @Valid ShopAccountPojo account) {
		if (saas.shop.account.addShopAccountService(account)) {
			return success(JsonResultCode.CODE_SUCCESS);
		} else {
			return fail(JsonResultCode.CODE_ACCOUNT_SAME);
		}
	}

	/**
	 * 商家账户列表查询
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/system/shop/account/list")
	public JsonResult getShopAccountList(@RequestBody ShopAccountListQueryParam param) {
		PageResult<ShopAccountPojo> result = saas.shop.account.getPageList(param);
		// TODO 空的判断
		for (ShopAccountPojo sap : result.dataList) {
			// sysId
			sap.setShopNumber(saas.shop.getShopNumber((Integer) sap.getSysId()));
			sap.setRenewMoney(saas.shop.renew.getRenewTotal((Integer) sap.getSysId()));
		}
		return success(result);
	}
	
	
	/**
	 * 修改添加商家账户
	 * @param account
	 * @return
	 */
	@PostMapping("/system/shop/account/edit")
	public JsonResult editShopAccount(@RequestBody ShopAccountPojo account) {
		JsonResultCode code=saas.shop.account.editShopAccountService(account);
		if (code.equals(JsonResultCode.CODE_SUCCESS)) {
			return success(JsonResultCode.CODE_SUCCESS);
		} else {
			return fail(code);
		}
	}
	
	
	/**
	 * 查询单个商家账户
	 * @param account
	 * @return
	 */
	@PostMapping("/system/shop/account/getOne")
	public JsonResult getShopAccount(@RequestBody @Valid ShopAccountOneParam param) {
		ShopAccountRecord record = saas.shop.account.checkByIdAndNameOnMain(param.getUserName(),param.getSysId());
		if(record!=null) {
			return success(record.into(ShopAccountOnePojo.class));
		}
		return fail();

	}
	

}

package com.vpu.mp.controller.admin;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.jooq.Record9;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.auth.ShopManageParam;
import com.vpu.mp.service.pojo.shop.auth.ShopManagePwdParam;
import com.vpu.mp.service.pojo.shop.auth.ShopReq;
import com.vpu.mp.service.pojo.shop.auth.ShopSelectResp;

/**
 * 
 * @author 新国
 *
 */
@RequestMapping("/api")
@Controller
@RestController
public class AdminRoleController extends AdminBaseController {
	final String TOKEN = "V-Token";

	/**
	 * 查询店铺列表
	 * 
	 * @return
	 */
	@PostMapping(value = "/admin/account/shop/select")
	public JsonResult shopSelect() {
		AdminTokenAuthInfo info = adminAuth.user();
		if (info == null) {
			return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
		}
		// saas.shop.accout
		List<Record9<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String>> shopList = saas.shop
				.getRoleShopList(info.getSysId(), info.getSubAccountId());
		if (shopList.size() == 0) {
			logger().info("用户sysId：" + info.getSysId() + "，店铺列表为空");
		}
		ShopSelectResp response = new ShopSelectResp();
		response.setDataList(saas.shop.getShopList(info, shopList));
		response.setVersionMap(saas.shop.version.getVersionMap());
		return success(response);

	}

	/**
	 * 切换店铺
	 * 
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value = "/admin/account/shop/switch")
	public JsonResult switchShop(@RequestBody ShopReq shopReq) {
		// 更新redis
		if (adminAuth.switchShopLogin(shopReq.getShopId())) {
			return success(JsonResultCode.CODE_SUCCESS);
		}
		return fail(JsonResultCode.CODE_ACCOUNT_SHOP_NULL);
	}

	/**
	 * 账户设置的查询 只让主账户进入设置
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/account/manage/query")
	public JsonResult manageQuery() {
		AdminTokenAuthInfo info = adminAuth.user();
		if (info.isSubLogin()) {
			// 权限不足
			return fail(JsonResultCode.CODE_ACCOUNT_ROLE__AUTH_INSUFFICIENT);
		}
		ShopAccountRecord shopRecord = saas.shop.account.checkByIdAndNameOnMain(info.getUserName(), info.getSysId());
		if (shopRecord == null) {
			return fail();
		}
		return success(shopRecord.into(ShopManageParam.class));
	}

	/**
	 * 账户设置更新
	 * 
	 * @param shopManageParam
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/admin/account/manage")
	public JsonResult manage(@RequestBody @Valid ShopManageParam shopManageParam, BindingResult result) {
		// saas.shop.accout.updateAccountInfo()
		if (result.hasErrors()) {
			return this.fail(result.getFieldError().getDefaultMessage());
		}
		AdminTokenAuthInfo info = adminAuth.user();
		ShopAccountRecord pojo = new ShopAccountRecord();
		pojo.setSysId(info.getSysId());
		pojo.setAccountName(shopManageParam.getAccountName());
		pojo.setShopAvatar(shopManageParam.getShopAvatar());
		if (saas.shop.account.updateById(pojo) < 0) {
			return fail(JsonResultCode.CODE_FAIL);
		}
		adminAuth.updateAccountName(shopManageParam.getAccountName());
		return success(JsonResultCode.CODE_SUCCESS);
	}

	/**
	 * 更新用户密码
	 * @param sParam
	 * @return
	 */
	@PostMapping(value = "/admin/account/manage/updatepwd")
	public JsonResult managePasswd(@RequestBody @Valid ShopManagePwdParam sParam, BindingResult result) {
		if (result.hasErrors()) {
			return this.fail(result.getFieldError().getDefaultMessage());
		}
		AdminTokenAuthInfo info = adminAuth.user();
		if (info == null) {
			return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
		}
		if (!sParam.getNewPasswd().equals(sParam.confNewPasswd)) {
			return fail(JsonResultCode.CODE_ACCOUNT_PASSWD_NO_SAME);
		}
		ShopAccountRecord oldRecode = saas.shop.account.verify(info.getUserName(), sParam.getPasswd());
		if(oldRecode==null) {
			return fail(JsonResultCode.CODE_ACCOUNT_OLD_PASSWD_ERROR);
		}
		if(sParam.getNewPasswd().equals(sParam.getPasswd())) {
			return fail(JsonResultCode.CODE_ACCOUNT_OLD_NEW_PASSWD_NO_SAME);
		}
		ShopAccountRecord pojo = new ShopAccountRecord();
		pojo.setSysId(info.getSysId());
		pojo.setPassword(Util.md5(sParam.newPasswd));
		
		if (saas.shop.account.updateById(pojo) < 0) {
			return fail(JsonResultCode.CODE_FAIL);
		}
		return success(JsonResultCode.CODE_SUCCESS);
	}
}

package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.main.tables.records.StoreAccountRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.store.account.StoreAccountEditParam;
import com.vpu.mp.service.pojo.shop.store.account.StoreAccountParam;
import com.vpu.mp.service.pojo.shop.store.account.StoreAccountVo;
import com.vpu.mp.service.pojo.shop.store.account.StoreInfoVo;
import com.vpu.mp.service.pojo.shop.store.account.StoreManageParam;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthListPage;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthListParam;
import com.vpu.mp.service.pojo.shop.store.authority.StoreAuthVo;
import com.vpu.mp.service.pojo.shop.store.authority.StoreConstant;
import com.vpu.mp.service.saas.shop.StoreManageService;

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
	@GetMapping("/api/admin/store/account/getSetting")
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
	public JsonResult settingInfo(@RequestBody @Valid StoreAuthListParam param) {
		boolean setting = saas.shop.storeManageService.setting(param, shopId());
		return setting ? success() : fail();
	}

	/**
	 * 门店账户列表
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/store/account/list")
	public JsonResult getAccount(@RequestBody StoreAuthListPage param) {
		PageResult<StoreAccountVo> accountList = saas.shop.storeManageService.getAccountList(shopId(), param);
		return success(accountList);
	}

	/**
	 * 应用门店弹窗查询
	 * 
	 * @param accountId
	 * @return
	 */
	@GetMapping("/api/admin/store/account/storeInfo/{accountId}")
	public JsonResult getStoreInfo(@PathVariable Integer accountId) {
		StoreInfoVo storeInfo = saas.shop.storeManageService.getStoreInfo(accountId, shopId());
		// TODO 返回id不存在
		return storeInfo == null ? fail() : success(storeInfo);
	}

	/**
	 * 一些处理
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/store/account/manage")
	public JsonResult accountStoreManage(@RequestBody @Valid StoreManageParam param) {
		String action = param.getAct();
		Integer accountId = param.getAccountId();
		boolean flag = false;
		JsonResultCode code = JsonResultCode.CODE_FAIL;
		StoreManageService storeManageService = saas.shop.storeManageService;
		switch (action) {
		case StoreConstant.ACT_CONFIG:
			logger().info("设置");
			flag = storeManageService.config(param);
			break;
		case StoreConstant.ACT_DEL:
			logger().info("删除");
			int update = storeManageService.storeAccountService.delStoreAccount(accountId);
			flag = update > 0 ? true : false;
			break;
		case StoreConstant.ACT_STOP:
			logger().info("停用");
			int stop = storeManageService.stop(accountId);
			flag = stop > 0 ? true : false;
			break;
		case StoreConstant.ACT_STATRT:
			logger().info("启用");
			code = storeManageService.start(accountId);
			flag = true;
			break;
		default:
			break;
		}
		if (!flag) {
			return fail();
		} else {
			return code.equals(JsonResultCode.CODE_SUCCESS) ? success() : fail(code);
		}
	}

	
	/**
	 * 新增
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/store/account/create")
	public JsonResult accountStoreCreate(@RequestBody @Valid StoreAccountParam param) {
		StoreAccountRecord record = saas.shop.storeManageService.storeAccountService.findInfo(param.getAccountName(),
				param.getMobile(), null);
		if (record != null) {
			// 账户名或手机号重复了
			return fail(JsonResultCode.CODE_ACCOUNT_MOBILE_SAME);
		}
		int create = saas.shop.storeManageService.storeAccountService.create(param);
		return create > 0 ? success() : fail();

	}

	/**
	 * 编辑
	 * @param param
	 * @return
	 */
	@PostMapping("/api/admin/store/account/edit")
	public JsonResult accountStoreEdit(@RequestBody @Valid StoreAccountEditParam param) {
		StoreAccountRecord record = saas.shop.storeManageService.storeAccountService.findInfo(param.getAccountName(),
				param.getMobile(), param.getAccountId());
		if (record != null) {
			// 账户名或手机号重复了
			return fail(JsonResultCode.CODE_ACCOUNT_MOBILE_SAME);
		}
		Boolean check = saas.shop.storeManageService.check(param.getAccountPasswd());
		if(!check) {
			//密码格式不正确
			return fail(JsonResultCode.CODE_ACCOUNT_PASSWD_LENGTH_LIMIT);
		}
		int edit = saas.shop.storeManageService.storeAccountService.edit(param);
		return edit > 0 ? success() : fail();

	}
}

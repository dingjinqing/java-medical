package com.vpu.mp.controller.admin;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.shop.version.VersionConfig;
import com.vpu.mp.service.pojo.saas.shop.version.VersionMainConfig;
import com.vpu.mp.service.pojo.shop.auth.MenuParam;
import com.vpu.mp.service.pojo.shop.auth.MenuReturnParam;
import com.vpu.mp.service.pojo.shop.auth.PrivilegeAndPassParam;
import com.vpu.mp.service.pojo.shop.auth.PrivilegeVo;

/**
 * 
 * @author 新国
 *
 */
@RestController
@RequestMapping("/api")
public class AdminIndexController extends AdminBaseController {

	@Autowired
	protected JedisManager jedis;
	
	final protected String menuJsonPath = "admin.privilegeList.json";
	final protected String privilegeJsonPath = "admin.privilegePass.json";
	
	private static final String ENNAME="V-EnName";

	/**
	 * 返回店铺菜单
	 * 
	 * @return
	 */

	@RequestMapping(value = "/admin/showMenu")
	public JsonResult showMenu() {
		String json = Util.loadResource(menuJsonPath);
		String json2 = Util.loadResource(privilegeJsonPath);
		MenuParam menuParam = Util.parseJson(json, MenuParam.class);
		PrivilegeAndPassParam privilParam = Util.parseJson(json2, PrivilegeAndPassParam.class);
		Integer roleId = saas.shop.getShopAccessRoleId(adminAuth.user().sysId, adminAuth.user().loginShopId,
				adminAuth.user().subAccountId);
		PrivilegeVo privilegeVo = new PrivilegeVo();
		//版本权限
		VersionConfig vConfig = saas.shop.version.mergeVersion(adminAuth.user().loginShopId);
		if (vConfig == null) {
			// 版本存在问题，请联系管理员
			return fail(JsonResultCode.CODE_FAIL);
		}
		VersionMainConfig mainConfig = vConfig.getMainConfig();
		privilegeVo.setVMainConfig(mainConfig);
		if (roleId != 0) {
			// 是子账户
			MenuReturnParam menuReturnParam = saas.shop.role.getPrivilegeListPublic(roleId);
			// 加个非空判断
			MenuParam outParam = saas.shop.role.outParam(menuParam, menuReturnParam.getPrivilegeList());
			PrivilegeAndPassParam privilParam2 = new PrivilegeAndPassParam();

			privilParam2.setPrivilegeLlist(Arrays.asList(menuReturnParam.getPrivilegePass().get(0)));
			privilParam2.setPassList((Arrays.asList(menuReturnParam.getPrivilegePass().get(1))));

			
			MenuParam specialParam = saas.shop.role.specialParam(outParam, menuParam.getPlus());
			
			privilegeVo.setMenuParam(specialParam);
			privilegeVo.setPassParam(privilParam2);
			return success(privilegeVo);
		} else {
			// 不是子账户
			MenuParam specialParam = saas.shop.role.specialParam(menuParam, menuParam.getPlus());
			privilegeVo.setMenuParam(specialParam);
			privilegeVo.setPassParam(privilParam);
			return success(privilegeVo);
		}
	}

	/**
	 * 点击的菜单或者功能有没有权限
	 * 
	 * @return
	 */
	 @RequestMapping(value = "/admin/checkMenu")
	public JsonResult checkMenu() {
		if (StringUtils.isEmpty(adminAuth.user().loginShopId)) {
			return fail(JsonResultCode.CODE_ACCOUNT_ROLE__SHOP_SELECT);
		}
		judgeVersion();
		Integer roleId = saas.shop.getShopAccessRoleId(adminAuth.user().sysId, adminAuth.user().loginShopId,
				adminAuth.user().subAccountId);
		if (roleId == -1) {
			// 错误
			return fail(JsonResultCode.CODE_FAIL);
		}
		if (roleId == 0) {
			// 不是子账户,返回有权限
			return success(JsonResultCode.CODE_SUCCESS);
		}
		// 子账户，判断是否可以点击
		if (saas.shop.role.checkPrivilegeList(roleId, request.getHeader(ENNAME))) {
			return success(JsonResultCode.CODE_SUCCESS);
		}
		return fail(JsonResultCode.CODE_FAIL);
	}

	public JsonResultCode judgeVersion() {
		VersionConfig vConfig = saas.shop.version.mergeVersion(adminAuth.user().loginShopId);
		if (vConfig == null) {
			// 版本存在问题，请联系管理员
			return JsonResultCode.CODE_FAIL;
		}
		VersionMainConfig mainConfig = vConfig.getMainConfig();
		String enName = request.getHeader(ENNAME);
		if(StringUtils.isEmpty(enName)) {
			return JsonResultCode.CODE_FAIL;
		}

		if (saas.shop.version.checkMainConfig(mainConfig, enName)) {
			//为true则请求在version版本里
			return JsonResultCode.CODE_SUCCESS;
		}
		//此功能需要更高版本才可使用。如需了解详情我们的产品顾问将尽快与您联系！！！
		return JsonResultCode.CODE_ACCOUNT_VERSIN_NO_POWER;
	}

}

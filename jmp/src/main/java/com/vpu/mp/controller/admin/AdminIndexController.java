package com.vpu.mp.controller.admin;

import java.util.Arrays;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JedisManager;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.Util;
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

	JedisManager jedis = JedisManager.instance();
	final protected String menuJsonPath = "admin.privilegeList.json";
	final protected String privilegeJsonPath = "admin.privilegePass.json";
	/**
	 * 返回店铺菜单
	 * 
	 * @return
	 */
	//@RequestMapping(value = "/admin/showMenu")
	public JsonResult showMenu() {
		String json = Util.loadResource(menuJsonPath);
		String json2 = Util.loadResource(privilegeJsonPath);
		MenuParam menuParam = Util.parseJson(json, MenuParam.class);
		PrivilegeAndPassParam privilParam = Util.parseJson(json2, PrivilegeAndPassParam.class);
		Integer roleId = saas.shop.getShopAccessRoleId(adminAuth.user().sysId, adminAuth.user().loginShopId,
				adminAuth.user().subAccountId);
		PrivilegeVo privilegeVo = new PrivilegeVo();
		if (roleId != 0) {
			// 是子账户
			MenuReturnParam menuReturnParam = saas.shop.role.getPrivilegeListPublic(roleId);
			// 加个非空判断
			MenuParam outParam = saas.shop.role.outParam(menuParam, menuReturnParam.getPrivilegeList());
			PrivilegeAndPassParam privilParam2 = new PrivilegeAndPassParam();
			
			privilParam2.setPrivilegeLlist(Arrays.asList(menuReturnParam.getPrivilegePass().get(0)));
			privilParam2.setPassList((Arrays.asList(menuReturnParam.getPrivilegePass().get(1))));
			
			privilegeVo.setMenuParam(outParam);
			privilegeVo.setPassParam(privilParam2);
			return success(privilegeVo);
		} else {
			// 不是子账户
			privilegeVo.setMenuParam(menuParam);
			privilegeVo.setPassParam(privilParam);
			return success(menuParam);
		}
	}

	@RequestMapping(value = "/admin/test")
	@ResponseBody
	public JsonResult test() throws InterruptedException {
		if("main".equals(this.input("db"))) {
			saas.repairDb.repairMainDb();
		}else if("shop_all".equals(this.input("db"))) {
			saas.repairDb.repairAllShopDb();
		}
		else if("shop".equals(this.input("db"))) {
			Integer shopId = Integer.valueOf(this.input("shop_id"));
			saas.repairDb.repairShopDb(shopId);
		}else {
			return fail();
		}
		return success( );
	}
	
	/**
	 * 点击的菜单或者功能有没有权限
	 * @return
	 */
	@RequestMapping(value = "/admin/checkMenu")
	public JsonResult checkMenu() {
		if(StringUtils.isEmpty(adminAuth.user().loginShopId)) {
			return fail(JsonResultCode.CODE_ACCOUNT_ROLE__SHOP_SELECT);
		}
		Integer roleId = saas.shop.getShopAccessRoleId(adminAuth.user().sysId, adminAuth.user().loginShopId,
				adminAuth.user().subAccountId);
		if(roleId==-1) {
			//错误
			return fail(JsonResultCode.CODE_FAIL);
		}
		if (roleId == 0) {
			// 不是子账户,返回有权限
			return success(JsonResultCode.CODE_SUCCESS);
		}
		//子账户，判断是否可以点击
		if(saas.shop.role.checkPrivilegeList(roleId, request.getHeader("enName"))) {
			return success(JsonResultCode.CODE_SUCCESS);
		}
		return fail(JsonResultCode.CODE_FAIL);
	}

}

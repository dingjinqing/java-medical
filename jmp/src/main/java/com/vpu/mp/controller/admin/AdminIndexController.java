package com.vpu.mp.controller.admin;

import java.util.Arrays;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JedisManager;
import com.vpu.mp.service.foundation.JsonResult;
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
	@RequestMapping(value = "/admin/showMenu")
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
	}}

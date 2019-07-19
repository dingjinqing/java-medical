package com.vpu.mp.service.saas.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.MenuUtil;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.foundation.MenuUtil.Menu;

/**
 * 
 * @author 新国
 *
 */
@Service
@Scope("prototype")
public class ShopMenuService extends BaseService {

	final protected String menuJsonPath = "admin.menu.json";
	final protected String authorityJsonPath = "admin.authority.json";

	final protected String menuJson = "admin.authorityNew.json";
	final protected String authorityJson = "admin.privilegePassNew.json";
	

	private static final String prNameList="prNameList";
	
	private static final String enNameList="enNameList";

	private static final String childConfig="child_config";
	public List<Menu> getMenu() {
		return getRoleMenu(0);
	}

	public List<Menu> getRoleMenu(Integer roleId) {
		String[] privilegeList = roleId == 0 ? null : saas().shop.role.getPrivilegeList(roleId);
		return MenuUtil.getRoleMenu(menuJsonPath, authorityJsonPath, privilegeList);
	}

	public Boolean isRoleAccess(List<Menu> menuList, String path) {
		return MenuUtil.isRoleAccess(menuList, path);
	}

	public Boolean isRoleAccess(Integer roleId, String path) {
		return MenuUtil.isRoleAccess(getRoleMenu(roleId), path);
	}

	/**
	 * 子账户对应展示按钮和输入密码的权限校验
	 * 
	 * @param roleId
	 * @param path
	 * @param reqeName
	 * @return
	 */
	public JsonResultCode passwdAccess(Integer roleId, String path, String reqeName, String passwd) {
		String[] privilegePass = roleId == 0 ? null : saas().shop.role.getPrivilegePass(roleId);
		if (privilegePass == null) {
			return JsonResultCode.CODE_SUCCESS;
		}
		if (StringUtils.isEmpty(privilegePass[0])) {
			// 您没有权限。不能展示
			return JsonResultCode.CODE_ACCOUNT_ROLE__AUTH_INSUFFICIENT;
		}
		if (StringUtils.isEmpty(privilegePass[1])) {
			// 不需要密码，验证成功
			return JsonResultCode.CODE_SUCCESS;
		}

		String json = Util.loadResource(authorityJson);

		ArrayList<?> list = Util.parseJson(json, ArrayList.class);
		Map<String, ?> map = (HashMap) list.get(0);
		String preName = (String) map.get("prName");
		if (prNameList.equals(preName)) {
			List<?> prNameList = (List<?>) map.get("includeApi");
			if (!includeEname(prNameList, reqeName)) {
				// 请求不在所有定义的权限里
				return JsonResultCode.CODE_ACCOUNT_ROLE__AUTH_INSUFFICIENT;
			}
		}
		
		String[] pShow = privilegePass[0].split(",");
		String[] pPass = privilegePass[1].split(",");

		if (!include(pShow, reqeName)) {
			// 请求不在权限json里
			return JsonResultCode.CODE_ACCOUNT_ROLE__AUTH_INSUFFICIENT;
		}

		for (int i = 1; i < list.size(); i++) {
			Map<String, ?> map2 = (HashMap<String, ?>) list.get(i);
			String prName2 = (String) map2.get("prName");
			if (prName2.equals(reqeName)) {
				if (includeEname((List<?>) map2.get("includeApi"), path)) {
					// 请求api在json对应的api里面
					//
					if (include(pShow, reqeName)) {
						// 按钮可以点
						if (include(pPass, reqeName)) {
							// 需要密码
							if (StringUtils.isEmpty(passwd)) {
								// 需要权限密码。
								return JsonResultCode.CODE_ACCOUNT_NEED_PRIVILEGEPASS;
							}
							if (saas().shop.role.veryPasswd(passwd, roleId)) {
								// 密码校验正常
								return JsonResultCode.CODE_SUCCESS;

							} else {
								// 密码错误
								return JsonResultCode.CODE_ACCOUNT_OR_PASSWORD_INCRRECT;

							}
						}
						return JsonResultCode.CODE_SUCCESS;
					}

				}
			}
		}

		return JsonResultCode.CODE_FAIL;
	}

	/**
	 * 子账户对应发送api权限的校验
	 * 
	 * @param roleId
	 * @param path
	 * @param reqeName
	 * @return
	 */
	public Boolean apiAccess(Integer roleId, String path, String reqeName) {
		String[] privilegeList = roleId == 0 ? null : saas().shop.role.getPrivilegeList(roleId);
		if (privilegeList == null) {
			// 主账户登录，暂时不校验权限。
			// TODO 加不加权限看以后
			return true;
		}
		if(StringUtils.isEmpty(reqeName)) {
			return false;
		}
		String json = Util.loadResource(menuJson);

		ArrayList<?> list = Util.parseJson(json, ArrayList.class);
		Map<String, ?> map = (HashMap) list.get(0);
		String eName = (String) map.get("enName");
		if (enNameList.equals(eName)) {
			List<?> eNameList = (List<?>) map.get("includeApi");
			if (!includeEname(eNameList, reqeName)) {
				// 请求不在所有定义的权限里
				return false;
			}
		}
		
		//单独处理：子账户不能操作店铺权限菜单
		if(childConfig.equals(reqeName)) {
			return false;
		}

		if (!include(privilegeList, reqeName)) {
			// 请求菜单不在用户权限里
			return false;
		}

		// 去json查询这个权限对应的api
		for (int i = 1; i < list.size(); i++) {
			Map<String, ?> map2 = (HashMap<String, ?>) list.get(i);
			String eName2 = (String) map2.get("enName");
			if (eName2.equals(reqeName)) {
				// 请求api在权限对应的api里面
				if (includeEname((List<?>) map2.get("includeApi"), path)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 匹配返回true
	 * 
	 * @param eNameList
	 * @param reqEnName
	 * @return
	 */
	private static Boolean includeEname(List<?> eNameList, String reqEnName) {
		for (Object allEname : eNameList) {
			if (allEname.equals(reqEnName)) {
				return true;
			}
		}
		return false;
	}

	private static Boolean include(String[] eNameList, String reqEnName) {
		for (Object allEname : eNameList) {
			if (allEname.equals(reqEnName)) {
				return true;
			}
		}
		return false;
	}
	
}

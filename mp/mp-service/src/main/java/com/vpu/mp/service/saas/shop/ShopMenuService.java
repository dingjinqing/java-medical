package com.vpu.mp.service.saas.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.shop.version.VersionConfig;
import com.vpu.mp.service.pojo.saas.shop.version.VersionMainConfig;

/**
 * 
 * @author 新国
 *
 */
@Service


public class ShopMenuService extends MainBaseService {


	final protected String menuJson = "admin.authorityNew.json";
	final protected String authorityJson = "admin.privilegePassNew.json";
	final protected String versionJson = "admin.versionNew.json";

	private static final String PRNAMELIST = "prNameList";

	private static final String ENNAMELIST = "enNameList";

	private static final String CHILDCONFIG = "child_config";
	
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
		if (PRNAMELIST.equals(preName)) {
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
		if (StringUtils.isEmpty(reqeName)) {
			return false;
		}
		String json = Util.loadResource(menuJson);

		ArrayList<?> list = Util.parseJson(json, ArrayList.class);
		Map<String, ?> map = (HashMap) list.get(0);
		String eName = (String) map.get("enName");
		if (ENNAMELIST.equals(eName)) {
			List<?> eNameList = (List<?>) map.get("includeApi");
			if (!includeEname(eNameList, reqeName)) {
				// 请求不在所有定义的权限里
				return false;
			}
		}

		// 单独处理：子账户不能操作店铺权限菜单
		if (CHILDCONFIG.equals(reqeName)) {
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

	/**
	 * 版本权限的校验 按照接口校验
	 * 
	 * @param shopId
	 * @param path
	 * @param reqEnName
	 * @param reqVsName
	 * @return
	 */
	public JsonResultCode versionAccess(Integer shopId, String path, String reqEnName, String reqVsName) {
		VersionConfig vConfig = saas().shop.version.mergeVersion(shopId);
		if (vConfig == null) {
			// 版本存在问题，请联系管理员
			return JsonResultCode.CODE_FAIL;
		}
		VersionMainConfig mainConfig = vConfig.getMainConfig();

		String json = Util.loadResource(versionJson);
		ArrayList<HashMap> list = Util.parseJson(json, ArrayList.class);
		List<String> versionJson = (List<String>) list.get(0).get("includeApi");

		if (!includeEname(versionJson, reqVsName)) {
			// 请求不在所有定义的权限里
			return JsonResultCode.CODE_ACCOUNT_ROLE__AUTH_INSUFFICIENT;
		}

		if (!saas().shop.version.checkMainConfig(mainConfig, reqVsName)) {
			// 此功能需要更高版本才可使用。如需了解详情我们的产品顾问将尽快与您联系！！
			return JsonResultCode.CODE_ACCOUNT_VERSIN_NO_POWER;
		}
		// 查询对应的api
		for (int i = 1; i < list.size(); i++) {
			Map hashMap = list.get(i);
			if (reqVsName.equals(hashMap.get("vsName")) && reqEnName.equals(hashMap.get("enName"))) {
				versionJson = (List<String>) hashMap.get("includeApi");
				// 有些特殊的功能在对应的api方法里校验。规定这些特殊的IncludeApi为空
				// 以后请往后添加-》》》目前包括： 小程序管理中的十个，门店买单送积分，签到送积分，门店买单 ，技师管理，服务管理
				if (versionJson.size() == 0) {
					return JsonResultCode.CODE_SUCCESS;
				}
				// 请求包含在api里
				if (includeEname(versionJson, reqVsName)) {
					// 和用户自己的权限进行校验
					return JsonResultCode.CODE_SUCCESS;
				}
				//
				return JsonResultCode.CODE_ACCOUNT_ROLE__AUTH_INSUFFICIENT;
			}

		}
		// 此功能需要更高版本才可使用。如需了解详情我们的产品顾问将尽快与您联系！！
		return JsonResultCode.CODE_ACCOUNT_VERSIN_NO_POWER;
	}
}

package com.vpu.mp.service.saas.shop;

import java.util.List;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.MenuUtil;
import com.vpu.mp.service.foundation.MenuUtil.Menu;

/**
 * 
 * @author 新国
 *
 */
public class ShopMenuService extends BaseService {

	final protected String menuJsonPath = "admin.menu.json";
	final protected String authorityJsonPath = "admin.authority.json";

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
}

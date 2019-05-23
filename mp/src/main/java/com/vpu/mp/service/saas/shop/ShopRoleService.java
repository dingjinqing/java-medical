package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.ShopRole.SHOP_ROLE;

import com.vpu.mp.db.main.tables.records.ShopRoleRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;

public class ShopRoleService extends BaseService {
	
	public ShopRoleRecord getRoleById(Integer roleId) {
		return db().selectFrom(SHOP_ROLE).where(SHOP_ROLE.ROLE_ID.eq(roleId)).fetchOne();
	}
	
	protected String[] getPrivilegeList(Integer roleId) {
		ShopRoleRecord role = getRoleById(roleId);
		if(role == null) return null;
		return Util.parseJSON(role.getPrivilegeList(),String[].class);
	}
}

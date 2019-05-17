package com.vpu.mp.service.saas.privilege;

import com.vpu.mp.db.main.tables.B2cSystemRole;
import com.vpu.mp.db.main.tables.records.B2cSystemRoleRecord;
import com.vpu.mp.service.foundation.BaseComponent;

/**
 * 
 * @author 新国
 *
 */
public class Role extends BaseComponent {

	protected B2cSystemRole tableRole = B2cSystemRole.B2C_SYSTEM_ROLE;

	public B2cSystemRoleRecord getRole(Integer roleId) {
		return db().selectFrom(tableRole).where(tableRole.ROLE_ID.eq(roleId)).fetchOne();
	}
}

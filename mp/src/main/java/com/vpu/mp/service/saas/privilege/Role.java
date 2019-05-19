package com.vpu.mp.service.saas.privilege;

import static com.vpu.mp.db.main.tables.SystemRole.SYSTEM_ROLE;
import com.vpu.mp.db.main.tables.records.SystemRoleRecord;
import com.vpu.mp.service.foundation.BaseComponent;

/**
 * 
 * @author 新国
 *
 */
public class Role extends BaseComponent {


	public SystemRoleRecord getRole(Integer roleId) {
		return db().selectFrom(SYSTEM_ROLE).where(SYSTEM_ROLE.ROLE_ID.eq(roleId)).fetchOne();
	}
}

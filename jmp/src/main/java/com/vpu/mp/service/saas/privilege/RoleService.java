package com.vpu.mp.service.saas.privilege;

import static com.vpu.mp.db.main.tables.SystemRole.SYSTEM_ROLE;
import com.vpu.mp.db.main.tables.records.SystemRoleRecord;
import com.vpu.mp.service.foundation.BaseService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 新国
 *
 */
@Service
@Scope("prototype")
public class RoleService extends BaseService {


	public SystemRoleRecord getRole(Integer roleId) {
		return db().selectFrom(SYSTEM_ROLE).where(SYSTEM_ROLE.ROLE_ID.eq(roleId)).fetchAny();
	}
}

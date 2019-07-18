package com.vpu.mp.service.saas.privilege;

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
public class MenuService extends BaseService {

	protected RoleService role;

}

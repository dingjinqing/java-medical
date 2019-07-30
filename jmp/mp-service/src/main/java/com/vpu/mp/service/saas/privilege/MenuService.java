package com.vpu.mp.service.saas.privilege;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.MainBaseService;

/**
 * 
 * @author 新国
 *
 */
@Service

public class MenuService extends MainBaseService {

	@Autowired public RoleService role;

}

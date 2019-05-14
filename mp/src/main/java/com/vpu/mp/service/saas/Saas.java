package com.vpu.mp.service.saas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.service.saas.privilege.ChildAccount;
import com.vpu.mp.service.saas.privilege.MenuManager;
import com.vpu.mp.service.saas.privilege.Role;
import com.vpu.mp.service.saas.privilege.SystemUser;

@Component
public class Saas {
	
	@Autowired
	public SystemUser sysUser;
	
	@Autowired
	public ChildAccount childAccount;
	
	@Autowired
	public Role role;
	
	@Autowired
	public MenuManager menu;
	
}

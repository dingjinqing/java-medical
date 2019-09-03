package com.vpu.mp.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import com.vpu.mp.command.CommandRunner;
import com.vpu.mp.service.saas.SaasApplication;

@Service
public class RepairDbCommand implements CommandRunner {

	@Autowired
	protected SaasApplication saas;
	
	@Override
	public void run(ApplicationArguments args) {
		saas.repairDb.repairAllShopDb(true);
	}

	@Override
	public String description() {
		return "Repair db tables";
	}

}

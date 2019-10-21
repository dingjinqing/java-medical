package com.vpu.mp.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.github.fonimus.ssh.shell.SshShellHelper;
import com.github.fonimus.ssh.shell.commands.SshShellComponent;
import com.vpu.mp.service.saas.SaasApplication;

@SshShellComponent
@ShellCommandGroup("Database Commands")
public class DbCommand {
	
	@Autowired
	private SshShellHelper helper;
	
	@Autowired
	protected SaasApplication saas;

	@ShellMethod("Repair Main Db. --onlyCheck [true|false]")
	public void repairMain(@ShellOption(arity = 1, defaultValue = "true") boolean onlyCheck) {
		saas.repairDb.repairMainDb(onlyCheck);
	}

	@ShellMethod("Repair Shop Db. --onlyCheck [true|false] --all [true|false] --shop-id shopId ")
	public void repairShop(@ShellOption(arity = 1, defaultValue = "true") boolean onlyCheck,
			boolean all,
			@ShellOption(defaultValue = "0") int shopId) {
		if(shopId > 0) {
			saas.repairDb.repairShopDb(onlyCheck, shopId);
		}else {
			if(all) {
				saas.repairDb.repairAllShopDb(onlyCheck);
			}else {
				this.helper.printError("--all and --shop-id must select one.");
			}
		}
	}

}

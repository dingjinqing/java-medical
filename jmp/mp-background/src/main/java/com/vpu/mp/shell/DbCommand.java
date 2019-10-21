package com.vpu.mp.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.github.fonimus.ssh.shell.PromptColor;
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

	@ShellMethod("Repair Main Db. Parameters: --onlyCheck [true|false]")
	public String repairMain(@ShellOption(arity = 1, defaultValue = "true") boolean onlyCheck) {
		this.log(String.format("repairMain onlyCheck=%b", onlyCheck));
		saas.repairDb.repairMainDb(onlyCheck);
		return "repairMain Done.";
	}

	@ShellMethod("Repair Shop Db.  Parameters: --onlyCheck [true|false] --all [true|false] --shop-id shopId ")
	public String repairShop(@ShellOption(arity = 1, defaultValue = "true") boolean onlyCheck,
			boolean all,
			@ShellOption(defaultValue = "0") int shopId) {
		this.log(String.format("repairShop onlyCheck=%b, shopId=%d, all=%b", onlyCheck, shopId, all));
		if (shopId > 0) {
			saas.repairDb.repairShopDb(onlyCheck, shopId);
			return "repairShopDb Done";
		} else {
			if (all) {
				saas.repairDb.repairAllShopDb(onlyCheck);
				return "repairShopDb All Done";
			} else {
				this.log("--all and --shop-id must select one.",PromptColor.RED);
				return "repairShop Failed";
			}
		}

	}
	
	@ShellMethod("Test Command")
	public void test() {
		for(int i=0;i<1000;i++) {
			String info = this.helper.progress(i, 1000);
			System.out.println(info);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void log(String message) {
		this.helper.print(message, null);
	}

	protected void log(String message, PromptColor color) {
		this.helper.print(message, color);
		this.helper.terminalWriter().flush();
		this.helper.terminalWriter().checkError();
	}

}

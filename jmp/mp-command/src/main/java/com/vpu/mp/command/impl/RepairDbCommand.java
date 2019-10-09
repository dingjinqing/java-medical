package com.vpu.mp.command.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import com.vpu.mp.command.CommandRunner;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * 修复数据库
 * 
 * @author lixinguo
 *
 */
@Service
public class RepairDbCommand implements CommandRunner {
	
	protected final String ARG_MAIN = "main";
	protected final String ARG_SHOP = "shop";
	protected final String ARG_SHOP_ID = "shop_id";
	protected final String ARG_OPT_ONLY_CHECK = "only_check";

	
	final Logger logger = LoggerFactory.getLogger(RepairDbCommand.class);

	@Autowired
	protected SaasApplication saas;

	@Override
	public void run(ApplicationArguments args) {
		if (args.getNonOptionArgs().size() < 2) {
			logger.error(description());
			return;
		}
		Boolean onlyCheck = this.getOption(args, ARG_OPT_ONLY_CHECK, Boolean.class, true);

		String type = this.getNonOption(args, 1);
		if (ARG_MAIN.equals(type)) {
			saas.repairDb.repairMainDb(onlyCheck);
		} else if (ARG_SHOP.equals(type)) {
			if (hasOption(args, ARG_SHOP_ID)) {
				Integer shopId = this.getOption(args, ARG_SHOP_ID, Integer.class, 0);
				if (shopId.intValue() == 0) {
					logger.error("--shop_id is invalid,usage: {} ", description());
					return;
				}
				saas.repairDb.repairShopDb(onlyCheck, shopId);
			} else {
				saas.repairDb.repairAllShopDb(onlyCheck);
			}
		}
	}

	@Override
	public String description() {
		return "Repair db tables, Params list: db:repair main|shop --shop_id=? --only_check=?true";
	}

}

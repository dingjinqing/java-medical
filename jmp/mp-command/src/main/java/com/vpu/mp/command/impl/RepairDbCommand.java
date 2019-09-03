package com.vpu.mp.command.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import com.vpu.mp.command.CommandRunner;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * 修复数据库
 * @author lixinguo
 *
 */
@Service
public class RepairDbCommand implements CommandRunner {

	final Logger logger = LoggerFactory.getLogger(RepairDbCommand.class);

	@Autowired
	protected SaasApplication saas;

	@Override
	public void run(ApplicationArguments args) {
		List<String> nonOptions = args.getNonOptionArgs();
		if (nonOptions.size() < 2) {
			logger.error(description());
			return;
		}
		Boolean onlyCheck = true;
		List<String> options = args.getOptionValues("--only_check");
		if (options.size() > 0 && StringUtils.equalsIgnoreCase(options.get(0), "false")) {
			onlyCheck = false;
		}

		String type = nonOptions.get(1);
		if (type.equals("main")) {
			saas.repairDb.repairMainDb(onlyCheck);
		} else if (type.equals("shop")) {
			List<String> shopIdOptions = args.getOptionValues("--shop_id");
			if (shopIdOptions.size() > 0) {
				Integer shopId = Util.getInteger(shopIdOptions.get(0));
				if (shopId == 0) {
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

package com.vpu.mp.command;

import com.vpu.mp.command.impl.RepairDbCommand;

import lombok.Getter;

public enum CommandsEnum {
	REPAIR_DB_COMMAND("db:repair", RepairDbCommand.class);

	@Getter
	private String key;
	@Getter
	private Class<? extends CommandRunner> cls;

	CommandsEnum(String key, Class<? extends CommandRunner> cls) {
		this.key = key;
		this.cls = cls;
	}

}

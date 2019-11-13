package com.vpu.mp.command;

import java.util.List;

import org.jooq.tools.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.vpu.mp.support.SpringUtil;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author lixinguo
 *
 */
@Component
@Slf4j
public class CommandKernel implements ApplicationRunner,ApplicationContextAware {

	 private ApplicationContext applicationContext;
	 
	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<String> arguments = args.getNonOptionArgs();
		if(arguments.size() == 0) {
			this.printDescriptions();
			exit();
			return;
		}
		CommandsEnum[] commans = CommandsEnum.values();
		for(CommandsEnum command : commans) {
			if(command.getKey().equals(arguments.get(0))){
				SpringUtil.getBean(command.getCls()).run(args);
				exit();
				return;
			}
		}
		this.printDescriptions();
		exit();
	}
	
	public void printDescriptions() {
		CommandsEnum[] commans = CommandsEnum.values();
		for(CommandsEnum command : commans) {
			output(command.getKey(),SpringUtil.getBean(command.getCls()).description());
		}
	}
	
	protected void output(String key,String description) {
		key = StringUtils.rightPad(key, 20 - key.length());
		System.out.println("\033[31;0m"+key+"\033[0m\t"+description);
	}
	
	protected void exit() {
		((ConfigurableApplicationContext) applicationContext).close();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}

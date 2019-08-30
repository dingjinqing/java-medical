package com.vpu.mp.command;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.vpu.mp.support.SpringUtil;

@Component
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
			System.out.println(command.getKey()+"\t\t"+SpringUtil.getBean(command.getCls()).description());
		}
	}
	
	protected void exit() {
		((ConfigurableApplicationContext) applicationContext).close();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}

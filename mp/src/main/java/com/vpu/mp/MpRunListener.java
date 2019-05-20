package com.vpu.mp;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

public class MpRunListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

	protected static ConfigurableEnvironment env = null;

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		System.out.println("ApplicationEnvironmentPreparedEvent Come");
		env = event.getEnvironment();
	}

	public synchronized static String getProperty(String key) {
		if(env == null) {
			System.out.println("ConfigurableEnvironment is NULL!");
			return null;
		}
		return env.getProperty(key);
	}

}

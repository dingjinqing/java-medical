package com.vpu.mp.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
/**
 * 
 * @author 新国
 *
 */
@Component
public class SpringConfig implements EnvironmentAware{

	private static Environment env = null;
	
	
	@Override
    public void setEnvironment(Environment environment) {
		System.out.println("SpringConfig setEnvironment");
		env = environment;
    }
     
    public synchronized static String getProperty(String key) {
    	if(env == null) {
    		System.out.println("SpringConfig env is null");
    		return null;
    	}
        return env.getProperty(key);
    }

}

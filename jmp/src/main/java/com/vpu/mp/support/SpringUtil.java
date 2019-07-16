package com.vpu.mp.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 新国
 *
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContextParam) throws BeansException {
		applicationContext = applicationContextParam;
	}

	public static <T> T getBean(Class<T> tClass) {
		return applicationContext.getBean(tClass);
	}
	
	public static Object getBean(String id) {
		return applicationContext.getBean(id);
	}

}

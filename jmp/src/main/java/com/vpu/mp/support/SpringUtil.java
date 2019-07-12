package com.vpu.mp.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author 新国
 *
 */
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContextParam) throws BeansException {
		applicationContext = applicationContextParam;
	}

	public static Object getObject(String id) {
		Object object = null;
		object = applicationContext.getBean(id);
		return object;
	}

	public static <T> T getObject(Class<T> tClass) {
		return applicationContext.getBean(tClass);
	}

	public static Object getBean(String tClass) {
		return applicationContext.getBean(tClass);
	}

	public <T> T getBean(Class<T> tClass) {
		return applicationContext.getBean(tClass);
	}

}

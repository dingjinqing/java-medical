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
		String name =tClass.getSimpleName();
		String className = name.substring(0,1).toLowerCase()+name.substring(1,name.length());
		if( applicationContext.containsBean(className) ){
			return applicationContext.getBean(tClass);
		}else {
			System.out.println("#########################"+tClass.getName());
		}
		return null;
	}
	public static boolean inited(){
		return applicationContext != null;
	}
	public static Object getBean(String id) {
		return applicationContext.getBean(id);
	}

}

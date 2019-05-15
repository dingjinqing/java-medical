package com.vpu.mp;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringConfig implements ApplicationContextAware{

	private static ApplicationContext context = null;

	@Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }
 
    public synchronized static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

}

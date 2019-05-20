package vpu.com;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
/**
 * 
 * @author 新国
 *
 */
@Component
public class SpringConfig implements ApplicationContextAware{

	@Autowired
	protected Environment enviornment;
	
	
	private static ApplicationContext context = null;
	
	private static Environment env = null;
	
	
	@Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
        env = enviornment;
    }
 
    public synchronized static Object getBean(String beanName) {
        return context.getBean(beanName);
    }
    
    
    public synchronized static String getProperty(String key) {
        return env.getProperty(key);
    }

}

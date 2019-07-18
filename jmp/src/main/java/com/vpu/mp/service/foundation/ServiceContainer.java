package com.vpu.mp.service.foundation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.vpu.mp.support.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 新国
 *
 */
public class ServiceContainer {
	/**
	 * 单线程下，分店铺服务（继承于BaseService）属性实例共享
	 */
	private static ThreadLocal<Map<String, Object>> servicesThreadLocal = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>(0);
		}
	};
	
	public Map<String, Object> getServiceInstance(){
		return servicesThreadLocal.get();
	}

	protected Integer shopId = 0;

	public ServiceContainer() {
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	/**
	 * 构造函数调用此函数，初始化本实例下所有继承于BaseService类的属性
	 */
	public void initServices() {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			boolean ret = BaseService.class.isAssignableFrom(field.getType());
			if (ret) {
				Object service = getService(field.getType().getName());
				field.setAccessible(true);
				try {
					field.set(this, service);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		}
	}
	/**
	 * 根据店铺ID和类名获取服务实例（单例模式）
	 * 
	 * @param serviceClassName
	 * @return
	 */
	protected Object getService(String serviceClassName) {
		Map<String, Object>  services = this.getServiceInstance();
		String key = shopId + "_" + serviceClassName;
		if (services.containsKey(key)) {
			String message = "load exists service: " + serviceClassName + "(" + shopId + ")";
			logger().debug(message);
			return services.get(key);
		}

		logger().debug("instance service: " + serviceClassName + "(" + shopId + ")");

		try {
			Object	fieldInstance;
//			if( SpringUtil.inited() ){
				Class<?> cls = Class.forName(serviceClassName);
				fieldInstance = SpringUtil.getBean(cls);
//			}else{
//				Class<?> cls = Class.forName(serviceClassName);
//				Constructor<?> constructor = cls.getConstructor();
//				fieldInstance =  constructor.newInstance();
//			}

			System.out.println(fieldInstance);
			ServiceContainer service = (ServiceContainer) fieldInstance;
			service.setShopId(shopId);
			services.put(key, fieldInstance);
			service.initServices();
			return fieldInstance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected Logger logger() {
		return LoggerFactory.getLogger(this.getClass());
	}
}

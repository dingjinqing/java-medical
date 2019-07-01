package com.vpu.mp.service.foundation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceContainer {
	/**
	 * 单线程下，分店铺服务（继承于BaseService）属性实例共享
	 */
	private static ThreadLocal<Map<String, Object>> servicesThreadLocal = new ThreadLocal<Map<String, Object>>() {
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
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
	 * @param shopId
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
			Class<?> cls = Class.forName(serviceClassName);
			Constructor<?> constructor = cls.getConstructor();
			Object fieldInstance =  constructor.newInstance();
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

package com.vpu.mp.service.foundation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author lixinguo
 *
 */
public class ServiceFactory {

	static Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

	protected static Map<String, Object> services = new HashMap<String, Object>();

	/**
	 * 初始化对象服务变量
	 * 
	 * @param o
	 */
	public static void initServices(Object o) {
		Integer shopId = (Integer) Util.getObjectProperty(o, "shopId");
		if (shopId == null) {
			shopId = 0;
		}

		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			boolean ret = BaseService.class.isAssignableFrom(field.getType());
			if (ret) {
				Object service = getService(shopId, field.getType().getName());
				field.setAccessible(true);
				try {
					field.set(o, service);
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
	public static Object getService(Integer shopId, String serviceClassName) {
		String key = shopId + "_" + serviceClassName;
		if (services.containsKey(key)) {
			String message = "load exists service: " + serviceClassName + "(" + shopId + ")";
			logger.debug(message);
			return services.get(key);
		}

		logger.debug("instance service: " + serviceClassName + "(" + shopId + ")");

		try {
			Class<?> cls = Class.forName(serviceClassName);
			if (!BaseService.class.isAssignableFrom(cls)) {
				return null;
			}
			Constructor<?> constructor = cls.getConstructor();
			Object fieldInstance = constructor.newInstance();
			services.put(key, fieldInstance);

			BaseService service = (BaseService) fieldInstance;
			service.initServices(shopId);

			return fieldInstance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}

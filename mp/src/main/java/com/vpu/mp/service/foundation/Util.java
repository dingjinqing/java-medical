package com.vpu.mp.service.foundation;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.DigestUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

	static String toJSON(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	static public <T> T parseJSON(String json, Class<T> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		// 如果json中有新增的字段并且是实体类类中不存在的，不报错
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(json, valueType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	static public String md5(String string) {
		return DigestUtils.md5DigestAsHex(string.getBytes());
	}

	static public String getCleintIp(HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (ipAddress.equals("127.0.0.1")) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				}
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
																// = 15
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
		} catch (Exception e) {
			ipAddress = "";
		}
		return ipAddress;
	}

	public static Timestamp convertToTimestamp(String dateTime) {
		return Timestamp.valueOf(dateTime);
	}

	public static void initComponents(Object o) {
		Integer shopId = (Integer) getObjectProperty(o, "shopId");
		System.out.println("getProperty shopId = " + shopId);
		if (shopId == null)
			shopId = 0;

		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			boolean ret = BaseComponent.class.isAssignableFrom(field.getType());
			if (ret) {
				System.out.println("initComponents class: " + o.getClass() + ", field: " + field.getType().toString());
				field.setAccessible(true);

				try {
					String className = field.getType().getName();
					Class<?> cls = Class.forName(className);
					Constructor<?> constructor = cls.getConstructor();
					Object fieldInstance = constructor.newInstance();
					field.set(o, fieldInstance);
					BaseComponent com = (BaseComponent) field.get(o);
					com.setShopId(shopId);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
	}

	/**
	 * 获取对象的属性值
	 * 
	 * @param o
	 * @param name
	 * @return
	 */
	public static Object getObjectProperty(Object o, String name) {
		Class<?> cls = o.getClass();
		while (cls != null && !cls.getName().toLowerCase().equals("java.lang.object")) {
			try {
				Field field = cls.getDeclaredField(name);
				field.setAccessible(true);
				return field.get(o);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				cls = cls.getSuperclass();
			}
		}
		return null;
	}

	public static String getProperty(String path,String key) {
		try {
			ClassPathResource resource = new ClassPathResource(path);
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			return properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

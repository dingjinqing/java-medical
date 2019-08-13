package com.vpu.mp.service.foundation.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.exception.DataTypeException;
import org.jooq.tools.Convert;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author 新国
 *
 */
public class Util {

	final protected static String UNDEER_LINE = "_";

	public static String toJson(Object o) {
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

	public static <T> T parseJson(String json, Class<T> valueType) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static <T> T parseJson(String json, TypeReference valueTypeRef) {
		ObjectMapper mapper = new ObjectMapper();
		// 如果json中有新增的字段并且是实体类类中不存在的，不报错
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(json, valueTypeRef);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T parseResourceJson(String path, Class<T> valueType) {
		return parseJson(Util.loadResource(path), valueType);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T parseResourceJson(String path, TypeReference valueTypeRef) {
		return parseJson(Util.loadResource(path), valueTypeRef);
	}

	public static String[] mergeArray(String[] array1, String[] array2) {
		Map<String, Integer> map = new HashMap<String, Integer>(0);
		for (String str : array1) {
			map.put(str, 1);
		}
		for (String str : array2) {
			if (!map.containsKey(str)) {
				map.put(str, 1);
			}
		}
		return (String[]) map.keySet().toArray(new String[map.size()]);
	}

	public static <T> void mergeList(List<T> list1, List<T> list2) {
		for (T t : list2) {
			if (!list1.contains(t)) {
				list1.add(t);
			}
		}
	}

	public static String md5(String string) {
		return DigestUtils.md5DigestAsHex(string.getBytes());
	}

	public static String getCleintIp(HttpServletRequest request) {
		String ipAddress = null;
		String unkown = "unknown";
		String localhost = "127.0.0.1";
		String comma = ",";
		Integer maxIpLength = 15;
		try {
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (localhost.equals(ipAddress)) {
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
			if (ipAddress != null && ipAddress.length() > maxIpLength) {
				if (ipAddress.indexOf(comma) > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(comma));
				}
			}
		} catch (Exception e) {
			ipAddress = "";
		}
		return ipAddress;
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
		while (cls != null && !cls.getName().equals(Object.class.getName())) {
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
	

	public static String getProperty(String path, String key) {
		try {
			ClassPathResource resource = new ClassPathResource(path);
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			return properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String loadResource(String path) {
		try {
			ClassPathResource resource = new ClassPathResource(path);
			return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final <T> T convert(Object from, Class<? extends T> toClass, T defaultValue) {
		try {
			if(from == null) {
				return defaultValue;
			}
			T t = Convert.convert(from, toClass);
			return t == null ? defaultValue : t;
		} catch (DataTypeException e) {
			return defaultValue;
		}
	}

	public static final Integer getInteger(Object from) {
		return convert(from, Integer.class, 0);
	}

	public static Integer randomInteger(Integer min, Integer max) {
		Random rnd = new Random();
		return min + rnd.nextInt(max - min);
	}

	public static List<Part> getFilePart(HttpServletRequest request, String name) throws IOException, ServletException {
		List<Part> result = new ArrayList<Part>();
		String names = name + "[]";
		Collection<Part> parts;
		parts = request.getParts();
		for (Part part : parts) {
			if (part.getName().equals(name) || part.getName().equals(names)) {
				result.add(part);
			}
		}
		return result;
	}

	/**
	 * 转换语言
	 *
	 * @param language
	 * @param message 
	 * @param defaultMessage 缺省内容
	 * @param languageType
	 * @return
	 */
	public static String translateMessage(String language, String message, String defaultMessage, String languageType,Object ...args) {
		language = StringUtils.isBlank(language) ? "zh_CN" : language;
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasename("static/i18n/" + languageType);
		source.setDefaultEncoding("UTF-8");
		MessageSourceAccessor accessor = new MessageSourceAccessor(source);
		String[] languages = language.split(UNDEER_LINE);
		Locale locale = new Locale(languages[0], languages[1]);
		String result =  accessor.getMessage(message, defaultMessage, locale);
		return MessageFormat.format(result, args);
	}

	/**
	 * 转换语言
	 *
	 * @param language
	 * @param message
	 * @return
	 */
	public static String translateMessage(String language, String message, String languageType,Object ...args) {
		return translateMessage(language, message, message, languageType,args);
	}

	/***
	 * 下划线命名转为驼峰命名
	 *
	 * @param para
	 *
	 */
	public static String underlineToHump(String para) {
		StringBuilder result = new StringBuilder();
		String[] a = para.split(UNDEER_LINE);
		for (String s : a) {
			if (!para.contains(UNDEER_LINE)) {
				result.append(s);
				continue;
			}
			if (result.length() == 0) {
				result.append(s.toLowerCase());
			} else {
				result.append(s.substring(0, 1).toUpperCase());
				result.append(s.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	/***
	 * 驼峰命名转为下划线命名
	 *
	 * @param para
	 *
	 */
	public static String humpToUnderline(String para) {
		StringBuilder sb = new StringBuilder(para);
		int temp = 0;
		if (!para.contains(UNDEER_LINE)) {
			for (int i = 0; i < para.length(); i++) {
				if (Character.isUpperCase(para.charAt(i))) {
					sb.insert(i + temp, UNDEER_LINE);
					temp += 1;
				}
			}
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 产生uuid
	 *
	 * @return
	 */
	public static String randomId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 将字符串数组中的每一个 元素 转换为 Integer类型，转换失败直接跳过，不会抛异常
	 *
	 * @param from
	 * @return
	 */
	public static List<Integer> valueOf(String[] from) {
		if (from == null || from.length == 0) {
			return new ArrayList<Integer>(0);
		}
		ArrayList<Integer> list = new ArrayList<Integer>(from.length);
		for (int i = 0; i < from.length; i++) {
			try {
				list.add(Integer.valueOf(from[i]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 获取某一天的开始时间
	 */
	public static Timestamp getStartToday(Date date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			date = formatter.parse(formatter.format(date));
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
//			return new Timestamp(0);
		}
	}

	/**
	 * 获取给定日期之前/之后多少天的日期
	 *
	 * @param date 给定日期
	 * @param days 指定多少天之前/之后
	 * @return 返回的是后推或者前移后的日期的开始时间
	 */
	public static Timestamp getEarlyTimeStamp(Date date, int days) {
		return new Timestamp(getEarlyDate(date, days).getTime());
	}

	public static java.sql.Date getEarlySqlDate(Date date, int days) {
		return new java.sql.Date(getEarlyDate(date, days).getTime());
	}

	public static Date getEarlyDate(Date date, int days) {
		date = getStartToday(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		/** 把日期往后推或者往前移；正数往后推,负数往前移 */
		calendar.add(Calendar.DATE, days);
		/** 这个时间就是变动后的结果 */
		date = calendar.getTime();
		return date;
	}

	/**
	 * 将map 以String方式返回
	 *
	 * @param map
	 * @return
	 */
	public static String convertMapToString(Map<?, ?> map) {
		return map.keySet().stream().map(key -> key + " = " + map.get(key)).collect(Collectors.joining(", ", "{", "}"));
	}


	public static <T extends Collection<?>> boolean isEmpty(T t) {
		return t == null || t.isEmpty();
	}


	

    /**
     * List 转 String
     */
    public static String listToString(List<Integer> rewardCouponIds) {
        return rewardCouponIds.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    /**
     * String 转 List
     */
    public static List<Integer> stringToList(String idString) {
        if (StringUtils.isEmpty(idString)) {
            return Collections.emptyList();
        }
        return Arrays.stream(idString.split(",")).map(Integer::valueOf).collect(Collectors.toList());
    }

    /**
     * 当前时间戳
     */
    public static Timestamp currentTimeStamp() {
        return new Timestamp(new java.util.Date().getTime());
    }
}
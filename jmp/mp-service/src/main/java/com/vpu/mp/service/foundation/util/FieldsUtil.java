package com.vpu.mp.service.foundation.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jooq.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将from类中的非null值赋值到to,可以自己赋值自己，或者类赋值到相应的Record类，两个类里字段相同
 * ，有get/set方法就行。方便后续插入
 * 
 * @author zhaojianqiang
 *
 */
public class FieldsUtil {
	private static Logger log = LoggerFactory.getLogger(FieldsUtil.class);
	/** 下划线转驼峰正则表达式的Pattern */
	private static Pattern underLineToCamel = Pattern.compile("_(\\w)");
	/**
	 * 对象相同属性赋值，不含null值属性
	 * @param from
	 * @param to
	 * @return
	 */
	public static Object assignNotNull(Object from, Object to) {
		return assignNotNull(from,to,null);
	}
	/**
	 * 对象相同属性赋值，不含null值属性
	 * @param from 来源对象
	 * @param to 赋值对象
	 * @param onlyPropNames 仅赋值的属性名字
	 * @return
	 */
	public static Object assignNotNull(Object from, Object to,List<String> onlyPropNames) {
		Class<?> clazz = from.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Class<?> clazz2 = to.getClass();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				String uid = "serialVersionUID";
				if (!field.getName().equals(uid)) {
					if(onlyPropNames != null && !onlyPropNames.contains(field.getName())) {
						continue;
					}
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
					// 获得读方法
					Method rm = pd.getReadMethod();
					Object o = rm.invoke(from);
					if (o != null) {
						PropertyDescriptor pd2 = new PropertyDescriptor(field.getName(), clazz2);
						// 获得写方法
						Method wm = pd2.getWriteMethod();
						wm.invoke(to, o);
					}
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		return to;
	}
	
	/**
	 * 	通过FieldName获取FieldValue（支持fieldName为下划线命名；支持基类属性）
	 * @param <T>
	 * @param fieldName 属性名
	 * @param object 对象
	 * @param clz 返回值类型
	 * @return 属性值（null）
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValueByFieldName(String fieldName , Object obj , Class<T> clz) {
		if(StringUtils.isBlank(fieldName)) {
			return null;
		}
		fieldName = underLineToCamel(fieldName);
		Class<?> objClass = obj.getClass();
		Field field = null;
		T t = null;
		for(;objClass != Object.class ; objClass = objClass.getSuperclass()) {
			try {
				field = objClass.getDeclaredField(fieldName);
				if(field != null) {
					field.setAccessible(true);
					t = (T)(field.get(obj));
					break;
				}
			} catch (Exception e) {
			}
		}
		return t;
	}
	/**
	 * 	下划线命名转驼峰
	 * @param str
	 * @return
	 */
	public static String underLineToCamel(String str) {
		Matcher matcher = underLineToCamel.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 打印对象值
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj) {
		if (obj==null){
			return "";
		}
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		sb.append(obj.getClass().getSimpleName()).append("{");
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				sb.append(field.getName()).append(":").append(field.get(obj)).append(",");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return sb.deleteCharAt(sb.length() - 1).append("}").toString();
	}
}

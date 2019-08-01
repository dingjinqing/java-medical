package com.vpu.mp.service.foundation.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

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

}

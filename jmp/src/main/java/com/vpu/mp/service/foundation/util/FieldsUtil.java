package com.vpu.mp.service.foundation.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 将object1类中的非null值赋值到object2,可以自己赋值自己，或者类赋值到相应的Record类，两个类里字段相同
 * ，有get/set方法就行。方便后续插入
 * 
 * @author zhaojianqiang
 *
 */
public class FieldsUtil {
	/**
	 * 
	 * @param object1
	 * @param object2
	 * @return
	 */
	public static Object assignNotNull(Object object1, Object object2) {
		Class<?> clazz = object1.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Class<?> clazz2 = object2.getClass();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				String uid = "serialVersionUID";
				if (!field.getName().equals(uid)) {
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
					// 获得读方法
					Method rm = pd.getReadMethod();
					Object o = rm.invoke(object1);
					if (o != null) {
						PropertyDescriptor pd2 = new PropertyDescriptor(field.getName(), clazz2);
						// 获得写方法
						Method wm = pd2.getWriteMethod();
						wm.invoke(object2, o);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return object2;
	}

}

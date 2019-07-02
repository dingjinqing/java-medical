package com.vpu.mp.service.foundation;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.vpu.mp.db.main.tables.records.ShopAccountRecord;

/**
 * 将object1类中的非null值赋值到object2 可以自己赋值自己，或者类赋值到相应的Record类，方便后续插入
 * 
 * @author zhaojianqiang
 *
 */
public class MyReflectUtil {
	/**
	 * 
	 * @param object1
	 * @param object2
	 * @return
	 */
	public static Object removeNull(Object object1, Object object2) {
		Class<?> clazz = object1.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Class<?> clazz2 = object2.getClass();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				if (!field.getName().equals("serialVersionUID")) {
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
					// 获得读方法
					Method rM = pd.getReadMethod();
					Object o = rM.invoke(object1);
					if (o != null) {
						PropertyDescriptor pd2 = new PropertyDescriptor(field.getName(), clazz2);
						// 获得写方法
						Method wM = pd2.getWriteMethod();
						wM.invoke(object2, o);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return object2;
	}

}

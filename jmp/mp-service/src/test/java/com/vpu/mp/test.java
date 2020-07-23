package com.vpu.mp;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

/**
* @author 黄壮壮
* @Date: 2019年10月14日
* @Description:
*/
@Slf4j
public class test {
	public static void main(String[] args) {
		String value="-11.00";
		int indexOf = value.indexOf("-");
		String subLimit = subLimit("name", value, indexOf);
		System.out.println(subLimit);
	}
	
	private static String subLimit(String name,String value,int num) {
		if (value.length() > num) {
			log.info("类型：{}，原来值：{}；长度要求:{}",name,value,num);
			value = value.substring(0, num);
			log.info("类型：{}，长度要求:{}，新值：{}；",name,num,value);
		}
		return value;
	}

	@Test
	private void test(){

	}
	public static <T> T cloneByStream(T obj) {
		if (null != obj && obj instanceof Serializable) {
			FastByteArrayOutputStream byteOut = new FastByteArrayOutputStream();
			ObjectOutputStream out = null;

			T var4;
			try {
				out = new ObjectOutputStream(byteOut);
				out.writeObject(obj);
				out.flush();
				ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
				var4 = (T) in.readObject();
			} catch (Exception var8) {
				throw new UtilException(var8);
			} finally {
				IoUtil.close(out);
			}
			return  var4;
		} else {
			return null;
		}
	}
}

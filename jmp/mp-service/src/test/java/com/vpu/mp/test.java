package com.vpu.mp;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import com.vpu.mp.service.pojo.shop.member.account.MemberCard;
import org.junit.Test;

/**
* @author 黄壮壮
* @Date: 2019年10月14日
* @Description:
*/
public class test {
	public static void main(String[] args) {
		List<MemberCard> list = new ArrayList<>();
		list.add(new MemberCard(1));
		System.out.println("大小"+list.size());
		for(MemberCard car:list) {
			System.out.println(car.toString());
		}
		List<Integer> cardNos = list.stream().map(MemberCard::getId).collect(Collectors.toList());
		for(Integer cardNo:cardNos) {
			System.out.println(cardNo);
		}
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

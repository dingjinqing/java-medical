package com.vpu.mp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vpu.mp.service.pojo.shop.member.account.MemberCard;

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

}

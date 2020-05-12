package com.vpu.mp.service.pojo.shop.member.card.create;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vpu.mp.service.foundation.util.Util;

import lombok.Data;
/**
 * 	卡权益具体信息
 * @author 黄壮壮
 *
 */
@Data
public class CardRight {
	/**
	 * 	权限名称
	 */
	private String crightName;
	
	/**
	 * 	权益图标url
	 */
	private String crightImage;
	
	/**
	 * 	权益说明
	 */
	private String crightContent;
	
	public static void main(String[] args) throws JsonProcessingException {
		CardRight demo = new CardRight();
		demo.setCrightContent("Hello World");
		String json = Util.toJson(demo);
		System.out.println(json);
	}
}

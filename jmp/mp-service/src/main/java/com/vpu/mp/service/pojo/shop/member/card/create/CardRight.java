package com.vpu.mp.service.pojo.shop.member.card.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vpu.mp.service.foundation.util.Util;

import lombok.Data;
/**
 * 	卡权益具体信息
 * @author Q10Viking
 *
 */
@Data
public class CardRight {
	/**
	 * 	权限名称
	 */
	@JsonProperty("cright_name")
	private String crightName;
	
	/**
	 * 	权益图标url
	 */
	@JsonProperty("cright_image")
	private String crightImage;
	
	/**
	 * 	权益说明
	 */
	@JsonProperty("cright_content")
	private String crightContent;
	
	public static void main(String[] args) throws JsonProcessingException {
		CardRight demo = new CardRight();
		demo.setCrightContent("Hello World");
		String json = Util.toJson(demo);
		System.out.println(json);
	}
}

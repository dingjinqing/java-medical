package com.vpu.mp.service.pojo.shop.member.card.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vpu.mp.service.foundation.util.Util;

import lombok.Data;

/**
 * 	卡自定义权益
 * @author 黄壮壮
 *
 */
@Data
public class CardCustomRights {
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
		CardCustomRights demo = new CardCustomRights();
		demo.setCrightContent("Hello World");
		String json = Util.toJson(demo);
		System.out.println(json);
	}
}

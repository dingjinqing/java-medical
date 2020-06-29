package com.vpu.jmd.service.shop.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ShopTypeEnum {
	V1("v1","体验版"),
	V2("v2","基础版"),
	V3("v3","高级版"),
	V4("v4","旗舰版");

	private String type;
	private String value;

	public static String getName(String type) {
		ShopTypeEnum[] values = values();
		for (ShopTypeEnum item : values) {
			if (item.getType().equals(type)) {
				return item.getValue();
			}
		}
		return null;
	}

}

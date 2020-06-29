package com.vpu.jmd.service.shop.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ShopFlagEnum {
	STORE((byte) 0, "店家"),
	OUPAI((byte) 1, "欧派"),
	HI_SHOPPING((byte) 2, "嗨购");

	private Byte type;
	private String value;


	public static String getName(Byte type) {
		ShopFlagEnum[] values = values();
		for (ShopFlagEnum item : values) {
			if(item.getType().equals(type)) {
				return item.getValue();
			}
		}
		return null;
	}

}

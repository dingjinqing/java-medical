package com.vpu.jmd.service.shop.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SubMerchantEnum {
	WX_PAY((byte) 0, "微信支付"), WX_SON_PAY((byte) 1, "微信子商户支付"), WX_CONNECT_PAY((byte) 2, "通联支付"),
	WX_INTE_PAY((byte) 3, "微信国际支付"), WX_HAIKE_PAY((byte) 4, "海科微信支付");

	private Byte type;
	private String value;

	public static String getName(Byte type) {
		SubMerchantEnum[] values = values();
		for (SubMerchantEnum item : values) {
			if (item.getType().equals(type)) {
				return item.getValue();
			}
		}
		return null;
	}

}

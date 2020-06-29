package com.vpu.jmd.service.shop.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OpenAccountTypeEnum {
	DIRECT_PURCHASE((byte) 1, "直接购买"),
	TRIAL_TRANSFER_PAYMENT((byte) 2, "试用转付费"),
	Gift((byte) 3, "赠送"),
	GIFT_TRANSFER_PAYMENT((byte) 4, "赠送转付费"),
	Trial((byte) 5, "试用"),
	Other((byte) 6, "其他");
	private Byte type;
	private String value;


	public static String getName(Byte type) {
		com.vpu.mp.service.pojo.saas.shop.OpenAccountTypeEnum[] values = values();
		for (com.vpu.mp.service.pojo.saas.shop.OpenAccountTypeEnum item : values) {
			if(item.getType().equals(type)) {
				return item.getValue();
			}
		}
		return null;
	}

}

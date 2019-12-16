package com.vpu.mp.service.pojo.shop.member.account;

import lombok.Data;

@Data
public class UserCardGetParam {
	private Byte getType;
	/** card info */
	private CardInfo cardInfo;
}

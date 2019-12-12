package com.vpu.mp.service.pojo.shop.member.ucard;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReceiveCardParam {
	@NotNull
	private Integer cardId;
	private String cardNo;       
	private String cardPwd;    
	private String code;    
	private Integer userId;
}

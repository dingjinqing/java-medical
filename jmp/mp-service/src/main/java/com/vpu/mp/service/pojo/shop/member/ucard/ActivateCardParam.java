package com.vpu.mp.service.pojo.shop.member.ucard;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivateCardParam {
	@NotNull
	private String cardNo;
	// 1 为 设置激活卡信息
	private Byte isSetting;
	private Integer userId;
}

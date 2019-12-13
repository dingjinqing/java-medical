package com.vpu.mp.service.pojo.shop.member.ucard;

import javax.validation.constraints.NotNull;

import lombok.Data;
/** 
 * @author 黄壮壮
 * 	设置默认会员卡
 */
@Data
public class DefaultCardParam {
	/**
	 * 	会员卡号
	 */
	@NotNull
	private String cardNo;
	private Integer userId;
}

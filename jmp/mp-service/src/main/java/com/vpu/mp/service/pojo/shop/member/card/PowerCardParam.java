package com.vpu.mp.service.pojo.shop.member.card;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年8月7日
* @Description: 禁用启动会员卡
*/
@Data
public class PowerCardParam {
	/** 会员卡id */
	private Integer id;
	
	/** 是否使用 1:使用中，2:停止使用 */
	private Byte flag;
}

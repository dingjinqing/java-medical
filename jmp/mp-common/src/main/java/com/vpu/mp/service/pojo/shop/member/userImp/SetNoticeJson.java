package com.vpu.mp.service.pojo.shop.member.userImp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SetNoticeJson {
	/** 通知说明 */
	private String explain;
	/** 积分 */
	private String score;
	/** 优惠券Id */
	@JsonProperty(value = "mrking_voucher_id")
	private String mrkingVoucherId;

}

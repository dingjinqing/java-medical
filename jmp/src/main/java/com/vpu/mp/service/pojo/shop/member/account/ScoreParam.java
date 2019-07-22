package com.vpu.mp.service.pojo.shop.member.account;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.JsonResultMessage;

import lombok.Data;

/**
 * @author 黄壮壮
 * 2019-07-19 14:38
 */
@Data
public class ScoreParam {
	private String remark;
	
	@NotNull(message=JsonResultMessage.MSG_MEMBER_SCORE_NOT_NULL)
	private Integer[] userId;
	
	@Min(message=JsonResultMessage.MSG_MEMBER_SCORE_NOT_BE_NEGATIVE, value = 0)
	@NotNull(message=JsonResultMessage.MSG_MEMBER_SCORE_NOT_NULL)
	private Integer score;
	
	@Min(message=JsonResultMessage.MSG_MEMBER_SCORE_NOT_BE_NEGATIVE, value = 0)
	@NotNull(message=JsonResultMessage.MSG_MEMBER_SCORE_NOT_NULL)
	private Integer scoreDis;
	private String orderSn; 
	
}

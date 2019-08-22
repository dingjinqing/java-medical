package com.vpu.mp.service.pojo.shop.member.account;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.data.JsonResultMessage;

import lombok.Data;

/**
 * @author 黄壮壮
 * 2019-07-19 14:38
 */
@Data
public class ScoreParam {
	/** 备注 */
	private String remark;
	
	@NotNull(message=JsonResultMessage.MSG_MEMBER_SCORE_NOT_NULL)
	private Integer[] userId;
	
	/** 积分变动数额 */
	@Min(message=JsonResultMessage.MSG_MEMBER_SCORE_NOT_BE_NEGATIVE, value = 0)
	@NotNull(message=JsonResultMessage.MSG_MEMBER_SCORE_NOT_NULL)
	private Integer score;
	
	/** 当前积分  当批量操作时，为所选用户中的最低积分*/
	@Min(message=JsonResultMessage.MSG_MEMBER_SCORE_NOT_BE_NEGATIVE, value = 0)
	@NotNull(message=JsonResultMessage.MSG_MEMBER_SCORE_NOT_NULL)
	private Integer scoreDis;
	
	/** 订单编号 */
	private String orderSn; 
	
}

package com.vpu.mp.service.pojo.shop.member.data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.data.JsonResultMessage;

import lombok.Getter;
import lombok.Setter;

/**
* @author 黄壮壮
* @Date: 2019年8月26日
* @Description: 会员积分数据
*/
@Setter
@Getter
public class ScoreData extends BasicData {
	/** 用户Id */
	protected Integer userId;
	/** 操作员id */
	protected Integer adminUser;
	/** 备注 */
	protected String remark;
	/** 交易类型 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum } */
	protected Byte tradeType;
	/** 资金流向 {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum }  */
	protected Byte tradeFlow;
	/** -积分变动数额 */
	private Integer score;
	
	/** -当前积分 -当批量操作时，为所选用户中的最低积分*/
	private Integer scoreDis;
	
	/** -订单编号 */
	private String orderSn; 
	
	/** -积分状态 {@link com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant} */
	private Byte scoreStatus;
}

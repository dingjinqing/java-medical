package com.vpu.mp.service.pojo.shop.member.data;

import lombok.Getter;
import lombok.Setter;

/**
* @author 黄壮壮
* @Date: 2019年8月26日
* @Description: 余额，积分，会员卡余额记录公共参数
*/
@Setter
@Getter
public abstract class BasicData {
	/** 用户Id */
	protected Integer userId;
	/** 操作员id */
	protected Integer adminUser;
	/** 备注 */
	protected String remark;
	/** 交易类型 */
	protected Byte tradeType;
	/** 资金流向 */
	protected Byte tradeFlow;
}

package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;
import java.util.List;

import com.vpu.mp.common.foundation.util.PageResult;

import lombok.Data;

/**
 * 分销员已邀请用户列表出参类
 * @author 常乐
 * 2019年8月8日
 */
@Data
public class DistributorInvitedListVo {

	/**累计获得佣金数*/
	private BigDecimal totalGetFanliMoney;
    /**邀请用户信息*/
	private PageResult<InviteUserInfoVo> InviteUserInfo;


}

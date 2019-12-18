package com.vpu.mp.service.pojo.shop.distribution;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.foundation.util.PageResult;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class DistributorWithdrawSumDetailVo {
	/**
	 * 当前用户提现列表
	 */
	private PageResult<DistributorWithdrawListVo> data;
	/**
	 * 已提现金额
	 */
	@JsonProperty("done")
	private BigDecimal withdrawCrash;
}

package com.vpu.mp.service.pojo.wxapp.coupon;

import com.vpu.mp.common.foundation.util.PageResult;

import lombok.Data;

/**
 * 优惠券按状态出参
 * @author 常乐
 * 2019年9月25日
 */
@Data
public class AvailCouponListVo {
	public Integer unusedNum;
	public Integer usedNum;
	public Integer expiredNum;
	public PageResult<AvailCouponVo> couponList;
	
}

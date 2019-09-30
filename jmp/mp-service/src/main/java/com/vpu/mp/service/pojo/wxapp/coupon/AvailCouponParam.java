package com.vpu.mp.service.pojo.wxapp.coupon;

import lombok.Data;

/**
 * 用户优惠券列表如参
 * @author 常乐
 * 2019年9月25日
 */
@Data
public class AvailCouponParam {
	/**
	 * 分页id
	 */
	public Integer pageId;
	
	/**
	 * 当前用户ID
	 * 暂时指定1
	 */
	public Integer userId=1;
}

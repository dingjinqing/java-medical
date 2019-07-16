package com.vpu.mp.service.pojo.shop.coupon;

import lombok.Data;

/**
 * 优惠券列表入参
 * @author 常乐
 * 2019年7月16日
 */
@Data
public class CouponListParam {
	private String actName;
	
	/**
     * 	分页信息
     */
    private int currentPage;
    private int pageRows;
}

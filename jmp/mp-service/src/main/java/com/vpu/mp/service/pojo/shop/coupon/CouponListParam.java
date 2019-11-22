package com.vpu.mp.service.pojo.shop.coupon;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 优惠券列表入参
 * @author 常乐
 * 2019年7月16日
 */
@Getter
@Setter
public class CouponListParam {
	private Integer nav;
	private String actName;

	/**
     * 	分页信息
     */
    private int currentPage;
    private int pageRows;
}

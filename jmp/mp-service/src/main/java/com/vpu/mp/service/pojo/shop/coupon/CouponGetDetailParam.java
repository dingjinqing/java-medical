package com.vpu.mp.service.pojo.shop.coupon;

import lombok.Data;

/**
 * 优惠券领取明细入参
 * @author 常乐
 * 2019年7月16日
 */
@Data
public class CouponGetDetailParam {
	private Integer id;
	private String userName;
	private String mobile;
	private Byte isUsed;
	
	/**
     * 	分页信息
     */
    private int currentPage;
    private int pageRows;
}

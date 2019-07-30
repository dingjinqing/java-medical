package com.vpu.mp.service.pojo.shop.coupon.give;

import lombok.Data;

/**
 * 定向发放优惠券
 * @author liangchen
 * @date 2019年7月29日
 */

@Data
public class CouponGiveListParam {
	private String actName;
	/**
     * 	分页信息
     */
    private int currentPage;
    private int pageRows;
}

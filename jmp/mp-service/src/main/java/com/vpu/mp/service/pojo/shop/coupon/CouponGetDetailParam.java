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
	/**优惠券类型：0：普通优惠券；1：分裂优惠券*/
	private Integer couponType;
	/**用户昵称*/
	private String userName;
	/**手机号*/
	private String mobile;
	/**使用状态 1 未使用 2 使用 3 过期 4 废除 */
	private Byte isUsed;

	/**
     * 	分页信息
     */
    private int currentPage;
    private int pageRows;
}

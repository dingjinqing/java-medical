package com.vpu.mp.service.pojo.shop.coupon.give;

import lombok.Data;

/**
 * 优惠券弹窗
 * @author liangchen
 * @date 2019年8月1日
 */
@Data
public class CouponGivePopParam {
	/** 优惠券名称 */
	private String actName;
	/** 分页信息 */
	private int currentPage;
    private int pageRows;
}

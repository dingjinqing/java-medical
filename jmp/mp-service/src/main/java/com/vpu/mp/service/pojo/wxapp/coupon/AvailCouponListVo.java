package com.vpu.mp.service.pojo.wxapp.coupon;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 优惠券按状态出参
 * @author 常乐
 * 2019年9月25日
 */
@Data
public class AvailCouponListVo {
	/**
	 * 未使用
	 */
	public List<AvailCouponVo> unused = new ArrayList<AvailCouponVo>();
	/**
	 * 已使用
	 */
	public List<AvailCouponVo> used = new ArrayList<AvailCouponVo>();
	/**
	 * 已过期
	 */
	public List<AvailCouponVo> expired = new ArrayList<AvailCouponVo>();
	
	public Integer unusedNum;
	public Integer usedNum;
	public Integer expiredNum;
	
}

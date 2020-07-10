package com.vpu.mp.service.pojo.shop.member.userImp;

import java.util.List;

import com.vpu.mp.service.pojo.shop.coupon.CouponView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetNoticeJsonVo {
	/** 通知说明 */
	private String explain;
	/** 积分 */
	private String score;
	/** 优惠券Id */
	private String mrkingVoucherId;
	private List<CouponView> mrkingVoucherList;
}

package com.vpu.mp.service.pojo.shop.member.account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *	 会员卡的优惠券信息
 * @author 黄壮壮
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCardCoupon {
	/**
	 * 	优惠券使用条件范围
	 * 	全部商品 | 部分商品
	 */
	private String couponCondition;
	/**
	 * 	优惠券使用时间描述
	 */
	private String expireTime;
	
	/**
	 * 	优惠券使用条件限制
	 *	如： 无门槛 | 满...使用
	 */
	private String useConsumeRestrict;
}

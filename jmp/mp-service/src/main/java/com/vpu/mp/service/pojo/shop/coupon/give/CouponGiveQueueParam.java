package com.vpu.mp.service.pojo.shop.coupon.give;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
  * 队列入参
 * @author liangchen
 * @date 2019年8月6日
 */
@Getter
@Setter
@NoArgsConstructor
public class CouponGiveQueueParam {
	public CouponGiveQueueParam(Integer shopId, List<Integer> userIds, Integer actId, String[] couponArray,Byte accessMode,Byte getSource) {
		super();
		this.shopId = shopId;
		this.userIds = userIds;
		this.actId = actId;
		this.couponArray = couponArray;
        this.accessMode = accessMode;
        this.getSource = getSource;
	}
    public CouponGiveQueueParam( List<Integer> userIds, Integer actId, String[] couponArray,Byte accessMode,Byte getSource) {
        super();
        this.userIds = userIds;
        this.actId = actId;
        this.couponArray = couponArray;
        this.accessMode = accessMode;
        this.getSource = getSource;
    }
	/** 店铺id 定时任务需要传 非定时任务不传*/
	private Integer shopId;
    /** 用户id */
	private List<Integer> userIds;
    /** 发放活动id 若来源为领取则传0 */
	private Integer actId;
    /** 优惠券id */
	private String[] couponArray;
    /** 获取方式，0：发放，1：领取 */
	private Byte accessMode;
    /** 1表单送券2支付送券3活动送券4积分兑换5直接领取6分裂优惠券7crm领券8幸运大抽奖9定向发券 */
	private Byte getSource;
    /** 未使用队列消息时不需要传 */
	@JsonProperty(access = Access.WRITE_ONLY)
    private Integer taskJobId;
}

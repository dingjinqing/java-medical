package com.vpu.mp.service.pojo.shop.coupon.give;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * 队列入参
 * @author liangchen
 * @date 2019年8月6日
 */
@Data
@NoArgsConstructor
public class CouponGiveQueueParam {
	public CouponGiveQueueParam(Integer shopId, List<Integer> userIds, Integer actId, String[] couponArray) {
		super();
		this.shopId = shopId;
		this.userIds = userIds;
		this.actId = actId;
		this.couponArray = couponArray;
	}
	private Integer shopId;
	private List<Integer> userIds;
	private Integer actId;
	private String[] couponArray;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer taskJobId;
}

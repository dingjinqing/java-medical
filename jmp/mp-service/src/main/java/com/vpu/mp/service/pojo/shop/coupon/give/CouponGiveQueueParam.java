package com.vpu.mp.service.pojo.shop.coupon.give;
import java.util.List;

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
@AllArgsConstructor
public class CouponGiveQueueParam {
	private Integer shopId;
	private List<Integer> userIds;
	private Integer actId;
	private String[] couponArray;
}

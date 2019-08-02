package com.vpu.mp.service.pojo.shop.coupon.give;
 
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * 发放优惠券
 * @author liangchen
 * @date 2019年7月31日
 */

@Data
public class CouponGiveGrantInfoParam {
	@JsonProperty(value = "custom_box")
	private Integer 	customBox;
	@JsonProperty(value = "point_start_time")
	private String 		pointStartTime;
	@JsonProperty(value = "point_end_time")
	private String 		pointEndTme;
	@JsonProperty(value = "cart_box")
	private Integer 	cartBox;
	@JsonProperty(value = "card_box")
	private Integer 	cardBox;
	@JsonProperty(value = "tag_box")
	private Integer 	tagBox;
	@JsonProperty(value = "goods_box")
	private Integer 	goodsBox;
	@JsonProperty(value = "goods_ids")
	private String 		goodsIds;
	@JsonProperty(value = "member_box")
	private Integer 	memberBox;
	@JsonProperty(value = "coupon_ids")
	private String		couponIds;
	
}

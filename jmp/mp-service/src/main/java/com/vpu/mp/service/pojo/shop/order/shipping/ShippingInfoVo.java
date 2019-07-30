package com.vpu.mp.service.pojo.shop.order.shipping;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ShippingInfoVo {
	private String orderSn;
	/**物流单号*/
	private String shippingNo;
	/**物流名称*/
	private String shippingName;
	/**发货时间*/
	private Timestamp shippingTime;
	/**确认收货时间*/
	private Timestamp confirmTime;
	/**商品信息*/
	private List<Goods> Goods;
	@Data
	@AllArgsConstructor
	public static class Goods{
		/**id,方便修改物流单号*/
		private Integer recId;
		private String goodsName;
		/**属性（规格）*/
		private String goodsAttr;
		/**发货数量*/
		private String sendNumber;
	}
	/*
	 * 以下属性不参与pojo->json
	 */
	/**id,方便修改物流单号*/
	@JsonIgnore
	private Integer recId;
	@JsonIgnore
	private String goodsName;
	/**属性（规格）*/
	@JsonIgnore
	private String goodsAttr;
	/**发货数量*/
	@JsonIgnore
	private String sendNumber;
	@Override
	public boolean equals(Object obj) {
		return ((ShippingInfoVo)obj).getShippingNo().equals(shippingNo) ? true : false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Goods == null) ? 0 : Goods.hashCode());
		result = prime * result + ((confirmTime == null) ? 0 : confirmTime.hashCode());
		result = prime * result + ((goodsAttr == null) ? 0 : goodsAttr.hashCode());
		result = prime * result + ((goodsName == null) ? 0 : goodsName.hashCode());
		result = prime * result + ((orderSn == null) ? 0 : orderSn.hashCode());
		result = prime * result + ((recId == null) ? 0 : recId.hashCode());
		result = prime * result + ((sendNumber == null) ? 0 : sendNumber.hashCode());
		result = prime * result + ((shippingName == null) ? 0 : shippingName.hashCode());
		result = prime * result + ((shippingNo == null) ? 0 : shippingNo.hashCode());
		result = prime * result + ((shippingTime == null) ? 0 : shippingTime.hashCode());
		return result;
	}
}

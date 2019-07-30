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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShippingInfoVo other = (ShippingInfoVo) obj;
		if (Goods == null) {
			if (other.Goods != null)
				return false;
		} else if (!Goods.equals(other.Goods))
			return false;
		if (confirmTime == null) {
			if (other.confirmTime != null)
				return false;
		} else if (!confirmTime.equals(other.confirmTime))
			return false;
		if (goodsAttr == null) {
			if (other.goodsAttr != null)
				return false;
		} else if (!goodsAttr.equals(other.goodsAttr))
			return false;
		if (goodsName == null) {
			if (other.goodsName != null)
				return false;
		} else if (!goodsName.equals(other.goodsName))
			return false;
		if (orderSn == null) {
			if (other.orderSn != null)
				return false;
		} else if (!orderSn.equals(other.orderSn))
			return false;
		if (recId == null) {
			if (other.recId != null)
				return false;
		} else if (!recId.equals(other.recId))
			return false;
		if (sendNumber == null) {
			if (other.sendNumber != null)
				return false;
		} else if (!sendNumber.equals(other.sendNumber))
			return false;
		if (shippingName == null) {
			if (other.shippingName != null)
				return false;
		} else if (!shippingName.equals(other.shippingName))
			return false;
		if (shippingNo == null) {
			if (other.shippingNo != null)
				return false;
		} else if (!shippingNo.equals(other.shippingNo))
			return false;
		if (shippingTime == null) {
			if (other.shippingTime != null)
				return false;
		} else if (!shippingTime.equals(other.shippingTime))
			return false;
		return true;
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

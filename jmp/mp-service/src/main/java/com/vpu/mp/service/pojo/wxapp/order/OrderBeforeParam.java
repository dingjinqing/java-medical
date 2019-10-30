package com.vpu.mp.service.pojo.wxapp.order;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.order.write.operate.AbstractOrderOperateQueryParam;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author 王帅
 *
 */
@Getter
@Setter
public class OrderBeforeParam extends AbstractOrderOperateQueryParam{
	private Integer addressId;
	private List<Goods> goods;
	private Integer storeId;
	private String memberCardNo;
	@JsonIgnore
	/**方便查找*/
	private Map<Integer, Goods> goodsMap;
	/**
	 * 商品参数
	 * @author 王帅
	 *
	 */
	@Getter
	@Setter
	public static class Goods{
		@NotNull
		private Integer goodsId;
		private BigDecimal goodsPrice;
		/**购买数量*/
		@NotNull(message = JsonResultMessage.MSG_ORDER_ORDERSN_NOT_NULL)
		private Integer goodsNumber;
		@NotNull
		private Integer productId;
		/**以下为后台产生逻辑值initGoods*/
		private Integer straId;
		private Integer purchasePriceId;
		private Integer purchasePriceRuleId;
		private String promoteInfo;
		/**以下为后台产生逻辑值directPurchase*/
		private BigDecimal productPrice;
		private Integer productNumbers;
		private Byte goodsType;
		private Byte isFirstSpecial;
		/**方便计算*/
		@JsonIgnore
		private GoodsSpecProductRecord productInfo;
	}
	
	/**
	 * 获取当前购买商品ids
	 * @return
	 */
	public List<Integer> getGoodsIds() {
		return goods == null ? Collections.emptyList() : goods.stream().map(Goods::getGoodsId).collect(Collectors.toList());
	}
	
	/**
	 * 获取当前购买商品规格ids
	 * @return
	 */
	public List<Integer> getProductIds() {
		return goods == null ? Collections.emptyList() : goods.stream().map(Goods::getProductId).collect(Collectors.toList());
	}
	
	/**
	 * 获取goodsMap
	 * @return k->proId,v->goods
	 */
	public Map<Integer, Goods> getGoodsMap(){
		if(goods == null) {
			return Collections.emptyMap();
		}else if(goodsMap != null){
			return goodsMap;
		}else {
			//重复key报错
			return goods.stream().collect(Collectors.toMap(Goods::getProductId, Function.identity()));
		}
	}
}

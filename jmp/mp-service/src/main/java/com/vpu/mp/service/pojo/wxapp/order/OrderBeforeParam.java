package com.vpu.mp.service.pojo.wxapp.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.order.write.operate.AbstractOrderOperateQueryParam;
import com.vpu.mp.service.pojo.wxapp.cart.activity.OrderCartProductBo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.validated.CreateOrderValidatedGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author 王帅
 *
 */
@Getter
@Setter
@ToString
public class OrderBeforeParam extends AbstractOrderOperateQueryParam{

    /** 指定本次结算所参加的唯一营销活动类型 {@link com.vpu.mp.service.foundation.data.BaseConstant} 下的ACTIVITY_TYPE**/
    private Byte activityType;
    /** 指定本次结算所参加的唯一营销活动类型 ID */
    private Integer activityId;
    @NotNull(groups = {CreateOrderValidatedGroup.class}, message = JsonResultMessage.MSG_ORDER_ADDRESS_NO_NULL)
	private Integer addressId;
    @Valid
	private List<Goods> goods;
    @NotNull(groups = {CreateOrderValidatedGroup.class}, message = JsonResultMessage.MSG_ORDER_DELIVER_TYPE_NO_NULL)
    private Byte deliverType;
    /**购物车标记*/
    private Byte isCart;
	private Integer storeId;
    /**
     * 0:默认选第一张；null：不选；其他：卡号
     */
	private String memberCardNo;
    /**
     * 0:默认选第一张；null：不选；其他：卡号
     */
    private String couponSn;
    /**积分抵扣*/
    private Integer scoreDiscount;
    /**余额抵扣金额*/
    private BigDecimal balance;
    /**会员卡抵扣金额*/
    private BigDecimal cardBalance;
    /**支付方式*/
    @NotNull(groups = {CreateOrderValidatedGroup.class}, message = JsonResultMessage.MSG_ORDER_AMOUNT_NO_NULL)
    private Byte orderPayWay;
	@JsonIgnore
	/**方便查找*/
	private Map<Integer, Goods> goodsMap;
    @JsonIgnore
    /**方便查找*/
    private List<OrderGoodsBo> bos;
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
		@NotNull(message = JsonResultMessage.MSG_ORDER_GOODS_NO_ZERO)
		private Integer goodsNumber;
		@NotNull
		private Integer productId;
		/**促销折扣均摊到每件商品的折扣*/
		private BigDecimal perDiscount;
        /**营销活动类型*/
        private Byte goodsPriceAction;
		/**以下为后台产生逻辑值initGoods*/
		private Integer straId;
		private Integer purchasePriceId;
		private Integer purchasePriceRuleId;
		private String promoteInfo;
		/**以下为后台产生逻辑值directPurchase*/
        /** 规格价 */
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private BigDecimal productPrice;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private Integer firstSpecialId;
		/**方便计算*/
		@JsonIgnore
		private GoodsSpecProductRecord productInfo;
        @JsonIgnore
        private GoodsRecord goodsInfo;
	}

    /**
     * 获取商品计算首单特惠活动。。。
     */
    public List<OrderCartProductBo.OrderCartProduct> getOrderCartProductBo(){
        List<OrderCartProductBo.OrderCartProduct> result = new ArrayList<>(goods.size());
        goods.forEach(x->{
            result.add(new OrderCartProductBo.OrderCartProduct(x.productId, x.goodsNumber));
        });
        return result;
    }

	/**
	 * 获取当前购买商品ids
	 * @return
	 */
	public List<Integer> getGoodsIds() {
		return goods == null ? Collections.emptyList() : goods.stream().map(Goods::getGoodsId).distinct().collect(Collectors.toList());
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

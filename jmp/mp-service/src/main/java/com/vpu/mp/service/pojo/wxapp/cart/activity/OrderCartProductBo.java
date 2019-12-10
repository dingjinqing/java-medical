package com.vpu.mp.service.pojo.wxapp.cart.activity;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import jodd.util.CollectionUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 购物车订单 商品数据数据
 * @author 孔德成
 * @date 2019/12/4 15:54
 */
public class OrderCartProductBo {

    private List<OrderCartProduct> info;

    private Map<Integer, OrderCartProduct> map;

    private List<Integer> productIds;



    private OrderCartProductBo(List<OrderCartProduct> info) {
        this.info = info;
    }

    public static OrderCartProductBo create(List<OrderCartProduct> info){
        return new OrderCartProductBo(CollectionUtils.isEmpty(info) ? Collections.emptyList() : info);
    }

    private void initMap(){
        map = info.stream().collect(Collectors.toMap(OrderCartProduct::getProductId, Function.identity()));
    }

    private void initProductIds(){
        productIds = getAll().stream().map(OrderCartProductBo.OrderCartProduct::getProductId).collect(Collectors.toList());
    }

    public List<OrderCartProduct> getAll(){
        return info;
    }

    public List<Integer> getProductIds(){
        if(CollectionUtils.isEmpty(productIds)){
            initProductIds();
        }
        return productIds;
    }

    public OrderCartProduct get(Integer productId){
        if(map == null) {
            initMap();
        }
        return map.get(productId);
    }

    @Getter
    @Setter
    @ToString
    public static class OrderCartProduct{
        private Integer productId;
        private Integer goodsNumber;
        private Byte isChecked;
        /**
         * 商品活动信息
         * k 活动类型
         */
        private Map<Byte ,GoodsActivityInfo> activityInfo =new HashMap<>();

        public OrderCartProduct(Integer productId, Integer goodsNumber) {
            //商品结算默认选中
            this(productId,goodsNumber, CartConstant.CART_IS_CHECKED);
        }
        public OrderCartProduct(Integer productId, Integer goodsNumber,byte isChecked) {
            this.productId = productId;
            this.goodsNumber = goodsNumber;
            this.isChecked =isChecked;
        }

        public GoodsActivityInfo getActivity(Byte activityType){
             return activityInfo.get(activityType);
        }

    }

}

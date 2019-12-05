package com.vpu.mp.service.pojo.wxapp.cart.activity;

import com.vpu.mp.service.foundation.data.BaseConstant;
import jodd.util.CollectionUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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

    public BigDecimal getFirstSpecialPrice(Integer productId){
        if(map == null) {
            initMap();
        }
        if(map.get(productId) == null) {
            return null;
        }else {
            return map.get(productId).getFirstSpecialPrice();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class OrderCartProduct{
        private Integer productId;
        private Integer goodsNumber;
        private Byte isChecked;
        private List<GoodsActivityInfo> activityInfo =new ArrayList<>();

        public OrderCartProduct(Integer productId, Integer goodsNumber) {
            this.productId = productId;
            this.goodsNumber = goodsNumber;
        }

        public BigDecimal getFirstSpecialPrice(){
            Optional<BigDecimal> first = activityInfo.stream()
                .filter(activity -> activity.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL)&&activity.getStatus().equals(BaseConstant.ACTIVITY_STATUS_NORMAL))
                .map(GoodsActivityInfo::getFirstSpecialPrice).findFirst();
            return first.orElse(null);
        }

        public Integer getFirstSpecialId(){
            Optional<Integer> first = activityInfo.stream()
                .filter(activity -> activity.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL)&&activity.getStatus().equals(BaseConstant.ACTIVITY_STATUS_NORMAL))
                .map(GoodsActivityInfo::getActivityId).findFirst();
            return first.orElse(null);
        }

        public BigDecimal getGradeCardPrice(){
            Optional<BigDecimal> first = activityInfo.stream()
                .filter(activity -> activity.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_MEMBER_GRADE)&&activity.getStatus().equals(BaseConstant.ACTIVITY_STATUS_NORMAL))
                .map(GoodsActivityInfo::getMemberPrice).findFirst();
            return first.orElse(null);
        }

    }

}

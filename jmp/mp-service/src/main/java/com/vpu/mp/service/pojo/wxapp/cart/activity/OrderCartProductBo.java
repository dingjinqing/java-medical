package com.vpu.mp.service.pojo.wxapp.cart.activity;

import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 购物车订单 商品数据数据
 * @author 孔德成
 * @date 2019/12/4 15:54
 */
@Getter
@Setter
@ToString
public class OrderCartProductBo {
    /**
     * 下单时间
     */
    private Timestamp date;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 店铺id
     */
    private Integer storeId;
    public List<OrderCartProduct> productList =new ArrayList<>();

    private Map<Integer, OrderCartProduct> map;

    private List<Integer> productIds;


    public OrderCartProductBo() {
    }
    public OrderCartProductBo(List<OrderCartProduct> info) {
        this.productList = info;
    }

    public List<OrderCartProduct> getAll(){
        return productList;
    }

    public List<Integer> getProductIds(){
        initProductIds();
        return productIds;
    }
    public OrderCartProduct get(Integer productId){
        initMap();
        return map.get(productId);
    }

    private void initMap(){
        map = productList.stream().collect(Collectors.toMap(OrderCartProduct::getProductId, Function.identity()));
    }

    private void initProductIds(){
        productIds = getAll().stream().map(OrderCartProductBo.OrderCartProduct::getProductId).collect(Collectors.toList());
    }
    @Getter
    @Setter
    @ToString
    public static class OrderCartProduct{

        private Integer productId;
        private GoodsSpecProductRecord productRecord;
        private Integer goodsNumber;
        private Byte isChecked;
        /**
         * 商品活动信息
         * k 活动类型
         */
        private Map<Byte ,GoodsActivityInfo> activityInfo =new HashMap<>();

        public OrderCartProduct(Integer productId, Integer goodsNumber,GoodsSpecProductRecord productRecord) {
            //商品结算默认选中
            this(productId,goodsNumber,productRecord, CartConstant.CART_IS_CHECKED);
        }
        public OrderCartProduct(Integer productId, Integer goodsNumber,GoodsSpecProductRecord productRecord,byte isChecked) {
            this.productId = productId;
            this.goodsNumber = goodsNumber;
            this.productRecord =productRecord;
            this.isChecked =isChecked;
        }

        public GoodsActivityInfo getActivity(Byte activityType){
             return activityInfo.get(activityType);
        }

    }

}

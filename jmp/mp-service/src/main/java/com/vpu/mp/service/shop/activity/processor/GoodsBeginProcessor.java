package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.cart.activity.OrderCartProductBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 数据初始化处理
 * @author 孔德成
 * @date 2019/11/12 14:42
 */
@Component
@Slf4j
public class GoodsBeginProcessor implements ActivityCartListStrategy{

    /**
     * 购物车
     * @param cartBo 业务数据类
     */
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        log.info("doCartOperation->WxAppCartBo:"+ Util.toJson(cartBo));
        //删除的,下架的--移动到失效列表
        List<WxAppCartGoods> invalidGoodsList = cartBo.getCartGoodsList().stream().filter(goods -> {
            if (goods.getGoodsId() == null || goods.getPrdId() == null|| goods.getDelFlag().equals(DelFlag.DISABLE_VALUE)) {
                log.debug("商品删除的"+"[getRecId:"+goods.getCartId()+",getGoodsName: "+goods.getGoodsName()+",getDelFlag:"+ goods.getDelFlag()+"]");
                goods.setGoodsStatus(CartConstant.GOODS_STATUS_DELETE);
                return true;
            }else if (goods.getIsOnSale().equals(GoodsConstant.OFF_SALE)){
                log.debug("商品下架的"+"[getRecId:"+goods.getCartId()+",getGoodsName: "+goods.getGoodsName()+",getIsOnSale:"+ goods.getIsOnSale()+"]");
                goods.setGoodsStatus(CartConstant.GOODS_STATUS_OFF_SALE);
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        cartBo.getCartGoodsList().removeAll(invalidGoodsList);
        cartBo.getInvalidCartList().addAll(invalidGoodsList);

        //售罄-- 取消选中
        cartBo.getCartGoodsList().forEach(goods->{
            if (goods.getPrdNumber() < goods.getLimitBuyNum() || goods.getPrdNumber() <= 0) {
                log.debug("商品售罄"+"[getGoodsName:"+goods.getGoodsName()+".getPrdNumber: "+goods.getPrdNumber()+",getLimitBuyNum:"+goods.getLimitBuyNum()+"]");
                goods.setGoodsStatus(CartConstant.GOODS_STATUS_SOLD_OUT);
                goods.setIsChecked(CartConstant.CART_NO_CHECKED);
            }
        });

        // 初始化活动list
        List<OrderCartProductBo.OrderCartProduct> orderCartProductList =new ArrayList<>(cartBo.getProductIdList().size());
        cartBo.getCartGoodsList().forEach(goods->{
            orderCartProductList.add(new OrderCartProductBo.OrderCartProduct(goods.getPrdId(), goods.getCartNumber(),goods.getIsChecked()));
        });
        cartBo.setOrderCartProductBo(new  OrderCartProductBo(orderCartProductList));

    }
}

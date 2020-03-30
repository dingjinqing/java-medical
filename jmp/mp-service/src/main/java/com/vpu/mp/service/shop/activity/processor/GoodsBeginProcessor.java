package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.cart.CartConstant;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.shop.user.cart.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @Autowired
    private CartService cartService;

    /**
     * 购物车
     * 1失效商品判断  删除的 下架的 规格修改的
     * 2售罄的商品判断
     * 2 购物车商品数量限制 (商品限制)
     * @param cartBo 业务数据类
     */
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        //删除的,下架的--移动到失效列表
        List<WxAppCartGoods> invalidGoodsList = cartBo.getCartGoodsList().stream().filter(goods -> {
            goods.setGoodsImg(goods.getGoodsRecord().getGoodsImg());
            if (goods.getGoodsId() == null ||  goods.getGoodsRecord().getDelFlag().equals(DelFlag.DISABLE_VALUE)) {
                log.debug("商品删除的"+"[getRecId:"+goods.getCartId()+",getGoodsName: "+goods.getGoodsName()+",getDelFlag:"+ goods.getGoodsRecord().getDelFlag()+"]");
                goods.setGoodsStatus(CartConstant.GOODS_STATUS_DELETE);
                goods.setIsChecked(CartConstant.CART_NO_CHECKED);
                cartService.switchCheckedProduct(cartBo.getUserId(),goods.getCartId(),CartConstant.CART_NO_CHECKED);
                return true;
            }else if (goods.getGoodsRecord().getIsOnSale().equals(GoodsConstant.OFF_SALE)){
                log.debug("商品下架的"+"[getRecId:"+goods.getCartId()+",getGoodsName: "+goods.getGoodsName()+",getIsOnSale:"+ goods.getGoodsRecord().getDelFlag()+"]");
                goods.setGoodsStatus(CartConstant.GOODS_STATUS_OFF_SALE);
                goods.setIsChecked(CartConstant.CART_NO_CHECKED);
                cartService.switchCheckedProduct(cartBo.getUserId(),goods.getCartId(),CartConstant.CART_NO_CHECKED);
                return true;
            }else if (goods.getProductRecord()==null){
                log.debug("商品规格修改"+"[getGoodsName:"+goods.getGoodsName()+",getLimitBuyNum:"+goods.getGoodsRecord().getLimitBuyNum()+"]");
                goods.setGoodsStatus(CartConstant.GOODS_STATUS_DISABLED);
                goods.setIsChecked(CartConstant.CART_NO_CHECKED);
                cartService.switchCheckedProduct(cartBo.getUserId(),goods.getCartId(),CartConstant.CART_NO_CHECKED);
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        cartBo.getCartGoodsList().removeAll(invalidGoodsList);
        cartBo.getInvalidCartList().addAll(invalidGoodsList);
        for (WxAppCartGoods goods : cartBo.getCartGoodsList()) {
            GoodsSpecProductRecord productRecord = goods.getProductRecord();
            GoodsRecord goodsRecord = goods.getGoodsRecord();
            //售罄-- 取消选中
            if (goods.getProductRecord().getPrdNumber() < goods.getGoodsRecord().getLimitBuyNum() || goods.getProductRecord().getPrdNumber() <= 0) {
                log.debug("商品售罄" + "[getGoodsName:" + goods.getGoodsName() + ".getPrdNumber: " + goods.getProductRecord().getPrdNumber() + ",getLimitBuyNum:" + goods.getGoodsRecord().getLimitBuyNum() + "]");
                goods.setGoodsStatus(CartConstant.GOODS_STATUS_SOLD_OUT);
                goods.setIsChecked(CartConstant.CART_NO_CHECKED);
                cartService.switchCheckedProduct(cartBo.getUserId(), goods.getCartId(), CartConstant.CART_NO_CHECKED);
            }
            //购物车数量不能大于商品库存
            if (goods.getCartNumber() > productRecord.getPrdNumber() && goods.getGoodsStatus().equals(CartConstant.GOODS_STATUS_ON_SALE)) {
                goods.setGoodsStatus(CartConstant.GOODS_STATUS_STOCK_SHORTAGE);
                goods.setIsChecked(CartConstant.CART_NO_CHECKED);
                cartService.switchCheckedProduct(cartBo.getUserId(), goods.getCartId(), CartConstant.CART_NO_CHECKED);
                //修改商品数量
            }
            //初始化商品的限制数量
            goods.setLimitMaxNum(goodsRecord.getLimitMaxNum());
            goods.setLimitBuyNum(goodsRecord.getLimitBuyNum());
            goods.setPrdNumber(productRecord.getPrdNumber());
            //初始化价格
            goods.setPrdPrice(productRecord.getPrdPrice());
            goods.setGoodsPrice(productRecord.getPrdPrice());
        }

    }
}

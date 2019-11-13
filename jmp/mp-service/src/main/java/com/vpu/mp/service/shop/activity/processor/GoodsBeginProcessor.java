package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 购物车
     * @param cartBo 业务数据类
     */
    @Override
    public void doCartOperation(WxAppCartBo cartBo) {
        log.info("doCartOperation",cartBo);
        //删除的,下架的--移动到失效列表
        List<WxAppCartGoods> invalidGoodsList = cartBo.getCartGoodsList().stream().filter(goods -> {
            if (goods.getGoodsId() == null || goods.getPrdId() == null) {
                return true;
            }
            return goods.getDelFlag().equals(DelFlag.DISABLE_VALUE) || goods.getIsOnSale().equals(GoodsConstant.OFF_SALE);
        }).collect(Collectors.toList());
        cartBo.getCartGoodsList().removeAll(invalidGoodsList);
        cartBo.setInvalidCartList(invalidGoodsList);
        //售罄-- 取消选中
        cartBo.getCartGoodsList().forEach(goods->{
            if (goods.getPrdNumber() < goods.getLimitBuyNum() || goods.getPrdNumber() <= 0) {
                goods.setIsChecked((byte) 0);
            }
        });
    }
}

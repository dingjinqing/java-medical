package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.shop.activity.processor.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 孔德成
 * @date 2019/11/7 18:32
 */
@Component
@Slf4j
public class CartProcessorContext {


    @Autowired
    private GoodsBeginProcessor goodsBegin;
    @Autowired
    private GoodsTailProcessor goodsTail;
    @Autowired
    private GradeCardProcessor gradeCard;
    @Autowired
    private ExclusiveProcessor exclusive;
    @Autowired
    private FirstSpecialProcessor firstSpecial;
    @Autowired
    private SecKillProcessor seckill;
    @Autowired
    private PreSaleProcessor preSale;
    @Autowired
    private ReducePriceProcessor reducePrice;


    /**
     *  购物车执行方法
     * @param cartBo
     */
    public void executeCart(WxAppCartBo cartBo){
        // 数据初始化
        executeStrategy(goodsBegin,cartBo);
        executeStrategy(seckill,cartBo);
        //预售
        executeStrategy(preSale,cartBo);
        //限时降价
        executeStrategy(reducePrice,cartBo);
        executeStrategy(exclusive,cartBo);
        executeStrategy(firstSpecial,cartBo);
        executeStrategy(gradeCard,cartBo);
        //活动冲突处理
        executeStrategy(goodsTail,cartBo);
    }


    private void executeStrategy(ActivityCartListStrategy strategy, WxAppCartBo cartBo){
        try {
            strategy.doCartOperation(cartBo);
        }catch (Exception e){
            log.error("商品规格策略失败"+strategy.getClass()+e.getMessage());
            e.printStackTrace();
        }
    }
}

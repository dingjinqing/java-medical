package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.foundation.data.BaseConstant;
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
    @Autowired
    private FullReductionProcessor fullReduction;
    @Autowired
    private PurchasePriceProcessor purchasePrice;


    /**
     *  购物车执行方法
     * @param cartBo
     */
    public void executeCart(WxAppCartBo cartBo){
        // 数据初始化
        executeStrategy(goodsBegin,cartBo);
        if (cartBo.getActivityType()!=null){
            switch (cartBo.getActivityType()){
                case 21:
                    executeStrategy(fullReduction,cartBo);
                    break;
                case 7:
                    executeStrategy(purchasePrice,cartBo);
                    break;
                default:

            }
            //活动冲突处理
            executeStrategy(goodsTail,cartBo);
            return;
        }
        //秒杀
        executeStrategy(seckill,cartBo);
        //预售
        executeStrategy(preSale,cartBo);
        //限时降价
        executeStrategy(reducePrice,cartBo);
        //满折满减
        executeStrategy(fullReduction,cartBo);
        //加价购
        executeStrategy(purchasePrice,cartBo);
        //会员专享
        executeStrategy(exclusive,cartBo);
        //首单特惠
        executeStrategy(firstSpecial,cartBo);
        //等级价格
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

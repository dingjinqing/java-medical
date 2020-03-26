package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.shop.activity.processor.ActivityCartListStrategy;
import com.vpu.mp.service.shop.activity.processor.ExclusiveProcessor;
import com.vpu.mp.service.shop.activity.processor.FirstSpecialProcessor;
import com.vpu.mp.service.shop.activity.processor.FullReductionProcessor;
import com.vpu.mp.service.shop.activity.processor.GoodsBeginProcessor;
import com.vpu.mp.service.shop.activity.processor.GoodsTailProcessor;
import com.vpu.mp.service.shop.activity.processor.GradeCardProcessor;
import com.vpu.mp.service.shop.activity.processor.PreSaleProcessor;
import com.vpu.mp.service.shop.activity.processor.PurchasePriceProcessor;
import com.vpu.mp.service.shop.activity.processor.ReducePriceProcessor;
import com.vpu.mp.service.shop.activity.processor.SecKillProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/11/7 18:32
 */
@Component
@Slf4j
public class CartProcessorContext {



    @Autowired(required = false)
    private List<ActivityCartListStrategy> sortProcessors;

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
     * 购物车一般活动
     */
    public final static List<Byte> GENERAL_ACTIVITY = Arrays.asList(
            BaseConstant.ACTIVITY_TYPE_MEMBER_GRADE,
            BaseConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE,
            BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL,
            BaseConstant.ACTIVITY_TYPE_SEC_KILL,
            BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE,
            BaseConstant.ACTIVITY_TYPE_FULL_REDUCTION,
            BaseConstant.ACTIVITY_TYPE_PURCHASE_PRICE
    );


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
        //首单特惠
        executeStrategy(firstSpecial,cartBo);
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

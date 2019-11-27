package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.shop.activity.processor.ActivityCartListStrategy;
import com.vpu.mp.service.shop.activity.processor.ExclusiveProcessor;
import com.vpu.mp.service.shop.activity.processor.FirstSpecialProcessor;
import com.vpu.mp.service.shop.activity.processor.GoodsBeginProcessor;
import com.vpu.mp.service.shop.activity.processor.GoodsTailProcessor;
import com.vpu.mp.service.shop.activity.processor.GradeCardProcessor;
import com.vpu.mp.service.shop.activity.processor.SecKillProcessor;
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


    /**
     *  购物车执行方法
     * @param cartBo
     */
    public void executeCart(WxAppCartBo cartBo){
        // 数据初始化
        executeStrategy(goodsBegin,cartBo);
        executeStrategy(seckill,cartBo);
        executeStrategy(exclusive,cartBo);
        executeStrategy(firstSpecial,cartBo);
        executeStrategy(gradeCard,cartBo);
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

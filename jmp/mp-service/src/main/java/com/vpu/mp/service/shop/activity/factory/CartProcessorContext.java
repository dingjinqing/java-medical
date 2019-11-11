package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartBo;
import com.vpu.mp.service.shop.activity.processor.ActivityCartListStrategy;
import com.vpu.mp.service.shop.activity.processor.ExclusiveProcessor;
import com.vpu.mp.service.shop.activity.processor.FirstSpecialProcessor;
import com.vpu.mp.service.shop.activity.processor.GradeCardProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 孔德成
 * @date 2019/11/7 18:32
 */
@Component
public class CartProcessorContext {


    @Autowired
    private GradeCardProcessor gradeCard;
    @Autowired
    private ExclusiveProcessor exclusive;
    @Autowired
    private FirstSpecialProcessor firstSpecial;


    /**
     *  购物车执行方法
     * @param cartBo
     */
    public void executeCart(WxAppCartBo cartBo){
        executeStrategy(exclusive,cartBo);
        executeStrategy(gradeCard,cartBo);
        executeStrategy(firstSpecial,cartBo);
    }


    private void executeStrategy(ActivityCartListStrategy strategy, WxAppCartBo cartBo){
        strategy.doCartOperation(cartBo);
    }
}

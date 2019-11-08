package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.shop.activity.processor.ActivityCartListStrategy;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/11/7 18:32
 */
@Component
public class CartProcessorContext {



    @Autowired(required = false)
    private List<ActivityCartListStrategy> strategy;

    @PostConstruct
    public void init(){
        if (strategy == null || strategy.size() == 0) {
            LoggerFactory.getLogger(this.getClass()).error("strategy加载失败", this.getClass());
            strategy = new ArrayList<>();
        } else {

            LoggerFactory.getLogger(this.getClass()).debug(strategy.toString());
        }
    }

}

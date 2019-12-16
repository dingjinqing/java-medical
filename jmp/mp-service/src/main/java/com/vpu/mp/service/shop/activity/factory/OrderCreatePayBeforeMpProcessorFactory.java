package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.wxapp.order.CreateParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.shop.activity.processor.GroupBuyProcessor;
import com.vpu.mp.service.shop.activity.processor.OrderCreatePayBeforeProcessor;
import com.vpu.mp.service.shop.activity.processor.SecKillProcessor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_TYPE_GROUP_BUY;

/**
 * 生成订单时的营销处理
 * @author: 王兵兵
 * @create: 2019-11-18 15:51
 **/
@Service
public class OrderCreatePayBeforeMpProcessorFactory extends AbstractProcessorFactory<OrderCreatePayBeforeProcessor, OrderBeforeVo> {
    @Override
    public void doProcess(List<OrderBeforeVo> capsules, Integer userId) throws MpException {

    }

    public Map<Class<? extends OrderCreatePayBeforeProcessor>,OrderCreatePayBeforeProcessor> processorMap;

    @Override
    @PostConstruct
    protected void init(){
        super.init();
        processorMap = new HashMap<>(processors.size());
        for (OrderCreatePayBeforeProcessor processor : processors) {
            processorMap.put(processor.getClass(), processor);
        }
    }

    /**
     * 处理下单时营销活动下单param中的相关
     * @param param
     * @throws MpException
     */
    public void initMarketOrderCreateParam(CreateParam param) {
        if(param.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_SEC_KILL)){
            //秒杀
            processorMap.get(SecKillProcessor.class).initMarketOrderCreateParam(param);
        }else if(param.getActivityType().equals(BaseConstant.ACTIVITY_TYPE_BARGAIN)){
            //砍价 todo
        }else if (param.getActivityType().equals(ACTIVITY_TYPE_GROUP_BUY)){
            // 拼团
            processorMap.get(GroupBuyProcessor.class).initMarketOrderCreateParam(param);
        }
    }
}

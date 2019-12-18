package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.wxapp.order.CreateParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.shop.activity.processor.CreateOrderProcessor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成订单时的营销处理
 * @author: 王兵兵
 * @create: 2019-11-18 15:51
 **/
@Service
public class OrderCreatePayBeforeMpProcessorFactory extends AbstractProcessorFactory<CreateOrderProcessor, OrderBeforeVo>  {
    @Override
    public void doProcess(List<OrderBeforeVo> capsules, Integer userId) throws MpException {

    }

    private Map<Byte,CreateOrderProcessor> processorMap;

    @Override
    @PostConstruct
    protected void init(){
        super.init();
        processorMap = new HashMap<>(processors.size());
        for (CreateOrderProcessor processor : processors) {
            processorMap.put(processor.getActivityType(), processor);
        }
    }

    /**
     * 处理下单时营销活动下单param中的相关
     * @param param
     * @throws MpException
     */
    public void processInitCheckedOrderCreate(CreateParam param) throws MpException {
        if (param.getActivityId()!=null){
            processorMap.get(param.getActivityType()).processInitCheckedOrderCreate(param);
        }
    }

    /**
     * 保存数据
     * @param param
     * @param order
     */
    public void processSaveOrderInfo(CreateParam param, OrderInfoRecord order) throws MpException {
        if (param.getActivityId()!=null){
            processorMap.get(param.getActivityType()).processSaveOrderInfo(param,order);
        }
    }

    /**
     * 库存修改
     * @param param
     * @throws MpException
     */
    public void processStockAndSales(CreateParam param) throws MpException {
        if (param.getActivityId()!=null){
            processorMap.get(param.getActivityType()).processStockAndSales(param);
        }
    }
}


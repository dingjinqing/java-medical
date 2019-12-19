package com.vpu.mp.service.shop.activity.factory;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.shop.activity.processor.CreateOrderProcessor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * 普通优惠卷
     */
    private final static List<Byte> generalActivity = Arrays.asList(
            BaseConstant.ACTIVITY_TYPE_PAY_AWARD,
            BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL,
            BaseConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE
    );

    /**
     * 单一营销
     */
    private Map<Byte,CreateOrderProcessor> processorMap;
    /**
     * 普通营销活动
     */
    private List<CreateOrderProcessor> processorGeneralList;

    @Override
    @PostConstruct
    protected void init(){
        super.init();
        processorMap = new HashMap<>(processors.size());
        processorGeneralList=new ArrayList<>(processors.size());
        for (CreateOrderProcessor processor : processors) {
            processorMap.put(processor.getActivityType(), processor);
            if (generalActivity.contains(processor.getActivityType())){
                processorGeneralList.add(processor);
            }
        }
    }

    /**
     * 处理下单时营销活动下单param中的相关
     * @param param
     * @throws MpException
     */
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        if (param.getActivityId()!=null){
            //单一营销
            processorMap.get(param.getActivityType()).processInitCheckedOrderCreate(param);
        }else {
            //普通营销
            for (CreateOrderProcessor processor : processorGeneralList) {
                processor.processInitCheckedOrderCreate(param);
            }
        }
    }

    /**
     * 保存数据
     * @param param
     * @param order
     */
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        if (param.getActivityId()!=null){
            //单一营销
            processorMap.get(param.getActivityType()).processSaveOrderInfo(param,order);
        }else {
            for (CreateOrderProcessor processor : processorGeneralList) {
                processor.processInitCheckedOrderCreate(param);
            }
        }
    }

    /**
     * 库存修改
     * @param param
     * @throws MpException
     */
    public void processStockAndSales(OrderBeforeParam param) throws MpException {
        if (param.getActivityId()!=null){
            //单一营销
            processorMap.get(param.getActivityType()).processStockAndSales(param);
        }else {
            for (CreateOrderProcessor processor : processorGeneralList) {
                processor.processInitCheckedOrderCreate(param);
            }
        }
    }
}


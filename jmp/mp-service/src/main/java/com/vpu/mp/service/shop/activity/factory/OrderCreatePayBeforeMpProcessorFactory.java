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
     * 一般营销  首单特惠 会员专享
     */
    private final static List<Byte> generalActivity = Arrays.asList(
            BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL,
            BaseConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE
    );
    /**
     * 全局的活动  支付有礼
     */
    private final static List<Byte> globalActivity = Arrays.asList(
            BaseConstant.ACTIVITY_TYPE_PAY_AWARD,
            BaseConstant.ACTIVITY_TYPE_GIFT
    );

    /**
     * 单一营销
     */
    private Map<Byte,CreateOrderProcessor> processorMap;
    /**
     * 普通营销活动
     */
    private List<CreateOrderProcessor> processorGeneralList;

    /**
     * 全局营销
     */
    private List<CreateOrderProcessor> processorGlobalList;

    @Override
    @PostConstruct
    protected void init(){
        super.init();
        processorMap = new HashMap<>(processors.size());
        processorGeneralList=new ArrayList<>(processors.size());
        processorGlobalList=new ArrayList<>(processors.size());
        for (CreateOrderProcessor processor : processors) {
            processorMap.put(processor.getActivityType(), processor);
            if (generalActivity.contains(processor.getActivityType())){
                processorGeneralList.add(processor);
            }
            if (globalActivity.contains(processor.getActivityType())){
                processorGlobalList.add(processor);
            }
        }
    }

    /**
     * 处理下单时营销活动下单param中的相关
     * @param param
     * @throws MpException
     */
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        if (param.getActivityId()!=null && param.getActivityType()!=null){
            //单一营销
            processorMap.get(param.getActivityType()).processInitCheckedOrderCreate(param);
        }else {
            //普通营销
            for (CreateOrderProcessor processor : processorGeneralList) {
                processor.processInitCheckedOrderCreate(param);
            }
        }
        for (CreateOrderProcessor processor : processorGlobalList) {
            //全局活动
            processor.processInitCheckedOrderCreate(param);
        }
    }

    /**
     * 保存数据（该方法不要在并发情况下出现临界资源）
     * @param param
     * @param order
     */
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        if (param.getActivityId()!=null){
            //单一营销
            processorMap.get(param.getActivityType()).processSaveOrderInfo(param,order);
        }else {
            for (CreateOrderProcessor processor : processorGeneralList) {
                processor.processSaveOrderInfo(param,order);
            }
        }
        for (CreateOrderProcessor processor : processorGlobalList) {
            //全局活动
            processor.processSaveOrderInfo(param,order);
        }
    }

    /**
     * 库存修改
     * @param param
     * @throws MpException
     */
    public void processStockAndSales(OrderBeforeParam param,OrderInfoRecord order) throws MpException {
        if (param.getActivityId()!=null){
            //单一营销
            processorMap.get(param.getActivityType()).processStockAndSales(param, order);
        }else {
            for (CreateOrderProcessor processor : processorGeneralList) {
                processor.processStockAndSales(param, order);
            }
        }
        for (CreateOrderProcessor processor : processorGlobalList) {
            //全局活动
            processor.processStockAndSales(param, order);
        }
    }
}


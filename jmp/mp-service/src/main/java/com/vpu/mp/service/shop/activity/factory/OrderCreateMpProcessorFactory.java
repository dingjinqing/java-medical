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
public class OrderCreateMpProcessorFactory extends AbstractProcessorFactory<CreateOrderProcessor, OrderBeforeVo>  {
    @Override
    public void doProcess(List<OrderBeforeVo> capsules, Integer userId) throws MpException {

    }

    /**
     * 一般营销  首单特惠 会员专享
     */
    private final static List<Byte> GENERAL_ACTIVITY = Arrays.asList(
            BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL,
            BaseConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE
    );
    /**
     * 全局的活动  支付有礼
     */
    private final static List<Byte> GLOBAL_ACTIVITY = Arrays.asList(
            BaseConstant.ACTIVITY_TYPE_PAY_AWARD,
            BaseConstant.ACTIVITY_TYPE_GIFT
    );

    /**
     * 全局的活动  支付有礼
     */
    public final static List<Byte> SINGLENESS_ACTIVITY = Arrays.asList(
            BaseConstant.ACTIVITY_TYPE_GROUP_BUY,
            BaseConstant.ACTIVITY_TYPE_SEC_KILL,
            BaseConstant.ACTIVITY_TYPE_BARGAIN
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
            if (SINGLENESS_ACTIVITY.contains(processor.getActivityType())){
                processorMap.put(processor.getActivityType(), processor);
            }
            if (GENERAL_ACTIVITY.contains(processor.getActivityType())){
                processorGeneralList.add(processor);
            }
            if (GLOBAL_ACTIVITY.contains(processor.getActivityType())){
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
     * 订单生效后（微信支付、其他支付、货到付款等）的营销后续处理（库存、活动状态相关）
     * @param param
     * @param order
     * @throws MpException
     */
    public void processOrderEffective(OrderBeforeParam param,OrderInfoRecord order) throws MpException {
        if (param.getActivityId()!=null){
            //单一营销
            // 可能有：我要送礼、限次卡兑换、拼团、砍价、积分兑换、秒杀、拼团抽奖、打包一口价、预售、抽奖、支付有礼、测评、好友助力、满折满减购物车下单
            processorMap.get(param.getActivityType()).processOrderEffective(param, order);
        }else {
            for (CreateOrderProcessor processor : processorGeneralList) {
                processor.processOrderEffective(param, order);
            }
        }
        for (CreateOrderProcessor processor : processorGlobalList) {
            //全局活动
            processor.processOrderEffective(param, order);
        }
    }
}


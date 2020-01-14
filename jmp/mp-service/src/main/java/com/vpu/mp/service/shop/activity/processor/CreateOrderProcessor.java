package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;

/**
 * 小程序-订单生成时-营销处理器接口
 * @author wangbingbing
 */
public interface CreateOrderProcessor extends Processor {

    /**
     *  初始化参数,活动校验（订单确认页接口、点击确认提交生成订单前）
     * @param param
     * @throws MpException
     */
    void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException;

    /**
     * 保存信息（订单入库前,订单信息已经计算完成）
     * @param param
     * @param order
     * @throws MpException
     */
    void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException;


    /**
     * 订单生效后（微信支付、其他支付、货到付款等）的营销后续处理（库存、活动状态相关）
     * @param param
     * @param order
     * @throws MpException
     */
    void processOrderEffective(OrderBeforeParam param,OrderInfoRecord order)throws MpException;
}

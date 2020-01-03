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
     * 保存信息（订单入库前）
     * @param param
     * @param order
     * @throws MpException
     */
    void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException;

    /**
     *  库存与销量（订单入库后）
     * @param param
     * @throws MpException
     */
    void  processStockAndSales(OrderBeforeParam param,OrderInfoRecord order)throws MpException;

    /**
     * 支付完成（订单生效后的回调）
     * @param param
     * @param order
     * @throws MpException
     */
    void processPayCallback(OrderBeforeParam param,OrderInfoRecord order)throws MpException;
}

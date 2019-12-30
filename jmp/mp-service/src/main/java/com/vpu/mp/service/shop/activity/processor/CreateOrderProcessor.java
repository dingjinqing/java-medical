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
     *  初始化参数,活动校验
     * @param param
     * @throws MpException
     */
    void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException;

    /**
     * 保存信息
     * @param param
     * @param order
     * @throws MpException
     */
    void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException;

    /**
     *  库存与销量
     * @param param 规格id
     * @throws MpException
     */
    void  processStockAndSales(OrderBeforeParam param,OrderInfoRecord order)throws MpException;

}

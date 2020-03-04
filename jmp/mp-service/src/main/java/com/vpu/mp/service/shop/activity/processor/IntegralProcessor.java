package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertSelectVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.activity.dao.IntegralProcessorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 积分兑换（单一营销）
 * @author 王帅
 */
@Component
public class IntegralProcessor implements Processor, CreateOrderProcessor {

    @Autowired
    private IntegralProcessorDao dao;

    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_INTEGRAL;
    }
    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        IntegralConvertSelectVo activityInfo = dao.getDetail(param.getActivityId());
        dao.orderCheck(param, activityInfo);
        dao.orderInit(param, activityInfo);
    }

    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    @Override
    public void processOrderEffective(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    @Override
    public void processReturn(Integer activityId, List<OrderReturnGoodsVo> returnGoods) throws MpException {

    }


}

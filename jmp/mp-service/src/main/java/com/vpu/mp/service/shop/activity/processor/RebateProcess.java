package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;

import java.util.List;

/**
 * 分销
 * @author 王帅
 */
public class RebateProcess implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor,CreateOrderProcessor{

    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_REBATE;
    }

    @Override
    public void processForList(List<GoodsListMpBo> capsules, Integer userId) {

    }

    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {

    }

    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    @Override
    public void processOrderEffective(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    @Override
    public void processUpdateStock(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    @Override
    public void processReturn(ReturnOrderRecord returnOrderRecord, Integer activityId, List<OrderReturnGoodsVo> returnGoods) throws MpException {

    }

    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {

    }
}

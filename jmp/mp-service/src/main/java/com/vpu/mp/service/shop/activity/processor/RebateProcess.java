package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.distribution.RebateRatioVo;
import com.vpu.mp.service.pojo.shop.distribution.UserDistributionVo;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.distribution.GoodsDistributionVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.distribution.MpDistributionGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 分销
 * @author 王帅
 */
@Slf4j
public class RebateProcess implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor,CreateOrderProcessor{
    @Autowired
    MpDistributionGoodsService distributionGoods;

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
        log.info("下单分销计算start");
        distributionGoods.calculatePrice(param);
        log.info("下单分销计算end");
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
    public void processGoodsDetail(GoodsDetailMpBo goodsDetailMpBo, GoodsDetailCapsuleParam param) {
        //商品分销
        if(goodsDetailMpBo.getCanRebate() == 1){
            GoodsDistributionVo goodsDistributionVo = new GoodsDistributionVo();
            //获取用户分销等级
            UserDistributionVo distributionLevel = distributionGoods.userDistributionLevel(param.getUserId());
            RebateRatioVo rebateRatioVo = distributionGoods.goodsRebateInfo(param.getGoodsId(), param.getCatId(), param.getSortId(), param.getUserId());
            goodsDistributionVo.setIsDistributor(distributionLevel.getIsDistributor());
            goodsDistributionVo.setCanRebate((byte)1);
            goodsDistributionVo.setRebateRatio(rebateRatioVo);
            goodsDetailMpBo.setGoodsDistribution(goodsDistributionVo);
        }
    }
}

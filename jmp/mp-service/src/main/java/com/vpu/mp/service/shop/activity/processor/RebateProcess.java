package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.distribution.RebateRatioVo;
import com.vpu.mp.service.pojo.shop.distribution.UserDistributionVo;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.distribution.GoodsDistributionVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsListMpBo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.distribution.MpDistributionGoodsService;
import com.vpu.mp.service.shop.order.action.base.Calculate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分销
 * @author 王帅
 */
@Slf4j
@Service
public class RebateProcess implements Processor,ActivityGoodsListProcessor,GoodsDetailProcessor,CreateOrderProcessor{
    @Autowired
    MpDistributionGoodsService distributionGoods;
    @Autowired
    Calculate calculate;
    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_INTEGER_MALL_PRIORITY;
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
        calculate.calculatePrice(param);
        log.info("下单分销计算end");
    }

    /**
     * @param param 参数
     * @param order 订单
     * @throws MpException
     */
    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        calculate.rebate(param,order );
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
            //判断商品是否存在营销活动
            GoodsActivityBaseMp activity = goodsDetailMpBo.getActivity();
            Byte[] intArray = new Byte[]{BaseConstant.ACTIVITY_STATUS_NORMAL,BaseConstant.ACTIVITY_TYPE_BARGAIN,BaseConstant.NAVBAR_TYPE_DISABLED,BaseConstant.ACTIVITY_TYPE_SEC_KILL,
                BaseConstant.ACTIVITY_TYPE_GROUP_DRAW,BaseConstant.ACTIVITY_TYPE_PACKAGE_SALE,BaseConstant.ACTIVITY_TYPE_PRE_SALE,BaseConstant.ACTIVITY_TYPE_LOTTERY_PRESENT,BaseConstant.ACTIVITY_TYPE_EXCHANG_ORDER,
                BaseConstant.ACTIVITY_TYPE_PROMOTE_ORDER,BaseConstant.ACTIVITY_TYPE_PAY_AWARD,BaseConstant.ACTIVITY_TYPE_ASSESS_ORDER};
            ArrayList<Byte> goodsTypes = new ArrayList<>(Arrays.asList(intArray));
            if(activity == null || !(goodsTypes.contains(activity.getActivityType()))){
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
}

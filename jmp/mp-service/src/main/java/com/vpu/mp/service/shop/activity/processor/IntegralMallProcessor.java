package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertSelectVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.integral.IntegralMallMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.integral.IntegralMallPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.activity.dao.IntegralMallProcessorDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 积分兑换处理器
 * @author 李晓冰
 * @date 2020年03月03日
 */
@Service
@Slf4j
public class IntegralMallProcessor implements Processor,GoodsDetailProcessor, CreateOrderProcessor{

    @Autowired
    private IntegralMallProcessorDao dao;

    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_INTEGER_MALL_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_INTEGRAL;
    }

    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        if (!BaseConstant.ACTIVITY_TYPE_INTEGRAL.equals(param.getActivityType()) || param.getActivityId() == null) {
            return;
        }
        IntegralMallMpVo integralMallMpVo = dao.getIntegralMallInfoForDetail(param.getActivityId(), param.getUserId());
        if (BaseConstant.ACTIVITY_STATUS_NOT_HAS.equals(integralMallMpVo.getActState())) {
            capsule.setActivity(integralMallMpVo);
            log.debug("小程序-商品详情-积分兑换商品信息-活动不存在[{}]-详情处理退出", param.getActivityId());
            return;
        }
        List<IntegralMallPrdMpVo> integralMallPrdMpVos = integralMallMpVo.getIntegralMallPrdMpVos();
        Map<Integer, GoodsPrdMpVo> prdMap = capsule.getProducts().stream().collect(Collectors.toMap(GoodsPrdMpVo::getPrdId, Function.identity()));
        List<IntegralMallPrdMpVo> newPrdMpVos = new ArrayList<>(integralMallPrdMpVos.size());
        int goodsNum = 0;

        for (IntegralMallPrdMpVo integralPrdMpVo : integralMallPrdMpVos) {
            GoodsPrdMpVo prd = prdMap.get(integralPrdMpVo.getProductId());
            if (prd == null) {
                continue;
            }
            if (prd.getPrdNumber()<integralPrdMpVo.getStock()) {
                integralPrdMpVo.setStock(prd.getPrdNumber());
            }
            goodsNum+=integralPrdMpVo.getStock();
            newPrdMpVos.add(integralPrdMpVo);
        }
        integralMallMpVo.setIntegralMallPrdMpVos(newPrdMpVos);
        capsule.setGoodsNumber(goodsNum);
        capsule.setActivity(integralMallMpVo);
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
        Map<Integer, OrderGoodsBo> addRecordParam = param.getBos().stream()
        .filter(x -> OrderConstant.IS_GIFT_N.equals(x.getIsGift()))
        .collect(Collectors.toMap(OrderGoodsBo::getProductId, Function.identity()));

        Map<Integer, Integer> updateParam = addRecordParam.values().stream()
            .collect(Collectors.toMap(OrderGoodsBo::getProductId, OrderGoodsBo::getGoodsNumber));
        dao.updateStockAndSales(param.getActivityId(), updateParam);
        dao.addRecords(order, addRecordParam);
    }

    @Override
    public void processReturn(Integer activityId, List<OrderReturnGoodsVo> returnGoods) throws MpException {
        Map<Integer, Integer> updateParam = returnGoods.stream().filter(x -> OrderConstant.IS_GIFT_N.equals(x.getIsGift())).collect(Collectors.toMap(OrderReturnGoodsVo::getProductId, OrderReturnGoodsVo::getGoodsNumber));
        updateParam.forEach((k, v)->updateParam.put(k, -v));
        if(updateParam.size() != 0){
            dao.updateStockAndSales(activityId, updateParam);
            dao.removeRecords(activityId, updateParam.keySet());
        }
    }
}

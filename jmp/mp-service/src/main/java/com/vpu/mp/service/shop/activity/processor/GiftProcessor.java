package com.vpu.mp.service.shop.activity.processor;

import com.google.common.collect.Maps;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.gift.GoodsGiftMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.activity.dao.GiftProcessorDao;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 赠品
 * @author 王帅
 */
@Service
@Slf4j
public class GiftProcessor implements GoodsDetailProcessor,CreateOrderProcessor{

    @Autowired
    private GiftProcessorDao giftDao;
    @Autowired
    private OrderGoodsService orderGoods;
    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_GIFT;
    }

    /*****************商品详情处理*******************/
    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {
        List<GoodsGiftMpVo> goodsGift = giftDao.getGoodsGiftInfoForDetail(capsule.getGoodsId(), DateUtil.getLocalDateTime());
        capsule.setGoodsGifts(goodsGift);
    }


    public void getGifts(Integer userId, List<OrderGoodsBo> goodsBo, List<Byte> orderType){
        giftDao.getGifts( userId, goodsBo, orderType);
    }

    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        //赠品不在这判断
    }

    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        //赠品不在这判断
    }

    @Override
    public void processOrderEffective(OrderBeforeParam param,OrderInfoRecord order) throws MpException {
        Map<Integer, Map<Integer, Integer>> updateparam = Maps.newHashMap();
        param.getBos().stream()
            .filter(x -> OrderConstant.IS_GIFT_Y.equals(x.getIsGift())).collect(Collectors.groupingBy(OrderGoodsBo::getGiftId))
            .forEach((k, v)->
                updateparam.put(k, v.stream().collect(Collectors.toMap(OrderGoodsBo::getProductId, OrderGoodsBo::getGoodsNumber)))
            );
        if(updateparam.size() != 0){
            giftDao.updateStockAndSales(updateparam);
        }
    }

    @Override
    public void processReturn(Integer activityId, List<OrderReturnGoodsVo> returnGoods) throws MpException{
        if(CollectionUtils.isEmpty(returnGoods)){
            return;
        }
        Map<Integer, Map<Integer, Integer>> updateparam = Maps.newHashMap();
        //退款商品recIds
        List<Integer> recIds = returnGoods.stream().map(OrderReturnGoodsVo::getRecId).collect(Collectors.toList());
        //退款退货商品对应的orderGoods商品
        Map<Integer, OrderGoodsRecord> orderGoodsRecord = orderGoods.getOrderGoods(returnGoods.get(0).getOrderSn(), recIds).intoMap(OrderGoodsRecord::getRecId);
        returnGoods.stream().collect(Collectors.groupingBy(x->orderGoodsRecord.get(x.getRecId()).getGiftId()))
            .forEach((k, v) ->{
                updateparam.put(k, v.stream().collect(Collectors.toMap(OrderReturnGoodsVo::getProductId, OrderReturnGoodsVo::getGoodsNumber)));
                //退款时将数量置为负数
                updateparam.get(k).forEach((k1, v1)-> updateparam.get(k).put(k1, -v1));
                }
            );
        if(updateparam.size() != 0){
            giftDao.updateStockAndSales(updateparam);
        }
    }

}

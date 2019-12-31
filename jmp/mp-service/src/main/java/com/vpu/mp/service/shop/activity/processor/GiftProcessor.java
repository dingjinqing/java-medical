package com.vpu.mp.service.shop.activity.processor;

import com.google.common.collect.Maps;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.activity.dao.GiftProcessorDao;
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
public class GiftProcessor implements CreateOrderProcessor{

    @Autowired
    private GiftProcessorDao giftDao;
    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_GIFT;
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
    public void processStockAndSales(OrderBeforeParam param,OrderInfoRecord order) throws MpException {
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
    public void processPayCallback(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }
}

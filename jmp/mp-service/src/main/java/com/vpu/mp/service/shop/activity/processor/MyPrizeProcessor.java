package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.PrizeRecordRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.market.prize.PrizeRecordService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_TYPE_LOTTERY_PRESENT;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_TYPE_MY_PRIZE;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_TYPE_PAY_AWARD;
import static com.vpu.mp.service.pojo.wxapp.market.prize.PrizeConstant.PRIZE_SOURCE;
import static com.vpu.mp.service.pojo.wxapp.market.prize.PrizeConstant.PRIZE_SOURCE_LOTTERY;
import static com.vpu.mp.service.pojo.wxapp.market.prize.PrizeConstant.PRIZE_SOURCE_PAY_AWARD;
import static com.vpu.mp.service.pojo.wxapp.market.prize.PrizeConstant.PRIZE_STATUS_EXPIRE;
import static com.vpu.mp.service.pojo.wxapp.market.prize.PrizeConstant.PRIZE_STATUS_RECEIVED;

/**
 * 我的奖品
 *
 * @author 孔德成
 * @date 2020/1/6 10:38
 */
@Component
public class MyPrizeProcessor extends ShopBaseService implements Processor, CreateOrderProcessor {

    @Autowired
    private PrizeRecordService prizeRecordService;
    @Override
    public Byte getPriority() {
        return 0;
    }

    @Override
    public Byte getActivityType() {
        return ACTIVITY_TYPE_MY_PRIZE;
    }

    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        logger().info("我的奖品>>>>{}", param.getGoods().get(0).getGoodsInfo().getGoodsName());
        //不使用优惠券和会员卡
        param.setMemberCardNo(StringUtils.EMPTY);
        param.setCouponSn(StringUtils.EMPTY);
        PrizeRecordRecord prizeRecord = prizeRecordService.getById(param.getActivityId());
        if (prizeRecord.getPrizeStatus().equals(PRIZE_STATUS_RECEIVED)) {
            logger().info("奖品已经领取过了");
            throw new MpException(JsonResultCode.MY_PRIZE_ACTIVITY_RECEIVED, null);
        }
        if (prizeRecord.getPrizeStatus().equals(PRIZE_STATUS_EXPIRE)) {
            logger().info("奖品过期了");
            throw new MpException(JsonResultCode.MY_PRIZE_ACTIVITY_EXPIRED, null);
        }

        for (OrderBeforeParam.Goods goods : param.getGoods()) {
            goods.setProductPrice(BigDecimal.ZERO);
            goods.setGoodsPriceAction(ACTIVITY_TYPE_MY_PRIZE);
        }
    }

    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        logger().info("增加奖品来源的活动类型");
        PrizeRecordRecord prizeRecord = prizeRecordService.getById(param.getActivityId());
        List<Byte> collect = Arrays.stream(OrderInfoService.orderTypeToArray(order.getGoodsType())).map(Byte::valueOf).collect(Collectors.toList());
        switch (prizeRecord.getActivityType()){
            case PRIZE_SOURCE_PAY_AWARD:
                logger().info("支付有礼");
                collect.add(ACTIVITY_TYPE_PAY_AWARD);
                break;
            case PRIZE_SOURCE_LOTTERY:
                logger().info("大抽奖");
                collect.add(ACTIVITY_TYPE_LOTTERY_PRESENT);
                break;
            case PRIZE_SOURCE:
                break;
            default:
        }
        order.setGoodsType(OrderInfoService.getGoodsTypeToInsert(collect));
    }

    /**
     * 领取奖品成功
     * @throws MpException 无
     */
    @Override
    public void processOrderEffective(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        logger().info("奖品已成功领取");
        int i = prizeRecordService.updateReceivedPrize(param.getActivityId(), order.getOrderSn());
    }

    @Override
    public void processReturn(Integer activityId, List<OrderReturnGoodsVo> returnGoods) {

    }
}

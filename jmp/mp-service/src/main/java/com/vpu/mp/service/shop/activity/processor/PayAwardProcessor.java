package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardVo;
import com.vpu.mp.service.pojo.wxapp.cart.activity.GoodsActivityInfo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.market.payaward.PayAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_TYPE_PAY_AWARD;
import static com.vpu.mp.service.foundation.data.BaseConstant.GOODS_AREA_TYPE_SECTION;

/**
 * 支付有礼活动 下单
 * @author 孔德成
 * @date 2019/12/18 10:56
 */
@Service
public class PayAwardProcessor extends ShopBaseService implements Processor,CreateOrderProcessor {

    @Autowired
    private PayAwardService payAwardService;


    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_BARGAIN_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return ACTIVITY_TYPE_PAY_AWARD;
    }



    /**
     *
     * @param param
     * @throws MpException
     */
    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {

    }

    /**
     * 订单确定后调用
     * 判断保存支付有礼记录
     * @param param
     * @param order
     * @throws MpException
     */
    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        logger().info("支付有礼活动校验");
        //获取进行中的活动
        PayAwardVo payAward = payAwardService.getGoingPayAward(param.getDate());
        //活动商品
        if (payAward.getGoodsAreaType().equals(GOODS_AREA_TYPE_SECTION.intValue())){
            logger().info("支付有礼商品查找");
            boolean payAwardFlag = false;
            for (OrderBeforeParam.Goods goods : param.getGoods()) {
                boolean hasGoodsId = Arrays.asList(payAward.getGoodsIds().split(",")).contains(goods.getGoodsId().toString());
                boolean hasCatId = Arrays.asList(payAward.getGoodsCatIds().split(",")).contains(goods.getGoodsInfo().getCatId().toString());
                boolean hasSortId = Arrays.stream(payAward.getGoodsSortIds().split(",")).anyMatch(goods.getGoodsInfo().getSortId().toString()::equals);
                if (hasGoodsId || hasCatId || hasSortId) {
                    GoodsActivityInfo activityInfo = new GoodsActivityInfo();
                    activityInfo.setActivityType(ACTIVITY_TYPE_PAY_AWARD);
                    activityInfo.setActivityId(payAward.getId());
                    param.getOrderCartProductBo();
                    payAwardFlag = true;
                }
            }
            if (!payAwardFlag){
                return;
            }
        }
        logger().info("");


    }

    /**
     * 减库存操作
     * @param param 规格id
     * @throws MpException
     */
    @Override
    public void processStockAndSales(OrderBeforeParam param) throws MpException {

    }
}

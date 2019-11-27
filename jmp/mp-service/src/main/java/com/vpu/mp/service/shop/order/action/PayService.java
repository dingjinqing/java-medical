package com.vpu.mp.service.shop.order.action;

import com.google.common.collect.Lists;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.atomic.AtomicOperation;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.trade.TradesRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单支付
 * @author 王帅
 */
@Component
public class PayService  extends ShopBaseService implements IorderOperate<OrderOperateQueryParam,OrderOperateQueryParam> {

    @Autowired
    private TradesRecordService tradesRecord;

    @Autowired
    private AtomicOperation atomicOperation;

    @Autowired
    private OrderGoodsService orderGoods;

    @Override
    public Object query(OrderOperateQueryParam param) throws MpException {
        return null;
    }

    @Override
    public ExecuteResult execute(OrderOperateQueryParam obj) {
        return null;
    }

    @Override
    public OrderServiceCode getServiceCode() {
        return OrderServiceCode.PAY;
    }

    /**
     * 目前转化为待发货只有微信这一种情况
     * @param orderInfo
     * @param payRecord
     * @throws MpException
     */
    public void toWaitDeliver(OrderInfoRecord orderInfo, PaymentRecordRecord payRecord) throws MpException {

        ArrayList<String> goodsTypes = Lists.newArrayList(OrderInfoService.orderTypeToArray(orderInfo.getGoodsType()));

        if(!OrderOperationJudgment.canWaitDeliver(orderInfo.getOrderStatus())) {
            logger().error("订单不可以变为待发货(PayService.toWaitDeliver),sn:{}", orderInfo.getOrderSn());
            throw new MpException(JsonResultCode.CODE_ORDER_NOT_TO_WAIT_DELIVER, "订单不可以变为待发货");
        }

        //TODO 目前转化只有可能为微信回调
        if(OrderConstant.PAY_CODE_WX_PAY.equals(orderInfo.getPayCode()) && payRecord != null) {
            logger().error("订单支付方式必须为微信支付且必须有payRecord(PayService.toWaitDeliver),sn:{}", orderInfo.getOrderSn());
            throw new MpException(JsonResultCode.CODE_ORDER_NOT_TO_WAIT_DELIVER, "订单支付方式必须为微信支付且必须有payRecord");
        }

        //微信支付记录(全部)
        tradesRecord.addRecord(orderInfo.getMoneyPaid(),orderInfo.getOrderSn(),orderInfo.getUserId(), TradesRecordService.TRADE_CONTENT_MONEY, RecordTradeEnum.TYPE_CRASH_WX_PAY.val(),RecordTradeEnum.TRADE_FLOW_IN.val(),TradesRecordService.TRADE_CONTENT_MONEY);
        //状态转化
        if(goodsTypes.contains(String.valueOf(OrderConstant.GOODS_TYPE_PIN_GROUP)) || goodsTypes.contains(String.valueOf(OrderConstant.GOODS_TYPE_GROUP_DRAW))){
            //拼团类型
            orderInfo.setOrderStatus(OrderConstant.ORDER_PIN_PAYED_GROUPING);
        }else{
            //TODO 通知服务、上报广告信息
            orderInfo.setOrderStatus(OrderConstant.ORDER_WAIT_DELIVERY);
        }
        orderInfo.setPayTime(DateUtil.getSqlTimestamp());
        orderInfo.setPaySn(payRecord == null ? StringUtils.EMPTY : payRecord.getPaySn());
        orderInfo.setPayCode(payRecord == null ? StringUtils.EMPTY : payRecord.getPaySn());
        orderInfo.update();
        //TODO 更新拼团状态

        //订单商品
        List<OrderGoodsBo> goods = orderGoods.getByOrderId(orderInfo.getOrderId()).into(OrderGoodsBo.class);
        //库存销量
        atomicOperation.updateStockAndSales(orderInfo, goods);
        //TODO 异常订单处理等等
    }
}

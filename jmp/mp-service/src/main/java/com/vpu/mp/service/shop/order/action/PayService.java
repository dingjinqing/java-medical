package com.vpu.mp.service.shop.order.action;

import com.google.common.collect.Lists;
import com.vpu.mp.db.shop.tables.OrderGoods;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupOrderVo;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleVo;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.pay.PayParam;
import com.vpu.mp.service.pojo.wxapp.order.CreateOrderVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam.Goods;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.activity.dao.GiftProcessorDao;
import com.vpu.mp.service.shop.activity.dao.PreSaleProcessorDao;
import com.vpu.mp.service.shop.activity.factory.OrderCreateMpProcessorFactory;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyListService;
import com.vpu.mp.service.shop.market.presale.PreSaleService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.atomic.AtomicOperation;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.trade.OrderPayService;
import com.vpu.mp.service.shop.order.trade.TradesRecordService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_TYPE_GROUP_BUY;
import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.IS_GROUPER_N;

/**
 * 订单支付
 * @author 王帅
 */
@Component
public class PayService  extends ShopBaseService implements IorderOperate<OrderOperateQueryParam,PayParam> {

    @Autowired
    private TradesRecordService tradesRecord;

    @Autowired
    private AtomicOperation atomicOperation;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private OrderInfoService orderInfo;

    @Autowired
    private PreSaleService preSale;

    @Autowired
    private CreateService createOrder;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSpecProductService goodsSpecProduct;

    @Autowired
    private OrderPayService orderPay;

    @Autowired
    private GroupBuyListService groupBuyListService;

    @Autowired
    private GiftProcessorDao giftProcessorDao;

    @Autowired
    private PreSaleProcessorDao preSaleProcessorDao;

    /**
     * 营销活动processorFactory
     */
    @Autowired
    private OrderCreateMpProcessorFactory marketProcessorFactory;

    @Override
    public OrderServiceCode getServiceCode() {
        return OrderServiceCode.PAY;
    }

    @Override
    public Object query(OrderOperateQueryParam param) {
        return null;
    }

    @Override
    public ExecuteResult execute(PayParam param) {
        OrderInfoRecord order = orderInfo.getRecord(param.getOrderId());
        if (order == null) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, null);
        }
        if (order.getOrderStatus() != OrderConstant.ORDER_WAIT_PAY) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_TOPAY_STATUS_NOT_WAIT_PAY, null);
        }
        //时间校验
        ExecuteResult checkPayTimeResult = checkPayTime(order);
        if (checkPayTimeResult != null) {
            return checkPayTimeResult;
        }
        //订单商品
        Result<OrderGoodsRecord> orderGoodsRecord = orderGoodsService.getByOrderId(param.getOrderId());
        //商品校验
        ExecuteResult checkGoodsResult = checkGoods(param, orderGoodsRecord);
        if (checkGoodsResult != null) {
            return checkGoodsResult;
        }
        //活动校验
        ExecuteResult checkActivityResult = checkActivity(order);
        if (checkActivityResult != null) {
            return checkActivityResult;
        }
        CreateOrderVo result = new CreateOrderVo();
        result.setOrderSn(order.getOrderSn());
        try {
            String orderSn;
            BigDecimal money;
            if(order.getOrderPayWay().equals(OrderConstant.PAY_WAY_DEPOSIT) && order.getBkOrderPaid().equals(OrderConstant.BK_PAY_FRONT)) {
                orderSn = order.getOrderSn() + OrderConstant.BK_SN_SUFFIX;
                money = order.getBkOrderMoney();
            }else {
                orderSn = order.getOrderSn();
                money = order.getMoneyPaid();
            }
            result.setWebPayVo(orderPay.isContinuePay(order, orderSn, money, orderPay.getGoodsNameForPay(order, orderGoodsRecord.into(OrderGoodsBo.class)), param.getClientIp(), param.getWxUserInfo().getWxUser().getOpenId(), null));
            return ExecuteResult.create(result);
        } catch (MpException e) {
            return ExecuteResult.create(e);
        }
    }

    /**
     * 支付时间校验
     * @param order
     * @return
     */
    private ExecuteResult checkPayTime(OrderInfoRecord order) {
        //过期校验
        long currenTmilliseconds = Instant.now().toEpochMilli();
        if (order.getBkOrderPaid() == OrderConstant.BK_PAY_FRONT) {
            //定金订单支付尾款
            Record2<Timestamp, Timestamp> timeInterval = preSale.getTimeInterval(order.getActivityId());
            if (timeInterval.value1().getTime() < currenTmilliseconds) {
                return ExecuteResult.create(JsonResultCode.CODE_ORDER_TOPAY_BK_PAY_NOT_START, null);
            }
            if (currenTmilliseconds > timeInterval.value2().getTime()) {
                return ExecuteResult.create(JsonResultCode.CODE_ORDER_TOPAY_EXPIRED, null);
            }
        } else {
            //普通订单或定金订单支付定金
            if (order.getExpireTime().getTime() < currenTmilliseconds) {
                return ExecuteResult.create(JsonResultCode.CODE_ORDER_TOPAY_EXPIRED, null);
            }
        }
        return null;
    }

    /**
     * 商品校验
     * @param param
     * @return
     */
    private ExecuteResult checkGoods(PayParam param, Result<OrderGoodsRecord> orderGoodsRecord) {
        //商品
        Map<Integer, GoodsRecord> goodsRecords = goodsService.getGoodsToOrder(orderGoodsRecord.stream().map(OrderGoodsRecord::getGoodsId).distinct().collect(Collectors.toList()));
        //规格信息,key proId
        Map<Integer, GoodsSpecProductRecord> productInfo = goodsSpecProduct.selectSpecByProIds(orderGoodsRecord.stream().map(OrderGoodsRecord::getProductId).distinct().collect(Collectors.toList()));
        for (OrderGoodsRecord orderGoods : orderGoodsRecord) {
            Goods temp = orderGoods.into(Goods.class);
            temp.setProductInfo(productInfo.get(temp.getProductId()));
            temp.setGoodsInfo(goodsRecords.get(temp.getGoodsId()));
            if(OrderConstant.IS_GIFT_Y.equals(orderGoods.getIsGift())) {
                if(!giftProcessorDao.toPayCheck(orderGoods.getGiftId(), orderGoods.getProductId(), orderGoods.getGoodsNumber())) {
                    //赠品不满足删除
                    orderGoods.delete();
                }
            }
            try {
                createOrder.checkGoodsAndProduct(temp);
            } catch (MpException e) {
                return ExecuteResult.create(e);
            }

        }
        return null;
    }

    /**
     * 活动校验
     */
    private ExecuteResult checkActivity(OrderInfoRecord order) {
        //订单类型
        ArrayList<String> goodsType = Lists.newArrayList(OrderInfoService.orderTypeToArray(order.getGoodsType()));
        if (goodsType.contains(ACTIVITY_TYPE_GROUP_BUY.toString())){
            GroupOrderVo groupBuyRecord = groupBuyListService.getByOrder(order.getOrderSn());
            Timestamp date = DateUtil.getLocalDateTime();
            // 是否可以参加拼团
            ResultMessage resultMessage = groupBuyListService.canCreatePinGroupOrder(groupBuyRecord.getUserId(), date, groupBuyRecord.getActivityId(), groupBuyRecord.getGroupId(), IS_GROUPER_N);
            if (!resultMessage.getFlag()) {
                String[] str2 = resultMessage.getMessages().toArray(new String[0]);
                return ExecuteResult.create(resultMessage.getJsonResultCode(), null, str2);
            }
        } else if (goodsType.contains(BaseConstant.ACTIVITY_TYPE_PRE_SALE.toString())){
            //预售
            PreSaleVo info = preSaleProcessorDao.getDetail(order.getActivityId());
            if(info == null || info.getDelFlag().equals(DelFlag.DISABLE_VALUE)) {
                return ExecuteResult.create(JsonResultCode.CODE_ORDER_ACTIVITY_DISABLE, null);
            }
        }
        return null;
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
        if(!OrderConstant.PAY_CODE_WX_PAY.equals(orderInfo.getPayCode()) || payRecord == null) {
            logger().error("订单支付方式必须为微信支付且必须有payRecord(PayService.toWaitDeliver),sn:{}", orderInfo.getOrderSn());
            throw new MpException(JsonResultCode.CODE_ORDER_NOT_TO_WAIT_DELIVER, "订单支付方式必须为微信支付且必须有payRecord");
        }
        //微信支付记录(全部)
        tradesRecord.addRecord(orderInfo.getMoneyPaid(),orderInfo.getOrderSn(),orderInfo.getUserId(), TradesRecordService.TRADE_CONTENT_MONEY, RecordTradeEnum.TYPE_CRASH_WX_PAY.val(),RecordTradeEnum.TRADE_FLOW_IN.val(),TradesRecordService.TRADE_CONTENT_MONEY);
        //状态转化
        if(goodsTypes.contains(String.valueOf(ACTIVITY_TYPE_GROUP_BUY)) || goodsTypes.contains(String.valueOf(BaseConstant.ACTIVITY_TYPE_GROUP_DRAW))){
            //拼团类型
            orderInfo.setOrderStatus(OrderConstant.ORDER_PIN_PAYED_GROUPING);
        }else{
            //TODO 通知服务、上报广告信息
            orderInfo.setOrderStatus(OrderConstant.ORDER_WAIT_DELIVERY);
        }
        orderInfo.setPayTime(DateUtil.getSqlTimestamp());
        orderInfo.setPaySn(payRecord == null ? StringUtils.EMPTY : payRecord.getPaySn());
        orderInfo.update();

        //订单商品
        List<OrderGoodsBo> goods = orderGoodsService.getByOrderId(orderInfo.getOrderId()).into(OrderGoodsBo.class);
        //库存销量
        atomicOperation.updateStockAndSalesByLock(orderInfo, goods, false);
        //TODO 异常订单处理等等

        // 订单生效时营销活动后续处理
        processOrderEffective(orderInfo, orderInfo);
    }

    /**
     * 将要过期的未支付订单，进行支付提醒通知。 定时每分钟执行，获取10分钟后过期的订单，通知用户支付
     */
    public void autoExpiringNoPayOrderNotify(){
        Result<OrderInfoRecord> orders = orderInfo.getExpiringNoPayOrderList();
        orders.forEach(order->{
            //TODO 小程序消息推送
        });

    }

    /**
     *  支付活动
     * @param param
     * @param orderInfo
     * @throws MpException
     */
    private void processOrderEffective(OrderInfoRecord param, OrderInfoRecord orderInfo) throws MpException {
        if (!orderInfo.getOrderStatus().equals(OrderConstant.ORDER_WAIT_DELIVERY)){
            return;
        }
        String[] strings = OrderInfoService.orderTypeToArray(orderInfo.getGoodsType());
        List<Byte> activityTypeList = Arrays.stream(strings).map(Byte::valueOf).collect(Collectors.toList());
        Byte activityType = OrderCreateMpProcessorFactory.SINGLENESS_ACTIVITY.stream().filter(activityTypeList::contains).findFirst().get();
        OrderBeforeParam orderBeforeParam =new OrderBeforeParam();
        orderBeforeParam.setActivityType(activityType);
        orderBeforeParam.setActivityId(orderInfo.getActivityId());
        orderBeforeParam.setDate(param.getCreateTime());
        orderBeforeParam.setGoods(new ArrayList<>());
        List<GoodsRecord> goodsList = orderGoodsService.getGoodsInfoRecordByOrderSn(orderInfo.getOrderSn());
        Map<Integer, OrderBeforeParam.Goods> orderGoodsMap = orderGoodsService.getOrderGoods(orderInfo.getOrderSn()).intoMap(OrderGoods.ORDER_GOODS.GOODS_ID, OrderBeforeParam.Goods.class);
        goodsList.forEach(goods->{
            OrderBeforeParam.Goods goodsParam = orderGoodsMap.get(goods.getGoodsId());
            goodsParam.setGoodsInfo(goods);
            orderBeforeParam.getGoods().add(goodsParam);
        });
        marketProcessorFactory.processOrderEffective(orderBeforeParam,orderInfo);
    }



}

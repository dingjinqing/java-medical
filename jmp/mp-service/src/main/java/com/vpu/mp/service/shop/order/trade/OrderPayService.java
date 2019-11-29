package com.vpu.mp.service.shop.order.trade;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.google.common.collect.Lists;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.BigDecimalPlus;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.Operator;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.data.AccountData;
import com.vpu.mp.service.pojo.shop.member.data.ScoreData;
import com.vpu.mp.service.pojo.shop.member.data.UserCardData;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.virtual.VirtualOrderPayInfo;
import com.vpu.mp.service.pojo.wxapp.order.CreateOrderVo;
import com.vpu.mp.service.pojo.wxapp.order.CreateParam;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.operation.RecordTradeService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.refund.record.OrderRefundRecordService;
import com.vpu.mp.service.shop.order.refund.record.RefundAmountRecordService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * 订单支付
 * @author 王帅
 *
 */
@Component
public class OrderPayService extends ShopBaseService{

    @Autowired
    private RecordTradeService recordMemberTrade;

    @Autowired
    private RefundAmountRecordService refundAmountRecord;

    @Autowired
    private OrderRefundRecordService orderRefundRecord;

    @Autowired
    private TradesRecordService tradesRecord;

    @Autowired
    private UserCardService card;

    @Autowired
    private MpPaymentService pay;

    @Autowired
    private OrderInfoService order;


    /**
     * 订单系统内金额支付方法
     * @param order 订单
     * @param useAccount 用户余额
     * @param score 积分
     * @param cardBalance 会员卡金额
     * @throws MpException 见具体方法
     */
    public void payMethodInSystem(OrderInfoRecord order, BigDecimal useAccount, BigDecimal score, BigDecimal cardBalance) throws MpException {
        payUseAccount(order, useAccount);
        payMemberCardBalance(order, cardBalance);
        payScoreDiscount(order, score);
    }

    /**
     * 是否需要继续微信支付
     * @param orderInfo 订单
     * @param orderSn 订单号（定金bk特殊）
     * @param money 金额（定金bk特殊）
     * @param goodsNameForPay 商品描述
     * @param ClientIp mp端ip
     * @param openId openid
     * @param activityType 活动类型
     * @throws MpException
     */
    public WebPayVo isContinuePay(OrderInfoRecord orderInfo, String orderSn, BigDecimal money, String goodsNameForPay,String ClientIp, String openId, Byte activityType) throws MpException {
        logger().info("继续支付接口start");
        ArrayList<String> goodsType = Lists.newArrayList(OrderInfoService.orderTypeToArray(orderInfo.getGoodsType()));
        if(orderInfo.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY || orderInfo.getOrderStatus() == OrderConstant.ORDER_PIN_PAYED_GROUPING){
            return null;
        }else if(OrderConstant.ORDER_WAIT_PAY == orderInfo.getOrderStatus() &&
            (((orderInfo.getBkOrderPaid() > 0 && goodsType.contains(String.valueOf(OrderConstant.GOODS_TYPE_PRE_SALE))))
                || OrderConstant.PAY_WAY_FRIEND_PAYMENT == orderInfo.getOrderPayWay())) {
            //待支付 && （（预售 && 已付定金或已付尾款） || 好友代付）
            return null;
        }else {
            //非系统金额支付
            try {
                logger().info("微信预支付调用接口调用start");
                WebPayVo webPayVo = pay.wxUnitOrder(ClientIp, goodsNameForPay, orderSn, money, openId);
                webPayVo.setOrderSn(orderInfo.getOrderSn());
                webPayVo.setOrderType(activityType == null ? null : activityType.toString());
                order.updatePrepayId(webPayVo.getResult().getPrepayId(), orderInfo.getOrderId(), orderSn);
                logger().info("微信预支付调用接口调用end");
                return webPayVo;
            } catch (WxPayException e) {
                logger().error("微信预支付调用接口失败WxPayException，订单号：{},异常：{}", orderInfo.getOrderSn(), e);
                throw new MpException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
            }catch (Exception e) {
                logger().error("微信预支付调用接口失败Exception，订单号：{},异常：{}", orderInfo.getOrderSn(), e.getMessage());
                throw new MpException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
            }
        }
    }

    public String getGoodsNameForPay(OrderInfoRecord orderInfo, List<OrderGoodsBo> orderGoodsBo) {
        StringBuilder result = new StringBuilder(orderGoodsBo.get(0).getGoodsName());
        if(result.length() > 32){
            result.substring(0, 32);
        }
        result.append(orderGoodsBo.size() == 1 ? StringUtils.EMPTY : "等").append(orderInfo.getGoodsAmount()).append("件");
        return result.toString();
    }

    /**
     * 会员卡余额
     * @param order 订单
     * @param money 金额
     * @throws MpException 见具体方法
     */
    public void payMemberCardBalance(OrderInfoRecord order, BigDecimal money) throws MpException {
        logger().info("下单支付会员卡余额start:{}", money);
        if(BigDecimalUtil.compareTo(money, null) == 0) {
            return;
        }
        /**
		 * 交易记录信息
		 */
		TradeOptParam tradeOpt = TradeOptParam
				.builder()
				.adminUserId(0)
				.tradeType(RecordTradeEnum.TYPE_CRASH_MCARD_ACCOUNT_REFUND.val())
				.tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val())
				.build();
        UserCardParam card = this.card.getCard(order.getCardNo());
        UserCardData userCardData = UserCardData.newBuilder().
            userId(order.getUserId()).
            cardId(card == null ? null : card.getCardId()).
            cardNo(order.getCardNo()).
            money(money.negate()).
            reason("订单下单会员卡余额支付"+order.getOrderSn()).
            //普通会员卡
            type(CardConstant.MCARD_TP_NORMAL).
            orderSn(order.getOrderSn()).
            tradeOpt(tradeOpt).build();
        //调用退会员卡接口
        recordMemberTrade.updateUserEconomicData(userCardData);
        logger().info("下单支付会员卡余额start:{}", money);
    }

    /**
     * 用户余额
     * @param order 订单
     * @param money 金额
     * @throws MpException 见具体方法
     */
    public void payUseAccount(OrderInfoRecord order, BigDecimal money) throws MpException {
        logger().info("下单支付用户余额start:{}", money);
        if(BigDecimalUtil.compareTo(money, null) == 0) {
            return;
        }
        AccountData accountData = AccountData.newBuilder().
            userId(order.getUserId()).
            orderSn(order.getOrderSn()).
            //退款金额
                amount(money.negate()).
                remark("下单："+order.getOrderSn()).
                payment(order.getPayCode()).
            //支付类型
                isPaid(RecordTradeEnum.UACCOUNT_RECHARGE.val()).

            //后台处理时为操作人id为0
                adminUser(0).

            //用户余额退款
                tradeType(RecordTradeEnum.TYPE_CRASH_MACCOUNT_REFUND.val()).
            //资金流量-支出
                tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val()).build();
        //调用退余额接口
        recordMemberTrade.updateUserEconomicData(accountData);
        logger().info("下单支付用户余额end:{}", money);
    }

    /**
     * 积分
     * @param order 订单
     * @param money 金额
     * @throws MpException 见具体方法
     */
    public void payScoreDiscount(OrderInfoRecord order, BigDecimal money) throws MpException {
        logger().info("下单支付用户积分start:{}", money);
        if(BigDecimalUtil.compareTo(money, null) == 0) {
            return;
        }
        //金额换算成积分
        Integer score = BigDecimalUtil.multiplyOrDivide(
            BigDecimalPlus.create(new BigDecimal(OrderConstant.TUAN_TO_FEN), Operator.multiply),
            BigDecimalPlus.create(money,null)
        ).intValue();

        ScoreData scoreData = ScoreData.newBuilder().
            userId(order.getUserId()).
            orderSn(order.getOrderSn()).
            //退款积分
                score(-score).
                remark("下单："+order.getOrderSn()).
            //后台处理时为操作人id为0
                adminUser(0).
            //用户余额充值
                tradeType(RecordTradeEnum.TYPE_CRASH_POWER_MACCOUNT.val()).
            //资金流量-支出
                tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val()).
            //积分变动是否来自退款
                isFromRefund(RecordTradeEnum.IS_FROM_REFUND_Y.val()).build();
        //调用退积分接口

        recordMemberTrade.updateUserEconomicData(scoreData);
        logger().info("下单支付用户积分end:{}", money);
    }

    /**
     * 虚拟订单微信退款
     * @param order
     * @param money
     * @throws MpException
     */
    public void refundVirtualWx(VirtualOrderPayInfo order , BigDecimal money) throws MpException {
        if(OrderConstant.PAY_CODE_WX_PAY.equals(order.getPayCode())) {
            //orderRefundRecord.wxPayRefund(order , money);
        }
        //交易记录
        tradesRecord.addRecord(money,order.getOrderSn(),order.getUserId(),TradesRecordService.TRADE_CONTENT_MONEY,RecordTradeEnum.TYPE_CASH_REFUND.val(),RecordTradeEnum.TRADE_FLOW_OUT.val(),TradesRecordService.TRADE_STATUS_ARRIVAL);
    }

}

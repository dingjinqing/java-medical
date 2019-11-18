package com.vpu.mp.service.shop.order.trade;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.order.OrderReadService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.BigDecimalPlus;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.Operator;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.data.AccountData;
import com.vpu.mp.service.pojo.shop.member.data.ScoreData;
import com.vpu.mp.service.pojo.shop.member.data.UserCardData;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.shop.operation.RecordMemberTradeService;
import com.vpu.mp.service.shop.order.refund.record.OrderRefundRecordService;
import com.vpu.mp.service.shop.order.refund.record.RefundAmountRecordService;
/**
 * 订单支付
 * @author 王帅
 *
 */
@Component
public class OrderPayService extends ShopBaseService{

    @Autowired
    private RecordMemberTradeService recordMemberTrade;
    @Autowired
    private RefundAmountRecordService refundAmountRecord;
    @Autowired
    private OrderRefundRecordService orderRefundRecord;
    @Autowired
    private TradesRecordService tradesRecord;
    @Autowired
    private UserCardService card;
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
     * @param orderInfo record
     */
    public void isContinuePay(OrderInfoRecord orderInfo){
        ArrayList<String> goodsType = Lists.newArrayList(OrderReadService.orderTypeToArray(orderInfo.getGoodsType()));
        if(goodsType.contains(String.valueOf(OrderConstant.ORDER_WAIT_DELIVERY)) || goodsType.contains(String.valueOf(OrderConstant.ORDER_PIN_PAYED_GROUPING))){
            return;
        }else if(OrderConstant.ORDER_WAIT_PAY == orderInfo.getOrderStatus() &&
            (((orderInfo.getBkOrderPaid() > 0 && goodsType.contains(String.valueOf(OrderConstant.GOODS_TYPE_PRE_SALE))))
                || OrderConstant.PAY_WAY_FRIEND_PAYMENT == orderInfo.getOrderPayWay())) {
            //待支付 && （（预售 && 已付定金或已付尾款） || 好友代付）
            return;
        }else {
            //TODO 微信支付
        }
    }
    /**
     * 会员卡余额
     * @param order 订单
     * @param money 金额
     * @throws MpException 见具体方法
     */
    public void payMemberCardBalance(OrderInfoRecord order, BigDecimal money) throws MpException {
        if(BigDecimalUtil.compareTo(money, null) == 0) {
            return;
        }
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
            //后台处理时为操作人id为0
                adminUser(0).
            //用户会员卡余额
                tradeType(RecordTradeEnum.MEMBER_CARD_PAY.getValue()).
            //资金流量-支出
                tradeFlow(RecordTradeEnum.TRADE_FLOW_INCOME.getValue()).build();
        //调用退会员卡接口
        recordMemberTrade.updateUserEconomicData(userCardData);
    }

    /**
     * 用户余额
     * @param order 订单
     * @param money 金额
     * @throws MpException 见具体方法
     */
    public void payUseAccount(OrderInfoRecord order, BigDecimal money) throws MpException {
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
                isPaid(RecordTradeEnum.CONSUMPTION.getValue()).
            //后台处理时为操作人id为0
                adminUser(0).
            //用户余额
                tradeType(RecordTradeEnum.ACCOUNT_PAY.getValue()).
            //资金流量-收入
                tradeFlow(RecordTradeEnum.TRADE_FLOW_INCOME.getValue()).build();
        //余额接口
        recordMemberTrade.updateUserEconomicData(accountData);
    }

    /**
     * 积分
     * @param order 订单
     * @param money 金额
     * @throws MpException 见具体方法
     */
    public void payScoreDiscount(OrderInfoRecord order, BigDecimal money) throws MpException {
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
                tradeType(RecordTradeEnum.SCORE_PAY.getValue()).
            //资金流量-支出
                tradeFlow(RecordTradeEnum.TRADE_FLOW_INCOME.getValue()).
            //积分变动是否来自退款
                isFromRefund(RecordTradeEnum.IS_FROM_REFUND_N.getValue()).build();
        //调用积分接口
        recordMemberTrade.updateUserEconomicData(scoreData);
    }

    /**
     * 	微信
     * @param order
     * @param money
     * @return
     * @throws MpException
     */
    public void refundMoneyPaid(OrderInfoVo order , Integer retId ,BigDecimal money) {
        if(OrderConstant.PAY_WAY_FRIEND_PAYMENT == order.getOrderPayWay()) {
            //TODO 好友代付
        }
        if(OrderConstant.PAY_CODE_WX_PAY.equals(order.getPayCode())) {
            try {
                orderRefundRecord.wxPayRefund(order, retId, money);
            } catch (MpException e) {
                //TODO 微信失败处理
            }

        }
        //交易记录
        tradesRecord.addRecord(money,order.getOrderSn(),order.getUserId(),TradesRecordService.TRADE_CONTENT_MONEY,RecordTradeEnum.CASH_REFUND.getValue(),RecordTradeEnum.TRADE_FLOW_OUTCOME.getValue(),TradesRecordService.TRADE_STATUS_ARRIVAL);
        //记录
        refundAmountRecord.addRecord(order.getOrderSn(), order.getUserId(), RefundAmountRecordService.MONEY_PAID, money, retId);
    }
}

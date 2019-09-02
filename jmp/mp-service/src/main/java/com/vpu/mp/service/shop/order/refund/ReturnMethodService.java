package com.vpu.mp.service.shop.order.refund;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.BigDecimalPlus;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.Operator;
import com.vpu.mp.service.foundation.util.FieldsUtil;
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
import com.vpu.mp.service.shop.order.trade.TradesRecordService;
/**
 * 
 * @author 王帅
 *
 */
@Component
public class ReturnMethodService extends ShopBaseService{
	
	private static String RETURN_METHOD_PREFIX = "refund_";
	@Autowired
	private RecordMemberTradeService recordMemberTrade;
	@Autowired
	private RefundAmountRecordService refundAmountRecord;
	@Autowired
	private OrderRefundRecordService orderRefundRecord;
	@Autowired
	private TradesRecordService tradesRecord;
	/**
	 * 	退款统一入口（微信、余额、积分、卡余额等）
	 * @param methodName 
	 * @param order
	 * @param returnOrder
	 * @param money
	 * @return
	 * @throws MpException 
	 */
	public boolean refundMethods(String methodName , OrderInfoVo order , ReturnOrderRecord returnOrder ,BigDecimal money) throws MpException {
		try {
			Method method = getClass().getMethod(FieldsUtil.underLineToCamel(RETURN_METHOD_PREFIX + methodName), OrderInfoVo.class,ReturnOrderRecord.class,BigDecimal.class);
			method.invoke(this, order,returnOrder,money);
		} catch (InvocationTargetException e) {
			//反射捕获自定义异常
			Throwable cause = e.getCause();
			if (cause instanceof MpException) {
				throw new MpException(((MpException) cause).getErrorCode(), e.getMessage());
			}else {
				logger().error("退款统一入口调用方法异常(非MpException)ReturnOrderSn:"+returnOrder.getReturnOrderSn()+"。异常信息："+e.getMessage());
				throw new MpException(JsonResultCode.CODE_ORDER_RETURN_METHOD_REFLECT_ERROR, e.getMessage());
			}
		} catch (Exception e) {
			logger().error("退款统一入口调用异常ReturnOrderSn:"+returnOrder.getReturnOrderSn()+"。异常信息："+e.getMessage());
			new MpException(JsonResultCode.CODE_ORDER_RETURN_METHOD_REFLECT_ERROR,e.getMessage());
		} 
		return true;
	}
	/**
	 * 	补款退款
	 * @param order
	 * @param returnOrder
	 * @param money
	 * @return
	 * @throws MpException
	 */
	public void refundBkMoney(OrderInfoVo order , ReturnOrderRecord returnOrder ,BigDecimal money) throws MpException {
		refundMoneyPaid(order, returnOrder, money);
	}
	/**
	 * 	退会员卡余额
	 * @param order
	 * @param returnOrder
	 * @param money
	 * @throws MpException 
	 */
	public void refundMemberCardBalance(OrderInfoVo order , ReturnOrderRecord returnOrder ,BigDecimal money) throws MpException {
		if(BigDecimalUtil.compareTo(money, null) == 0) {
			return;
		}
		UserCardData userCardData = UserCardData.newBuilder().
		userId(order.getUserId()).
		cardId(order.getCardId()).
		cardNo(order.getCardNo()).
		money(money).
		reason("订单会员卡余额支付退款").
		//普通会员卡
		type(CardConstant.NORMAL_TYPE).
		orderSn(order.getOrderSn()).
		//后台处理时为操作人id为0
		adminUser(0).
		//用户会员卡余额退款
		tradeType(RecordTradeEnum.MEMBER_CARD_ACCOUNT_REFUND.getValue()).
		//资金流量-支出
		tradeFlow(RecordTradeEnum.TRADE_FLOW_OUTCOME.getValue()).build();
		//调用退会员卡接口
		recordMemberTrade.updateUserEconomicData(userCardData);
		//记录
		refundAmountRecord.addRecord(order.getOrderSn(), order.getUserId(), RefundAmountRecordService.MEMBER_CARD_BALANCE, money, returnOrder.getRetId());
	}
	
	/**
	 * 	退余额
	 * @param order
	 * @param returnOrder
	 * @param money
	 * @throws MpException 
	 */
	public void refundUseAccount(OrderInfoVo order , ReturnOrderRecord returnOrder ,BigDecimal money) throws MpException {
		if(BigDecimalUtil.compareTo(money, null) == 0) {
			return;
		}
		AccountData accountData = AccountData.newBuilder().
		userId(order.getUserId()).
		orderSn(order.getOrderSn()).
		//退款金额
		amount(money).
		remark("订单："+order.getOrderSn()+"余额退款").
		payment(order.getPayCode()).
		//支付类型
		isPaid(RecordTradeEnum.RECHARGE.getValue()).
		//后台处理时为操作人id为0
		adminUser(0).
		//用户余额退款
		tradeType(RecordTradeEnum.MEMBER_ACCOUNT_REFUND.getValue()).
		//资金流量-支出
		tradeFlow(RecordTradeEnum.TRADE_FLOW_OUTCOME.getValue()).build();
		//调用退余额接口
		recordMemberTrade.updateUserEconomicData(accountData);
		//记录
		refundAmountRecord.addRecord(order.getOrderSn(), order.getUserId(), RefundAmountRecordService.USE_ACCOUNT, money, returnOrder.getRetId());
	}
	
	/**
	 * 	积分退款
	 * @param order
	 * @param returnOrder
	 * @param money
	 * @return
	 * @throws MpException
	 */
	public void refundScoreDiscount(OrderInfoVo order , ReturnOrderRecord returnOrder ,BigDecimal money) throws MpException {
		if(BigDecimalUtil.compareTo(money, null) == 0) {
			return;
		}
		//金额换算成积分
		Integer score = BigDecimalUtil.multiplyOrDivide(
				BigDecimalPlus.create(new BigDecimal(100), Operator.multiply),
				BigDecimalPlus.create(money,null)
				).intValue();
				
		ScoreData scoreData = ScoreData.newBuilder().
		userId(order.getUserId()).
		orderSn(order.getOrderSn()).
		//退款积分
		score(score).
		remark("订单："+order.getOrderSn()+"退款，退积分：score").
		//后台处理时为操作人id为0
		adminUser(0).
		//用户余额充值 
		tradeType(RecordTradeEnum.POWER_MEMBER_ACCOUNT.getValue()).
		//资金流量-支出
		tradeFlow(RecordTradeEnum.TRADE_FLOW_OUTCOME.getValue()).
		//积分变动是否来自退款
		isFromRefund(RecordTradeEnum.IS_FROM_REFUND_Y.getValue()).build();
		//调用退积分接口
		recordMemberTrade.updateUserEconomicData(scoreData);
		//记录
		refundAmountRecord.addRecord(order.getOrderSn(), order.getUserId(), RefundAmountRecordService.SCORE_DISCOUNT, money, returnOrder.getRetId());
	}
	
	/**
	 * 	微信退款
	 * @param order
	 * @param returnOrder
	 * @param money
	 * @return
	 * @throws MpException
	 */
	public void refundMoneyPaid(OrderInfoVo order , ReturnOrderRecord returnOrder ,BigDecimal money) {
		if(OrderConstant.PAY_WAY_FRIEND_PAYMENT == order.getOrderPayWay()) {
			//TODO 好友代付
		}
		if(OrderConstant.PAY_CODE_WX_PAY.equals(order.getPayCode())) {
			try {
				orderRefundRecord.wxPayRefund(order, returnOrder, money);
			} catch (MpException e) {
				//TODO 微信失败处理
			}
			//交易记录
			tradesRecord.addRecord(money,order.getOrderSn(),order.getUserId(),TradesRecordService.TRADE_CONTENT_MONEY,RecordTradeEnum.CASH_REFUND.getValue(),RecordTradeEnum.TRADE_FLOW_OUTCOME.getValue(),TradesRecordService.TRADE_STATUS_ARRIVAL);	
			//记录
			refundAmountRecord.addRecord(order.getOrderSn(), order.getUserId(), RefundAmountRecordService.MONEY_PAID, money, returnOrder.getRetId());
		}
	}
}

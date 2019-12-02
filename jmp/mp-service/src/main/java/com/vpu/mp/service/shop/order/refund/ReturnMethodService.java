package com.vpu.mp.service.shop.order.refund;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import com.vpu.mp.service.shop.payment.PaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.virtual.VirtualOrderPayInfo;
import com.vpu.mp.service.shop.operation.RecordTradeService;
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
	private RecordTradeService recordMemberTrade;
	@Autowired
	private RefundAmountRecordService refundAmountRecord;
	@Autowired
	private OrderRefundRecordService orderRefundRecord;
	@Autowired
	private TradesRecordService tradesRecord;
    @Autowired
    private OrderInfoService orderInfo;
    @Autowired
    private PaymentRecordService paymentRecord;
    @Autowired
    private MpPaymentService mpPayment;

	/**
	 * 	退款统一入口（微信、余额、积分、卡余额等）
	 * @param methodName 
	 * @param order
	 * @param money
	 * @return
	 * @throws MpException 
	 */
	public boolean refundMethods(String methodName , OrderInfoVo order , Integer retId ,BigDecimal money) throws MpException {
		try {
			//RETURN_METHOD_PREFIX + methodName获取该优先级退款的具体方法
			Method method = getClass().getMethod(FieldsUtil.underLineToCamel(RETURN_METHOD_PREFIX + methodName), OrderInfoVo.class,Integer.class,BigDecimal.class);
			method.invoke(this, order,retId,money);
		} catch (InvocationTargetException e) {
			//反射捕获自定义异常
			Throwable cause = e.getCause();
			if (cause instanceof MpException) {
				throw new MpException(((MpException) cause).getErrorCode(), e.getMessage());
			}else {
				logger().error("退款统一入口调用方法异常(非MpException)retId:"+retId+"。异常信息："+e.getMessage());
				throw new MpException(JsonResultCode.CODE_ORDER_RETURN_METHOD_REFLECT_ERROR, e.getMessage());
			}
		} catch (Exception e) {
			logger().error("退款统一入口调用异常retId:"+retId+"。异常信息："+e.getMessage());
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_METHOD_REFLECT_ERROR,e.getMessage());
		}
		return true;
	}
	/**
	 * 	补款退款
	 * @param order
	 * @param money
	 * @return
	 * @throws MpException
	 */
	public void refundBkMoney(OrderInfoVo order , Integer retId ,BigDecimal money) throws MpException {
		refundMoneyPaid(order, retId, money);
	}
	/**
	 * 	退会员卡余额
	 * @param order
	 * @param money
	 * @throws MpException 
	 */
	public void refundMemberCardBalance(OrderInfoVo order , Integer retId ,BigDecimal money) throws MpException {
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
		
		UserCardData userCardData = UserCardData.newBuilder().
		userId(order.getUserId()).
		cardId(order.getCardId()).
		cardNo(order.getCardNo()).
		money(money).
		reason("订单会员卡余额支付退款").
		//普通会员卡
		type(CardConstant.MCARD_TP_NORMAL).
		orderSn(order.getOrderSn()).
		tradeOpt(tradeOpt).build();
		
		//调用退会员卡接口
		recordMemberTrade.updateUserEconomicData(userCardData);
		//记录
		refundAmountRecord.addRecord(order.getOrderSn(), order.getUserId(), RefundAmountRecordService.MEMBER_CARD_BALANCE, money, retId);
	}
	
	/**
	 * 	退余额
	 * @param order
	 * @param money
	 * @throws MpException 
	 */
	public void refundUseAccount(OrderInfoVo order , Integer retId ,BigDecimal money) throws MpException {
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
		isPaid(RecordTradeEnum.UACCOUNT_RECHARGE.val()).
		//后台处理时为操作人id为0
		adminUser(0).
		//交易类型
		tradeType(RecordTradeEnum.TYPE_CRASH_MACCOUNT_REFUND.val()).
		//资金流量-支出
		tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val()).build();
		//调用退余额接口
		recordMemberTrade.updateUserEconomicData(accountData);
		//记录
		refundAmountRecord.addRecord(order.getOrderSn(), order.getUserId(), RefundAmountRecordService.USE_ACCOUNT, money, retId);
	}
	
	/**
	 * 	积分退款
	 * @param order
	 * @param money
	 * @return
	 * @throws MpException
	 */
	public void refundScoreDiscount(OrderInfoVo order , Integer retId ,BigDecimal money) throws MpException {
		if(BigDecimalUtil.compareTo(money, null) == 0) {
			return;
		}
		//金额换算成积分
		Integer score = BigDecimalUtil.multiplyOrDivide(
				BigDecimalPlus.create(new BigDecimal(OrderConstant.TUAN_FEN_RATIO), Operator.multiply),
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
		//交易类型
		tradeType(RecordTradeEnum.TYPE_SCORE_REFUND.val()).
		//资金流量-支出
		tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val()).
		//积分变动是否来自退款
		isFromRefund(RecordTradeEnum.IS_FROM_REFUND_Y.val()).build();
		//调用退积分接口
		recordMemberTrade.updateUserEconomicData(scoreData);
		//记录
		refundAmountRecord.addRecord(order.getOrderSn(), order.getUserId(), RefundAmountRecordService.SCORE_DISCOUNT, money, retId);
	}
	
	/**
	 * 	微信退款
	 * @param order
	 * @param money
	 * @return
	 * @throws MpException
	 */
	public void refundMoneyPaid(OrderInfoVo order , Integer retId ,BigDecimal money) throws MpException {
		if(OrderConstant.PAY_WAY_FRIEND_PAYMENT == order.getOrderPayWay()) {
			//TODO 好友代付
		}
		if(OrderConstant.PAY_CODE_WX_PAY.equals(order.getPayCode())) {
            logger().info("微信退款（refundMoneyPaid）start,退款金额：{}", money);
            String refundSn = null;
            //支付记录
            PaymentRecordRecord payRecord = null;
            //微信退款结果
            WxPayRefundResult refundResult = null;
            try {
                //子订单取主订单订单号
                String orderSn = orderInfo.isSubOrder(order) ? order.getMainOrderSn() : order.getOrderSn();
                //退款流水号
                refundSn = IncrSequenceUtil.generateOrderSn(OrderConstant.RETURN_SN_PREFIX);
                //支付记录
                payRecord = paymentRecord.getPaymentRecordByOrderSn(orderSn);
                if(payRecord == null) {
                    logger().error("wxPayRefund 微信支付记录未找到 order_sn="+orderSn);
                    throw new MpException(JsonResultCode.CODE_ORDER_RETURN_WXPAYREFUND_NO_RECORD);
                }
                //微信金额单为为分需单位换算
                refundResult = refundByApi(payRecord.getPayCode(), payRecord.getTradeNo(), refundSn,
                    BigDecimalUtil.multiply(payRecord.getTotalFee(), new BigDecimal(Byte.valueOf(OrderConstant.TUAN_FEN_RATIO).toString())).intValue(),
                    BigDecimalUtil.multiply(money, new BigDecimal(Byte.valueOf(OrderConstant.TUAN_FEN_RATIO).toString())).intValue());
                //退款记录
                orderRefundRecord.addRecord(refundSn, payRecord, refundResult, order, retId);
                logger().info("微信退款（refundMoneyPaid）end");
			} catch (MpException e) {
                logger().info("微信退款异常（refundMoneyPaid）");
                orderRefundRecord.addRecord(refundSn, payRecord, refundResult, order, retId);
				throw new MpException(JsonResultCode.CODE_ORDER_RETURN_WX_FAIL);
			}
		}
		if(!OrderConstant.PAY_CODE_COD.equals(order.getPayCode())){
            //交易记录
            tradesRecord.addRecord(money,order.getOrderSn(),order.getUserId(),TradesRecordService.TRADE_CONTENT_MONEY,RecordTradeEnum.TYPE_CASH_REFUND.val(),RecordTradeEnum.TRADE_FLOW_OUT.val(),TradesRecordService.TRADE_STATUS_ARRIVAL);
        }
		//记录
		refundAmountRecord.addRecord(order.getOrderSn(), order.getUserId(), RefundAmountRecordService.MONEY_PAID, money, retId);
	}

    /**
     * 	退款api
     * @param payCode 订单支付code(微信支付)
     * @param tradeNo 微信支付订单号
     * @param returnSn 退款流水号
     * @param totalFee 该笔支付总金额
     * @param money 此次退款金额
     * @throws MpException
     */
    public WxPayRefundResult refundByApi(String payCode ,String tradeNo , String returnSn ,Integer totalFee , Integer money) throws MpException {
        //微信退款
        if(payCode.equals(OrderConstant.PAY_CODE_WX_PAY)){
            try {
                return mpPayment.refundByTransactionId(tradeNo, returnSn, totalFee, money);
            } catch (WxPayException e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                logger().error(sw.toString());
                logger().error(e.getCause().getMessage());
                e.getCause().printStackTrace();
                logger().error("微信退款失败捕获WxPayException：{}", e.getCustomErrorMsg());

                throw new MpException(JsonResultCode.CODE_ORDER_RETURN_WXPAYREFUND_ERROR,e.getMessage());
            }
        }else {
            //TODO 将来扩展其他支付
            throw new MpException(JsonResultCode.CODE_ORDER_RETURN_WXPAYREFUND_ERROR);
        }
    }
	
	/**
	 * 虚拟订单微信退款
	 * @param order
	 * @param money
	 * @throws MpException 
	 */
	public void refundVirtualWx(VirtualOrderPayInfo order , BigDecimal money) throws MpException {
		if(OrderConstant.PAY_CODE_WX_PAY.equals(order.getPayCode())) {
            //退款流水号
            String refundSn = IncrSequenceUtil.generateOrderSn(OrderConstant.RETURN_SN_PREFIX);
            //支付记录
            PaymentRecordRecord payRecord = paymentRecord.getPaymentRecordByOrderSn(order.getOrderSn());
            if(payRecord == null) {
                logger().error("wxPayRefund 微信支付记录未找到 order_sn={}",order.getOrderSn());
                throw new MpException(JsonResultCode.CODE_ORDER_RETURN_WXPAYREFUND_NO_RECORD);
            }
            //微信金额单为为分需单位换算
            refundByApi(payRecord.getPayCode(), payRecord.getTradeNo(), refundSn, payRecord.getTotalFee().intValue() * OrderConstant.TUAN_FEN_RATIO, money.intValue() * OrderConstant.TUAN_FEN_RATIO);
            //TODO
            //addRecord();
		}
		//交易记录
		tradesRecord.addRecord(money,order.getOrderSn(),order.getUserId(),TradesRecordService.TRADE_CONTENT_MONEY,RecordTradeEnum.TYPE_CASH_REFUND.val(),RecordTradeEnum.TRADE_FLOW_OUT.val(),TradesRecordService.TRADE_STATUS_ARRIVAL);	
	}
}

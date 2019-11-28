package com.vpu.mp.service.shop.order.refund.record;

import static com.vpu.mp.db.shop.tables.OrderRefundRecord.ORDER_REFUND_RECORD;

import java.math.BigDecimal;

import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.db.shop.tables.OrderRefundRecord;
import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.RandomUtil;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.virtual.VirtualOrderPayInfo;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import com.vpu.mp.service.shop.payment.PaymentRecordService;

/**
 * 	退款记录表
 * @author 王帅
 *
 */
@Service
public class OrderRefundRecordService extends ShopBaseService{
	
	public final OrderRefundRecord TABLE = ORDER_REFUND_RECORD;
	
	@Autowired
	private OrderInfoService orderInfo;
	@Autowired
	private PaymentRecordService paymentRecord;
	@Autowired
	private MpPaymentService mpPayment;
	/**
	 * 
	 * @param order 
	 * @param money
	 * @throws MpException 
	 */
	public void wxPayRefund(OrderInfoVo order ,Integer retId ,BigDecimal money) throws MpException {
		//子订单取主订单订单号
		String orderSn = orderInfo.isSubOrder(order) ? order.getMainOrderSn() : order.getOrderSn();
		//退款流水号
		String refundSn = IncrSequenceUtil.generateOrderSn(OrderConstant.RETURN_SN_PREFIX);
		//支付记录
		PaymentRecordRecord payRecord = paymentRecord.getPaymentRecordByOrderSn(orderSn);
		if(payRecord == null) {
			logger().error("wxPayRefund 微信支付记录未找到 order_sn="+orderSn);
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_WXPAYREFUND_NO_RECORD);
		}
		//微信金额单为为分需单位换算
		refundByApi(payRecord.getPayCode(), payRecord.getTradeNo(), refundSn, payRecord.getTotalFee().intValue() * OrderConstant.TUAN_TO_FEN, money.intValue() * OrderConstant.TUAN_TO_FEN);
		
		addRecord();
		
		
	}
	
	/**
	 * 虚拟订单微信退款
	 * @param order 
	 * @param money
	 * @throws MpException 
	 */
	public void wxPayRefund(VirtualOrderPayInfo order , BigDecimal money) throws MpException {
		//退款流水号
		String refundSn = IncrSequenceUtil.generateOrderSn(OrderConstant.RETURN_SN_PREFIX);
		//支付记录
		PaymentRecordRecord payRecord = paymentRecord.getPaymentRecordByOrderSn(order.getOrderSn());
		if(payRecord == null) {
			logger().error("wxPayRefund 微信支付记录未找到 order_sn={}",order.getOrderSn());
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_WXPAYREFUND_NO_RECORD);
		}
		//微信金额单为为分需单位换算
		refundByApi(payRecord.getPayCode(), payRecord.getTradeNo(), refundSn, payRecord.getTotalFee().intValue() * OrderConstant.TUAN_TO_FEN, money.intValue() * OrderConstant.TUAN_TO_FEN);
		
		addRecord();
		
		
	}
	
	/**
	 * 	退款api
	 * @param payCode
	 * @param tradeNo
	 * @param returnSn
	 * @param totalFee
	 * @param money
	 * @return
	 * @throws MpException 
	 */
	public void refundByApi(String payCode ,String tradeNo , String returnSn ,Integer totalFee , Integer money) throws MpException {
		//微信退款
		if(payCode.equals(OrderConstant.PAY_CODE_WX_PAY)){
			try {
				mpPayment.refundByTransactionId(tradeNo, returnSn, totalFee, money);
			} catch (WxPayException e) {
				//TODO 增加类型判断并记录
				throw new MpException(JsonResultCode.CODE_ORDER_RETURN_WXPAYREFUND_ERROR,e.getMessage());
			} catch (Exception e) {
				throw new MpException(JsonResultCode.CODE_ORDER_RETURN_WXPAYREFUND_ERROR,e.getMessage());
			}
		}else {
			throw new MpException(JsonResultCode.CODE_ORDER_RETURN_WXPAYREFUND_ERROR);
		}
	}
	
	public void addRecord() {
		
	}
}

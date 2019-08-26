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
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.pay.record.PaymentRecordVo;
import com.vpu.mp.service.shop.payment.PaymentRecordService;
/**
 * 
 * @author 王帅
 *
 */
@Component
public class ReturnMethodService extends ShopBaseService{
	@Autowired
	private ReturnOrderService returnOrder;
	@Autowired
	private PaymentRecordService paymentRecord;
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
		Object invoke = null;
		try {
			Method method = getClass().getMethod(methodName, OrderInfoVo.class,ReturnOrderRecord.class,BigDecimal.class);
			invoke = method.invoke(this, order,returnOrder,money);
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
		return (boolean)invoke;
	}
	
	public boolean refundBkMoney(OrderInfoVo order , ReturnOrderRecord returnOrder ,BigDecimal money) throws MpException {
		//目前补款都是微信支付
		if(OrderConstant.PAY_CODE_WX_PAY.equals(order.getPayCode())) {
			wxReturnOrderMoney(order.getBkOrderSn(), money, returnOrder.getRetId());
		}
		
		return false;
	}
	
	public void wxReturnOrderMoney(String orderSn , BigDecimal money , Integer retId) throws MpException {
		PaymentRecordVo payRecords = paymentRecord.getPaymentRecordByOrderSn(orderSn).into(PaymentRecordVo.class);
		if(payRecords == null) {
			logger().error("wxPayRefund微信支付记录未找到 order_sn:"+orderSn);
			throw new MpException(JsonResultCode.CODE_ORDER_WX_RETURN_FAIL);
		}
		//退款流水号
		String returnSn = returnOrder.generateReturnOrderSn();
		//TODO 微信退款返回对象目前模拟
		if(!refundByApi(payRecords.getPayCode(), payRecords.getTradeNo(), returnSn, payRecords.getTotalFee(), money)) {
			//返回return_code
		}
	}
	
	public boolean refundByApi(String payCode ,String tradeNo , String returnSn ,BigDecimal totalFee , BigDecimal money) {
		//TODO
		return false;
	}
	
}

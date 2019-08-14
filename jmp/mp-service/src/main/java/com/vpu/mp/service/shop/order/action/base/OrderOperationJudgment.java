package com.vpu.mp.service.shop.order.action.base;

import java.math.BigDecimal;

import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;

/**
 * 	订单操作判断
 * @author 王帅
 *
 */

public class OrderOperationJudgment {
	/**
	 * 	订单是否可退款
	 * @param order
	 * @param isMp true小程序；false后台
	 * @return true可退；false不可退
	 */
	public static boolean isReturnMoney(OrderListInfoVo order , Boolean isMp) {
		//后台权限大,不支持退的也可以退
		if(order.getReturnTypeCfg().equals(OrderConstant.CFG_RETURN_TYPE_N) && isMp) {
			return false;
		}
		/**
		 * 1退款支持状态:待发货 已发货 已收货;
		 * 2货到付款设置支付金额为0,然后判断支付金额是否>0
		 * 3余额支付判断积分抵扣金额，余额消费金额，会员卡消费金额，子单金额
		 */
		if(OrderConstant.isReturnMoney.contains(order.getOrderStatus())
				&& !isPayZero(order)) {
			return true;					
		}
		// 货到付款加确认收货可仅退款
		if (OrderConstant.ORDER_RECEIVED == order.getOrderStatus()
				&& OrderConstant.PAY_CODE_COD.equals(order.getPayCode())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 	是否可退货退款，卖家操作
	 * @param order
	 * @return true可退；false不可退
	 */
	public static boolean adminIsReturnGoods(OrderListInfoVo order) {
		//TODO 此功能为卖家专用，现后台退部分发货已发货不支持，后期做 
		//已收货,已完成状态
		if(OrderConstant.isReturnGoods.contains(order.getOrderStatus())){
			return true;
		}
		return false;
	}
	
	/**
	 * 	是否可退货退款，买家操作
	 * @param order
	 * @return true可退；false不可退
	 */
	public static boolean mpIsReturnGoods(OrderListInfoVo order) {
		//商家是否支持
		if(order.getReturnTypeCfg().equals(OrderConstant.CFG_RETURN_TYPE_N)) {
			return false;
		}
		//部分发货 已发货 已收货 (取消发货后有支付才可以退)
		if((OrderConstant.ORDER_WAIT_DELIVERY == order.getOrderStatus() && OrderConstant.PART_SHIP  == order.getPartShipFlag())
				||OrderConstant.ORDER_SHIPPED == order.getOrderStatus()
				||OrderConstant.ORDER_RECEIVED == order.getOrderStatus()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 	退运费，买家操作
	 * @param order
	 * @return true可退；false不可退
	 */
	public static Boolean adminIsReturnShipingFee(OrderListInfoVo order , BigDecimal returnShipingFee) {
		if(BigDecimalUtil.compareTo(order.getShippingFee(), returnShipingFee) < 1) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * 	判断当前订单是否支付为0：
	 * 	余额支付：积分抵扣金额，余额消费金额，会员卡消费金额，子单金额（
	 * 	货到付款：设置支付金额moneyPaid为0）
	 * @param order
	 * @return
	 */
	public static boolean isPayZero(OrderListInfoVo order) {
		if(BigDecimalUtil.compareTo(OrderConstant.PAY_CODE_COD.equals(order.getPayCode()) ? BigDecimal.ZERO : order.getMoneyPaid(),BigDecimal.ZERO) > 1
				||((OrderConstant.PAY_CODE_BALANCE_PAY.equals(order.getPayCode()) 
						&& (BigDecimalUtil.compareTo(order.getScoreDiscount(),BigDecimal.ZERO) > 1
						||  BigDecimalUtil.compareTo(order.getUseAccount(),BigDecimal.ZERO) > 1
						||  BigDecimalUtil.compareTo(order.getMemberCardBalance(),BigDecimal.ZERO) > 1
						||  BigDecimalUtil.compareTo(order.getSubGoodsPrice(),BigDecimal.ZERO) > 1 )))) {
			return true;
		}
		return false;
	}
	
	public static boolean isReturnGoods(OrderListInfoVo order , Boolean isMp) {
		if(isMp) {
			//判断mp是否可以退货
			if(mpIsReturnGoods(order)) {
				return true;
			}
		}else {
			//后台是否可以退货
			if(adminIsReturnGoods(order)) {
				return true;
			}
		}
		return false;
	}
}

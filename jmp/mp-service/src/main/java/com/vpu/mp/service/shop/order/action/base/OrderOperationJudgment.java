package com.vpu.mp.service.shop.order.action.base;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.BigDecimalPlus;
import com.vpu.mp.service.foundation.util.BigDecimalUtil.Operator;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.goods.OrderGoodsVo;
import com.vpu.mp.service.pojo.shop.order.mp.order.OrderListMpVo;

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
	public static boolean isReturnMoney(OrderListInfoVo order , Byte isMp) {
		//后台权限大,不支持退的也可以退
		if(OrderConstant.CFG_RETURN_TYPE_N == order.getReturnTypeCfg() && (isMp == OrderConstant.IS_MP_Y)) {
			return false;
		}
		/**
		 * 1退款支持状态:待发货 已发货 已收货;
		 * 2货到付款设置支付金额为0,然后判断支付金额是否>0
		 * 3余额支付判断积分抵扣金额，余额消费金额，会员卡消费金额，子单金额
		 */
		if(OrderConstant.IS_RETURNMONEY.contains(order.getOrderStatus())
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
		if(OrderConstant.IS_RETURNGOODS.contains(order.getOrderStatus())){
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
	 * @param returnShipingFee 已退运费
	 * @return true可退；false不可退
	 */
	public static Boolean adminIsReturnShipingFee(OrderListInfoVo order , BigDecimal returnShipingFee) {
		if(BigDecimalUtil.compareTo(order.getShippingFee(), returnShipingFee) <= 0) {
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
	/**
	 * 退货判断
	 * @param order
	 * @param isMp
	 * @return
	 */
	public static boolean isReturnGoods(OrderListInfoVo order , Byte isMp) {
		if(isMp == OrderConstant.IS_MP_Y) {
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
	
	/**
	 * 订单是否可以取消
	 * @param order
	 * @return
	 */
	public static boolean mpIsCancel(OrderListInfoVo order) {
		if(//1待付款  且  无补款或补款未支付
			(order.getOrderStatus() == OrderConstant.ORDER_WAIT_PAY && order.getBkOrderPaid() == OrderConstant.BK_PAID_N)
			//2待发货  且 余额支付  且 系统金额为0(。。。不可以取消)
			//|| order.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY && order.getPayCode() == OrderConstant.PAY_CODE_BALANCE_PAY && BigDecimalUtil.compareTo(getOnlinePayAmount(order), null) == 0
			//3待发货  且  货到付款(。。。不可以取消)
			//|| order.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY && order.getPayCode() == OrderConstant.PAY_CODE_COD
			) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取系统金额
	 * @param order
	 * @return
	 */
	public static BigDecimal getOnlinePayAmount(OrderListInfoVo order) {
		return BigDecimalUtil.addOrSubtrac(
				BigDecimalPlus.create(OrderConstant.PAY_CODE_COD.equals(order.getPayCode()) ? BigDecimal.ZERO : order.getMoneyPaid(), Operator.add),
				BigDecimalPlus.create(order.getScoreDiscount() , Operator.add),
				BigDecimalPlus.create(order.getMemberCardBalance() , Operator.add),
				BigDecimalPlus.create(order.getUseAccount() , null));
	}
	
	/**
	 * 订单是否可以关闭
	 * @param order
	 * @return
	 */
	public static boolean mpIsClose(OrderListInfoVo order) {
		if(//待支付不存在补款或补款未支付
			order.getOrderStatus() == OrderConstant.ORDER_WAIT_PAY && order.getBkOrderPaid() == OrderConstant.BK_PAID_N
			//待发货 且 货到付款 且 系统支付0
			|| order.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY && BigDecimalUtil.compareTo(getOnlinePayAmount(order), null) == 0 && order.getPayCode() == OrderConstant.PAY_CODE_COD) {
			return true;
		}
		return false;
	}
	
	/**
	 * 订单是否可以完成
	 * @param order
	 * @return
	 */
	public static boolean mpIsFinish(OrderListInfoVo order , Integer isReturnCount) {
		if(order.getOrderStatus() == OrderConstant.ORDER_RECEIVED) {
			if(order.getReturnTypeCfg() == OrderConstant.CFG_RETURN_TYPE_N) {
				return true;
			}
			if(isReturnCount != null && isReturnCount > 0) {
				return false;
			}
			if(order.getReturnTypeCfg() == OrderConstant.CFG_RETURN_TYPE_Y
					&& Instant.now().isAfter(order.getConfirmTime().toInstant().plusSeconds(Duration.ofDays(order.getOrderTimeoutDays()).getSeconds()))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 订单是否可以核销
	 * @param order
	 * @return
	 */
	public static boolean isVerify(OrderListInfoVo order) {
		if(order.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY && order.getDeliverType() == OrderConstant.DELIVER_TYPE_SELF) {
			return true;
		}
		return false;
	}
	
	public static void operationSet(OrderListInfoVo order , Integer isReturnCount , List<OrderGoodsVo> canBeShippedList) {
		//设置所有订单是否可以关闭
		if(OrderOperationJudgment.mpIsClose(order)) {
			order.setCanClose(Boolean.TRUE);
		}
		//设置所有订单是否可以完成
		if(OrderOperationJudgment.mpIsFinish(order , isReturnCount)) {
			order.setCanFinish(Boolean.TRUE);
		}
		//待发货状态判断是否可发货
		if(order.getOrderStatus() == OrderConstant.ORDER_WAIT_DELIVERY) {
			if(CollectionUtils.isNotEmpty(canBeShippedList)) {
				order.setCanDeliver(Boolean.TRUE);
			}
		}
		//判断核销
		if(OrderOperationJudgment.isVerify(order)) {
			order.setCanVerify(Boolean.TRUE);
		}
	}
	
	/**
	 * 延长收货
	 * @param order
	 * @param extendReceiveDays
	 * @return
	 */
	public static Boolean isExtendReceive(OrderListMpVo order , int extendReceiveDays) {
		if(extendReceiveDays >0 && order.getOrderStatus() == OrderConstant.ORDER_SHIPPED) {
			//自动收货时间
			Instant autoReceive = order.getShippingTime().toInstant().plusSeconds(Duration.ofDays(order.getReturnDaysCfg()).getSeconds());
			//可延迟2天收货
			if(Instant.now().plusSeconds(Duration.ofDays(2).getSeconds()).isAfter(autoReceive)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public static void operationSet(OrderListMpVo order) {
		
	}
}

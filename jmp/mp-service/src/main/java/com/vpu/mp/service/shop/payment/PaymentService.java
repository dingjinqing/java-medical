package com.vpu.mp.service.shop.payment;

import static com.vpu.mp.db.shop.tables.Payment.PAYMENT;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import com.vpu.mp.service.pojo.shop.payment.PaymentVo;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.PaymentRecord;
import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.payment.PayCode;
import com.vpu.mp.service.pojo.shop.payment.PaymentRecordParam;
import com.vpu.mp.service.shop.order.info.OrderInfoService;

@Service
public class PaymentService extends ShopBaseService {

	@Autowired
	public PaymentRecordService record;

	@Autowired
	public OrderInfoService order;

	@Autowired
	public MpPaymentService mpPay;

	public PaymentRecord getPaymentInfo(String payCode) {
		return db().fetchAny(PAYMENT, PAYMENT.PAY_CODE.eq(payCode));
	}

	/**
	 * 设置支付方式开启状态
	 * 
	 * @param payCode 支付方式
	 * @param enabled 是否开启
	 */
	public void switchPayStatus(String payCode, Byte enabled) {
		db().update(PAYMENT).set(PAYMENT.ENABLED, enabled).where(PAYMENT.PAY_CODE.eq(payCode));
	}

	/**
	 * 得到所有支付方式
	 * 
	 * @return
	 */
	public Result<PaymentRecord> getPayment() {
		return db().fetch(PAYMENT);
	}

	/**
	 * 得到支持的支付方式
	 * 
	 * @return
	 */
	public Map<String, PaymentVo> getSupportPayment() {
		String[] payCodes = { PayCode.PAY_CODE_WX_PAY, PayCode.PAY_CODE_COD };
        return db().selectFrom(PAYMENT)
            .where(PAYMENT.ENABLED.eq(PayCode.PAY_CODE_ENABLED)
                .and(PAYMENT.PAY_CODE.in(payCodes)))
            .orderBy(PAYMENT.PAY_CODE.desc())
            .fetchMap(PAYMENT.PAY_CODE, PaymentVo.class);
    }

	/**
	 * 统一订单支付回调
	 */
	public void unionPayNotify(PaymentRecordParam param) {
		// TODO: 统一支付回调
	}

	/**
	 * 
	 * @param param
	 * @throws WxPayException
	 */
	protected void onPayNotify(PaymentRecordParam param) throws WxPayException {
		Byte orderPayFull = 0;
		Byte orderPayHasPreMoney = 1;
		Byte notPaid = 0;
		Byte preMondyPaid = 1;
		Byte bkMoneyPaid = 2;
		String orderSn = param.getOrderSn().split("_")[0];
		param.setOrderSn(orderSn);

		OrderInfoRecord orderInfo = order.getOrderByOrderSn(orderSn);
		if (orderInfo == null) {
			throw new WxPayException("orderSn " + orderSn + "not found");
		}
		String[] goodsTypes = orderInfo.getGoodsType().split(",");
		BigDecimal totalFee = new BigDecimal(param.getTotalFee());

		// 全款支付，且金额不相同，则抛出异常
		if (orderInfo.getMoneyPaid() != totalFee && orderInfo.getOrderPayWay().equals(orderPayFull)) {
			throw new WxPayException("onPayNotify orderSn " + orderSn + " pay amount  did not match");
		}

		// 订单状态已经是支付后状态，则直接返回
		if (orderInfo.getOrderStatus() >= OrderConstant.ORDER_WAIT_DELIVERY) {
			logger().info("onPayNotify orderSn {} has paied", orderSn);
			return;
		}

		// TODO: 如果为欧派或者寺库，订单推送，可以尝试消息队列

		// 补款订单，订单号为补款订单号
		if (!orderInfo.getBkOrderPaid().equals(bkMoneyPaid)) {
			param.setOrderSn(orderInfo.getBkOrderSn());
		}

		// 添加支付记录
		PaymentRecordRecord paymentRecord = record.addPaymentRecord(param);

		// 订单状态处理
		if (Arrays.asList(goodsTypes).contains(String.valueOf(OrderConstant.GOODS_TYPE_PRE_SALE))) {
			/**
			 * 预售单独处理,先支付定金，后支付尾款 1. 未支付时，如果为定金支付，则BK_ORDER_PAID置为1(定金已支付)，
			 * 否则为全款支付，则直接BK_ORDER_PAID置为2(尾款已支付)，状态变为待发货 最后修改相应预售商品数量销量库存 2.
			 * 已支付定金状态时，则直接BK_ORDER_PAID置为2(尾款已支付)，状态变为待发货
			 */
			if (orderInfo.getBkOrderPaid().equals(notPaid)) {
				// 未支付时
				if (orderInfo.getOrderPayWay().equals(orderPayHasPreMoney)) {
					// 定金尾款支付方式时，先标记定金已支付
					db().update(order.TABLE).set(order.TABLE.BK_ORDER_PAID, preMondyPaid)
							.where(order.TABLE.ORDER_ID.eq(orderInfo.getOrderId()))
							.execute();
				} else {
					// 全款支付方式时，则直接标记为尾款已支付
					db().update(order.TABLE).set(order.TABLE.BK_ORDER_PAID, bkMoneyPaid)
							.where(order.TABLE.ORDER_ID.eq(orderInfo.getOrderId()))
							.execute();
					/**
					 * 状态变为待发货 TODO: order.waitDeliver();
					 */
				}
				/**
				 * 修改相应预售商品数量销量库存 TODO: preSale.preSaleProduct.updateNumber(orderInfo, -1);
				 */
			} else {
				// 定金已支付，标记为尾款已支付
				db().update(order.TABLE).set(order.TABLE.BK_ORDER_PAID, bkMoneyPaid)
						.where(order.TABLE.ORDER_ID.eq(orderInfo.getOrderId()))
						.execute();
				/**
				 * 状态变为待发货 TODO: order.waitDeliver();
				 */
			}
		} else {
			/**
			 * 状态变为待发货 TODO: order.waitDeliver();
			 */
		}

		/**
		 * TODO:POS推送订单
		 */

		/**
		 * TODO: 好友助力--支付完成修改助力进度
		 */

		/**
		 * TODO: 分销订单发送返利模板消息
		 */

	}
}

package com.vpu.mp.service.shop.payment;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.PaymentRecord;
import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.payment.PayCode;
import com.vpu.mp.service.pojo.shop.payment.PaymentRecordParam;
import com.vpu.mp.service.pojo.shop.payment.PaymentVo;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.Payment.PAYMENT;

@Service
public class PaymentService extends ShopBaseService {

	@Autowired
	public PaymentRecordService record;

	@Autowired
	public OrderInfoService order;

	@Autowired
	public MpPaymentService mpPay;

	public PaymentVo getPaymentInfo(String payCode) {
		return db().select(PAYMENT.asterisk()).from(PAYMENT).where(PAYMENT.PAY_CODE.eq(payCode)).fetchOneInto(PaymentVo.class);
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
        return db().selectFrom(PAYMENT)
            .where(PAYMENT.ENABLED.eq(PayCode.PAY_CODE_ENABLED))
            .orderBy(PAYMENT.PAY_CODE.desc())
            .fetchMap(PAYMENT.PAY_CODE, PaymentVo.class);
    }

	/**
	 * 统一订单支付回调
	 */
	public void unionPayNotify(PaymentRecordParam param) {
        String orderSn = param.getOrderSn();
        String prefix = orderSn.substring(0,1);
        switch (prefix) {
            //TODO 订单根据前缀判断处理类型,将字面量替换为对应常量
            case "S":
                //服务订单统一支付回调
                break;
            case "D":
                //门店买单订单统一支付回调
                break;
            case "C":
                //会员卡充值订单统一支付回调
                break;
            case "M":
                //会员卡充值订单统一支付回调
                break;
            case "T":
                //代付/子订单统一支付回调
                break;
            case OrderConstant.ORDER_SN_PREFIX:
                //订单统一支付回调
                try {
                    onPayNotify(param);
                } catch (WxPayException e) {
                    e.printStackTrace();
                }
                break;
            default:
                return;
        }

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
		if (orderInfo.getMoneyPaid().equals(totalFee) && orderInfo.getOrderPayWay().equals(orderPayFull)) {
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

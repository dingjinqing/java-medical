package com.vpu.mp.service.shop.payment;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.PaymentRecord;
import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.db.shop.tables.records.ServiceOrderRecord;
import com.vpu.mp.db.shop.tables.records.StoreOrderRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.payment.PayCode;
import com.vpu.mp.service.pojo.shop.payment.PaymentRecordParam;
import com.vpu.mp.service.pojo.shop.payment.PaymentVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.activity.factory.OrderCreateMpProcessorFactory;
import com.vpu.mp.service.shop.activity.processor.PayAwardProcessor;
import com.vpu.mp.service.shop.order.action.PayService;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.store.StoreOrderService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Payment.PAYMENT;
import static com.vpu.mp.service.pojo.wxapp.store.StoreConstant.STORE_ORDER_SN_PREFIX;
import static com.vpu.mp.service.pojo.wxapp.store.StoreConstant.STORE_SERVICE_ORDER_SN_PREFIX;
import static com.vpu.mp.service.shop.store.service.ServiceOrderService.ORDER_STATUS_WAIT_PAY;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
public class PaymentService extends ShopBaseService {

	@Autowired
	public PaymentRecordService record;

	@Autowired
	public OrderInfoService order;

	@Autowired
	public MpPaymentService mpPay;

    @Autowired
    public PayService pay;
    @Autowired
    private PayAwardProcessor payAwardProcessor;
    @Autowired
    private OrderGoodsService orderGoodsService;
	/**
	 * 营销活动processorFactory
	 */
	@Autowired
	private OrderCreateMpProcessorFactory marketProcessorFactory;

    /**
     * The Service order service.门店服务订单
     */
    @Autowired
    public ServiceOrderService serviceOrderService;

    @Autowired
    private StoreOrderService storeOrder;

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
    public void unionPayNotify(PaymentRecordParam param) throws MpException, WxPayException {
        String orderSn = param.getOrderSn();
        String prefix = orderSn.substring(0,1);
        switch (prefix) {
            //TODO 订单根据前缀判断处理类型,将字面量替换为对应常量
            case STORE_SERVICE_ORDER_SN_PREFIX:
                //服务订单统一支付回调
                onPayNotifyService(param);
                break;
            case STORE_ORDER_SN_PREFIX:
                //门店买单订单统一支付回调
                onPayNotifyStore(param);
                break;
            case "C":
                //会员卡充值订单统一支付回调
                break;
            case "M":
                //购买会员卡虚拟订单统一支付回调
                break;
            case "T":
                //代付/子订单统一支付回调
                break;
            case OrderConstant.ORDER_SN_PREFIX:
                //订单统一支付回调
                onPayNotify(param);
                break;
            default:
                return;
        }

	}

	/**
     * 订单统一支付回调业务处理
	 * @param param
	 * @throws WxPayException
	 */
	protected void onPayNotify(PaymentRecordParam param) throws MpException {

		String orderSn = param.getOrderSn().split("_")[0];
		param.setOrderSn(orderSn);

		OrderInfoRecord orderInfo = order.getOrderByOrderSn(orderSn);
		if (orderInfo == null) {
            logger().error("订单统一支付回调,未找到订单sn:{}", orderSn);
			throw new MpException(JsonResultCode.CODE_ORDER_NOT_EXIST, "orderSn " + orderSn + "not found");
		}
		String[] goodsTypes = OrderInfoService.orderTypeToArray(orderInfo.getGoodsType());
		BigDecimal totalFee = new BigDecimal(param.getTotalFee());

		// 全款支付，且金额不相同，则抛出异常
		if (!orderInfo.getMoneyPaid().equals(totalFee) && orderInfo.getOrderPayWay().equals(OrderConstant.PAY_WAY_FULL)) {
            logger().error("订单统一支付回调,全款支付但金额不相同,订单sn:{},参数金额:{},订单金额:{}",
                orderSn, totalFee, orderInfo.getMoneyPaid());
			throw new MpException(null, "onPayNotify orderSn " + orderSn + " pay amount  did not match");
		}

		// 订单状态已经是支付后状态，则直接返回
		if (orderInfo.getOrderStatus() >= OrderConstant.ORDER_WAIT_DELIVERY) {
			logger().info("onPayNotify orderSn {} has paied", orderSn);
			return;
		}

		// TODO: 如果为欧派或者寺库，订单推送，可以尝试消息队列

		// 补款订单，订单号为补款订单号
		if (OrderConstant.BK_PAY_FRONT == orderInfo.getBkOrderPaid()) {
			param.setOrderSn(orderInfo.getBkOrderSn());
		}

		// 添加支付记录（wx）
        PaymentRecordRecord paymentRecord = record.addPaymentRecord(param);

		// 订单状态处理
		if (Arrays.asList(goodsTypes).contains(String.valueOf(OrderConstant.GOODS_TYPE_PRE_SALE))) {
			/**
			 * 预售单独处理,先支付定金，后支付尾款 1. 未支付时，如果为定金支付，则BK_ORDER_PAID置为1(定金已支付)，
			 * 否则为全款支付，则直接BK_ORDER_PAID置为2(尾款已支付)，状态变为待发货 最后修改相应预售商品数量销量库存 2.
			 * 已支付定金状态时，则直接BK_ORDER_PAID置为2(尾款已支付)，状态变为待发货
			 */
			if (orderInfo.getBkOrderPaid() == OrderConstant.BK_PAY_NO) {
				// 未支付时
				if (orderInfo.getOrderPayWay() == OrderConstant.PAY_WAY_DEPOSIT) {
					// 定金尾款支付方式时，先标记定金已支付
                    orderInfo.setBkOrderPaid(OrderConstant.BK_PAY_FRONT);
				} else {
					// 全款支付方式时，则直接标记为尾款已支付
                    orderInfo.setBkOrderPaid(OrderConstant.BK_PAY_FINISH);
					//状态变为待发货
					pay.toWaitDeliver(orderInfo, paymentRecord);
				}
				/**
				 * 修改相应预售商品数量销量库存 TODO: preSale.preSaleProduct.updateNumber(orderInfo, -1);
				 */
			} else {
				// 定金已支付，标记为尾款已支付
                orderInfo.setBkOrderPaid(OrderConstant.BK_PAY_FINISH);
				//状态变为待发货
                pay.toWaitDeliver(orderInfo, paymentRecord);
			}
		} else {
			//状态变为待发货
            pay.toWaitDeliver(orderInfo, paymentRecord);
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

	/**
	 *  支付活动
	 * @param param
	 * @param orderInfo
	 * @throws MpException
	 */
	private void payAwardActivity(PaymentRecordParam param, OrderInfoRecord orderInfo) throws MpException {
		if (!orderInfo.getOrderStatus().equals(OrderConstant.ORDER_WAIT_DELIVERY)){
			return;
		}
		String[] strings = OrderInfoService.orderTypeToArray(orderInfo.getGoodsType());
		List<Byte> activityTypeList = Arrays.stream(strings).map(Byte::valueOf).collect(Collectors.toList());
		Byte activityType = OrderCreateMpProcessorFactory.SINGLENESS_ACTIVITY.stream().filter(activityTypeList::contains).findFirst().get();
		OrderBeforeParam orderBeforeParam =new OrderBeforeParam();
		orderBeforeParam.setActivityType(activityType);
		orderBeforeParam.setActivityId(orderInfo.getActivityId());
		orderBeforeParam.setDate(param.getCreated());
		orderBeforeParam.setGoods(new ArrayList<>());
		List<GoodsRecord> orderGoods = orderGoodsService.getGoodsInfoRecordByOrderSn(orderInfo.getOrderSn());
		orderGoods.forEach(orderGood->{
			OrderBeforeParam.Goods goods = new OrderBeforeParam.Goods();
			goods.setGoodsId(orderGood.getGoodsId());
			goods.setGoodsInfo(orderGood);
			orderBeforeParam.getGoods().add(goods);
		});
		marketProcessorFactory.processOrderEffective(orderBeforeParam,orderInfo);
	}

    /**
     * On pay notify store.门店买单订单统一支付回调
     *
     * @param param the param
     */
    private void onPayNotifyStore(PaymentRecordParam param) throws WxPayException {
        String orderSn = param.getOrderSn();
        StoreOrderRecord orderInfo = storeOrder.fetchStoreOrder(orderSn);
        if (Objects.isNull(orderInfo)) {
            logger().error("门店买单订单统一支付回调（onPayNotifyStore）：买单订单【订单号：{}】不存在！", orderSn);
            throw new WxPayException("onPayNotifyStore：orderSn 【" + orderSn + "】not found ！");
        }
        if (NumberUtils.createBigDecimal(param.getTotalFee()).compareTo(orderInfo.getMoneyPaid()) != INTEGER_ZERO) {
            logger().error("门店买单订单统一支付回调（onPayNotifyStore）：订单【订单号：{}】实付金额不符【系统计算金额：{} != 微信支付金额：{}】！", orderSn, orderInfo.getMoneyPaid(), param.getTotalFee());
            throw new WxPayException("onPayNotifyStore：orderSn 【 " + orderSn + "】 pay amount  did not match ！");
        }
        if (BYTE_ONE.equals(orderInfo.getOrderStatus())) {
            logger().info("门店买单订单统一支付回调（onPayNotifyStore）：订单【订单号：{}】已支付！", orderSn);
            return;
        }
        // 添加支付记录（wx）
        PaymentRecordRecord paymentRecord = record.addPaymentRecord(param);
        // 完成支付
        storeOrder.finishPayCallback(orderInfo, paymentRecord);
        logger().info("门店买单订单统一支付回调SUCCESS完成！");
    }

    /**
     * On pay notify service.
     *
     * @param param the param
     * @throws WxPayException the wx pay exception
     */
    public void onPayNotifyService(PaymentRecordParam param) throws WxPayException {
        String orderSn = param.getOrderSn();
        ServiceOrderRecord orderInfo = serviceOrderService.getRecord(orderSn);
        if (Objects.isNull(orderInfo)) {
            logger().error("服务订单统一支付回调（onPayNotifyService）：订单【订单号：{}】不存在！", orderSn);
            throw new WxPayException("onPayNotifyStore：orderSn 【" + orderSn + "】not found ！");
        }
        if (NumberUtils.createBigDecimal(param.getTotalFee()).compareTo(orderInfo.getMoneyPaid()) != INTEGER_ZERO) {
            logger().error("服务订单统一支付回调（onPayNotifyService）：订单【订单号：{}】实付金额不符【系统计算金额：{} != 微信支付金额：{}】！", orderSn, orderInfo.getMoneyPaid(), param.getTotalFee());
            throw new WxPayException("onPayNotifyStore：orderSn 【 " + orderSn + "】 pay amount  did not match ！");
        }
        if (!ORDER_STATUS_WAIT_PAY.equals(orderInfo.getOrderStatus())) {
            logger().info("服务订单统一支付回调（onPayNotifyService）：订单【订单号：{}】已支付！", orderSn);
            return;
        }
        // 添加支付记录（wx）
        PaymentRecordRecord paymentRecord = record.addPaymentRecord(param);
        // 完成支付
        serviceOrderService.finishPayCallback(orderInfo, paymentRecord);
        logger().info("服务订单统一支付回调SUCCESS完成！");
    }
}

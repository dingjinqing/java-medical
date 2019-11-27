package com.vpu.mp.service.shop.payment;

import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.util.SignUtils;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.payment.PayCode;
import com.vpu.mp.service.pojo.shop.payment.PaymentRecordParam;
import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import com.vpu.mp.service.pojo.wxapp.pay.jsapi.JsApiVo;
import com.vpu.mp.service.wechat.WxPayment;
import com.vpu.mp.support.PemToPkcs12;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.*;
import static com.vpu.mp.service.shop.order.store.StoreOrderService.HUNDRED;

@Service
public class MpPaymentService extends ShopBaseService {

	@Autowired
	protected DomainConfig domainConfig;

	@Autowired
	public PaymentService pay;

	/**
	 * 得到店铺小程序支付实例
     *
	 * @return
	 */
	public WxPayment getMpPay() {
		MpAuthShopRecord mp = getMpAuthShop();
		WxPayConfig wxPayConfig = this.getWxPayConfig(mp);
		WxPayment wxPayment = new WxPayment();
		wxPayment.setConfig(wxPayConfig);
		return wxPayment;
	}

	/**
	 * 得到当前店铺小程序授权信息
     *
	 * @return
	 */
	public MpAuthShopRecord getMpAuthShop() {
		return saas.shop.mp.getAuthShopByShopId(getShopId());
	}

	/**
	 * 小程序支付配置
     *
	 * @param mp
	 * @return
	 */
	protected WxPayConfig getWxPayConfig(MpAuthShopRecord mp) {
		byte[] keyContent = null;
		try {
			keyContent = PemToPkcs12.pemToPkcs12(mp.getPayKeyContent(), mp.getPayCertContent());
		} catch (Exception e) {
			this.logger().error("pemToPkcs12 error, message: {}", e.getMessage());
		}
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(mp.getAppId());
		payConfig.setMchId(mp.getPayMchId());
		payConfig.setMchKey(mp.getPayKey());
		payConfig.setKeyContent(keyContent);
		return payConfig;
	}

	/**
	 * 统一下单
     *
	 * @param clientIp  客户IP
	 * @param goodsName 商品名称
	 * @param orderSn   订单号
	 * @param amount    单位分
	 * @param openId    用户OpenId
	 * @return
	 * @throws WxPayException
	 */
    public WebPayVo wxUnitOrder(String clientIp, String goodsName, String orderSn, BigDecimal amount, String openId) throws WxPayException, MpException {
        logger().info("微信预支付调用接口start,clientIp:{},goodsName:{},orderSn:{},amount:{},openId:{}", clientIp,  goodsName,  orderSn,  amount,  openId);
		WxPayment wxPayment = this.getMpPay();
		WxPayUnifiedOrderRequest payInfo = WxPayUnifiedOrderRequest.newBuilder()
				.openid(openId)
				.outTradeNo(orderSn)
            // 订单总金额，单位为分
            .totalFee(amount.multiply(HUNDRED).intValue())
				.body(goodsName)
				.tradeType(WxPayConstants.TradeType.JSAPI)
				.spbillCreateIp(clientIp)
				.notifyUrl(domainConfig.getWxMaPayNotifyUrl(this.getShopId()))
				.build();
		this.logger().info("PartnerKey is : {}", wxPayment.getConfig().getMchKey());
		//已经校验
		WxPayUnifiedOrderResult result = wxPayment.unifiedOrder(payInfo);
		this.logger().info("微信预支付调用接口result : {}", result);
        //获取前台支付调用参数
        WebPayVo webParam = getWebParam(result, wxPayment.getConfig());
        this.logger().info("前台支付调用参数result : {}", webParam);
		return webParam;
	}

    /**
     * 王帅
     * 获取web端调用微信支付参数
     * @param result result
     * @return WebPayVo
     * @source com.github.binarywang.wxpay.service.impl.BaseWxPayServiceImpl.getPayInfo()
     */
    private WebPayVo getWebParam(WxPayUnifiedOrderResult result, WxPayConfig config) throws MpException {
        WebPayVo vo = null;
	    //微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
        String prepayId = result.getPrepayId();
        if (StringUtils.isBlank(prepayId)) {
            logger().info(String.format("无法获取prepay id，错误代码： '%s'，信息：%s。", result.getErrCode(), result.getErrCodeDes()));
            throw new MpException(JsonResultCode.CODE_WX_PAY_PREPAY_ID_IS_NULL);
        }
        Map<String, String> payInfo = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = String.valueOf(System.currentTimeMillis());
        if (WxPayConstants.TradeType.NATIVE.equals(result.getTradeType())) {
            //TODO 原生扫码支付
        } else if (WxPayConstants.TradeType.APP.equals(result.getTradeType())) {
            //TODO App支付
        } else if (WxPayConstants.TradeType.JSAPI.equals(result.getTradeType())) {
            //公众号支付/小程序支付.
            vo = JsApiVo.builder().
                appId(result.getAppid()).
                timeStamp(timestamp).nonceStr(nonceStr).
                packageAlias("prepay_id=" + prepayId).
                signType(config.getSignType()).
                paySign(SignUtils.createSign(payInfo, config.getSignType(), config.getMchKey(), null)).
                build();
        }
        vo.setResult(result);
        return vo;
    }

    /**
	 * 通过微信订单号退款
     *
	 * @param transactionId 微信订单号
	 * @param outRefundNo   退款单号
	 * @param refundFee     退款金额，单位分
	 * @param totalFee      订单总金额，单位分
	 * @return
	 * @throws WxPayException
	 */
	public WxPayRefundResult refundByTransactionId(String transactionId, String outRefundNo, Integer refundFee,
			Integer totalFee) throws WxPayException {
		return this.refund(null, transactionId, outRefundNo, refundFee, totalFee);
	}

	/**
	 * 通过订单号进行退款
     *
	 * @param outTradeNo  商家订单号
	 * @param outRefundNo 退款单号
	 * @param refundFee   退款金额，单位分
	 * @param totalFee    订单总金额，单位分
	 * @return
	 * @throws WxPayException
	 */
	public WxPayRefundResult refundByOutTradeNo(String outTradeNo, String outRefundNo, Integer refundFee,
			Integer totalFee) throws WxPayException {
		return this.refund(outTradeNo, null, outRefundNo, refundFee, totalFee);
	}

	/**
	 * 退款
     *
	 * @param outTradeNo    商家订单号
	 * @param transactionId 微信订单号
	 * @param outRefundNo   退款单号
	 * @param refundFee     退款金额，单位分
	 * @param totalFee      订单总金额，单位分
	 * @return
	 * @throws WxPayException
	 */
	public WxPayRefundResult refund(String outTradeNo, String transactionId, String outRefundNo, Integer refundFee,
			Integer totalFee)
			throws WxPayException {
		WxPayment wxPayment = this.getMpPay();
		WxPayRefundRequest request = WxPayRefundRequest.newBuilder()
				.transactionId(transactionId)
				.outTradeNo(outTradeNo)
				.outRefundNo(outRefundNo)
				.refundFee(refundFee)
				.totalFee(totalFee).build();
		return wxPayment.refund(request);
	}

	/**
	 * 企业付款到个人
	 * @param clientIp 来源IP
	 * @param outTradeNumber 付款单号
	 * @param openId 用户OpenId
	 * @param realUserName 用户真实姓名
	 * @param amount 付款金额，单位分
	 * @param desc 描述
	 * @return
	 * @throws WxPayException
	 */
	public EntPayResult companyPay(String clientIp, String outTradeNumber, String openId, String realUserName,
			Integer amount, String desc)
			throws WxPayException {
		WxPayment wxPayment = this.getMpPay();
		EntPayRequest wxEntPayRequest = EntPayRequest.newBuilder()
				.spbillCreateIp(clientIp)
				.openid(openId)
				.amount(amount)
				.description(desc)
				.reUserName(realUserName)
				.partnerTradeNo(outTradeNumber)
				.checkName("FORCE_CHECK")
				.build();
		return wxPayment.getEntPayService().entPay(wxEntPayRequest);
	}

	/**
	 * 微信小程序支付回调
	 * @param orderResult
	 */
	public void onPayNotify(WxPayOrderNotifyResult orderResult) throws MpException {
		PaymentRecordParam param = PaymentRecordParam.builder()
				.payCode(PayCode.PAY_CODE_WX_PAY)
				.tradeNo(orderResult.getTransactionId())
				.trdaeStatus((byte)0)
				.trdaeOriginStatus(orderResult.getResultCode())
				.quantity(1)
				.orderSn(orderResult.getOutTradeNo())
				.totalFee(BaseWxPayResult.fenToYuan(orderResult.getTotalFee()))
				.buyerId(orderResult.getOpenid())
				.sellerId(orderResult.getMchId())
                .gmtCreate(DateUtil.convertToTimestamp(orderResult.getTimeEnd()))
                .notifyTime(DateUtil.convertToTimestamp(orderResult.getTimeEnd()))
                .gmtCloseTime(DateUtil.convertToTimestamp(orderResult.getTimeEnd()))
				.created(Timestamp.valueOf(LocalDateTime.now()))
				.remark2(orderResult.toString())
				.build();
		pay.unionPayNotify(param);
	}

    /**
     * Gets prepay id.获取微信支付id
     */
    public String getPrepayId(String goodsName, String orderSn, String openId, BigDecimal amount) {
        // 过滤字符串中的表情,微信接口不支持表情
        goodsName = Util.filterEmoji(goodsName, "");
        MpAuthShopRecord mpAuthShopRecord = getMpAuthShop();
        //根据不同的子商户模式,执行不同的支付方法
        switch (mpAuthShopRecord.getIsSubMerchant()) {
            case CONDITION_ZERO:
                // todo 非子商户
                return null;
            case CONDITION_ONE:
                // todo 微信子商户支付
                return null;
            case CONDITION_TWO:
                // todo 通联小程序支付
                return null;
            case CONDITION_THREE:
                // todo 微信国际支付方式
                return null;
            default:
        }
        WxPayment wxPayment = getMpPay();
        // ...
        return null;
    }
}

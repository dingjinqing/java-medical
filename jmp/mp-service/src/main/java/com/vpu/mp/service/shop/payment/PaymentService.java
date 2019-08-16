package com.vpu.mp.service.shop.payment;

import static com.vpu.mp.db.shop.tables.Payment.PAYMENT;

import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.PaymentRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.payment.PayCode;
import com.vpu.mp.service.pojo.shop.payment.PaymentRecordParam;

@Service
public class PaymentService extends ShopBaseService {

	@Autowired
	public PaymentRecordService record;

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
	public Result<PaymentRecord> getSupportPayment() {
		String[] payCodes = { PayCode.PAY_CODE_WX_PAY, PayCode.PAY_CODE_COD };
		return db().selectFrom(PAYMENT)
				.where(PAYMENT.ENABLED.eq(PayCode.PAY_CODE_ENABLED)
						.and(PAYMENT.PAY_CODE.in(payCodes)))
				.orderBy(PAYMENT.PAY_CODE.desc())
				.fetch();
	}

	/**
	 * 统一订单支付回调
	 */
	public void unionPayNotify(PaymentRecordParam param) {
		// TODO: 统一支付回调
	}

}

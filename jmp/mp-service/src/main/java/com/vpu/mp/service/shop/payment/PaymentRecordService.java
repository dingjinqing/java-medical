package com.vpu.mp.service.shop.payment;

import static com.vpu.mp.db.shop.tables.PaymentRecord.PAYMENT_RECORD;

import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.image.ImageListQueryParam;
import com.vpu.mp.service.pojo.shop.payment.PaymentRecordParam;
import com.vpu.mp.service.pojo.shop.payment.PaymentRecordVo;

@Service
public class PaymentRecordService extends ShopBaseService {

	/**
	 * 添加PaymentRecord记录
	 * @param param
	 * @return 
	 */
	public PaymentRecordRecord addPaymentRecord(PaymentRecordParam param) {
		param.setPaySn(generatePaySn());
		PaymentRecordRecord record = db().newRecord(PAYMENT_RECORD,param);
		record.insert();
		return record;
	}
	
	/**
	 * 通过支付单号得到支付记录
	 * @param paySn
	 * @return
	 */
	public PaymentRecordRecord getPaymentRecordByPaySn(String paySn) {
		return db().fetchAny(PAYMENT_RECORD,PAYMENT_RECORD.PAY_SN.eq(paySn));
	}
	
	/**
	 * 通过商家单号得到支付记录
	 * @param orderSn
	 * @return
	 */
	public PaymentRecordRecord getPaymentRecordByOrderSn(String orderSn) {
		return db().fetchAny(PAYMENT_RECORD,PAYMENT_RECORD.ORDER_SN.eq(orderSn));
	}
	
	/**
	 * 通过交易单号得到支付记录
	 * @param orderSn
	 * @return
	 */
	public PaymentRecordRecord getPaymentRecordByTradeNo(String tradeNo) {
		return db().fetchAny(PAYMENT_RECORD,PAYMENT_RECORD.TRADE_NO.eq(tradeNo));
	}
	
	/**
	 * 产生PaySn
	 * @return
	 */
    protected String generatePaySn()
    {
    	while(true) {
    		String paySn = String.format("PS%s%.4d", DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL),Math.random());
    		int count = db().fetchCount(PAYMENT_RECORD,PAYMENT_RECORD.PAY_SN.eq(paySn));
    		if(count == 0) {
    			return paySn;
    		}
    	}
    }
    
    /**
     * 得到分页记录
     * @param param
     * @return
     */
    public PageResult<PaymentRecordVo> getPageList(ImageListQueryParam param) {
        SelectWhereStep<PaymentRecordRecord> select = db().selectFrom(PAYMENT_RECORD);
        select.orderBy(PAYMENT_RECORD.ID.desc());
        return this.getPageResult(select, param.page,param.pageRows, PaymentRecordVo.class);
    }
	
	
}

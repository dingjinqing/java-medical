package com.vpu.mp.service.shop.order.refund.record;

import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.vpu.mp.db.shop.tables.OrderRefundRecord;
import com.vpu.mp.db.shop.tables.records.OrderRefundRecordRecord;
import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.vpu.mp.db.shop.tables.OrderRefundRecord.ORDER_REFUND_RECORD;

/**
 * 	非系统金额退款记录表
 * @author 王帅
 *
 */
@Service
public class OrderRefundRecordService extends ShopBaseService{
	
	public final OrderRefundRecord TABLE = ORDER_REFUND_RECORD;
    private static final byte success = 1;
    private static final byte fail = 2;
    /**
     * 非系统金额退款记录
     * @param refundSn 退款流水号
     * @param refundResult 退款结果（失败==null）
     * @param retId 退订单号
     */
	public void addRecord(String refundSn, PaymentRecordRecord payRecord, WxPayRefundResult refundResult, Integer retId) {
        OrderRefundRecordRecord record = db().newRecord(TABLE);
        record.setRefundSn(refundSn);
        record.setPaySn(payRecord.getPaySn());
        record.setOrderSn(payRecord.getOrderSn());
        record.setPayCode(payRecord.getPayCode());
        record.setRefundTime(DateUtil.getSqlTimestamp());
        record.setRetId(retId);
        record.setTransSn(payRecord.getTradeNo());

        if(refundResult != null) {
            //成功
            record.setDealStatus(success);
            record.setDealStatusName("非系统金额退款成功");
            record.setDealRemark(refundResult.toString());
            record.setRefundAmount(
                BigDecimalUtil.divide(new BigDecimal(refundResult.getRefundFee().toString()), new BigDecimal("100"))
            );
        }else {
            //失败
            record.setDealStatus(fail);
            record.setDealStatusName("非系统金额退款失败");
            record.setDealRemark("见日志");
            record.setRefundAmount(BigDecimal.ZERO);
        }
        record.insert();
    }

    /**
     * 判断改退款订单是否存在非系统金额退款失败情况
     * @param retId
     * @return
     */
    public boolean isReturnSucess(Integer retId){
        OrderRefundRecordRecord count = db().selectFrom(TABLE).where(TABLE.RET_ID.eq(retId).and(TABLE.DEAL_STATUS.eq(fail))).fetchAny();
        if(count != null) {
            return false;
        }
        return true;
	}

}

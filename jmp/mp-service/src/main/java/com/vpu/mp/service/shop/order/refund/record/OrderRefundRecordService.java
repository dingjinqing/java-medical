package com.vpu.mp.service.shop.order.refund.record;

import static com.vpu.mp.db.shop.tables.OrderRefundRecord.ORDER_REFUND_RECORD;

import java.math.BigDecimal;

import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.vpu.mp.db.shop.tables.records.OrderRefundRecordRecord;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.db.shop.tables.OrderRefundRecord;
import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.RandomUtil;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.virtual.VirtualOrderPayInfo;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import com.vpu.mp.service.shop.payment.PaymentRecordService;

/**
 * 	非系统金额退款记录表
 * @author 王帅
 *
 */
@Service
public class OrderRefundRecordService extends ShopBaseService{
	
	public final OrderRefundRecord TABLE = ORDER_REFUND_RECORD;

    /**
     * 非系统金额退款记录
     * @param refundSn 退款流水号
     * @param refundResult 退款结果（失败==null）
     * @param order 订单
     * @param retId 退订单号
     */
	public void addRecord(String refundSn, PaymentRecordRecord payRecord, WxPayRefundResult refundResult, OrderInfoVo order, Integer retId) {
        OrderRefundRecordRecord record = db().newRecord(TABLE);
        record.setRefundSn(refundSn);
        record.setPaySn(payRecord.getPaySn());
        record.setOrderSn(order.getOrderSn());
        record.setPayCode(order.getPayCode());
        record.setRefundTime(DateUtil.getSqlTimestamp());
        record.setRetId(retId);
        record.setTransSn(payRecord.getTradeNo());

        if(refundResult != null) {
            //成功
            record.setDealStatus(NumberUtils.BYTE_ONE);
            record.setDealStatusName("退款成功");
            record.setDealRemark(refundResult.toString());
            record.setRefundAmount(
                BigDecimalUtil.divide(new BigDecimal(refundResult.getRefundFee().toString()), new BigDecimal("100"))
            );
        }else {
            //失败
            record.setDealStatus((byte)2);
            record.setDealStatusName("退款失败");
            record.setDealRemark("见日志");
            record.setRefundAmount(
                BigDecimalUtil.divide(new BigDecimal(refundResult.getRefundFee().toString()), new BigDecimal("100"))
            );
        }
        record.insert();
    }
}

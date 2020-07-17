package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.audit.AuditExternalParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionOneParam;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.refund.ReturnOrderService;
import com.vpu.mp.service.shop.order.refund.goods.ReturnOrderGoodsService;
import com.vpu.mp.service.shop.order.refund.record.OrderRefundRecordService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 审核
 * @author 孔德成
 * @date 2020/7/17 15:30
 */
@Service
@Slf4j
public class AuditService extends ShopBaseService implements IorderOperate<AuditExternalParam,AuditExternalParam> {

    @Autowired
    private OrderRefundRecordService orderRefundRecord;
    @Autowired
    private ReturnOrderService returnOrder;

    @Autowired
    private OrderInfoService orderInfo;
    @Autowired
    private OrderGoodsService orderGoods;
    @Autowired
    private ReturnOrderGoodsService returnOrderGoods;
    @Autowired
    private ReturnService returnService;
    @Autowired
    private PrescriptionService prescriptionService;

    @Override
    public OrderServiceCode getServiceCode() {
        return null;
    }

    @Override
    public Object query(AuditExternalParam param) throws MpException {
        return null;
    }

    /**
     * 审核订单
     * @param param
     * @return
     */
    @Override
    public ExecuteResult execute(AuditExternalParam param) {
        log.info("审核订单(AuditService)-开始");
        OrderInfoRecord orderRecord = orderInfo.getOrderByOrderSn(param.getOrderSn());
        Result<OrderGoodsRecord> oGoods = orderGoods.getByOrderId(orderRecord.getOrderId());
        FetchPrescriptionOneParam fetchparam =new FetchPrescriptionOneParam();
        fetchparam.setPrescriptionCode(param.getPrescriptionCode());
        prescriptionService.pullExternalOnePrescriptionInfo(fetchparam);
        for (OrderGoodsRecord goods: oGoods){
            goods.setPrescriptionNo(param.getPrescriptionCode());
            goods.setAuditTime(DateUtils.getLocalDateTime());
            if (param.getIsAudit().equals(BaseConstant.YES)){
                goods.setMedicalAuditStatus((byte)1);
            }else {
                goods.setMedicalAuditStatus((byte)2);
            }
            goods.update();
        }
        orderRecord.setPrescriptionCodeList(param.getPrescriptionCode());
        if (param.getIsAudit().equals(BaseConstant.YES)){
            orderRecord.setOrderAuditStatus((byte)1);
        }else {
            orderRecord.setOrderAuditStatus((byte)2);
        }
        orderRecord.setAuditTime(DateUtils.getLocalDateTime());
        orderRecord.update();
        if (param.getIsAudit().equals(BaseConstant.YES)){

        }else {
            returnOrderOption(param.getOrderSn());
        }
        log.info("审核订单(AuditService)-结束");
        return null;
    }

    /**
     * 退款订单
     * @param orderSn 订单号
     */
    private void returnOrderOption(String orderSn) {
        OrderInfoRecord orderRecord = orderInfo.getOrderByOrderSn(orderSn);
        Result<OrderGoodsRecord> oGoods = orderGoods.getByOrderId(orderRecord.getOrderId());
        //组装退款param
        RefundParam param = new RefundParam();
        param.setAction((byte) OrderServiceCode.RETURN.ordinal());//1是退款
        param.setIsMp(OrderConstant.IS_MP_AUTO);
        param.setReturnSourceType(OrderConstant.RS_AUTO_COMMUNITY_GROUP);
        param.setOrderSn(orderSn);
        param.setOrderId(orderRecord.getOrderId());
        param.setReturnType(OrderConstant.RT_ONLY_MONEY);
        param.setReturnMoney(orderRecord.getMoneyPaid().add(orderRecord.getScoreDiscount()).add(orderRecord.getUseAccount()).add(orderRecord.getMemberCardBalance()).subtract(orderRecord.getShippingFee()));
        param.setShippingFee(orderRecord.getShippingFee());

        List<RefundParam.ReturnGoods> returnGoodsList = new ArrayList<>();
        oGoods.forEach(orderGoods->{
            RefundParam.ReturnGoods returnGoods = new RefundParam.ReturnGoods();
            returnGoods.setRecId(orderGoods.getRecId());
            returnGoods.setReturnNumber(orderGoods.getGoodsNumber());

            returnGoodsList.add(returnGoods);
        });
        param.setReturnGoods(returnGoodsList);
        returnService.execute(param);
    }


}

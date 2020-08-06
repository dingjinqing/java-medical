package com.vpu.mp.dao.shop.order;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReportVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.OrderToPrescribeQueryParam;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static org.jooq.impl.DSL.avg;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.date;
import static org.jooq.impl.DSL.sum;
import static org.jooq.impl.DSL.when;

/**
 * @author yangpengcheng
 * @date 2020/7/28
 **/
@Repository
public class OrderInfoDao extends ShopBaseDao {
    /**
     *
     * @param orderId
     * @param auditStatus
     */
    public void updateAuditStatus(Integer orderId,Byte auditStatus){
        db().update(ORDER_INFO).set(ORDER_INFO.ORDER_AUDIT_STATUS,auditStatus).where(ORDER_INFO.ORDER_ID.eq(orderId)).execute();
    }

    public PageResult<OrderInfoVo>  listOrderInfo(OrderToPrescribeQueryParam param){
        SelectJoinStep<? extends Record> select = db().select().from(ORDER_INFO);
        if(param.getOrderStatus()!=null){
            select.where(ORDER_INFO.ORDER_STATUS.eq(param.getOrderStatus()));
        }
        if (param.getAuditType() != null) {
            select.where(ORDER_INFO.ORDER_AUDIT_TYPE.eq(param.getAuditType()));
        }
        if (param.getAuditStatus() != null) {
            select.where(ORDER_INFO.ORDER_AUDIT_STATUS.eq(param.getAuditStatus()));
        }
        if(param.getOrderId()!=null){
            select.where(ORDER_INFO.ORDER_ID.eq(param.getOrderId()));
        }
        select.orderBy(ORDER_INFO.CREATE_TIME.desc());
        return getPageResult(select, param.getCurrentPage(), param.getPageRows(),OrderInfoVo.class);
    }
    /**
     * 订单状态改为待发货
     * @param orderId 订单id
     * @param prescriptionCode 处方号
     */
    public void updateAuditedToWaitDelivery(Integer orderId, String prescriptionCode) {
        db().update(ORDER_INFO)
                .set(ORDER_INFO.ORDER_AUDIT_STATUS, OrderConstant.MEDICAL_AUDIT_PASS)
                .set(ORDER_INFO.ORDER_STATUS,OrderConstant.ORDER_WAIT_DELIVERY)
                .where(ORDER_INFO.ORDER_ID.eq(orderId))
                .execute();
    }

    /**
     * 药品销售报表
     * @return
     */
    public Map<Date, MedicalOrderReportVo> orderSalesReport(Timestamp startTime, Timestamp  endTime){
        return db().select(
                //日期
                date(ORDER_INFO.CREATE_TIME).as(ActiveDiscountMoney.CREATE_TIME),
                //销售金额
                sum((ORDER_INFO.ORDER_AMOUNT)).as(ActiveDiscountMoney.ORDER_AMOUNT),
                //销售单数
                count(ORDER_INFO.ORDER_ID).as(ActiveDiscountMoney.ORDER_NUMBER),
                //平均数
                avg(ORDER_INFO.ORDER_AMOUNT).as(ActiveDiscountMoney.ORDER_AVG),
                //处方药销售金额
                sum(when(ORDER_INFO.ORDER_MEDICAL_TYPE.eq(OrderConstant.MEDICAL_TYPE_RX), ORDER_INFO.ORDER_AMOUNT).otherwise(BigDecimal.ZERO)).as(ActiveDiscountMoney.ORDER_MEDICAL_AMOUNT),
                //处方药销售单数
                sum(when(ORDER_INFO.ORDER_MEDICAL_TYPE.eq(OrderConstant.MEDICAL_TYPE_RX), 1).otherwise(0)).as(ActiveDiscountMoney.ORDER_MEDICAL_NUMBER)
        )
                .from(ORDER_INFO)
                .where(ORDER_INFO.CREATE_TIME.between(startTime, endTime))
                .groupBy(date(ORDER_INFO.CREATE_TIME))
                .orderBy(ORDER_INFO.CANCELLED_TIME)
                .fetchMap(date(ORDER_INFO.CREATE_TIME).as(ActiveDiscountMoney.CREATE_TIME), MedicalOrderReportVo.class);
    }

}

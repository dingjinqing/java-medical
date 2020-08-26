package com.vpu.mp.dao.shop.order;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.OrderInfoDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReportVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.OrderToPrescribeQueryParam;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.date;
import static org.jooq.impl.DSL.sum;

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
                //销售金额  微信+余额+运费
                sum((ORDER_INFO.MONEY_PAID.add(ORDER_INFO.USE_ACCOUNT).add(ORDER_INFO.SHIPPING_FEE))).as(ActiveDiscountMoney.ORDER_AMOUNT),
                //微信
                sum(ORDER_INFO.MONEY_PAID).as(ActiveDiscountMoney.MONEY_PAID),
                //余额
                sum(ORDER_INFO.USE_ACCOUNT).as(ActiveDiscountMoney.USE_ACCOUNT),
                //运费
                sum(ORDER_INFO.SHIPPING_FEE).as(ActiveDiscountMoney.SHIPPING_FEE),
                 //销售单数
                count(ORDER_INFO.ORDER_ID).as(ActiveDiscountMoney.ORDER_NUMBER)
        )
                .from(ORDER_INFO)
                .where(ORDER_INFO.CREATE_TIME.between(startTime, endTime))
                .groupBy(date(ORDER_INFO.CREATE_TIME))
                .orderBy(ORDER_INFO.CANCELLED_TIME)
                .fetchMap(date(ORDER_INFO.CREATE_TIME).as(ActiveDiscountMoney.CREATE_TIME), MedicalOrderReportVo.class);
    }

    /**
     * 根据用户id查询订单
     * @param userId 用户id
     * @return List<OrderInfoDo>
     */
    public List<OrderInfoDo> selectOrderInfoByUserId(Integer userId) {
        return db().select().from(ORDER_INFO)
            .where(ORDER_INFO.USER_ID.eq(userId))
            .and(ORDER_INFO.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).fetchInto(OrderInfoDo.class);
    }

    /**
     * 根据订单id查询订单sn号
     * @param orderId 订单id
     * @return String
     */
    public String selectOrderSnByOrderId(Integer orderId) {
        return db().select(ORDER_INFO.ORDER_SN)
            .from(ORDER_INFO)
            .where(ORDER_INFO.ORDER_ID.eq(orderId)).fetchAnyInto(String.class);
    }

    public List<OrderInfoDo> listUpdateOrderByYesterday(Timestamp beginTime, Timestamp endTime) {
      return  db().selectFrom(ORDER_INFO)
                .where(ORDER_INFO.UPDATE_TIME.ge(beginTime)).and(ORDER_INFO.UPDATE_TIME.le(endTime))
                .and(ORDER_INFO.CREATE_TIME.le(beginTime))
                .fetchInto(OrderInfoDo.class);
    }

    public List<OrderInfoDo> listCreateOrderByYesterday(Timestamp beginTime, Timestamp endTime) {
        return  db().selectFrom(ORDER_INFO)
                .where(ORDER_INFO.CREATE_TIME.ge(beginTime)).and(ORDER_INFO.CREATE_TIME.le(endTime))
                .fetchInto(OrderInfoDo.class);
    }

    /**
     * 检查核销码是否正确
     * @param verifyCode 核销码
     * @param orderSn 订单OrderSn
     * @return boolean
     * @author 赵晓东
     */
    public boolean checkVerifyCode(String verifyCode, String orderSn) {
        OrderInfoDo orderInfoDo = db().select().from(ORDER_INFO)
            .where(ORDER_INFO.ORDER_SN.eq(orderSn))
            .and(ORDER_INFO.VERIFY_CODE.eq(verifyCode)).fetchAnyInto(OrderInfoDo.class);
        return orderInfoDo != null;
    }
}

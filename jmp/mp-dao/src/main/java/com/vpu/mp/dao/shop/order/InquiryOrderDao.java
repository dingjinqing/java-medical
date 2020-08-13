package com.vpu.mp.dao.shop.order;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.InquiryOrderRecord;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.*;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.statistics.InquiryOrderStatistics;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.vo.InquiryOrderStatisticsVo;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.vo.InquiryOrderTotalVo;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.DEPARTMENT;
import static com.vpu.mp.db.shop.Tables.DOCTOR;
import static com.vpu.mp.db.shop.tables.InquiryOrder.INQUIRY_ORDER;
import static org.jooq.impl.DSL.*;

/**
 * @author yangpengcheng
 */
@Repository
public class InquiryOrderDao extends ShopBaseDao {


    public PageResult<InquiryOrderDo> getInquiryOrderList(InquiryOrderListParam param){
        SelectJoinStep<? extends Record> select = db()
            .select()
            .from(INQUIRY_ORDER);
        select.where(INQUIRY_ORDER.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        select=buildOptions(select, param);
        PageResult<InquiryOrderDo> list=this.getPageResult(select,param.getCurrentPage(),param.getPageRows(),InquiryOrderDo.class);
        return list;
    }

    /**
     *
     * @param select
     * @param param
     */
    protected SelectJoinStep<? extends Record> buildOptions(SelectJoinStep<? extends Record> select, InquiryOrderListParam param) {
        Timestamp nowDate = new Timestamp(System.currentTimeMillis());
        if(param.getDoctorId()!=null) {
            select.where(INQUIRY_ORDER.DOCTOR_ID.eq(param.getDoctorId()));
        }
        if(param.getOrderStatus()!=null) {
            select.where(INQUIRY_ORDER.ORDER_STATUS.eq(param.getOrderStatus()));
        }
        if(param.getDepartmentId()!=null) {
            select.where(INQUIRY_ORDER.DEPARTMENT_ID.eq(param.getDepartmentId()));
        }
        if(StringUtils.isNotBlank(param.getDoctorName())) {
            select.where(INQUIRY_ORDER.DOCTOR_NAME.like(this.likeValue(param.getDoctorName())));
        }
        if(StringUtils.isNotBlank(param.getPatientName())) {
            select.where(INQUIRY_ORDER.PATIENT_NAME.like(this.likeValue(param.getPatientName())));
        }
        return select;
    }

    /**
     * 新增
     *
     * @param inquiryOrderDo
     * @return
     */
    public int save(InquiryOrderDo inquiryOrderDo){
        InquiryOrderRecord inquiryOrderRecord=db().newRecord(INQUIRY_ORDER);
        FieldsUtil.assign(inquiryOrderDo,inquiryOrderRecord);
        inquiryOrderRecord.insert();
        return inquiryOrderRecord.getOrderId();
    }

    /**
     * 更新微信支付id
     *
     * @param orderSn
     * @param prepayId
     */
    public void updatePrepayId(String orderSn ,String prepayId){
        db().update(INQUIRY_ORDER).set(INQUIRY_ORDER.PREPAY_ID,prepayId).where(INQUIRY_ORDER.ORDER_SN.eq(orderSn)).execute();
    }


    /**
     * getByOrderSn
     *
     * @param orderSn
     * @return
     */
    public InquiryOrderDo getByOrderSn(String orderSn){
        return db().select().from(INQUIRY_ORDER).where(INQUIRY_ORDER.ORDER_SN.eq(orderSn)).and(INQUIRY_ORDER.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchOneInto(InquiryOrderDo.class);

    }

    /**
     * getByOrderId
     * @param orderId
     * @return
     */
    public InquiryOrderDo getByOrderId(Integer orderId){
        return db().select().from(INQUIRY_ORDER).where(INQUIRY_ORDER.ORDER_ID.eq(orderId)).and(INQUIRY_ORDER.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchOneInto(InquiryOrderDo.class);
    }

    /**
     * @param inquiryOrderDo
     */
    public void update(InquiryOrderDo inquiryOrderDo){
        InquiryOrderRecord record=new InquiryOrderRecord();
        FieldsUtil.assign(inquiryOrderDo,record);
        db().executeUpdate(record);
    }

    /**
     * 获得待支付的超时问诊订单
     * @return
     */
    public List<InquiryOrderDo> getCanceledToPaidCloseOrder(){
        return db().selectFrom(INQUIRY_ORDER).where(INQUIRY_ORDER.ORDER_STATUS.eq(InquiryOrderConstant.ORDER_TO_PAID)).and(INQUIRY_ORDER.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
            .and(INQUIRY_ORDER.CREATE_TIME.le(DateUtils.getTimeStampPlus(0-InquiryOrderConstant.EXPIRY_TIME_HOUR, ChronoUnit.HOURS))).fetchInto(InquiryOrderDo.class);
    }

    /**
     * 获得待接诊的超时未接诊的问诊订单
     *
     * @return
     */
    public List<InquiryOrderDo>  getCanceledToWaitingCloseOrder(){
        return db().selectFrom(INQUIRY_ORDER).where(INQUIRY_ORDER.ORDER_STATUS.eq(InquiryOrderConstant.ORDER_TO_RECEIVE)).and(INQUIRY_ORDER.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
            .and(INQUIRY_ORDER.CREATE_TIME.le(DateUtils.getTimeStampPlus(0-InquiryOrderConstant.EXPIRY_TIME_HOUR, ChronoUnit.HOURS))).fetchInto(InquiryOrderDo.class);
    }

    /**
     *获取接诊中超时自动结束的问诊订单
     * @return
     */
    public List<InquiryOrderDo> getFinishedCloseOrder(){
        return db().selectFrom(INQUIRY_ORDER).where(INQUIRY_ORDER.ORDER_STATUS.eq(InquiryOrderConstant.ORDER_RECEIVING)).and(INQUIRY_ORDER.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
            .and(INQUIRY_ORDER.LIMIT_TIME.le(currentTimestamp())).fetchInto(InquiryOrderDo.class);
    }
    /**
     * @param param
     * @return
     */
    public List<InquiryOrderDo> getOrderByParams(InquiryOrderParam param){
        List<InquiryOrderDo> list= db().select().from(INQUIRY_ORDER).where(INQUIRY_ORDER.USER_ID.eq(param.getUserId())
            .and(INQUIRY_ORDER.PATIENT_ID.eq(param.getPatientId()))
            .and(INQUIRY_ORDER.DOCTOR_ID.eq(param.getDoctorId())
        )).fetchInto(InquiryOrderDo.class);
        return list;
    }

    /**
     *问诊订单统计报表详情分页查询
     * @param param
     * @return
     */
    public PageResult<InquiryOrderStatisticsVo> orderStatisticsPage(InquiryOrderStatisticsParam param){
        SelectJoinStep<? extends Record> select=db().select(
            date((INQUIRY_ORDER.CREATE_TIME)).as(InquiryOrderStatistics.CREAT_TIME),
            //咨询单数
            count((INQUIRY_ORDER.ORDER_ID)).as(InquiryOrderStatistics.AMOUNT),
            //咨询总金额
            sum(INQUIRY_ORDER.ORDER_AMOUNT).as(InquiryOrderStatistics.AMOUNT_PRICE),
            //咨询单次价格
            avg(INQUIRY_ORDER.ORDER_AMOUNT).as(InquiryOrderStatistics.ONE_PRICE),
            //医师id
            INQUIRY_ORDER.DOCTOR_ID.as(InquiryOrderStatistics.DOCTOR_ID)
        ).from(INQUIRY_ORDER);
        select=buildOptions(select,param);
        select.groupBy(INQUIRY_ORDER.DOCTOR_ID,date(INQUIRY_ORDER.CREATE_TIME));
        PageResult<InquiryOrderStatisticsVo> result=this.getPageResult(select,param.getCurrentPage(),param.getPageRows(),InquiryOrderStatisticsVo.class);
        return result;
    }
    public SelectJoinStep<? extends Record> buildOptions(SelectJoinStep<? extends Record> selectJoinStep,InquiryOrderStatisticsParam param){
        selectJoinStep.where(INQUIRY_ORDER.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        selectJoinStep.where(INQUIRY_ORDER.ORDER_STATUS.gt(InquiryOrderConstant.ORDER_TO_PAID));
        if(param.getDoctorId()!=null){
            selectJoinStep.where(INQUIRY_ORDER.DOCTOR_ID.eq(param.getDoctorId()));
        }
        if(param.getStartTime()!=null){
            selectJoinStep.where(INQUIRY_ORDER.CREATE_TIME.ge(param.getStartTime()));
        }
        if(param.getEndTime()!=null){
            selectJoinStep.where(INQUIRY_ORDER.CREATE_TIME.le(param.getEndTime()));
        }
        return selectJoinStep;
    }

    /**
     * 问诊订单统计报表详情查询
     * @param param
     * @return
     */
    public List<InquiryOrderStatisticsVo> orderStatistics(InquiryOrderStatisticsParam param){
        SelectJoinStep<? extends Record> select=db().select(
            date((INQUIRY_ORDER.CREATE_TIME)).as(InquiryOrderStatistics.CREAT_TIME),
            //咨询单数
            count((INQUIRY_ORDER.ORDER_ID)).as(InquiryOrderStatistics.AMOUNT),
            //咨询总金额
            sum(INQUIRY_ORDER.ORDER_AMOUNT).as(InquiryOrderStatistics.AMOUNT_PRICE),
            //咨询单次价格
            avg(INQUIRY_ORDER.ORDER_AMOUNT).as(InquiryOrderStatistics.ONE_PRICE),
            //医师id
            INQUIRY_ORDER.DOCTOR_ID.as(InquiryOrderStatistics.DOCTOR_ID)
        ).from(INQUIRY_ORDER);
        select=buildOptions(select,param);
        select.groupBy(INQUIRY_ORDER.DOCTOR_ID,date(INQUIRY_ORDER.CREATE_TIME));
        List<InquiryOrderStatisticsVo> list=select.fetchInto(InquiryOrderStatisticsVo.class);
        return list;
    }

    /**
     * 咨询报表总数total查询
     * @param param
     * @return
     */
    public InquiryOrderTotalVo orderStatisticsTotal(InquiryOrderStatisticsParam param){
        SelectJoinStep<? extends Record> select=db().select(
            //咨询单数
            count((INQUIRY_ORDER.ORDER_ID)).as(InquiryOrderStatistics.AMOUNT_TOTAL),
            //咨询总金额
            sum(INQUIRY_ORDER.ORDER_AMOUNT).as(InquiryOrderStatistics.AMOUNT_PRICE_TOTAL),
            //咨询单次价格
            avg(INQUIRY_ORDER.ORDER_AMOUNT).as(InquiryOrderStatistics.ONE_PRICE_TOTAL)
        ).from(INQUIRY_ORDER);
        select=buildOptions(select,param);
        InquiryOrderTotalVo inquiryOrderTotalVo=select.fetchOneInto(InquiryOrderTotalVo.class);
        return inquiryOrderTotalVo;
    }
}

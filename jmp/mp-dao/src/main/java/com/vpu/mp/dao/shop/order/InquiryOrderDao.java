package com.vpu.mp.dao.shop.order;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.validator.ListValid;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.InquiryOrderRecord;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryOrderConstant;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryOrderListParam;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.vpu.mp.db.shop.tables.InquiryOrder.INQUIRY_ORDER;

@Repository
public class InquiryOrderDao extends ShopBaseDao {


    public PageResult<InquiryOrderDo> getInquiryOrderList(InquiryOrderListParam param){
        SelectJoinStep<? extends Record> select = db()
            .select()
            .from(INQUIRY_ORDER);
        select.where(INQUIRY_ORDER.IS_DELETE.eq((byte)0));
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
        if(param.getDoctorId()!=null)
            select.where(INQUIRY_ORDER.DOCTOR_ID.eq(param.getDoctorId()));
        if(param.getOrderStatus()!=null)
            select.where(INQUIRY_ORDER.ORDER_STATUS.eq(param.getOrderStatus()));
        if(param.getDepartmentId()!=null)
            select.where(INQUIRY_ORDER.DEPARTMENT_ID.eq(param.getDepartmentId()));
        if(StringUtils.isNotBlank(param.getDoctorName()))
            select.where(INQUIRY_ORDER.DOCTOR_NAME.like(this.likeValue(param.getDoctorName())));
        if(StringUtils.isNotBlank(param.getPatientName()))
            select.where(INQUIRY_ORDER.PATIENT_NAME.like(this.likeValue(param.getPatientName())));
        return select;
    }
    /*
     *新增
     */
    public int save(InquiryOrderDo inquiryOrderDo){
        InquiryOrderRecord inquiryOrderRecord=db().newRecord(INQUIRY_ORDER);
        FieldsUtil.assign(inquiryOrderDo,inquiryOrderRecord);
        inquiryOrderRecord.insert();
        return inquiryOrderRecord.getOrderId();
    }
    /*
     *更新微信支付id
     */
    public void updatePrepayId(String orderSn ,String prepayId){
        db().update(INQUIRY_ORDER).set(INQUIRY_ORDER.PREPAY_ID,prepayId).where(INQUIRY_ORDER.ORDER_SN.eq(orderSn)).execute();
    }
    /*
     *@param orderSn
     * @return
     */
    public InquiryOrderDo getByOrderSn(String orderSn){
        return db().select().from(INQUIRY_ORDER).where(INQUIRY_ORDER.ORDER_SN.eq(orderSn)).and(INQUIRY_ORDER.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchOneInto(InquiryOrderDo.class);

    }
    /*
     *@param orderId
     * @return
     */
    public InquiryOrderDo getByOrderId(Integer orderId){
        return db().select().from(INQUIRY_ORDER).where(INQUIRY_ORDER.ORDER_ID.eq(orderId)).and(INQUIRY_ORDER.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchOneInto(InquiryOrderDo.class);
    }

    public void update(InquiryOrderDo inquiryOrderDo){
        InquiryOrderRecord record=new InquiryOrderRecord();
        FieldsUtil.assign(inquiryOrderDo,record);
        db().executeUpdate(record);
    }

    /**
     * 获得待支付的超时问诊订单
     */
    public List<InquiryOrderDo> getCanceledToPaidCloseOrder(){
        return db().selectFrom(INQUIRY_ORDER).where(INQUIRY_ORDER.ORDER_STATUS.eq(InquiryOrderConstant.ORDER_TO_PAID))
            .and(INQUIRY_ORDER.CREATE_TIME.le(DateUtils.getTimeStampPlus(0-InquiryOrderConstant.EXPIRY_TIME_HOUR, ChronoUnit.HOURS))).fetchInto(InquiryOrderDo.class);
    }
    /**
     * 获得待接诊的超时未接诊的问诊订单
     */
    public List<InquiryOrderDo>  getCanceledToWaitingCloseOrder(){
        return db().selectFrom(INQUIRY_ORDER).where(INQUIRY_ORDER.ORDER_STATUS.eq(InquiryOrderConstant.ORDER_TO_RECEIVE))
            .and(INQUIRY_ORDER.CREATE_TIME.le(DateUtils.getTimeStampPlus(0-InquiryOrderConstant.EXPIRY_TIME_HOUR, ChronoUnit.HOURS))).fetchInto(InquiryOrderDo.class);
    }
}

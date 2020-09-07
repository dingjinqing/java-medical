package com.vpu.mp.dao.shop.rebate;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderRebateDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.InquiryOrderRebateRecord;
import com.vpu.mp.service.pojo.shop.rebate.*;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.tables.InquiryOrderRebate.INQUIRY_ORDER_REBATE;
import static com.vpu.mp.db.shop.tables.PrescriptionRebate.PRESCRIPTION_REBATE;


/**
 * @author yangpengcheng
 * @date 2020/8/24
 **/
@Repository
public class InquiryOrderRebateDao extends ShopBaseDao {

    /**
     * 问诊返利add
     * @param param
     */
    public void addInquiryOrderRebate(InquiryOrderRebateParam param){
        InquiryOrderRebateRecord rebateRecord=db().newRecord(INQUIRY_ORDER_REBATE);
        FieldsUtil.assign(param,rebateRecord);
        rebateRecord.insert();
    }

    /**
     * 更改返利状态
     * @param orderSn
     */
    public void updateStatus(String orderSn,Byte status,String reason){
        UpdateSetMoreStep<InquiryOrderRebateRecord> update= db().update(INQUIRY_ORDER_REBATE).set(INQUIRY_ORDER_REBATE.STATUS, status);
        if(StringUtils.isNotBlank(reason)){
            update.set(INQUIRY_ORDER_REBATE.REASON,reason);
        }
        update.where(INQUIRY_ORDER_REBATE.ORDER_SN.eq(orderSn)).execute();
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    public PageResult<InquiryOrderRebateVo> getPageList(InquiryOrderRebateListParam param){
        SelectJoinStep<? extends Record> select = db()
            .select(DOCTOR.NAME.as("doctorName"),INQUIRY_ORDER.ORDER_STATUS,INQUIRY_ORDER.PATIENT_NAME,DOCTOR.MOBILE,USER.USERNAME.as("userName"),INQUIRY_ORDER_REBATE.asterisk())
            .from(INQUIRY_ORDER_REBATE);
        select.leftJoin(DOCTOR).on(DOCTOR.ID.eq(INQUIRY_ORDER_REBATE.DOCTOR_ID))
            .leftJoin(INQUIRY_ORDER).on(INQUIRY_ORDER.ORDER_SN.eq(INQUIRY_ORDER_REBATE.ORDER_SN))
            .leftJoin(USER).on(USER.USER_ID.eq(INQUIRY_ORDER.USER_ID));
        select.where(INQUIRY_ORDER_REBATE.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        select=buildOptions(select,param);
        select.orderBy(INQUIRY_ORDER_REBATE.CREATE_TIME.desc());
        PageResult<InquiryOrderRebateVo> result=this.getPageResult(select,param.getCurrentPage(),param.getPageRows(),InquiryOrderRebateVo.class);
        return result;
    }
    protected SelectJoinStep<? extends Record> buildOptions(SelectJoinStep<? extends Record> select,InquiryOrderRebateListParam param){
        if(StringUtils.isNotBlank(param.getDoctorName())){
            select.where(DOCTOR.NAME.like(this.likeValue(param.getDoctorName())));
        }
        if(param.getDoctorId()!=null){
            select.where(INQUIRY_ORDER_REBATE.DOCTOR_ID.eq(param.getDoctorId()));
        }
        if(param.getStatus()!=null){
            select.where(INQUIRY_ORDER_REBATE.STATUS.eq(param.getStatus()));
        }
        if(param.getStartTime()!=null){
            select.where(INQUIRY_ORDER_REBATE.CREATE_TIME.ge(param.getStartTime()));
        }
        if(param.getEndTime()!=null){
            select.where(INQUIRY_ORDER_REBATE.CREATE_TIME.le(param.getEndTime()));
        }
        return select;
    }

    /**
     * 根据订单号获取
     * @param orderSn
     * @return
     */
    public InquiryOrderRebateVo getRebateByOrderSn(String orderSn){
        return db().select(DOCTOR.NAME.as("doctorName"),DOCTOR.MOBILE,INQUIRY_ORDER_REBATE.asterisk()).from(INQUIRY_ORDER_REBATE)
            .leftJoin(DOCTOR).on(DOCTOR.ID.eq(INQUIRY_ORDER_REBATE.DOCTOR_ID))
            .where(INQUIRY_ORDER_REBATE.ORDER_SN.eq(orderSn)).fetchOneInto(InquiryOrderRebateVo.class);
    }
}

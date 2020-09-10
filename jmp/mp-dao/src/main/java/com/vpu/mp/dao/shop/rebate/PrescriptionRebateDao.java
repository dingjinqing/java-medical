package com.vpu.mp.dao.shop.rebate;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PrescriptionRebateRecord;
import com.vpu.mp.service.pojo.shop.rebate.*;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.UpdateSetMoreStep;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.tables.PrescriptionRebate.PRESCRIPTION_REBATE;

/**
 * @author yangpengcheng
 * @date 2020/8/26
 **/
@Repository
public class PrescriptionRebateDao extends ShopBaseDao {

    /**
     * 处方返利入库
     * @param param
     */
    public void addPrescriptionRebate(PrescriptionRebateParam param){
        PrescriptionRebateRecord record=db().newRecord(PRESCRIPTION_REBATE);
        FieldsUtil.assign(param,record);
        record.insert();
    }

    /**
     * 更改返利状态
     * @param prescriptionCode
     * @param status
     */
    public void updateStatus(String prescriptionCode,Byte status,String reason){
        UpdateSetMoreStep<PrescriptionRebateRecord> update= db().update(PRESCRIPTION_REBATE).set(PRESCRIPTION_REBATE.STATUS,status);
        if(PrescriptionRebateConstant.REBATED.equals(status)){
            update.set(PRESCRIPTION_REBATE.REBATE_TIME, DateUtils.getLocalDateTime());
        }
        if(StringUtils.isNotBlank(reason)){
            update.set(PRESCRIPTION_REBATE.REASON,reason);
        }
        update.where(PRESCRIPTION_REBATE.PRESCRIPTION_CODE.eq(prescriptionCode)).execute();
    }

    /**
     * 根据处方号获取
     * @param prescriptionCode
     * @return
     */
    public PrescriptionRebateParam getRebateByPrescriptionCode(String prescriptionCode){
        return db().select().from(PRESCRIPTION_REBATE).where(PRESCRIPTION_REBATE.PRESCRIPTION_CODE.eq(prescriptionCode)).fetchAnyInto(PrescriptionRebateParam.class);
    }

    /**
     * 更新实际返利金额
     * @param prescriptionCode
     * @param realRebateMoney
     */
    public void updateRealRebateMoney(String prescriptionCode, BigDecimal realRebateMoney){
        db().update(PRESCRIPTION_REBATE).set(PRESCRIPTION_REBATE.REAL_REBATE_MONEY,realRebateMoney).where(PRESCRIPTION_REBATE.PRESCRIPTION_CODE.eq(prescriptionCode))
            .execute();
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    public PageResult<PrescriptionRebateVo> getPageList(PrescriptionRebateListParam param){
        SelectJoinStep<? extends Record> select = selectOptions();
        select=buildOptions(select,param);
        select.orderBy(PRESCRIPTION_REBATE.CREATE_TIME.desc());
        PageResult<PrescriptionRebateVo> result=this.getPageResult(select,param.getCurrentPage(),param.getPageRows(),PrescriptionRebateVo.class);
        return result;

    }
    public SelectJoinStep<? extends Record> selectOptions(){
        SelectJoinStep<? extends Record> select=db().select(DOCTOR.NAME.as("doctorName"),DOCTOR.MOBILE,USER.USERNAME.as("userName"),PRESCRIPTION_REBATE.asterisk())
            .from(PRESCRIPTION_REBATE);
        select.leftJoin(DOCTOR).on(DOCTOR.ID.eq(PRESCRIPTION_REBATE.DOCTOR_ID))
            .leftJoin(PRESCRIPTION).on(PRESCRIPTION.PRESCRIPTION_CODE.eq(PRESCRIPTION_REBATE.PRESCRIPTION_CODE))
            .leftJoin(USER).on(USER.USER_ID.eq(PRESCRIPTION.USER_ID));
        return select;
    }
    protected SelectJoinStep<? extends Record> buildOptions(SelectJoinStep<? extends Record> select,PrescriptionRebateListParam param){
        select.where(PRESCRIPTION_REBATE.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        if(StringUtils.isNotBlank(param.getDoctorName())){
            select.where(DOCTOR.NAME.like(this.likeValue(param.getDoctorName())));
        }
        if(param.getDoctorId()!=null){
            select.where(PRESCRIPTION_REBATE.DOCTOR_ID.eq(param.getDoctorId()));
        }
        if(param.getStatus()!=null){
            select.where(PRESCRIPTION_REBATE.STATUS.eq(param.getStatus()));
        }
        if(param.getStartTime()!=null){
            select.where(PRESCRIPTION_REBATE.CREATE_TIME.ge(DateUtil.beginOfDay(param.getStartTime()).toTimestamp()));
        }
        if(param.getEndTime()!=null){
            select.where(PRESCRIPTION_REBATE.CREATE_TIME.le(DateUtil.endOfDay(param.getEndTime()).toTimestamp()));
        }
        return select;
    }

    /**
     * 获取List
     * @param param
     * @return
     */
    public List<PrescriptionRebateReportVo> getList(PrescriptionRebateListParam param){
        SelectJoinStep<? extends Record> select = selectOptions();
        select=buildOptions(select,param);
        select.orderBy(PRESCRIPTION_REBATE.CREATE_TIME.desc());
        return select.fetchInto(PrescriptionRebateReportVo.class);
    }

}

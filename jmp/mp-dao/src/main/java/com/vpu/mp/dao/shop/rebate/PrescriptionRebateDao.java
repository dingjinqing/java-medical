package com.vpu.mp.dao.shop.rebate;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PrescriptionRebateRecord;
import com.vpu.mp.service.pojo.shop.rebate.PrescriptionRebateListParam;
import com.vpu.mp.service.pojo.shop.rebate.PrescriptionRebateParam;
import com.vpu.mp.service.pojo.shop.rebate.PrescriptionRebateVo;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.DOCTOR;
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
    public void updateStatus(String prescriptionCode,Byte status){
        db().update(PRESCRIPTION_REBATE).set(PRESCRIPTION_REBATE.STATUS,status).where(PRESCRIPTION_REBATE.PRESCRIPTION_CODE.eq(prescriptionCode))
            .execute();
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
     * 分页查询
     * @param param
     * @return
     */
    public PageResult<PrescriptionRebateVo> getPageList(PrescriptionRebateListParam param){
        SelectJoinStep<? extends Record> select = db()
            .select(DOCTOR.NAME.as("doctorName"),DOCTOR.MOBILE,PRESCRIPTION_REBATE.asterisk())
            .from(PRESCRIPTION_REBATE);
        select.leftJoin(DOCTOR).on(DOCTOR.ID.eq(PRESCRIPTION_REBATE.DOCTOR_ID));
        select.where(PRESCRIPTION_REBATE.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        select=buildOptions(select,param);
        select.orderBy(PRESCRIPTION_REBATE.CREATE_TIME.desc());
        PageResult<PrescriptionRebateVo> result=this.getPageResult(select,param.getCurrentPage(),param.getPageRows(),PrescriptionRebateVo.class);
        return result;

    }
    protected SelectJoinStep<? extends Record> buildOptions(SelectJoinStep<? extends Record> select,PrescriptionRebateListParam param){
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
            select.where(PRESCRIPTION_REBATE.CREATE_TIME.ge(param.getStartTime()));
        }
        if(param.getEndTime()!=null){
            select.where(PRESCRIPTION_REBATE.CREATE_TIME.ge(param.getEndTime()));
        }
        return select;
    }

}

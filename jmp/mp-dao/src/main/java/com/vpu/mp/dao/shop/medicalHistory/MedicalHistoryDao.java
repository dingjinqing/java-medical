package com.vpu.mp.dao.shop.medicalHistory;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.medicalHistory.MedicalHistoryListParam;
import com.vpu.mp.service.pojo.shop.medicalHistory.MedicalHistoryListVo;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.medicalHistory.MedicalHistoryPageInfoParam;
import com.vpu.mp.service.pojo.shop.medicalHistory.MedicalHistoryPageInfoVo;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.tables.MedicalHistory.MEDICAL_HISTORY;


/**
 * @author 赵晓东
 * @description
 * @create 2020-07-07 10:40
 */
@Repository
public class MedicalHistoryDao extends ShopBaseDao {

    /**
     * 病历分页查询（测试，不使用此接口）
     *
     * @param param 病历入参
     * @return PageResult<MedicalHistoryListVo>
     */
    public PageResult<MedicalHistoryListVo> listPageResult(MedicalHistoryListParam param) {
        SelectConditionStep<Record> and = db().select().from(MEDICAL_HISTORY)
            .where(MEDICAL_HISTORY.ID.eq(param.getId()))
            .and(MEDICAL_HISTORY.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        return getPageResult(and, param, MedicalHistoryListVo.class);
    }

    /**
     * 病历详情分页查询
     *
     * @param medicalHistoryListParam 病历详情入参
     * @return PageResult<MedicalHistoryListVo>
     */
    public PageResult<MedicalHistoryListVo> getListPageResult(MedicalHistoryListParam medicalHistoryListParam) {
        SelectConditionStep<Record> and = db().select().from(MEDICAL_HISTORY)
            .where(MEDICAL_HISTORY.ID.eq(medicalHistoryListParam.getId())
                .and(MEDICAL_HISTORY.IS_DELETE.eq(DelFlag.NORMAL_VALUE)));
        return getPageResult(and, medicalHistoryListParam, MedicalHistoryListVo.class);
    }

    /**
     * 病历表列表查询
     * @param medicalHistoryPageInfoParam 病历表列表入参
     * @return PageResult<MedicalHistoryPageInfoVo>
     */
    public PageResult<MedicalHistoryPageInfoVo> getPageInfo(MedicalHistoryPageInfoParam medicalHistoryPageInfoParam) {
        SelectConditionStep<Record> and = db().select().from(MEDICAL_HISTORY)
            .where(MEDICAL_HISTORY.PATIENT_ID.eq(medicalHistoryPageInfoParam.getPatientId())
                .and(MEDICAL_HISTORY.IS_DELETE.eq(DelFlag.NORMAL_VALUE)));
        return getPageResult(and, medicalHistoryPageInfoParam, MedicalHistoryPageInfoVo.class);
    }
}

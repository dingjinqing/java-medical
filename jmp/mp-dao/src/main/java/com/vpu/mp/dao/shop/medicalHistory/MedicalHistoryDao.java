package com.vpu.mp.dao.shop.medicalHistory;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.medicalHistory.MedicalHistoryParam;
import com.vpu.mp.common.pojo.shop.medicalHistory.MedicalHistoryVo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
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
     * 病历分页查询
     * @param param 病历入参
     * @return PageResult<MedicalHistoryVo>
     */
    public PageResult<MedicalHistoryVo> listPageResult(MedicalHistoryParam param) {
        SelectConditionStep<Record> and = db().select().from(MEDICAL_HISTORY)
            .where(MEDICAL_HISTORY.ID.eq(param.getId()))
            .and(MEDICAL_HISTORY.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        return getPageResult(and, param, MedicalHistoryVo.class);
    }
}

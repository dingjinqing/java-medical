package com.vpu.mp.service.shop.medicine;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.medicalHistory.MedicalHistoryParam;
import com.vpu.mp.common.pojo.shop.medicalHistory.MedicalHistoryVo;
import com.vpu.mp.common.pojo.shop.prescription.PrescriptionListParam;
import com.vpu.mp.common.pojo.shop.prescription.PrescriptionListVo;
import com.vpu.mp.dao.shop.medicalHistory.MedicalHistoryDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.Tables.PRESCRIPTION;

/**
 * @author 赵晓东
 * @description
 * @create 2020-07-07 10:29
 */
@Service
public class MedicalHistoryService extends ShopBaseService {

    @Autowired
    private MedicalHistoryDao medicalHistoryDao;

    /**
     * 分页
     * @return
     */
    public PageResult<MedicalHistoryVo> listPageResult(MedicalHistoryParam param){
        return medicalHistoryDao.listPageResult(param);
    }
}

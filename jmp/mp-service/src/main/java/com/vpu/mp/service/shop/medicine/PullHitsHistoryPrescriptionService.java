package com.vpu.mp.service.shop.medicine;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.medical.FetchMedicalAdviceParam;
import com.vpu.mp.service.pojo.shop.medicalhistory.FetchMedicalHistoryParam;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionParam;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author zhaoxiaodong
 * @create 2020-07-21 10:09
 */

@Service
public class PullHitsHistoryPrescriptionService extends ShopBaseService {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private MedicalAdviceService medicalAdviceService;


    /**
     * 通过患者姓名、患者手机号、患者身份证号码、患者医院唯一编码、增量查询时间从hits系统拉取本地病历信息和处方信息
     * @param fetchMedicalHistoryParam 拉取时入参
     * @return JsonResult
     */
    public JsonResult pullExternalHistoryPrescription(FetchMedicalHistoryParam fetchMedicalHistoryParam){
        FetchPrescriptionParam fetchPrescriptionParam = new FetchPrescriptionParam();
        FetchMedicalAdviceParam fetchMedicalAdviceParam = new FetchMedicalAdviceParam();
        FieldsUtil.assign(fetchMedicalHistoryParam, fetchPrescriptionParam);
        FieldsUtil.assign(fetchMedicalHistoryParam, fetchMedicalAdviceParam);
        JsonResult jsonResultMedicalHistory
            = medicalHistoryService.pullExternalMedicalHistoryList(fetchMedicalHistoryParam);
        JsonResult pullExternalMedicalHistoryList
            = medicalAdviceService.pullExternalMedicalHistoryList(fetchMedicalAdviceParam);
        JsonResult pullExternalAllPrescriptionInfo
            = prescriptionService.pullExternalAllPrescriptionInfo(fetchPrescriptionParam);
        // 拉取处方、病历、医嘱都成功
        if (jsonResultMedicalHistory.getError() > 0
            && pullExternalMedicalHistoryList.getError() > 0
            && pullExternalAllPrescriptionInfo.getError() > 0) {
            return jsonResultMedicalHistory;
        }
        // 失败
        return prescriptionService.pullExternalAllPrescriptionInfo(fetchPrescriptionParam);
    }
}

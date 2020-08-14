package com.vpu.mp.service.shop.medicine;


import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.dao.shop.medical.MedicalHistoryDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.medicalhistory.*;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientOneParam;
import com.vpu.mp.service.shop.patient.PatientService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.common.foundation.data.JsonResultCode.FETCH_HITS_NULL;

/**
 * @author 赵晓东
 * @description
 * @create 2020-07-07 10:29
 */
@Service
public class MedicalHistoryService extends ShopBaseService {

    @Autowired
    private MedicalHistoryDao medicalHistoryDao;
    @Autowired
    public PatientService patientService;
    @Autowired
    public PrescriptionService prescriptionService;

    /**
     * 查询单条病历详情
     * @param medicalHistoryListParam 病历详情入参
     * @return MedicalHistoryListVo
     */
    public MedicalHistoryListVo getMedicalHistoryDetail(MedicalHistoryListParam medicalHistoryListParam){
        return medicalHistoryDao.getMedicalHistoryDetail(medicalHistoryListParam);
    }

    /**
     * 分页查询病历展示页面字段
     * @param medicalHistoryPageInfoParam 病历表分页入参
     * @return PageResult<MedicalHistoryPageInfoVo>
     */
    public PageResult<MedicalHistoryPageInfoVo> getMedicalHistoryPageInfo(MedicalHistoryPageInfoParam medicalHistoryPageInfoParam){
        if (medicalHistoryPageInfoParam.getUserId() != null) {
            List<String> prescriptionNos = prescriptionService.getPrescriptionNosByUserId(medicalHistoryPageInfoParam.getUserId());
            medicalHistoryPageInfoParam.setPrescriptionNos(prescriptionNos);
        }
        return medicalHistoryDao.getMedicalHistoryPageInfo(medicalHistoryPageInfoParam);
    }

    /**
     * 拉取病历列表
     * @param fetchMedicalHistoryParam 拉取病历信息
     * @return JsonResult
     */
    public JsonResult pullExternalMedicalHistoryList(UserPatientOneParam fetchMedicalHistoryParam) {
        String appId = ApiExternalRequestConstant.APP_ID_HIS;
        Integer shopId = getShopId();
        String serviceName = ApiExternalRequestConstant.SERVICE_NAME_FETCH_MEDICAL_HISTORY_INFOS;

        //增量
        Long lastRequestTime = saas().externalRequestHistoryService.getLastRequestTime(ApiExternalRequestConstant.APP_ID_HIS,
            shopId, serviceName);
        fetchMedicalHistoryParam.setStartTime(lastRequestTime);

        //拉取数据
        ApiExternalRequestResult apiExternalRequestResult = saas().apiExternalRequestService
            .externalRequestGate(appId, shopId, serviceName, Util.toJson(fetchMedicalHistoryParam));

        // 数据拉取错误
        if (!ApiExternalRequestConstant.ERROR_CODE_SUCCESS.equals(apiExternalRequestResult.getError())) {
            JsonResult result = new JsonResult();
            result.setError(apiExternalRequestResult.getError());
            result.setMessage(apiExternalRequestResult.getMsg());
            result.setContent(apiExternalRequestResult.getData());
            return result;
        }
        if (apiExternalRequestResult.getData() == null) {
            return new JsonResult().fail("zh_CN", FETCH_HITS_NULL);
        }
        //得到Data
        String dataJson = apiExternalRequestResult.getData();
        List<FetchMedicalHistoryVo> fetchMedicalHistoryVos = Util.parseJson(dataJson, new TypeReference<List<FetchMedicalHistoryVo>>() {
        });

        // 数据库新增或更新
        assert fetchMedicalHistoryVos != null;
        UserPatientOneParam patientParam = new UserPatientOneParam();
        patientParam.setName(fetchMedicalHistoryParam.getName());
        patientParam.setMobile(fetchMedicalHistoryParam.getMobile());
        patientParam.setIdentityCode(fetchMedicalHistoryParam.getIdentityCode());
        PatientOneParam patientInfo = patientService.getPatientByNameAndMobile(patientParam);
        for (FetchMedicalHistoryVo fetchMedicalHistoryVo : fetchMedicalHistoryVos) {
            //如果没有当前病历就新增
            fetchMedicalHistoryVo.setPatientId(patientInfo.getId());
            fetchMedicalHistoryVo.setPatientName(patientInfo.getName());
            if (medicalHistoryDao.getMedicalHistoryDetailByCode(fetchMedicalHistoryVo.getPosCode()) == null) {
                medicalHistoryDao.addHitsMedicalHistory(fetchMedicalHistoryVo);

            } else {  //否则就修改
                medicalHistoryDao.updateHitsMedicalHistory(fetchMedicalHistoryVo);
            }
        }
        return JsonResult.success();
    }
}

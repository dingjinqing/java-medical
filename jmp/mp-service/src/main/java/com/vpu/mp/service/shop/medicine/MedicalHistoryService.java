package com.vpu.mp.service.shop.medicine;


import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.dao.foundation.transactional.DbTransactional;
import com.vpu.mp.dao.foundation.transactional.DbType;
import com.vpu.mp.dao.shop.medicalHistory.MedicalHistoryDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;

import com.vpu.mp.service.pojo.shop.medicalHistory.*;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionItemVo;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionParam;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

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
        return medicalHistoryDao.getMedicalHistoryPageInfo(medicalHistoryPageInfoParam);
    }

    /**
     * 拉取病历列表
     * @param fetchMedicalHistoryParam 拉取病历信息
     * @return JsonResult
     */
    @DbTransactional(type = DbType.SHOP_DB)
    public JsonResult pullExternalMedicalHistoryList(FetchMedicalHistoryParam fetchMedicalHistoryParam) {
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
        //得到Data
        String dataJson = apiExternalRequestResult.getData();
        ArrayList<FetchMedicalHistoryVo> fetchMedicalHistoryVos = Util.parseJson(dataJson, new TypeReference<List<FetchPrescriptionVo>>() {
        });

        // 数据库新增或更新
        assert fetchMedicalHistoryVos != null;
        for (FetchMedicalHistoryVo fetchMedicalHistoryVo : fetchMedicalHistoryVos) {
            //如果没有当前病历就新增
            if (medicalHistoryDao.getMedicalHistoryDetailByCode(fetchMedicalHistoryVo.getPosCode()) == null) {
                medicalHistoryDao.addHitsMedicalHistory(fetchMedicalHistoryVo);

            } else {  //否则就修改
                medicalHistoryDao.updateHitsMedicalHistory(fetchMedicalHistoryVo);
            }
        }
        return JsonResult.success();
    }
}

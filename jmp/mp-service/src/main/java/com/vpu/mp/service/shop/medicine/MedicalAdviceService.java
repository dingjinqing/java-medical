package com.vpu.mp.service.shop.medicine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.dao.foundation.transactional.DbTransactional;
import com.vpu.mp.dao.foundation.transactional.DbType;
import com.vpu.mp.dao.shop.medical.MedicalAdviceDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.medical.FetchMedicalAdviceParam;
import com.vpu.mp.service.pojo.shop.medical.FetchMedicalAdviceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 赵晓东
 * @description 拉取医嘱列表
 * @create 2020-07-24 09:35
 **/

@Service
public class MedicalAdviceService extends ShopBaseService {

    @Autowired
    private MedicalAdviceDao medicalAdviceDao;

    /**
     * 拉取医嘱列表
     * @param fetchMedicalAdviceParam 拉取医嘱信息
     * @return JsonResult
     */
    @DbTransactional(type = DbType.SHOP_DB)
    public JsonResult pullExternalMedicalHistoryList(FetchMedicalAdviceParam fetchMedicalAdviceParam) {
        String appId = ApiExternalRequestConstant.APP_ID_HIS;
        Integer shopId = getShopId();
        String serviceName = ApiExternalRequestConstant.SERVICE_NAME_FETCH_MEDICAL_ADVICE_INFOS;

        //增量
        Long lastRequestTime = saas().externalRequestHistoryService.getLastRequestTime(ApiExternalRequestConstant.APP_ID_HIS,
            shopId, serviceName);
        fetchMedicalAdviceParam.setStartTime(lastRequestTime);

        //拉取数据
        ApiExternalRequestResult apiExternalRequestResult = saas().apiExternalRequestService
            .externalRequestGate(appId, shopId, serviceName, Util.toJson(fetchMedicalAdviceParam));

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
        ArrayList<FetchMedicalAdviceVo> fetchMedicalAdviceVos = Util.parseJson(dataJson, new TypeReference<List<FetchMedicalAdviceVo>>() {
        });

        // 数据库新增或更新
        assert fetchMedicalAdviceVos != null;
        for (FetchMedicalAdviceVo fetchMedicalAdviceVo : fetchMedicalAdviceVos) {
            //如果没有当前医嘱就新增
            if (medicalAdviceDao.getMedicalAdviceByCode(fetchMedicalAdviceVo.getPosCode()) == null
                && medicalAdviceDao.getMedicalAdviceByCode(fetchMedicalAdviceVo.getPosCode()) == 0) {
                medicalAdviceDao.addHitsMedicalAdvice(fetchMedicalAdviceVo);

            } else {  //否则就修改
                medicalAdviceDao.updateHitsMedicalAdvice(fetchMedicalAdviceVo);
            }
        }
        return JsonResult.success();
    }

}

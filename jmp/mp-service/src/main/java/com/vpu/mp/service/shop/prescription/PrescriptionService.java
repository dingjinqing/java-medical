package com.vpu.mp.service.shop.prescription;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.pojo.saas.api.ApiExternalConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestParam;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.dao.foundation.transactional.DbTransactional;
import com.vpu.mp.dao.foundation.transactional.DbType;
import com.vpu.mp.dao.shop.patient.UserPatientCoupleDao;

import com.vpu.mp.service.pojo.shop.prescription.*;
import com.vpu.mp.dao.shop.goods.GoodsDao;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionItemDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.saas.api.ApiExternalBaseService;
import com.vpu.mp.service.saas.api.ApiExternalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 处方
 * @author 孔德成
 * @date 2020/7/2 14:25
 */
@Service
public class PrescriptionService extends ShopBaseService {

    @Autowired
    protected PrescriptionDao prescriptionDao;
    @Autowired
    protected PrescriptionItemDao prescriptionItemDao;
    @Autowired
    protected GoodsMedicalInfoDao goodsMedicalInfoDao;
    @Autowired
    protected UserPatientCoupleDao userPatientCoupleDao;
    @Autowired
    protected GoodsDao goodsDao;


    /**
     * 保存处方
     */
    public void addPrescription(PrescriptionParam param){
        param.setId(null);
        int save = prescriptionDao.save(param);
        if (save>0) {
            param.getList().forEach(item->item.setId(null));
            prescriptionItemDao.batchSave(param.getList());
        }
    }

    /**
     * 查询处方
     * @return
     */
    public PrescriptionVo getById(Integer id){
        return prescriptionDao.getById(id,PrescriptionVo.class);
    }

    /**
     * 分页
     * @return
     */
    public PageResult<PrescriptionListVo> listPageResult(PrescriptionListParam param){
        return prescriptionDao.listPageResult(param);
    }

    /**
     * *****
     *  获取处方关联商品
     *  1商品id 2通用名+系数 3系数
     * @param goodsId 商品id
     * @param patientId
     * @param goodsCommonName 商品通用名
     * @param goodsQualityRatio 商品规格系数
     * @return 处方明细
     */
    public PrescriptionVo getByGoodsInfo(Integer goodsId, Integer patientId, String goodsCommonName, String goodsQualityRatio, String productionEnterprise) {
        PrescriptionVo prescriptionItem = prescriptionDao.getValidByGoodsId(goodsId,patientId);
        if (prescriptionItem==null){
            prescriptionItem=  prescriptionDao.getValidByCommonNameAndQualityRatio(patientId,goodsCommonName,goodsQualityRatio,productionEnterprise);
        }
        if (prescriptionItem==null){
            prescriptionItem = prescriptionDao.getValidByCommonNameAndQualityRatio(patientId,goodsCommonName, goodsQualityRatio);
        }
        if (prescriptionItem==null){
            prescriptionItem = prescriptionDao.getValidByCommonName(patientId,goodsCommonName);
        }
        return prescriptionItem;
    }



    /**
     * 患者的处方列表
     * @param param
     * @return
     */
    public PageResult<PrescriptionSimpleVo> listPageResultWx(PrescriptionListParam param) {
        Integer patientId = userPatientCoupleDao.defaultPatientIdByUser(param.getUserId());
        param.setPatientId(patientId);
        return prescriptionDao.listPageResultWx(param);
    }

    /**
     * *****
     * 处方号
     * @param prescriptionNo 处方号
     */
    public PrescriptionInfoVo getInfoByPrescriptionNo(String prescriptionNo) {
        /**处方号*/
        PrescriptionInfoVo byPrescription = prescriptionDao.getInfoByPrescriptionNo(prescriptionNo);
        if (byPrescription==null){
            return null;
        }
        List<PrescriptionItemInfoVo> prescriptionItemSimpleVos = prescriptionItemDao.listByPrescriptionNo(prescriptionNo);
        byPrescription.setItemList(prescriptionItemSimpleVos);
        return byPrescription;
    }

    /**
     * *****
     * 处方号
     * @param prescriptionNo 处方号
     * @return
     */
    public PrescriptionVo getDoByPrescriptionNo(String prescriptionNo){
        return prescriptionDao.getDoByPrescriptionNo(prescriptionNo);
    }

    /**
     * *****
     * 获取患者的处方药集合（包括已删除，未上架以及售罄的）
     * @param patientId
     * @return
     */
    public List<Integer> getPrescriptionGoodsIdsByPatientId(Integer patientId) {
        List<String> prescriptionNos = prescriptionDao.getValidPrescriptionByPatient(patientId);
        List<Integer> goodsIds = prescriptionItemDao.getPrescriptionGoodsIdsByPrescriptionNos(prescriptionNos);
        return goodsIds;
    }

    /**
     * @description hits系统拉取处方信息，处方详情
     * @author zhaoxiaodong
     * @create 2020-7-16 10:36
     */

    /**
     * 拉取处方列表
     * @param fetchPrescriptionParam 处方页面入参
     * @return JsonResult
     */
    @DbTransactional(type = DbType.SHOP_DB)
    public JsonResult pullExternalAllPrescriptionInfo(FetchPrescriptionParam fetchPrescriptionParam) {
        String appId = ApiExternalConstant.APP_ID_HIS;
        Integer shopId = getShopId();
        String serviceName = ApiExternalConstant.SERVICE_NAME_FETCH_PRESCRIPTION_INFOS;

        //增量
        Long lastRequestTime = saas().externalRequestHistoryService.getLastRequestTime(ApiExternalConstant.APP_ID_HIS,
            shopId, ApiExternalConstant.SERVICE_NAME_FETCH_PRESCRIPTION_INFOS);
        fetchPrescriptionParam.setStartTime(lastRequestTime);

        //拉取数据
        ApiExternalRequestResult apiExternalRequestResult = saas().apiExternalRequestService
            .externalRequestGate(appId, shopId, serviceName, Util.toJson(fetchPrescriptionParam));

        // 数据拉取错误
        if (!ApiExternalConstant.ERROR_CODE_SUCCESS.equals(apiExternalRequestResult.getError())) {
            JsonResult result = new JsonResult();
            result.setError(apiExternalRequestResult.getError());
            result.setMessage(apiExternalRequestResult.getMsg());
            result.setContent(apiExternalRequestResult.getData());
            return result;
        }
        //得到Data
        String dataJson = apiExternalRequestResult.getData();
        ArrayList<FetchPrescriptionVo> fetchPrescriptionVos = Util.parseJson(dataJson, new TypeReference<List<FetchPrescriptionVo>>() {
        });

        //数据库新增或更新
        for (FetchPrescriptionVo prescriptionVo : fetchPrescriptionVos) {
            //如果没有当前处方就新增
            if (prescriptionDao.getDoByPrescriptionNo(prescriptionVo.getPrescriptionCode()) == null) {
                prescriptionDao.addHitsPrescription(prescriptionVo);
                //遍历得到的处方表中的处方明细，如果没有就新增，有就更新
                for (FetchPrescriptionItemVo fetchPrescriptionItemVo : prescriptionVo.getList()) {
                    if (prescriptionItemDao.getPrescriptionById(fetchPrescriptionItemVo.getId()) != null) {
                        prescriptionItemDao.save(fetchPrescriptionItemVo);
                    }
                }
            } else {  //否则就修改
                prescriptionDao.updateHitsPrescription(prescriptionVo);
                for (FetchPrescriptionItemVo fetchPrescriptionItemVo : prescriptionVo.getList()) {
                    if (prescriptionItemDao.getPrescriptionById(fetchPrescriptionItemVo.getId()) != null) {
                        prescriptionItemDao.save(fetchPrescriptionItemVo);
                    } else {
                        prescriptionItemDao.updateHitsPrescriptionItem(fetchPrescriptionItemVo);
                    }
                }
            }
        }
        return JsonResult.success();
    }

    /**
     * @param fetchPrescriptionOneParam 更新单个处方
     * @return JsonResult
     */
    @DbTransactional(type = DbType.SHOP_DB)
    public JsonResult pullExternalOnePrescriptionInfo(FetchPrescriptionOneParam fetchPrescriptionOneParam) {
        String appId = ApiExternalConstant.APP_ID_HIS;
        Integer shopId = getShopId();
        String serviceName = ApiExternalConstant.SERVICE_NAME_FETCH_PRESCRIPTION_INFOS;
        String str = "[{\"id\":55,\"prescriptionCode\":10,\"posCode\":1,\"patientId\":1,\"patientTreatmentCode\":1,\"identityCode\":11,\"patientName\":\"张麻子\",\"patientAge\":58,\"patientSex\":1,\"list\":[{\"id\":11,\"posCode\":11,\"posDetailCode\":11,\"prescriptionCode\":11,\"prescriptionDetailCode\":11,\"goodsId\":11,\"goodsCommonName\":\"麻黄\"}]}]";
                //拉取数据
//        ApiExternalRequestResult apiExternalRequestResult = saas().apiExternalRequestService
//            .externalRequestGate(appId, shopId, serviceName, Util.toJson(fetchPrescriptionOneParam));

//        // 数据拉取错误
//        if (!ApiExternalConstant.ERROR_CODE_SUCCESS.equals(apiExternalRequestResult.getError())) {
//            JsonResult result = new JsonResult();
//            result.setError(apiExternalRequestResult.getError());
//            result.setMessage(apiExternalRequestResult.getMsg());
//            result.setContent(apiExternalRequestResult.getData());
//            return result;
//        }
        //得到Data
        //String dataJson = apiExternalRequestResult.getData();
        String dataJson = str;
        ArrayList<FetchPrescriptionVo> fetchPrescriptionVos = Util.parseJson(dataJson, new TypeReference<List<FetchPrescriptionVo>>() {
        });
//        FetchPrescriptionVo fetchPrescriptionVos = Util.parseJson(dataJson, FetchPrescriptionVo.class);
//
        //数据库新增或更新
        for (FetchPrescriptionVo prescriptionVo : fetchPrescriptionVos) {
            //如果没有当前处方就新增
            PrescriptionVo doByPrescriptionNo = prescriptionDao.getDoByPrescriptionNo(prescriptionVo.getPrescriptionCode());
            if (doByPrescriptionNo == null) {
                prescriptionDao.addHitsPrescription(prescriptionVo);
                //遍历得到的处方表中的处方明细，如果没有就新增，有就更新
                for (FetchPrescriptionItemVo fetchPrescriptionItemVo : prescriptionVo.getList()) {
                    if (prescriptionItemDao.getPrescriptionById(fetchPrescriptionItemVo.getId()) != null) {
                        prescriptionItemDao.save(fetchPrescriptionItemVo);
                    }
                }
            } else {  //否则就修改
                prescriptionDao.updateHitsPrescription(prescriptionVo);
                for (FetchPrescriptionItemVo fetchPrescriptionItemVo : prescriptionVo.getList()) {
                    if (prescriptionItemDao.getPrescriptionById(fetchPrescriptionItemVo.getId()) != null) {
                        prescriptionItemDao.save(fetchPrescriptionItemVo);
                    } else {
                        prescriptionItemDao.updateHitsPrescriptionItem(fetchPrescriptionItemVo);
                    }
                }
            }
        }
        return JsonResult.success();
    }

}

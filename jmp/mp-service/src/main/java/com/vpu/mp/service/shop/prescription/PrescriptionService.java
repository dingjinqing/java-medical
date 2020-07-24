package com.vpu.mp.service.shop.prescription;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.*;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsDo;
import com.vpu.mp.dao.foundation.transactional.DbTransactional;
import com.vpu.mp.dao.foundation.transactional.DbType;
import com.vpu.mp.dao.shop.doctor.DoctorDao;
import com.vpu.mp.dao.shop.goods.GoodsDao;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.dao.shop.patient.PatientDao;
import com.vpu.mp.dao.shop.patient.UserPatientCoupleDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionItemDao;
import com.vpu.mp.db.shop.tables.Goods;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsMatchParam;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsDetailVo;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsPrdVo;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.prescription.*;
import com.vpu.mp.service.pojo.shop.prescription.config.PrescriptionConstant;
import com.vpu.mp.service.shop.goods.MedicalGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    protected DoctorDao doctorDao;
    @Autowired
    protected PatientDao patientDao;
    @Autowired
    public MedicalGoodsService medicalGoodsService;

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
     * 根据处方id获取处方所关联的药品id
     * @param prescriptionCode
     * @return
     */
    public List<Integer> getPrescriptionGoodsIdsByPrescriptionCode(String prescriptionCode){
        return prescriptionItemDao.getPrescriptionGoodsIdsByPrescriptionNos(Collections.singletonList(prescriptionCode));
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
        String appId = ApiExternalRequestConstant.APP_ID_HIS;
        Integer shopId = getShopId();
        String serviceName = ApiExternalRequestConstant.SERVICE_NAME_FETCH_PRESCRIPTION_INFOS;

        //增量
        Long lastRequestTime = saas().externalRequestHistoryService.getLastRequestTime(ApiExternalRequestConstant.APP_ID_HIS,
            shopId, ApiExternalRequestConstant.SERVICE_NAME_FETCH_PRESCRIPTION_INFOS);
        fetchPrescriptionParam.setStartTime(lastRequestTime);

        //拉取数据
        ApiExternalRequestResult apiExternalRequestResult = saas().apiExternalRequestService
            .externalRequestGate(appId, shopId, serviceName, Util.toJson(fetchPrescriptionParam));

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
        String appId = ApiExternalRequestConstant.APP_ID_HIS;
        Integer shopId = getShopId();
        String serviceName = ApiExternalRequestConstant.SERVICE_NAME_FETCH_PRESCRIPTION_INFOS;
        String str = "[{\"id\":100,\"prescriptionCode\":15,\"posCode\":1,\"patientId\":1,\"patientTreatmentCode\":1,\"identityCode\":11,\"patientName\":\"张麻子\",\"patientAge\":58,\"patientSex\":1,\"list\":[{\"id\":11,\"posCode\":11,\"posDetailCode\":11,\"prescriptionCode\":15,\"prescriptionDetailCode\":11,\"goodsId\":11,\"goodsCommonName\":\"麻黄\"}]}]";
                //拉取数据
        ApiExternalRequestResult apiExternalRequestResult = saas().apiExternalRequestService
            .externalRequestGate(appId, shopId, serviceName, Util.toJson(fetchPrescriptionOneParam));

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
        ArrayList<FetchPrescriptionVo> fetchPrescriptionVos = Util.parseJson(dataJson, new TypeReference<List<FetchPrescriptionVo>>() {
        });
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


    /**
     * 上传保存处方
     * @param param
     */
    public void insertPrescription(PrescriptionOneParam param){
        PrescriptionParam prescriptionParam=buildPrescription(param);
        this.addPrescription(prescriptionParam);

    }

    /**
     * 生成处方，处方明细数据
     * @param param
     * @return
     */
    public PrescriptionParam buildPrescription(PrescriptionOneParam param){

        DoctorOneParam doctor=doctorDao.getOneInfo(param.getDoctorId());
        PatientOneParam patient = patientDao.getOneInfo(param.getPatientId());
        //生成处方
        PrescriptionParam prescriptionParam=new PrescriptionParam();
        FieldsUtil.assign(param,prescriptionParam);
        prescriptionParam.setDoctorCode(doctor.getHospitalCode());
        prescriptionParam.setDoctorName(doctor.getName());
        prescriptionParam.setPatientName(patient.getName());
        prescriptionParam.setPatientSex(patient.getSex());
        prescriptionParam.setPatientDiseaseHistory(patient.getDiseaseHistory());
        prescriptionParam.setPatientAllergyHistory(patient.getAllergyHistory());
        prescriptionParam.setIdentityType(patient.getIdentityType());
        prescriptionParam.setIdentityCode(patient.getIdentityCode());
        prescriptionParam.setPatientTreatmentCode(patient.getTreatmentCode());
        prescriptionParam.setPrescriptionCode(IncrSequenceUtil.generatePrescriptionCode(PrescriptionConstant.PRESCRIPTION_CODE_PREFIX));
        prescriptionParam.setExpireType(PrescriptionConstant.EXPIRE_TYPE_TIME);
        prescriptionParam.setPrescriptionExpireTime(DateUtils.getTimeStampPlus(PrescriptionConstant.PRESCRIPTION_EXPIRE_DAY, ChronoUnit.DAYS));
        //药品信息生成处方明细列表
        List<GoodsMedicalInfoDo> goodsMedicalInfoDoList=goodsMedicalInfoDao.listByGoodsIds(param.getGoodsIdList());

        List<PrescriptionItemParam> itemList=new ArrayList<>();
        for (GoodsMedicalInfoDo info: goodsMedicalInfoDoList) {
            PrescriptionItemParam item=new PrescriptionItemParam();
            //药品信息映射
            FieldsUtil.assign(info,item);
            item.setPrescriptionCode(prescriptionParam.getPrescriptionCode());
            GoodsDo goods=goodsDao.getByGoodsId(info.getGoodsId());
            item.setMedicinePrice(goods.getShopPrice());
            itemList.add(item);
        }
        prescriptionParam.setList(itemList);
        return prescriptionParam;
    }

    /**
     * 根据处方号匹配系统中已有药品信息列表
     * @param code
     * @return
     */
    public List<GoodsPrdVo> listGoodsByPrescriptionCode(String code) {
        List<PrescriptionItemInfoVo> prescriptionItemList = prescriptionItemDao.listByPrescriptionNo(code);
        List<GoodsPrdVo> goodsList = new ArrayList<>();
        for (PrescriptionItemInfoVo prescriptionItem : prescriptionItemList) {
            GoodsMatchParam goodsMatchParam = new GoodsMatchParam();
            FieldsUtil.assign(prescriptionItem, goodsMatchParam);
            GoodsPrdVo goodsDetail = medicalGoodsService.matchGoodsMedicalDetail(goodsMatchParam);
            if (goodsDetail != null) {
                goodsList.add(goodsDetail);
            }
        }
        return goodsList;
    }
}

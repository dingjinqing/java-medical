package com.vpu.mp.service.shop.prescription;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.PrescriptionDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsDo;
import com.vpu.mp.dao.shop.doctor.DoctorDao;
import com.vpu.mp.dao.shop.goods.GoodsDao;
import com.vpu.mp.dao.shop.goods.GoodsMedicalInfoDao;
import com.vpu.mp.dao.shop.patient.PatientDao;
import com.vpu.mp.dao.shop.patient.UserPatientCoupleDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionItemDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsMatchParam;
import com.vpu.mp.service.pojo.shop.medical.goods.vo.GoodsPrdVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.audit.DoctorAuditedPrescriptionParam;
import com.vpu.mp.service.pojo.shop.patient.PatientConstant;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionItemVo;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionOneParam;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionParam;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionDrugVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionInfoVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionItemInfoVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionItemParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionListParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionListVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionOneParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionPatientListParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionSimpleVo;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionVo;
import com.vpu.mp.service.pojo.shop.prescription.config.PrescriptionConstant;
import com.vpu.mp.service.shop.goods.MedicalGoodsService;
import com.vpu.mp.service.shop.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.vpu.mp.common.foundation.data.JsonResultCode.FETCH_HITS_NULL;

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
    @Autowired
    public PatientService patientService;

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
     * 分页
     * @return
     */
    public PageResult<PrescriptionListVo> listPatientPageResult(PrescriptionPatientListParam param){
        if (param.getUserId() != null) {
            List<String> prescriptionNos = getPrescriptionNosByUserId(param.getUserId());
            param.setPrescriptionNos(prescriptionNos);
        }
        return prescriptionDao.listPatientPageResult(param);
    }

    /**
     * *****
     *  获取处方关联商品
     *  1商品id 2通用名+系数 3系数
     * @param goodsId 商品id
     * @param param
     * @param goodsCommonName 商品通用名
     * @param goodsQualityRatio 商品规格系数
     * @return 处方明细
     */
    public PrescriptionVo getByGoodsInfo(Integer goodsId, UserPatientParam param, String goodsCommonName, String goodsQualityRatio, String productionEnterprise) {
        UserPatientParam userPatientParam = patientService.getUserPatient(param);
        if (userPatientParam==null){
            return null;
        }
        List<String> prescriptionNos = getValidPrescriptionByUserPatient(userPatientParam);
        PrescriptionVo prescriptionItem = prescriptionDao.getValidByGoodsId(goodsId,prescriptionNos);
        if (prescriptionItem==null){
            prescriptionItem=  prescriptionDao.getValidByCommonNameAndQualityRatio(prescriptionNos,goodsCommonName,goodsQualityRatio,productionEnterprise);
        }
        if (prescriptionItem==null){
            prescriptionItem = prescriptionDao.getValidByCommonNameAndQualityRatio(prescriptionNos,goodsCommonName, goodsQualityRatio);
        }
        if (prescriptionItem==null){
            prescriptionItem = prescriptionDao.getValidByCommonName(prescriptionNos,goodsCommonName);
        }
        return prescriptionItem;
    }



    /**
     * 患者的处方列表
     * @param param
     * @return
     */
    public PageResult<PrescriptionSimpleVo> listPageResultWx(PrescriptionPatientListParam param) {
        UserPatientParam userPatientParam = userPatientCoupleDao.defaultPatientByUser(param.getUserId());
        if (userPatientParam==null){
            return null;
        }
        param.setUserPatientParam(userPatientParam);
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
     * @param param
     * @return
     */
    public List<Integer> getPrescriptionGoodsIdsByUserPatient(UserPatientParam param) {
        List<String> prescriptionNos = getValidPrescriptionByUserPatient(param);
        List<Integer> goodsIds = prescriptionItemDao.getPrescriptionGoodsIdsByPrescriptionNos(prescriptionNos);
        return goodsIds;
    }

    /**
     * *****
     * 获取用户患者的处方药集合（包括已删除，未上架以及售罄的）
     * @param userId
     * @return
     */
    public List<Integer> getPrescriptionGoodsIdsByUserId(Integer userId) {
        if (userId == null) {
            return new ArrayList<>();
        }
        UserPatientParam userPatientParam = userPatientCoupleDao.defaultPatientByUser(userId);
        return getPrescriptionGoodsIdsByUserPatient(userPatientParam);
    }

    /**
     * *****
     * 获取用户患者的处方药集合（包括已删除，未上架以及售罄的）
     * @param userId
     * @return
     */
    public List<String> getPrescriptionNosByUserId(Integer userId) {
        UserPatientParam userPatientParam = userPatientCoupleDao.defaultPatientByUser(userId);
        List<String> prescriptionNos = getValidPrescriptionByUserPatient(userPatientParam);
        return prescriptionNos;
    }
    /**
     * *****
     * 患者未过期的历史处方no
     * @param param
     * @return
     */
    public List<String> getValidPrescriptionByUserPatient(UserPatientParam param) {
        if (PatientConstant.FETCH.equals(param.getIsFetch())) {
            return prescriptionDao.getValidPrescriptionByPatient(param.getPatientId());
        } else {
            return prescriptionDao.getValidPrescriptionByUserPatient(param);
        }
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

        if (apiExternalRequestResult.getData() == null) {
            return new JsonResult().fail("zh_CN", FETCH_HITS_NULL);
        }
        //得到Data
        String dataJson = apiExternalRequestResult.getData();
        List<FetchPrescriptionVo> fetchPrescriptionVos = Util.parseJson(dataJson, new TypeReference<List<FetchPrescriptionVo>>() {
        });

        //数据库新增或更新
        assert fetchPrescriptionVos != null;
        for (FetchPrescriptionVo prescriptionVo : fetchPrescriptionVos) {
            //如果没有当前处方就新增
            if (prescriptionDao.getDoByPrescriptionNo(prescriptionVo.getPrescriptionCode()) == null) {
                prescriptionDao.addHitsPrescription(prescriptionVo);
                //遍历得到的处方表中的处方明细，如果没有就新增，有就更新
                for (FetchPrescriptionItemVo fetchPrescriptionItemVo : prescriptionVo.getDataList()) {
                    prescriptionItemDao.save(fetchPrescriptionItemVo);
                }
            } else {  //否则就修改
                prescriptionDao.updateHitsPrescription(prescriptionVo);
            }
        }
        return JsonResult.success();
    }

    /**
     * @param fetchPrescriptionOneParam 更新单个处方
     * @return JsonResult
     */
    public JsonResult pullExternalOnePrescriptionInfo(FetchPrescriptionOneParam fetchPrescriptionOneParam) {
        String appId = ApiExternalRequestConstant.APP_ID_HIS;
        Integer shopId = getShopId();
        String serviceName = ApiExternalRequestConstant.SERVICE_NAME_FETCH_PRESCRIPTION_DETAIL;

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
        if (apiExternalRequestResult.getData() == null) {
            return new JsonResult().fail("zh_CN", FETCH_HITS_NULL);
        }
        //得到Data
        String dataJson = apiExternalRequestResult.getData();
        List<FetchPrescriptionVo> fetchPrescriptionVos = Util.parseJson(dataJson, new TypeReference<List<FetchPrescriptionVo>>() {
        });
        //数据库新增或更新
        assert fetchPrescriptionVos != null;
        for (FetchPrescriptionVo prescriptionVo : fetchPrescriptionVos) {
            //如果没有当前处方就新增
            PrescriptionVo doByPrescriptionNo = prescriptionDao.getDoByPrescriptionNo(prescriptionVo.getPrescriptionCode());
            if (doByPrescriptionNo == null) {
                prescriptionDao.addHitsPrescription(prescriptionVo);
                //遍历得到的处方表中的处方明细，如果没有就新增，有就更新
                for (FetchPrescriptionItemVo fetchPrescriptionItemVo : prescriptionVo.getDataList()) {
                    prescriptionItemDao.save(fetchPrescriptionItemVo);
                }
            } else {  //否则就修改
                prescriptionDao.updateHitsPrescription(prescriptionVo);
            }
        }
        return JsonResult.success();
    }


    /**
     * 上传保存处方
     * @param param
     */
    public PrescriptionParam insertPrescription(PrescriptionOneParam param){
        PrescriptionParam prescriptionParam=buildPrescription(param);
        this.addPrescription(prescriptionParam);
        return prescriptionParam;
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
        prescriptionParam.setPatientAge(DateUtils.getAgeByBirthDay(patient.getBirthday()));
        prescriptionParam.setPatientDiseaseHistory(patient.getDiseaseHistory());
        prescriptionParam.setPatientAllergyHistory(patient.getAllergyHistory());
        prescriptionParam.setIdentityType(patient.getIdentityType());
        prescriptionParam.setIdentityCode(patient.getIdentityCode());
        prescriptionParam.setPatientTreatmentCode(patient.getTreatmentCode());
        prescriptionParam.setPrescriptionCode(IncrSequenceUtil.generatePrescriptionCode(PrescriptionConstant.PRESCRIPTION_CODE_PREFIX));
        prescriptionParam.setExpireType(PrescriptionConstant.EXPIRE_TYPE_TIME);
        prescriptionParam.setPrescriptionExpireTime(DateUtils.getTimeStampPlus(PrescriptionConstant.PRESCRIPTION_EXPIRE_DAY, ChronoUnit.DAYS));
        prescriptionParam.setStatus(PrescriptionConstant.STATUS_PASS);
        prescriptionParam.setIsValid(BaseConstant.YES);
        List<PrescriptionDrugVo> goodsList=param.getGoodsList();
        List<Integer> goodsIdList=goodsList.stream().map(PrescriptionDrugVo::getGoodsId).collect(Collectors.toList());
        Map<Integer,PrescriptionDrugVo> goodsMap=goodsList.stream().collect(Collectors.toMap(PrescriptionDrugVo::getGoodsId, Function.identity(),(x1, x2) -> x1));
        //药品信息生成处方明细列表
        List<GoodsMedicalInfoDo> goodsMedicalInfoDoList=goodsMedicalInfoDao.listByGoodsIds(goodsIdList);

        List<PrescriptionItemParam> itemList=new ArrayList<>();
        for (GoodsMedicalInfoDo info: goodsMedicalInfoDoList) {
            PrescriptionItemParam item=new PrescriptionItemParam();
            //药品信息映射
            FieldsUtil.assignWithIgnoreField(info,item,getPrescriptionIgnoreFields());
            item.setUseMethod(info.getGoodsUseMethod());
            item.setPrescriptionCode(prescriptionParam.getPrescriptionCode());
            GoodsDo goods=goodsDao.getByGoodsId(info.getGoodsId());
            item.setMedicinePrice(goods.getShopPrice());
            item.setDragSumNum(goodsMap.get(info.getGoodsId()).getDragSumNum());
            item.setDragSumUnit(info.getGoodsPackageUnit());
            item.setGoodsImg(goods.getGoodsImg());
            itemList.add(item);
        }
        prescriptionParam.setList(itemList);
        return prescriptionParam;
    }

    /**
     * 拷贝要忽略的字段
     * @return
     */
    private Set<String> getPrescriptionIgnoreFields(){
        Set<String> ignoreField = new HashSet<>(2);
        ignoreField.add("createTime");
        ignoreField.add("updateTime");
        return ignoreField;
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
            System.out.printf(goodsMatchParam.getGoodsCommonName());
            GoodsPrdVo goodsDetail = medicalGoodsService.matchGoodsMedicalDetail(goodsMatchParam);
            if (goodsDetail != null) {
                goodsList.add(goodsDetail);
            }
        }
        return goodsList;
    }

    /**
     * 医师获取处方列表
     * @param param
     */
    public PageResult<PrescriptionParam> auditedPrescriptionList(DoctorAuditedPrescriptionParam param) {
        PageResult<PrescriptionParam> result = prescriptionDao.listAuditedByDoctor(param);
        List<String> preCodeList = result.getDataList().stream().map(PrescriptionDo::getPrescriptionCode).collect(Collectors.toList());
        Map<String, List<PrescriptionItemParam>> stringPrescriptionItemInfoVoMap = prescriptionItemDao.mapByPrescriptionCodeList(preCodeList);
        result.getDataList().forEach(prescription -> {
            if (stringPrescriptionItemInfoVoMap.containsKey(prescription.getPrescriptionCode())){
                prescription.setList(stringPrescriptionItemInfoVoMap.get(prescription.getPrescriptionCode()));
            }
        });
        return result;
    }
}

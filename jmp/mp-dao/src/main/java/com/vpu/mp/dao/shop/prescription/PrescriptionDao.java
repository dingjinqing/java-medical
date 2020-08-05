package com.vpu.mp.dao.shop.prescription;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.patient.PatientConstant;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import com.vpu.mp.service.pojo.shop.prescription.*;
import com.vpu.mp.common.pojo.shop.table.PrescriptionDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PrescriptionRecord;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.GOODS_MEDICAL_INFO;
import static com.vpu.mp.db.shop.Tables.PRESCRIPTION;
import static com.vpu.mp.db.shop.Tables.PRESCRIPTION_ITEM;

/**
 * 处方
 *
 * @author 孔德成
 * @date 2020/7/2 14:17
 */
@Repository
public class PrescriptionDao extends ShopBaseDao {

    /**
     * 添加
     *
     * @param param
     * @return
     */
    public int save(PrescriptionDo param) {
        PrescriptionRecord record = db().newRecord(PRESCRIPTION, param);
        return record.insert();
    }

    /**
     * @description hits系统拉取处方信息入库
     * @author zhaoxiaodong
     * @create 2020-7-16 9:40
     */
    /**
     * 新增处方
     * @param fetchPrescriptionVo 处方入参
     */
    public void addHitsPrescription(FetchPrescriptionVo fetchPrescriptionVo){
        PrescriptionRecord prescriptionRecord = db().newRecord(PRESCRIPTION, fetchPrescriptionVo);
        prescriptionRecord.insert();
    }


    /**
     * 获取一条记录
     *
     * @param id
     * @return
     */
    public PrescriptionDo getById(Integer id) {
        return db().selectFrom(PRESCRIPTION).where(PRESCRIPTION.ID.eq(id)).fetchAnyInto(PrescriptionDo.class);
    }
    /**
     *
     * 获取
     * @param prescriptionCode
     * @return PrescriptionInfoVo
     */
    public PrescriptionInfoVo getInfoByPrescriptionNo(String prescriptionCode) {
         return db().select(PRESCRIPTION.PRESCRIPTION_CODE,PRESCRIPTION.PATIENT_TREATMENT_CODE,
                 PRESCRIPTION.PATIENT_NAME,PRESCRIPTION.PATIENT_AGE,PRESCRIPTION.PATIENT_SEX,PRESCRIPTION.DEPARTMENT_NAME,
                 PRESCRIPTION.DIAGNOSIS_NAME,PRESCRIPTION.PHARMACIST_NAME,PRESCRIPTION.PRESCRIPTION_CREATE_TIME,
             PRESCRIPTION.DOCTOR_NAME,PRESCRIPTION.DIAGNOSIS_DETAIL)
                 .from(PRESCRIPTION)
                 .where(PRESCRIPTION.PRESCRIPTION_CODE.eq(prescriptionCode))
                 .fetchAnyInto(PrescriptionInfoVo.class);
    }
    /**
     *
     * 获取
     * @param prescriptionCode
     * @return
     */
    public PrescriptionVo getDoByPrescriptionNo(String prescriptionCode){
        return db().select().from(PRESCRIPTION)
                .where(PRESCRIPTION.PRESCRIPTION_CODE.eq(prescriptionCode))
                .fetchAnyInto(PrescriptionVo.class);
    }

    /**
     * 获取一条记录
     *
     * @param id
     * @return
     */
    public <E> E getById(Integer id, Class<? extends E> type) {
        return db().selectFrom(PRESCRIPTION).where(PRESCRIPTION.ID.eq(id)).fetchAnyInto(type);
    }

    /**
     * *****
     * 分页
     *
     * @param param
     * @return
     */
    public PageResult<PrescriptionListVo> listPageResult(PrescriptionListParam param) {
        SelectConditionStep<Record> and = db().select().from(PRESCRIPTION)
                .where(PRESCRIPTION.PATIENT_ID.eq(param.getPatientId()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        if (param.getPrescriptionNos() != null) {
            and.and(PRESCRIPTION.PRESCRIPTION_CODE.in(param.getPrescriptionNos()));
        }
        return getPageResult(and, param, PrescriptionListVo.class);
    }

    /**
     * *****
     * 小程序分页
     * @param param
     * @return
     */
    public PageResult<PrescriptionSimpleVo> listPageResultWx(PrescriptionListParam param) {
        SelectConditionStep<Record> and = db().select().from(PRESCRIPTION)
                .where(PRESCRIPTION.PATIENT_ID.eq(param.getUserPatientParam().getPatientId()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        if (PatientConstant.FETCH.equals(param.getUserPatientParam().getIsFetch())) {
            and.and(PRESCRIPTION.USER_ID.eq(param.getUserPatientParam().getUserId()));
        }
        return getPageResult(and, param, PrescriptionSimpleVo.class);
    }

    /**
     * 小程序分页
     * @return
     */
    public <E> List<E> listPrescriptionByCode(Collection<String> codeList, Class<? extends E> type) {
       return db().select().from(PRESCRIPTION)
                .where(PRESCRIPTION.PRESCRIPTION_CODE.in(codeList))
               .fetchInto(type);
    }
    /**
     * 小程序分页
     * @return
     */
    public <E> Map<String, E> mapPrescriptionByCode(Collection<String> codeList, Class<? extends E> type) {
       return db().select().from(PRESCRIPTION)
                .where(PRESCRIPTION.PRESCRIPTION_CODE.in(codeList))
               .fetchMap(PRESCRIPTION.PRESCRIPTION_CODE,type);
    }

    /**
     * 根据处方号查询处方信息
     * @return
     */
    public List<PrescriptionVo> listPrescriptionList(Collection<String> codeList){
       return db().select(PRESCRIPTION.asterisk()).from(PRESCRIPTION).where(PRESCRIPTION.PRESCRIPTION_CODE.in(codeList)).fetchInto(PrescriptionVo.class);
    }

    /**
     * *****
     * 获取有效处方通过商品id
     * @param goodsId 商品id
     * @param prescriptionNos
     * @return
     */
    public PrescriptionVo getValidByGoodsId(Integer goodsId, List<String> prescriptionNos) {
        return db().select(PRESCRIPTION.asterisk())
                .from(PRESCRIPTION_ITEM)
                .leftJoin(PRESCRIPTION).on(PRESCRIPTION.PRESCRIPTION_CODE.eq(PRESCRIPTION_ITEM.PRESCRIPTION_CODE))
                .where(PRESCRIPTION_ITEM.GOODS_ID.eq(goodsId))
                .and(PRESCRIPTION.PRESCRIPTION_CODE.in(prescriptionNos))
                .and(PRESCRIPTION.PRESCRIPTION_EXPIRE_TIME.gt(DateUtil.date().toTimestamp()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
                .orderBy(PRESCRIPTION.PRESCRIPTION_CREATE_TIME.desc())
                .fetchAnyInto(PrescriptionVo.class);
    }



    /**
     * *****
     * 通用有效名查询处方明细
     *
     * @param prescriptionNos
     * @param goodsCommonName 商品名称
     * @return
     */
    public PrescriptionVo getValidByCommonName( List<String> prescriptionNos, String goodsCommonName){
        return db().select(PRESCRIPTION.asterisk())
                .from(PRESCRIPTION_ITEM)
                .leftJoin(PRESCRIPTION).on(PRESCRIPTION.PRESCRIPTION_CODE.eq(PRESCRIPTION_ITEM.PRESCRIPTION_CODE))
                .where(PRESCRIPTION_ITEM.GOODS_COMMON_NAME.eq(goodsCommonName))
                .and(PRESCRIPTION.PRESCRIPTION_CODE.in(prescriptionNos))
                .and(PRESCRIPTION.PRESCRIPTION_EXPIRE_TIME.gt(DateUtil.date().toTimestamp()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
                .orderBy(PRESCRIPTION.PRESCRIPTION_CREATE_TIME.desc())
                .fetchAnyInto(PrescriptionVo.class);
    }

    /**
     * *****
     * 通过有效通用名和规格系数查询处方明细
     * @param prescriptionNos
     * @param goodsCommonName 通用名
     * @param goodsQualityRatio 规格系数
     */
    public PrescriptionVo getValidByCommonNameAndQualityRatio( List<String> prescriptionNos , String goodsCommonName, String goodsQualityRatio) {
        return db().select(PRESCRIPTION.asterisk())
                .from(PRESCRIPTION_ITEM)
                .leftJoin(PRESCRIPTION).on(PRESCRIPTION.PRESCRIPTION_CODE.eq(PRESCRIPTION_ITEM.PRESCRIPTION_CODE))
                .where(PRESCRIPTION_ITEM.GOODS_COMMON_NAME.eq(goodsCommonName))
                .and(PRESCRIPTION.PRESCRIPTION_CODE.in(prescriptionNos))
                .and(PRESCRIPTION_ITEM.GOODS_QUALITY_RATIO.eq(goodsQualityRatio))
                .and(PRESCRIPTION.PRESCRIPTION_EXPIRE_TIME.gt(DateUtil.date().toTimestamp()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
                .orderBy(PRESCRIPTION.PRESCRIPTION_CREATE_TIME.desc())
                .fetchAnyInto(PrescriptionVo.class);
    }
    /**
     * *****
     * 通过有效通用名和规格系数查询处方明细
     * @param goodsCommonName 通用名
     * @param goodsQualityRatio 规格系数
     * @param productionEnterprise 生产企业
     */
    public PrescriptionVo getValidByCommonNameAndQualityRatio(List<String> prescriptionNos,String goodsCommonName, String goodsQualityRatio,String productionEnterprise) {
        return db().select(PRESCRIPTION.asterisk())
                .from(PRESCRIPTION_ITEM)
                .leftJoin(PRESCRIPTION).on(PRESCRIPTION.PRESCRIPTION_CODE.eq(PRESCRIPTION_ITEM.PRESCRIPTION_CODE))
                .leftJoin(GOODS_MEDICAL_INFO).on(GOODS_MEDICAL_INFO.GOODS_PRODUCTION_ENTERPRISE.eq(productionEnterprise))
                .where(PRESCRIPTION_ITEM.GOODS_COMMON_NAME.eq(goodsCommonName))
                .and(PRESCRIPTION.PRESCRIPTION_CODE.in(prescriptionNos))
                .and(PRESCRIPTION_ITEM.GOODS_QUALITY_RATIO.eq(goodsQualityRatio))
                .and(PRESCRIPTION.PRESCRIPTION_EXPIRE_TIME.gt(DateUtil.date().toTimestamp()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
                .orderBy(PRESCRIPTION.PRESCRIPTION_CREATE_TIME.desc())
                .fetchAnyInto(PrescriptionVo.class);
    }


    /**
     * *****
     * 患者未过期的历史处方no(所有)
     * @param patientId
     * @return
     */
    public List<String> getValidPrescriptionByPatient(Integer patientId) {
        return db().select(PRESCRIPTION.PRESCRIPTION_CODE).from(PRESCRIPTION)
            .where(PRESCRIPTION.PATIENT_ID.eq(patientId))
                .and(PRESCRIPTION.IS_VALID.eq(BaseConstant.YES)
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(String.class);
    }

    /**
     * *****
     * 患者未过期的历史处方no（仅用户自己）
     * @param param
     * @return
     */
    public List<String> getValidPrescriptionByUserPatient(UserPatientParam param) {
        return db().select(PRESCRIPTION.PRESCRIPTION_CODE).from(PRESCRIPTION)
            .where(PRESCRIPTION.PATIENT_ID.eq(param.getPatientId()))
                .and(PRESCRIPTION.USER_ID.eq(param.getUserId()))
                    .and(PRESCRIPTION.IS_VALID.eq(BaseConstant.YES)
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(String.class);
    }

    /**
     * 患者未过期的历史处方列表
     * @param patientId
     * @return
     */
    public List<PrescriptionInfoVo> listValidPrescriptionInfosByPatient(Integer patientId) {
        return db().select().from(PRESCRIPTION)
            .where(PRESCRIPTION.PATIENT_ID.eq(patientId)
                .and(PRESCRIPTION.PRESCRIPTION_EXPIRE_TIME.gt(DateUtil.date().toTimestamp()))
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(PrescriptionInfoVo.class);
    }

    /**
     * 获取患者的历史疾病
     * @param patientId 患者id
     * @return
     */
    public List<PrescriptionDo> listDiagnosis(Integer patientId) {
        return db().select(PRESCRIPTION.DIAGNOSIS_NAME,PRESCRIPTION.PATIENT_TREATMENT_CODE)
                .from(PRESCRIPTION)
                .where(PRESCRIPTION.PATIENT_ID.eq(patientId))
                .fetchInto(PrescriptionDo.class);
    }



    /**
     * 更新处方
     * @param fetchPrescriptionVo 处方入参
     */
    public void updateHitsPrescription(FetchPrescriptionVo fetchPrescriptionVo){
        PrescriptionRecord prescriptionRecord = db().select().from(PRESCRIPTION)
            .where(PRESCRIPTION.ID.eq(fetchPrescriptionVo.getId()))
            .fetchOneInto(PrescriptionRecord.class);
        FieldsUtil.assign(fetchPrescriptionVo, prescriptionRecord);
        prescriptionRecord.update();
        fetchPrescriptionVo.setId(prescriptionRecord.getId());
    }

    /**
     * 根据上次打开已开具页面查询是否有未读消息
     * @param timestamp 上次打开已开具页面时间
     * @return Byte
     */
    public Byte isExistAlreadyReadPrescription(Timestamp timestamp){
        List<Timestamp> timestamps = db().select(PRESCRIPTION.UPDATE_TIME).from(PRESCRIPTION)
            .where(PRESCRIPTION.UPDATE_TIME.gt(timestamp)
                .and(PRESCRIPTION.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
                .and(PRESCRIPTION.IS_VALID.eq(DelFlag.NORMAL_VALUE))).fetchInto(Timestamp.class);
        if (timestamps.isEmpty()) {
            return DelFlag.DISABLE_VALUE;
        }
        return DelFlag.NORMAL_VALUE;
    }

    /**
     * 医生获取处方
     * @param type 0 全部 1审核 2开方 3
     * @param doctorCode
     * @return
     */
    public List<PrescriptionDo>  listAuditedByDoctor(Byte type, String doctorCode) {
        SelectConditionStep<Record> where = db().select()
                .from(PRESCRIPTION)
                .where(PRESCRIPTION.DOCTOR_CODE.eq(doctorCode));
        switch (type){
            case 0:
                //全部
                break;
            case 1:
                //审核
                where.and(PRESCRIPTION.AUDIT_TYPE.eq(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT));
                break;
            case 2:
                //开方
                where.and(PRESCRIPTION.AUDIT_TYPE.eq(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_CREATE));
                break;
            case 3:
                //开方
                where.and(PRESCRIPTION.AUDIT_TYPE.eq(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_PRESCRIPTION));
                break;
            default:
        }
        return where.orderBy(PRESCRIPTION.CREATE_TIME.desc()).fetchInto(PrescriptionDo.class);
    }
}

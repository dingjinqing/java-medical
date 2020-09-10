package com.vpu.mp.dao.shop.patient;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.*;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PatientRecord;
import com.vpu.mp.service.pojo.shop.doctor.DoctorExternalRequestParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.patient.*;
import org.jooq.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.tables.InquiryOrder.INQUIRY_ORDER;

/**
 * @author chenjie
 */
@Repository
public class PatientDao extends ShopBaseDao{
    /**
     * 患者列表
     *
     * @param param
     * @return
     */
    public PageResult<PatientOneParam> getPatientList(PatientListParam param) {
        SelectJoinStep<? extends Record> select = db()
            .select()
            .from(PATIENT);
        select.where(PATIENT.IS_DELETE.eq((byte) 0));
        buildOptions(select, param);
        select.orderBy(PATIENT.ID.desc());
        PageResult<PatientOneParam> patientList = this.getPageResult(select, param.getCurrentPage(),
            param.getPageRows(), PatientOneParam.class);
        return patientList;
    }

    /**
     * 患者搜索查询
     *
     * @param select
     * @param param
     */
    protected void buildOptions(SelectJoinStep<? extends Record> select, PatientListParam param) {
        if (param.getName() != null) {
            select.where(PATIENT.NAME.like(likeValue(param.getName())));
        }
        if (param.getMobile() != null) {
            select.where(PATIENT.MOBILE.like(likeValue(param.getMobile())));
        }
        if (param.getUserId()>0) {
            select.where(PATIENT.ID.in(param.getPatientIds()));
        }
    }

    /**
     * 获取一条患者的信息
     *
     * @param patientId
     * @return
     */
    public PatientOneParam getOneInfo(Integer patientId) {
        PatientOneParam info = db().select().from(PATIENT).where(PATIENT.ID.eq(patientId))
            .fetchOneInto(PatientOneParam.class);
        return info;
    }


    /**
     * 编辑保存
     *
     * @param param
     * @return
     */
    public void updatePatient(PatientDo param) {
        PatientRecord record = new PatientRecord();
        FieldsUtil.assign(param, record);
        db().executeUpdate(record);
    }

    /**
     * 添加患者
     *
     * @param param
     * @return
     */
    public int insertPatient(PatientDo param) {
        PatientRecord record = db().newRecord(PATIENT);
        FieldsUtil.assign(param, record);
        record.insert();
        param.setId(record.getId());
        return record.getId();
    }

    /**
     * 删除
     *
     * @param patientId
     * @return
     */
    public int deletePatient(Integer patientId) {
        int res = db().update(PATIENT).set(PATIENT.IS_DELETE, (byte) 1).where(PATIENT.ID.eq(patientId))
            .execute();
        return res;
    }

    public List<PatientOneParam> listPatientByIds (List<Integer> patientIds) {
        List<PatientOneParam> patientList = db().select().from(PATIENT).where(PATIENT.ID.in(patientIds))
            .fetch().into(PatientOneParam.class);
        return patientList;
    }

    /**
     * 查询患者
     *
     * @param patientInfoParam
     * @return
     */
    public PatientOneParam getPatientByNameAndMobile(UserPatientOneParam patientInfoParam){
        SelectConditionStep<? extends Record> select= db().select().from(PATIENT)
            .where(PATIENT.NAME.eq(patientInfoParam.getName()))
            .and(PATIENT.MOBILE.eq(patientInfoParam.getMobile())).and(PATIENT.IDENTITY_CODE.eq(patientInfoParam.getIdentityCode()));
        return select.fetchOneInto(PatientOneParam.class);
    }

    /**
     * 根据患者姓名，手机号查询当前患者
     * @param userPatientOneParam 患者入参
     * @return PatientOneParam
     */
    public PatientOneParam getPatientByName(UserPatientOneParam userPatientOneParam) {
        return  db().select().from(PATIENT)
            .where(PATIENT.NAME.eq(userPatientOneParam.getName()))
            .and(PATIENT.MOBILE.eq(userPatientOneParam.getMobile()))
            .fetchOneInto(PatientOneParam.class);
    }

    /**
     * 根据患者姓名，手机号查询当前患者
     * @param userPatientOneParam 患者入参
     * @return PatientOneParam
     */
    public PatientOneParam getPatientByName(UserPatientFetchParam userPatientOneParam) {
        return  db().select().from(PATIENT)
            .where(PATIENT.NAME.eq(userPatientOneParam.getName()))
            .and(PATIENT.MOBILE.eq(userPatientOneParam.getMobile()))
            .fetchOneInto(PatientOneParam.class);
    }



    /**
     * 获取患者信息
     * @param patientIds id集合
     * @return
     */
    public List<PatientSimpleInfoVo> listPatientInfo(List<Integer> patientIds){
        return db().select(PATIENT.ID,PATIENT.NAME).from(PATIENT).where(PATIENT.ID.in(patientIds).and(PATIENT.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(PatientSimpleInfoVo.class);
    }


    /**
     * 患者是否存在，用来新增检查
     * @param param
     * @return
     */
    public Integer getPatientExist(PatientExternalRequestParam param) {
        Condition condition = PATIENT.NAME.eq(param.getName()).and(PATIENT.MOBILE.eq(param.getMobile())).and(PATIENT.IDENTITY_TYPE.eq((byte) 1)).and(PATIENT.IDENTITY_CODE.eq(param.getIdentityCode()));
        Integer patientId = db().select(PATIENT.ID).from(PATIENT).where(condition).fetchAnyInto(Integer.class);
        return patientId;
    }

    /**
     * 根据身份证查询患者id
     * @param identityCode 身份证
     * @return Integer
     */
    public Integer getPatientIdByIdentityCode(String identityCode) {
        return db().select(PATIENT.ID)
            .from(PATIENT)
            .where(PATIENT.IDENTITY_CODE.eq(identityCode)).fetchAnyInto(Integer.class);
    }

    /**
     * 用户绑定的患者数
     * @param userId
     * @return
     */
    public Integer countPatientByUser(Integer userId) {
        return db().fetchCount(USER_PATIENT_COUPLE, USER_PATIENT_COUPLE.USER_ID.eq(userId).and(USER_PATIENT_COUPLE.IS_DELETE.eq((byte) 0)));
    }

    /**
     * 查询患者购药记录
     * @param patientMedicineParam 患者id
     * @return PageResult<PatientMedicineVo> 患者购药记录出参
     */
    public PageResult<PatientMedicineVo> getPatientMedicine(PatientMedicineParam patientMedicineParam) {
        SelectOnConditionStep<Record10<String, String, String, String, Integer, BigDecimal, String, String, BigDecimal, Timestamp>> select = db().select(
              GOODS_MEDICAL_INFO.GOODS_COMMON_NAME
            , GOODS_MEDICAL_INFO.GOODS_QUALITY_RATIO
            , GOODS_MEDICAL_INFO.GOODS_PRODUCTION_ENTERPRISE
            , GOODS_MEDICAL_INFO.GOODS_APPROVAL_NUMBER
            , ORDER_GOODS.GOODS_NUMBER
            , ORDER_GOODS.GOODS_PRICE
            , ORDER_GOODS.PRESCRIPTION_CODE
            , ORDER_GOODS.ORDER_SN
            , ORDER_GOODS.DISCOUNTED_TOTAL_PRICE
            , ORDER_GOODS.CREATE_TIME).from(ORDER_GOODS)
            .leftJoin(ORDER_INFO)
            .on(ORDER_INFO.ORDER_SN.eq(ORDER_GOODS.ORDER_SN))
            .leftJoin(GOODS_MEDICAL_INFO)
            .on(ORDER_GOODS.GOODS_ID.eq(GOODS_MEDICAL_INFO.GOODS_ID));
        patientMedicineBuildOptions(select, patientMedicineParam);
        return this.getPageResult(select, patientMedicineParam.getCurrentPage(),
            patientMedicineParam.getPageRows(), PatientMedicineVo.class);
    }

    /**
     * 患者购药记录条件查询筛选
     * @param select 查询实体
     * @param patientMedicineParam 患者条件查询入参
     */
    private void patientMedicineBuildOptions(SelectOnConditionStep<Record10<String, String, String, String, Integer,
        BigDecimal, String, String, BigDecimal, Timestamp>> select, PatientMedicineParam patientMedicineParam) {
        if (patientMedicineParam.getGoodsCommonName() != null && patientMedicineParam.getGoodsCommonName().trim().length() > 0) {
            select.where(GOODS_MEDICAL_INFO.GOODS_COMMON_NAME.like(likeValue(patientMedicineParam.getGoodsCommonName().trim())));
        }
        if (patientMedicineParam.getGoodsApprovalNumber() != null && patientMedicineParam.getGoodsApprovalNumber().trim().length() > 0) {
            select.where(GOODS_MEDICAL_INFO.GOODS_APPROVAL_NUMBER.like(likeValue(patientMedicineParam.getGoodsApprovalNumber())));
        }
        if (patientMedicineParam.getGoodsProductionEnterprise() != null && patientMedicineParam.getGoodsProductionEnterprise().trim().length() > 0) {
            select.where(GOODS_MEDICAL_INFO.GOODS_PRODUCTION_ENTERPRISE.like(likeValue(patientMedicineParam.getGoodsProductionEnterprise())));
        }
        if (patientMedicineParam.getStartTime() != null || patientMedicineParam.getEndTime() != null) {
            select.where(ORDER_GOODS.CREATE_TIME.ge(patientMedicineParam.getStartTime()))
                .and(ORDER_GOODS.CREATE_TIME.le(patientMedicineParam.getEndTime()));
        }
        select.where(ORDER_INFO.PATIENT_ID.eq(patientMedicineParam.getPatientId()))
            .orderBy(ORDER_GOODS.CREATE_TIME.desc());
    }

    /**
     * 根据患者id查询关联处方
     * @param patientPrescriptionParam 查询处方入参
     * @return
     */
    public PageResult<PatientPrescriptionVo> getPatientPrescription(PatientPrescriptionParam patientPrescriptionParam) {
        SelectConditionStep<Record> where = db()
            .select(PRESCRIPTION.asterisk(), ORDER_INFO.ORDER_SN.as("orderSnByOrderInfo"))
            .from(PRESCRIPTION)
            .leftJoin(ORDER_GOODS)
            .on(ORDER_GOODS.PRESCRIPTION_CODE.eq(PRESCRIPTION.PRESCRIPTION_CODE))
            .leftJoin(ORDER_INFO)
            .on(ORDER_INFO.ORDER_SN.eq(ORDER_GOODS.ORDER_SN))
            .where(PRESCRIPTION.PATIENT_ID.eq(patientPrescriptionParam.getPatientId()));
        patientPrescriptionBuildOptions(patientPrescriptionParam, where);
        List<PrescriptionDo> prescriptionDos = where.fetchInto(PrescriptionDo.class);
        return this.getPageResult(where, patientPrescriptionParam.getCurrentPage(),
            patientPrescriptionParam.getPageRows(), PatientPrescriptionVo.class);
    }

    private void patientPrescriptionBuildOptions(PatientPrescriptionParam patientPrescriptionParam, SelectConditionStep<Record> select) {
        if (patientPrescriptionParam.getDoctorName() != null && patientPrescriptionParam.getDoctorName().trim().length() > 0) {
            select.and(PRESCRIPTION.DOCTOR_NAME.like(likeValue(patientPrescriptionParam.getDoctorName().trim())));
        }
        if (patientPrescriptionParam.getDepartmentName() != null && patientPrescriptionParam.getDepartmentName().trim().length() > 0) {
            select.and(PRESCRIPTION.DEPARTMENT_NAME.like(likeValue(patientPrescriptionParam.getDepartmentName().trim())));
        }
        if (patientPrescriptionParam.getPrescriptionType() != null) {
            select.and(PRESCRIPTION.AUDIT_TYPE.eq(patientPrescriptionParam.getPrescriptionType()));
        }
        if (patientPrescriptionParam.getStartTime() != null || patientPrescriptionParam.getEndTime() != null) {
            select.and(PRESCRIPTION.CREATE_TIME.ge(patientPrescriptionParam.getStartTime()))
                .and(PRESCRIPTION.CREATE_TIME.le(patientPrescriptionParam.getEndTime()));
        }
        select.orderBy(PRESCRIPTION.CREATE_TIME.desc());
    }

    public PageResult<InquiryOrderDo> getPatientInquiry(PatientPrescriptionParam patientPrescriptionParam) {
        SelectConditionStep<Record> where = db().select()
            .from(INQUIRY_ORDER)
            .where(INQUIRY_ORDER.PATIENT_ID.eq(patientPrescriptionParam.getPatientId()));
        if (patientPrescriptionParam.getDoctorName() != null && patientPrescriptionParam.getDoctorName().trim().length() > 0) {
            where.and(INQUIRY_ORDER.DOCTOR_NAME.like(likeValue(patientPrescriptionParam.getDoctorName().trim())));
        }
        if (patientPrescriptionParam.getStartTime() != null || patientPrescriptionParam.getEndTime() != null) {
            where.and(INQUIRY_ORDER.CREATE_TIME.ge(patientPrescriptionParam.getStartTime()))
                .and(INQUIRY_ORDER.CREATE_TIME.le(patientPrescriptionParam.getEndTime()));
        }
        where.orderBy(INQUIRY_ORDER.CREATE_TIME.desc());
        return this.getPageResult(where, patientPrescriptionParam.getCurrentPage(),
            patientPrescriptionParam.getPageRows(), InquiryOrderDo.class);
    }
}

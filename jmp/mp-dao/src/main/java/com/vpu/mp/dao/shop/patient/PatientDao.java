package com.vpu.mp.dao.shop.patient;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.PatientDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PatientRecord;
import com.vpu.mp.service.pojo.shop.doctor.DoctorExternalRequestParam;
import com.vpu.mp.service.pojo.shop.patient.*;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.PATIENT;

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
}

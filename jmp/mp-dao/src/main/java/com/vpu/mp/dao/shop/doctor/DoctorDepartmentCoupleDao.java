package com.vpu.mp.dao.shop.doctor;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.DoctorDepartmentCoupleRecord;
import com.vpu.mp.service.pojo.shop.department.DepartmentCodeVo;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorConstant;
import com.vpu.mp.service.pojo.shop.doctor.DoctorConsultationOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorConsultationParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorDepartmentOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * @author chenjie
 */
@Repository
public class DoctorDepartmentCoupleDao extends ShopBaseDao{
    /**
     * 医师科室列表
     *
     * @param doctorId
     * @return
     */
    public List<DepartmentOneParam> getDepartmentsByDoctorId(Integer doctorId) {
        List<DepartmentOneParam> departmentList = db()
            .select(DEPARTMENT.ID,DEPARTMENT.NAME)
            .from(DOCTOR_DEPARTMENT_COUPLE)
            .leftJoin(DEPARTMENT).on(DEPARTMENT.ID.eq(DOCTOR_DEPARTMENT_COUPLE.DEPARTMENT_ID))
            .where(DOCTOR_DEPARTMENT_COUPLE.DOCTOR_ID.eq(doctorId)).and(DOCTOR_DEPARTMENT_COUPLE.IS_DELETE.eq((byte) 0))
            .fetchInto(DepartmentOneParam.class);
        return departmentList;
    }

    /**
     * 获取医师的一个科室
     * @return
     */
    public DepartmentCodeVo getOneCodeByByDoctorId(Integer doctorId){
       return db()
                .select(DEPARTMENT.CODE,DEPARTMENT.NAME)
                .from(DOCTOR_DEPARTMENT_COUPLE)
                .leftJoin(DEPARTMENT).on(DEPARTMENT.ID.eq(DOCTOR_DEPARTMENT_COUPLE.DEPARTMENT_ID))
                .where(DOCTOR_DEPARTMENT_COUPLE.DOCTOR_ID.eq(doctorId))
                .and(DOCTOR_DEPARTMENT_COUPLE.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
                .fetchAnyInto(DepartmentCodeVo.class);
    }

    /**
     * 医师科室名集合
     *
     * @param doctorId
     * @return
     */
    public List<String> getDepartmentNamesByDoctorId(Integer doctorId) {
        List<String> departmentNameList = db()
            .select(DEPARTMENT.NAME)
            .from(DOCTOR_DEPARTMENT_COUPLE)
            .leftJoin(DEPARTMENT).on(DEPARTMENT.ID.eq(DOCTOR_DEPARTMENT_COUPLE.DEPARTMENT_ID))
            .where(DOCTOR_DEPARTMENT_COUPLE.DOCTOR_ID.eq(doctorId)).and(DOCTOR_DEPARTMENT_COUPLE.IS_DELETE.eq((byte) 0)).and(DEPARTMENT.ID.gt(0))
            .fetchInto(String.class);
        return departmentNameList;
    }

    /**
     * 医师科室名集合
     *
     * @param doctorId
     * @return
     */
    public List<Integer> getDepartmentIdsByDoctorId(Integer doctorId) {
        List<Integer> departmentIdList = db()
            .select(DEPARTMENT.ID)
            .from(DOCTOR_DEPARTMENT_COUPLE)
            .leftJoin(DEPARTMENT).on(DEPARTMENT.ID.eq(DOCTOR_DEPARTMENT_COUPLE.DEPARTMENT_ID))
            .where(DOCTOR_DEPARTMENT_COUPLE.DOCTOR_ID.eq(doctorId)).and(DOCTOR_DEPARTMENT_COUPLE.IS_DELETE.eq((byte) 0))
            .fetchInto(Integer.class);
        return departmentIdList;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int deleteDepartmentByDoctor(Integer id) {
        int res = db().delete(DOCTOR_DEPARTMENT_COUPLE).where(DOCTOR_DEPARTMENT_COUPLE.DOCTOR_ID.eq(id))
            .execute();
        return res;
    }

    /**
     * 添加医师科室
     *
     * @param param
     * @return
     */
    public int insertDoctorDepartment(DoctorDepartmentOneParam param) {
        DoctorDepartmentCoupleRecord record = new DoctorDepartmentCoupleRecord();
        FieldsUtil.assign(param, record);
        return db().executeInsert(record);
    }

    /**
     * 医师科室名集合
     *
     * @param departmentIds
     * @return
     */
    public List<Integer> getDoctorIdsByDepartmentIds(List<Integer> departmentIds) {
        List<Integer> doctorIds = db()
            .selectDistinct(DOCTOR_DEPARTMENT_COUPLE.DOCTOR_ID)
            .from(DOCTOR_DEPARTMENT_COUPLE)
            .where(DOCTOR_DEPARTMENT_COUPLE.DEPARTMENT_ID.in(departmentIds)).and(DOCTOR_DEPARTMENT_COUPLE.IS_DELETE.eq((byte) 0))
            .fetchInto(Integer.class);
        return doctorIds;
    }

    /**
     * 供咨询的医师列表
     *
     * @param doctorParam
     * @return
     */
    public PageResult<DoctorConsultationOneParam> listDoctorForConsultation(DoctorConsultationParam doctorParam) {
        Condition condition = DOCTOR.IS_DELETE.eq((byte) 0).and(DOCTOR.STATUS.eq((byte) 1)).and(DOCTOR.CAN_CONSULTATION.eq((byte) 1));
        if (doctorParam.getKeyword() != null && doctorParam.getKeyword() != "") {
            condition = condition.and(DOCTOR.NAME.like(likeValue(doctorParam.getKeyword())).or(DOCTOR.ID.in(doctorParam.getDoctorIds())));
        }
        if (doctorParam.getDepartmentId() != null && doctorParam.getDepartmentId() > 0) {
            condition = condition.and(DOCTOR.ID.in(doctorParam.getDepartmentDoctorIds()));
        }
        if (doctorParam.getTitleId() != null && doctorParam.getTitleId() > 0) {
            condition = condition.and(DOCTOR_TITLE.ID.eq(doctorParam.getTitleId()));
        }
        if (DoctorConstant.ATTENTION_TYPE.equals(doctorParam.getType()) && doctorParam.getUserId() > 0) {
            condition = condition.and(DOCTOR.ID.in(doctorParam.getUserDoctorIds()));
        }
        SelectJoinStep<? extends Record> select = db().select(DOCTOR.ID,DOCTOR.NAME,DOCTOR.IS_ON_DUTY,DOCTOR.TITLE_ID,DOCTOR.SEX
            ,DOCTOR.TREAT_DISEASE,DOCTOR.CONSULTATION_PRICE,DOCTOR_TITLE.NAME.as("titleName")).from(DOCTOR)
            .leftJoin(DOCTOR_TITLE).on(DOCTOR_TITLE.ID.eq(DOCTOR.TITLE_ID));
        select.where(condition);
        select.orderBy(DOCTOR.IS_ON_DUTY.desc());
        if (DoctorConstant.TITLE_SORT_TYPE.equals(doctorParam.getSortType())){
            select.orderBy(DOCTOR_TITLE.FIRST.desc());
        } else if (DoctorConstant.COMMENT_SORT_TYPE.equals(doctorParam.getSortType())){
            select.orderBy(DOCTOR.AVG_COMMENT_STAR.desc());
        } else if (DoctorConstant.ANSWER_SORT_TYPE.equals(doctorParam.getSortType())){
            select.orderBy(DOCTOR.AVG_ANSWER_TIME.desc());
        } else if (DoctorConstant.CONSULTATION_SORT_TYPE.equals(doctorParam.getSortType())){
            select.orderBy(DOCTOR.CONSULTATION_NUMBER.desc());
        } else if (DoctorConstant.ATTENTION_SORT_TYPE.equals(doctorParam.getSortType())){
            select.orderBy(DOCTOR.ATTENTION_NUMBER.desc());
        }
        PageResult<DoctorConsultationOneParam> doctorList = this.getPageResult(select, doctorParam.getCurrentPage(),
            doctorParam.getPageRows(), DoctorConsultationOneParam.class);
        return doctorList;
    }

    /**
     * 已咨询的医师列表
     *
     * @param doctorParam
     * @return
     */
    public List<Integer> listHistoryDoctorIds(UserPatientParam doctorParam) {
        return db().selectDistinct(DOCTOR.ID).from(IM_SESSION)
            .leftJoin(DOCTOR).on(DOCTOR.ID.eq(IM_SESSION.DOCTOR_ID))
            .where(IM_SESSION.USER_ID.eq(doctorParam.getUserId()))
            .and(IM_SESSION.PATIENT_ID.eq(doctorParam.getPatientId()))
            .and(DOCTOR.ID.gt(0))
            .fetchInto(Integer.class);
    }

    /**
     * 供咨询的医师科室列表
     *
     * @param doctorIds
     * @return
     */
    public List<DoctorConsultationOneParam> listHistoryDoctor(List<Integer> doctorIds) {
        SelectHavingStep<Record2<Integer, Integer>> table = db().select(DOCTOR.ID, DSL.count(IM_SESSION.ID).as("number"))
            .from(IM_SESSION)
            .leftJoin(DOCTOR).on(DOCTOR.ID.eq(IM_SESSION.DOCTOR_ID))
            .and(DOCTOR.ID.gt(0)).groupBy(DOCTOR.ID);
        return db().select(DOCTOR.ID,DOCTOR.NAME,DOCTOR.IS_ON_DUTY,DOCTOR.TITLE_ID,DOCTOR.SEX
            ,DOCTOR.TREAT_DISEASE,DOCTOR.CONSULTATION_PRICE,DOCTOR_TITLE.NAME.as("titleName")).from(DOCTOR)
            .leftJoin(DOCTOR_TITLE).on(DOCTOR_TITLE.ID.eq(DOCTOR.TITLE_ID))
            .leftJoin(table).on(table.field(DOCTOR.ID).eq(DOCTOR.ID))
            .where(DOCTOR.ID.in(doctorIds))
            .and(DOCTOR.IS_DELETE.eq((byte) 0))
            .and(DOCTOR.STATUS.eq((byte) 1))
            .orderBy(table.field("number"))
            .limit(10)
            .fetchInto(DoctorConsultationOneParam.class);
    }

    /**
     * 供咨询的医师科室列表
     *
     * @param doctorDepartments
     * @return
     */
    public List<DoctorConsultationOneParam> listDoctorMore(List<Integer> doctorDepartments, Integer limit) {
        SelectHavingStep<Record2<Integer, Integer>> table = db().select(DOCTOR.ID, DSL.count(IM_SESSION.ID).as("number"))
            .from(IM_SESSION)
            .leftJoin(DOCTOR).on(DOCTOR.ID.eq(IM_SESSION.DOCTOR_ID))
            .and(DOCTOR.ID.gt(0)).groupBy(DOCTOR.ID);
        return db().select(DOCTOR.ID,DOCTOR.NAME,DOCTOR.IS_ON_DUTY,DOCTOR.TITLE_ID,DOCTOR.SEX
            ,DOCTOR.TREAT_DISEASE,DOCTOR.CONSULTATION_PRICE,DOCTOR_TITLE.NAME.as("titleName")).from(DOCTOR)
            .leftJoin(DOCTOR_TITLE).on(DOCTOR_TITLE.ID.eq(DOCTOR.TITLE_ID))
            .leftJoin(table).on(table.field(DOCTOR.ID).eq(DOCTOR.ID))
            .where(DOCTOR.ID.notIn(doctorDepartments))
            .and(DOCTOR.IS_DELETE.eq((byte) 0))
            .and(DOCTOR.STATUS.eq((byte) 1))
            .orderBy(table.field("number"))
            .limit(limit)
            .fetchInto(DoctorConsultationOneParam.class);
    }
}

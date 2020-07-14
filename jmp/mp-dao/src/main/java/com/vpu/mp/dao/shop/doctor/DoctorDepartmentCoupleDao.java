package com.vpu.mp.dao.shop.doctor;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.DoctorDepartmentCoupleRecord;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorDepartmentOneParam;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.DEPARTMENT;
import static com.vpu.mp.db.shop.Tables.DOCTOR_DEPARTMENT_COUPLE;

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
            .where(DOCTOR_DEPARTMENT_COUPLE.DOCTOR_ID.eq(doctorId)).and(DOCTOR_DEPARTMENT_COUPLE.IS_DELETE.eq((byte) 0))
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
}

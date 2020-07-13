package com.vpu.mp.dao.shop.doctor;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.DoctorRecord;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorListParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;

@Repository
public class DoctorDao extends ShopBaseDao {
    public static final Integer ROOT_ID = 0;

    /**
     * 医师列表
     *
     * @param param
     * @return
     */
    public PageResult<DoctorOneParam> getDoctorList(DoctorListParam param) {
        SelectJoinStep<? extends Record> select = db()
            .select(DOCTOR.ID, DOCTOR.NAME, DOCTOR.CREATE_TIME,
                DOCTOR.HOSPITAL_CODE, DOCTOR.REGISTER_HOSPITAL, DOCTOR.CERTIFICATE_CODE,DOCTOR.PROFESSIONAL_CODE,
                DOCTOR.REGISTER_TIME,DOCTOR.MOBILE,DOCTOR.DEPARTMENT_ID,DOCTOR.TITLE_ID,
                DOCTOR.STATUS,DOCTOR_TITLE.NAME.as("titleName"))
            .from(DOCTOR).leftJoin(DOCTOR_TITLE).on(DOCTOR_TITLE.ID.eq(DOCTOR.TITLE_ID));
        select.where(DOCTOR.IS_DELETE.eq((byte) 0));
        buildOptions(select, param);
        PageResult<DoctorOneParam> doctorList = this.getPageResult(select, param.getCurrentPage(),
            param.getPageRows(), DoctorOneParam.class);
        return doctorList;
    }

    /**
     * 医师搜索查询
     *
     * @param select
     * @param param
     */
    protected void buildOptions(SelectJoinStep<? extends Record> select, DoctorListParam param) {
        if (param.getName() != null) {
            select.where(DOCTOR.NAME.like(likeValue(param.getName())));
        }
        if (param.getDoctorNo() != null) {
            select.where(DOCTOR.HOSPITAL_CODE.like(param.getDoctorNo()));
        }
        if (param.getDoctorIds() != null) {
            select.where(DOCTOR.HOSPITAL_CODE.in(param.getDoctorIds()));
        }
    }

    /**
     * 获取一条医师的信息
     *
     * @param doctorId
     * @return
     */
    public DoctorOneParam getOneInfo(Integer doctorId) {
        DoctorOneParam info = db().select().from(DOCTOR).where(DOCTOR.ID.eq(doctorId))
            .fetchOneInto(DoctorOneParam.class);
        return info;
    }

    /**
     * 编辑保存
     *
     * @param param
     * @return
     */
    public int updateDoctor(DoctorOneParam param) {
        DoctorRecord record = db().select().from(DOCTOR).where(DOCTOR.ID.eq(param.getId()))
            .fetchOneInto(DoctorRecord.class);
        FieldsUtil.assign(param, record);
        return db().executeUpdate(record);
    }

    /**
     * 添加医师
     *
     * @param param
     * @return
     */
    public int insertDoctor(DoctorOneParam param) {
        DoctorRecord record = new DoctorRecord();
        FieldsUtil.assign(param, record);
        return db().executeInsert(record);
    }

    /**
     * 删除
     *
     * @param doctorId
     * @return
     */
    public int deleteDoctor(Integer doctorId) {
        int res = db().update(DOCTOR).set(DOCTOR.IS_DELETE, (byte) 1).where(DOCTOR.ID.eq(doctorId))
            .execute();
        return res;
    }

//    /**
//     * 医师是否存在，用来新增检查
//     * @param departmentId 科室ID
//     * @param name 科室名称
//     * @return true 存在 false 不存在
//     */
//    public boolean isNameExist(Integer departmentId,String name) {
//        Condition condition = DEPARTMENT.NAME.eq(name);
//        if (departmentId != null) {
//            condition = condition.and(DEPARTMENT.ID.ne(departmentId));
//        }
//        int count = db().fetchCount(DEPARTMENT, condition);
//        return count>0;
//    }
}

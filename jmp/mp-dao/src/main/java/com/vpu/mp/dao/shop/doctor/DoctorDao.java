package com.vpu.mp.dao.shop.doctor;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.DoctorDo;
import com.vpu.mp.common.pojo.shop.table.UserDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.Department;
import com.vpu.mp.db.shop.tables.records.DoctorRecord;
import com.vpu.mp.service.pojo.shop.department.DepartmentListVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAuthParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorListParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorSimpleVo;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * @author chenjie
 */
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
            .select(DOCTOR.asterisk(),DOCTOR_TITLE.NAME.as("titleName"))
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
    public void updateDoctor(DoctorOneParam param) {
        DoctorRecord record = db().select().from(DOCTOR).where(DOCTOR.ID.eq(param.getId()))
            .fetchOneInto(DoctorRecord.class);
        FieldsUtil.assign(param, record);
        record.update();
        param.setId(record.getId());
    }

    /**
     * 添加医师
     *
     * @param param
     * @return
     */
    public void insertDoctor(DoctorOneParam param) {
        DoctorRecord record = db().newRecord(DOCTOR);
        FieldsUtil.assign(param, record);
        record.insert();
        param.setId(record.getId());
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

    /**
     * 获取一条医师的信息(根据hospitalCode)
     *
     * @param hospitalCode
     * @return
     */
    public DoctorOneParam getDoctorByHospitalCode(String hospitalCode) {
        return db().select().from(DOCTOR).where(DOCTOR.HOSPITAL_CODE.eq(hospitalCode))
            .fetchOneInto(DoctorOneParam.class);
    }

    /**
     * 医师是否存在，用来新增检查
     * @param doctorId 医师ID
     * @param code 医师Code
     * @return true 存在 false 不存在
     */
    public boolean isCodeExist(Integer doctorId,String code) {
        Condition condition = DOCTOR.HOSPITAL_CODE.eq(code);
        if (doctorId != null) {
            condition = condition.and(DOCTOR.ID.ne(doctorId));
        }
        int count = db().fetchCount(DOCTOR, condition);
        return count>0;
    }

    /**
     * @Author 赵晓东
     * @Create 2020-07-22 14:53:51
     */
    /**
     * 医师认证
     * @param doctorAuthParam 医师认证入参
     * @return DoctorDo
     */
    public DoctorDo doctorAuth(DoctorAuthParam doctorAuthParam) {
        DoctorDo doctorDo = new DoctorDo();
        doctorDo = db().select().from(DOCTOR)
            .where(DOCTOR.NAME.eq(doctorAuthParam.getName())
            .and(DOCTOR.MOBILE.eq(doctorAuthParam.getMobile())
                .and(DOCTOR.HOSPITAL_CODE.eq(doctorAuthParam.getHospitalCode()))))
            .fetchAnyInto(DoctorDo.class);
        return doctorDo;
    }

    /**
     * 更新医师表用户id
     * @param userDo 当前用户
     * @return int
     */
    public int updateUserId(UserDo userDo){
        return db().update(DOCTOR).set(DOCTOR.USER_ID, userDo.getUserId())
            .where(DOCTOR.NAME.eq(userDo.getUsername())
                .and(DOCTOR.MOBILE.eq(userDo.getMobile()))).execute();
    }

    /**
     * 查询医师信息集合
     * @param doctorIds 医师id集合
     * @return
     */
    public List<DoctorSimpleVo> listDoctorSimpleInfo(List<Integer> doctorIds) {
        return db().select(DOCTOR.ID,DOCTOR.NAME).from(DOCTOR).where(DOCTOR.ID.in(doctorIds).and(DOCTOR.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(DoctorSimpleVo.class);
    }

    /**
     *
     * 	根据医生id查询医生所属科室
     * @param doctorId 医师id
     * @return List<Department>
     */
    public List<DepartmentListVo> selectDepartmentsByDoctorId(Integer doctorId){
        return db().select().from(DEPARTMENT)
            .join(DOCTOR_DEPARTMENT_COUPLE)
            .on(DEPARTMENT.ID.eq(DOCTOR_DEPARTMENT_COUPLE.DEPARTMENT_ID)
            .and(DOCTOR_DEPARTMENT_COUPLE.DOCTOR_ID.eq(doctorId))
            .and(DEPARTMENT.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(DepartmentListVo.class);
    }

}

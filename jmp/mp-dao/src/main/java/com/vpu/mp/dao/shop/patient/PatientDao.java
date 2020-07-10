package com.vpu.mp.dao.shop.patient;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PatientRecord;
import com.vpu.mp.service.pojo.shop.patient.PatientListParam;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.PATIENT;

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
        Timestamp nowDate = new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取一条患者的信息
     *
     * @param patientId
     * @return
     */
    public PatientOneParam getOneInfo(Integer patientId) {
        PatientOneParam info = db().select().from(PATIENT).where(PATIENT.ID.eq(patientId))
            .fetchOne().into(PatientOneParam.class);
        return info;
    }

    /**
     * 编辑保存
     *
     * @param param
     * @return
     */
    public int updatePatient(PatientOneParam param) {
        PatientRecord record = new PatientRecord();
        FieldsUtil.assign(param, record);
        return db().executeUpdate(record);
    }

    /**
     * 添加患者
     *
     * @param param
     * @return
     */
    public int insertPatient(PatientOneParam param) {
        PatientRecord record = new PatientRecord();
        FieldsUtil.assign(param, record);
        return db().executeInsert(record);
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

    public List<PatientOneParam> listPatientByIds (List<Integer> patientIds) {
        List<PatientOneParam> patientList = db().select().from(PATIENT).where(PATIENT.ID.in(patientIds))
            .fetch().into(PatientOneParam.class);
        return patientList;
    }
}

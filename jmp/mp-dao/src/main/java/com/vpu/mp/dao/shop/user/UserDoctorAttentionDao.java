package com.vpu.mp.dao.shop.user;

import com.vpu.mp.common.pojo.shop.table.UserDoctorAttentionDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.UserDoctorAttentionRecord;
import com.vpu.mp.service.pojo.shop.doctor.DoctorDutyParam;
import com.vpu.mp.service.pojo.shop.user.user.UserDoctorParam;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.PATIENT;
import static com.vpu.mp.db.shop.Tables.USER_DOCTOR_ATTENTION;
/**
 * @author chenjie
 * @date 2020年08月11日
 */
@Repository
public class UserDoctorAttentionDao extends ShopBaseDao {
    /**
     * 新增用户医师关注
     * @param
     * @return
     */
    public int insertUserDoctor(UserDoctorAttentionDo userDoctorAttentionDo){
        UserDoctorAttentionRecord record=db().newRecord(USER_DOCTOR_ATTENTION,userDoctorAttentionDo);
        record.insert();
        userDoctorAttentionDo.setId(record.getId());
        return record.getId();
    }

    /**
     * 解除用户医师关注
     * @param param
     */
    public void deleteUserDoctor(UserDoctorParam param) {
        db().delete(USER_DOCTOR_ATTENTION).where(USER_DOCTOR_ATTENTION.USER_ID.eq(param.getUserId())).and(USER_DOCTOR_ATTENTION.DOCTOR_ID.eq(param.getDoctorId()))
            .execute();
    }

    /**
     * 获取用户关注医师Id集合
     * @param userId
     * @return
     */
    public List<Integer> listDoctorIdsByUser(Integer userId) {
        List<Integer> doctorIds = db().select(USER_DOCTOR_ATTENTION.DOCTOR_ID).from(USER_DOCTOR_ATTENTION)
            .where(USER_DOCTOR_ATTENTION.USER_ID.eq(userId).and(USER_DOCTOR_ATTENTION.IS_DELETE.eq((byte) 0)))
            .fetchInto(Integer.class);
        return doctorIds;
    }

    /**
     * 获取医师关注数
     * @param doctorId
     * @return
     */
    public Integer getAttentionNumber(Integer doctorId) {
        return db().selectCount().from(USER_DOCTOR_ATTENTION)
            .where(USER_DOCTOR_ATTENTION.DOCTOR_ID.eq(doctorId))
            .fetchAnyInto(Integer.class);
    }
}

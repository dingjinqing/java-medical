package com.vpu.mp.dao.shop.doctor;

import com.vpu.mp.common.pojo.shop.table.DoctorLoginLogDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.ANCHOR_POINTS;
import static com.vpu.mp.db.shop.Tables.DOCTOR_LOGIN_LOG;
import static org.jooq.impl.DSL.date;

/**
 * @author 孔德成
 * @date 2020/9/16 14:07
 */
@Repository
public class DoctorLoginLogDao extends ShopBaseDao {


    /**
     * 医师的出勤天数
     * @param doctorId 医师id
     * @return
     */
    public Integer getDoctorAttendanceDayNum(Integer doctorId, Timestamp startTime, Timestamp endTime) {
        Integer count = db().selectCount().from(DOCTOR_LOGIN_LOG)
                .where(DOCTOR_LOGIN_LOG.DOCTOR_ID.eq(doctorId))
                .and(DOCTOR_LOGIN_LOG.CREATE_TIME.between(startTime, endTime))
                .groupBy(date(ANCHOR_POINTS.CREATE_TIME))
                .fetchAnyInto(Integer.class);
        return count==null?0:count;

    }

    /**
     * 保存
     * @param param
     * @return
     */
    public Integer save(DoctorLoginLogDo param) {
        return db().newRecord(DOCTOR_LOGIN_LOG, param).insert();
    }
}

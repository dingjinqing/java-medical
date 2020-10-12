package com.vpu.mp.dao.shop.doctor;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.DoctorLoginLogDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAttendanceListParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAttendanceOneParam;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.DOCTOR;
import static com.vpu.mp.db.shop.Tables.DOCTOR_LOGIN_LOG;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.date;

/**
 * @author 孔德成
 * @date 2020/9/16 14:07
 */
@Repository
public class DoctorLoginLogDao extends ShopBaseDao {


    public final static String  NAME ="name";
    public final static String  LAST_TIME ="last_time";
    public final static String  LOGIN_DAYS ="login_days";
    public final static String  DATE ="date";
    public final static String  VALUE ="value";
    public final static Byte  THIS_MONTH = 1;
    public final static Integer  INTEGER_ZERO = 0;
    /**
     * 医师的出勤天数
     * @param doctorId 医师id
     * @return
     */
    public Integer getDoctorAttendanceDayNum(Integer doctorId, Timestamp startTime, Timestamp endTime) {
        List<Integer> integers = db().selectCount().from(DOCTOR_LOGIN_LOG)
                .where(DOCTOR_LOGIN_LOG.DOCTOR_ID.eq(doctorId))
                .and(DOCTOR_LOGIN_LOG.CREATE_TIME.between(startTime, endTime))
                .groupBy(date(DOCTOR_LOGIN_LOG.CREATE_TIME))
                .fetchInto(Integer.class);
        return integers.size();

    }

    /**
     * 保存
     * @param param
     * @return
     */
    public Integer save(DoctorLoginLogDo param) {
        return db().newRecord(DOCTOR_LOGIN_LOG, param).insert();
    }

    /**
     * 医师出勤率
     * @param param
     * @return
     */
    public PageResult<DoctorAttendanceOneParam> getDoctorAttendancePage(DoctorAttendanceListParam param) {
        Timestamp startTime = getStartTime(param.getType());
        SelectJoinStep<? extends Record> select = db().select(DOCTOR.ID.as("doctor_id"),DSL.countDistinct(date(DOCTOR_LOGIN_LOG.CREATE_TIME)).as(LOGIN_DAYS)
            , DSL.max(DOCTOR_LOGIN_LOG.CREATE_TIME).as(LAST_TIME),DOCTOR.NAME)
            .from(DOCTOR)
            .leftJoin(DOCTOR_LOGIN_LOG).on(DOCTOR.ID.eq(DOCTOR_LOGIN_LOG.DOCTOR_ID).and(DOCTOR_LOGIN_LOG.CREATE_TIME.ge(startTime)).and(DOCTOR_LOGIN_LOG.CREATE_TIME.ge(DOCTOR.AUTH_TIME)));
        select.groupBy(DOCTOR.ID,DOCTOR.NAME).orderBy(DSL.countDistinct(date(DOCTOR_LOGIN_LOG.CREATE_TIME)).desc());
        return this.getPageResult(select, param.getCurrentPage(), 5, DoctorAttendanceOneParam.class);
    }

    public Timestamp getStartTime(Byte type) {
        if (THIS_MONTH.equals(type)) {
            LocalDateTime today = LocalDate.now().atStartOfDay();
            DateTime mouthStart = DateUtil.beginOfMonth(Date.valueOf(today.toLocalDate()));
            Timestamp mouthStartTime = new Timestamp(mouthStart.getTime());
            return mouthStartTime;
        }
        LocalDateTime today = LocalDate.now().atStartOfDay();
        return Timestamp.valueOf(today.minusDays(30));
    }

    public List<Integer> getDoctorIds(Integer min, Integer max,Byte type){
        Timestamp startTime = getStartTime(type);
        return db().select(DOCTOR.ID)
            .from(DOCTOR)
            .leftJoin(DOCTOR_LOGIN_LOG).on(DOCTOR_LOGIN_LOG.DOCTOR_ID.eq(DOCTOR.ID).and(DOCTOR_LOGIN_LOG.CREATE_TIME.ge(startTime)))
            .groupBy(DOCTOR.ID)
            .having(DSL.countDistinct(date(DOCTOR_LOGIN_LOG.CREATE_TIME)).ge(min).and(DSL.countDistinct(date(DOCTOR_LOGIN_LOG.CREATE_TIME)).lt(max)))
            .fetchInto(Integer.class);
    }

    /**
     * 根据医师id获取出勤信息
     * @param doctorId
     * @param startTime
     * @param endTime
     * @return
     */
    public DoctorAttendanceOneParam getDoctorAttend(Integer doctorId,Timestamp startTime,Timestamp endTime){
        SelectJoinStep<? extends Record> select = db().select(DOCTOR_LOGIN_LOG.DOCTOR_ID,DSL.countDistinct(date(DOCTOR_LOGIN_LOG.CREATE_TIME)).as(LOGIN_DAYS)
            , DSL.max(DOCTOR_LOGIN_LOG.CREATE_TIME).as(LAST_TIME),DOCTOR.NAME)
            .from(DOCTOR)
            .leftJoin(DOCTOR_LOGIN_LOG).on(DOCTOR.ID.eq(DOCTOR_LOGIN_LOG.DOCTOR_ID));
        select.where(DOCTOR.ID.eq(doctorId));
        if(startTime!=null){
            select.where(DOCTOR_LOGIN_LOG.CREATE_TIME.ge(startTime));
        }
        if(endTime!=null){
            select.where(DOCTOR_LOGIN_LOG.CREATE_TIME.le(endTime));
        }
        select.groupBy(DOCTOR_LOGIN_LOG.DOCTOR_ID,DOCTOR.NAME).orderBy(DSL.countDistinct(date(DOCTOR_LOGIN_LOG.CREATE_TIME)).desc());
        return select.fetchAnyInto(DoctorAttendanceOneParam.class);
    }

    /**
     * 医师出勤排名
     * @param
     * @return
     */
    public Integer getDoctorAttendanceRank(Integer loginDays, Byte type) {
        Timestamp startTime = getStartTime(type);
        Integer rank = db().selectCount()
            .from(DOCTOR)
            .leftJoin(DOCTOR_LOGIN_LOG).on(DOCTOR.ID.eq(DOCTOR_LOGIN_LOG.DOCTOR_ID).and(DOCTOR_LOGIN_LOG.CREATE_TIME.ge(startTime)).and(DOCTOR_LOGIN_LOG.CREATE_TIME.ge(DOCTOR.AUTH_TIME)))
            .groupBy(DOCTOR.ID)
            .having(DSL.countDistinct(date(DOCTOR_LOGIN_LOG.CREATE_TIME)).gt(loginDays))
            .fetchOneInto(Integer.class);
        return (rank == null) ? 1:(rank+1);
    }
}

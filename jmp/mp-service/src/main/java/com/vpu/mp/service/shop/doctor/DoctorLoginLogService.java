package com.vpu.mp.service.shop.doctor;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.pojo.shop.table.DoctorLoginLogDo;
import com.vpu.mp.dao.shop.doctor.DoctorLoginLogDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2020/9/16 14:05
 */
@Service
public class DoctorLoginLogService extends ShopBaseService {


    @Autowired
    private DoctorLoginLogDao doctorLoginLogDao;

    /**
     * 医师的出勤天数
     *
     * @param doctorId 医师id
     * @return
     */
    public String getDoctorAttendanceRate(Integer doctorId, Timestamp startTime, Timestamp endTime) {
        int attendanceDay = doctorLoginLogDao.getDoctorAttendanceDayNum(doctorId,startTime,endTime);
        long  dueAttendanceDay = DateUtil.betweenDay(startTime, endTime,true)+1;
        return BigDecimal.valueOf(attendanceDay).divide(BigDecimal.valueOf(dueAttendanceDay),3,BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 添加医师登录记录
     *
     * @param param
     * @return
     */
    public Integer save(DoctorLoginLogDo param) {
        return doctorLoginLogDao.save(param);

    }


}

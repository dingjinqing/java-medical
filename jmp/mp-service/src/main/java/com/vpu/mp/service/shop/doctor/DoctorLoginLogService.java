package com.vpu.mp.service.shop.doctor;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.DoctorLoginLogDo;
import com.vpu.mp.dao.shop.doctor.DoctorLoginLogDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAttendanceDivideVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAttendanceOneParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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

    public Integer getDoctorNum(Integer min, Integer max){
        List<Integer> doctorIds = doctorLoginLogDao.getDoctorIds(min,max);
        return doctorIds.size();
    }

    public DoctorAttendanceDivideVo getDoctorAttendanceDivide(){
        Integer dayOfMonth = DateUtils.getLocalDate().getDayOfMonth();
        Integer halfDays = (Integer)(int)Math.ceil(Double.valueOf(dayOfMonth)/Double.valueOf(2));
        Integer thirdQuarterDays = (Integer)(int)Math.ceil(Double.valueOf(dayOfMonth)/Double.valueOf(2));
        DoctorAttendanceDivideVo doctorAttendanceDivideVo = new DoctorAttendanceDivideVo();
        doctorAttendanceDivideVo.setHalfNum(getDoctorNum(0,halfDays));
        doctorAttendanceDivideVo.setThirdQuarterNum(getDoctorNum(halfDays,thirdQuarterDays));
        doctorAttendanceDivideVo.setFourthQuarterNum(getDoctorNum(thirdQuarterDays,dayOfMonth+1));
        return doctorAttendanceDivideVo;
    }

    /**
     * 医师出勤率
     * @param page
     * @return
     */
    public PageResult<DoctorAttendanceOneParam> getDoctorAttendancePage(Integer page) {
        Integer dayOfMonth = DateUtils.getLocalDate().getDayOfMonth();
        PageResult<DoctorAttendanceOneParam> dataList = doctorLoginLogDao.getDoctorAttendancePage(page);
        for(DoctorAttendanceOneParam data:dataList.getDataList()) {
            data.setLoginRate(new BigDecimal(Double.valueOf(data.getLoginDays())/Double.valueOf(dayOfMonth)));
        }
        return dataList;
    }

}

package com.vpu.mp.service.shop.doctor;

import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.DoctorLoginLogDo;
import com.vpu.mp.dao.shop.doctor.DoctorLoginLogDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAttendanceDivideVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAttendanceListParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAttendanceListVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorAttendanceOneParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 孔德成
 * @date 2020/9/16 14:05
 */
@Service
public class DoctorLoginLogService extends ShopBaseService {


    @Autowired
    private DoctorLoginLogDao doctorLoginLogDao;

    public final static Byte  THIS_MONTH = 1;


    /**
     * 添加医师登录记录
     *
     * @param param
     * @return
     */
    public Integer save(DoctorLoginLogDo param) {
        return doctorLoginLogDao.save(param);

    }

    public Integer getDoctorNum(Integer min, Integer max,Byte type){
        List<Integer> doctorIds = doctorLoginLogDao.getDoctorIds(min,max,type);
        return doctorIds.size();
    }

    public DoctorAttendanceDivideVo getDoctorAttendanceDivide(Byte type){
        Integer days = 30;
        if (THIS_MONTH.equals(type)) {
            days = DateUtils.getLocalDate().getDayOfMonth();
        }
        Integer halfDays = (Integer)(int)Math.ceil(Double.valueOf(days)/Double.valueOf(2));
        Integer thirdQuarterDays = (Integer)(int)Math.ceil(Double.valueOf(days)*Double.valueOf(0.75));
        DoctorAttendanceDivideVo doctorAttendanceDivideVo = new DoctorAttendanceDivideVo();
        doctorAttendanceDivideVo.setHalfNum(getDoctorNum(0,halfDays,type));
        doctorAttendanceDivideVo.setThirdQuarterNum(getDoctorNum(halfDays,thirdQuarterDays,type));
        doctorAttendanceDivideVo.setFourthQuarterNum(getDoctorNum(thirdQuarterDays,days+1,type));
        return doctorAttendanceDivideVo;
    }

    /**
     * 医师出勤率
     * @param param
     * @return
     */
    public DoctorAttendanceListVo getDoctorAttendancePage(DoctorAttendanceListParam param) {
        Integer dayOfMonth = THIS_MONTH.equals(param.getType()) ? DateUtils.getLocalDate().getDayOfMonth():30;
        PageResult<DoctorAttendanceOneParam> dataList = doctorLoginLogDao.getDoctorAttendancePage(param);
        BigDecimal loginRate = new BigDecimal(1);
        BigDecimal lastRate = param.getLastRate();
        Integer lastRank  = param.getLastRank();
        Integer index = 1;
        for(DoctorAttendanceOneParam data:dataList.getDataList()) {
            loginRate = new BigDecimal(Double.valueOf(data.getLoginDays())/Double.valueOf(dayOfMonth)).setScale(2, BigDecimal.ROUND_HALF_UP);
            data.setLoginRate(loginRate);
            if(!lastRate.equals(loginRate)) {
                lastRank = (dataList.getPage().getCurrentPage() - 1)*5 + index;
            }
            data.setLoginRank(lastRank);
            lastRate = loginRate;
            index++;
        }
        DoctorAttendanceListVo doctorList = new DoctorAttendanceListVo();
        doctorList.setDoctorList(dataList);
        doctorList.setLastRank(lastRank);
        doctorList.setLastRate(lastRate);
        return doctorList;
    }

}

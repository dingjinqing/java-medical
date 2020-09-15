package com.vpu.mp.service.shop.task.doctor;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.doctor.DoctorStatisticParam;
import com.vpu.mp.service.shop.doctor.DoctorStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.vpu.mp.service.shop.task.overview.GoodsStatisticTaskService.TYPE_LIST_1;

/**
 * @author chenjie
 * @date 2020年09月15日
 */
@Service
public class DoctorTaskService extends ShopBaseService {
    @Autowired
    public DoctorStatisticService doctorStatisticService;
    public void insertDoctorStatistic(Integer doctorId) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        TYPE_LIST_1.forEach((e) -> {
            DoctorStatisticParam param = new DoctorStatisticParam();
            param.setDoctorId(doctorId);
            param.setStartTime(Timestamp.valueOf(today.minusDays(e+1)));
            param.setEndTime(Timestamp.valueOf(today.minusDays(1)));
            param.setType(e);
            param.setRefDate(Date.valueOf(today.minusDays(1).toLocalDate()));
            doctorStatisticService.statisticDoctor(param);
        });
    }
}

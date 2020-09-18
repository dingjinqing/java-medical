package com.vpu.mp.service.shop.task.department;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.department.DepartmentStatisticParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorStatisticMinMaxVo;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticParam;
import com.vpu.mp.service.shop.department.DepartmentService;
import com.vpu.mp.service.shop.department.DepartmentStatisticService;
import com.vpu.mp.service.shop.store.statistic.StoreOrderSummaryTrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.vpu.mp.service.shop.task.overview.GoodsStatisticTaskService.TYPE_LIST_1;

/**
 * @author chenjie
 * @date 2020年08月27日
 */
@Service
public class DepartmentTaskService extends ShopBaseService {
    @Autowired
    public DepartmentStatisticService departmentStatisticService;
    public void insertDepartmentStatistic(Integer departmentId) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        TYPE_LIST_1.forEach((e) -> {
            DepartmentStatisticParam param = new DepartmentStatisticParam();
            param.setDepartmentId(departmentId);
            param.setStartTime(Timestamp.valueOf(today.minusDays(e+1)));
            param.setEndTime(Timestamp.valueOf(today.minusDays(1)));
            param.setType(e);
            param.setRefDate(Date.valueOf(today.minusDays(1).toLocalDate()));
            departmentStatisticService.statisticDepartment(param);
        });
    }

    public void updateDepartmentStatisticScore(Byte type,Date refDate, DoctorStatisticMinMaxVo doctorStatisticMinMax) {
        departmentStatisticService.updateDepartmentStatisticScore(type,refDate,doctorStatisticMinMax);
    }
}

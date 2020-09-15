package com.vpu.mp.schedule;

import com.vpu.mp.service.pojo.saas.shop.ShopListInfoVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenjie
 * @date 2020年09月15日
 */
@Component
@ConditionalOnProperty(prefix = "schedule", name = "switch", havingValue = "on")
public class DoctorStatisticScheduleTask {
    @Autowired
    private SaasApplication saas;
    /**
     * 医师业绩统计
     * b2c_doctor_summary_trend医师业绩统计表
     * 每天凌晨零点过后8秒开始统计前一天的数据
     */
    @Scheduled(cron = "8 0 0 * * ?")
    public void doctorStatistics() {
        List<ShopListInfoVo> result = saas.shopService.getShopListInfo();
        result.forEach((r) -> {
            ShopApplication shop = saas.getShopApp(r.getShopId());
            List<DoctorOneParam> allDoctors = shop.doctorService.getAllDoctor();
            allDoctors.forEach((d)->{
                shop.doctorTaskService.insertDoctorStatistic(d.getId());
            });
        });
    }
}

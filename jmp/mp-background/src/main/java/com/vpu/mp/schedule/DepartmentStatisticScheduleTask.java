package com.vpu.mp.schedule;

import com.vpu.mp.service.pojo.saas.shop.ShopListInfoVo;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenjie
 * @date 2020年08月27日
 */
@Component
@ConditionalOnProperty(prefix = "schedule", name = "switch", havingValue = "on")
public class DepartmentStatisticScheduleTask {
    @Autowired
    private SaasApplication saas;
    /**
     * 门店订单统计
     * b2c_store_order_summary_trend 商品概况信息表
     * 每天凌晨零点过后8秒开始统计前一天的数据
     */
    @Scheduled(cron = "8 0 0 * * ?")
    public void storeOrderStatistics() {
        List<ShopListInfoVo> result = saas.shopService.getShopListInfo();
        result.forEach((r) -> {
            ShopApplication shop = saas.getShopApp(r.getShopId());
            List<DepartmentOneParam> allStore = shop.departmentService.getAllDepartment();
            allStore.forEach((d)->{
                shop.departmentTaskService.insertDepartmentStatistic(d.getId());
            });
        });
    }
}

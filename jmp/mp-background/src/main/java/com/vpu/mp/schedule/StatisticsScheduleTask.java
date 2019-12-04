package com.vpu.mp.schedule;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.shop.ShopApplication;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author liufei
 * @date 11/18/19
 * 概况模块-统计信息定时任务
 */
@Component
@EnableScheduling
@EnableAsync
@ConditionalOnProperty(prefix = "schedule", name = "statistics", havingValue = "on")
public class StatisticsScheduleTask {

    @Autowired
    private SaasApplication saas;

    /**
     * 商品统计
     * b2c_goods_overview_summary 商品概况信息表
     * b2c_goods_summary 商品效果信息表
     * 每天凌晨零点过后8秒开始统计前一天的数据
     */
    @Scheduled(cron = "8 * 0 * * ?")
    public void goodsStatistics() {
        Result<ShopRecord> result = saas.shop.getAll();
        result.forEach((r) -> {
            ShopApplication shop = saas.getShopApp(r.getShopId());
            shop.shopTaskService.goodsStatisticTaskService.insertOverview();
            shop.shopTaskService.goodsStatisticTaskService.insertGoodsSummary();
        });
    }
}
